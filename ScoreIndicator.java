package components;
import biuoop.DrawSurface;
/**
 * The type ScoreIndicator.
 * @author Shir sabo
 */
public class ScoreIndicator implements Sprite {
    private Counter scoreOfGame;
    /**
     * Connstructor.
     * @param score score
     */
    public ScoreIndicator(Counter score) {
     this.scoreOfGame = score;
    }
    /**
     * draw the rectangle.
     * @param d the surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.white);
        d.fillRectangle(50, 0, 800, 50);
        d.setColor(java.awt.Color.black);
       d.drawText((800 / 2), (50 / 2), "score:" + (Integer) (this.scoreOfGame.getValue()), 30);
    }
    /**
     * do nothing.
     */
    public void timePassed() {
    }
}
