
// Velocity specifies the change in position on the `x` and the `y` axes.
public class Velocity {
    public double dx;
    public double dy;
    public Velocity(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.cos(angle) ;
        double dy = speed * Math.sin(angle);
        return new Velocity(dx, dy);
    }
    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p){
        double x = p.getX();
        double y = p.getY();
        Point p1 = new Point(x + this.dx, y + this.dy);
        return p1;
    }
    public double getDx() {
        return this.dx;
    }
    public double getDy() {
        return this.dy;
    }
}