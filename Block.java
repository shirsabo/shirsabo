import biuoop.DrawSurface;
import java.awt.Color;
/**
 * @author Shir sabo
 **/
public class Block implements Collidable, Sprite {
    private Rectangle rect;
    private java.awt.Color color;
    private Integer hits;
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
    public void timePassed() {

    }
    public Block(Rectangle rectangle1, java.awt.Color color) {
        this.rect = rectangle1;
        this.color = color;
    }
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }
    // Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).
    public Velocity hit(Point collisionPoint, Velocity currentVelocity){
        this.newhit();
        double collisionX = collisionPoint.getX();
        double colisionY = collisionPoint.getY();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        Rectangle newRec = this.rect;
        Velocity newVel = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        double x = (newRec.getVertical1().getGradient()) * collisionX + newRec.getVertical1().getCollisionY();
        if(collisionPoint.getX() == newRec.getVertical1().start().getX()) {
            newVel = new Velocity(-dx, dy);
            dx = -dx;
        } else if (collisionPoint.getX() == newRec.getVertical2().start().getX()) {
            newVel = new Velocity(-dx, dy);
            dx = -dx;
        }
        if (collisionPoint.getY() == newRec.getHorizonal1().start().getY()) {
            newVel = new Velocity(dx, -dy);
        }
        if (collisionPoint.getY() == newRec.getHorizonal2().start().getY()) {
            newVel = new Velocity(dx, -dy);
        }
        return newVel;
    }
    public void drawonsurface(DrawSurface surface, java.awt.Color color, Rectangle r) {
        Point upperLeft = r.getUpperLeft();
        int x = (int)upperLeft.getX();
        int y = (int)upperLeft.getY();
        surface.setColor(color);
        surface.fillRectangle(x, y, (int)r.getWidth(), (int)r.getHeight());
    }
    public void drawOn(DrawSurface surface) {
        Point upperLeft = this.rect.getUpperLeft();
        int x = (int) upperLeft.getX();
        int y = (int) upperLeft.getY();
        double width = this.rect.getWidth();
        double height = this.rect.getHeight();
        surface.setColor(color);
        surface.fillRectangle(x, y, (int) this.rect.getWidth(), (int) this.rect.getHeight());
        surface.setColor(Color.white);
        surface.drawText((int) (x + width / 2), (int) (y + height / 2), hitsToSring(), 10);
    }
    public String hitsToSring() {
        if (this.hits == 0) {
            return "X";
        }
       return Integer.toString(this.hits);
    }
    public void setHits(Integer hits) {
        this.hits = hits;
    }
    public void newhit() {
        if (this.hits == 0){
            return;
        }
        this.hits = this.hits - 1;
    }
}
