import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @version  2018-01-16
*
*/
public final class Selector {

   /**
    * Can't instantiate this class.
    *
    * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
    *
    */
   private Selector() { }


   /**
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int min(int[] a) throws IllegalArgumentException {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      int minValue = a[0];
      try {
         for (int i = 0; i < a.length; i++) {
            if (a[i] < minValue) {
               minValue = a[i];
            }
         }
      }
      catch (IllegalArgumentException e) {
         System.out.println("Error: IllegalArgumentException");
      }
      finally {
         return minValue;
      }
   }


   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int max(int[] a) throws IllegalArgumentException {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      int maxValue = a[0];
      try {
         for (int i = 0; i < a.length; i++) {
            if (a[i] > maxValue) {
               maxValue = a[i];
            }
         }
      }
      catch (IllegalArgumentException e) {
         System.out.println("Error: IllegalArgumentException.");
      }
      finally {
         return maxValue;
      }
   }


   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmin(int[] a, int k) throws IllegalArgumentException {
      if ((a == null) || (a.length == 0) || (k < 1) || (k > a.length)) {
         throw new IllegalArgumentException();
      }
      int[] a2 = a.clone();
      Arrays.sort(a2);
      int n = 0;
      int least = -999;
      for (int i = 0; i < a2.length; i++) {
         if (a2[i] > least) {
            least = a2[i];
            n++;
            if (n == k) {
               break;
            }
         }
      }
      if (n < k) {
         throw new IllegalArgumentException();
      }
      return least;
   }


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmax(int[] a, int k) throws IllegalArgumentException {
      if ((a == null) || (a.length == 0) || (k < 1) || (k > a.length)) {
         throw new IllegalArgumentException();
      }
      int[] a2 = a.clone();
      Arrays.sort(a2);
      int n = 0;
      int greatest = 999;
      for (int i = a2.length - 1; i >= 0; i--) {
         if (a2[i] < greatest) {
            greatest = a2[i];
            n++;
            if (n == k) {
               break;
            }
         }
      }
      if (n < k) {
         throw new IllegalArgumentException();
      }
      return greatest;
   }


   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    */
   public static int[] range(int[] a, int low, int high) throws IllegalArgumentException {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      try {
         int num3 = 0;
      }
      catch (IllegalArgumentException e) {
         System.out.println("Error: IllegalArgumentException");
      }
      finally {
         int num1 = 0;
         for (int i = 0; i < a.length; i++) {
            if ((a[i] >= low) && (a[i] <= high)) {
               num1++;
            }
         }
         int[] values = new int[num1];
         int num2 = 0;
         for (int i = 0; i < a.length; i++) {
            if ((a[i] >= low) && (a[i] <= high)) {
               values[num2] = a[i];
               num2++;
            }
         }
         return values;
      }
   }


   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int ceiling(int[] a, int key) throws IllegalArgumentException {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      int maxValue = max(a);
      if (key > maxValue) {
         throw new IllegalArgumentException();
      }
      int number = a[0];
      try {
         for (int i = 0; i < a.length; i++) {
            if (a[i] >= key) {
               number = a[i];
               for (int j = 0; j < a.length; j++) {
                  if ((a[j] < number) && (a[j] >= key)) {
                     number = a[j];
                  }
               }
            }
         }
      }
      catch (IllegalArgumentException e) {
         System.out.println("Error: IllegalArgumentException");
      }
      finally {
         return number;
      }
   }


   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int floor(int[] a, int key) throws IllegalArgumentException {
      if (a == null) {
         throw new IllegalArgumentException();
      }
      if (a.length == 0) {
         throw new IllegalArgumentException();
      }
      int minValue = min(a);
      if (minValue > key) {
         throw new IllegalArgumentException();
      }
      int number = a[0];
      try {
         for (int i = 0; i < a.length; i++) {
            if (a[i] <= key) {
               number = a[i];
               for (int j = 0; j < a.length; j++) {
                  if ((a[j] > number) && (a[j] <= key)) {
                     number = a[j];
                  }
               }
            }
         }
      }
      catch (IllegalArgumentException e) {
         System.out.println("Error: IllegalArgumentException");
      }
      finally {
         return number;
      }
   }

}
