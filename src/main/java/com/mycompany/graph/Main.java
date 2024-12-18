/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author crypt
 */
public class Main {

	static Scanner sc = new Scanner(System.in);
	static ArrayList answers;
		
	private static String requestAnswer (String message, ArrayList<String> answers) {
			while (true) {
				System.out.print(message);
				String choice = sc.nextLine();
				for (String answer : answers) {
					if (choice.compareTo(answer) != 0) continue;
					return answer;
				}
				System.out.println("Invalid choice");
			}
	}
	
	private static Graph makeGraph (String searchType) {
		boolean chose = false;
		int vertices = 0;
		while (!chose) {
			System.out.print("Enter vertices count(3-8): ");
			vertices = sc.nextInt();
			chose = true;
			if (vertices < 3 || vertices > 8)
				chose = false;
		}
		
		Graph graph = new Graph(vertices);
		

		if ("b".equals(searchType)) {
			int start = 0;
			chose = false;
			while (!chose) {
				System.out.print("Enter starting vertix: ");
				start = sc.nextInt();
				chose = true;
				if (start < 0 || start >= vertices)
					chose = false;
			}
			graph.breadthStart = start;
		}

		int numberOfEdges = ("b".equals(searchType) ? vertices * (vertices + 1) / 2 : vertices * (vertices));
		for (boolean done = false; !done && numberOfEdges >= 0;) {
			System.out.print("Connect edge: ");
			int from = sc.nextInt();
			System.out.print("To edge: ");
			int to = sc.nextInt();

			if (graph.setEdge(from, to)) numberOfEdges--;
			if ("b".equals(searchType)) graph.setEdge(to, from); // undirected 

			answers = new ArrayList<>(Arrays.asList("a", "d"));
			String choice = requestAnswer("'a'dd/'d'one: ", answers);

			switch (choice) {
				case "a" -> done = false;
				case "d" -> done = true;
			}
		}
		return graph;
	}
	
	private static Graph randomGraph (String searchType) {
		Random r = new Random();
		int vertices = r.nextInt(4, 7);
		System.out.println("Set vertices number to " + vertices + ".");
		
		Graph graph = new Graph(vertices);

		int numberOfEdges = ("b".equals(searchType) ? vertices * (vertices + 1) / 2 : vertices * (vertices));
		numberOfEdges = r.nextInt(3, numberOfEdges + 1);
		numberOfEdges = (numberOfEdges > 7 ? 7 : numberOfEdges);
		System.out.println("Set number of edges to " + numberOfEdges + ".");
		
		for (int i = numberOfEdges; i > 0;) {
			int from = r.nextInt(0, vertices);
			int to = r.nextInt(0, vertices);
			if (graph.setEdge(from, to)) {
				i--;
				System.out.println("Set edge (" + from + ", " + to + ").");
			}
			if ("b".equals(searchType)) {
				graph.setEdge(to, from);
				graph.breadthStart = r.nextInt(0, vertices);
			} // undirected 
			
		}
		return graph;
	}

	public static void main(String[] args) {
		ArrayList<String> answers;
		try {
			for (boolean showAgain = true; showAgain;) {
				answers = new ArrayList<>(Arrays.asList("b", "d"));
				String searchType = requestAnswer("What type('b'readthFirst/'d'epthFirst): ", answers);
				answers = new ArrayList<>(Arrays.asList("y", "n"));
				String random = requestAnswer("Generate random example?(y/n) ", answers);

				Graph graph = null;
				switch (random) {
					case "y" -> graph = randomGraph(searchType);
					case "n" -> graph = makeGraph(searchType);
				}
				
				answers = new ArrayList<>(Arrays.asList("g", "c"));
				String display = requestAnswer("Display?('g'raphical/'c'onsole) ", answers);
				
				switch (display) {
					case "g" -> {
						Display d = new Display(graph, searchType);
						while (d.isVisible()) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					case "c" -> {
						switch (searchType) {
							case "b" -> graph.breadthFirstSearch();
							case "d" -> graph.depthFirstSearch();
						}
					}
				}
				
				answers = new ArrayList<>(Arrays.asList("y", "n"));
				String choice = requestAnswer("Run again? (y/n): ", answers);
 				switch (choice) {
					case "y" -> showAgain = true;
					case "n" -> showAgain = false;
				}
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.print("Exception occurred: " + ex.getMessage());
		}
	}
}
