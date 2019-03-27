import biuoop.DrawSurface;

public class Block implements Collidable {
    private Rectangle rect;

    public Block(Rectangle rectangle1) {
        this.rect = rectangle1;
    }
    public Rectangle getCollisionRectangle(){
        return this.rect;
    }
    // Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).
    public Velocity hit(Point collisionPoint, Velocity currentVelocity){
        double collisionX = collisionPoint.getX();
        double colisionY = collisionPoint.getY();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Rectangle newRec = this.rect;
        Velocity newVel = new Velocity(currentVelocity.getDx(),currentVelocity.getDy());
        double x =(newRec.getVertical1().getGradient())*collisionX + newRec.getVertical1().getCollisionY();
        if(collisionPoint.getX()== newRec.getVertical1().start().getX()) {
            newVel = new Velocity(-dx, dy);
        } else if(collisionPoint.getX()== newRec.getVertical2().start().getX()) {
            newVel = new Velocity(-dx, dy);
        } else {
            newVel = new Velocity(dx, -dy);
        }
        return newVel;
    }
    public void drawonsurface(DrawSurface surface,java.awt.Color color,Rectangle r){
        Point upperLeft = r.getUpperLeft();
        int x = (int)upperLeft.getX();
        int y = (int)upperLeft.getY();


        surface.setColor(color);
        surface.fillRectangle(x,y,(int)r.getWidth(),(int)r.getHeight());
    }


}
