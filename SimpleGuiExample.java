import biuoop.GUI; 
import biuoop.DrawSurface;
import java.util.Random;
import java.awt.Color;

public class SimpleGuiExample {

public void drawLines(){
    Random rand = new Random();
    Line[] arrayOfLines = new Line[10];
    GUI gui = new GUI("Random Circles Example", 5000, 5000);
    DrawSurface d = gui.getDrawSurface();
    DrawSurface s = gui.getDrawSurface();
   for (int i = 0; i < 10; ++i) {
       int x1 = rand.nextInt(400)+1;
       int y1 = rand.nextInt(300)+1;
       int x2 = rand.nextInt(400)+1;
       int y2 = rand.nextInt(300)+1;
       Line l = new Line((double)x1,(double)y1,(double)x2,(double)y2);
       //System.out.println(l.length());
       arrayOfLines[i]=l;
       d.drawLine(x1, y1, x2, y2);
       d.setColor(Color.BLACK);
       
    } 
    drawIntersection(arrayOfLines,d); 
    gui.show(d);
}

  public void drawIntersection(Line[] arrayOfLines,DrawSurface d) {
    for (int i = 0; i < 10; i++) {
        for (int j = i+1; j < 10; j++) {
            Point intersection = arrayOfLines[i].intersectionWith(arrayOfLines[j]);
            if (intersection == null){
                continue;
            }
           double x = intersection.getX();
           double y = intersection.getY();
            drawcircleIntersection((int)x,(int)y,(double)3,d);
        }
    }
  }
  public void drawcircleIntersection(double x,double y,double radius,DrawSurface d) {
       d.setColor(Color.RED);
       d.fillCircle((int)x,(int)y,(int)radius);
  }
  public static void main(String[] args) {
     SimpleGuiExample example = new SimpleGuiExample();
     example.drawLines();
  }
}
