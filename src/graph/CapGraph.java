/**
 * 
 */
package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.GraphLoader;

/**
 * @author Your name here.
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {

	private HashMap<Integer, HashSet<Integer>> nodes = new HashMap<>();
	HashSet<Integer> visited;
	Deque<Integer> finished;
	Deque<Integer> vertices;
	
	
	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		if (!nodes.containsKey(num)) {
			nodes.put(num, new HashSet<Integer>());
			
		}

	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		
		if (nodes.containsKey(from)) {
			nodes.get(from).add(to);
		}
		/*if (!nodes.containsKey(to)) {
			nodes.put(to, new HashSet<Integer>());
			nodes.get(to).add(from);
			
			
		}
		*/
		

	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		
		if (nodes.containsKey(center)) {
			CapGraph graph = new CapGraph();
			graph.addVertex(center);
			graph.nodes.get(center).addAll(nodes.get(center));
			for (Integer i : graph.nodes.get(center)) {
				graph.addVertex(i);
				graph.nodes.get(i).add(center);
				for (Integer j : nodes.get(i)) {
					if (graph.nodes.get(center).contains(j)) {
						graph.nodes.get(i).add(j);
					}
				}
				
				
			}
			
			return graph;
		}
				
		return null;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	
	
	public Deque<Integer> DFS () {
		visited = new HashSet<>();
		finished = new ArrayDeque<Integer>();
		vertices = new ArrayDeque<Integer>();
		for (Integer i : nodes.keySet()) {
			vertices.add(i);
		}
		
		while (!vertices.isEmpty()) {
			Integer v = vertices.poll();
			if (!visited.contains(v)) {
				DFSVisit(v);
			}
		}
		
		System.out.println(finished.toString());
		
		return finished;
	}
	
	private void DFSVisit(Integer v) {
		visited.add(v);
		for (Integer n : nodes.get(v)) {
			if (!visited.contains(n)) {
				DFSVisit(n);
			}
		}
		
		finished.push(v);
		
	}
	
	private HashMap<Integer, HashSet<Integer>> reverseGraph(HashMap<Integer, HashSet<Integer>> originGraph) {
		HashMap<Integer, HashSet<Integer>> graph = new HashMap<>();
		for (Integer i : nodes.keySet()) {
			graph.put(i, new HashSet<Integer>());
		}
		
		for (Integer i : graph.keySet()) {
			for (Integer vertices : originGraph.get(i)) {
				graph.get(vertices).add(i);
			}
		}
		
		
		
		
		return graph;
		
	}
	
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		Integer i;
		List<Graph> sccsList = new ArrayList<>();
		while (!finished.isEmpty()) {
			i = finished.poll();
			
		}
		
		
		
		return null;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		
		return nodes;
	}
	
	
	public static void main(String[] args) {
		/*CapGraph graph = new CapGraph();
		graph.addVertex(7);
		graph.addVertex(8);
		graph.addVertex(9);
		graph.addVertex(6);
		graph.addVertex(5);
		graph.addEdge(7, 8);
		graph.addEdge(7, 9);
		
		System.out.println(graph.getEgonet(7).exportGraph().get(7).size());
		
		Graph graph = new CapGraph();
        GraphLoader.loadGraph(graph, "data/facebook_ucsd.txt");
        HashMap<Integer, HashSet<Integer>> res = graph.getEgonet(0).exportGraph();
		System.out.println(res.get(22));
		//System.out.println(res);
		*/
		// for test SCC graph
		 Graph g = new CapGraph();
         GraphLoader.loadGraph(g, "data/scc/test_2.txt");
         System.out.println(g.exportGraph());
         ((CapGraph) g).DFS();
         
		
	}

	
}
