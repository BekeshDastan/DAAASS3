package org.example;

public class DSU {
    int[] parent;
    int[] rank;

    DSU(int n){
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i<n; i++){
            parent[i] = i;
            rank[i]=1;
        }
    }

    public int find (int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) return;

        if (rank[a] < rank[b]) {
            parent[a] = b;
        } else if (rank[a] > rank[b]) {
            parent[b] = a;
        } else {
            parent[b] = a;
            rank[a]++;
        }
    }
}