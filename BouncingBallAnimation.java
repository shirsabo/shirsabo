
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
public class BouncingBallAnimation {
    public static void main(String[] args) {

        GUI gui = new GUI("title",200,200);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(0, 0, 30, java.awt.Color.BLACK);
        Velocity v = Velocity.fromAngleAndSpeed(90, 2);
        ball.setVelocity(v);
        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }

}
