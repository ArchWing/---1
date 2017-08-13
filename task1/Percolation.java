import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;
    
public class Percolation{
    private WeightedQuickUnionUF wuf;
    private int N;
    private int[] white;
 
    public Percolation(int n) 
    {
        if (n<=0)
            throw new IllegalArgumentException ("index<=0");
        N=n;
        
        white=new int[n*n+2];
        white[0]=1;
        white[n*n+1]=1;
        
        wuf=new WeightedQuickUnionUF(n*n+2);
        for(int i=1;i<=n;i++)
        {
            wuf.union(0,i);
            wuf.union(n*n+1,n*n-i+1);
        }
        
    }
    
    public void open(int row, int col)   // open site (row, col) if it is not open already
    {
        int index=(row-1)*N+col;
        
        if(row<=0||row>N||col<=0||col>N)
            throw new IndexOutOfBoundsException ("index out of bounds");
        
        white[index]=1;
        
        if(row>1)
        {
            int index1=index-N;
            if(isOpen(row-1,col))
                wuf.union(index,index1);
        }
            
        if(row<N)
        {
            int index2=index+N;
            if(isOpen(row+1,col))
                wuf.union(index,index2);
        }
        
        if(col>1)
        {
            if(isOpen(row,col-1))
                wuf.union(index,index-1);
        }
        
        if(col<N)
        {
            if(isOpen(row,col+1))
                wuf.union(index,index+1);
        }
    }
    
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        int index=(row-1)*N+col;
        if(row<=0||row>N||col<=0||col>N)
            throw new IndexOutOfBoundsException ("index out of bounds");
            
        if(white[index]==1)
            return true;
        else
            return false;
        
    }
    
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        int index=(row-1)*N+col;
        if(row<=0||row>N||col<=0||col>N)
            throw new IndexOutOfBoundsException ("index out of bounds");
        
        return wuf.connected(0,index)&&isOpen(row,col);
    }
    public int numberOfOpenSites()      // number of open sites
    {
        int count=0;
        for(int i=1;i<=N*N;i++)
        {
            if(white[i]==1)
                count++;
        }
        return count;
    }
    public boolean percolates()            // does the system percolate?
    {
        if (N==1)
            return white[1]==1;
        else
            return wuf.connected(0,N*N+1);
    }

    public static void main(String[] args)
    {
        int n = StdIn.readInt();
        Percolation pl = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            pl.open(p,q);
        }
        StdOut.println(pl.percolates());
    }
}
    