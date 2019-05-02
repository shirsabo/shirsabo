import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
/**
 * The type Game.
 *
 * @author Shir sabo
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Paddle paddle;
    /**
     * Instantiates a new Game.
     */
    public Game() {
        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
    }
    /**
     * Adds a collidable.
     *
     * @param c the c
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add sprite.
     *
     * @param s the s
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initialize.
     */
// Initialize a new game: create the Blocks and Ball (and Paddle)
    // and add them to the game.
    public void initialize() {
        GUI gui1 = new GUI("Game", 500, 500);
        biuoop.KeyboardSensor keyboard = gui1.getKeyboardSensor();
        this.gui = gui1;
        Point p = new Point(250, 470);
        Rectangle rec = new Rectangle(p, 100, 20);
        Paddle paddle1 = new Paddle(rec, Color.orange);
        paddle1.setHits(0);
        paddle1.setbounds(0, 500);
        this.paddle = paddle1;
        paddle.addToGame(this);
        makeBounds();
        Block block1 = null;
        Ball ball1 = new Ball(60, 10, 5, Color.RED);
        ball1.setBoundries(10, 10, 490, 490);
        Ball ball2 = new Ball(150, 250, 5, Color.RED);
        ball2.setBoundries(10, 10, 490, 490);
        ball1.setVelocity(6, 5);
        ball2.setVelocity(8, 6);
        ball1.setgame(this.environment);
        ball2.setgame(this.environment);
        ball1.addToGame(this);
        ball2.addToGame(this);
        //creates the blocks
        for (int i = 0; i < 7; i++) {
            for (int j = i; j < 12; j++) {
                int x = 130 + j * 30;
                int y = 100 + i * 20;
                Point p1 = new Point(x, y);
                Rectangle rec1 = new Rectangle(p1, 30, 20);
                if (i == 0) {
                     block1 = new Block(rec1, Color.gray);
                     block1.setHits(2);
                }
                if (i == 1) {
                    block1 = new Block(rec1, Color.red);

                }
                if (i == 2) {
                    block1 = new Block(rec1, Color.yellow);

                }
                if (i == 3) {
                    block1 = new Block(rec1, Color.cyan);

                }
                if (i == 4) {
                    block1 = new Block(rec1, Color.pink);

                }
                if (i == 5) {
                    block1 = new Block(rec1, Color.green);

                }

                if (i != 0) {
                    block1.setHits(1);
                }
                block1.addToGame(this);
            }
        }
    }
    /**
     // Run the game -- start the animation loop.
     */
    public void run() {
        //...
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();
            background(d);
            this.paddle.sensor(this.gui.getKeyboardSensor());
            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
    /**
     * Make bounds.
     */
    public void makeBounds() {
        Rectangle rec1 = null;
        Block block1 = null;
        for (int i = 0; i <= 3; i++) {
            if (i == 0 || i == 1) {
                Point p1 = new Point(0, 0);
                if (i == 0) {
                    rec1 = new Rectangle(p1, 500, 10);
                } else {
                    rec1 = new Rectangle(p1, 10, 500);
                }
            }
            if (i == 2) {
                Point p1 = new Point(490, 0);
                rec1 = new Rectangle(p1, 10, 490);
            }
            if (i == 3) {
                Point p1 = new Point(0, 490);
                rec1 = new Rectangle(p1, 500, 10);
            }
            block1 = new Block(rec1, Color.DARK_GRAY);
            block1.addToGame(this);
            block1.setHits(1);
        }
    }
    /**
     * Background.
     *
     * @param d the d
     */
    public void background(DrawSurface d) {
        d.setColor(Color.blue);
        d.fillRectangle(0, 0, 500, 500);
    }
}
