package com.zz.disjointset;

/**
 * @ClassName UnionFind_QuickU_RankPH
 * @Description TODO
 * @Author carl
 * @Date 2020/11/18 02:22
 * @Version 1.0
 **/
public class UnionFind_QuickU_RankPH extends UnionFind_QuickU_RankOp {
    public UnionFind_QuickU_RankPH(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {
            parents[v] = parents[parents[v]];
            v = parents[v];
        }
        return v;
    }
}
