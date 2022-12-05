package boggle;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The BoggleStats class for the first Assignment in CSC207, Fall 2022
 * The BoggleStats will contain statsitics related to game play Boggle 
 */
public class BoggleStats {

    /**
     * check which determines whether the game being played is multiplayer or not
     */
    private Boolean gameMultiplayer = false;
    /**
     * set of words player1 finds in a given round
     */
    private Set<String> player1Words = new HashSet<String>();
    /**
     * set of words player2 finds in a given round
     */
    private Set<String> player2Words = new HashSet<String>();
    /**
     * player1's score for the current round
     */
    private int p1Score;
    /**
     * player2's score for the current round
     */
    private int p2Score;
    /**
     * player1's total score across every round
     */
    private int p1ScoreTotal;
    /**
     * player2's total score across every round
     */
    private int p2ScoreTotal;
    /**
     * the average number of words, per round, found by player1
     */
    private double p1AverageWords;
    /**
     * the average number of words, per round, found by player2
     */
    private double p2AverageWords;
    /**
     * set of words the player finds in a given round 
     */  
    private Set<String> playerWords = new HashSet<String>();  
    /**
     * set of words the computer finds in a given round 
     */  
    private Set<String> computerWords = new HashSet<String>();  
    /**
     * the player's score for the current round
     */  
    private int pScore; 
    /**
     * the computer's score for the current round
     */  
    private int cScore; 
    /**
     * the player's total score across every round
     */  
    private int pScoreTotal; 
    /**
     * the computer's total score across every round
     */  
    private int cScoreTotal; 
    /**
     * the average number of words, per round, found by the player
     */  
    private double pAverageWords; 
    /**
     * the average number of words, per round, found by the computer
     */  
    private double cAverageWords; 
    /**
     * the current round being played
     */  
    private int round; 

    /**
     * enumarable types of players (human or computer or player1 or player2)
     */  
    public enum Player {
        Human("Human"),
        Player1("Player1"),
        Player2("Player2"),
        Computer("Computer");
        private final String player;
        Player(final String player) {
            this.player = player;
        }
    }

    /* BoggleStats constructor
     * ----------------------
     * Sets round, totals and averages to 0.
     * Initializes word lists (which are sets) for computer and human players.
     */
    public BoggleStats() {
        this.p1Score = 0;
        this.p2Score = 0;
        this.p1ScoreTotal = 0;
        this.p2ScoreTotal = 0;
        this.p1AverageWords = 0;
        this.p2AverageWords = 0;

        this.pScore = 0;
        this.cScore = 0;
        this.pScoreTotal = 0;
        this.cScoreTotal = 0;
        this.pAverageWords = 0;
        this.cAverageWords = 0;
        this.round = 0;
    }

    /* 
     * Add a word to a given player's word list for the current round.
     * You will also want to increment the player's score, as words are added.
     *
     * @param word     The word to be added to the list
     * @param player  The player to whom the word was awarded
     */
    public void addWord(String word, Player player) {
        if (!gameMultiplayer) {
            if (Objects.equals(player.player, "Human")) {
                if (!this.playerWords.contains(word)) {
                    this.playerWords.add(word);
                    this.pScore += (word.length() - 3);
                }
            } else {
                if (!this.computerWords.contains(word)) {
                    this.computerWords.add(word);
                    this.cScore += (word.length() - 3);
                }
            }
        } else {
            if (Objects.equals(player.player, "Player1")) {
                if (!this.player1Words.contains(word)) {
                    this.player1Words.add(word);
                    this.p1Score += (word.length() - 3);
                }
            } else if (Objects.equals(player.player, "Player2")) {
                if (!this.player2Words.contains(word)) {
                    this.player2Words.add(word);
                    this.p2Score += (word.length() - 3);
                }
            } else {
                if (!this.computerWords.contains(word)) {
                    this.computerWords.add(word);
                }
            }
        }
    }

