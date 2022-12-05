import boggle.BoggleGame;
import boggle.BoggleMultiplayer;

import java.util.Objects;
import java.util.Scanner;

/**
 * The Main class for the first the game boggle for the CSC207, Fall 2022 group project.
 */
public class Main {
    /**
     * Main method.
     * @param args command line arguments.
     **/
    public static void main(String[] args) {
        Scanner check = new Scanner(System.in);
        System.out.println("Press 'M' for a multiplayer game or 'C' for a game against the computer.");
        String letterCheck = check.nextLine();
        while (!(Objects.equals(letterCheck.toLowerCase(), "m")) && !(Objects.equals(letterCheck.toLowerCase(), "c"))) {
            System.out.println("Press 'M' for a multiplayer game or 'C' for a game against the computer.");
            letterCheck = check.nextLine();
        }
        if (letterCheck.equalsIgnoreCase("c")) {
            BoggleGame b = new BoggleGame();
            b.giveInstructions();
            b.playGame();
        } else {
            BoggleMultiplayer b = new BoggleMultiplayer();
            b.giveInstructions();
            b.playGame();
        }
    }

}