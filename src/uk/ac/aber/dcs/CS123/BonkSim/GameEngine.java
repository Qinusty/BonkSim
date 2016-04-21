package uk.ac.aber.dcs.CS123.BonkSim;

/**
 * Created by qinusty on 10/03/16.
 */
public class GameEngine {
    public static final int startingBonks = 20;
    public static final int startingZaps = 20;
    private final int cycleMax = 100;
    private World world;

    public GameEngine() {
        // grab instance from singleton.
        world = World.getInstance();
        world.initialise();
    }

    public void gameLoop() {
        do {
            world.performCycle();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("ERROR: Can't sleep (Interrupted exception)");
            }
        } while (!world.isGameOver() && world.getCycleCount() < cycleMax);
        if (world.isGameOver()) {
            System.out.println("Game over, All bonks are dead!");
            System.out.println("The bonk population survived " + world.getCycleCount() + " game cycles");
        }
        else {
            System.out.println("The bonks survived the turns. At the end there were " + world.getBonkCount() +
            " bonks left living.");
        }
    }

    /* MAIN */
    public static void main(String[] args) {
        GameEngine eng = new GameEngine();
        eng.gameLoop();
    }
}
