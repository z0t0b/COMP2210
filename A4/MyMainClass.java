import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * My main class.
 *
 * @version 3/26/18
 */
public class MyMainClass implements WordSearchGame {
   
   private boolean lexiconRan = false;
   private TreeSet<String> lexiconTree;
   private SortedSet<String> validWords;
   private List<Integer> wordPath;
   private List<Integer> otherWordPath;
   private Boolean[][] beenVisited;
   private String[][] newBoard;
   private int boardSize;
   private int wordSize;
   private int length;
   private int newMinLength;
   private String result;
   private int result2;
   private double boardDimensions;
   
   /**
    * Constructor for MyMainClass().
    */
   public MyMainClass() {
      lexiconTree = new TreeSet<String>();
      validWords = new TreeSet<String>();
      wordPath = new ArrayList<Integer>();
      otherWordPath = new ArrayList<Integer>();
   }
   
   /**
    * Loads the lexicon into a data structure for later use. 
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
   public void loadLexicon(String fileName) throws IllegalArgumentException {
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      lexiconTree = new TreeSet<String>();
      try {
         Scanner s = 
               new Scanner(new BufferedReader(new FileReader(new File(fileName))));
         while (s.hasNext()) {
            String str = s.next();
            lexiconTree.add(str.toLowerCase());
            s.nextLine();
         }
         lexiconRan = true;
      }
      catch (Exception e) {
         throw new IllegalArgumentException("Error loading word list: " + fileName + ": " + e);
      } 
   }
   
   /**
    * Stores the incoming array of Strings in a data structure that will make
    * it convenient to find words.
    * 
    * @param letterArray This array of length N^2 stores the contents of the
    *     game board in row-major order. Thus, index 0 stores the contents of board
    *     position (0,0) and index length-1 stores the contents of board position
    *     (N-1,N-1). Note that the board must be square and that the strings inside
    *     may be longer than one character.
    * @throws IllegalArgumentException if letterArray is null, or is  not
    *     square.
    */

