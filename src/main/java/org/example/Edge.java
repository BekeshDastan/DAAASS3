package org.example;

public class Edge {
    int src,destination, weight;

    Edge(int s, int d, int w){
        this.src = s;
        this.weight = w;
        this.destination = d;
    }

    public int either() { return src; }

    public int other(int v) {
        if (v == src) return destination;
        else return src;
    }

    @Override
    public String toString() {
        return src + "-" + destination + " " + weight;
    }
}
