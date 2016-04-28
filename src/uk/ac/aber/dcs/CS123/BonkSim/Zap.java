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

    public Zap() {
        name = "Z" + (productionCount++);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void act() throws CannotActException {
        /* Kill All in room */
        room.killBonks();
        /* Move */
        move();
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public String toString() {
        return getLocation().toString() + " : " + name;
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

