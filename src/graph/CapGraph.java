/**
 * 
 */
package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
		if (!nodes.containsKey(to)) {
			nodes.put(to, new HashSet<Integer>());
			nodes.get(to).add(from);
			
			
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
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
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
		*/
		Graph graph = new CapGraph();
        GraphLoader.loadGraph(graph, "data/facebook_ucsd.txt");
        HashMap<Integer, HashSet<Integer>> res = graph.getEgonet(0).exportGraph();
		System.out.println(res.get(22));
		//System.out.println(res);
		
		
		
	}

	
}
