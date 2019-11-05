package graph;

import java.util.ArrayList;
import java.util.HashSet;

public class Vertices {
	
	private int num;
	private ArrayList <Vertices> neighbors = new ArrayList<>(); 
	
	public Vertices(int num) {
		// TODO Auto-generated constructor stub
		this.num = num;
	}
	
	public void addNeighbors(Vertices neighbor) {
		neighbors.add(neighbor);
	}
	
	public ArrayList <Vertices> getNeighbors () {
		return neighbors;
	}
	
	
	
}
