import java.util.ArrayList;
import java.util.List;
/**
 * The type Line.
 *
 * @author Shir sabo
 */
public class Line {
    private Point start;
    private Point last;
    private double length;
    private double gradient;
    private double intersectingY;
    private int flag;
    /**
     * Constrauctor.
     *
     * @param startIn point
     * @param endIn   point
     */
    public Line(Point startIn, Point endIn) {
        this.start = startIn;
        this.last = endIn;
        this.length = start.distance(endIn);
    }
    /**
     * Constrauctor.
     * @param x1 double
     * @param y1 double
     * @param x2 double
     * @param y2 double
     */
    public Line(double x1, double y1, double x2, double y2) {
        //calls to the other constructor
        this(new Point(x1, y1), new Point(x2, y2));
        if (x1 == x2) {
            //if the line is vertical
            this.gradient = -17989977;
          this.flag = -1;
          return;
        } else {
        this.flag = 0;
        }
        //calculates the gradient
        this.gradient = (y2 - y1) / (x2 - x1);
        this.intersectingY = y1 - gradient * x1;
    }
    /**
     * Returns the length of the line.
     *
     * @return Output : double
     */
    public double getGradient() {
        return this.gradient;
    }

    /**
     * Gets collision y.
     *
     * @return the collision y
     */
    public double getCollisionY() {
        return this.intersectingY;
    }

    /**
     * Length double.
     *
     * @return the double
     */
    public double length() {
        return this.length;
    }

    /**
     * Returns the middle point of the line.
     *
     * @return Output : Point
     */
    public Point middle() {
        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.last.getX();
        double y2 = this.last.getY();
        double midX = (x1 + x2) / 2;
        double midY = (y1 + y2) / 2;
        return new Point(midX, midY);
    }
    /**
     * Returns the start point of the line.
     *
     * @return Output : Point
     */
    public Point start() {
        return this.start;
    }
    /**
     * Returns the end point of the line.
     *
     * @return Output : Point
     */
    public Point end() {
        return this.last;
    }

    /**
     * Returns wether two lines intersec.
     *
     * @param other line
     * @return Output : boolean
     */
    public boolean isIntersecting(Line other) {
        if (intersectionWith(other) == null) {
            return false;
        }
        return true;
    }
    /**
     * Returns the intersection point if the lines intersect and null otherwise.
     * @param other line
     * @return Output : point or null
     */
    public Point intersectionWith(Line other) {
        double intersecX;
        double intersecY;
        if (((this.flag == -1) && (other.flag == -1)) || ((this.gradient == other.gradient))) {
            if ((start.equals(other.last)) || (start.equals(other.start))) {
                 return start;
            }
            if ((last.equals(other.start)) || (last.equals(other.last))) {
                return last;
              }
            return null;
        }
        double thisX = this.start.getX();
        if ((this.flag == -1) || (other.flag == -1)) {
            if ((this.flag == -1) && (other.flag == 0)) {
                if ((other.start.getX() < this.start.getX()) && (other.last.getX() < this.start.getX())) {
                    return null;
                }
                if ((other.start.getX() > this.start.getX()) && (other.last.getX() > this.start.getX())) {
                    return null;
                }
                Point p1 = new Point(thisX, other.gradient * thisX + other.intersectingY);
                if (p1.isPointOnLine(this)) {
                return p1;
                }
                return null;
            }
            if ((this.flag == 0) && (other.flag == -1)) {
                if ((this.start.getX() < other.start.getX()) && (this.last.getX() < other.start.getX())) {
                    return null;
                }
                if ((this.start.getX() > other.start.getX()) && (this.last.getX() > other.start.getX())) {
                    return null;
                }
                Point p2 = new Point(other.start.getX(), this.gradient * other.start.getX() + this.intersectingY);
                if (p2.isPointOnLine(other)) {
                    return p2;
                }
                return null;
            }
        }
        // Isolating x from the equation y1 = m1x +b1 y2 = m2x +b2
        intersecX = ((this.intersectingY) - (other.intersectingY)) / ((other.gradient) - (this.gradient));
        // Isolating x from the equation y1 = m1x +b1 y2 = m2x +b2
        intersecY = (gradient * intersecX) + this.intersectingY;
        // Creats the intersection point
        Point intersec = new Point(intersecX, intersecY);
        Point starting = (this.start);
        Point ending = (this.last);
        Point starting1 = (other.start);
        Point ending2 = (other.last);
        // Checks whether the point is the on both of the lines
        if ((starting.distance(intersec) <= this.length) && ((ending).distance(intersec) <= this.length)) {
            if ((starting1.distance(intersec) <= other.length) && (ending2.distance(intersec) <= other.length)) {
                return intersec;
            }
        }
        return null;
    }
    /**
     * Returns whether the lines are equal.
     *
     * @param other line
     * @return Output : boolean
     */
    public boolean equals(Line other) {
        if ((this.start.equals(other.start) || this.start.equals(other.last))) {
            if ((this.last.equals(other.start)) || (this.last.equals(other.last))) {
                return true;
            }
        }
        return false;
    }
    /**
     * Closest intersection to start of line point.
     * @param rect the rect
     * @return the point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> l1 = new ArrayList<Point>();
        l1.addAll(0, rect.intersectionPoints(this));
        int n = l1.size(), indexOfInter = 0;
        double minDis = 0;
        double[] dis = new double[n];
        if (n == 0) {
            return null;
        }
        for (int i = 0; i < n; i++) {
            dis[i] = (l1.get(i)).distance(start);
        }
        minDis = dis[0];
        for (int i = 0; i < n; i++) {
            if (dis[i] < minDis) {
                minDis = dis[i];
                indexOfInter = i;
            }
        }
        return l1.get(indexOfInter);
    }
}
