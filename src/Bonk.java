/**
 * Created by qinusty on 10/03/16.
 */
public class Bonk implements Being, Mortal {
    /* Static Variables */
    private int productionCount;
    public int getProductionCount() {
        return productionCount;
    }

    /* Instance Variables */
    private int name;
    private Gender gender;
    private int age;
    private int lastReproduced;
    private Position location;

    /* Public Methods */

    /**
     * Constructor which allows you to give the Bonk a name and a gender.
     * @param gender The gender of the Bonk.
     */
    public Bonk(Gender gender) {
        this();
        this.gender = gender;
    }

    /**
     * Default constructor for the Bonk class.
     */
    public Bonk() {
        // Set name to productionCount, then add one to production count.
        name = productionCount++;
        age = 0;
        lastReproduced = 0;
    }

    /**
     * Gets the gender of the Bonk
     * @return Returns the Gender of the Bonk.
     */
    public Gender getGender() {
        return gender;
    }
    @Override
    public int getAge() {
        return age;
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

    }

    @Override
    public Position getLocation() {
        return location;
    }

    @Override
    public void setLocation(Position location) {
        this.location = location;
    }

    /* Private Methods*/
    private void reproduce() {
    }
    private Room getRoom() {
        return World.getInstance().getRoom(location);
    }
}
