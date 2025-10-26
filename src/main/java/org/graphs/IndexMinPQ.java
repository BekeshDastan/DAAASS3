package org.graphs;

public class IndexMinPQ<Key extends Comparable <Key>> {
    private int N;
    private int[] pq;
    private int[] qp;
    private Key[] keys;


    public IndexMinPQ(int maxN) {
        keys = (Key[]) new Comparable[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for (int i = 0; i <= maxN; i++) {
            qp[i] = -1;
        }
    }

    public boolean isEmpty(){return N==0;};
    public boolean contains (int k) {
        return qp[k]!=-1;
    }
    public void insert(int k, Key key){
        N++;
        qp[k]=N;
        pq[N]=k;
        keys[k]=key;
        swim(N);
    }

    public int delMin(){
        int indexOfMin = pq[1];
        exch(1,N--);
        sink(1);
        qp[indexOfMin]=-1;
        keys[indexOfMin]=null;
        pq[N+1] = -1;
        return indexOfMin;
    }

    public Key min() {
        return keys[pq[1]];
    }

    public void decreaseKey(int k, Key key){
        keys[k]=key;
        swim(qp[k]);
        sink (qp[k]);
    }

    private boolean less (int i, int j){
        return keys[pq[i]].compareTo(keys[pq[j]]) <0;
    }

    private void exch (int i, int j){
        int swap = pq[i];
        pq[i]=pq[j];
        pq[j]=swap;
        qp[pq[i]]=i;
        qp[pq[j]]=j;
    }

    private void swim(int k){
        while(k>1 && less (k,k/2)){
            exch(k,k/2);
            k=k/2;
        }
    }

    private void sink (int k) {
        while (2*k <=N){
            int j=2*k;
            if (j<N  && less(j+1,j)) j++;
            if(!less(j,k)) break;
            exch(k,j);
            k=j;
        }
    }

}
