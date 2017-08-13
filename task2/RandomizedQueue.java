import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
   
   private Item[] a = (Item[]) new Object[1];
   private int N;
   
   private void resize(int max)
   {
       Item[] temp = (Item[]) new Object[max];
       for(int i = 0; i<a.length;i++)
           temp[i] = a[i];
       a = temp;
   }
   
   public RandomizedQueue()                 // construct an empty randomized queue
   {
       N = 0;
   }
   public boolean isEmpty()                 // is the queue empty?
   {
       return N==0;
   }
   public int size()                        // return the number of items on the queue
   {
       return N;
   }
   public void enqueue(Item item)           // add the item
   {
       if(item==null)
           throw new java.lang.IllegalArgumentException ("item is null");
       if(a.length==N)
           resize(2*N);
       a[N++]=item;
   }
   public Item dequeue()                    // remove and return a random item
   {
       if(isEmpty())
           throw new java.util.NoSuchElementException("array is null");
       Item item = a[--N];
       a[N]=null;
       if(N>0&&N==1/4*a.length)
           resize(N/2);
       return item;
   }
   public Item sample()                     // return (but do not remove) a random item
   {
       if(isEmpty())
           throw new java.util.NoSuchElementException("array is null");
       int a1=StdRandom.uniform(N);
       return a[a1];
   }
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
       return new RandomIterator();
   }
   private class RandomIterator implements Iterator<Item>
   {
       private Item[] b;
       private int i = -1;
       public RandomIterator()
       {
           b = (Item[]) new Object[N];
           for(int i=0; i<N; i++)  
               b[i] = a[i]; 
           StdRandom.shuffle(b);
       }
       public void remove() 
       {
       throw new java.lang.UnsupportedOperationException("don't remove");
       }
       public boolean hasNext(){return i!=N-1;}
       public Item next()
       {
           if(i==N-1)
               throw new java.util.NoSuchElementException("it's the end");
           return b[++i];
       }
   }
   public static void main(String[] args)   // unit testing (optional)
   {
       RandomizedQueue<String> rq = new RandomizedQueue<String> ();
       while(!StdIn.isEmpty())
       {
           String item = StdIn.readString();
           rq.enqueue(item);
           //if(!rq.isEmpty())
           //    StdOut.print(rq.dequeue()+' ');
       }
       Iterator<String> collection = rq.iterator();
       //StdOut.print(collection);
       while(collection.hasNext())
       {
           //StdOut.println("yes");
           String s = collection.next();
           StdOut.println(s);
       }
   }
}