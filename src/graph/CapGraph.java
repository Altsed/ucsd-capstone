/**
 * 
 */
package graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import util.GraphLoader;

/**
 * @author Your name here.
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {

	HashMap<Integer, HashSet<Integer>> nodes = new HashMap<>();
	HashSet<Integer> visited;
	Deque<Integer> finished;
	Deque<Integer> nodesOfSCC;
	List<Graph> listSCC;
	
	private HashMap<Integer, Vertices> nodes1 = new HashMap<>();
	
	
	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	*/
	public HashMap<Integer, Vertices> getNodes() {
		return nodes1;
	}
	public void addVertex1(int num) {
		// TODO Auto-generated method stub
		if (!nodes.containsKey(num)) {
			nodes.put(num, new HashSet<Integer>());
			
		}

	}
	@Override
	public void addVertex (int num) {
		// TODO Auto-generated method stub
		if (!nodes1.containsKey(num)) {
			Vertices v = new Vertices(num);
			nodes1.put(num, v);
			
		}

	}
	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	
	public void addEdge1(int from, int to) {
		// TODO Auto-generated method stub
		
		if (nodes.containsKey(from)) {
			nodes.get(from).add(to);
		}
		

	}
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		
		if (nodes1.containsKey(from)) {
			nodes1.get(from).addNeighbor(to);
		}
		

	}
	
	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	
	public Graph getEgonet1(int center) {
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
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		
		if (nodes1.containsKey(center)) {
			
			CapGraph graph = new CapGraph();
			graph.addVertex(center);
			graph.nodes1.get(center).addAllNeighbors(nodes1.get(center).getNeighbors());
			
			
			
			for (Integer i : graph.nodes1.get(center).getNeighbors()) {
				graph.addVertex(i);
				
				graph.nodes1.get(i).addNeighbor(center);
				for (Integer j : nodes1.get(i).getNeighbors()) {
					
					if (graph.nodes1.get(center).getNeighbors().contains(j)) {
						graph.nodes1.get(i).addNeighbor(j);
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
	
	public HashMap<Integer, HashSet<Integer>> exportGraph1() {
		// TODO Auto-generated method stub
		
		return nodes;
	}
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		HashMap<Integer, HashSet<Integer>> result = new HashMap<>();
		for (Integer i : nodes1.keySet()) {
			
			result.put(i, nodes1.get(i).getNeighbors());
		}
		
		return result;
	}
	
	// create new map for suggested relationships.
	 	
	public HashMap<Integer, HashSet<Integer>> getNewRelations() {
		HashMap<Integer, HashSet<Integer>> res = new HashMap<>();
		HashSet<Integer> list = new HashSet<>();
		for (Integer i : nodes1.keySet()) {
			list.addAll(nodes1.get(i).getNeighbors());
		}
		
		for (Integer i : nodes1.keySet()) {
			HashSet<Integer> temp = nodes1.get(i).getNeighbors();
			for (Integer j : list) {
				if (j != i && !temp.contains(j)) {
					if (res.get(i)==null) {res.put(i, new HashSet<Integer>());}
					res.get(i).add(j);
				}
			}
		}
		
		System.out.println(res);
		return res;
		
	}
	
	public HashSet<Integer> mds() throws IOException {
		HashMap<Integer, Vertices> map = new HashMap<Integer, Vertices>();
		HashSet<Integer> result = new HashSet<>();
		HashSet<Integer> nodesToDelete = new HashSet<>();
		map.putAll(nodes1);
		while (!map.isEmpty()) {
			int node = getBiggestNode(map);
			result.add(node);
			nodesToDelete.add(node);
			//adding nodes to remove
			for (Integer i : map.keySet()) {
				if (map.get(i).getNeighbors().contains(node)) {
					nodesToDelete.add(i);
				}
			}
			//remove links for neighbors 
			for (Integer i : map.keySet()) {
				for (Integer j : nodesToDelete) {
					map.get(i).getNeighbors().remove(j);
				}
			}
			//remove nodes from map 
			for (Integer i : nodesToDelete) {
				map.remove(i);
			}
			
		}
	
		return result;
	}
	
	public int getBiggestNode (HashMap<Integer, Vertices> map){

		int id = (int) map.keySet().toArray()[0];
		for (Integer i : map.keySet()) {
			if (map.get(i).getNeighbors().size() > map.get(id).getNeighbors().size()) {
				id = i;
			}
		}
		
		return id;
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {

		Graph graph = new CapGraph();
        GraphLoader.loadGraph(graph, "data/small_test_graph.txt");
        Map<Integer, HashSet<Integer>> res1 =  graph.exportGraph();
        System.out.println(res1);
        try {
			System.out.println(((CapGraph) graph).mds());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}

