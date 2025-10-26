package org.graphs;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Graph {

    int V;
    List<Edge>[] adj;
    String[] names;
    private Map<String, Integer> nameToIndex;
    private int E = 0;

    Graph(String[] nodes){
        this.V = nodes.length;
        this.names = nodes;
        adj = new LinkedList[V];
        for (int i=0; i<V; i++){
            adj[i] = new LinkedList<>();
        }
        nameToIndex = new HashMap<>();
        for (int i = 0; i < V; i++) {
            nameToIndex.put(nodes[i], i);
        }
    }

    public void addEdge(String src, String dest, int weight) {
        if (!nameToIndex.containsKey(src)) {
            throw new IllegalArgumentException("Node not found: " + src);
        }
        if (!nameToIndex.containsKey(dest)) {
            throw new IllegalArgumentException("Node not found: " + dest);
        }

        E++;

        int u = nameToIndex.get(src);
        int v = nameToIndex.get(dest);

        Edge e1 = new Edge(u, v, weight, names);
        Edge e2 = new Edge(v, u, weight, names);


        adj[u].add(e1);
        adj[v].add(e2);
    }


    public void printGraph() {
        for (int i = 0; i < V; i++) {
            System.out.print(names[i] + ": ");
            for (Edge e : adj[i]) {
                System.out.print(e.toString() + "  ");
            }
            System.out.println();
        }
    }

    public int V() {
        return V;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public int E() {return E;}

    public Iterable<Edge> edges() {
        List<Edge> allEdges = new LinkedList<>();
        for (int u = 0; u < V; u++) {
            for (Edge e : adj[u]) {
                int v = e.other(u);
                if (v > u) {
                    allEdges.add(e);
                }
            }
        }
        return allEdges;
    }
}
