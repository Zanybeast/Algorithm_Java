package com.zz.graph;

import com.zz.disjointset.GenericUnionFind;

import java.nio.file.Path;
import java.util.*;

/**
 * @ClassName ListGraph
 * @Description TODO
 * @Author carl
 * @Date 2020/11/29 11:28
 * @Version 1.0
 **/
public class ListGraph<V, W> extends Graph<V, W> {
    public ListGraph() { }
    public ListGraph(WeightManager<W> weightManager) {
        super(weightManager);
    }

    private Map<V, Vertex<V, W>> vertices = new HashMap<>();
    private Set<Edge<V, W>> edges = new HashSet<>();
    private Comparator<Edge<V, W>> edgeComparator = (Edge<V, W> e1, Edge<V, W> e2) -> {
        return weightManager.compare(e1.weight, e2.weight);
    };

    @Override
    public int edgeSize() {
        return edges.size();
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    /*
     * @Description 添加顶点
     * @Date 12:31 2020/11/29
     */
    @Override
    public void addVertex(V v) {
        if (vertices.containsKey(v)) return;
        vertices.put(v, new Vertex<>(v));
    }

    /*
     * @Description 添加边, 默认权值为空
     * @Date 12:31 2020/11/29
     */
    @Override
    public void addEdge(V from, V to) { addEdge(from, to, null); }

    /*
     * @Description 添加带权值的边
     * @Date 12:30 2020/11/29
     */
    @Override
    public void addEdge(V from, V to, W weight) {
        Vertex<V, W> fromVertex = vertices.get(from);
        if (fromVertex == null) {
            fromVertex = new Vertex<>(from);
            vertices.put(from, fromVertex);
        }

        Vertex<V, W> toVertex = vertices.get(to);
        if (toVertex == null) {
            toVertex = new Vertex<>(to);
            vertices.put(to, toVertex);
        }

        Edge<V, W> edge = new Edge<>(fromVertex, toVertex);
        edge.weight = weight;
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);
    }

    /*
     * @Description 删除顶点
     * @Date 12:30 2020/11/29
     */
    @Override
    public void removeVertex(V v) {
        Vertex<V, W> vertex = vertices.remove(v);
        if (vertex == null) return;
        //将顶点的所有出度边删除
        for (Iterator<Edge<V, W>> iterator = vertex.outEdges.iterator(); iterator.hasNext();) {
            Edge<V, W> edge = iterator.next();
            edge.to.inEdges.remove(edge);
            iterator.remove();
            edges.remove(edge);
        }

        //将顶点的所有入度边删除
        for (Iterator<Edge<V, W>> iterator = vertex.inEdges.iterator(); iterator.hasNext();) {
            Edge<V, W> edge = iterator.next();
            edge.from.outEdges.remove(edge);
            iterator.remove();
            edges.remove(edge);
        }
    }

    /*
     * @Description 删除边
     * @Date 12:30 2020/11/29
     */
    @Override
    public void removeEdge(V from, V to) {
       Vertex<V, W> fromVertex = vertices.get(from);
       if (fromVertex == null) return;

       Vertex<V, W> toVertex = vertices.get(to);
       if (toVertex == null) return;

       Edge<V, W> edge = new Edge<>(fromVertex, toVertex);
       if (fromVertex.outEdges.remove(edge)) {
           toVertex.inEdges.remove(edge);
           edges.remove(edge);
       }
    }

    /**********************
     *  搜索-排序-最小生成树
     ***********************/
    /*
     * @Description 广度优先搜索 BFS
     * @Date 15:46 2020/11/29
     */
    @Override
    public void bfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        Vertex<V, W> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;

        Set<Vertex<V, W>> visitedVertices = new HashSet<>();
        Queue<Vertex<V, W>> queue = new LinkedList<>();
        queue.offer(beginVertex);
        visitedVertices.add(beginVertex);

