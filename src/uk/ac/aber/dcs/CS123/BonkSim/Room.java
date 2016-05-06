package uk.ac.aber.dcs.CS123.BonkSim;

import java.util.ArrayList;

/**
 * Created by qinusty on 10/03/16.
 * @Author Josh Smith | Jos67@aber.ac.uk
 */
public class Room {
    /* Instance Variables */
    private Position position;
    private ArrayList<Being> beings;
    private final ArrayList<Position> connectingRooms;


    /* Public Methods */

    /**
     * uk.ac.aber.dcs.CS123.BonkSim.Room constructor which takes the position of the room and also calls the default constructor.
     * @param p uk.ac.aber.dcs.CS123.BonkSim.Position.
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
     * Adds a being to the uk.ac.aber.dcs.CS123.BonkSim.Room.
     * @param b uk.ac.aber.dcs.CS123.BonkSim.Being to add.
     */
    public void addBeing(Being b) {
        beings.add(b);
        b.setLocation(position);
    }

    /**
     * Finds a mate which is compatible with the bonk passed as a parameter and meets the eligibility defined
     * in uk.ac.aber.dcs.CS123.BonkSim.Bonk.ableToBreed()
     * @param bonk The uk.ac.aber.dcs.CS123.BonkSim.Bonk which needs a mate found.
     * @return Returns an eligible Mate or can return null if no eligible mate found.
     */
    public Bonk findMate(Bonk bonk) {
        if (beings.size() > 1) {
            for (Being being : beings) {

                if (being instanceof Bonk) {
                    Bonk potentialMate = (Bonk) being;
                    // If different genders.
                    if ((potentialMate.getGender() != (bonk.getGender()))) {
                        if (potentialMate.ableToBreed()) {
                            return potentialMate;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Removes a being from the uk.ac.aber.dcs.CS123.BonkSim.Room.
     * @param b uk.ac.aber.dcs.CS123.BonkSim.Being to add.
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
        ArrayList<Being> newBeings = new ArrayList<>(beings);
        for (Being b : beings) {
            if (b instanceof Bonk) {
                ((Bonk)b).kill();
                newBeings.remove(b);
                bonksKilled ++;
            }
        }
        beings = newBeings;
        return bonksKilled;
    }

    /**
     * Displays the room in a minimalist manner while still showing a decent level of information
     */
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

    public Position getPosition() {
        return position;
    }

}
