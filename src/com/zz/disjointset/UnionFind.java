package com.zz.disjointset;

/**
 * @ClassName UnionFind
 * @Description TODO
 * @Author carl
 * @Date 2020/11/18 00:23
 * @Version 1.0
 **/
public abstract class UnionFind {
    protected int[] parents;

    public UnionFind(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must be >= 0");
        }

        parents = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            parents[i] = i;
        }
    }

    public abstract int find(int v);

    public abstract void union(int v1, int v2);

    public boolean isSame(int v1, int v2) {
        return find(v1) == find(v2);
    }

    protected void rangeCheck(int v) {
        if (v < 0 || v >= parents.length) {
            throw new IllegalArgumentException("index out of bounds");
        }
    }
}
