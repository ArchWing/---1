import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;

public class Deque<Item> implements Iterable<Item> {
   
   private Node first;
   private Node last;
   private int N;
   
   private class Node
   {
       Item item;
       Node next;
       Node before;
   }
   
   public Deque()                           // construct an empty deque
   {
       first = null;
       last = null;
       N = 0;
   }
   public boolean isEmpty()                 // is the deque empty?
   {
       return N==0;
   }
   public int size()                        // return the number of items on the deque
   {
       return N;
   }
   public void addFirst(Item item)          // add the item to the front
   {
       if(item==null)
           throw new IllegalArgumentException ("item is null");
       Node old_first = first;
       first = new Node();
       first.item = item;
       first.next = old_first;
       first.before = null;
       if(isEmpty())
           last=first;
       else
           old_first.before = first;
       N++;
   }
   public void addLast(Item item)           // add the item to the end
   {
       if(item==null)
           throw new IllegalArgumentException ("item is null");
       Node old_last = last;
       last = new Node();
       last.item = item;
       last.before = old_last;
       last.next = null;
       if(isEmpty())
           first=last;
       else
           old_last.next = last;
       N++;
   }
   public Item removeFirst()                // remove and return the item from the front
   {
       if(N==0)
           throw new NoSuchElementException("the deque is null");
       Item item = first.item;
       first = first.next;
       N--;
       if(isEmpty())
           last = null;
       else
           first.before = null;
       
       return item;
   }
   public Item removeLast()                 // remove and return the item from the end
   {
       if(N==0)
           throw new NoSuchElementException("the deque is null");
       Item item = last.item;
       last = last.before;
       N--;
       if(isEmpty())
           first = null;
       else
           last.next = null;
       
       return item;
   }
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
       return new ForwardIterator();
   }
   private class ForwardIterator implements Iterator<Item>
   {
       private Node current = first;
       public boolean hasNext()
       {return current!=null;}
       public void remove()
       {
           throw new UnsupportedOperationException("don't use remove");
       }
       public Item next()
       {
           if(current==null)
               throw new NoSuchElementException("now is the end");
           Item item = current.item;
           current = current.next;
           return item;
       }
   }
   public static void main(String[] args)   // unit testing (optional)
   {
       Deque<String> dq = new Deque<String>();
       while(!StdIn.isEmpty())
       {
           String item = StdIn.readString();
           dq.addFirst(item);
           if(!dq.isEmpty())
               StdOut.print(dq.removeFirst()+" ");
       }
       
   }
}