/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author crypt
 */

public class Node {
	static private final Color LINE1COLOR = Color.decode("#87854f");
	static private final Color LINE2COLOR = Color.decode("#fcf51e");
	static private final Color UNVISISTED_COLOR = Color.decode("#282828");
	static private final Color VISITED_COLOR = Color.decode("#006818");
	static private final Color TO_VISIT_COLOR = Color.decode("#666800");
	static private final Color TEXT_COLOR = Color.decode("#ffffff");
	static private final BasicStroke thinStroke = new BasicStroke(2);
	static private final BasicStroke normalStroke = new BasicStroke(3);
	static private final BasicStroke boldStroke = new BasicStroke(5);
	static private final int RADIUS = 40;

	private static HashMap<Integer, Node> all = all = new HashMap();
	static Graphics2D g2D;
	static int screenWidth, screenHeight;
	static boolean directed; 
	public int index;
	public int distance;
	public int parent;
	public int end;
	public int status; // 0: unvisited, 1: to visit, 2: visited
	public LinkedList<Integer> neighbours;
	
	public Node (LinkedList<Integer> n, int i) {
		neighbours = n;
		distance = -1;
		parent = -1;
		end = -1;
		status = 0;
		index = i;
		all.put(index, this);
	}
	
	static public void draw () {
		double bigRadius = 0.5 * (5 * RADIUS) / Math.cos(Math.PI * (0.5 - (1.0 / all.size())));
		for (Node n : all.values()) {
			double xPos = (screenWidth / 2.0) + bigRadius * Math.sin(2.0 * Math.PI * ((double)n.index / all.size()));
			double yPos = (screenHeight / 2.0) + bigRadius * Math.cos(2.0 * Math.PI * ((double)n.index / all.size()));
			
			for (int adj : n.neighbours) {
				g2D.setStroke(thinStroke);
				g2D.setPaint(LINE1COLOR);
				if (adj == n.index) {
					g2D.drawArc(adj, adj, adj, adj, adj, adj);
					double angle = Math.atan(((screenHeight / 2.0) - yPos) / ((screenWidth / 2.0) - xPos));
					if (xPos > (screenWidth / 2.0)) angle += Math.PI;
					g2D.drawArc((int)(xPos + 2.0 * RADIUS * Math.cos(angle + Math.PI) - RADIUS * 1.5),
											(int)(yPos + 2.0 * RADIUS * Math.sin(angle + Math.PI) - RADIUS * 1.5),
											(int)(RADIUS * 3), (int)(RADIUS * 3),
											0, 360);
					continue;
				};
				double xPosAdj = (screenWidth / 2.0) + bigRadius * Math.sin(2.0 * Math.PI * ((double)adj / all.size()));
				double yPosAdj = (screenHeight / 2.0) + bigRadius * Math.cos(2.0 * Math.PI * ((double)adj / all.size()));
				g2D.drawLine((int)xPos, (int)yPos, (int)xPosAdj, (int)yPosAdj);
				if (directed) {
					g2D.setStroke(normalStroke);
					g2D.setPaint(LINE2COLOR);
					double angle = Math.atan((yPosAdj - yPos) / (xPosAdj - xPos));
					if (xPos > xPosAdj) angle += Math.PI;
					double xPosArrowTip = xPosAdj - Math.cos(angle) * RADIUS;
					double yPosArrowTip = yPosAdj - Math.sin(angle) * RADIUS;
					double xPosArrowEnd = xPosArrowTip + Math.cos(angle + (0.75 * Math.PI)) * RADIUS / 4.0;
					double yPosArrowEnd = yPosArrowTip + Math.sin(angle + (0.75 * Math.PI)) * RADIUS / 4.0;
					g2D.drawLine((int)xPosArrowTip, (int)yPosArrowTip, (int)xPosArrowEnd, (int)yPosArrowEnd);
					xPosArrowEnd = xPosArrowTip + Math.cos(angle - (0.75 * Math.PI)) * RADIUS / 4.0;
					yPosArrowEnd = yPosArrowTip + Math.sin(angle - (0.75 * Math.PI)) * RADIUS / 4.0;
					g2D.drawLine((int)xPosArrowTip, (int)yPosArrowTip, (int)xPosArrowEnd, (int)yPosArrowEnd);
				}
			}
		}

		for (Node n : all.values()) {
			double xPos = (screenWidth / 2.0) + bigRadius * Math.sin(2.0 * Math.PI * ((double)n.index / all.size()));
			double yPos = (screenHeight / 2.0) + bigRadius * Math.cos(2.0 * Math.PI * ((double)n.index / all.size()));
			
			Color background = null;
			switch (n.status) {
				case 0 -> background = UNVISISTED_COLOR;
				case 1 -> background = TO_VISIT_COLOR;
				case 2 -> background = VISITED_COLOR;
			}
			
		
			g2D.setPaint(background);
			g2D.fillOval((int)xPos - RADIUS, (int)yPos - RADIUS, RADIUS * 2, RADIUS * 2);
			
			
			double angle = Math.atan(((screenHeight / 2.0) - yPos) / ((screenWidth / 2.0) - xPos));
			if (xPos > screenWidth / 2.0) angle += Math.PI;
						
			g2D.setStroke(boldStroke);
			g2D.setPaint(TEXT_COLOR);
			g2D.setFont(new Font("Ink Free", Font.BOLD, 20));
			g2D.drawString(String.valueOf((char)('a' + n.index)), (int)(xPos + 2.0 * RADIUS * Math.cos(angle + Math.PI)),
																														(int)(yPos + 2.0 * RADIUS * Math.sin(angle + Math.PI)));
			
			String innerText = "";
			if (n.status > 0) {
				if (!directed) {
					innerText = String.valueOf(String.valueOf(n.distance));
				} else {
					innerText = String.valueOf(n.distance) + "/" + (n.status == 2 ? String.valueOf(n.end) : "");
				}
			}
			g2D.drawString(innerText, (int)(xPos - (0.25 + (directed ? 0.3 : 0)) * RADIUS), (int)(yPos + 0.2 * RADIUS));
			
			g2D.setPaint(LINE1COLOR);
			g2D.setStroke(boldStroke);
			
			g2D.drawOval((int)xPos - RADIUS, (int)yPos - RADIUS, RADIUS * 2, RADIUS * 2);
		}
	}
	
	public static void setup(Graphics2D g2D, int screenWidth, int screenHeight, boolean directed) {
		Node.g2D = g2D;
		Node.screenWidth = screenWidth;
		Node.screenHeight = screenHeight;
		Node.directed = directed;
	}
	
	public static void clear() {
		Node.all.clear();
	}
}
