package boggle;

import boggle.BoggleGame;
import boggle.BoggleGrid;
import boggle.BoggleStats;
import boggle.Dictionary;

import java.io.*;
import java.util.*;

/** Represents a Boggle Model for Boggle.
 */

public class BoggleModel implements Serializable{
    // new

    public static final int WIDTH = 200; //size of the board

    public static final int HEIGHT = 200; //height of the board

    public static final int BUFFERZONE = 20; //space at the top

    protected BoggleGrid grid;

    protected BoggleStats boggleStats;

    protected Dictionary dictionary;

    protected boolean game; //true when game is being played

    /**
     * Constructor for a boggle model
     */
    public BoggleModel(){
        game = false;
    }

    /**
     * Start new game
     */
    public void startGame() {
        BoggleGame b = new BoggleGame();
        b.giveInstructions();
        b.playGame();
    }

    /**
     * Start a new game
     */
    public void newGame() {
        startGame();
    }

    public int getWidth() {
        return this.WIDTH;
    }

    public int getHeight() {
        return this.HEIGHT;
    }
}




