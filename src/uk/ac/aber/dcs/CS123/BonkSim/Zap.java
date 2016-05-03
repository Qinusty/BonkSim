package uk.ac.aber.dcs.CS123.BonkSim;


/**
 * Created by qinusty on 10/03/16.
 */
public class Zap
        extends MovableBeing {

    /* Static Variables */
    private static long productionCount;


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
        /* Prevents the zap from moving a second time if it runs into a room which hasn't yet performed its cycle. */
        super.act();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Zap zap = (Zap) o;

        return name != null ? name.equals(zap.name) : zap.name == null;

    }
}

