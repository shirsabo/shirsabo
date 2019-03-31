import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
/**
 * @author Shir sabo
 **/
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rect;
    private java.awt.Color color;
    private double minBound;
    private double maxBound;
    private Integer hits;
    public Paddle (Rectangle rectangle1, java.awt.Color color){
        this.rect = rectangle1;
        this.color = color;
    }
    public void moveLeft() {
       double x = this.rect.getUpperLeft().getX();
       double y = this.rect.getUpperLeft().getY();
       Point p = new Point(x - 10, y);
       this.rect = new Rectangle (p, this.rect.getWidth(), this.rect.getHeight());
    }
    public void moveRight() {
        double x = this.rect.getUpperLeft().getX();
        double y = this.rect.getUpperLeft().getY();

        Point p = new Point(x + 10, y);
        this.rect = new Rectangle (p, this.rect.getWidth(), this.rect.getHeight());
    }
    // Sprite
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            if (this.rect.getUpperLeft().getX() - 5 >= this.minBound) {
                moveLeft();
            }
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            if (this.rect.getUpperLeft().getX()+this.rect.getWidth() + 5 <= this.maxBound) {
                moveRight();
            }
        }
    }
    public void setbounds(double min,double max) {
        this.minBound = min;
        this.maxBound = max;
    }

    public void drawOn(DrawSurface d) {
        Point upperLeft = this.rect.getUpperLeft();
        int x = (int)upperLeft.getX();
        int y = (int)upperLeft.getY();
        d.setColor(color);
        d.fillRectangle(x, y, (int)this.rect.getWidth(), (int)this.rect.getHeight());
    }
    // Collidable
    public Rectangle getCollisionRectangle() {
        return  this.rect;
    }
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double collisionX = collisionPoint.getX();
        double colisionY = collisionPoint.getY();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Point p0 = new Point(0, 0);
        Point p1 = new Point(dx, dy);
        Rectangle[] recs = paddleDivission();
        Velocity newVel = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        for (int i = 0; i < recs.length; i++) {
            Rectangle newRec = recs[i];
            double x = (newRec.getVertical1().getGradient()) * collisionX + newRec.getVertical1().getCollisionY();
            if (collisionPoint.getX() == newRec.getVertical1().start().getX()) {
                newVel = newVel.fromAngleAndSpeed(300 + i * 30, p0.distance(p1));
            } else if (collisionPoint.getX() == newRec.getVertical2().start().getX()) {
                newVel = newVel.fromAngleAndSpeed(300 + i * 30, p0.distance(p1));
            }
            if (collisionPoint.getY() == newRec.getHorizonal1().start().getY()) {
                if ((collisionX >= newRec.getUpperLeft().getX()) && (collisionX <= newRec.getUpperLeft().getX() + newRec.getWidth())) {

                    if (i == 2) {
                        newVel = new Velocity(dx,-currentVelocity.getDy());
                    }
                    else {
                        newVel = newVel.fromAngleAndSpeed(300 + i * 30, p0.distance(p1));
                    }
                }
            }
            if (collisionPoint.getY() == newRec.getHorizonal2().start().getY()) {
                if ((collisionX > newRec.getUpperLeft().getX()) && (collisionX < newRec.getUpperLeft().getX() + newRec.getWidth())) {

                    if (i == 2) {
                        newVel = new Velocity(dx,-currentVelocity.getDy());
                    }else {
                        newVel = newVel.fromAngleAndSpeed((300 + i * 30), p0.distance(p1));
                        // in order to bring the ball down
                        newVel = new Velocity(newVel.getDx(), -newVel.getDy());
                    }
                }
            }
        }
        return newVel;
    }
    // Add this paddle to the game.
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
    public Rectangle[] paddleDivission() {
       Rectangle[] divisions = new Rectangle[5];
       double widthOfDivision = (this.rect.getWidth()) / 5;
       double x = this.rect.getUpperLeft().getX();
       double y = this.rect.getUpperLeft().getY();
       double height = this.rect.getHeight();
       double newX;
       Point newUpperLeft;
       for (int i = 0; i < divisions.length; i++) {
           newX = x + i * widthOfDivision;
           newUpperLeft = new Point(newX,y);
           divisions[i] = new Rectangle(newUpperLeft, widthOfDivision, height);
       }
       return divisions;
    }
    public void sensor(biuoop.KeyboardSensor keyboardIn) {
        this.keyboard=keyboardIn;
    }
    public void setHits(Integer hits) {
        this.hits = hits;
    }
    public void newhit() {
        if (this.hits == 0) {
            return;
        }
        this.hits = hits - 1;
    }
}