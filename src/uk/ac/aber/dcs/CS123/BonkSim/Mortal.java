package uk.ac.aber.dcs.CS123.BonkSim;

/**
 * Created by qinusty on 10/03/16.
 * @Author Josh Smith | Jos67@aber.ac.uk
 */
public interface Mortal {
    /**
     * Every mortal on GridWorld will have an age.
     * @return returns the age of the mortal.
     */
    int getAge();

    /**
     * Every mortal on the gridWorld must have a state of alive or dead.
     * @return returns true if the being is alive.
     */
    boolean isAlive();

    /**
     * Kills the being.
     */
    void kill();
}
