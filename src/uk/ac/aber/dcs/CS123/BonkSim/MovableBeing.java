package uk.ac.aber.dcs.CS123.BonkSim;

import java.util.Random;

/**
 * Developed by qinusty.
 * Version 1.0 - 12/04/16
 * @Author Josh Smith | Jos67@aber.ac.uk
 */
public abstract class MovableBeing implements Being {
    /* Static Variables*/
    protected static Random random = new Random();

    /* Instance Variables */
    protected Room room;
    private int lastActed;

    /**
     * Moves the movable object.
     */
    public void move() {
        int move = random.nextInt(room.getConnectingRooms().size() + 1);
        // if 0, don't move
        if (move > 0) {
            // Move to connectingRoom index move-1
            room.moveBeing(this, room.getPosition(), room.getConnectingRooms().get(move-1));
        }
    }

    @Override
    public void act() throws CannotActException {
        resetLastActed();
    }

    /**
     * Sets the lastActed variable to this cycle.
     */
    public void resetLastActed() {
        this.lastActed = World.getInstance().getCycleCount();
    }

    public int getLastActed() {
        return lastActed;
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
