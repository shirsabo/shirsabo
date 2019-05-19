package components;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import graphics.Point;
import graphics.Rectangle;
import java.util.ArrayList;
import java.util.List;
/**
 * The type Game.
 * @author Shir sabo
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Paddle paddle;
    private Counter blockscounter;
    private Counter ballscounter;
    private Counter scorecounter;
    private LivesIndicator livescounter;
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
        GUI gui1 = new GUI("Game", 800, 600);
        this.gui = gui1;
        Block block1 = null;
        for (int i = 0; i < 7; i++) {
            for (int j = i; j < 12; j++) {
                int x = 190 + j * 50;
                int y = 100 + i * 20;
                Point p1 = new Point(x, y);
                Rectangle rec1 = new Rectangle(p1, 50, 20);
                if (i == 0) {
                     block1 = new Block(rec1, java.awt.Color.gray);
                     block1.setHits(2);
                }
                if (i == 1) {
                    block1 = new Block(rec1,  java.awt.Color.red);

                }
                if (i == 2) {
                    block1 = new Block(rec1,  java.awt.Color.yellow);

                }
                if (i == 3) {
                    block1 = new Block(rec1,  java.awt.Color.cyan);

                }
                if (i == 4) {
                    block1 = new Block(rec1,  java.awt.Color.pink);

                }
                if (i == 5) {
                    block1 = new Block(rec1,  java.awt.Color.green);

                }

                if (i != 0) {
                    block1.setHits(1);
                }
                block1.addToGame(this);
            }
        }
      //  PrintingHitListener printHit = new PrintingHitListener();
        List<Collidable> col = new ArrayList<Collidable>(this.environment.getColidables());
        Counter count = new Counter(col.size() - 1); //not including bounds
        this.blockscounter = count;
        BlockRemover bRemover = new BlockRemover(this, count);
        Counter score = new Counter(0);
        this.scorecounter = score;
        ScoreTrackingListener scoreListner = new ScoreTrackingListener(score);
        createScoreIndicator();
        for (Collidable hl : col) {
            if (hl instanceof Block) {
                if (!((Block) hl).getColor().equals(java.awt.Color.DARK_GRAY)) {
                 //   ((Block) hl).addHitListener(printHit);
                    ((Block) hl).addHitListener(bRemover);
                    ((Block) hl).addHitListener(scoreListner);
                }
            }
        }
        createScoreIndicator();
        makeBounds();
    }
    /**
     * Run the game -- start the animation loop.
     */
    public void playOneTurn() {
        //...
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        if (this.livescounter.getlives() == 4) {
            newlive();
        }
        while (true) {
            if ((blockscounter.getValue() == 0) || (ballscounter.getValue() == 0)) {
                if (this.blockscounter.getValue() == 0) {
                    this.scorecounter.increase(100);
                    return;
                }
                removeCollidable(this.paddle);
                removeSprite(this.paddle);
                this.paddle = null;
                newlive();
                this.livescounter.oneRun();
                return;
            }
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
        for (int i = 0; i < 3; i++) {
            if (i == 0 || i == 1) {
                Point p1 = new Point(0, 50);
                if (i == 0) {
                    rec1 = new Rectangle(p1, 800, 10);
                } else {
                    rec1 = new Rectangle(p1, 10, 600);
                }
            }
            if (i == 2) {
                Point p1 = new Point(790, 0);
                rec1 = new Rectangle(p1, 10, 600);
            }
            block1 = new Block(rec1,  java.awt.Color.DARK_GRAY);
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
        d.setColor(java.awt.Color.blue);
        d.fillRectangle(0, 0, 800, 600);
    }
    /**
     *remove the Collidable.
     * @param c the Collidable
     */
    public void removeCollidable(Collidable c) {
        this.environment.deleteCollidable(c);
    }
    /**
     *remove the Sprite.
     * @param s the Collidable
     */
    public void removeSprite(Sprite s) {
        this.sprites.deleteSprite(s);
    }
    /**
     *create ScoreIndicator.
     */
    public void createScoreIndicator() {
        Sprite indicator = new ScoreIndicator(this.scorecounter);
        this.sprites.addSprite(indicator);
    }
    /**
     *create lives Indicator.
     * @param number int
     */
    public void creatLivesIndicator(int number) {
        Counter countingLives = new Counter(number);
        LivesIndicator lives = new LivesIndicator(countingLives);
        this.livescounter = lives;
        this.sprites.addSprite(lives);
    }
    /**
     *gui getter.
     * @return gui gui
     */
    public GUI guiGetter() {
        return this.gui;
    }
    /**
     *gui getter.
     */
    public void run() {
        creatLivesIndicator(4);
        while ((this.livescounter.getlives() != 0) && (this.blockscounter.getValue() != 0)) {
            playOneTurn();
        }
        gui.close();
    }
    /**
     *gui getter.
     */
    public void newlive() {
        biuoop.KeyboardSensor keyboard = this.guiGetter().getKeyboardSensor();
        Point p = new Point(380, 570);
        Rectangle rec = new Rectangle(p, 100, 20);
        Paddle paddle1 = new Paddle(rec,  java.awt.Color.orange);
        paddle1.setHits(0);
        paddle1.setbounds(0, 800);
        this.paddle = paddle1;
        paddle.addToGame(this);
        paddle.sensor(keyboard);
        Block block1 = null;
        Ball ball1 = new Ball(60, 70, 5,  java.awt.Color.RED);
        ball1.setBoundries(10, 10, 790, 590);
        Ball ball2 = new Ball(150, 250, 5,  java.awt.Color.RED);
        ball2.setBoundries(10, 10, 790, 590);
        ball1.setVelocity(6, 5);
        ball2.setVelocity(8, 6);
        ball1.setgame(this.environment);
        ball2.setgame(this.environment);
        ball1.addToGame(this);
        ball2.addToGame(this);
        Point deathPoint = new Point(0, 600);
        Rectangle death = new Rectangle(deathPoint, 900, 20);
        Block deathBlock = new Block(death,  java.awt.Color.black);
        deathBlock.setHits(0);
        deathBlock.addToGame(this);
        Counter countballs = new Counter(2);
        this.ballscounter = countballs;
        BallRemover removeBalls = new BallRemover(this, countballs);
        deathBlock.addHitListener(removeBalls);
    }
    /**
     *decrease ball counter.
     */
    public void setBallscounter() {
        this.ballscounter.decrease(1);
    }
    /**
     * balls counter.
     * @return dfgh
     */
    public Counter ballsgetter() {
      return this.ballscounter;
    }
}
