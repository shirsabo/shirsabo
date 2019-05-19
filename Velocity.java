package components;

import graphics.Point;

/**
 * The type Velocity.
 *
 * @author Shir sabo
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor.
     *
     * @param dx double
     * @param dy double
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Sets velocity according to angle and speed.
     *
     * @param angle double
     * @param speed double
     * @return Output : velocity
     */
    public Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx1 = speed * Math.sin(Math.toRadians(angle));
        double dy1 = -speed * Math.cos(Math.toRadians(angle));
        return new Velocity(dx1, dy1);
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p Point
     * @return Output : Point
     */
    public Point applyToPoint(Point p) {
        double x = p.getX();
        double y = p.getY();
        Point p1 = new Point(x + this.dx, y + this.dy);
        return p1;
    }

    /**
     * Returns the dx.
     *
     * @return Output : double
     */
    public double getDx() {

        return this.dx;
    }

    /**
     * Returns the dy.
     *
     * @return Output : double
     */
    public double getDy() {
        return this.dy;
    }
}