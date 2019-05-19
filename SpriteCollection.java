package components;
import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;
/**
 * The type Sprite collection.
 *
 * @author Shir sabo
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Instantiates a new Sprite collection.
     */
//Constructor
    public  SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }
    /**
     * Add sprite.
     *
     * @param s the s
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }
    /**
     * Delete sprite.
     *
     * @param s the s
     */
    public void deleteSprite(Sprite s) {
        this.sprites.remove(s);
    }
    /**
     * Notify all time passed.
     */
// call timePassed() on all sprites.
    public void notifyAllTimePassed() {
        List<Sprite> spriters = new ArrayList<Sprite>(this.sprites);
        for (Sprite s : spriters) {
            s.timePassed();
        }
    }
    /**
     * Draw all on.
     *
     * @param d the d
     */
// call drawOn(d) on all sprites.
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}
