package org.example;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        DSU dsu = new DSU(5);
        dsu.union(0, 1);
        dsu.union(1,2);
        dsu.union(3, 4);

        System.out.println(dsu.find(2));
        System.out.println(dsu.find(0) == dsu.find(2));
    }
}