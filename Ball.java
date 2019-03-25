import biuoop.DrawSurface;
/**
 * @author Shir sabo
 **/
public class Ball {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity vel;
    private  double xLeftUpperCorner;
    private  double yLeftUpperCorner;
    private int length;
    private int height;
    private GameEnvironment game;
    /**
     * Constructor.
     * @param x double
     * @param y double
     * @param r int
     * @param color java.awt.Color
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        // calls to the other constructor
        this(new Point(x, y), r, color);
    }
    /**
     * Constructor.
     * @param center point
     * @param r int
     * @param color java.awt.Color
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.vel = new Velocity(0, 0);
    }
    /**
     * Sets boundries to the ball's movemet.
     * @param xLeftUpperCornerIn double
     * @param yLeftUpperCornerIn double
     * @param lengthIn int
     * @param heightIn int
     */
    public void setBoundries(double xLeftUpperCornerIn, double yLeftUpperCornerIn, int lengthIn, int heightIn) {
       this.xLeftUpperCorner =  xLeftUpperCornerIn;
       this.yLeftUpperCorner = yLeftUpperCornerIn;
       this.length = lengthIn;
       this.height = heightIn;
    }
    /**
     * Accessor to x.
     * @return Output: int
     */
    public int getX() {
        return (int) (this.center).getX();
    }
    /**
     * Accessor to y.
     * @return Output: int
     */
    public int getY() {

        return (int) (this.center).getY();
    }
    /**
     * Accessor to radius.
     * @return Output: int
     */
    public int getSize() {
        return this.radius;
    }
    /**
     * Accessor to the ball's color.
     * @return Output: java.awt.Color
     */
    public java.awt.Color getColor() {

        return this.color;
    }
    /**
     * Draws the ball on the given DrawSurface.
     * @param surface DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
    }
    /**
     * Sets velocity.
     * @param v velocity
     */
    public void setVelocity(Velocity v) {

        this.vel = v;
    }
    /**
     * Sets velocity.
     * @param dx velocity
     * @param dy velocity
     */
    public void setVelocity(double dx, double dy) {
        this.vel = new Velocity(dx, dy);
    }
    /**
     * Accessor to velocity.
     * @return Output: velocity
     */
    public Velocity getVelocity() {

        return this.vel;
    }
    /**
     * Creates the ball's next stop according to it's boundaries in space.
     */
    public void moveOneStep() {
        double x1 = this.center.getX();
        double y1 = this.center.getY();
        double dx = this.vel.getDx();
        double dy = this.vel.getDy();
        //if the ball is in bottom
        if (y1 + this.vel.getDy() + this.radius >= this.height) {
            if (this.vel.getDy() > 0) {
                this.vel = new Velocity(dx, -dy);
                dy = -dy;
            }
        }
        //if the ball is on the right wall
        if (x1 + this.vel.getDx() + this.radius >= this.length) {
            if (this.vel.getDx() > 0) {
                this.vel = new Velocity(-dx, dy);
                dx = -dx;
            }
        }
        //if the ball is on the ceiling
        if  (y1 + this.vel.getDy() - this.radius <= this.yLeftUpperCorner) {
            if (this.vel.getDy() < 0) {
                this.vel = new Velocity(dx, -dy);
                dy = -dy;

            }
        }
        //if the ball is on the left wall
        if (x1 + this.vel.getDx() - this.radius <= this.xLeftUpperCorner) {
            if (this.vel.getDx() < 0) {
                this.vel = new Velocity(-dx, dy);
                dx = -dx;
            }
        }
        checkGameCollidables();
        // Updates the ball's location
        this.center = this.getVelocity().applyToPoint(this.center);
    }
    public void checkGameCollidables() {
        GameEnvironment game = this.game;
        Point start= this.center;
        Point temp = this.center;
        Velocity temp1 = this.getVelocity();
        while(PointBetweenboundries(temp)) {
           temp = temp1.applyToPoint(temp);
        }
        Point end = temp;
        Line trajectory = new Line(start, end);
        CollisionInfo info = game.getClosestCollision(trajectory);

        if(info!= null) {
           this.vel = info.collisionObject().hit(info.collisionPoint(),temp1);
        }
    }
    public boolean PointBetweenboundries(Point p) {
        double x= p.getX();
        double y =p.getY();
       if((x > this.xLeftUpperCorner) && (x < this.xLeftUpperCorner + length)) {
           if((y > this.yLeftUpperCorner ) && (y<this.yLeftUpperCorner + height)) {
               return true;
           }
       }
        return false;
    }
    public void setgame(GameEnvironment game){
        this.game = game;
    }
}