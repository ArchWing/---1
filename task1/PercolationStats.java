import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import java.lang.Integer;
import java.lang.Math;
    
public class PercolationStats {
    
    private double[] result;
    private int T;
   
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   {
       if(n<=0||trials<=0)
            throw new IllegalArgumentException ("index<=0");
       
       T=trials;
       result=new double[trials];
       for(int i=0;i<trials;i++)
       {
           Percolation pl=new Percolation(n);
           int count=0;
           while(!pl.percolates())
           {
           int a1=StdRandom.uniform(n)+1;
           int a2=StdRandom.uniform(n)+1;
           if(pl.isOpen(a1,a2))
               continue;
           pl.open(a1,a2);
           count++;
           }
           result[i]=(double)count/(n*n);
       }
   }
   
   public double mean()                          // sample mean of percolation threshold
   {
       return StdStats.mean(result);
   }
   public double stddev()                        // sample standard deviation of percolation threshold
   {
       return StdStats.stddev(result);
   }
   
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   {
       return mean()-1.96*stddev()/Math.sqrt(T);
   }
   
   public double confidenceHi()                  // high endpoint of 95% confidence interval
   {
       return mean()+1.96*stddev()/Math.sqrt(T);
   }

   public static void main(String[] args)        // test client (described below)
   {
       int n=Integer.parseInt(args[0]);
       int t=Integer.parseInt(args[1]);
       PercolationStats ps=new PercolationStats(n,t);
       StdOut.println("mean    = "+ps.mean());
       StdOut.println("stddev  = "+ps.stddev());
       StdOut.printf("95%% confidence interval = [%f,%f]\n", ps.confidenceLo(),ps.confidenceHi());
   }
}