package CS123.MainAssignment;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by qinusty on 10/03/16.
 */
public class Bonk implements Being, Mortal, Movable {
    /* Static Variables */
    private static int productionCount;
    private static Random random = new Random();

    public static int getProductionCount() {
        return productionCount;
    }

    /* Instance Variables */
    private String name;
    private Gender gender;
    private int birthCycle;
    private int lastReproduced;
    private int lastActed;
    private Room room;
    private boolean alive;
    private LinkedList<Bonk> parents;
    private LinkedList<Bonk> children;

    /* Public Methods */

    /**
     * Constructor which allows you to give the CS123.MainAssignment.Bonk a name and a gender.
     *
     * @param gender The CS123.MainAssignment.Gender of the CS123.MainAssignment.Bonk.
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
     * Default constructor for the CS123.MainAssignment.Bonk class.
     */
    public Bonk() {
        // Set name to productionCount, then add one to production count.
        name = "BONK{" + (productionCount++) + "}";
        birthCycle = World.getInstance().getCycleCount();
        lastReproduced = World.getInstance().getCycleCount();
        alive = true;
        parents = new LinkedList<>();
        children = new LinkedList<>();
    }

    /**
     * Gets the gender of the CS123.MainAssignment.Bonk
     *
     * @return Returns the CS123.MainAssignment.Gender of the CS123.MainAssignment.Bonk.
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
        return null;
    }

    @Override
    public void act() throws CannotActException {
        if (isAlive() && World.getInstance().getCycleCount() - lastActed > 0) {
            /* Reproduction */
            if (ableToBreed()) {
                reproduce(room.findMate(this));
            }
            /* Move to random connecting room or stay */
            move();
            lastActed = World.getInstance().getCycleCount();
        }
    }

    /**
     * Has a chance to move the Bonk
     */
    @Override
    public void move() {
        int move = random.nextInt(room.getConnectingRooms().size() + 1); // if 0, don't move
        if (move > 0) {
            //System.out.print(name + ": Moving from " + this.room.getPosition().toString() + " to ");
            // Move to connectingRoom index move-1
            room.moveBeing(this, room.getPosition(), room.getConnectingRooms().get(move - 1));
            //System.out.println(this.room.getPosition().toString());
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
