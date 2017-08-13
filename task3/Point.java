/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.In;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {
    
    //public final Comparator<Point> SLOPE_ORDER = new slopeOrder();
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
    private final SlopeComparator bySlope = new SlopeComparator();

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        int x0 = this.x;
        int y0 = this.y;
        int x1 = that.x;
        int y1 = that.y;
        
        if(x1==x0)
        {
            if(y1==y0)
                return Double.NEGATIVE_INFINITY;
            else
                return Double.POSITIVE_INFINITY;
        }
        else
        {
            if(y1==y0)
                return +0.0;
            else
                return (y1-y0)/(double)(x1-x0);
        }
            
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        int x0 = this.x;
        int y0 = this.y;
        int x1 = that.x;
        int y1 = that.y;
        if(y0<y1)
            return -1;
        else
        {
            if(y1==y0)
           {
                if(x1==x0)
                    return 0;
                else 
                {
                    if(x1>x0)
                        return -1;
                    else
                        return +1;
                }
                    
            }
            else
            {
                return +1;
            }
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    
    private class SlopeComparator implements Comparator<Point>
    {
        private final Point p0 = Point.this;
        public int compare(Point p1, Point p2)
        {
            double s1 = p0.slopeTo(p1);
            double s2 = p0.slopeTo(p2);
            if(s1==s2) return 0;
            if(s1<s2) return -1;
            else return +1;
        }
    }
    
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return this.bySlope;
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
  
//        int N = StdIn.readInt();
//        StdOut.println(N);
//        StdDraw.show(0);
//        StdDraw.setXscale(0, 32768);  
//        StdDraw.setYscale(0, 32768);
//        for (int i = 0; i < N; i++) 
//        {  
//            StdOut.println(i);
//            int x = StdIn.readInt();  
//            int y = StdIn.readInt();    
//            Point p = new Point(x, y); 
//            StdOut.println(p.toString());
//            p.draw();  
//        }   
//        StdDraw.show(); 

        
        StdOut.print("Enter File Name: ");
        String inFile = StdIn.readLine();
        StdOut.println(inFile);
        In in = new In(inFile+ ".txt");
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01); 
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

    }
}
