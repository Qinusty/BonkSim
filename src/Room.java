import java.util.ArrayList;
import java.util.Random;

/**
 * Created by qinusty on 10/03/16.
 */
public class Room {
    /* Instance Variables */
    private Position position;
    private ArrayList<Bonk> maleBonks;
    private ArrayList<Bonk> femaleBonks;
    private ArrayList<Zap> zaps;
    private ArrayList<Position> connectingRooms;
    private Random random;

    /* Public Methods */

    /**
     * Room constructor which takes the position of the room and also calls the default constructor.
     * @param p
     */
    public Room(Position p) {
        this();
        position = p;
    }

    /**
     * Default constructor for Room, instantiates the arraylists and Random.
     */
    public Room() {
        maleBonks = new ArrayList<>();
        femaleBonks = new ArrayList<>();
        zaps = new ArrayList<>();
        random = new Random();
    }

    /**
     * Adds a being to the Room.
     * @param b Being to add.
     */
    public void addBeing(Being b) {
        if (b instanceof Bonk) {
            Bonk bonk = (Bonk)b;
            if (bonk.getGender() == Gender.Male) {
                maleBonks.add(bonk);
            } else {
                femaleBonks.add(bonk);
            }
        } else if (b instanceof Zap) {
            zaps.add((Zap)b);
        }
    }
    /**
     * Removes a being from the Room.
     * @param b Being to add.
     */
    public void moveBeing(Being b) {
        if (b instanceof Bonk) {
            Bonk bonk = (Bonk)b;
            if (bonk.getGender() == Gender.Male) {
                maleBonks.remove(bonk);
            } else {
                femaleBonks.remove(bonk);
            }
        } else if (b instanceof Zap) {
            zaps.remove((Zap)b);
        }

        Position newPos = connectingRooms.get(random.nextInt(connectingRooms.size()));
        World.getInstance().getRoom(newPos).addBeing(b);
        b.setLocation(newPos);
    }

    public void killBonks() {
        maleBonks.clear();
        femaleBonks.clear();
    }

    public ArrayList<Position> getConnectingRooms() {
        return connectingRooms;
    }

    public void setConnectingRooms(ArrayList<Position> connectingRooms) {
        this.connectingRooms = connectingRooms;
    }
    /* Private Methods */

}
