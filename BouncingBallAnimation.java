import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
/**
 * @author Shir sabo
 **/
public class BouncingBallAnimation {
    /**
     * Calls to the classes that creat a bouncing  ball.
     * @param args array of strings
     */
    public static void main(String[] args) {
        // Opens java window with the wanted size.
        GUI gui = new GUI("Bouncing Ball", 200, 200);
        Sleeper sleeper = new Sleeper();
        // Creates a ball
        Ball ball = new Ball(0, 0, 30, java.awt.Color.BLACK);
        // Sets velocity
        Velocity v = Velocity.fromAngleAndSpeed(90, 2);
        ball.setVelocity(v);
        while (true) {
            ball.setBoundries(0, 0, 200, 200);
            ball.moveOneStep();
            //creates draw surface
            DrawSurface d = gui.getDrawSurface();
            // draws the ball on the surface
            ball.drawOn(d);
            // shows the result
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }
}
