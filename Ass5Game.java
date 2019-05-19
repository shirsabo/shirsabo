import components.Game;
/**
 * The game, entry point.
 * @author Shir sabo
 */
public class Ass5Game {
    /**
     * The entry point of application.
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
