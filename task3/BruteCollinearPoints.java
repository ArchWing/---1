import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class BruteCollinearPoints {
    
   private int numbers = 0;
   private final LineSegment[] seglist; 
   //private final LineSegment[] seglist2;
    
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
       if (points == null) {
             throw new java.lang.IllegalArgumentException();
         }
       
       for(int i = 0; i< points.length; i++)
       {
           if (points[i] == null) {
                 throw new java.lang.IllegalArgumentException();
             }
       }
       for(int i = 0; i< points.length; i++)
       {
           for(int j = i+1;j<points.length;j++)
           {
               if(points[i].compareTo(points[j])==0)
                   throw new java.lang.IllegalArgumentException("duplicate point");
           }
       }
       
       this.seglist = new LineSegment[points.length];
       Point[] newpoints = Arrays.copyOf(points, points.length);
       Arrays.sort(newpoints);
       for(int i=0; i<newpoints.length;i++)
       {
           for(int j=i+1;j<newpoints.length;j++)
          {
               for(int k=j+1;k<newpoints.length;k++)
                   for(int l=k+1;l<newpoints.length;l++)
                  {
                       if((newpoints[i].slopeTo(newpoints[j])==newpoints[i].slopeTo(newpoints[k]))&&
                           (newpoints[i].slopeTo(newpoints[j])==newpoints[i].slopeTo(newpoints[l])))
                       {   
                           seglist[numbers] = new LineSegment(newpoints[i],newpoints[l]);
                           numbers++; 
                       }
                   }
           }
       }
       //seglist2 = Arrays.copyOf(seglist, numbers);

   }
   
   public int numberOfSegments()        // the number of line segments
   {
       return numbers;
   }
   
   public LineSegment[] segments()                // the line segments
   {
       return Arrays.copyOf(this.seglist, this.numbers);
   }
   
   public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}
