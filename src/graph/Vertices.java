package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Vertices {
	
	private int num;
	private HashSet<Integer> neighbors = new HashSet<>();
		
	public Vertices(int num) {
		// TODO Auto-generated constructor stub
		this.num = num;
	}
	
	
	public void addNeighbor(int neighbor) {
		neighbors.add(neighbor);
	}
	public void addAllNeighbors(HashSet<Integer> neighbors) {
		
		this.neighbors.addAll(neighbors);
	}
	
	public HashSet <Integer> getNeighbors () {
		
		return neighbors;
	}
	
	public int getVertices () {
		return num;
	}


	
	
	
}

