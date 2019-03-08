import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.util.Random;
import java.awt.Color;
public class Ball {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity vel;

    public Ball(double x, double y, int r, java.awt.Color color) {
        this(new Point(x, y), r, color);
    }

    // constructor
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
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
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    public static void main(String[] args) {
        GUI gui = new GUI("title", 200, 200);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(0, 0, 30, java.awt.Color.BLACK);
        ball.setVelocity(2, 2);
        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }

    }
}
