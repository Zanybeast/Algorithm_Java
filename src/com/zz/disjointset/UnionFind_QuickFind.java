package com.zz.disjointset;

/**
 * @ClassName UnionFind_QuickFind
 * @Description TODO
 * @Author carl
 * @Date 2020/11/18 01:32
 * @Version 1.0
 **/
public class UnionFind_QuickFind extends UnionFind {

    public UnionFind_QuickFind(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        return parents[v];
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = parents[v1];
        int p2 = parents[v2];
        if (p1 == p2) return;

        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == p1)
                parents[i] = p2;
        }
    }


}
