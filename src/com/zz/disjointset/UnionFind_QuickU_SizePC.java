package com.zz.disjointset;

/**
 * @ClassName UnionFind_QuickU_SizePC
 * @Description TODO
 * @Author carl
 * @Date 2020/11/18 02:25
 * @Version 1.0
 **/
public class UnionFind_QuickU_SizePC extends UnionFind_QuickU_SizeOp {
    public UnionFind_QuickU_SizePC(int capacity) {
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
