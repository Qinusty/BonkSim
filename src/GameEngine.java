/**
 * Created by qinusty on 10/03/16.
 */
public class GameEngine {
    private final int startingBonks = 20;
    private final int startingZaps = 5;
    private World world;
    private int cycleCount;

    public GameEngine() {
        cycleCount = 0;
        // grab instance from singleton.
        world = World.getInstance();
    }

    public void gameLoop() {
        do {
            world.performCycle();
            
        } while (!world.isGameOver());
    }

    /* MAIN */
    public static void main(String[] args) {
        GameEngine eng = new GameEngine();
    }
}
