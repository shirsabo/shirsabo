package components;
import biuoop.DrawSurface;

/**
 * The type LivesIndicator.
 * @author Shir sabo
 */
public class LivesIndicator implements Sprite {
   private Counter lives;
    /**
     * Connstructor.
     * @param gameLife lives
     */
    public LivesIndicator(Counter gameLife) {
       this.lives = gameLife;
    }
    /**
     * when one run ended.
     */
    public void oneRun() {
        this.lives.decrease(1);
    }
    /**
     * getter to lives.
     * @return int lives.
     */
    public int getlives() {
        return this.lives.getValue();
    }
    /**
     * draws life rectangle.
     * @param d DrawSurfac

     */
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.white);
        d.fillRectangle(0, 0, 50, 50);
        d.setColor(java.awt.Color.black);
        d.drawText((50 / 5), (50 / 2), "Lives:" + (Integer) (this.getlives()), 30);

    }
    /**
     * do nothing.
     */
    public void timePassed() {
    }
}
