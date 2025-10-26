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

    public void checkAcyclicKruskal() {

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

        assertFalse(connected, "MST should indicate disconnected graph");
    }

}