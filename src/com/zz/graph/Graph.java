package com.zz.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Graph
 * @Description TODO
 * @Author carl
 * @Date 2020/11/19 11:19
 * @Version 1.0
 **/
public abstract class Graph<V, W> {
    protected WeightManager<W> weightManager;

    public Graph() {}
    public Graph(WeightManager<W> weightManager) {
        this.weightManager = weightManager;
    }

    public abstract int edgeSize();
    public abstract int verticesSize();

    public abstract void addVertex(V v);
    public abstract void addEdge(V from, V to);
    public abstract void addEdge(V from, V to, W weight);

    public abstract void removeVertex(V v);
    public abstract void removeEdge(V from, V to);

    public interface WeightManager<W> {
        int compare(W w1, W w2);
        W add(W w1, W w2);
        W zero();
    }

    public interface VertexVisitor<V> {
        boolean visit(V v);
    }
    
    /**********************
     *  广度优先搜索 BFS/深度优先搜索 DFS
     ***********************/
    public abstract void bfs(V begin, VertexVisitor<V> visitor);
    public abstract void dfs(V begin, VertexVisitor<V> visitor);
    /**********************
     *  拓扑排序
     ***********************/
    public abstract List<V> topologicalSort();
    /**********************
     *  最小生成树
     ***********************/
    public abstract Set<EdgeInfo<V, W>> mst();
    /**********************
     *  最短路径
     ***********************/
    public abstract Map<V, PathInfo<V, W>> shortestPath(V begin);

    public abstract Map<V, Map<V, PathInfo<V, W>>> shortestPath();

    /**********************
     *  路径信息, 包含走过哪条路, 经过哪条边
     ***********************/
    public static class PathInfo<V, W> {
        protected W weight;
        protected List<EdgeInfo<V, W>> edgeInfos = new LinkedList<>();

        public PathInfo() {

        }

        public PathInfo(W weight) {
            this.weight = weight;
        }

        public W getWeight() {
            return weight;
        }

        public void setWeight(W weight) {
            this.weight = weight;
        }

        public List<EdgeInfo<V, W>> getEdgeInfos() {
            return edgeInfos;
        }

        public void setEdgeInfos(List<EdgeInfo<V, W>> edgeInfos) {
            this.edgeInfos = edgeInfos;
        }

        @Override
        public String toString() {
            return "PathInfo{" +
                    "weight=" + weight +
                    ", edgeInfos=" + edgeInfos +
                    '}';
        }
    }

    /**********************
     *  边信息,包含起点, 终点, 权重等信息
     ***********************/
    public static class EdgeInfo<V, W> {
          private V from;
          private V to;
          private W weight;

          public EdgeInfo(V from, V to, W weight) {
              this.from = from;
              this.to = to;
              this.weight = weight;
          }

        public V getFrom() {
            return from;
        }

        public void setFrom(V from) {
            this.from = from;
        }

        public V getTo() {
            return to;
        }

        public void setTo(V to) {
            this.to = to;
        }

        public W getWeight() {
            return weight;
        }

        public void setWeight(W weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "EdgeInfo{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }
}
