import biuoop.DrawSurface;
/**
 * @author Shir sabo
 **/
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity vel;
    private  double xLeftUpperCorner;
    private  double yLeftUpperCorner;
    private int length;
    private int height;
    private GameEnvironment game;
    private Line trajectory;
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
    public void setGame(GameEnvironment gameIn) {
        this.game = gameIn;
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
    public void timePassed() {
        checkGameCollidables();
        // Updates the ball's location
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
        checkGameCollidables();
        // Updates the ball's location
    }
    /**
     * Sets the trajectory to the ball
     * @param trajectory1 DrawSurface
     */
    public void setTrajectory(Line trajectory1) {
        this.trajectory = trajectory1;
    }
    /**
     * Checks for  collidables, and sets the new velocity according to the case.
     */
    public void checkGameCollidables() {
        Point start = this.center;
        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = x1 + this.vel.getDx();
        double y2 = y1 + this.vel.getDy();
        Line trajectory1 = new Line(x1, y1, x2, y2);
        int flag1 = 0;
        int flag2 = 0;
        setTrajectory(trajectory1);
        //gets the closest collision possible
        CollisionInfo info = game.getClosestCollision(this.trajectory);
        //if there is collision
        if (info != null) {
            Point p = info.collisionPoint();
            //checks where the collision is  relation to the rectangle
            // left vertical
            if (p.getX() == info.collisionObject().getCollisionRectangle().getVertical1().start().getX()) {
                this.center = new Point(p.getX() - this.radius - 1, p.getY());
                flag1 = 1;
            }
            //right vertical
            if (p.getX() == info.collisionObject().getCollisionRectangle().getVertical2().start().getX()) {
                this.center = new Point(p.getX() + this.radius + 1, p.getY());
                flag2 = 1;
            }
            //upper horizonal
            if (p.getY() == info.collisionObject().getCollisionRectangle().getHorizonal1().start().getY()) {
                this.center = new Point(p.getX(), p.getY() - this.radius - 1);
                if (flag1 == 1) {
                    this.center = new Point(p.getX() - this.radius - 1, p.getY());
                }
            }
            //lower horizonal
            if (p.getY() == info.collisionObject().getCollisionRectangle().getHorizonal2().start().getY()) {
                this.center = new Point(p.getX(), p.getY() + this.radius + 1);
                if (flag2 == 1) {
                    this.center = new Point(p.getX() + this.radius + 1, p.getY());
                }
            }
            // in order to change the ball's velocity
            Collidable c = info.collisionObject();
            this.vel = c.hit(p, this.vel);
            //move the ball ordinary
        } else {
            this.center = this.getVelocity().applyToPoint(this.center);
        }
    }
    /**
     * Checks whether the point is out of boundries.
     * @param p Point
     * @return Output: boolean
     */
    public boolean pointBetweenBoundries(Point p) {
        double x = p.getX();
        double y = p.getY();
       if ((x >= this.xLeftUpperCorner) && (x <= this.xLeftUpperCorner + length)) {
           if ((y >= this.yLeftUpperCorner) && (y <= this.yLeftUpperCorner + height)) {
               return true;
           }
       }
        return false;
    }
    /**
     * Sets the game that the ball is bouncing in.
     * @param game1 game
     */
    public void setgame(GameEnvironment game1) {
        this.game = game1;
    }
    /**
     * Adds a ball to the sprites.
     * @param g game
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}