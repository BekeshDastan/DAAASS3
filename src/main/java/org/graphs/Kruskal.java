package org.graphs;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Kruskal {
    private static final double FLOATING_POINT_EPSILON = 1.0E-12;
    private double weight;
    private Queue<Edge> mst = new LinkedList<Edge>();
    private double time = 0;
    private int operations = 0;


    public Kruskal(Graph G){
        long start = System.nanoTime();
        Edge[] edges = new Edge[G.E()];
        int t=0;
        for (Edge e : G.edges()){
            edges[t++]=e;
        }
        Arrays.sort(edges);

        UF uf = new UF(G.V());
        for (int i=0;i<G.E() && mst.size()< G.V()-1;i++){
            Edge e = edges[i];
            operations++;
            int v = e.either();
            int w = e.other(v);
            operations+=2;

            if (uf.find(v) != uf.find(w)){
                operations+=2;
                uf.union(v,w);
                operations++;
                mst.add(e);
                operations++;
                weight += e.weight;
                operations++;
            }
        }
        assert check(G);
        long end = System.nanoTime();
        time = (end-start)/1_000_000.0;
    }


    public Iterable<Edge> edges(){return mst;}

    public double weight(){return weight;}

    public double getTime(){return time;}
    public int getOperations() { return operations; }


    private boolean check(Graph G) {
        double total = 0.0;
        for (Edge e : edges()) {
            total +=e.weight();
        }

        if (Math.abs(total - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, weight());
            return false;
        }

        UF uf = new UF(G.V());
        for (Edge e : edges()){
            int v=e.either(), w=e.other(v);
            if(uf.find(v) == uf.find(w)){
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v,w);
        }

        for (Edge e : G.edges()){
            int v=e.either(), w = e.other(v);
            if (uf.find(v) != uf.find(w)){
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        for (Edge e : edges()) {

            uf = new UF(G.V());
            for (Edge f : mst) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (uf.find(x) != uf.find(y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }
        }

            return true;
    }





}
