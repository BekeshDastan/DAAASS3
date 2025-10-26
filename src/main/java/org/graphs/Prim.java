package org.graphs;

import java.util.Queue;
import java.util.LinkedList;

public class Prim {
    private static final double FLOATING_POINT_EPSILON = 1.0E-12;
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;

    private int comparisons = 0;
    private int finds = 0;
    private int unions = 0;
    private int adds = 0;

    private double time = 0;

    public Prim(Graph G){
        long start = System.nanoTime();
        edgeTo= new Edge[G.V()];
        distTo= new double[G.V()];
        marked = new boolean [G.V()];
        pq = new IndexMinPQ<Double>(G.V());

        for (int v=0; v<G.V();v++){
            distTo[v]= Double.POSITIVE_INFINITY;
        }


        for (int v=0; v<G.V();v++){
            if (!marked[v]) prim(G,v);
        }
        assert check(G);

        long end = System.nanoTime();
        time = (end - start)/ 1_000_000.0;
    }

    private void prim (Graph G, int s){
        distTo[s] = 0.0;
        pq.insert(s,distTo[s]);
        while (!pq.isEmpty()){
            int v = pq.delMin();
            adds++;
            scan(G,v);
        }
    }


    private void scan (Graph G, int v){
        marked[v] = true;
        for (Edge e: G.adj(v)){
            int w = e.other(v);

            if (marked[w]) continue;
            comparisons++;

            if (e.weight < distTo[w] ) {
                distTo[w]=e.weight;
                edgeTo[w]=e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else pq.insert(w,distTo[w]);
            }
        }
    }

    public double weight() {
        double total_weight = 0;
        for (Edge e : edges()) {
            total_weight += e.weight;
        }
        return total_weight;
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new LinkedList<>();
        for (Edge e : edgeTo) {
            if (e != null) mst.add(e);
        }
        return mst;
    }

    public int numberEdges(){
        int countEdges = 0;
        for (Edge e : edgeTo) {
            if (e != null) countEdges++;
        }
        return countEdges;
    }




    public double getTime() { return time; }

    public int getComparisons() { return comparisons; }
    public int getAdds() { return adds; }
    public int getFinds() { return finds; }
    public int getUnions() { return unions; }

    private boolean check(Graph G) {

        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight;
        }

        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
        }

        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) == uf.find(w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        for (Edge e : edgeTo) {
            if (e == null) continue;
            int v = e.either(), w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        for (Edge e : edges()) {
            if (e == null) continue;
            uf = new UF(G.V());
            for (Edge f : edges()) {
                if (f == null) continue;
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

            for (Edge f : edgeTo) {
                if (f == null) continue;
                int x = f.either(), y = f.other(x);
                if (uf.find(x) != uf.find(y)) {
                    if (f.weight < e.weight) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }


        }

        return true;
    }
}