    /* 
     * End a given round.
     * This will clear out the human and computer word lists, so we can begin again.
     * The function will also update each player's total scores, average scores, and
     * reset the current scores for each player to zero.
     * Finally, increment the current round number by 1.
     */
    public void endRound() {
        if (!gameMultiplayer) {
            this.pAverageWords = (this.pAverageWords * this.round + playerWords.size()) / (this.round + 1);
            this.cAverageWords = (this.cAverageWords * this.round + computerWords.size()) / (this.round + 1);
            this.playerWords.clear();
            this.computerWords.clear();
            this.pScoreTotal += this.pScore;
            this.cScoreTotal += this.cScore;
            this.pScore = 0;
            this.cScore = 0;
        } else {
            this.p1AverageWords = (this.p1AverageWords * this.round + player1Words.size()) / (this.round + 1);
            this.p2AverageWords = (this.p2AverageWords * this.round + player2Words.size()) / (this.round + 1);
            this.player1Words.clear();
            this.player2Words.clear();
            this.computerWords.clear();
            this.p1ScoreTotal += this.p1Score;
            this.p2ScoreTotal += this.p2Score;
            this.p1Score = 0;
            this.p2Score = 0;
        }
        this.round += 1;
    }

    /* 
     * Summarize one round of boggle.  Print out:
     * The words each player found this round.
     * Each number of words each player found this round.
     * Each player's score this round.
     */
    public void summarizeRound() {
        if (!gameMultiplayer) {
            System.out.println("The words found by the human player are: " + this.playerWords);
            System.out.println("The number of words found by the human player are: " + this.playerWords.size());
            System.out.println("Human Player's Score: " + this.pScore);
            System.out.println("The words found by the computer are: " + this.computerWords);
            System.out.println("The number of words found by the computer are: " + this.computerWords.size());
            System.out.println("Computer's Score: " + this.cScore);
        } else {
            System.out.println("The words found by Player1 are: " + this.player1Words);
            System.out.println("The number of words found by Player1 are: " + this.player1Words.size());
            System.out.println("Player1's Score: " + this.p1Score);
            System.out.println("The words found by Player2 are: " + this.player2Words);
            System.out.println("The number of words found by Player2 are: " + this.player2Words.size());
            System.out.println("PLayer2's Score: " + this.p2Score);
            System.out.println("The words left in the matrix to find: " + this.computerWords);
        }
    }

    /* 
     * Summarize the entire boggle game.  Print out:
     * The total number of rounds played.
     * The total score for either player.
     * The average number of words found by each player per round.
     */
    public void summarizeGame() {
        if (!gameMultiplayer) {
            System.out.println("The total number of rounds played: " + this.round);
            System.out.println("The total score for the human player: " + this.pScoreTotal);
            System.out.println("The total score for the computer: " + this.cScoreTotal);
            System.out.println("The average number of words found by the human player per round: " + this.pAverageWords);
            System.out.println("The average number of words found by the computer per round: " + this.cAverageWords);
        } else {
            System.out.println("The total number of rounds played: " + this.round);
            System.out.println("The total score for Player1: " + this.p1ScoreTotal);
            System.out.println("The total score for the Player2: " + this.p2ScoreTotal);
            System.out.println("The average number of words found by Player1 per round: " + this.p1AverageWords);
            System.out.println("The average number of words found by Player2 per round: " + this.p2AverageWords);
        }
    }

    /* 
     * @return Set<String> The player's word list
     */
    public Set<String> getPlayerWords() {
        return this.playerWords;
    }

    /*
     * @return int The number of rounds played
     */
    public int getRound() { return this.round; }

    /*
    * @return int The current player score
    */
    public int getScore() {
        return this.pScore;
    }

    public void setGameMultiplayer() {
        this.gameMultiplayer = true;
    }

    /*
     * @return Set<String> Player1's word list
     */
    public Set<String> getPlayer1Words() {
        return this.player1Words;
    }

    /*
     * @return Set<String> Player2's word list
     */
    public Set<String> getPlayer2Words() {
        return this.player2Words;
    }

}
