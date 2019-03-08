// Velocity specifies the change in position on the `x` and the `y` axes.
public class Velocity {
    private double dx;
    private double dy;
    public Velocity(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p){
        double x = p.getX();
        double y = p.getY();
        Point p1 = new Point(x + this.dx, y + this.dy);
        return p1;
    }
}
