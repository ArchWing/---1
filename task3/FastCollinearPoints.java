import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
    
   private int numbers = 0;
   private ArrayList<LineSegment> seglist = new ArrayList<LineSegment>();
   //private final LineSegment[] seglist2;
    
   public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
   {
       if (points == null) {
             throw new java.lang.IllegalArgumentException();
         }
  
       int N = points.length;
       
       for(int i = 0; i<N ; i++)
       {
           if (points[i] == null) {
                 throw new java.lang.IllegalArgumentException();
             }
       }
       for(int i = 0; i<N ; i++)
       {
           for(int j = i+1;j<N;j++)
           {
               if(points[i].compareTo(points[j])==0)
                   throw new java.lang.IllegalArgumentException("duplicate point");
           }
       }
       
       if(N<4)
           return;
       
       Point p1;
       Point[] otherpoint = new Point[N-1];
       //Arrays.sort(points);
       //this.seglist = new LineSegment[N];
       
       for(int i = 0; i<N ; i++)
       {
           p1 = points[i];
           for(int j = 0;j<N-1;j++)
           {
               if(j<i)
                   otherpoint[j] = points[j];
               else
                   otherpoint[j] = points[j+1];
               //StdOut.println(otherpoint[j]);
           }
           //StdOut.println(otherpoint.length);
           
           // the most important trick
           Arrays.sort(otherpoint, p1.slopeOrder());
           
           int flag = 0;
           double start = p1.slopeTo(otherpoint[flag]);
           //StdOut.println("first:"+start);
           //StdOut.println("second:"+p1.slopeTo(otherpoint[N-2]));
           int index = 1;
           for(int m = 1; m<N-1; m++)
           {
               if(p1.slopeTo(otherpoint[m])==start)
                   index++;
               else
               {
                   if(index<3)
                   {   start = p1.slopeTo(otherpoint[m]);index=1;flag=m; }
                   else
                   {
                       Point max = p1;
                       Point min = p1;
                       for(int n = flag;n<m;n++)
                       {
                           if(otherpoint[n].compareTo(max)>0)
                               max = otherpoint[n];
                           if(otherpoint[n].compareTo(min)<0)
                               min = otherpoint[n];
                       }
                       if(min.compareTo(p1)==0)
                       {
                       seglist.add(new LineSegment(p1, max));
                       numbers++;
                       start = p1.slopeTo(otherpoint[m]);index=1;flag=m;
                       }
                       //the following one is hard for me to find, be cautious
                       start = p1.slopeTo(otherpoint[m]);index=1;flag=m;
                   } 
               }   
           }
           if(index>=3)
           {
               Point max = p1;
               Point min = p1;
               for(int n = flag;n<N-1;n++)
               {
                    if(otherpoint[n].compareTo(max)>0)
                        max = otherpoint[n];
                    if(otherpoint[n].compareTo(min)<0)
                        min = otherpoint[n];
                }
               if(min.compareTo(p1)==0)
               {         
                   seglist.add(new LineSegment(p1, max));
                   numbers++;
               }
           }   
       }
//       StdOut.println(numbers);       
       
       //LineSegment[] seglist3  = Arrays.copyOf(seglist, numbers);
       //deal with the duplicate segment
       //Arrays.sort(seglist3);
//       int benum = numbers;
//       int flag = 0;
//       //seglist2[0] = seglist[0];
//       String ss = seglist[0].toString();
       
//       String[] strs = new String[numbers];
//       for(int i =0;i<numbers;i++)
//           strs[i] = seglist[i].toString();
//       for(int i =0;i<numbers;i++)
//       {
//           String s1 = strs[i];
//           if(s1==null) continue;
//           for(int j=i+1;j<numbers;j++)
//       {
//           if(strs[j]==null) continue;
//           if(strs[j].equals(s1))
//           {strs[j]=null;}
//       }
//       }
//       
//       numbers=0;
//       for(int w = 0;w<benum;w++)
//          {
//               if(strs[w]!=null)
//                   numbers++;
//           }

//       seglist2 = new LineSegment[numbers];
//           for(int w = 0;w<benum;w++)
//          {
//               
//               if(strs[w]!=null)
//               {   
//                   seglist2[flag] = seglist[w];
//                   flag++;
//               }
//
//           }
       
   }
   
   public int numberOfSegments()        // the number of line segments
   {
       return this.numbers;
   }
   
   public LineSegment[] segments()                // the line segments
   {
       LineSegment[] results = new LineSegment[numbers];
       for(int i = 0;i<numbers;i++)
           results[i] = seglist.get(i);
       return results;
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}