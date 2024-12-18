/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.graph;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author crypt
 */
public class Display extends JFrame {
	MyPanel panel;
	public Display (Graph graph, String searchType) {
		panel = new MyPanel(graph, searchType);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		panel.run();
		Node.clear();
		this.dispose();
	}
}
