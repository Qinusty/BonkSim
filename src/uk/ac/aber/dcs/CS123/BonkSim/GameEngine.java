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
 */
public class GameEngine {
    private int cycleMax;
    private Scanner scanner;
    private World world;
    private int delayTime = 1000;

    public GameEngine() {
        scanner = new Scanner(System.in);
    }

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

    public void mainMenu() {
        System.out.print("Enter the number related to your choice:\n" +
                "1: Run simulation\n" +
                "2: Run custom simulation\n" +
                "3: Visualize previous data.\n" +
                "4: Exit.\n");
        int choice = 4;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input for menu scanner.");
            e.printStackTrace();
        } finally {
            scanner.nextLine();
        }

        switch (choice) {
            case 1:
                defaultSimulation();
                break;
            case 2:
                customSimulation();
                break;
            case 3:
                // TODO
                break;
        }
        if (choice < 3) {
            endMenu();
        }
    }

    private void endMenu() {
        System.out.print("Enter the number related to your choice:\n" +
                        "1: See population graph.\n" +
                        "2: Save population data.\n" +
                        "3: Return to main menu. \n" +
                        "4: Exit.\n");
        int choice = 4;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input for menu scanner.");
            e.printStackTrace();
        } finally {
            scanner.nextLine();
        }
        switch (choice) {
            case 1:
                Application.launch(GraphForm.class);
                endMenu();
                break;
            case 2:
                savePopulationData();
                endMenu();
                break;
            case 3:
                mainMenu();
                break;
        }


    }

    private void defaultSimulation() {
        // grab instance from singleton.
        world = World.resetWorld();
        cycleMax = 100;
        world.initialise(cycleMax, 20, 5, 20, 20);
        gameLoop();
    }

    private void customSimulation() {
        int maxCycles, sBonks, sZaps, gWidth, gHeight;
        System.out.println("Enter custom values separated by spaces (Max Cycles, Starting Bonks, Starting Zaps," +
                            " Grid Width, Grid Height, Cycle Delay in ms)");
        maxCycles = scanner.nextInt();
        sBonks = scanner.nextInt();
        sZaps = scanner.nextInt();
        gWidth = scanner.nextInt();
        gHeight = scanner.nextInt();
        delayTime = scanner.nextInt();

        scanner.nextLine();
        world = world.resetWorld();
        cycleMax = maxCycles;
        world.initialise(cycleMax, sBonks, sZaps, gWidth, gHeight);
        gameLoop();
    }

    private String savePopulationData() {

        int[] data = world.getBonkPopulations();
        String filePath = "data/" + getDateTimeString() + ".dat";
        Path file = Paths.get(filePath);
        ArrayList<String> lines = new ArrayList<>();
        lines.add(world.getWidth() + "x" + world.getHeight());
        lines.add(world.getStartingBonks() + ":" + world.getStartingZaps());
        for (int i : data) {
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
