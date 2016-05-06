package uk.ac.aber.dcs.CS123.BonkSim;


/**
 * Created by qinusty on 10/03/16.
 * @Author Josh Smith | Jos67@aber.ac.uk
 */
public class Bonk
        extends MovableBeing
        implements Mortal {

    /* Static Variables */
    private static long productionCount;

    /* Instance Variables */
    private String name;
    private Gender gender;
    private int birthCycle;
    private int lastReproduced;

    private boolean alive;

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

    /**
     * Default constructor for the uk.ac.aber.dcs.CS123.BonkSim.Bonk class.
     */
    public Bonk() {
        // Set name to productionCount, then add one to production count.
        name = "B" + (productionCount++);
        birthCycle = World.getInstance().getCycleCount();
        resetLastReproduced();
        alive = true;
    }

    /**
     * Kills the bonk.
     */
    public void kill() {
        this.alive = false;
    }

    /**
     * Checks whether the Bonk meets the criteria for mating.
     * @return Returns true if the bonk is alive, is older than 1 cycle and hasn't reproduced this cycle.
     */
    public boolean ableToBreed() {
        // eligibility criteria
        if (isAlive()) {
            if (getAge() > 0) {
                if (cyclesSinceLastReproduced() != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the gender of the uk.ac.aber.dcs.CS123.BonkSim.Bonk
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

    /**
     * Sets the lastReproduced variable to this cycle.
     */
    public void resetLastReproduced() {
        this.lastReproduced = World.getInstance().getCycleCount();
    }

    public int getLastReproduced() {
        return lastReproduced;
    }



    /* Overridden Methods */
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

        }
        super.act();
    }

    @Override
    public void move() {
        super.move();
    }


    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bonk bonk = (Bonk) o;

        if (productionCount != bonk.productionCount) return false;
        if (getAge() != bonk.getAge()) return false;
        if (lastReproduced != bonk.lastReproduced) return false;
        if (name != null ? !name.equals(bonk.name) : bonk.name != null) return false;
        if (gender != bonk.gender) return false;
        return room != null ? room.equals(bonk.room) : bonk.room == null;

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
            Bonk child = new Bonk(g);
            room.addBeing(child);

            //System.out.println("Successful breeding complete!");
        }
    }

    private int cyclesSinceLastReproduced() {
        return World.getInstance().getCycleCount() - getLastReproduced();
    }
}
