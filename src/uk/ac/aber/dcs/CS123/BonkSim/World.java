package uk.ac.aber.dcs.CS123.BonkSim;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by qinusty on 10/03/16.
 * @Author Josh Smith | Jos67@aber.ac.uk
 */
public class World {

    /* Singleton */
    private static World instance = null;

    /**
     * The primary function for use with the singleton. This either creates a new instance of World for the singleton
     * if the current instance is null and returns it or returns the current instance.
     * @return Returns the singleton instance.
     */
    public static World getInstance() {
        return instance == null? instance = new World(): instance;
    }

    /**
     * Reinitialises the instance of World.
     * @return Returns the newly reinitialised instance object.
     */
    public static World resetWorld() {
        instance = new World();
        return instance;
    }

    /* Instance Variables */
    private Room[][] gridWorld;
    private final boolean gridView = true;

    private boolean gameOver;
    private Random random;
    private int cycleCount;
    private long[] bonkPopulations;
    private int startingBonks;
    private int startingZaps;
    private int width;
    private int height;

    /**
     * Private constructor to prevent instantiation from within other classes.
     */
    private World() {
    }

    /* Public Methods*/


    /**
     * Needs to be run before the performCycle as this initialises key variables.
     * @param maxCycles Maximum number of cycles to be used.
     * @param startBonks Starting bonks to use.
     * @param startZaps Starting zaps to use.
     * @param width Grid Width to use.
     * @param height Grid Height to use.
     */
    public void initialise(int maxCycles, int startBonks, int startZaps, int width, int height) {
        startingBonks = startBonks;
        startingZaps = startZaps;
        this.width = width;
        this.height = height;
        gridWorld = new Room[width][height];
        initRooms();
        gameOver = false;
        random = new Random();
        cycleCount = 0;
        populateRooms();
        bonkPopulations = new long[maxCycles];
    }

    /**
     * Performs the basic cycle of the world. ALWAYS RUN initialise FIRST
     */
    public void performCycle() {
        // gameOver is always true unless a living bonk is found.
        gameOver = true;
        long bonkCount = getBonkCount();
        bonkPopulations[cycleCount] = bonkCount;
        System.out.println("Cycle: " + cycleCount + " - BonkCount: " + bonkCount);
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

        bonkCount = getBonkCount();
        if (bonkCount == 0)
            gameOver = true;

        if (gridView)
            displayGridWorldNicely();
        else
            displayGridWorld();

        cycleCount ++;
    }

    /**
     * Runs the code necessary at the end of a game.
     */
    public void endGame() {
        if (gameOver) {
            // Sets the final cycle.
            bonkPopulations[cycleCount] = getBonkCount();
        }
    }

    /**
     * Gets the amount of bonks within the world
     * @return A long value storing the amount of bonks within the world at the current time.
     */
    public long getBonkCount() {
        long beingCounter = 0;
        for (Room[] col : gridWorld) {
            for (Room r : col) {
                beingCounter += r.getBeings().size();
            }
        }
        return beingCounter - startingZaps;
    }


    /**
     * Gets the room at the specific position.
     * @param pos The position of the room to find.
     * @return Returns the room at the position passed as a parameter.
     */
    public Room getRoom(Position pos) {
        return gridWorld[pos.getX()][pos.getY()];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    /**
     * Gets the state of the game as a boolean.
     * @return Returns true if the game is over.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    public int getStartingBonks() {
        return startingBonks;
    }

    public int getStartingZaps() {
        return startingZaps;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    /**
     * Gets an array of longs which represent the bonk populations per cycle in the simulation.
     * @return
     */
    public long[] getBonkPopulations() {
        return bonkPopulations;
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
    private void displayGridWorldNicely() {
        final int boxWidth = 6;
        final int boxHeight = 3;
        for (int y = 0; y < height; y ++) {
            // Lines between rows
            for (int i = 0; i < width * (boxWidth + 1); i++) {
                System.out.print('-');
            }
            System.out.println();
            // contents - box size,
            int printedBeings[] = new int[width];
            for (int i = 0; i < boxHeight; i++) { // lines per row
                for (int x = 0; x < width; x++) {
                    System.out.print("|");
                    String text = " ";
                    boolean printBeing = false;
                    if (gridWorld[x][y].getBeings().size() > i) {
                        // if bonk check if alive
                        if (gridWorld[x][y].getBeings().get(i) instanceof Bonk) {
                            Bonk b = (Bonk)gridWorld[x][y].getBeings().get(i);
                            if (b.isAlive()) {
                                printBeing = true;
                            }
                        } else {
                            printBeing = true;
                        }
                        // if eligible to print (Alive bonk or Zap)
                        if (printBeing) {
                            if (printedBeings[x] == boxHeight - 1) {
                                text = "+" + (gridWorld[x][y].getBeings().size() - i);
                            } else {
                                text = gridWorld[x][y].getBeings().get(i).getName();
                            }
                            printedBeings[x] ++;
                        }
                    }
                    System.out.print(setLengthString(text, boxWidth));

                }
                System.out.println("|");
            }
        }
        for (int i = 0; i < width * (boxWidth + 1); i++) {
            System.out.print('-');
        }
        System.out.println();
    }

    /**
     * Do stuff here.
     * @param s The string to check against.
     * @param maxLength Maximum length of the string to return.
     * @return Returns the string altered to the length specified.
     */
    private String setLengthString(String s, int maxLength) {
        String retString = s;
        if (s.length() < maxLength) {
            retString = s;
            for (int i = 0; i < maxLength - s.length(); i++) {
                retString += " ";
            }
        } else if (s.length() > maxLength) {
            retString = "";
            for (int i = 0; i < maxLength - 2; i++) {
                retString += s.charAt(i);
            }
            retString += "..";
        }
        return retString;
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

    /**
     * Populates the rooms with  a set number of randomly placed bonks and zap.
     */
    private void populateRooms() {
        /* Bonks */
        for (int i = 0; i < startingBonks; i++) {
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
        for (int i = 0; i < startingZaps; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            gridWorld[x][y].addBeing(new Zap());
        }
    }

}
