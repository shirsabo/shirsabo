import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;
/**
 * @author Shir sabo
 **/
public class MultipleFramesBouncingBallsAnimation {
    /**
     * Creats the balls(pink and cyan).
     * @param balls type array of balls
     * @param numbers type array of ints
     * @param n type int
     * @return balls: array of Balls
     */
    public Ball[] creatBalls(Ball[] balls, int[] numbers, int n) {
        int[] speed = new int[n];
        speed = creatSpeed(n);
        numbers = buubbleSort(numbers, n);
        for (int i = 0; i < n; i++) {
            if (i < n / 2) {
                Random rand = new Random();
                int x = rand.nextInt(450 - numbers[i]) + 50;
                int y = rand.nextInt(450 - numbers[i]) + 50;
                balls[i] = new Ball(x, y, numbers[i], Color.CYAN);
                Velocity v = Velocity.fromAngleAndSpeed(rand.nextInt(90) + 1, speed[i]);
                balls[i].setVelocity(v);
            }  else {

                Random rand = new Random();
                int x = rand.nextInt(150 - numbers[i]) + 450;
                int y = rand.nextInt(150 - numbers[i]) + 450;
                balls[i] = new Ball(x, y, numbers[i], Color.PINK);
                Velocity v = Velocity.fromAngleAndSpeed(rand.nextInt(90) + 1, speed[i]);
                balls[i].setVelocity(v);
            }
        }
        return balls;
    }
    /**
     * Converts array of strings to an array of ints.
     * @param numbers type array of ints
     * @param n type int
     * @return arr: array of ints
     */
    public static int[] stringsToIntegers(String[] numbers, int n) {
        int[] arr = new int[numbers.length];
        int i;
        for (i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(numbers[i]);
        }
        return arr;
    }
    /**
     * creats an array of dec order of speed.
     * @param n type int
     * @return speed: array of ints
     */
    public static int[] creatSpeed(int n) {
        int[] speed = new int[n];
        for (int i = 0; i < n; i++) {
            speed[i] =  n - i;
        }
        return speed;
    }
    /**
     * Sorting an array.
     * @param arr type array of ints
     * @param n type int
     * @return arr: array of ints
     */
    public static int[] buubbleSort(int[]arr, int n) {
        int temp;
        for (int i = 1; i < n; i++) {
             // Last i elements are already in its place
            for (int j = 0; j <=  n - 2; j++) {
                if (arr[j] > arr[j + 1]) {

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
     * Sorting an array.
     * @param args type array of strings
     */
    public static void main(String[] args) {
        MultipleFramesBouncingBallsAnimation  bouncingBalls = new MultipleFramesBouncingBallsAnimation();
        // Converts to ints
        int[]arr = bouncingBalls.stringsToIntegers(args, args.length);
        // CreateS new array of balls
        Ball[] balls = new Ball[args.length];
        // Calls to function that creats balls.
        balls = bouncingBalls .creatBalls(balls, arr, args.length);
        // Opens Jva screen , sleeper object
        GUI gui = new GUI("Multiple frames bouncing balls", 600, 600);
        Sleeper sleeper = new Sleeper();
        while (true) {
            // Creates draw surface
            DrawSurface d = gui.getDrawSurface();
            // Sets the color to gray
            d.setColor(Color.GRAY);
            // Creates rectangle
            d.fillRectangle(50, 50, 450, 450);
            // Changes the color to yellow
            d.setColor(Color.YELLOW);
            d.fillRectangle(450, 450, 150, 150);
            for (int i = 0; i < args.length; i++) {
                if (i < (args.length / 2)) {
                    balls[i].setBoundries(50, 50, 500, 500);
                    balls[i].moveOneStep();
                } else {
                    balls[i].setBoundries(450, 450, 600, 600);
                    balls[i].moveOneStep();
                }

                balls[i].drawOn(d);
            }
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
            gui.show(d);

        }

    }
}
