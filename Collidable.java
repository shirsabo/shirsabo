package components;
import graphics.Point;
import graphics.Rectangle;
//import java.awt.*;

/**
 * The interface Collidable.
 * @author Shir sabo
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     * @return the collision rectangle
     */
    Rectangle getCollisionRectangle();
    /**
     // Notify the object that we collided with it at collisionPoint with
     // a given velocity.
     // The return is the new velocity expected after the hit (based on
     // the force the object inflicted on us).
     * @param hitter the ball
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @return the velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
    /**
     * Sets hits.
     * @param hits the hits
     */
    void setHits(Integer hits);
    /**
     * New hit,reduces it by one if not zero.
     */
    void newhit();
}
