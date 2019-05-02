/**
 * The type Collision info.
 *
 * @author Shir sabo
 */
public class CollisionInfo {
    private Collidable c;
    private Point colissionP;
    /**
     * Instantiates a new Collision info.
     *
     * @param collision the collision
     * @param element   the element
     */
    public CollisionInfo(Point collision, Collidable element) {
        // the point at which the collision occurs.
        this.colissionP = collision;
        this.c = element;
    }
    /**
     * A getter to the collision point.
     *
     * @return the point
     */
    public Point collisionPoint() {
        return this.colissionP;
    }
    /**
     *the collidable object involved in the collision.
     *
     * @return the collidable
     */
    public Collidable collisionObject() {
        return this.c;

    }
}
