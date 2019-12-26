/**
 * 
 */
package graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
public class CapGraph2 implements Graph {

	private HashMap<Integer, HashSet<Integer>> nodes = new HashMap<>();
	HashSet<Integer> visited;
	Deque<Integer> finished;
	Deque<Integer> nodesOfSCC;
	List<Graph> listSCC;
	
	private HashMap<Integer, Vertices> nodes1 = new HashMap<>();
	
	
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

	public void addVertex1 (int num) {
		// TODO Auto-generated method stub
		if (!nodes1.containsKey(num)) {
			Vertices v = new Vertices(num);
			nodes1.put(num, v);
			
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
		

	}
	public void addEdge1(int from, int to) {
		// TODO Auto-generated method stub
		
		if (nodes1.containsKey(from)) {
			nodes1.get(from).addNeighbor(to);
		}
		

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
	public Deque<Integer> nodesToVertices (HashMap<Integer, HashSet<Integer>> nodesToExplore) {
		Deque<Integer> vertices = new ArrayDeque<Integer>();
			
		for (Integer i : nodesToExplore.keySet()) {
			vertices.add(i);
		}
		
		return vertices;
		
	}
	
	public Deque<Integer> DFS (Deque<Integer> vertices, HashMap<Integer, HashSet<Integer>> nodesToExplore) {
		
		visited = new HashSet<>();
		finished = new ArrayDeque<Integer>();
		listSCC = new ArrayList<>();
		nodesOfSCC= new ArrayDeque<Integer>();
		
		while (!vertices.isEmpty()) {
			
			Integer v = vertices.poll();
			if (!visited.contains(v)) {
				DFSVisit(v, nodesToExplore);
				
				CapGraph g = new CapGraph();
				while (!nodesOfSCC.isEmpty()) {
					g.addVertex(nodesOfSCC.poll());
				}
				listSCC.add(g);
				
			}
		}
		
		
		
		
		
		
		return finished;
	}
	
	private void DFSVisit(Integer v, HashMap<Integer, HashSet<Integer>> nodesToExplore) {
		
		visited.add(v);
		for (Integer n : nodesToExplore.get(v)) {
			if (!visited.contains(n)) {
			
				
				DFSVisit(n, nodesToExplore);
			}
		}

		finished.push(v);
		nodesOfSCC.push(v);
		
	}
	
	private static HashMap<Integer, HashSet<Integer>> reverseGraph(HashMap<Integer, HashSet<Integer>> originGraph) {
		HashMap<Integer, HashSet<Integer>> reverseNodes = new HashMap<>();
		for (Integer i : originGraph.keySet()) {
			reverseNodes.put(i, new HashSet<Integer>());
		}
		
		for (Integer i : reverseNodes.keySet()) {
			for (Integer vertices : originGraph.get(i)) {
				reverseNodes.get(vertices).add(i);
			}
		}

		return reverseNodes;
		
	}
	
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		
		
		DFS(nodesToVertices(nodes), nodes);
		
		DFS(finished, reverseGraph(nodes));
		
		return listSCC;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		
		return nodes;
	}
	
	
	
	/*
	public static void main(String[] args) throws FileNotFoundException {
	
		
		//CapGraph graph = new CapGraph();
	
		
		
		
		
		
		Graph graph = new CapGraph();
        GraphLoader.loadGraph(graph, "data/facebook_ucsd.txt");
        HashMap<Integer, HashSet<Integer>> res = graph.getEgonet(0).exportGraph();
		System.out.println(res.get(22));
		//System.out.println(res);
		
		// for test SCC graph
		 Graph g = new CapGraph();
         GraphLoader.loadGraph(g, "data/scc/test_1.txt");
         
        // System.out.println(g.exportGraph());
         
         g.getSCCs();
         
		
		
		
	}

	*/
}
