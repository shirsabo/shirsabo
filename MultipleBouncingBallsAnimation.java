import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.util.Random;
/**
 * @author Shir sabo
 **/
public class MultipleBouncingBallsAnimation {
    /**
     * Creates balls and sets their velocity .
     * @param numbers double
     * @param n double
     * @return Output: array of balls
     */
    public Ball[] creatBalls(int[] numbers, int n) {
        Ball[] balls = new Ball[n];
        int[] speed = new int[n];
        speed = creatSpeed(n);
        numbers = buubbleSort(numbers, n);
        for (int i = 0; i < n; i++) {
            Random rand = new Random();
            //random atarting position
            int x = rand.nextInt(200 - numbers[i]);
            int y = rand.nextInt(200 - numbers[i]);
            balls[i] = new Ball(x, y, numbers[i], java.awt.Color.RED);
            Velocity v = Velocity.fromAngleAndSpeed(rand.nextInt(360) + 1, speed[i]);
            balls[i].setVelocity(v);
            // Sets boundries to the ball's movemet
            balls[i].setBoundries(0, 0, 200, 200);
        }
        return balls;
        }
    /**
     * Converts strings to ints.
     * @param numbers double
     * @param n double
     * @return Output: array of ints
     */
    public static int[] stringsToInts(String[] numbers, int n) {
        int[] arr = new int[numbers.length];
        int i;
        for (i = 0; i < n; i++) {
            //converts to int
            arr[i] = Integer.parseInt(numbers[i]);
        }
        return arr;
    }
    /**
     * Creates an array of different speeds(descending order).
     * @param n double
     * @return Output: array of ints
     */
    public static int[] creatSpeed(int n) {
        int[] speed = new int[n];
        for (int i = 0; i < n; i++) {
            speed[i] = n - i;
        }
        return speed;
    }
    /**
     * Sorts the array.
     * @param arr double
     * @param n double
     * @return Output: array of ints
     */
    public static int[] buubbleSort(int[] arr, int n) {
        int temp;
        for (int i = 1; i < n; i++) {
            // Last i elements are already in its place
            for (int j = 0; j <= n - 2; j++) {
                if (arr[j] > arr[j + 1]) {
                    //swap
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }
    /**
     * Entry point.
     * @param args double
     */
    public static void main(String[] args) {
        MultipleBouncingBallsAnimation ball = new MultipleBouncingBallsAnimation();
        Ball[] balls = ball.creatBalls(stringsToInts(args, args.length), args.length);
            //opens Java window
            GUI gui = new GUI("Bouncing Balls Animation", 200, 200);
            Sleeper sleeper = new Sleeper();
            while (true) {
                //creates draw surface
                DrawSurface d = gui.getDrawSurface();
                for (int i = 0; i < args.length; i++) {
                    // the ball changes position
                    balls[i].checkGameCollidables();
                    balls[i].drawOn(d);
                }
                sleeper.sleepFor(50);  // wait for 50 milliseconds.
                gui.show(d);
            }
    }
}
