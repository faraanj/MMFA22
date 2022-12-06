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
        boggleGame = new BoggleGame();
        boggleStats = new BoggleStats();
    }

    /**
     * Start new game
     */
    public void startGame() {
        boggleGame.giveInstructions();
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

    public void setGame(int size, String letters){
        grid = new BoggleGrid(size);
        grid.initalizeBoard(letters);
        Dictionary boggleDict = new Dictionary("wordlist.txt");
        allWords = new HashMap<String, ArrayList<Position>>();
        this.boggleGame.getAllWords(allWords, boggleDict, grid);
    }

    public void checkWord(String word){
        if (allWords.containsKey(word.toUpperCase())){
            boggleStats.addWord(word, BoggleStats.Player.Human);
        }
    }

    private void computerMove(Map<String,ArrayList<Position>> all_words){
        Set<String> strList = all_words.keySet();
        Iterator<String> itr = strList.iterator();
        while (itr.hasNext()) {
            String wrd = itr.next();
            if (!this.boggleStats.getPlayerWords().contains(wrd)) {
                this.boggleStats.addWord(wrd, BoggleStats.Player.Computer);
            }
        }
    }

    public void endRound(){
        System.out.println(this.boggleStats.getRound());
        computerMove(allWords);
        this.boggleStats.summarizeRound();
        this.boggleStats.endRound();
    }
    public void endGame(){
        endRound();
        this.boggleStats.summarizeGame();
    }

}




