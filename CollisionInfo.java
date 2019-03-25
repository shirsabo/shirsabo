public class CollisionInfo {
    private Collidable c;
    private Point colissionP;
    // the point at which the collision occurs.
    public CollisionInfo (Point collision, Collidable element) {
        this.colissionP = collision;
        this.c = element;
    }
    public Point collisionPoint() {
        return this.colissionP;
    }
    // the collidable object involved in the collision.
    public Collidable collisionObject(){
        return this.c;

    }
}
