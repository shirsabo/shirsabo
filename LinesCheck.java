
import biuoop.GUI;
import biuoop.DrawSurface;
import java.util.Random;
import java.awt.Color;
    /**
     * @author Nikol Babai. */
    public class LinesCheck {
        /**
         * building lines and circles in specific points. */
        public void drawRandomCircles() {
            Random rand = new Random(); // create a random-number generator
            //GUI gui = new GUI("Random Lines", 1000, 1000);
            //DrawSurface d = gui.getDrawSurface();
            // creating an array of lines
            Line[] randLines = new Line[2];
            Point start1 = new Point(10, 20);
            Point end1 = new Point(10, 40);
            randLines[0] = new Line(start1 ,end1);
            Point start2 = new Point(50, 20);
            Point end2 = new Point(50, 40);
            randLines[1] = new Line(start2 ,end2);
            GUI gui = new GUI("Random Circles Example", 400, 300);
            DrawSurface l = gui.getDrawSurface();
            l.drawLine((int)start1.getX(),(int)start1.getY(), (int)end1.getX(),(int)end1.getY());
            l.drawLine((int)start2.getX(),(int)start2.getY(), (int)end2.getX(),(int)end2.getY());
            //draw(randLines, gui, l);
            for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < i; j++) {
                    // there is an intersection
                    if (randLines[i].isIntersecting(randLines[j])) {
                        // creating a circle around the middle point of the line
                        l.setColor(Color.RED);
                        Point intersect = randLines[i].intersectionWith(randLines[j]);
                        l.fillCircle((int) intersect.getX(), (int) intersect.getY(), 3);
                    }
                }
            }
            gui.show(l);
            //gui.show(d);
        }
        /**
         * calling the drawRandomCircles in order to create random pictures.
         *
         * @param args - an array we recieve from the user. */
        public static void main(String[] args) {
            LinesCheck example = new LinesCheck();
            example.drawRandomCircles();
        }
    }

