import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Extractor.java. Implements feature extraction for collinear points in
 * two dimensional data.
 *
 * @version 2018-02-27
 *
 */
public class Extractor {
   
   /** raw data: all (x,y) points from source data. */
   private Point[] points;
   
   /** lines identified from raw data. */
   private SortedSet<Line> lines;
  
   /**
    * Builds an extractor based on the points in the file named by filename. 
    */
   public Extractor(String filename) {
      try {
         Scanner scan = new Scanner(new File(filename));
         int fileLength = scan.nextInt();
         points = new Point[fileLength];
         int count = 0;
         while (scan.hasNext()) {
            int x = scan.nextInt();
            int y = scan.nextInt();
            Point p = new Point(x, y);
            points[count] = p;
            count++;
         }
      } catch (Exception e) {
         System.out.println("File not found");
      }
   }
  
   /**
    * Builds an extractor based on the points in the Collection named by pcoll. 
    *
    * THIS METHOD IS PROVIDED FOR YOU AND MUST NOT BE CHANGED.
    */
   public Extractor(Collection<Point> pcoll) {
      points = pcoll.toArray(new Point[]{});
   }
  
   /**
    * Returns a sorted set of all line segments of exactly four collinear
    * points. Uses a brute-force combinatorial strategy. Returns an empty set
    * if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesBrute() {
      lines = new TreeSet<Line>();
      Iterator itr = lines.iterator();
      Point[] array = Arrays.copyOf(points, points.length);
      for (int z = 3; z < array.length; z++) {
         for (int a = 2; a < z; a++) {
            for (int c = 1; c < a; c++) {
               for (int h = 0; h < c; h++) {
                  if ((array[z].slopeTo(array[a])) == (array[z].slopeTo(array[c]))) {
                     if ((array[z].slopeTo(array[c])) == (array[z].slopeTo(array[h]))) {
                        Line l = new Line();
                        l.add(array[z]);
                        l.add(array[a]);
                        l.add(array[c]);
                        l.add(array[h]);
                        lines.add(l);
                     }
                  }
               }
            }
         }
      }
      return lines;
   }
  
   /**
    * Returns a sorted set of all line segments of at least four collinear
    * points. The line segments are maximal; that is, no sub-segments are
    * identified separately. A sort-and-scan strategy is used. Returns an empty
    * set if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesFast() {
      lines = new TreeSet<Line>();
      Point[] arrayCopy = Arrays.copyOf(points, points.length);
      for (int i = 0; i < points.length; i++) {
         int numberOfEqualSlopes = 0;
         Arrays.sort(arrayCopy, points[i].slopeOrder);
         for (int j = 0; j < points.length - 1; j = j + numberOfEqualSlopes) {
            int count = 0;
            numberOfEqualSlopes = 0;
            while (((j + count) < points.length) && (points[i].slopeOrder.compare(arrayCopy[j], arrayCopy[j + count]) == 0)) {
               count++;
               numberOfEqualSlopes++;
            }
            if (numberOfEqualSlopes > 2) {
               Line l = new Line();
               l.add(points[i]);
               for (int k = 0; k < numberOfEqualSlopes; k++) {
                  l.add(arrayCopy[j + k]);
               }
               lines.add(l);
            }
         }
      }
      return lines;
   }
   
}
