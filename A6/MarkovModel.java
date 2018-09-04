import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * MarkovModel.java Creates an order K Markov model of the supplied source
 * text. The value of K determines the size of the "kgrams" used to generate
 * the model. A kgram is a sequence of k consecutive characters in the source
 * text.
 *
 * @version    2018-04-17
 *
 */
public class MarkovModel {

   // Map of <kgram, chars following> pairs that stores the Markov model.
   private HashMap<String, String> model;

   // add other fields as you need them ...
   int x = 0;
   int y = 0;
   int index = 0;
   String first = "";
   String emptyString = "";
   String kgram = "";
   String testString = "";
   Random random = new Random();

   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int k, File sourceText) {
      model = new HashMap<>();
      try {
         String text = new Scanner(sourceText).useDelimiter("\\Z").next();
         buildModel(k, text);
      }
      catch (IOException e) {
         System.out.println("Error loading source text: " + e);
      }
   }


   /**
    * Calls buildModel to construct the order K model of the string sourceText.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int k, String sourceText) {
      model = new HashMap<>();
      buildModel(k, sourceText);
   }


   /**
    * Builds an order K Markov model of the string sourceText.
    */
   private void buildModel(int k, String sourceText) {
      int k2 = k;
      x = 0;
      y = 0;
      first = sourceText.substring(0 , k2);
      while ((x + k2) <= sourceText.length()) {
         kgram = sourceText.substring(x, x + k2);
         if (!(model.containsKey(kgram))) {
            int k3 = k;
            while ((y + k3) < sourceText.length()) {
               testString = sourceText.substring(y, y + k3);
               if ((y + k2) >= sourceText.length()) {
                  emptyString += '\u0000';
               }
               if (kgram.equals(testString)) {
                  emptyString += sourceText.substring(y + k3, y + k3 + 1);
               }
               y++;
            }
            model.put(kgram, emptyString);
         }
         y = 0;
         x++;
      } 
   }


   /** Returns the first kgram found in the source text. */
   public String getFirstKgram() {
      return first;
   }


   /** Returns a kgram chosen at random from the source text. */
   public String getRandomKgram() {
      x = model.size();
      y = 0;
      index = random.nextInt(x);
      for (String s : model.keySet()) {
         if (index == y) {
            return s;
         }
         y++;
      }
      return null;
   }


   /**
    * Returns the set of kgrams in the source text.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   public Set<String> getAllKgrams() {
      return model.keySet();
   }


   /**
    * Returns a single character that follows the given kgram in the source
    * text. This method selects the character according to the probability
    * distribution of all characters that follow the given kgram in the source
    * text.
    */
   public char getNextChar(String kgram) {
      x = 0;
      for (String s : model.keySet()) {
         if (s.equals(kgram)) {
            emptyString = model.get(kgram);
            y = emptyString.length();
            if (y > 0) {
               x = random.nextInt(y) + 1;
            }
         }
      }
      y = x - 1;
      if (!(emptyString.equals(""))) {
         return emptyString.charAt(y);
      } 
      return '\u0000';
   }


   /**
    * Returns a string representation of the model.
    * This is not part of the provided shell for the assignment.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   @Override
    public String toString() {
      return model.toString();
   }

}
