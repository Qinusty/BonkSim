package CS123.MainAssignment;

import java.util.ArrayList;

/**
 * Created by qinusty on 10/03/16.
 */
public class Room {
    /* Instance Variables */
    private Position position;
    private ArrayList<Being> beings;
    private final ArrayList<Position> connectingRooms;


    /* Public Methods */

    /**
     * CS123.MainAssignment.Room constructor which takes the position of the room and also calls the default constructor.
     * @param p
     */
    public Room(Position p) {
        position = p;
        beings = new ArrayList<>();
        connectingRooms = World.getInstance().calculateConnectingRooms(this.position);
    }

    public ArrayList<Being> getBeings() {
        return beings;
    }

    /**
     * Finds a mate which is compatible with the bonk passed as a parameter and meets the eligibility defined
     * in CS123.MainAssignment.Bonk.ableToBreed()
     * @param bonk The CS123.MainAssignment.Bonk which needs a mate found.
     * @return Returns an eligible Mate or can return null if no eligible mate found.
     */
    public Bonk findMate(Bonk bonk) {
        for (Being being : beings) {
            if (being instanceof Bonk) {
                Bonk potentialMate = (Bonk) being;
                // If different genders.
                if (!potentialMate.getGender().equals(bonk.getGender())) {
                    if (potentialMate.ableToBreed()) {
                        return potentialMate;
                    }
                }
            }
        }
        return null;
    }
    /**
     * Adds a being to the CS123.MainAssignment.Room.
     * @param b CS123.MainAssignment.Being to add.
     */
    public void addBeing(Being b) {
        beings.add(b);
        b.setLocation(position);
    }
    /**
     * Removes a being from the CS123.MainAssignment.Room.
     * @param b CS123.MainAssignment.Being to add.
     */
    public void moveBeing(Being b, Position from, Position to) {
        boolean successfullyRemoved = World.getInstance().getRoom(from).getBeings().remove(b);
        if (successfullyRemoved) {
            World.getInstance().getRoom(to).addBeing(b);
        } else {
            System.err.println("ERROR: Tried to move being from a room which it wasn't in.");
        }

    }

    /**
     * Killswitch, kills all bonks in the room.
     *
     * @return Returns an integer representing the amount of bonks killed during this
     * murderous occasion.
     */
    public int killBonks() {
        int bonksKilled = 0;
        for (Being b : beings) {
            if (b instanceof Bonk) {
                ((Bonk)b).kill();
                bonksKilled ++;
            }
        }
        return bonksKilled;
    }

    public void displayRoom() {
        for (Being b: beings) {
            System.out.println(b.toString());
        }
    }

    /**
     * Gets a list of all connecting rooms to this room.
     * @return Returns the list of connecting rooms.
     */
    public ArrayList<Position> getConnectingRooms() {
        return connectingRooms;
    }
    /* Private Methods */

    public Position getPosition() {
        return position;
    }

}
