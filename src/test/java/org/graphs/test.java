package org.graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class test {
    Prim prim;
    Kruskal kruskal;
    Parser.InputData data;
    Graph graph;

    @BeforeEach
    public void setup() {
        data = Parser.readInput();

        Parser.GraphInput g = data.graphs.get(0);
        graph = new Graph(g.nodes.toArray(new String[0]));
        for (Parser.EdgeInput e : g.edges) {
            graph.addEdge(e.from, e.to, e.weight);
        }
        prim = new Prim(graph);
        kruskal = new Kruskal(graph);
    }


    @Test
    public void checkWeigths(){
        assertEquals(prim.weight(),kruskal.weight());
    }

    @Test
    public void checkEdgesPrim(){
        int count = 0;
        for (Edge e : prim.edges()) count++;
        assertEquals(graph.V() - 1, count);
    }

    @Test
    public void checkEdgesKruskal(){
        int count = 0;
        for (Edge e : kruskal.edges()) count++;
        assertEquals(graph.V() - 1, count);
    }

    @Test
    public void checkAcyclicPrim() {
        boolean acyclic = true;
        UF uf = new UF(graph.V());
        for (Edge e : prim.edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) == uf.find(w)) {
                acyclic = false;
                break;
            }
            uf.union(v, w);
        }
        assertTrue(acyclic);
    }

    @Test
    public void checkAcyclicKruskal() {
        boolean acyclic = true;
        UF uf = new UF(graph.V());
        Kruskal kruskal = new Kruskal(graph);
        for (Edge e : kruskal.edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) == uf.find(w)) {
                acyclic = false;
                throw new AssertionError("MST contains a cycle!");
            }
            uf.union(v, w);
        }
        assertTrue(acyclic);

    }

    @Test
    public void checkConnectsPrim(){
        UF uf = new UF(graph.V());

        for (Edge e : prim.edges()) {
            int v = e.either();
            int w = e.other(v);
            uf.union(v, w);
        }

        int root = uf.find(0);
        boolean connected = true;

        for (int i = 1; i < graph.V(); i++) {
            if (uf.find(i) != root) {
                connected = false;
                break;
            }
        }
        assertTrue(connected);
    }

    @Test
    public void checkConnectsKruskal() {
        Kruskal kruskal = new Kruskal(graph);

        UF uf = new UF(graph.V());
        for (Edge e : kruskal.edges()) {
            int v = e.either();
            int w = e.other(v);
            uf.union(v, w);
        }

        int root = uf.find(0);
        boolean connected = true;

        for (int i = 1; i < graph.V(); i++) {
            if (uf.find(i) != root) {
                connected = false;
                break;
            }
        }

        assertTrue(connected);
    }

    @Test
    public void testDisconnectedGraph() {

        Graph graph = new Graph(new String[]{"A", "B", "C", "D"});
        graph.addEdge("A","B",1);
        graph.addEdge("C","D",2);

        Prim prim = new Prim(graph);
        Iterable<Edge> mstEdges = prim.edges();

        UF uf = new UF(graph.V());
        for (Edge e : mstEdges) {
            int v = e.either();
            int w = e.other(v);
            uf.union(v, w);
        }

        int root = uf.find(0);
        boolean connected = true;
        for (int i = 1; i < graph.V(); i++) {
            if (uf.find(i) != root) {
                connected = false;
                break;
            }
        }

        assertFalse(connected);
    }

    @Test
    public void testDisconnectedGraphKruskal() {
        Graph graph = new Graph(new String[]{"A", "B", "C", "D"});
        graph.addEdge("A","B",1);
        graph.addEdge("C","D",2);

        Kruskal kruskal = new Kruskal(graph);
        Iterable<Edge> mstEdges = kruskal.edges();

        UF uf = new UF(graph.V());
        for (Edge e : mstEdges) {
            int v = e.either();
            int w = e.other(v);
            uf.union(v, w);
        }

        int root = uf.find(0);
        boolean connected = true;
        for (int i = 1; i < graph.V(); i++) {
            if (uf.find(i) != root) {
                connected = false;
                break;
            }
        }

        assertFalse(connected);
    }

   @Test
   public void timePrim(){
       Prim prim1 = new Prim(graph);
       double time = prim1.getTime();
       assertTrue(time >= 0);
   }

   @Test
    public void timeKruskal(){
       Kruskal kruskal1 = new Kruskal(graph);
       double time = kruskal1.getTime();
       assertTrue(time >= 0);
   }

    @Test
    public void metricsPrim(){
        Prim prim1 = new Prim(graph);
        int unions = prim1.getUnions();
        int comparisons = prim1.getComparisons();
        int adds = prim1.getAdds();
        assertTrue(unions >= 0 && comparisons >= 0 && adds >= 0);
    }


    @Test
    public void metricsKruskal(){
       Kruskal kruskal1 = new Kruskal(graph);
       int unions = kruskal1.getUnions();
       int comparisons = kruskal1.getComparisons();
       int adds = kruskal1.getAdds();
       assertTrue(unions >= 0 && comparisons >= 0 && adds >= 0);
   }

   public void reproduciblePrim(){
       Prim prim1 = new Prim(graph);
       Prim prim2 = new Prim(graph);
       assertEquals(prim1.weight(), prim2.weight());
       assertEquals(prim1.numberEdges(), prim2.numberEdges());
   }

    public void reproducibleKruskal(){
        Kruskal kruskal1 = new Kruskal(graph);
        Kruskal kruskal2 = new Kruskal(graph);
        assertEquals(kruskal1.weight(), kruskal2.weight());
        assertEquals(kruskal1.numberEdges(), kruskal2.numberEdges());
    }






}