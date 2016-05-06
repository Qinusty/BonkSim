package uk.ac.aber.dcs.CS123.BonkSim;

import javafx.application.Application;
import uk.ac.aber.dcs.CS123.BonkSim.Forms.GraphForm;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Created by qinusty on 10/03/16.
 * @Author Josh Smith | Jos67@aber.ac.uk
 */
public class GameEngine {
    private int cycleMax;
    private Scanner scanner;
    private World world;
    private int delayTime = 1000;

    public GameEngine() {
        scanner = new Scanner(System.in);
    }

    /**
     * Runs the game loop until
     */
    public void gameLoop() {
        do {
            world.performCycle();
            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException e) {
                System.err.println("ERROR: Can't sleep (Interrupted exception)");
            }
        } while (!world.isGameOver() && world.getCycleCount() < cycleMax);
        world.endGame();
        if (world.isGameOver()) {
            System.out.println("Game over, All bonks are dead!");
            System.out.println("The bonk population survived " + world.getCycleCount() + " game cycles");
        }
        else {
            System.out.println("The bonks survived the turns. At the end there were " + world.getBonkCount() +
            " bonks left living.");
        }
    }

    /**
     * Displays an interactive main menu.
     */
    private void mainMenu() {
        System.out.print("Enter the number related to your choice:\n" +
                "1: Run default simulation\n" +
                "2: Run custom simulation\n" +
                "3: Exit.\n");
        int choice = 3;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input for menu scanner.");
            e.printStackTrace();
        } finally {
            scanner.nextLine();
        }
        // Perform action based on choice.
        switch (choice) {
            case 1:
                defaultSimulation();
                break;
            case 2:
                customSimulation();
                break;
        }
        if (choice == 1 || choice == 2) {
            Application.launch(GraphForm.class);
            String filename = savePopulationData();
            System.out.println("File Saved to: " + filename);
            while (!scanner.hasNextLine()) {
                ;
            }
        }
    }

    private void defaultSimulation() {
        // grab instance from singleton.
        world = World.resetWorld();
        cycleMax = 100;
        // 20 bonks, 5 zaps, 30x11 grid.
        world.initialise(cycleMax, 20, 5, 30, 11);
        gameLoop();
    }

    private void customSimulation() {
        int sBonks, sZaps, gWidth, gHeight;
        Settings.load();
        System.out.println("Would you like to use previous custom settings? (y/n)\n" +
                            "Max Cycles: " + Settings.getMaxCycles() + "\n" +
                            "Starting Bonks: " + Settings.getStartingBonks() + "\n" +
                            "Starting Zaps: " + Settings.getStartingZaps() + "\n" +
                            "Grid Width: " + Settings.getGridWidth() + "\n" +
                            "Grid Height: " + Settings.getGridHeight() + "\n" +
                            "Delay in ms: " + Settings.getDelayTime() + "\n");

        char choice;
        do {
            String input;

            input = scanner.nextLine();
            if (input != null && !input.equals("")) {
                choice = input.trim().toLowerCase().charAt(0);
                if (choice == 'y' || choice == 'n') {
                    break;
                } else {
                    System.out.println("y or n, Don't test me human.");
                }
            } else { System.out.println("y or n, Don't test me human."); }

        } while (true);
        // new settings
        if (choice == 'n') {
            System.out.println("Enter custom values separated by spaces (Max Cycles, Starting Bonks, Starting Zaps," +
                    " Grid Width, Grid Height, Cycle Delay in ms)");
            cycleMax = scanner.nextInt();
            sBonks = scanner.nextInt();
            sZaps = scanner.nextInt();
            gWidth = scanner.nextInt();
            gHeight = scanner.nextInt();
            delayTime = scanner.nextInt();

            Settings.modifyAllSettings(cycleMax, sBonks, sZaps, gWidth, gHeight, delayTime);
            Settings.save();

            scanner.nextLine();
        } else { // load previous settings

            cycleMax = Settings.getMaxCycles();
            sBonks = Settings.getStartingBonks();
            sZaps = Settings.getStartingZaps();
            gWidth = Settings.getGridWidth();
            gHeight = Settings.getGridHeight();
            delayTime = Settings.getDelayTime();
        }
        world = World.resetWorld();
        world.initialise(cycleMax, sBonks, sZaps, gWidth, gHeight);
        gameLoop();
    }

    private String savePopulationData() {
        long[] data = world.getBonkPopulations();
        String filePath = "data/" + getDateTimeString() + ".dat";
        Path file = Paths.get(filePath);
        ArrayList<String> lines = new ArrayList<>();
        lines.add(world.getWidth() + "x" + world.getHeight());
        lines.add(world.getStartingBonks() + ":" + world.getStartingZaps());
        for (long i : data) {
            lines.add("" + i);
        }
        try {
            // Create / check file and or path
            File f = new File(filePath);
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            if (!f.exists())
                f.createNewFile();
            // write data to file.
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.err.println("Error Writing file.\n" + e.getMessage());
        }
        return filePath;
    }

    private String getDateTimeString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

        //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    // static stuff

    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        engine.mainMenu();
    }
}
