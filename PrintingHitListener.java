package components;
/**
 * The type PrintingHitListener.
 *
 * @author Shir sabo
 */
public class PrintingHitListener implements HitListener {
    /**
     * hit event.
     * @param beingHit the block
     * @param hitter the ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block with " + beingHit.getHitPoints() + " points was hit.");
    }
}
