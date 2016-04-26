package uk.ac.aber.dcs.CS123.BonkSim;


/**
 * Created by qinusty on 10/03/16.
 */
public class Zap
        extends MovableBeing {

    /* Static Variables */
    private static int productionCount;

    public static int getProductionCount() { return productionCount; }

    /* Instance Variables */
    private String name;
    private int killCount;

    public Zap() {
        name = "Z" + (productionCount++);
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

    @Override
    public void move() {
        super.move();
    }

    public int getKillCount() {
        return killCount;
    }

    @Override
    public String toString() {
        return getLocation().toString() + " : " + name + " : Kills=" + killCount;
    }

    @Override
    public void setLocation(Position location) {
        super.setLocation(location);
    }

    @Override
    public Position getLocation() {
        return super.getLocation();
    }
}

