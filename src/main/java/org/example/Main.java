package org.example;

public class Main {
    public static void main(String[] args) {
        Graph g = new Graph(6);
        g.addEdge(0,2,3);
        g.addEdge(1,5,7);
        g.addEdge(2,3,4);
        g.addEdge(3,4,1);
        g.addEdge(4,5,3);

        g.printGraph();
    }
}