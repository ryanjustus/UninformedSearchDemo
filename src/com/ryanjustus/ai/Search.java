package com.ryanjustus.ai;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/29/12
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class Search {
	public static void main(String[] args){
		if(args.length<1){
			System.out.println("usage: java -jar Search fileName");
		}else{
			int[] initialBoard = readBoard(new File(args[0]));
			Puzzle p = new Puzzle(initialBoard);

			System.out.println("Uninformed search");
			printSolution("Depth First", p, new DepthFirst(p));
			printSolution("Breadth First", p, new BreadthFirst(p));
			printSolution("Depth Limited (40)", p, new DepthLimited(p,40));
			printSolution("Iterative Deepening", p, new IterativeDeepening(p));
			printSolution("BiDirectional", p, new BiDirectional(p));

			System.out.println("Informed searches");
			Solver greedy = new Greedy(new ManhattanHeuristic(), p);
			printSolution("Greedy (Manhattan Heuristic)",p,greedy);

			Solver aStarManhattan = new AStar(new ManhattanHeuristic(),p);
			printSolution("A* (Manhattan Heuristic)", p, aStarManhattan);

			Heuristic optimal = new OptimalHeuristic();
			optimal.init();
			Solver aStarOptimum = new AStar(optimal,p);
			printSolution("A* (Optimal Heuristic)", p, aStarOptimum);

			Heuristic ryan5 = new RyanHeuristic(5);
			ryan5.init();
			Solver aStarRyan5 = new AStar(ryan5,p);
			printSolution("A* (Ryan 5 deep)",p,aStarRyan5);

			Heuristic ryan21 = new RyanHeuristic(21);
			ryan21.init();
			Solver aStarRyan21 = new AStar(ryan21,p);
			printSolution("A* (Ryan 21 deep)",p,aStarRyan21);
		}
	}

	public static void printSolution(String name, Puzzle p, Solver s){
		System.out.println("\r\n"+name+" : ");
	    p.print(System.out);
		System.out.println();
		Puzzle solved = s.solve();
		if(solved==null){
			System.out.println("NO SOLUTION FOUND");
		}else{
			solved.printMoves(System.out);
			solved.print(System.out);
			System.out.println("Number of moves: " + solved.getNumMoves());
			System.out.println("Number of expansions: "+ s.getNumExpansions());
			System.out.println("Elapsed time (ms): "+ s.getTime()/1000.0/1000.0);
		}
		System.out.println("**************\r\n");
	}

	public static int[] readBoard(File f){
		try {
			Scanner s = new Scanner(new FileInputStream(f));
			Pattern p = Pattern.compile("[1-8_]");
			int[] board = new int[9];
			int index=0;
			while(s.hasNext(p) && index<9){
				String c = s.next(p);
				if(c.equals("_")){
					c="-1";
				}
				board[index] = Integer.parseInt(c);
				index++;
			}
			return board;
		} catch (FileNotFoundException e) {
			 System.err.print("File not found: "+f.getName());
		}
		return null;
	}
}
