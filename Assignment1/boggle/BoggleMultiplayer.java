package boggle;

import java.util.*;

/**
 * The BoggleMultiplayer class that handles the game
 * if 2 players want to play the game against each other.
 */
public class BoggleMultiplayer {


    /**
     * scanner used to interact with the user via console
     */
    public Scanner scanner;
    /**
     * stores game statistics
     */
    private BoggleStats gameStats;

    /**
     * dice used to randomize letter assignments for a small grid
     */
    private final String[] dice_small_grid= //dice specifications, for small and large grids
            {"AAEEGN", "ABBJOO", "ACHOPS", "AFFKPS", "AOOTTW", "CIMOTU", "DEILRX", "DELRVY",
                    "DISTTY", "EEGHNW", "EEINSU", "EHRTVW", "EIOSST", "ELRTTY", "HIMNQU", "HLNNRZ"};
    /**
     * dice used to randomize letter assignments for a big grid
     */
    private final String[] dice_big_grid =
            {"AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM", "AEEGMU", "AEGMNN", "AFIRSY",
                    "BJKQXZ", "CCNSTW", "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DDHNOT", "DHHLOR",
                    "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU", "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"};

    /*
     * BoggleGame constructor
     */
    public BoggleMultiplayer() {
        this.scanner = new Scanner(System.in);
        this.gameStats = new BoggleStats();
    }
}
