import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Stack;

public class Solver {
    
    private Node currentnode;
    private boolean solvable;
    
    // at first define the search node for this game
    private class Node implements Comparable<Node>
    {
        private Board current;
        private Node prev;
        private int moves;
        private boolean istwin;
        private int priority;
            
        public Node(Board current, Node prev, int moves, boolean istwin)
        {
            this.current = current;
            this.prev = prev;
            this.moves = moves;
            this.istwin = istwin;
            this.priority = current.manhattan()+moves;
        }
        
        public int compareTo(Node that)
        {
            int priority1 = this.priority;
            int priority2 = that.priority;
            if(priority1==priority2)
                return 0;
            else if(priority1<priority2)
                return -1;
            else return 1;
        }
    }
    
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if(initial == null)
            throw new java.lang.IllegalArgumentException(); 
        
        MinPQ<Node> queue = new MinPQ<Node>();
        solvable = true;
        
        queue.insert(new Node(initial, null, 0, false));
        queue.insert(new Node(initial.twin(), null, 0, true));
        
        while(true)
        {
            Node process = queue.delMin();
            
            //if(!process.istwin)
                               
                
            if(process.current.isGoal())
            {
                if(process.istwin)
                    solvable=false;
                else
                    currentnode = process; 
                break;
            }
            
            for(Board neighbor : process.current.neighbors())
            {
                if((process.prev==null)||(!process.prev.current.equals(neighbor)))
                    queue.insert(new Node(neighbor, process, process.moves+1, process.istwin));
            }
        }
    }
        
    
    public boolean isSolvable()            // is the initial board solvable?
    {
        return solvable;
    }
    
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if(isSolvable())
            return currentnode.moves;
        else 
            return -1;
    }
    
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if(!isSolvable())
            return null;
        
        Stack<Board> solutions = new Stack<Board>();
        Node node = currentnode;
        
        while(node.prev!=null)
        {
            solutions.push(node.current);
            node = node.prev;
        }
        
        solutions.push(node.current);
        
        Stack<Board> solutions2 = new Stack<Board>();
        while(!solutions.empty())
        {
            solutions2.push(solutions.pop());
        }
        return solutions2;
    }
    
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
    }
}