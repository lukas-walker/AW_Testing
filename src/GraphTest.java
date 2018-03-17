import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GraphTest {

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

            System.out.println();
            System.out.println("testcase " + i);
            testcase(adj);
            System.out.println();

        }
    }


    public static void testcase(AdjacencyList adj)
    {
        int n = adj.n;

        if(n <= 0) return;

        List<Integer> a = new ArrayList<Integer>();
        List<Integer> b = new ArrayList<Integer>();

        int[]       set     = new int[n];
        boolean[]   visited = new boolean[n];

        Queue<Integer> q = new PriorityQueue<Integer>();

        for(int i = 0; i < n; i++)
        {
            if(set[i] == 0)
            {
                q.add(i);
                a.add(i);
                set[i] = 1;

                while(!q.isEmpty())
                {
                    int v = q.poll();

                    for(Edge e : adj.getNeighbors(v))
                    {
                        if (set[e.u] == set[v])  //not bipartite
                        {
                            System.out.println("Not bipartite!");
                            return;
                        }
                        else
                        {
                            if (set[e.u] != 0) continue;

                            if(set[v] == 1) //neighbors go to set b
                            {
                                set[e.u] = 2;
                                b.add(e.u);
                            }
                            else //neighbors go to set a
                            {
                                set[e.u] = 1;
                                a.add(e.u);
                            }

                            q.add(e.u);
                        }
                    }
                }


            }
        }

        System.out.println("a ["+a.size()+"]: "+Arrays.toString(a.toArray()));
        System.out.println("b ["+b.size()+"]: "+Arrays.toString(b.toArray()));
    }
}




/*
    Stores an extended Adjacency List
 */
class AdjacencyList {

    private List<Edge>[] adj;
    int n;

    public AdjacencyList(int n)
    {
        this.n = n;
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

        if(!(eRemove == null))
        {
            adj[v].remove(eRemove);
            adj[u].remove(eRemove.same);
        }

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

    public List<Edge> getNeighbors(int v)
    {
        if (v < 0 || v >= adj.length) return null;

        return adj[v];
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
