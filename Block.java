package components;
import biuoop.DrawSurface;
import graphics.Rectangle;
import graphics.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
/**
 * The type Block.
 *
 * @author Shir sabo
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private java.awt.Color color;
    private Integer hitsOnBlock;
    private List<HitListener> hitListeners;
    /**
     * Add to game.
     *
     * @param g the g
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
    /**
     * Remove from game.
     * @param game game
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }
    /**
     * Not in use yet.
     */
    public void timePassed() {
    }
    /**
     * Constructor.
     * @param rectangle1 the rectangle 1
     * @param color      the color
     */
    public Block(Rectangle rectangle1, java.awt.Color color) {
        this.rect = rectangle1;
        this.color = color;
        this.hitListeners = new ArrayList<HitListener>();
    }
    /**
     * @return the rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }
    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param collisionPoint the rectangle 1
     * @param currentVelocity the color
     * @param hitter the ball
     @return the velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double collisionX = collisionPoint.getX();
        double collisionY = collisionPoint.getY();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Rectangle newRec = this.rect;
        Velocity newVel = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        double x = (newRec.getVertical1().getGradient()) * collisionX + newRec.getVertical1().getCollisionY();
        if (collisionX == newRec.getVertical1().start().getX()) {
            newVel = new Velocity(-dx, dy);
            dx = -dx;
        } else if (collisionX == newRec.getVertical2().start().getX()) {
            newVel = new Velocity(-dx, dy);
            dx = -dx;
        }
        if (collisionY == newRec.getHorizonal1().start().getY()) {
            newVel = new Velocity(dx, -dy);
        }
        if (collisionY == newRec.getHorizonal2().start().getY()) {
            newVel = new Velocity(dx, -dy);
        }
        this.notifyHit(hitter);
        this.newhit();
        return newVel;
    }
    /**
     * which will be called whenever a hit() occurs,
     * and notifiers all of the registered HitListener objects by calling their hitEvent method.
     * @param hitter ball
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    /**
     * Draws on surface.
     * @param surface the surface
     * @param colorIn   the color
     * @param r the r
     */
    public void drawonsurface(DrawSurface surface, java.awt.Color colorIn, Rectangle r) {
        Point upperLeft = r.getUpperLeft();
        //casting to int
        int x = (int) upperLeft.getX();
        int y = (int) upperLeft.getY();
        //changed to the wanted color
        //fills the rectangle
        surface.setColor(colorIn);
        surface.fillRectangle(x, y, (int) r.getWidth(), (int) r.getHeight());
    }
    /**
     * Draws the rectangle and prints the number of hits on the rectangle.
     * @param surface the surface
     */
    public void drawOn(DrawSurface surface) {
        Point upperLeft = this.rect.getUpperLeft();
        int x = (int) upperLeft.getX();
        int y = (int) upperLeft.getY();
        double width = this.rect.getWidth();
        double height = this.rect.getHeight();
        surface.setColor(color);
       surface.fillRectangle(x, y, (int) width, (int) height);
        surface.setColor(Color.black);
        surface.drawRectangle(x, y, (int) width, (int) height);
        surface.setColor(Color.white);
        surface.drawText((int) (x + width / 2), (int) (y + height / 2), hitsToSring(), 10);
    }
    /**
     * Hits to sring string.
     * @return the string
     */
    public String hitsToSring() {
        if (this.hitsOnBlock == 0) {
            return "X";
        }
       return Integer.toString(this.hitsOnBlock);
    }
    /**
     * sets the hits1.
     * @param hits nInteger
     */
    public void setHits(Integer hits)  {
        this.hitsOnBlock = hits;
    }
    /**
     * Reduces the number of hits by 1.
     */
    public void newhit() {
        if (this.hitsOnBlock == 0) {
            return;
        }
        this.hitsOnBlock = this.hitsOnBlock - 1;
    }
    /**
     *  Add hl as a listener to hit events.
     * @param hl HitListener
     */
   public void addHitListener(HitListener hl) {
      this.hitListeners.add(hl);
   }
    /**
     *  Remove hl from the list of listeners to hit events.
     * @param hl HitListener
     */
    public void removeHitListener(HitListener hl) {
       this.hitListeners.remove(hl);
    }
    /**
     *  Remove hl from the list of listeners to hit events.
     * @return Integer
     */
    public Integer getHitPoints() {
        return this.hitsOnBlock;
    }
    /**
     *  Getter for color.
     * @return Integer
     */
    public Color getColor() {
        return this.color;
    }
}
