package org.example;

public class Main {
    public static void main(String[] args) {

        Graph G = new Graph(5);
        G.addEdge(0, 1, 2);
        G.addEdge(0, 3, 6);
        G.addEdge(1, 2, 3);
        G.addEdge(1, 3, 8);
        G.addEdge(1, 4, 5);
        G.addEdge(2, 4, 7);
        G.addEdge(3, 4, 9);

        G.printGraph();

        Prim mst = new Prim(G);

        System.out.println("Edges in MST:");
        for (Edge e : mst.edges()) {
            System.out.println(e);
        }

        System.out.println("Total weight: " + mst.weight());
    }
}