   public void setBoard(String[] letterArray) throws IllegalArgumentException {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      boardDimensions = Math.sqrt(letterArray.length);
      if (boardDimensions % 1 > 0) {
         throw new IllegalArgumentException();
      }
      else {
         length = (int) boardDimensions;
         newBoard = new String[length][length];
         beenVisited = new Boolean[length][length];
         for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
               beenVisited[i][j] = false;
               newBoard[i][j] = letterArray[boardSize].toLowerCase();
               boardSize++;
            }
         }
      }
   }
   
   /**
    * Creates a String representation of the board, suitable for printing to
    *   standard out. Note that this method can always be called since
    *   implementing classes should have a default board.
    */
   public String getBoard() {
      for (String[] s : newBoard) {
         for (String s2 : s) {
            result = result + s2;
         }
      }
      return result;
   }
   
   /**
    * Retrieves all valid words on the game board, according to the stated game
    * rules.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the board.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game board and in the lexicon.
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public SortedSet<String> getAllValidWords(int minimumWordLength) throws IllegalArgumentException,
      IllegalStateException {
      newMinLength = minimumWordLength;
      validWords.clear();
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (lexiconRan == false) {
         throw new IllegalStateException();
      }
      for (int i = 0; i < length; i++) {
         for (int j = 0; j < length; j++) {
            findWord(newBoard[i][j], i, j);
         }
      }
      return validWords;
   }
   
  /**
   * Computes the cummulative score for the scorable words in the given set.
   * To be scorable, a word must (1) have at least the minimum number of characters,
   * (2) be in the lexicon, and (3) be on the board. Each scorable word is
   * awarded one point for the minimum number of characters, and one point for 
   * each character beyond the minimum number.
   *
   * @param words The set of words that are to be scored.
   * @param minimumWordLength The minimum number of characters required per word
   * @return the cummulative score of all scorable words in the set
   * @throws IllegalArgumentException if minimumWordLength < 1
   * @throws IllegalStateException if loadLexicon has not been called.
   */  
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      
      if (lexiconRan == false) {
         throw new IllegalStateException();
      }
      
      for (String s: words) {
         wordSize = s.length();
         result2 += (wordSize - minimumWordLength) + 1;
      }
      return result2;
   }
   
   /**
    * Determines if the given word is in the lexicon.
    * 
    * @param wordToCheck The word to validate
    * @return true if wordToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidWord(String wordToCheck) throws IllegalArgumentException,
      IllegalStateException {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      if (lexiconRan == false) {
         throw new IllegalStateException();
      }
      
      if (lexiconTree.contains(wordToCheck.toLowerCase())) {
         return true;
      }
      return false;
   }  
   
   /**
    * Determines if there is at least one word in the lexicon with the 
    * given prefix.
    * 
    * @param prefixToCheck The prefix to validate
    * @return true if prefixToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if prefixToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidPrefix(String prefixToCheck) {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      if (lexiconRan == false) {
         throw new IllegalStateException();
      }
      
      if (lexiconTree.ceiling(prefixToCheck).startsWith(prefixToCheck)) {
         return true;
      }
      return false;
   }
   
   /**
    * Determines if the given word is in on the game board. If so, it returns
    * the path that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer objects with  the path
    *     that makes up the word on the game board. If word is not on the game
    *     board, return an empty list. Positions on the board are numbered from zero
    *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
    *     board, the upper left position is numbered 0 and the lower right position
    *     is numbered N^2 - 1.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      if (lexiconRan == false) {
         throw new IllegalStateException();
      }
      
      wordPath.clear();
      otherWordPath.clear();
      for (int i = 0; i < (int) length; i++) {
         for (int j = 0; j < length; j++) {
            if (Character.toUpperCase(newBoard[i][j].charAt(0)) == Character.toUpperCase(wordToCheck.charAt(0))) {
               int returnValue = j + (i * length);
               wordPath.add(returnValue);
               myRecursionMethod(wordToCheck, newBoard[i][j], i, j);
               if (!otherWordPath.isEmpty()) {
                  return otherWordPath;
               }
               wordPath.clear();
               otherWordPath.clear();
            }
         }
      }
      return wordPath;
   }

   // MY NEW METHODS
   
   /**
    * This method finds words for the getAllValidWords() method.
    * @param wordToCheck for word to check.
    * @param x is the x value.
    * @param y is the y value.
    */
   public void findWord(String wordToCheck, int x, int y) {
      if (!isValidPrefix(wordToCheck)) {
         return;
      }
      beenVisited[x][y] = true;
      if (isValidWord(wordToCheck) && wordToCheck.length() >= newMinLength) {
         validWords.add(wordToCheck.toUpperCase());
      }
   
      for (int i = -1; i <= 1; i++) {
         for (int j = -1; j <= 1; j++) {
            if ((x + i) <= ((int) length - 1) && (y + j) <= ((int) length - 1) && (x + i) >= 0 && (y + j) >= 0 && !beenVisited[x + i][y + j]) {
               beenVisited[x + i][y + j] = true;
               findWord(wordToCheck + newBoard[x + i][y + j], x + i, y + j);
               beenVisited[x + i][y + j] = false;
            }
         }
      }
      beenVisited[x][y] = false;
   }

   /**
    * My recursion method for isOnBoard().
    * @param wordToCheck is the word to check.
    * @param currentWord is current word.
    * @param x is the x value.
    * @param y is the y value.
*/
   public void myRecursionMethod(String wordToCheck, String currentWord, int x, int y) {
      beenVisited[x][y] = true;
      if (!isValidPrefix(currentWord)) {
         return;
      }
      
      if (currentWord.toUpperCase().equals(wordToCheck.toUpperCase())) {
         otherWordPath = new ArrayList(wordPath);
         return;
      }
      
      for (int i = -1; i <= 1; i++) {
         for (int j = -1; j <= 1; j++) {
            if (currentWord.equals(wordToCheck)) {
               return;
            }
            if ((x + i) <= (length - 1) && (y + j) <= (length - 1) && (x + i) >= 0 && (y + j) >= 0 && !beenVisited[x + i][y + j]) {
               beenVisited[x + i][y + j] = true;
               int value = (x + i) * length + y + j;
               wordPath.add(value);
               myRecursionMethod(wordToCheck, currentWord + newBoard[x + i][y + j], x + i, y + j);
               beenVisited[x + i][y + j] = false;
               wordPath.remove(wordPath.size() - 1);
            }
         }
      }
      beenVisited[x][y] = false;
      return;
   }
}
