import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;
public class MultipleBouncingBallsAnimation{

    public Ball[] creatBalls(int[] numbers, int n) {
        Ball[] balls = new Ball[n];
        int[] speed = new int[n];
        speed = creatSpeed(n);
        numbers = buubbleSort(numbers,n);
        for (int i = 0; i < n; i++){
            Random rand = new Random();
            int x = rand.nextInt(200 - numbers[i]);
            int y = rand.nextInt(200 - numbers[i]);
            balls[i] = new Ball(x, y, numbers[i], java.awt.Color.RED);
            Velocity v = Velocity.fromAngleAndSpeed(rand.nextInt(360 + 1), speed[i]);
            balls[i].setVelocity(v);

        }
        return balls;
        }
    public static int[] stringsToInts(String[] numbers,int n) {
        int[] arr = new int[numbers.length];
        int i;
        for(i = 0; i < n; i++){
            arr[i]= Integer.parseInt(numbers[i]);
        }
        return arr;
    }
    public static int[] creatSpeed(int n) {
        int[] speed = new int[n];
        for (int i = 0; i < n; i++) {
            speed[i]= n - i;
        }
        return speed;
    }

    public static int[] buubbleSort(int[]arr,int n) {
        int temp;
        for (int i = 1; i < n; i++){
// Last i elements are already in its place
            for (int j = 0; j <= n - 2; j++){
                if(arr[j] > arr[j + 1])
                {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }

            }
        }
        return arr;
    }



    public static void main(String[] args) {
        MultipleBouncingBallsAnimation ball = new MultipleBouncingBallsAnimation();
        Ball[] balls = ball.creatBalls (stringsToInts(args, args.length),args.length);
            GUI gui = new GUI("title",200,200);
            Sleeper sleeper = new Sleeper();
            while (true) {
                DrawSurface d = gui.getDrawSurface();
                for (int i = 0; i < args.length; i++) {
                    balls[i].moveOneStep();
                    balls[i].drawOn(d);

                }
                sleeper.sleepFor(50);  // wait for 50 milliseconds.
                gui.show(d);

            }

    }


}
