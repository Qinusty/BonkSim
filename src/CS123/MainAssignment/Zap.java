package CS123.MainAssignment;

import java.util.Random;

/**
 * Created by qinusty on 10/03/16.
 */
public class Zap implements Being {

    /* Static Variables */
    private static int productionCount;
    private static Random random = new Random();
    public static int getProductionCount() { return productionCount; }

    /* Instance Variables */
    private String name;
    private int killCount;
    private Room room;

    public Zap() {
        name = "ZAP{" + (productionCount++) + "}";
        killCount = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void act() throws CannotActException {
        /* Kill All in room */
        killCount += room.killBonks();

        /* Move */
        move();
    }

    public void move() {
        int move = random.nextInt(room.getConnectingRooms().size() + 1); // if 0, don't move
        if (move > 0) {
            // Move to connectingRoom index move-1
            room.moveBeing(this, room.getPosition(), room.getConnectingRooms().get(move-1));
        }
    }

    public int getKillCount() {
        return killCount;
    }

    @Override
    public String toString() {
        return getLocation().toString() + " : " + name + " : Kills=" + killCount;
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
