package uk.ac.aber.dcs.CS123.BonkSim;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

/**
 * Created by qinusty on 28/04/16.
 */
public class Settings {
    private static final String FILE_NAME = "settings.properties";
    private static int maxCycles,
            startingBonks,
            startingZaps,
            gridWidth,
            gridHeight,
            delayTime;


    public static int getMaxCycles() {
        return maxCycles;
    }

    public static void setMaxCycles(int maxCycles) {
        Settings.maxCycles = maxCycles;
    }

    public static int getStartingBonks() {
        return startingBonks;
    }

    public static void setStartingBonks(int startingBonks) {
        Settings.startingBonks = startingBonks;
    }

    public static int getStartingZaps() {
        return startingZaps;
    }

    public static void setStartingZaps(int startingZaps) {
        Settings.startingZaps = startingZaps;
    }

    public static int getGridWidth() {
        return gridWidth;
    }

    public static void setGridWidth(int gridWidth) {
        Settings.gridWidth = gridWidth;
    }

    public static int getGridHeight() {
        return gridHeight;
    }

    public static void setGridHeight(int gridHeight) {
        Settings.gridHeight = gridHeight;
    }

    public static int getDelayTime() {
        return delayTime;
    }

    public static void setDelayTime(int delayTime) {
        Settings.delayTime = delayTime;
    }

    public static void modifyAllSettings(int maxCycles, int startingBonks, int startingZaps,
                                         int gridWidth, int gridHeight, int delayTime) {

        setMaxCycles(maxCycles);
        setStartingBonks(startingBonks);
        setStartingZaps(startingZaps);
        setGridWidth(gridWidth);
        setGridHeight(gridHeight);
        setDelayTime(delayTime);
    }
    public static void load() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(FILE_NAME));
        } catch (IOException e) {
            System.err.println("ERROR: SOMETHING HAPPENED (IO loading file, Settings Load)");
        }
        try {
            maxCycles = Integer.parseInt(properties.getProperty("maxCycles"));
            startingBonks = Integer.parseInt(properties.getProperty("startingBonks"));
            startingZaps = Integer.parseInt(properties.getProperty("startingZaps"));
            gridWidth = Integer.parseInt(properties.getProperty("gridWidth"));
            gridHeight = Integer.parseInt(properties.getProperty("gridHeight"));
            delayTime = Integer.parseInt(properties.getProperty("delayTime"));
        } catch (Exception e) {
            System.err.println("ERROR: SOMETHING HAPPENED (Type issue, Settings Load)");
        }

    }

    public static void save() {
        try {
            Properties props = new Properties();
            props.setProperty("maxCycles", "" + maxCycles);
            props.setProperty("startingBonks", "" + startingBonks);
            props.setProperty("startingZaps", "" + startingZaps);
            props.setProperty("gridWidth", "" + gridWidth);
            props.setProperty("gridHeight", "" + gridHeight);
            props.setProperty("delayTime", "" + delayTime);
            File f = new File(FILE_NAME);

            OutputStream out = new FileOutputStream(f);
            props.store(out, "DO NOT EDIT SETTINGS UNLESS YOU KNOW WHAT YOU ARE DOING!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
