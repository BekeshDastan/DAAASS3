package org.graphs;

public class Edge implements Comparable<Edge> {
    int src,destination, weight;
    String[] names;

    Edge(int s, int d, int w, String[] names){
        this.src = s;
        this.weight = w;
        this.destination = d;
        this.names=names;
    }

    public int either() { return src; }

    public int other(int v) {
        if (v == src) return destination;
        else return src;
    }
    public String getSrcName() {
        return names[src];
    }

    public String getDestName() {
        return names[destination];
    }
    public double weight(){
        return  weight;
    }

    @Override
    public String toString() {
        return names[src] + "-" + names[destination] + " (" + weight + ")";
    }

    @Override
    public int compareTo(Edge that) {
        return Integer.compare(this.weight, that.weight);
    }
}
