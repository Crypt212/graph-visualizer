/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author crypt
 */
public class GraphPlot {

	public static int time;
	public final int verticesCount;
	public int breadthStart;
	public HashMap<Integer, Node> network;
	private final MyPanel mp;

	public GraphPlot(Graph graph, MyPanel mp) {
		this.mp = mp;
		this.verticesCount = graph.verticesCount;
		this.breadthStart = graph.breadthStart;
		network = new HashMap();
		for (int i = 0; i < graph.verticesCount; i++) {
			LinkedList<Integer> n = graph.vertices.get(i).adjList;
			network.put(i, new Node(n, i));
		}
	}

	public void breadthFirstSearch() {
		if (breadthStart < 0 || breadthStart >= verticesCount) {
			throw new ArrayIndexOutOfBoundsException("vertix index is out of bounds.");
		}
		Queue toVisit = new LinkedList<Integer>();

		update();

		network.get(breadthStart).distance = 0;
		network.get(breadthStart).status = 1;
		toVisit.add(breadthStart);

		while (!toVisit.isEmpty()) {
			int nodeIndex = (Integer) toVisit.poll();
			network.get(nodeIndex).status = 2;
			System.out.println("Node " + nodeIndex + " visited at distance " + network.get(nodeIndex).distance + ".");
			update();
			for (int adj : network.get(nodeIndex).neighbours) {
				if (network.get(adj).status >= 1) // visited(2) or set to be visit(1)
				{
					continue;
				}
				network.get(adj).distance = network.get(nodeIndex).distance + 1;
				network.get(adj).parent = nodeIndex;
				network.get(adj).status = 1;
				toVisit.add(adj);
				System.out.println("Saw new node " + adj + " at distance " + network.get(nodeIndex).distance + ".");
				update();
			}
		}
	}

	private void depthFirstSearchHelper(int node) {
		time++;
		network.get(node).distance = time;
		System.out.println("Found node " + node + " at time " + time + ".");
		update();

		for (int adj : network.get(node).neighbours) {
			if (network.get(adj).status >= 1) {
				continue;
			}
			network.get(adj).distance = network.get(node).distance + 1;
			network.get(adj).parent = node;
			network.get(adj).status = 1;
			depthFirstSearchHelper(adj);
		}
		time++;
		network.get(node).status = 2;
		System.out.println("Visited node " + node + " at time " + time + ".");
		network.get(node).end = time;
		update();
	}

	public void depthFirstSearch() {
		time = 0;
		for (int i = 0; i < verticesCount; i++) {
			if (network.get(i).status == 2) {
				continue;
			}
			network.get(i).status = 1;

			System.out.println("New start from node " + i + ".");
			depthFirstSearchHelper(i);
		}
	}

	private void update() {
		mp.repaint();
		mp.waitForSpaceBar();
	}
}
