package boggle;

public class Achievements {
    /**
     * The longest word that the player has found
     */
    private String longestWord;
    /**
     * The player's highest score
     */
    private int highestScore;

    public enum Player{
        Player1("Player1"),
        Player2("Player2");
        private final String player;
        Player(final String player) {
            this.player = player;
        }
    }

    public Achievements(){
        this.longestWord = "";
        this.highestScore = 0;
    }

    public void recordLongestWord(String longestWord){
        this.longestWord = longestWord;
    }

    public void recordHighestScore(int highestScore){
        this.highestScore = highestScore;
    }
    // todo: add more achievements,
    //  adjust addWord in boggleStats,
    //  implement the Player-based achievements once multiplayer mode is added,
    //  implement GUI Achievement screen once GUI is functional
}
