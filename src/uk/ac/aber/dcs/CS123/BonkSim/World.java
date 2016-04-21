package uk.ac.aber.dcs.CS123.BonkSim;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by qinusty on 10/03/16.
 */
public class World {
    /* Constants */
    private final int width = 20;
    private final int height = 20;
    /* Singleton */
    private static World instance = null;
    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }
    /* Instance Variables */
    private Room[][] gridWorld;
    private boolean gameOver;
    private Random random;
    private int cycleCount;

    /* Public Methods*/
    public World() {
    }

    public void initialise() {
        gridWorld = new Room[width][height];
        initRooms();
        gameOver = false;
        random = new Random();
        cycleCount = 0;
        populateRooms();
    }

    /**
     * Performs the basic cycle of the world.
     */
    public void performCycle() {
        // gameOver is always true unless a living bonk is found.
        gameOver = true;
        for (Room[] col : gridWorld) {
            for (Room r : col) {
                // Creates a copy of the list for us to iterate through despite
                // modifications during b.act()
                ArrayList<Being> beings = new ArrayList<>(r.getBeings());
                for (Being b : beings) {
                    if (b instanceof Bonk) {
                        if (((Bonk) b).isAlive()) {
                            gameOver = false;
                        }
                    }
                    try {
                        b.act();
                    } catch (CannotActException e) {
                        System.err.println("ERROR: Cannnot act.");
                    }
                }
            }
        }
        
        int bonkCount = getBonkCount();
        System.out.println("Cycle: " + cycleCount + " - BonkCount: " + bonkCount);
        if (bonkCount == 0)
            gameOver = true;
        displayGridWorld();
        cycleCount ++;
    }

    public int getBonkCount() {
        int bonkCounter = 0;
        for (Room[] col : gridWorld) {
            for (Room r : col) {
                for (Being b : r.getBeings()) {
                    if (b instanceof Bonk && ((Bonk) b).isAlive()) {
                        bonkCounter ++;
                    }
                }
            }
        }
        return bonkCounter;
    }


    /**
     * Gets the room at the specific position.
     * @param pos The position of the room to find.
     * @return Returns the room at the position passed as a parameter.
     */
    public Room getRoom(Position pos) {
        return gridWorld[pos.getX()][pos.getY()];
    }

    /**
     * Gets the state of the game as a boolean.
     * @return Returns true if the game is over.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    /**
     * Calculates all possible connecting rooms based on a position and returns an ArrayList of their positions
     * @param pos The position to check for connecting rooms.
     * @return Returns an ArrayList of uk.ac.aber.dcs.CS123.BonkSim.Position which represent the positions of connecting rooms.
     */
    public ArrayList<Position> calculateConnectingRooms(Position pos) {
        ArrayList<Position> ret = new ArrayList<>();

        if (pos.getX() + 1 < width) {
            ret.add(new Position(pos.getX() + 1, pos.getY())); // right
            if (pos.getY() + 1 < height) {
                ret.add(new Position(pos.getX() + 1, pos.getY() + 1)); // bottom right
            }
            if (pos.getY() - 1 >= 0) {
                ret.add(new Position(pos.getX() + 1, pos.getY() - 1)); // top right
            }
        }
        if (pos.getX() - 1 >= 0) {
            ret.add(new Position(pos.getX() - 1, pos.getY())); // left
            if (pos.getY() + 1 < height) {
                ret.add(new Position(pos.getX() - 1, pos.getY() + 1)); // bottom left
            }
            if (pos.getY() - 1 >= 0) {
                ret.add(new Position(pos.getX() - 1, pos.getY() - 1)); // top left
            }
        }
        if (pos.getY() - 1 >= 0) {
            ret.add(new Position(pos.getX(), pos.getY() - 1));
        }
        if (pos.getY() + 1 < height) {
            ret.add(new Position(pos.getX(), pos.getY() + 1));
        }

        return ret;
    }
    /* Private Methods*/
    private void displayGridWorld() {
        for (int x = 0; x < width; x ++) {
            for (int y = 0; y < height; y++) {
                gridWorld[x][y].displayRoom();
            }
        }
    }

    /**
     * Initialises rooms.
     */
    private void initRooms() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                gridWorld[x][y] = new Room(new Position(x,y));
            }
        }
    }

    private void populateRooms() {
        /* Bonks */
        for (int i = 0; i < GameEngine.startingBonks; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Gender g;
            if (random.nextBoolean())
                g = Gender.Male;
            else
                g = Gender.Female;

            gridWorld[x][y].addBeing(new Bonk(g));
        }
         /* Zaps */
        for (int i = 0; i < GameEngine.startingZaps; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            gridWorld[x][y].addBeing(new Zap());
        }
    }

}
