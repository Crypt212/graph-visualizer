/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.graph;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 *
 * @author crypt
 */
public class MyPanel extends JPanel {
	final int screenHeight = 700;
	final int screenWidth = 700;
	GraphPlot graphPlot;
	String searchType;
	private boolean waitingForSpace = false;
	
	MyPanel (Graph graph, String searchType) {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.graphPlot = new GraphPlot(graph, this);
		this.searchType = searchType;
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if (waitingForSpace && e.getKeyCode() == KeyEvent.VK_SPACE) {
					waitingForSpace = false;
				}
			}
		});
		setFocusable(true);
	}
	
	public void waitForSpaceBar() {
		waitingForSpace = true;
		while (waitingForSpace) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void run () {
		switch (searchType) {
			case "b" -> graphPlot.breadthFirstSearch();
			case "d" -> graphPlot.depthFirstSearch();
		}
	}
	
	@Override
	public void paint (Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setPaint(Color.BLACK);
		g2D.fillRect(0, 0, screenWidth, screenHeight);
		Node.setup(g2D, screenWidth, screenHeight, searchType.equals("d"));
		Node.draw();
	}
}
