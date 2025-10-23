package org.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
    int V;
    List<Edge>[] adj;
    Graph (int V){
        this.V = V;
        adj = new LinkedList[V];
        for (int i=0; i<V; i++){
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int src, int dest, int weight){
        adj[src].add(new Edge(src,dest,weight));
        adj[dest].add(new Edge(dest,src,weight));
    }

    public void printGraph(){
        for (int i=0;i<V;i++){
            System.out.print("Vertex " + i + ": ");
            for (Edge e : adj[i]){
                System.out.println(" ");
                System.out.print(e.destination + " (" + e.weight + ")" );

            }
            System.out.println(" ");
        }

    }
}