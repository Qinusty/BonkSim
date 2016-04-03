package CS123.MainAssignment;

import java.util.Random;

/**
 * Created by qinusty on 10/03/16.
 */
public class Bonk implements Being, Mortal {
    /* Static Variables */
    private static int productionCount;
    private static Random random = new Random();
    public static int getProductionCount() {
        return productionCount;
    }

    /* Instance Variables */
    private String name;
    private Gender gender;
    private int age;
    private int lastReproduced;
    private Room room;
    private boolean alive;

    /* Public Methods */

    /**
     * Constructor which allows you to give the CS123.MainAssignment.Bonk a name and a gender.
     * @param gender The CS123.MainAssignment.Gender of the CS123.MainAssignment.Bonk.
     */
    public Bonk(Gender gender) {
        this();
        this.gender = gender;
    }

    /**
     * Default constructor for the CS123.MainAssignment.Bonk class.
     */
    public Bonk() {
        // Set name to productionCount, then add one to production count.
        name = "BONK{" + (productionCount++) + "}";
        age = 0;
        lastReproduced = 0;
        alive = true;
    }

    /**
     * Gets the gender of the CS123.MainAssignment.Bonk
     * @return Returns the CS123.MainAssignment.Gender of the CS123.MainAssignment.Bonk.
     */
    public Gender getGender() {
        return gender;
    }

    @Override
    public int getAge() {
        return age;
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

    /* Overriden Methods */
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void act() throws CannotActException {
        if (isAlive()) {
            /* Reproduction */
            if (ableToBreed()) {
                reproduce(room.findMate(this));
            }
            /* Move to random connecting room or stay */
            move();
        }
    }

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

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        this.alive = false;
    }

    public boolean ableToBreed() {
        // eligibility criteria
        if (age > 0) {
            if (cyclesSinceLastReproduced() != 0) {
                if (isAlive()) {
                    return true;
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
        if (age != bonk.age) return false;
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
        result = 31 * result + age;
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

    /* Private Methods*/
    private void reproduce(Bonk b) {
        this.resetLastReproduced();
        b.resetLastReproduced();
        /* Get a random gender for the Baby Bonk */
        Gender g;
        if (random.nextBoolean())
            g = Gender.Male;
        else
            g = Gender.Female;

        room.addBeing(new Bonk(g));

    }
    private int cyclesSinceLastReproduced() {
        return World.getInstance().getCycleCount() - lastReproduced;
    }
}
