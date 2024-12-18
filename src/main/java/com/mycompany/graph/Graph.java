/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author crypt
 */
class Vertex {
	public int index;
	public int distance;
	public int parent;
	public int end;
	public boolean found;
	public LinkedList<Integer> adjList;
	public Vertex (int i, int d) { index = i; distance = d; parent = -1; end = -1; adjList = new LinkedList(); }
};

public class Graph {
	public static int time;
	public final int verticesCount;
	public int breadthStart;
	public LinkedList<Vertex> vertices;
	
	public Graph (int maxVertices) {
		verticesCount = maxVertices;
		vertices = new LinkedList();
		for (int i = 0; i < maxVertices; i++) {
			vertices.add(new Vertex(i, -1));
			vertices.get(0).adjList = new LinkedList();
		}
	}
	
	public boolean setEdge(int from, int to) {
		if (from < 0 || to < 0 || from >= verticesCount || to >= verticesCount)
			throw new ArrayIndexOutOfBoundsException ("vertix index is out of bounds.");
		if (vertices.get(from).adjList.contains(to)) return false;
		vertices.get(from).adjList.add(to);
		return true;
	}
	
	public void breadthFirstSearch () {
		if (breadthStart < 0 || breadthStart >= verticesCount)
			throw new ArrayIndexOutOfBoundsException ("vertix index is out of bounds.");
		ArrayList visited = new ArrayList<Integer>();
		Queue toVisit = new LinkedList<Integer>();
		
		vertices.get(breadthStart).distance = 0;
		toVisit.add(breadthStart);
		
		while (!toVisit.isEmpty()) {
			int nodeIndex = (Integer)toVisit.poll();
			visited.add(nodeIndex);
			System.out.println("Node " + nodeIndex + " visited at distance " + vertices.get(nodeIndex).distance + ".");
			for (int adj : vertices.get(nodeIndex).adjList) {
				if (visited.contains(adj) || toVisit.contains(adj))
					continue;
				vertices.get(adj).parent = nodeIndex;
				vertices.get(adj).distance = vertices.get(nodeIndex).distance;
				toVisit.add(adj);
				System.out.println("Saw new node " + vertices.get(adj).index + " at distance " + vertices.get(adj).distance + ".");
			}
		}
	}
	
	private void depthFirstSearchHelper (int nodeIndex, ArrayList visited) {
		time++;
		vertices.get(nodeIndex).distance = time;
		System.out.println("Found node " + nodeIndex + " at time " + time + ".");
		
		for (int adj : vertices.get(nodeIndex).adjList) {
			if (visited.contains(adj) || vertices.get(adj).found)
				continue;
			vertices.get(adj).distance = vertices.get(nodeIndex).distance + 1;
			vertices.get(adj).parent = nodeIndex;
			vertices.get(adj).found = true;
			depthFirstSearchHelper(adj, visited);
		}
		visited.add(nodeIndex);
		time++;
		System.out.println("Visited node " + nodeIndex + " at time " + time + ".");
		vertices.get(nodeIndex).end = time;
	}
		
	public void depthFirstSearch () {
		time = 0;
		ArrayList visited = new ArrayList<Integer>();
		for (int i = 0; i < verticesCount; i++) {
			if (visited.contains(i))
				continue;
			vertices.get(i).found = true;
			vertices.get(i).distance = time;
			System.out.println("New start from node " + i + ".");
			depthFirstSearchHelper(i, visited);
		}
	}
}
