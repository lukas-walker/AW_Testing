import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException
    {
        // Create a new Scanner object for reading the input
        //Scanner sc = new Scanner(System.in);

        Scanner sc = new Scanner(new File("example.in"));

        // Read the number of testcases to follow
        int t = sc.nextInt();

        //Iterate over the testcases and solve the problem
        for (int i = 0; i < t; i++) {
            int n = sc.nextInt();
            int m = sc.nextInt();

            AdjacencyList adj = new AdjacencyList(n);

            for (int j = 0; j < m; j++)
            {
                int v = sc.nextInt();
                int u = sc.nextInt();

                adj.addEdge(v, u);
            }

            testcase(adj);

        }
    }


    public static void testcase(AdjacencyList adj)
    {
        
    }
}




/*
    Stores an extended Adjacency List
 */
class AdjacencyList {

    private List<Edge>[] adj;

    public AdjacencyList(int n)
    {
        adj = (List<Edge>[]) new List[n];
        for (int i = 0; i < n; i++)
            adj[i] = new ArrayList<Edge>();
    }

    public void addEdge(int v, int u)
    {
        if (getEdge(v,u) != null) return; //already exists

        Edge e1 = new Edge(v, u);
        Edge e2 = new Edge(u, v);
        e1.same = e2;
        e2.same = e1;

        adj[v].add(e1);
        adj[u].add(e2);
    }

    public void removeEdge(int v, int u)
    {
        if (getEdge(v, u) == null) return; //doesn't exist

        Edge eRemove = null;

        for(Edge e : adj[v])
        {
            if(e.u == u) eRemove = e;
        }

        adj[v].remove(eRemove);
    }

    public Edge getEdge(int v, int u)
    {
        for(Edge e : adj[v])
        {
            if(e.u == u)
            {
                return e;
            }
        }
        return null;
    }

}

class Edge {
    int v;
    int u;
    public Edge same;

    public Edge(int v, int u)
    {
        this.v = v;
        this.u = u;
    }
}