        while (!queue.isEmpty()) {
            Vertex<V, W> vertex = queue.poll();
            if (visitor.visit(vertex.value)) return;

            for (Edge<V, W> edge: vertex.outEdges) {
                if (visitedVertices.contains(edge.to)) continue;
                queue.offer(edge.to);
                visitedVertices.add(edge.to);
            }
        }
    }
    
    /*
     * @Description 深度优先搜索
     * @Date 16:13 2020/11/29
     */
    @Override
    public void dfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        Vertex<V, W> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;

        Set<Vertex<V, W>> visitedVertices = new HashSet<>();
        Stack<Vertex<V, W>> stack = new Stack<>();

        stack.push(beginVertex);
        visitedVertices.add(beginVertex);
        if (visitor.visit(begin)) return;

        while (!stack.isEmpty()) {
            Vertex<V, W> vertex = stack.pop();

            for (Edge<V, W> edge :
                    vertex.outEdges) {
                if (visitedVertices.contains(edge.to)) continue;

                stack.push(edge.from);
                stack.push(edge.to);
                visitedVertices.add(edge.to);
                if (visitor.visit(edge.to.value)) return;

                break;
            }
        }
    }

    /*
     * @Description 拓扑排序
     * 将入度为 0 的元素放入列表结果中,然后从图中去掉这些顶点->重复操作, 直到找不到入度为 0 的顶点->如果
     * 结果列表元素与原顶点个数相同,则排序完成,否则图中有环
     * *************************************************
     * 翻译到代码实现就是,利用链表保存结果,然后使用队列遍历入度为 0 的元素->将入度不为 0 的元素保存到映射中,
     * 从队列出队元素,遍历其边,将与该队列直接相连的顶点入度减一,重新保存到映射中
     * @Date 16:30 2020/11/29
     */
    public List<V> topologicalSort() {
        List<V> list = new ArrayList<>();   //保存结果的链表
        Queue<Vertex<V, W>> queue = new LinkedList<>();     //保存入度为 0 的顶点队列
        Map<Vertex<V, W>, Integer> ins = new HashMap<>();   //保存每一个顶点的入度的映射

        vertices.forEach( (V v, Vertex<V, W> vertex) -> {
            int in = vertex.inEdges.size();
            if (in == 0) {
                queue.offer(vertex);
            } else {
                ins.put(vertex, in);
            }
        });

        while (!queue.isEmpty()) {
            Vertex<V, W> vertex = queue.poll();
            list.add(vertex.value);

            for (Edge<V, W> edge: vertex.outEdges) {
                int toCount = ins.get(edge.to) - 1;
                if (toCount == 0) {
                    queue.offer(edge.to);
                } else {
                    ins.put(edge.to, toCount);
                }
            }
        }

        return list;
    }
    
    /*
     * @Description 最小生成树
     * @Date 17:16 2020/11/29
     */

    @Override
    public Set<EdgeInfo<V, W>> mst() {
        return kruskal();
    }
    /*
     * @Description Prim 算法
     * 实现思路: 切分定理: 给定任意切分, 横切边中权值最小的边必然属于最小生成树
     * @Date 17:18 2020/11/29
     */
    private Set<EdgeInfo<V, W>> prim() {
        Iterator<Vertex<V, W>> it = vertices.values().iterator();
        if (!it.hasNext()) return null;

        Vertex<V, W> vertex = it.next();
        Set<EdgeInfo<V, W>>edgeInfos = new HashSet<>();
        Set<Vertex<V, W>> addedVertices = new HashSet<>();
        addedVertices.add(vertex);
        MinHeap<Edge<V, W>> heap = new MinHeap<>(vertex.outEdges, edgeComparator);
        int verticesSize = vertices.size();
        while (!heap.isEmpty() && addedVertices.size() < verticesSize) {
            Edge<V, W> edge = heap.remove();
            if (addedVertices.contains(edge.to)) continue;
            edgeInfos.add(edge.info());
            addedVertices.add(edge.to);
            heap.addAll(edge.to.outEdges);
        }
        return edgeInfos;
    }
    /*
     * @Description Kruskal 算法
     * 实现思路: 按照边的权重顺序从小到大将边加入生成树, 直到生成树中含有V - 1 条边为止(V 为顶点数量)
     * 若加入的边会与生成树形成环, 则不加入该边
     * 从第三条边开始可能会与生成树形成环
     * @Date 17:58 2020/11/29
     */
    private Set<EdgeInfo<V, W>> kruskal() {
        int edgeSize = vertices.size() - 1;
        if (edgeSize == -1) return null;
        Set<EdgeInfo<V, W>> edgeInfos = new HashSet<>();
        MinHeap<Edge<V, W>> heap = new MinHeap<>(edges, edgeComparator);
        GenericUnionFind<Vertex<V, W>> uf = new GenericUnionFind<>();
        vertices.forEach((V v, Vertex<V, W> vertex) -> {
            uf.makeSet(vertex);
        });
        while (!heap.isEmpty() && edgeInfos.size() < edgeSize) {
            Edge<V, W> edge = heap.remove();
            if (uf.isSame(edge.from, edge.to)) continue;
            edgeInfos.add(edge.info());
            uf.union(edge.from, edge.to);
        }
        return edgeInfos;
    }

    /**********************
     *  最短路径
     ***********************/
    @Override
    public Map<V, PathInfo<V, W>> shortestPath(V begin) {
//        return dijkstra(begin);
        return bellmanFord(begin);
    }
    @Override
    public Map<V, Map<V, PathInfo<V, W>>> shortestPath() {
        return floyd();
    }
    
    /*
     * @Description Dijkstra
     * @Date 21:42 2020/11/29
     */
    private Map<V, PathInfo<V, W>> dijkstra(V begin) {
        Vertex<V, W> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;

        Map<V, PathInfo<V, W>> selectedPaths = new HashMap<>();
        Map<Vertex<V, W>, PathInfo<V, W>> paths = new HashMap<>();
        paths.put(beginVertex, new PathInfo<>(weightManager.zero()));

        while (!paths.isEmpty()) {
            Map.Entry<Vertex<V, W>, PathInfo<V, W>> minEntry = getMinPath(paths);

            Vertex<V, W> minVertex = minEntry.getKey();
            PathInfo<V, W> minPath = minEntry.getValue();
            selectedPaths.put(minVertex.value, minPath);
            paths.remove(minVertex);

            for (Edge<V, W> edge: minVertex.outEdges) {
                if (selectedPaths.containsKey(edge.to.value)) continue;
                relaxForDijkstra(edge, minPath, paths);
            }
        }

        selectedPaths.remove(begin);
        return selectedPaths;
    }
    /*
     * @Description 给 Dijkstra 的松弛操作
     * @Date 21:51 2020/11/29
     */
    private void relaxForDijkstra(Edge<V, W> edge, PathInfo<V, W> fromPath, Map<Vertex<V, W>, PathInfo<V, W>> paths) {
        W newWeight = weightManager.add(fromPath.weight, edge.weight);

        PathInfo<V, W> oldPath = paths.get(edge.to);
        if(oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return;

        if (oldPath == null) {
            oldPath = new PathInfo<>();
            paths.put(edge.to, oldPath);
        } else {
            oldPath.edgeInfos.clear();
        }

        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
    }

    /*
     * @Description BellmanFord
     * @Date 22:05 2020/11/29
     */
    private Map<V, PathInfo<V, W>> bellmanFord(V begin) {
        Vertex<V, W> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;
        
        Map<V, PathInfo<V, W>> selectedPaths = new HashMap<>();
        selectedPaths.put(begin, new PathInfo<>(weightManager.zero()));
        
        int count = vertices.size() - 1;
        for (int i = 0; i < count; i++) {
            for (Edge<V, W> edge: edges) {
                PathInfo<V, W> fromPath = selectedPaths.get(edge.from.value);
                if (fromPath == null) continue;
                relax(edge, fromPath, selectedPaths);
            }
        }
        
        for (Edge<V, W> edge: edges) {
            PathInfo<V, W> fromPath = selectedPaths.get(edge.from.value);
            if (fromPath == null) continue;
            if (relax(edge, fromPath, selectedPaths)) {
                System.out.println("有负权环");
                return null;
            }
        }
        
        selectedPaths.remove(begin);
        return selectedPaths;
    }

    /*
     * @Description Floyd, 多源最短路径算法, 可以求出不止一个
     * @Date 22:16 2020/11/29
     */
    private Map<V, Map<V, PathInfo<V, W>>> floyd() {
        Map<V, Map<V, PathInfo<V, W>>> paths = new HashMap<>();

        for (Edge<V, W> edge: edges) {
            Map<V, PathInfo<V, W>> map = paths.get(edge.from.value);
            if (map == null) {
                map = new HashMap<>();
                paths.put(edge.from.value, map);
            }

            PathInfo<V, W> pathInfo = new PathInfo<>(edge.weight);
            pathInfo.edgeInfos.add(edge.info());
            map.put(edge.to.value, pathInfo);
        }

        vertices.forEach((V v2, Vertex<V, W> vertex2) -> {
            vertices.forEach((V v1, Vertex<V, W> vertex1) -> {
                vertices.forEach((V v3, Vertex<V, W> vertex3) -> {
                    if (v1.equals(v2) || v2.equals(v3) || v1.equals(v3)) return;

                    PathInfo<V, W> path12 = getPathInfo(v1, v2, paths);
                    if (path12 == null) return;

                    PathInfo<V, W> path23 = getPathInfo(v2, v3, paths);
                    if (path23 == null) return;

                    PathInfo<V, W> path13 = getPathInfo(v1, v3, paths);

                    W newWeight = weightManager.add(path12.weight, path23.weight);
                    if (path13 != null && weightManager.compare(newWeight, path13.weight) >= 0) return;

                    if (path13 == null) {
                        path13 = new PathInfo<V, W>();
                        paths.get(v1).put(v3, path13);
                    } else {
                        path13.edgeInfos.clear();
                    }

                    path13.weight = newWeight;
                    path13.edgeInfos.addAll(path12.edgeInfos);
                    path13.edgeInfos.addAll(path23.edgeInfos);
                });
            });
        });

        return paths;
    }
    /*
     * @Description 获取路径信息
     * @Date 22:23 2020/11/29
     */
    private PathInfo<V, W> getPathInfo(V from, V to, Map<V, Map<V, PathInfo<V, W>>> paths) {
        Map<V, PathInfo<V, W>> map = paths.get(from);
        return map == null ? null : map.get(to);
    }
    
    /*
     * @Description 通用松弛方法, 给 BellmanFord 和 Floyd 用
     * @Date 22:10 2020/11/29
     */
    private boolean relax(Edge<V, W> edge, PathInfo<V, W> fromPath, Map<V, PathInfo<V, W>> paths) {
        W newWeight = weightManager.add(fromPath.weight, edge.weight);

        PathInfo<V, W> oldPath = paths.get(edge.to.value);
        if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return false;

        if (oldPath == null) {
            oldPath = new PathInfo<>();
            paths.put(edge.to.value, oldPath);
        } else {
            oldPath.edgeInfos.clear();
        }

        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());

        return true;
    }



    
    /*
     * @Description 从路径中挑出最短路径出来
     * @Date 21:45 2020/11/29
     */
    private Map.Entry<Vertex<V, W>, PathInfo<V, W>> getMinPath(Map<Vertex<V, W>, PathInfo<V, W>> paths) {
        Iterator<Map.Entry<Vertex<V, W>, PathInfo<V, W>>> it = paths.entrySet().iterator();
        Map.Entry<Vertex<V, W>, PathInfo<V, W>> minEntry = it.next();
        while (it.hasNext()) {
            Map.Entry<Vertex<V, W>, PathInfo<V, W>> entry = it.next();
            if (weightManager.compare(entry.getValue().weight, minEntry.getValue().weight) < 0) {
                minEntry = entry;
            }
        }
        return minEntry;
    }

    /**********************
     *  顶点类
     ***********************/
    private static class Vertex<V, W> {
        V value;
        Set<Edge<V, W>> inEdges = new HashSet<>();
        Set<Edge<V, W>> outEdges = new HashSet<>();
        Vertex(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex<V, W> vertex = (Vertex<V, W>) o;
            return Objects.equals(value, vertex.value);
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : value.hashCode();
        }

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }

    /**********************
     *  边信息
     ***********************/
    private static class Edge<V, W> {
        Vertex<V, W> from;
        Vertex<V, W> to;
        W weight;

        public Edge(Vertex<V, W> from, Vertex<V, W> to) {
            this.from = from;
            this.to = to;
        }

        EdgeInfo<V, W> info() {
            return new EdgeInfo<>(from.value, to.value, weight);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<V, W> edge = (Edge<V, W>) o;
            return Objects.equals(from, edge.from) &&
                    Objects.equals(to, edge.to) ;
        }

        @Override
        public int hashCode() {
            return from.hashCode() * 31 + to.hashCode();
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }

    /**********************
     *  打印函数
     ***********************/
    public void print() {
        System.out.println("[顶点]---------------");
        vertices.forEach((V v, Vertex<V, W> vertex) -> {
            System.out.println(v);
            System.out.println("out-----------");
//            System.out.println(vertex.outEdges);
            vertex.outEdges.forEach((Edge<V, W> edge) -> {
                System.out.println(edge);
            });
            System.out.println("in-----------");
//            System.out.println(vertex.inEdges);
            vertex.inEdges.forEach((Edge<V, W> edge) -> {
                System.out.println(edge);
            });
        });

        System.out.println("[边]-------------------");
        edges.forEach((Edge<V, W> edge) -> {
            System.out.println(edge);
        });
    }
}
