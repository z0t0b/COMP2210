
/**
 * Provides a factory method for creating word search games. 
 *
 * @version 3/26/18
 */
public class WordSearchGameFactory {

   /**
    * Returns an instance of a class that implements the WordSearchGame
    * interface.
    */
   public static WordSearchGame createGame() {
      return new MyMainClass();
   }

}
