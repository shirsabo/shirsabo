import java.util.ArrayList;
import java.util.List;

/**
 * The type Rectangle.
 *
 * @author Shir sabo
 */
public class Rectangle {
    private Point upperleftPoint;
    private double widthOfRec;
    private double heightOfrec;
    private Line vertical1;
    private Line vertical2;
    private Line horizonal1;
    private Line horizonal2;

    /**
     * Instantiates a new Rectangle.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     */
// Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperleftPoint = upperLeft;
        this.widthOfRec = width;
        this.heightOfrec = height;
        double upperX =  upperLeft.getX();
        double upperY = upperLeft.getY();
        this.horizonal1 = new Line(upperX, upperY, upperX + width, upperY);
        this.horizonal2 = new Line(upperX, upperY + height, upperX + width, upperY + height);
        this.vertical1 = new Line(upperX, upperY, upperX, upperY + height);
        this.vertical2 = new Line(upperX + width, upperY, upperX + width, upperY + height);
    }

    /**
     * Intersection points java . util . list.
     *
     * @param line the line
     * @return the java . util . list
     */
// Return a (possibly empty) List of intersection points
    // with the specified line.
    public java.util.List intersectionPoints(Line line) {
        List<Point> l1 = new ArrayList<Point>();
        if (this.vertical1.intersectionWith(line) != null) {
            l1.add(vertical1.intersectionWith(line));
        }
        if (this.vertical2.intersectionWith(line) != null) {
            l1.add(vertical2.intersectionWith(line));
        }
        if (this.horizonal1.intersectionWith(line) != null) {
            l1.add(horizonal1.intersectionWith(line));
        }
        if (this.horizonal2.intersectionWith(line) != null) {
            l1.add(horizonal2.intersectionWith(line));
        }
        return l1;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
// Return the width and height of the rectangle
    public double getWidth() {
        return this.widthOfRec;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return this.heightOfrec;
    }

    /**
     * Gets upper left.
     *
     * @return the upper left
     */
// Returns the upper-left point of the rectangle.
    public Point getUpperLeft() {
        return this.upperleftPoint;
    }

    /**
     * Gets vertical 1.
     *
     * @return the vertical 1
     */
    public Line getVertical1() {
        return this.vertical1;
    }

    /**
     * Gets vertical 2.
     *
     * @return the vertical 2
     */
    public Line getVertical2() {
        return this.vertical2;
    }

    /**
     * Gets horizonal 1.
     *
     * @return the horizonal 1
     */
    public Line getHorizonal1() {
        return this.horizonal1;
    }

    /**
     * Gets horizonal 2.
     *
     * @return the horizonal 2
     */
    public Line getHorizonal2() {
        return this.horizonal2;
    }

}
