/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author crypt
 */
public class Graph {
	public class Vertix {
		public int vertix;
		public int distance;
		public int parent;
		public int end;
		public Vertix (int v, int d) { vertix = v; distance = d; parent = -1; end = -1; }
	};
	public static int time;
	private final int vertices;
	private LinkedList<LinkedList<Integer>> adjList;
	//private ArrayList visited;
	//private Queue toVisit;
	
	public Graph (int maxVertices) {
		
		vertices = maxVertices;
		adjList = new LinkedList();
		
		for (int i = 0; i < maxVertices; i++) {
			adjList.add(new LinkedList());
		}
	}
	
	public boolean setEdge(int from, int to) {
		if (from < 0 || to < 0 || from >= vertices || to >= vertices)
			throw new ArrayIndexOutOfBoundsException ("vertix index is out of bounds.");
		if (adjList.get(from).contains(to)) return false;
		adjList.get(from).add(to);
		return true;
	}
	
	public void breadthFirstSearch (int start) {
		if (start < 0 || start >= vertices)
			throw new ArrayIndexOutOfBoundsException ("vertix index is out of bounds.");
		ArrayList visited = new ArrayList<Vertix>();
		ArrayList visitedIndices = new ArrayList<Integer>();
		Queue toVisit = new LinkedList<Vertix>();
		
		toVisit.add(new Vertix(start, 0));
		
		while (!toVisit.isEmpty()) {
			Vertix node = (Vertix)toVisit.poll();
				visited.add(node);
				visitedIndices.add(node.vertix);
			for (int adj : adjList.get(node.vertix)) {
				if (visitedIndices.contains(adj))
					continue;
				Vertix child = new Vertix(adj, node.distance + 1);
				child.parent = node.vertix;
				toVisit.add(child);
			}
		}
	}
	
	private void depthFirstSearchHelper (Vertix node, ArrayList visited, ArrayList visitedIndices) {
		time++;
		node.distance = time;
		
		for (int adj : adjList.get(node.vertix)) {
			if (visitedIndices.contains(adj))
				continue;
			Vertix child = new Vertix(adj, -1);
			child.parent = node.vertix;
			depthFirstSearchHelper(child, visited, visitedIndices);
		}
		visited.add(node);
		time++;
		node.end = time;
	}
		
	public void depthFirstSearch () {
		time = 0;
		ArrayList visited = new ArrayList<Vertix>();
		ArrayList visitedIndices = new ArrayList<Integer>();
		for (int i = 0; i < vertices; i++) {
			if (visitedIndices.contains(i))
				continue;
			Vertix node = new Vertix(i, time);
			depthFirstSearchHelper(node, visited, visitedIndices);
		}
	}
}
