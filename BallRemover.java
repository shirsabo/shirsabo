package components;


/**
 * The type BallRemover.
 *
 * @author Shir sabo
 */
public class BallRemover implements HitListener {
    private Game game;
    /**
     * Constructor.
     * @param game the game
     * @param removedBalls the counter
     * */
    public BallRemover(Game game, Counter removedBalls) {
        this.game = game;
    }
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     * @param beingHit Block
     * @param hitter Ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getCollisionRectangle().getUpperLeft().getY() >= 600) {
            hitter.removeFromGame(this.game);
            this.game.setBallscounter();
        }
    }

}
