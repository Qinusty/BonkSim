package uk.ac.aber.dcs.CS123.BonkSim;

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
    String getName();
    /**
     * When called the uk.ac.aber.dcs.CS123.BonkSim.Being does its stuff, e.g. move. So this represents behaviour.
     * What this is will vary between different kinds of uk.ac.aber.dcs.CS123.BonkSim.Being
     * @throws CannotActException Thrown if the state of the uk.ac.aber.dcs.CS123.BonkSim.Being prevents it
     * from acting, e.g. it is dead
     */
    void act() throws CannotActException;
    /**
     * Returns the current location of the uk.ac.aber.dcs.CS123.BonkSim.Being (which uk.ac.aber.dcs.CS123.BonkSim.Room it's in)
     * @return Returns a uk.ac.aber.dcs.CS123.BonkSim.Position that encapsulates its coordinates in Grid uk.ac.aber.dcs.CS123.BonkSim.World
     */
    Position getLocation();
    /**
     * Allows the relocation of the uk.ac.aber.dcs.CS123.BonkSim.Being to another uk.ac.aber.dcs.CS123.BonkSim.Room in Grid uk.ac.aber.dcs.CS123.BonkSim.World
     * @param location Location of the Being as a uk.ac.aber.dcs.CS123.BonkSim.Position
     */
    void setLocation(Position location);
}
