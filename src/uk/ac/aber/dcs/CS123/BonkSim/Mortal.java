package uk.ac.aber.dcs.CS123.BonkSim;

/**
 * Created by qinusty on 10/03/16.
 */
public interface Mortal {
    /**
     * Every motral on GridWorld will have an age.
     * @return returns the age of the mortal.
     */
    int getAge();

    boolean isAlive();
}
