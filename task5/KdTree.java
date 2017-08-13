import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet; 
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
   
   private KdNode tree;
   private int N;
   
   // cannot undersatnd
   private static final RectHV CONTAINER = new RectHV(0, 0, 1, 1);
    
   private class KdNode
   {
       private final Point2D root;
       private KdNode left;
       private KdNode right;
       private final boolean coord;  //true means x-axis

       public KdNode(Point2D root, KdNode left,KdNode right, boolean coord)
       {
           this.root = root;
           this.left = left;
           this.right = right;
           this.coord = coord;
       }
   }
       
       private KdNode put(final KdNode node, final Point2D key, final boolean coord)
       {
           if(node==null)
           {
               N++;
               return new KdNode(key, null, null, coord); 
           }
           
           if(node.root.x() == key.x() && node.root.y() == key.y())
               return node;
           
           if(node.coord==true)
           {
               if(node.root.x()>key.x())
                   node.left = put(node.left, key, !node.coord);
               else
                   node.right = put(node.right, key, !node.coord);
           }
           else
           {
               if(node.root.y()>key.y())
                   node.left = put(node.left, key, !node.coord);
               else
                   node.right = put(node.right, key, !node.coord);
           }
           return node;
        }
       
       private boolean get(KdNode node, Point2D key)
       {
           if(node==null)
               return false;
           
           if(node.root.x() == key.x() && node.root.y() == key.y())
               return true;
           
           if(node.coord==true)
           {
               if(node.root.x()>key.x())
                   return get(node.left, key);
               else
                   return get(node.right, key);
           }
           else
           {
               if(node.root.y()>key.y())
                   return get(node.left, key);
               else
                   return get(node.right, key);
           }   
       }
      
   
   public  KdTree()                               // construct an empty set of points
   {
       this.N = 0;
       this.tree = null;
   }
   public  boolean isEmpty()                      // is the set empty? 
   {
       return N == 0;
   }
   public  int size()                         // number of points in the set 
   {
       return N;
   }
   public  void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
       this.tree = put(this.tree, p, true);
   }
   public  boolean contains(Point2D p)            // does the set contain point p? 
   {
       return get(tree, p);
   }
   
   public  void draw()                         // draw all points to standard draw 
   {
            StdDraw.setScale(0, 1); 
            StdDraw.setPenColor(StdDraw.BLACK);    
            StdDraw.setPenRadius();    
            CONTAINER.draw();    
        
            draw(tree, CONTAINER); 
   }
   private void draw(final KdNode node, final RectHV rect) 
   {    
            if (node == null) {    
                return;    
            }    
        
            // draw the point    
            StdDraw.setPenColor(StdDraw.BLACK);    
            StdDraw.setPenRadius(0.01);    
            new Point2D(node.root.x(), node.root.y()).draw();    
        
            // get the min and max points of division line    
            Point2D min, max;    
            if (node.coord) {    
                StdDraw.setPenColor(StdDraw.RED);    
                min = new Point2D(node.root.x(), rect.ymin());    
                max = new Point2D(node.root.x(), rect.ymax());    
            } else {    
                StdDraw.setPenColor(StdDraw.BLUE);    
                min = new Point2D(rect.xmin(), node.root.y());    
                max = new Point2D(rect.xmax(), node.root.y());    
            }    
        
            // draw that division line    
            StdDraw.setPenRadius();    
            min.drawTo(max);    
        
            // recursively draw children    
            draw(node.left, leftRect(rect, node));    
            draw(node.right, rightRect(rect, node));    
        }    
   
   private RectHV leftRect(final RectHV rect, final KdNode node) 
   {    
            if (node.coord) 
            {    
                return new RectHV(rect.xmin(), rect.ymin(), node.root.x(), rect.ymax());    
            } 
            else 
            {    
                return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.root.y());    
            }    
   }    
        
   private RectHV rightRect(final RectHV rect, final KdNode node) {    
            if (node.coord) {   
                return new RectHV(node.root.x(), rect.ymin(), rect.xmax(), rect.ymax());    
            } else {    
                return new RectHV(rect.xmin(), node.root.y(), rect.xmax(), rect.ymax()); 
                //return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), rect.ymax()); 
                
            }    
   }  
        
   public  Iterable<Point2D> range(final RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
   {
       final TreeSet<Point2D> rangeSet = new TreeSet<Point2D>();    
       range(this.tree, CONTAINER, rect, rangeSet);    
        
       return rangeSet;
   }
   
   private void range(final KdNode node, final RectHV nrect,    
                final RectHV rect, final TreeSet<Point2D> rangeSet) {    
            if (node == null)    
                return;    
        
            if (rect.intersects(nrect)) {    
                final Point2D p = new Point2D(node.root.x(), node.root.y());    
                if (rect.contains(p))    
                    rangeSet.add(p);    
                range(node.left, leftRect(nrect, node), rect, rangeSet);    
                range(node.right, rightRect(nrect, node), rect, rangeSet);    
            }    
   }   
   
   public  Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
       return nearest(tree, CONTAINER, p.x(), p.y(), null);
   }
   
   private Point2D nearest(final KdNode node, final RectHV rect,    
                final double x, final double y, final Point2D candidate) {    
            if (node == null){    
                return candidate;    
            }    
        
            double dqn = 0.0;    
            double drq = 0.0;    
            RectHV left = null;    
            RectHV rigt = null;    
            final Point2D query = new Point2D(x, y);    
            Point2D nearest = candidate;    
        
            if (nearest != null) {    
                dqn = query.distanceSquaredTo(nearest);    
                drq = rect.distanceSquaredTo(query);    
            }    
        
            if (nearest == null || dqn > drq) {    
                final Point2D point = new Point2D(node.root.x(), node.root.y());    
                if (nearest == null || dqn > query.distanceSquaredTo(point))    
                    nearest = point;    
        
                if (node.coord) {    
                    left = new RectHV(rect.xmin(), rect.ymin(), node.root.x(), rect.ymax());    
                    rigt = new RectHV(node.root.x(), rect.ymin(), rect.xmax(), rect.ymax());    
        
                    if (x < node.root.x()) {    
                        nearest = nearest(node.left, left, x, y, nearest);    
                        nearest = nearest(node.right, rigt, x, y, nearest);    
                    } else {    
                        nearest = nearest(node.right, rigt, x, y, nearest);    
                        nearest = nearest(node.left, left, x, y, nearest);    
                    }    
                } else {    
                    left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.root.y());    
                    rigt = new RectHV(rect.xmin(), node.root.y(), rect.xmax(), rect.ymax());    
        
                    if (y < node.root.y()) {    
                        nearest = nearest(node.left, left, x, y, nearest);    
                        nearest = nearest(node.right, rigt, x, y, nearest);    
                    } else {    
                        nearest = nearest(node.right, rigt, x, y, nearest);    
                        nearest = nearest(node.left, left, x, y, nearest);    
                    }    
                }    
            }    
        
            return nearest;    
        }    

   public static void main(String[] args)                  // unit testing of the methods (optional) 
   {
   }
   
}