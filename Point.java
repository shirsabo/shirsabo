package graphics;
/**
 * The type Point.
 *
 * @author Shir sabo
 */
public class Point {
    private double x;
    private double y;

    /**
     * Constructor.
     *
     * @param x double
     * @param y double
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * distance -- return the distance of this point to the other point.
     *
     * @param other type Point.
     * @return Output : double
     */
    public double distance(Point other) {
        double x1 = other.x;
        double y1 = other.y;
        // calculates the distance
        double dis = Math.sqrt(((this.x - x1) * (this.x - x1)) + ((this.y - y1) * (this.y - y1)));
        return dis;
    }

    /**
     * equals -- return true if the points are equal, false otherwise.
     *
     * @param other type Point.
     * @return Output : double
     */
    public boolean equals(Point other) {
        if ((this.x == other.x) && (this.y == other.y)) {
            return true;
        }
        return false;
    }
    /**
     * Return the x value of this point.
     *
     * @return Output : double
     */
    public double getX() {
        return this.x;
    }

    /**
     * Return the y value of this point.
     *
     * @return Output : double
     */
    public double getY() {
        return this.y;
    }

    /**
     * Is point on line boolean.
     *
     * @param lineIn the line in
     * @return the boolean
     */
    public boolean isPointOnLine(Line lineIn) {
        Point starting = lineIn.start();
        Point ending = lineIn.end();
        double x1 = starting.distance(this);
        double x2 = lineIn.length();
        double x3 = ending.distance(this);
        double x4 = lineIn.length();

        if ((starting.distance(this) <= lineIn.length()) && (ending.distance(this) <= lineIn.length())) {
            return true;
        }
        return false;
    }
}
