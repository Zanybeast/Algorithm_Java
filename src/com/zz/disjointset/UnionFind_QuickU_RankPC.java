package com.zz.disjointset;

/**
 * @ClassName UnionFind_QuickU_RankC
 * @Description TODO
 * @Author carl
 * @Date 2020/11/18 01:53
 * @Version 1.0
 **/
public class UnionFind_QuickU_RankPC extends UnionFind_QuickU_RankOp {
    public UnionFind_QuickU_RankPC(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        if (parents[v] != v) {
            parents[v] = find(parents[v]);
        }
        return parents[v];
    }
}
