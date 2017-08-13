import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.lang.Integer;
import java.util.Iterator;

public class Permutation {
   public static void main(String[] args)
   {
       int k=Integer.parseInt(args[0]);
       RandomizedQueue<String> rq = new RandomizedQueue<String> ();
       while(!StdIn.isEmpty())
       {
           String item = StdIn.readString();
           rq.enqueue(item);
       }
       Iterator<String> collection = rq.iterator();
       for(int i = 0;i<k;i++)
       {
           String s = collection.next();
           StdOut.println(s);
       }
           
   }
}