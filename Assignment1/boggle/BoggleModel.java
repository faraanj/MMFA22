package boggle;

import java.io.*;
import java.util.*;

/** Represents a Boggle Model for Boggle.
 */

public class BoggleModel implements Serializable{

    public int boardSize;

    private String letters;

    private Map<String, ArrayList<Position>> allWords;

    protected BoggleGrid grid;

    protected BoggleStats boggleStats;

    protected Dictionary dictionary;

    protected BoggleGame boggleGame;

    protected LongestWord longestWord;

    private String fact;

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

    public void randomizeLetters() {
        letters = this.boggleGame.getRandomizeLetter(boardSize);
        setGame(boardSize, letters);
    }

    public void setGame(int size, String letters){
        this.letters = letters;
        grid = new BoggleGrid(size);
        grid.initalizeBoard(this.letters);
        Dictionary boggleDict = new Dictionary("./Assignment1/wordlist.txt");
        allWords = new HashMap<String, ArrayList<Position>>();
        longestWord = new LongestWord();
        this.boggleGame.getAllWords(allWords, boggleDict, grid);
    }

    public void checkWord(String word){
        if (allWords.containsKey(word.toUpperCase())){
            boggleStats.addWord(word, BoggleStats.Player.Human);
            Set<String> playerList = boggleStats.getPlayerWords();
            ArrayList<String> playerWord = new ArrayList<String>();
            for (String i : playerList)
                playerWord.add(i);
            fact = longestWord.getDescription(playerWord);
            System.out.println(fact);
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
        System.out.println(fact);
    }

}




