import biuoop.GUI;
import biuoop.DrawSurface;
import java.util.Random;
import java.awt.Color;
/**
 * @author Shir sabo
 **/
public class AbstractArtDrawing {
    /**
     *Draws the lines.
     */
    public void drawLines() {
        Random rand = new Random();
        Line[] arrayOfLines = new Line[10];
        GUI gui = new GUI("Abstract art drawing", 500, 500);
        DrawSurface d = gui.getDrawSurface();
        for (int i = 0; i < 10; i++) {
            int x1 = rand.nextInt(400) + 1;
            int y1 = rand.nextInt(300) + 1;
            int x2 = rand.nextInt(400) + 1;
            int y2 = rand.nextInt(300) + 1;
            Line l = new Line((double) x1, (double) y1, (double) x2, (double) y2);
            arrayOfLines[i] = l;
            d.drawLine(x1, y1, x2, y2);
            d.setColor(Color.BLACK);
        }
        drawIntersection(arrayOfLines, d);
        gui.show(d);
    }
    /**
     * Draws the circle.
     * @param arrayOfLines  array of lines
     * @param d DrawSurface
     */
    public void drawIntersection(Line[] arrayOfLines, DrawSurface d) {
        for (int i = 0; i < 10; i++) {
            for (int j = i + 1; j < 10; j++) {
                //checks intersection
                Point intersection = arrayOfLines[i].intersectionWith(arrayOfLines[j]);
                if (intersection == null) {
                    continue;
                }
                double x = intersection.getX();
                double y = intersection.getY();
                //draws intersection if exists
                drawcircleIntersection((int) x, (int) y, (double) 3, d, 0);
            }
            Point mid = arrayOfLines[i].middle();
            double xMid = mid.getX();
            double yMid = mid.getY();
            // draws middle point
            drawcircleIntersection((int) xMid, (int) yMid, (double) 3, d, 1);
        }
    }
    /**
     * Draws the circle.
     * @param x double
     * @param y double
     * @param radius  double
     * @param d DrawSurface
     * @param flag int
     */
    public void drawcircleIntersection(double x, double y, double radius, DrawSurface d, int flag) {
        if (flag == 0) {
            //intersection
            d.setColor(Color.RED);
        } else  {
            //middle
            d.setColor(Color.BLUE);
        }
        //creats a ball
        d.fillCircle((int) x, (int) y, (int) radius);
    }
    /**
     * Calls to drawLines function.
     * @param args array of strings
     */
    public static void main(String[] args) {
        AbstractArtDrawing example = new AbstractArtDrawing();
        example.drawLines();
    }
}