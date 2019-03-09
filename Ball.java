import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.util.Random;
import java.awt.Color;
public class Ball {
    private Point center;
    private int radius;
    private java.awt.Color color;
    public Velocity vel;

    public Ball(double x, double y, int r, java.awt.Color color) {
        this(new Point(x, y), r, color);
    }

    // constructor
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.vel= new Velocity(0,0);
    }
    // accessors
    public int getX() {
        return (int) (this.center).getX();
    }

    public int getY() {
        return (int) (this.center).getY();
    }

    public int getSize() {
        return this.radius;
    }

    public java.awt.Color getColor() {
        return this.color;
    }

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
    }

    public void setVelocity(Velocity v) {
        this.vel = v;
    }

    public void setVelocity(double dx, double dy) {
        this.vel = new Velocity(dx, dy);
    }

    public Velocity getVelocity() {
        return this.vel;

    }

    public void moveOneStep() {
        double x1= this.center.getX();
        double y1 =this.center.getY();
        if (y1 + this.radius >= 200) {
            this.vel.dy = -this.vel.dy;
        }
        if (x1 + this.radius >= 200) {
            this.vel.dx= -this.vel.dx;
        }
        if   (y1 - this.radius <= 0){
            if (this.vel.getDy()<0) {
                this.vel.dy = -this.vel.dy;
            }
        }
        if (x1 - this.radius <= 0){
            if (this.vel.getDx()<0) {
                this.vel.dx = -this.vel.dx;
            }
        }
        this.center = this.getVelocity().applyToPoint(this.center);
    }

}