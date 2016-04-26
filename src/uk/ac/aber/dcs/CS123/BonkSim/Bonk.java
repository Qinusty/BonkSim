package uk.ac.aber.dcs.CS123.BonkSim;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by qinusty on 10/03/16.
 */
public class Bonk
        extends MovableBeing
        implements Mortal {

    /* Static Variables */
    private static int productionCount;

    public static int getProductionCount() {
        return productionCount;
    }

    /* Instance Variables */
    private String name;
    private Gender gender;
    private int birthCycle;
    private int lastReproduced;
    private int lastActed;
    private boolean alive;
    private LinkedList<Bonk> parents;
    private LinkedList<Bonk> children;

    /* Public Methods */

    /**
     * Constructor which allows you to give the uk.ac.aber.dcs.CS123.BonkSim.Bonk a name and a gender.
     *
     * @param gender The uk.ac.aber.dcs.CS123.BonkSim.Gender of the uk.ac.aber.dcs.CS123.BonkSim.Bonk.
     */
    public Bonk(Gender gender) {
        this();
        this.gender = gender;
    }

    public Bonk(Gender gender, Bonk[] parents) {
        this(gender);
        for (Bonk p : parents) {
            this.parents.add(p);
            p.addChild(this);
        }
    }

    /**
     * Default constructor for the uk.ac.aber.dcs.CS123.BonkSim.Bonk class.
     */
    public Bonk() {
        // Set name to productionCount, then add one to production count.
        name = "B" + (productionCount++);
        birthCycle = World.getInstance().getCycleCount();
        resetLastReproduced();
        alive = true;
        parents = new LinkedList<>();
        children = new LinkedList<>();
    }

    /**
     * Gets the gender of the uk.ac.aber.dcs.CS123.BonkSim.Bonk
     *
     * @return Returns the uk.ac.aber.dcs.CS123.BonkSim.Gender of the uk.ac.aber.dcs.CS123.BonkSim.Bonk.
     */
    public Gender getGender() {
        return gender;
    }

    @Override
    public int getAge() {
        return World.getInstance().getCycleCount() - birthCycle;
    }

    public Room getRoom() {
        return room;
    }

    public void resetLastReproduced() {
        this.lastReproduced = World.getInstance().getCycleCount();
    }

    public int getLastReproduced() {
        return lastReproduced;
    }

    public void resetLastActed() {
        this.lastActed = World.getInstance().getCycleCount();
    }

    public int getLastActed() {
        return lastActed;
    }

    /* Overriden Methods */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void act() throws CannotActException {
        if (isAlive() && World.getInstance().getCycleCount() - getLastActed() > 0) {
            /* Reproduction */
            if (ableToBreed()) {
                reproduce(room.findMate(this));
            }
            /* Move to random connecting room or stay */
            move();
            resetLastActed();
        }
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public Position getLocation() {
        return room.getPosition();
    }

    @Override
    public void setLocation(Position location) {
        this.room = World.getInstance().getRoom(location);
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        this.alive = false;
    }

    public boolean ableToBreed() {
        // eligibility criteria
        if (isAlive()) {
            if (getAge() > 0) {
                if (cyclesSinceLastReproduced() != 0) {
                    // Restrict children to 2
                    if (children.size() < 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bonk bonk = (Bonk) o;

        if (productionCount != bonk.productionCount) return false;
        if (getAge() != bonk.getAge()) return false;
        if (lastReproduced != bonk.lastReproduced) return false;
        if (random != null ? !random.equals(bonk.random) : bonk.random != null) return false;
        if (name != null ? !name.equals(bonk.name) : bonk.name != null) return false;
        if (gender != bonk.gender) return false;
        return room != null ? room.equals(bonk.room) : bonk.room == null;

    }

    @Override
    public int hashCode() {
        int result = productionCount;
        result = 31 * result + (random != null ? random.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + getAge();
        result = 31 * result + lastReproduced;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String ret = getLocation().toString() + " : " +
                name + " : " + gender.toString() + " : ";
        if (isAlive())
            ret += "ALIVE";
        else
            ret += "DEAD";

        return ret;
    }

    public void addChild(Bonk b) {
        children.add(b);
    }

    /* Private Methods*/
    private void reproduce(Bonk b) {
        if (b != null) {
            this.resetLastReproduced();
            b.resetLastReproduced();
        /* Get a random gender for the Baby Bonk */
            Gender g;
            if (random.nextBoolean())
                g = Gender.Male;
            else
                g = Gender.Female;
            // adds a new bonk with the current bonk and the passed bonk b as parents with gender g.
            Bonk child = new Bonk(g, new Bonk[]{this, b});
            room.addBeing(child);

            //System.out.println("Successful breeding complete!");
        }
        else {
            return; // No possible mates found
        }
    }

    private int cyclesSinceLastReproduced() {
        return World.getInstance().getCycleCount() - lastReproduced;
    }
}
