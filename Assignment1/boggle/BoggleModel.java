package boggle;

import java.io.*;
import java.util.*;

/** Represents a Boggle Model for Boggle.
 */

public class BoggleModel implements Serializable{
    // new

    public static final int WIDTH = 200; //size of the board

    public static final int HEIGHT = 200; //height of the board

    public static final int BUFFERZONE = 20; //space at the top

    public int boardSize;

    public String letters;

    private Map<String, ArrayList<Position>> allWords;

    protected BoggleGrid grid;

    protected BoggleStats boggleStats;

    protected Dictionary dictionary;

    protected BoggleGame boggleGame;

    protected boolean game; //true when game is being played

    /**
     * Constructor for a boggle model
     */
    public BoggleModel(){
        game = false;
        boggleGame = new BoggleGame();
    }

    /**
     * Start new game
     */
    public void startGame() {
        boggleGame.giveInstructions();
    }

    /**
     * Plays a round of the Game
     */
    public void playGame(){
        boggleGame.playGame();
    }

    /**
     * Start a new game
     */
    public void newGame() {
        playGame();
    }

    public int getWidth() {
        return this.WIDTH;
    }

    public int getHeight() {
        return this.HEIGHT;
    }

    public void randomizeLetters() {
        letters = this.boggleGame.getRandomizeLetter(boardSize);
        setGame(boardSize, letters);
    }

    private void setGame(int size, String letters){
        grid = new BoggleGrid(size);
        grid.initalizeBoard(letters);
        Dictionary boggleDict = new Dictionary("wordlist.txt");
        allWords = new HashMap<String, ArrayList<Position>>();
        this.boggleGame.getAllWords(allWords, boggleDict, grid);
    }
}




