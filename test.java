import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.*;
public class test {
    public static void main(String[] args) {

        // Opens java window with the wanted size.
        GUI gui = new GUI("Bouncing Ball", 500, 500);
        GameEnvironment game = new GameEnvironment();
        Sleeper sleeper = new Sleeper();
        // Creates a ball
        Ball ball = new Ball(420, 150, 5, Color.RED);
        ball.setgame(game);
        Line l1 = new Line(0,0,500,500);
        Line l2=  new Line(120,120,120,120);
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,0);
        Point newp= l1.intersectionWith(l2);
        if(newp==null){

        }
        Point p3 = new Point(490,0);
        Point p4 = new Point(0,490);
        Point p5 = new Point(345,156);
        Point p6 = new Point(86,286);
        Point p7 = new Point(126,326);
        Point p8 = new Point(156,456);
        Rectangle rec1 = new Rectangle(p1,500,10);
        Rectangle rec2 = new Rectangle(p2,10,500);
        Rectangle rec3 = new Rectangle(p3,10,500);
        Rectangle rec4 = new Rectangle(p4,500,10);
        Rectangle rec5 = new Rectangle(p5,50,120);
        Rectangle rec6 = new Rectangle(p6,50,19);
        Rectangle rec7 = new Rectangle(p7,50,10);
        Rectangle rec8 = new Rectangle(p8,50,18);
        //rec1.RectangleLines(p1,100,100);
        //rec2.RectangleLines(p2,100,100);
        //rec3.RectangleLines(p3,100,100);
        //rec3.RectangleLines(p4,100,100);
        Block block1 = new Block (rec1,Color.DARK_GRAY);
        Block block2 = new Block (rec2,Color.DARK_GRAY);
        Block block3 = new Block (rec3,Color.DARK_GRAY);
        Block block4 = new Block (rec4,Color.DARK_GRAY);
        Block block5 = new Block (rec5,Color.WHITE);
        Block block6 = new Block (rec6,Color.YELLOW);
        Block block7 = new Block (rec7,Color.CYAN);
        Block block8 = new Block (rec8,Color.ORANGE);
        game.addCollidable(block1);
        game.addCollidable(block2);
        game.addCollidable(block3);
        game.addCollidable(block4);
        game.addCollidable(block5);
        game.addCollidable(block6);
        game.addCollidable(block7);
        game.addCollidable(block8);
        // Sets velocity
        ball.setVelocity(10,10);
        while (true) {
            ball.setBoundries(0, 0, 500, 500);
            ball.timePassed();
            // Updates the ball's location
            //creates draw surface
            DrawSurface d = gui.getDrawSurface();
            // draws the ball on the surface
            ball.drawOn(d);
           // d.drawLine(0,0,500,500);
           // d.drawLine(12,120,120,120);
          //  d.setColor(Color.RED);
           //d.drawLine((int)x+10,(int)y+10,(int)x1+10,(int)y1+10);
          //  d.drawLine(0,30,30,30);
            block1.drawonsurface(d, Color.DARK_GRAY,block1.getCollisionRectangle());
            block2.drawonsurface(d, Color.DARK_GRAY,block2.getCollisionRectangle());
            block3.drawonsurface(d, Color.DARK_GRAY,block3.getCollisionRectangle());
            block4.drawonsurface(d, Color.DARK_GRAY,block4.getCollisionRectangle());
            block5.drawonsurface(d, Color.BLUE,block5.getCollisionRectangle());
            block6.drawonsurface(d, Color.GREEN,block6.getCollisionRectangle());
            block7.drawonsurface(d, Color.YELLOW,block7.getCollisionRectangle());
            block8.drawonsurface(d, Color.PINK,block8.getCollisionRectangle());
            // shows the result
            gui.show(d);
            sleeper.sleepFor(20);  // wait for 50 milliseconds.
        }
    }




}

