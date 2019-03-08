public class Line {
    private Point start ;
    private Point last;
    private double length;
    private double gradient;
    private double intersectingY;
    // constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.last = end;
        this.length = start.distance(end);
    }
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1),new Point(x2, y2));
        this.gradient = (y2 - y1) / (x2 - x1);
        this.intersectingY = y1- gradient * x1;
    }

    // Return the length of the line
    public double length() {
        return this.length;
    }

    // Returns the middle point of the line
    public Point middle() {
        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.last.getX();
        double y2 = this.last.getY();
        double midX = (x1 + x2) / 2;
        double midY = (y1 + y2) / 2;
        return new Point(midX, midY);
    }

    // Returns the start point of the line
    public Point start() {
        return this.start;
    }

    // Returns the end point of the line
    public Point end() {
        return this.last;
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
        if (intersectionWith(other) == null) {
            return false;
        }
        return true;

    }

    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {
        double intersecX;
        double intersecY;
        if (this.gradient == other.gradient){
            return null;
        }
        intersecX = ((this.intersectingY) - (other.intersectingY)) / ((other.gradient)-(this.gradient));
        intersecY = (gradient * intersecX) + this.intersectingY;
        Point intersec = new Point(intersecX, intersecY);
        Point starting = (this.start);
        Point ending = (this.last);
        Point starting1 = (other.start);
        Point ending2 = (other.last);

        if ((starting.distance(intersec) < this.length) && ((ending).distance(intersec) < this.length)){
            if((starting1.distance(intersec) < other.length) && (ending2.distance(intersec) < other.length)){
                return intersec;

            }
        }
        return null;

    }
    // equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        if ((((this.start == other.start)|| this.start == other.last))&&((this.last == other.start)|| (this.last == other.last))){
            return true;
        }
        return false;
    }
}
