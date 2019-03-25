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
        double colissionY = collisionPoint.getY();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Rectangle newRec = this.rect;
        Velocity newVel = new Velocity(currentVelocity.getDx(),currentVelocity.getDy());
        if(colissionY == (newRec.getHorizonal1().getGradient())*collisionX + newRec.getHorizonal1().getCollisionY()){
            if(collisionPoint.isPointOnLine(newRec.getHorizonal1())){
                 newVel = new Velocity(dx,-dy);
                 dy = -dy;
            }
        }
        if(colissionY == (newRec.getHorizonal2().getGradient())*collisionX + newRec.getHorizonal2().getCollisionY()){
            if(collisionPoint.isPointOnLine(newRec.getHorizonal2())){
                newVel = new Velocity(dx,-dy);
                dy = -dy;

            }
        }
        if(colissionY == (newRec.getVertical1().getGradient())*collisionX + newRec.getVertical1().getCollisionY()){
            if(collisionPoint.isPointOnLine(newRec.getVertical1())){
                newVel = new Velocity(-dx,dy);
                dx = -dx;

            }
        }
        if(colissionY == (newRec.getVertical2().getGradient())*collisionX + newRec.getVertical2().getCollisionY()){
            if(collisionPoint.isPointOnLine(newRec.getVertical2())){
                newVel = new Velocity(-dx,dy);
                dx = -dx;

            }
        }
        return newVel;
    }
}
