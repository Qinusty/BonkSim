import java.util.ArrayList;

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
            return instance;
        }
        return instance;
    }
    /* Instance Variables */
    private Room[][] gridWorld;
    private boolean gameOver;

    /* Public Methods*/
    public World() {
        gridWorld = new Room[width][height];
        initRooms();
        gameOver = false;
    }

    /**
     * Performs the basic cycle of the world.
     */
    public void performCycle() {

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
    /* Private Methods*/
    /**
     * Initialises rooms.
     */
    private void initRooms() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                gridWorld[x][y] = new Room(new Position(x,y));
                gridWorld[x][y].setConnectingRooms(calculateConnectingRooms(new Position(x,y)));
            }
        }
    }


    private ArrayList<Position> calculateConnectingRooms(Position pos) {
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

}
