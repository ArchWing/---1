import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
   //size
   private int N;
   private SET<Point2D> sets = new SET<Point2D>();
   
   public PointSET()                               // construct an empty set of points 
   {
       N=0;
   }
   
   public boolean isEmpty()                      // is the set empty? 
   {
       return N==0;
   }
   public int size()                         // number of points in the set
   {
       return N;
   }
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
       if(p==null)
           throw new java.lang.IllegalArgumentException();  
       sets.add(p);
       N++;
   }
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
       if(p==null)
           throw new java.lang.IllegalArgumentException();
       return sets.contains(p);
   }
   public void draw()                         // draw all points to standard draw 
   {
       for(Point2D p : sets)
       {
           StdDraw.point(p.x(), p.y());
       }
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
   {
       SET<Point2D> inset = new SET<Point2D>();
       if(rect==null)
           throw new java.lang.IllegalArgumentException();
       for(Point2D point : sets)
       {
           if(rect.contains(point))
               inset.add(point);
       }
       return inset;
       
   }
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
       if(p==null)
           throw new java.lang.IllegalArgumentException();
       if(N==0) 
           return null;
       Point2D near = null;
       double dis = Double.MAX_VALUE;
       for(Point2D po : sets)
       {
           if(po.distanceTo(p)<dis)
           {
               near = po;
               dis = po.distanceTo(p);
           }
       }
       return near;
       
   }

   public static void main(String[] args)                  // unit testing of the methods (optional) 
   {




}
}