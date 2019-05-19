package components;
import biuoop.DrawSurface;
import graphics.Rectangle;
import graphics.Point;
import biuoop.KeyboardSensor;

//import java.awt.*;

/**
 * The type Paddle.
 *
 * @author Shir sabo
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rect;
    private java.awt.Color color;
    private double minBound;
    private double maxBound;
    private Integer hitsOnPaddle;
    /**
     * Instantiates a new Paddle.
     *
     * @param rectangle1 the rectangle 1
     * @param color      the color
     */
    public Paddle(Rectangle rectangle1, java.awt.Color color) {
        this.rect = rectangle1;
        this.color = color;
    }
    /**
     * Move left.
     */
    public void moveLeft() {
       double x = this.rect.getUpperLeft().getX();
       double y = this.rect.getUpperLeft().getY();
       Point p = new Point(x - 10, y);
       this.rect = new Rectangle(p, this.rect.getWidth(), this.rect.getHeight());
    }
    /**
     * Move right.
     */
    public void moveRight() {
        double x = this.rect.getUpperLeft().getX();
        double y = this.rect.getUpperLeft().getY();

        Point p = new Point(x + 10, y);
        this.rect = new Rectangle(p, this.rect.getWidth(), this.rect.getHeight());
    }
    // Sprite
    /**
     * moves the paddle according to the input.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            if (this.rect.getUpperLeft().getX() - 5 >= this.minBound) {
                moveLeft();
            }
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            if (this.rect.getUpperLeft().getX() + this.rect.getWidth() + 5 <= this.maxBound) {
                moveRight();
            }
        }
    }
    /**
     * Sets bounds.
     *
     * @param min the min
     * @param max the max
     */
    public void setbounds(double min, double max) {
        this.minBound = min;
        this.maxBound = max;
    }
    /**
     * Draws the paddle.
     * @param d DrawSurface
     */
    public void drawOn(DrawSurface d) {
        Point upperLeft = this.rect.getUpperLeft();
        int x = (int) upperLeft.getX();
        int y = (int) upperLeft.getY();
        d.setColor(color);
        d.fillRectangle(x, y, (int) this.rect.getWidth(), (int) this.rect.getHeight());
    }
    // Collidable
    /**
     * returns the rectangle.
     * @return the rectangle
     */
    public Rectangle getCollisionRectangle() {
        return  this.rect;
    }
    /**
     * Returns the hit.
     * @param collisionPoint DrawSurface
     * @param currentVelocity Velocity
     * @param hitter Ball
     * @return the hit
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
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
            double upperX = newRec.getUpperLeft().getX();
            double x = (newRec.getVertical1().getGradient()) * collisionX + newRec.getVertical1().getCollisionY();
            if (collisionPoint.getX() == newRec.getVertical1().start().getX()) {
                newVel = newVel.fromAngleAndSpeed(300 + i * 30, p0.distance(p1));
            } else if (collisionPoint.getX() == newRec.getVertical2().start().getX()) {
                newVel = newVel.fromAngleAndSpeed(300 + i * 30, p0.distance(p1));
            }
            if (collisionPoint.getY() == newRec.getHorizonal1().start().getY()) {
                if ((collisionX >= newRec.getUpperLeft().getX()) && (collisionX <= upperX + newRec.getWidth())) {
                    if (i == 2) {
                        newVel = new Velocity(dx, -currentVelocity.getDy());
                    } else {
                        newVel = newVel.fromAngleAndSpeed(300 + i * 30, p0.distance(p1));
                    }
                }
            }
            if (collisionPoint.getY() == newRec.getHorizonal2().start().getY()) {
                if ((collisionX > newRec.getUpperLeft().getX()) && (collisionX < upperX + newRec.getWidth())) {

                    if (i == 2) {
                        newVel = new Velocity(dx, -currentVelocity.getDy());
                    } else {
                        newVel = newVel.fromAngleAndSpeed((300 + i * 30), p0.distance(p1));
                        // in order to bring the ball down
                        newVel = new Velocity(newVel.getDx(), -newVel.getDy());
                    }
                }
            }
        }
        return newVel;
    }
    /**
     * Add to game.
     * @param g the g
     */
// Add this paddle to the game.
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
    /**
     * Paddle divission rectangle [ ].
     *
     * @return the rectangle [ ]
     */
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
           newUpperLeft = new Point(newX, y);
           divisions[i] = new Rectangle(newUpperLeft, widthOfDivision, height);
       }
       return divisions;
    }

    /**
     * Sensor.
     *
     * @param keyboardIn the keyboard in
     */
    public void sensor(biuoop.KeyboardSensor keyboardIn) {
        this.keyboard = keyboardIn;
    }
    /**
     *Sets the hit.
     @param hits Integer of hits
     */
    public void setHits(Integer hits) {
        this.hitsOnPaddle = hits;
    }
    /**
     * reduces the hit by one.
     */
    public void newhit() {
        if (this.hitsOnPaddle == 0) {
            return;
        }
        this.hitsOnPaddle = this.hitsOnPaddle - 1;
    }
}