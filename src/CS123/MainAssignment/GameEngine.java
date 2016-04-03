package CS123.MainAssignment;

/**
 * Created by qinusty on 10/03/16.
 */
public class GameEngine {
    public static final int startingBonks = 20;
    public static final int startingZaps = 5;
    private World world;

    public GameEngine() {
        // grab instance from singleton.
        world = World.getInstance();
        world.initialise();
        gameLoop();
    }

    public void gameLoop() {
        do {
            world.performCycle();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println("ERROR: Can't sleep (Interrupted exception)");
            }
        } while (!world.isGameOver());
        System.out.println("Game over, All bonks are dead!");
        System.out.println("The bonks survived " + world.getCycleCount() + " game cycles");
    }

    /* MAIN */
    public static void main(String[] args) {
        GameEngine eng = new GameEngine();
    }
}
