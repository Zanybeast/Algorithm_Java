package com.zz.disjointset;

/**
 * @ClassName UnionFind_QuickU_RankPS
 * @Description TODO
 * @Author carl
 * @Date 2020/11/18 02:20
 * @Version 1.0
 **/
public class UnionFind_QuickU_RankPS extends UnionFind_QuickU_RankOp {

    public UnionFind_QuickU_RankPS(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {
            int p = parents[v];
            parents[v] = parents[parents[v]];
            v = p;
        }
        return v;
    }
}
