import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.lang.Math;
import java.util.Comparator; 


public class Board {
    
    private int n;
    private int[] board;
    //private int[] twin;
    
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    {
        if(blocks == null)
            throw new java.lang.IllegalArgumentException();
            
        n = blocks.length;
        board = new int[n*n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
        {
            board[i*n+j]=blocks[i][j];
        }
    }
    
    public int dimension()                 // board dimension n
    {
        return n;
    }
    public int hamming()                   // number of blocks out of place
    {
        int size = board.length;
        int number = 0;
        for(int i=0;i<size-1;i++)
        {
            if(board[i]!=i+1)
                number++;                
        }
        return number;
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int size = board.length;
        int number = 0;
        for(int i=0;i<size;i++)
        {
            if(i<size-1)
            {
                if(board[i]==0)
                    continue;
                else if(board[i]!=i+1){
                    int r = i/n;
                    int c = i%n;
                    number+=Math.abs((board[i]-1)/n-r)+Math.abs((board[i]-1)%n-c);
                }
            }
            else
            {
                if(board[i]!=0){
                    int r = i/n;
                    int c = i%n;
                    number+=Math.abs((board[i]-1)/n-r)+Math.abs((board[i]-1)%n-c);
                }
            }
        }
        return number;
    }
    
    public boolean isGoal()                // is this board the goal board?
    {
        return hamming()==0;
    }
    
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] twin = new int[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
        {
            twin[i][j]=board[i*n+j];
        }
        
        if ((twin[0][0] == 0) || (twin[0][1] == 0)) 
        {
            int temp = twin[1][1];
            twin[1][1]=twin[1][0];
            twin[1][0]=temp;            
        }
        else 
        {
            int temp = twin[0][0];
            twin[0][0]=twin[0][1];
            twin[0][1]=temp;            
        }
        
        return new Board(twin);
    }
    
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n) return false;
        for (int i=0;i<n*n;i++)
        {
            if(this.board[i]!=that.board[i])
                return false;
        }
        return true;
        
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        MinPQ<Board> pq = new MinPQ<Board> (new Comparator<Board>() {  
            public int compare(Board o1, Board o2) {  
                if (o1.manhattan() < o2.manhattan()) return -1;  
               else if (o1.manhattan() == o2.manhattan()) return 0;  
               else return 1;  
            }  
        });
        
        int prei = 0;
        int prej = 0;
        int[][] clone = new int[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
        {
            clone[i][j]=board[i*n+j];
            if(board[i*n+j]==0)
            {
                prei = i;
                prej = j;
            }
        }
        
        if(prej!=0)
        {
            int temp = clone[prei][prej-1];
            clone[prei][prej] = temp;
            clone[prei][prej-1] = 0;
            pq.insert(new Board(clone));
            
            //recover
            clone[prei][prej] = 0;
            clone[prei][prej-1] = temp;
        }
        
        if(prej!=n-1)
        {
            int temp = clone[prei][prej+1];
            clone[prei][prej] = temp;
            clone[prei][prej+1] = 0;
            pq.insert(new Board(clone));
            
            //recover
            clone[prei][prej] = 0;
            clone[prei][prej+1] = temp;
        }
        
        if(prei!=0)
        {
            int temp = clone[prei-1][prej];
            clone[prei][prej] = temp;
            clone[prei-1][prej] = 0;
            pq.insert(new Board(clone));
            
            clone[prei][prej] = 0;
            clone[prei-1][prej] = temp;
        }
        
        if(prei!=n-1)
        {
            int temp = clone[prei+1][prej];
            clone[prei][prej] = temp;
            clone[prei+1][prej] = 0;
            pq.insert(new Board(clone));
            
            clone[prei][prej] = 0;
            clone[prei+1][prej] = temp;
        }
        
        return pq;  
    }
    
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i*n+j]));
            }
            s.append("\n");
        }
    return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
         // read in the board specified in the filename
         In in = new In(args[0]);
         int n = in.readInt();
         int[][] tiles = new int[n][n];
         for (int i = 0; i < n; i++) {
             for (int j = 0; j < n; j++) {
                 tiles[i][j] = in.readInt();
             }
         }
 
         // solve the slider puzzle
         Board initial = new Board(tiles);
         StdOut.printf("hamming:%d manhattan:%d \n", initial.hamming(),
             initial.manhattan());
        StdOut.println("dim:" + initial.dimension());
         StdOut.println(initial.toString());
         StdOut.println("goal:" + initial.isGoal());
        StdOut.println("twin:\n" + initial.twin().toString()); 
        StdOut.println("neighbours:");
 
         for (Board s : initial.neighbors()) 
         {
             StdOut.println(s.toString());
         }     
        
    }
}