import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

import boggle.*;
import boggle.Dictionary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoggleTests {

    //BoggleGame  Test
    @Test
    void findAllWords_small() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleGame game = new BoggleGame();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("./Assignment1/wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        Object r = method.invoke(game, allWords, boggleDict, grid);

        Set<String> expected = new HashSet<>(Arrays.asList("GHOST", "HOST", "THIN"));
        assertEquals(expected, allWords.keySet());
    }

    //Dictionary Test
    @Test
    void containsWord() {
        Dictionary dict = new Dictionary("./Assignment1/wordlist.txt");
        assertTrue(dict.containsWord("ENZYME"));
        assertTrue(dict.isPrefix("pench"));
    }

    //BoggleGrid Test
    @Test
    void setupBoard() {
        BoggleGrid grid = new BoggleGrid(10);
        String letters = "";
        for (int i = 0; i < 10; i++) {
            letters = letters + "0123456789";
        }

        grid.initalizeBoard(letters);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(letters.charAt(i*10+j), grid.getCharAt(i, j));
            }
        }
    }

    //BoggleStats Test
    @Test
    void endRoundTest() {
        BoggleStats stats = new BoggleStats();
        stats.endRound();
        stats.endRound();
        stats.endRound();
        assertEquals(3, stats.getRound());
    }

    @Test
    void toStringTest() {
        BoggleGrid grid = new BoggleGrid(10);
        System.out.printf(grid.toString());
    }

    //BoggleMultiplayerTest1 tests the case where both players don't enter anything
    @Test
    void boggleMultiplayerTest1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleMultiplayer game = new BoggleMultiplayer();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("./Assignment1/wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        Object r = method.invoke(game, allWords, boggleDict, grid);
        BoggleStats gameStats = game.getGameStats();

        Method method1 = game.getClass().getDeclaredMethod("wordsNotFound", Map.class);
        method1.setAccessible(true);
        Object s = method1.invoke(game, allWords);

        Set<String> expected = new HashSet<>(Arrays.asList("GHOST", "HOST", "THIN"));

        assertEquals(expected, gameStats.getComputerWords());
        assertEquals(new HashSet<>(), gameStats.getPlayer1Words());
        assertEquals(new HashSet<>(), gameStats.getPlayer2Words());
    }

    //BoggleMultiplayerTest2 tests the case where Player1 enters words and Player2 doesn't
    // enter anything
    @Test
    void boggleMultiplayerTest2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleMultiplayer game = new BoggleMultiplayer();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("./Assignment1/wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        Object r = method.invoke(game, allWords, boggleDict, grid);
        BoggleStats gameStats = game.getGameStats();
        gameStats.addWord("GHOST", BoggleStats.Player.Player1);

        Method method1 = game.getClass().getDeclaredMethod("wordsNotFound", Map.class);
        method1.setAccessible(true);
        Object s = method1.invoke(game, allWords);

        Set<String> expected = new HashSet<>(Arrays.asList("HOST", "THIN"));

        assertEquals(expected, gameStats.getComputerWords());
        assertEquals(new HashSet<>(Arrays.asList("GHOST")), gameStats.getPlayer1Words());
        assertEquals(new HashSet<>(), gameStats.getPlayer2Words());
    }

    //BoggleMultiplayerTest3 tests the case where Player2 enters words and Player1 doesn't
    // enter anything
    @Test
    void boggleMultiplayerTest3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleMultiplayer game = new BoggleMultiplayer();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("./Assignment1/wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        Object r = method.invoke(game, allWords, boggleDict, grid);
        BoggleStats gameStats = game.getGameStats();
        gameStats.addWord("HOST", BoggleStats.Player.Player2);
        gameStats.addWord("THIN", BoggleStats.Player.Player2);

        Method method1 = game.getClass().getDeclaredMethod("wordsNotFound", Map.class);
        method1.setAccessible(true);
        Object s = method1.invoke(game, allWords);

        Set<String> expected = new HashSet<>(Arrays.asList("HOST", "THIN"));

        assertEquals(new HashSet<>(Arrays.asList("GHOST")), gameStats.getComputerWords());
        assertEquals(new HashSet<>(), gameStats.getPlayer1Words());
        assertEquals(expected, gameStats.getPlayer2Words());
    }

    //BoggleMultiplayerTest4 tests the case where both players enter words with words
    //left for the computer to display
    @Test
    void boggleMultiplayerTest4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleMultiplayer game = new BoggleMultiplayer();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("./Assignment1/wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        Object r = method.invoke(game, allWords, boggleDict, grid);
        BoggleStats gameStats = game.getGameStats();
        gameStats.addWord("HOST", BoggleStats.Player.Player1);
        gameStats.addWord("THIN", BoggleStats.Player.Player2);

        Method method1 = game.getClass().getDeclaredMethod("wordsNotFound", Map.class);
        method1.setAccessible(true);
        Object s = method1.invoke(game, allWords);

        Set<String> expected = new HashSet<>(Arrays.asList("THIN"));

        assertEquals(new HashSet<>(Arrays.asList("GHOST")), gameStats.getComputerWords());
        assertEquals(new HashSet<>(Arrays.asList("HOST")), gameStats.getPlayer1Words());
        assertEquals(expected, gameStats.getPlayer2Words());
    }

    //BoggleMultiplayerTest5 tests the case where both players enter words however there no are words
    //left for the computer to display
    @Test
    void boggleMultiplayerTest5() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleMultiplayer game = new BoggleMultiplayer();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("./Assignment1/wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        Object r = method.invoke(game, allWords, boggleDict, grid);
        BoggleStats gameStats = game.getGameStats();
        gameStats.addWord("HOST", BoggleStats.Player.Player1);
        gameStats.addWord("THIN", BoggleStats.Player.Player1);
        gameStats.addWord("GHOST", BoggleStats.Player.Player2);

        Method method1 = game.getClass().getDeclaredMethod("wordsNotFound", Map.class);
        method1.setAccessible(true);
        Object s = method1.invoke(game, allWords);

        Set<String> expected = new HashSet<>(Arrays.asList("GHOST"));

        assertEquals(new HashSet<>(), gameStats.getComputerWords());
        assertEquals(new HashSet<>(Arrays.asList("HOST", "THIN")), gameStats.getPlayer1Words());
        assertEquals(expected, gameStats.getPlayer2Words());
    }

    //BoggleMultiplayerTest6 tests the case of roundWinner where both players enter words that gives
    //no one the win
    @Test
    void boggleMultiplayerTest6() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleMultiplayer game = new BoggleMultiplayer();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("./Assignment1/wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        Object r = method.invoke(game, allWords, boggleDict, grid);
        BoggleStats gameStats = game.getGameStats();
        gameStats.addWord("HOST", BoggleStats.Player.Player1);
        gameStats.addWord("THIN", BoggleStats.Player.Player1);
        gameStats.addWord("GHOST", BoggleStats.Player.Player2);

        Method method1 = gameStats.getClass().getDeclaredMethod("roundWinner");
        method1.setAccessible(true);
        Object s = method1.invoke(gameStats);

        ArrayList<String> newArray = new ArrayList<String>();
        newArray.add("No One");
        newArray.add("0");

        assertEquals(newArray, s);
    }

    //BoggleMultiplayerTest7 tests the case of roundWinner where both players don't enter words
    // that gives the win to no player
    @Test
    void boggleMultiplayerTest7() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleMultiplayer game = new BoggleMultiplayer();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("./Assignment1/wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        Object r = method.invoke(game, allWords, boggleDict, grid);
        BoggleStats gameStats = game.getGameStats();

        Method method1 = gameStats.getClass().getDeclaredMethod("roundWinner");
        method1.setAccessible(true);
        Object s = method1.invoke(gameStats);

        ArrayList<String> newArray = new ArrayList<String>();
        newArray.add("No One");
        newArray.add("0");

        assertEquals(newArray, s);
    }

    //BoggleMultiplayerTest8 tests the case of roundWinner where Player1 enters more words than
    //Player2
    @Test
    void boggleMultiplayerTest8() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleMultiplayer game = new BoggleMultiplayer();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("./Assignment1/wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        Object r = method.invoke(game, allWords, boggleDict, grid);
        BoggleStats gameStats = game.getGameStats();
        gameStats.addWord("GHOST", BoggleStats.Player.Player1);
        gameStats.addWord("HOST", BoggleStats.Player.Player1);
        gameStats.addWord("THIN", BoggleStats.Player.Player2);

        Method method1 = gameStats.getClass().getDeclaredMethod("roundWinner");
        method1.setAccessible(true);
        Object s = method1.invoke(gameStats);

        ArrayList<String> newArray = new ArrayList<String>();
        newArray.add("Player1");
        newArray.add("2");

        assertEquals(newArray, s);
    }

    //BoggleMultiplayerTest9 tests the case of roundWinner where Player2 enters more words than
    //Player1
    @Test
    void boggleMultiplayerTest9() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoggleMultiplayer game = new BoggleMultiplayer();
        Method method = game.getClass().getDeclaredMethod("findAllWords", Map.class, Dictionary.class, BoggleGrid.class);
        method.setAccessible(true);

        Dictionary boggleDict = new Dictionary("./Assignment1/wordlist.txt");
        Map<String, ArrayList<Position>> allWords = new HashMap<>();
        BoggleGrid grid = new BoggleGrid(4);
        grid.initalizeBoard("RHLDNHTGIPHSNMJO");
        Object r = method.invoke(game, allWords, boggleDict, grid);
        BoggleStats gameStats = game.getGameStats();
        gameStats.addWord("HOST", BoggleStats.Player.Player1);
        gameStats.addWord("THIN", BoggleStats.Player.Player2);
        gameStats.addWord("GHOST", BoggleStats.Player.Player2);

        Method method1 = gameStats.getClass().getDeclaredMethod("roundWinner");
        method1.setAccessible(true);
        Object s = method1.invoke(gameStats);

        ArrayList<String> newArray = new ArrayList<String>();
        newArray.add("Player2");
        newArray.add("2");

        assertEquals(newArray, s);
    }

}
