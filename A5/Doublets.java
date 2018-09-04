import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. The lexicon
 * is stored as a HashSet of Strings.
 *
 * @version 2018-04-06
 */
public class Doublets implements WordLadderGame {

    // The word list used to validate words.
    // Must be instantiated and populated in the constructor.
   private HashSet<String> lexicon;
   private List<String> emptyLadder = new ArrayList<>();
   private ArrayList returnArray = new ArrayList();
   private int result;
   private int wordCount;
   private int count;
   private String position;
   private Node x;
   private Node i;
   
   // The private class Node, implemented within getMinLadder.
   private class Node {
      String nodePosition1;
      Node node1;
      
      // Node's public constructor.
      public Node(String nodePosition2, Node node2) {
         nodePosition1 = nodePosition2;
         node1 = node2;
      }
   }
    
    /**
     * Instantiates a new instance of Doublets with the lexicon populated with
     * the strings in the provided InputStream. The InputStream can be formatted
     * in different ways as long as the first string on each line is a word to be
     * stored in the lexicon.
     */
   public Doublets(InputStream in) {
      try {
         lexicon = new HashSet<String>();
         Scanner s =
                new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next();
            lexicon.add(str.toLowerCase());
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }

   /**
     * Returns the Hamming distance between two strings, str1 and str2. The
     * Hamming distance between two strings of equal length is defined as the
     * number of positions at which the corresponding symbols are different. The
     * Hamming distance is undefined if the strings have different length, and
     * this method returns -1 in that case. See the following link for
     * reference: https://en.wikipedia.org/wiki/Hamming_distance
     *
     * @param  str1 the first string
     * @param  str2 the second string
     * @return      the Hamming distance between str1 and str2 if they are the
     *                  same length, -1 otherwise
     */
   public int getHammingDistance(String str1, String str2) {
      result = 0;
      if (str1.length() != str2.length() || (str1 == null || str2 == null)) {
         return -1;
      }
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            result++;
         }
      }
      return result;
   }

   /**
    * Returns a minimum-length word ladder from start to end. If multiple
    * minimum-length word ladders exist, no guarantee is made regarding which
    * one is returned. If no word ladder exists, this method returns an empty
    * list.
    *
    * Breadth-first search must be used in all implementing classes.
    *
    * @param  start  the starting word
    * @param  end    the ending word
    * @return        a minimum length word ladder from start to end
    */
   public List<String> getMinLadder(String start, String end) {
      List<String> ladder = new ArrayList<String>();
      if (start.equals(end)) {
         ladder.add(start);
         return ladder;
      }
      else if ((start.length() != end.length()) || (!isWord(start) || (!isWord(end)))) {
         return emptyLadder;
      }
      TreeSet<String> beenVisited = new TreeSet<String>();
      Deque<Node> queue = new ArrayDeque<>();
      beenVisited.add(start);
      queue.addLast(new Node(start, null));
      while (!queue.isEmpty()) {
         x = queue.removeFirst();
         position = x.nodePosition1;
         for (String s : getNeighbors(position)) {
            if (!beenVisited.contains(s)) {
               beenVisited.add(s);
               queue.addLast(new Node(s, x));
            }
            if (s.equals(end)) {
               i = queue.removeLast();
               while (i != null) {
                  ladder.add(0, i.nodePosition1);
                  i = i.node1;
               }
               return ladder;
            }
         }
      }
      return emptyLadder;
   }
    
    /**
     * Returns all the words that have a Hamming distance of one relative to the
     * given word.
     *
     * @param  word the given word
     * @return      the neighbors of the given word
     */
   public List<String> getNeighbors(String word) {
      returnArray.clear();
      for (String s: lexicon) {
         if (getHammingDistance(word, s) == 1) {
            returnArray.add(s);
         }
      }   
      if (returnArray.isEmpty()) {
         return emptyLadder;
      }     
      return returnArray;
   }
    
    /**
     * Returns the total number of words in the current lexicon.
     *
     * @return number of words in the lexicon
     */
   public int getWordCount() {
      wordCount = lexicon.size();
      return wordCount;
   }

    /**
     * Checks to see if the given string is a word.
     *
     * @param  str the string to check
     * @return     true if str is a word, false otherwise
     */
   public boolean isWord(String str) {
      if (!(lexicon.contains(str))) {
         return false;
      }
      return true;
   }

    /**
     * Checks to see if the given sequence of strings is a valid word ladder.
     *
     * @param  sequence the given sequence of strings
     * @return          true if the given sequence is a valid word ladder,
     *                       false otherwise
     */
   public boolean isWordLadder(List<String> sequence) {
      if (sequence.isEmpty() || sequence == null) {
         return false;
      }
      for (int i = 0; i < sequence.size(); i++) {
         if (!(lexicon.contains(sequence.get(i)))) {
            return false;
         }
      }
      for (int i = 0; i < sequence.size() - 1; i++) {
         if (getHammingDistance(sequence.get(i), sequence.get(i + 1)) != 1) {
            return false;
         }
      }
      return true;
   }

}

