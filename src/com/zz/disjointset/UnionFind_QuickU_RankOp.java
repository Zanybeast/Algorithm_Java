package com.zz.disjointset;

/**
 * @ClassName UnionFind_QuickU_RankOp
 * @Description 对并查集进行优化, 维护一个树的高度数组, 将矮树并到高树上
 * @Author carl
 * @Date 2020/11/18 01:47
 * @Version 1.0
 **/
public class UnionFind_QuickU_RankOp extends UnionFind_QuickU {
    private int[] rank;

    public UnionFind_QuickU_RankOp(int capacity) {
        super(capacity);

        rank = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            rank[i] = 1;
        }
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);

        if (rank[p1] < rank[p2]) {
            parents[p1] = p2;
        } else if (rank[p1] > rank[p2]) {
            parents[p2] = p1;
        } else {
            parents[p1] = p2;
            rank[p2] += 1;
        }
    }
}
