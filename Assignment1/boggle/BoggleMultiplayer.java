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
     * BoggleMultiplayer constructor
     */
    public BoggleMultiplayer() {
        this.scanner = new Scanner(System.in);
        this.gameStats = new BoggleStats();
        this.gameStats.setGameMultiplayer();
    }

    /*
     * Provide instructions to the players, so they know how to play the game.
     */
    public void giveInstructions()
    {
        System.out.println("The Boggle board contains a grid of letters that are randomly placed.");
        System.out.println("You and your friend are both going to try to find words in this grid by joining the letters.");
        System.out.println("You can form a word by connecting adjoining letters on the grid.");
        System.out.println("Two letters adjoin if they are next to each other horizontally, ");
        System.out.println("vertically, or diagonally. The words you find must be at least 4 letters long, ");
        System.out.println("and you can't use a letter twice in any single word. Your points ");
        System.out.println("will be based on word length: a 4-letter word is worth 1 point, 5-letter");
        System.out.println("words earn 2 points, and so on. After you find as many words as you can,");
        System.out.println("It will be your friend's turn to find words.");
        System.out.println("\nHit return when you're ready...");
    }


    /*
     * Gets information from the player to initialize a new Boggle multiplayer game.
     * It will loop until the players indicates they are done playing.
     */
    public void playGame(){
        int boardSize;
        while(true){
            System.out.println("Enter 1 to play on a big (5x5) grid; 2 to play on a small (4x4) one:");
            String choiceGrid = scanner.nextLine();

            //get grid size preference
            if(choiceGrid == "") break; //end game if user inputs nothing
            while(!choiceGrid.equals("1") && !choiceGrid.equals("2")){
                System.out.println("Please try again.");
                System.out.println("Enter 1 to play on a big (5x5) grid; 2 to play on a small (4x4) one:");
                choiceGrid = scanner.nextLine();
            }

            if(choiceGrid.equals("1")) boardSize = 5;
            else boardSize = 4;

            //get letter choice preference
            System.out.println("Enter 1 to randomly assign letters to the grid; 2 to provide your own.");
            String choiceLetters = scanner.nextLine();

            if(choiceLetters == "") break; //end game if user inputs nothing
            while(!choiceLetters.equals("1") && !choiceLetters.equals("2")){
                System.out.println("Please try again.");
                System.out.println("Enter 1 to randomly assign letters to the grid; 2 to provide your own.");
                choiceLetters = scanner.nextLine();
            }

            if(choiceLetters.equals("1")){
                playRound(boardSize,randomizeLetters(boardSize));
            } else {
                System.out.println("Input a list of " + boardSize*boardSize + " letters:");
                choiceLetters = scanner.nextLine();
                while(!(choiceLetters.length() == boardSize*boardSize)){
                    System.out.println("Sorry, bad input. Please try again.");
                    System.out.println("Input a list of " + boardSize*boardSize + " letters:");
                    choiceLetters = scanner.nextLine();
                }
                playRound(boardSize,choiceLetters.toUpperCase());
            }

            //round is over! So, store the statistics, and end the round.
            this.gameStats.summarizeRound();
            this.gameStats.endRound();

            //Shall we repeat?
            System.out.println("Play again? Type 'Y' or 'N'");
            String choiceRepeat = scanner.nextLine().toUpperCase();

            if(choiceRepeat == "") break; //end game if user inputs nothing
            while(!choiceRepeat.equals("Y") && !choiceRepeat.equals("N")){
                System.out.println("Please try again.");
                System.out.println("Play again? Type 'Y' or 'N'");
                choiceRepeat = scanner.nextLine().toUpperCase();
            }

            if(choiceRepeat == "" || choiceRepeat.equals("N")) break; //end game if user inputs nothing

        }

        //we are done with the game! So, summarize all the play that has transpired and exit.
        this.gameStats.summarizeGame();
        System.out.println("Thanks for playing!");
    }

    /*
     * Play a round of Boggle.
     * This initializes the main objects: the board, the dictionary, the map of all
     * words on the board, and the set of words found by the user. These objects are
     * passed by reference from here to many other functions.
     */
    public void playRound(int size, String letters){
        //step 1. initialize the grid
        BoggleGrid grid = new BoggleGrid(size);
        grid.initalizeBoard(letters);
        //step 2. initialize the dictionary of legal words
        Dictionary boggleDict = new Dictionary("./Assignment1/wordlist.txt"); //you may have to change the path to the wordlist, depending on where you place it.
        //step 3. find all legal words on the board, given the dictionary and grid arrangement.
        Map<String, ArrayList<Position>> allWords = new HashMap<String, ArrayList<Position>>();
        findAllWords(allWords, boggleDict, grid);
        //step 4. allow the players to try to find some words on the grid
        humanMove(grid, allWords, "Player1");
        humanMove(grid, allWords, "Player2");
        wordsNotFound(allWords);
    }

    /*
     * This method should return a String of letters (length 16 or 25 depending on the size of the grid).
     * There will be one letter per grid position, and they will be organized left to right,
     * top to bottom. A strategy to make this string of letters is as follows:
     * -- Assign a one of the dice to each grid position (i.e. dice_big_grid or dice_small_grid)
     * -- "Shuffle" the positions of the dice to randomize the grid positions they are assigned to
     * -- Randomly select one of the letters on the given die at each grid position to determine
     *    the letter at the given position
     *
     * @return String a String of random letters (length 16 or 25 depending on the size of the grid)
     */
    private String randomizeLetters(int size){
        StringBuilder letterString = new StringBuilder();
        ArrayList<Integer> numList = new ArrayList<>();

        if (size == 4) {
            while (numList.size() < size*size) {
                Random randomNum = new Random();
                int num = randomNum.nextInt(size*size);
                numList.add(num);
                int numLetter = randomNum.nextInt(6);
                letterString.append(this.dice_small_grid[num].charAt(numLetter));
            }
        } else if (size == 5) {
            while (numList.size() < size*size) {
                Random randomNum = new Random();
                int num = randomNum.nextInt(size * size);
                numList.add(num);
                int numLetter = randomNum.nextInt(6);
                letterString.append(this.dice_big_grid[num].charAt(numLetter));
            }
        }

        return letterString.toString();
    }


    /*
     * This should be a recursive function that finds all valid words on the boggle board.
     * Every word should be valid (i.e. in the boggleDict) and of length 4 or more.
     * Words that are found should be entered into the allWords HashMap.  This HashMap
     * will be consulted as we play the game.
     *
     * Note that this function will be a recursive function.  You may want to write
     * a wrapper for your recursion. Note that every legal word on the Boggle grid will correspond to
     * a list of grid positions on the board, and that the Position class can be used to represent these
     * positions. The strategy you will likely want to use when you write your recursion is as follows:
     * -- At every Position on the grid:
     * ---- add the Position of that point to a list of stored positions
     * ---- if your list of stored positions is >= 4, add the corresponding word to the allWords Map
     * ---- recursively search for valid, adjacent grid Positions to add to your list of stored positions.
     * ---- Note that a valid Position to add to your list will be one that is either horizontal, diagonal, or
     *      vertically touching the current Position
     * ---- Note also that a valid Position to add to your list will be one that, in conjunction with those
     *      Positions that precede it, form a legal PREFIX to a word in the Dictionary (this is important!)
     * ---- Use the "isPrefix" method in the Dictionary class to help you out here!!
     * ---- Positions that already exist in your list of stored positions will also be invalid.
     * ---- You'll be finished when you have checked EVERY possible list of Positions on the board, to see
     *      if they can be used to form a valid word in the dictionary.
     * ---- Food for thought: If there are N Positions on the grid, how many possible lists of positions
     *      might we need to evaluate?
     *
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     * @param boggleDict A dictionary of legal words
     * @param boggleGrid A boggle grid, with a letter at each position on the grid
     */
    private void findAllWords(Map<String,ArrayList<Position>> allWords, Dictionary boggleDict, BoggleGrid boggleGrid) {
        for (int i = 0; i < boggleGrid.numRows(); i++) {
            Map<String,ArrayList<Position>> wordList = new HashMap<>();
            for (int j= 0; j < boggleGrid.numCols(); j++) {

                Position pos = new Position(i, j);
                ArrayList<Position> posList = new ArrayList<>();
                posList.add(pos);

                wordList.putAll(recurseWords(String.valueOf(boggleGrid.getCharAt(i, j)), posList, boggleDict, boggleGrid));
            }
            allWords.putAll(wordList);
        }
    }

    private Map<String,ArrayList<Position>> recurseWords(String word, ArrayList<Position> posList, Dictionary boggleDict, BoggleGrid boggleGrid) {
        Map<String,ArrayList<Position>> wordList = new HashMap<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int newI = posList.get(posList.size() - 1).getRow(1) + i;
                int newJ = posList.get(posList.size() - 1).getCol(1) + j;
                int size = boggleGrid.numRows();
                if (0 <= newI && newI < size && 0 <= newJ && newJ < size) {
                    Position newPos = new Position(newI, newJ);
                    if (!inList(posList, newPos)) {

                        StringBuilder newWord = new StringBuilder(word + boggleGrid.getCharAt(newI, newJ));
                        String wrd = String.valueOf(newWord);
                        ArrayList<Position> newPosList = new ArrayList<>(posList);
                        newPosList.add(newPos);

                        if (boggleDict.isPrefix(wrd)) {
                            if (wrd.length() >= 4 && boggleDict.containsWord(wrd) && !wordList.containsKey(wrd)) {
                                wordList.put(wrd, newPosList);
                            }
                            wordList.putAll(recurseWords(wrd, newPosList, boggleDict, boggleGrid));
                        }
                    }
                }
            }
        }
        return wordList;
    }

    private boolean inList(ArrayList<Position> posList,  Position newPos) {
        for (int i = 0; i < posList.size(); i++) {
            if (posList.get(i).getRow(1) == newPos.getRow(1)) {
                if (posList.get(i).getCol(1) == newPos.getCol(1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Gets words from the players.  As words are input, check to see that they are valid.
     * If yes, add the word to the player's word list (in boggleStats) and increment
     * the player's score (in boggleStats).
     * End the turn once the user hits return (with no word).
     *
     * @param board The boggle board
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     * @param playerID The ID of the player whose turn it is, i.e. "Player1" or "Player2"
     */
    private void humanMove(BoggleGrid board, Map<String,ArrayList<Position>> allWords, String playerID){
        System.out.println("It's " + playerID + "'s turn to find some words!");
        while(true) {
            //You write code here!
            //step 1. Print the board for the user, so they can scan it for words
            //step 2. Get a input (a word) from the user via the console
            //step 3. Check to see if it is valid (note validity checks should be case-insensitive)
            //step 4. If it's valid, update the player's word list and score (stored in boggleStats)
            //step 5. Repeat step 1 - 4
            //step 6. End when the player hits return (with no word choice).
            System.out.printf(board.toString());
            String word = scanner.nextLine().toLowerCase().strip();
            if (Objects.equals(word, "")){
                break;
            }
            if (allWords.containsKey(word.toUpperCase())) {
                if (Objects.equals(playerID, "Player1")) {
                    this.gameStats.addWord(word.toUpperCase(), BoggleStats.Player.Player1);
                } else {
                    this.gameStats.addWord(word.toUpperCase(), BoggleStats.Player.Player2);
                }
            }
        }
    }

    /*
     * Finds the words that both Player1 and Player2 have not been able to find
     * in the round
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     */
    private void wordsNotFound(Map<String,ArrayList<Position>> all_words) {
        Set<String> strList = all_words.keySet();
        Iterator<String> itr = strList.iterator();
        while (itr.hasNext()) {
            String wrd = itr.next();
            if (!this.gameStats.getPlayer1Words().contains(wrd)) {
                if (!this.gameStats.getPlayer2Words().contains(wrd)) {
                    this.gameStats.addWord(wrd, BoggleStats.Player.Computer);
                }
            }
        }
    }

    /*
     * @return BoggleStats The stats of this game are returned for testing purposes
     */
    public BoggleStats getGameStats() {
        return this.gameStats;
    }

}
