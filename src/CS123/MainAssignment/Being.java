package CS123.MainAssignment;

/**
 * Represents an inhabitant of GridWorld
 * @author cwl
 * @version 1.0
 */
public interface Being {
    /**
     * Every inhabitant on GridWorld must have a name given to them
     * at birth or creation. It is fixed, but can be discovered via this method
     * @return The name
     */
    public String getName();
    /**
     * When called the CS123.MainAssignment.Being does its stuff, e.g. move. So this represents behaviour.
     * What this is will vary between different kinds of CS123.MainAssignment.Being
     * @throws CannotActException Thrown if the state of the CS123.MainAssignment.Being prevents it
     * from acting, e.g. it is dead
     */
    public void act() throws CannotActException;
    /**
     * Returns the current location of the CS123.MainAssignment.Being (which CS123.MainAssignment.Room it's in)
     * @return Returns a CS123.MainAssignment.Position that encapsulates its coordinates in Grid CS123.MainAssignment.World
     */
    public Position getLocation();
    /**
     * Allows the relocation of the CS123.MainAssignment.Being to another CS123.MainAssignment.Room in Grid CS123.MainAssignment.World
     * @param location
     */
    public void setLocation(Position location);
}
