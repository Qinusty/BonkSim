package uk.ac.aber.dcs.CS123.BonkSim;

import java.util.Random;

/**
 * Developed by qinusty.
 * Version 1.0 - 12/04/16
 */
public abstract class MovableBeing implements Being {
    /* Static Variables*/
    protected static Random random = new Random();

    /* Instance Variables */
    protected Room room;

    /**
     * Moves the movable object.
     */
    public void move() {
        int move = random.nextInt(room.getConnectingRooms().size() + 1); // if 0, don't move
        if (move > 0) {
            // Move to connectingRoom index move-1
            room.moveBeing(this, room.getPosition(), room.getConnectingRooms().get(move-1));
        }
    }

    @Override
    public Position getLocation() {
        return room.getPosition();
    }

    @Override
    public void setLocation(Position location) {
        this.room = World.getInstance().getRoom(location);
    }
}
