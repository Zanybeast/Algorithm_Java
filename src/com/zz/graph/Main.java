package com.zz.graph;

import com.zz.test.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Main
 * @Description TODO
 * @Author carl
 * @Date 2020/11/29 11:57
 * @Version 1.0
 **/
public class Main {
	/*
	 * @Description 自定义权值类, 权值管理
	 * @Date 15:57 2020/11/29
	 */
	static Graph.WeightManager<Double> weightManager = new Graph.WeightManager<Double>() {
		@Override
		public int compare(Double w1, Double w2) {
			return w1.compareTo(w2);
		}

		@Override
		public Double add(Double w1, Double w2) {
			return w1 + w2;
		}

		@Override
		public Double zero() {
			return 0.0;
		}
	};

	/*
	 * @Description 生成有向图的方法
	 * @Date 15:56 2020/11/29
	 */
	private static Graph<Object, Double> directedGraph(Object[][] data) {
		Graph<Object, Double> graph = new ListGraph<>(weightManager);
		for (Object[] edge : data) {
			if (edge.length == 1) {
				graph.addVertex(edge[0]);
			} else if (edge.length == 2) {
				graph.addEdge(edge[0], edge[1]);
			} else if (edge.length == 3) {
				double weight = Double.parseDouble(edge[2].toString());
				graph.addEdge(edge[0], edge[1], weight);
			}
		}
		return graph;
	}

	/*
	 * @Description 生成无向图
	 * @Date 16:00 2020/11/29
	 */
	private static Graph<Object, Double> undirectedGraph(Object[][] data) {
		Graph<Object, Double> graph = new ListGraph<>(weightManager);
		for (Object[] edge : data) {
			if (edge.length == 1) {
				graph.addVertex(edge[0]);
			} else if (edge.length == 2) {
				graph.addEdge(edge[0], edge[1]);
				graph.addEdge(edge[1], edge[0]);
			} else if (edge.length == 3) {
				double weight = Double.parseDouble(edge[2].toString());
				graph.addEdge(edge[0], edge[1], weight);
				graph.addEdge(edge[1], edge[0], weight);
			}
		}
		return graph;
	}

    public static void main(String[] args) {
		testMultiSp();
//		testSp();
//		testMst();
//		testTopo();
//		testDFS();
//		testBFS();
//    	testRemove();
//        testAdd();
    }

	static void testMultiSp() {
		Graph<Object, Double> graph = directedGraph(Data.NEGATIVE_WEIGHT1);
		Map<Object, Map<Object, Graph.PathInfo<Object, Double>>> sp = graph.shortestPath();
		sp.forEach((Object from, Map<Object, Graph.PathInfo<Object, Double>> paths) -> {
			System.out.println(from + "---------------------");
			paths.forEach((Object to, Graph.PathInfo<Object, Double> path) -> {
				System.out.println(to + " - " + path);
			});
		});
	}

	static void testSp() {
		Graph<Object, Double> graph = directedGraph(Data.NEGATIVE_WEIGHT2);
		Map<Object, Graph.PathInfo<Object, Double>> sp = graph.shortestPath(0);
		if (sp == null) return;
		sp.forEach((Object v, Graph.PathInfo<Object, Double> path) -> {
			System.out.println(v + " - " + path);
		});
	}

    static void testMst() {
		Graph<Object, Double> graph = undirectedGraph(Data.MST_01);
		Set<Graph.EdgeInfo<Object, Double>> infos = graph.mst();
		for (Graph.EdgeInfo<Object, Double> info: infos) {
			System.out.println(info);
		}
	}

	static void testTopo() {
		Graph<Object, Double> graph = directedGraph(Data.TOPO);
		List<Object> list = graph.topologicalSort();
		System.out.println(list);
	}

	static void testBfs() {
		Graph<Object, Double> graph = directedGraph(Data.BFS_02);
		graph.bfs(0, (Object v) -> {
			System.out.println(v);
			return false;
		});
	}

    public static void testDFS() {
		Graph<Object, Double> graph = directedGraph(Data.DFS_02);
		graph.dfs("a", (Object v) -> {
			System.out.println(v);
			return false;
		});
	}

    public static void testBFS() {
    	Graph<Object, Double> graph = directedGraph(Data.BFS_02);
    	graph.bfs(0, (Object v) -> {
			System.out.print("_" + v + "_");
			return false;
		});
	}

    public static void testAdd() {
        ListGraph<String, Integer> graph = new ListGraph<>();

        graph.addEdge("V0", "V1");
		graph.addEdge("V1", "V0");

		graph.addEdge("V0", "V2");
		graph.addEdge("V2", "V0");

		graph.addEdge("V0", "V3");
		graph.addEdge("V3", "V0");

		graph.addEdge("V1", "V2");
		graph.addEdge("V2", "V1");

		graph.addEdge("V2", "V3");
		graph.addEdge("V3", "V2");

		graph.print();
    }

    public static void testRemove() {
		ListGraph<String, Integer> graph = new ListGraph<>();

		graph.addEdge("V1", "V0", 9);
		graph.addEdge("V1", "V2", 3);
		graph.addEdge("V2", "V0", 2);
		graph.addEdge("V2", "V3", 5);
		graph.addEdge("V3", "V4", 1);
		graph.addEdge("V0", "V4", 6);

//		graph.removeEdge("V0", "V4");
		graph.removeVertex("V0");

		graph.print();
	}
}
