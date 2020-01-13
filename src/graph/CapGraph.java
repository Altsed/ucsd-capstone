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
import java.util.Iterator;
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
 *         For the warm up assignment, you must implement your Graph in a class
 *         named CapGraph. Here is the stub file.
 *
 */
public class CapGraph implements Graph {

	HashMap<Integer, HashSet<Integer>> nodes = new HashMap<>();
	private HashMap<Integer, Vertices> nodes1 = new HashMap<>();
	HashSet<Integer> visited;
	Deque<Integer> finished;
	Deque<Integer> nodesOfSCC;
	List<Graph> listSCC;

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see graph.Graph#addVertex(int)
	 */
	public HashMap<Integer, Vertices> getNodes() {
		return nodes1;
	}

	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		if (!nodes1.containsKey(num)) {
			Vertices v = new Vertices(num);
			nodes1.put(num, v);

		}

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see graph.Graph#addEdge(int, int)
	 */

	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub

		if (nodes1.containsKey(from)) {
			nodes1.get(from).addNeighbor(to);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graph.Graph#getEgonet(int)
	 */

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see graph.Graph#getSCCs()
	 */
	
	//this helper method used to find SCC
	public Deque<Integer> nodesToVertices(HashMap<Integer, HashSet<Integer>> nodesToExplore) {
		Deque<Integer> vertices = new ArrayDeque<Integer>();

		for (Integer i : nodesToExplore.keySet()) {
			vertices.add(i);
		}

		return vertices;

	}
	

	public Deque<Integer> DFS(Deque<Integer> vertices, HashMap<Integer, HashSet<Integer>> nodesToExplore) {

		visited = new HashSet<>();
		finished = new ArrayDeque<Integer>();
		listSCC = new ArrayList<>();
		nodesOfSCC = new ArrayDeque<Integer>();

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		HashMap<Integer, HashSet<Integer>> result = new HashMap<>();
		for (Integer i : nodes1.keySet()) {
			result.put(i, nodes1.get(i).getNeighbors());
		}
		return result;
	}

	// 
	/*
	 * When we pass egoNet to the method it returns HashMap with suggested relations, there are nodes (keys) Users
	 * and value is the list with unconnected users which have mutual friend.
	 */
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
					if (res.get(i) == null) {
						res.put(i, new HashSet<Integer>());
					}
					res.get(i).add(j);
				}
			}
		}

		System.out.println(res);
		return res;

	}
	/*
	 * finding minimum dominated set (mds) 
	 * create copy of graph (map)
	 * First step: find User with maximum quantity of friends (see helper method getBiggestNode)
	 * add this node to the result set
	 * For each friend from list of User friends
	 * 	- for each friend from the list with friends 
			- Delete connection with friend of the User
			- Delete connection with User
	 * 	- delete friend from map
	 * delete User from map
	 * repeat from first step while map have any nodes
	 * return HashSet which presents minimum dominated set
	 */
	
	public HashSet<Integer> mds()  {
		HashMap<Integer, Vertices> map = new HashMap<Integer, Vertices>();
		HashSet<Integer> result = new HashSet<>();
		map.putAll(nodes1);
		while (!map.isEmpty()) {
			int max_node = getBiggestNode(map);
			result.add(max_node);
			// Iterate through max_node neighbors and their neighbors to remove connection with max_node
			for (Iterator<Integer> iterator = map.get(max_node).getNeighbors().iterator(); iterator.hasNext();) {
				Integer i = iterator.next();
				for (Iterator<Integer> itr = map.get(i).getNeighbors().iterator(); itr.hasNext();) {
					Integer j = itr.next();
					if (j != max_node) {
						map.get(j).getNeighbors().remove(i);
						map.get(j).getNeighbors().remove(max_node);
					}
				}
				map.remove(i);
			}
			map.remove(max_node);
			
			
		}
		//helper method to test correctness of algorithm
		checkMds(result);
		return result;

		
	}
	// helper method to return node with maximum number of neighbors
	public int getBiggestNode(HashMap<Integer, Vertices> map) {

		int max_node_id = (int) map.keySet().toArray()[0];
		for (Integer i : map.keySet()) {
			if (map.get(i).getNeighbors().size() > map.get(max_node_id).getNeighbors().size()) {
				max_node_id = i;
			}
		}

		return max_node_id;

	}
	/*
	 * For testing correctness of minimum dominated set 
	 * This method removes nodes and all friends of nodes from 
	 * dataset which are presented in minimum dominated set, 
	 * as result size of dataset must be zero.
	 */

	public void checkMds(HashSet<Integer> result) {
		HashMap<Integer, Vertices> test_nodes = new HashMap<>();
		test_nodes.putAll(nodes1);
		for (Integer i : result) {
			HashSet<Integer> temp = test_nodes.get(i).getNeighbors();
			for (Integer j : temp) {
				test_nodes.remove(j);
			}
			test_nodes.remove(i);
		}
		System.out.println("Number of uncovered users: " + test_nodes.size());
	}


	public static void main(String[] args) throws FileNotFoundException {

		Graph graph = new CapGraph();
		GraphLoader.loadGraph(graph, "data/facebook_ucsd.txt");
		Map<Integer, HashSet<Integer>> res1 = graph.exportGraph();
		System.out.println(res1);
		System.out.println(((CapGraph) graph).mds());
		
		

	}

}
