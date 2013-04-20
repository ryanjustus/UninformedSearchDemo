package com.ryanjustus.ai;


import java.util.*;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/29/12
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class PuzzleTest extends TestCase {
	public void testGeneratePuzzle() throws Exception {
		Puzzle p = Puzzle.generatePuzzle();
		p.print(System.out);
	}


	public void testHash(){
		int count=10000;
		int[] puzzle = new int[]{1,2,3,4,5,6,7,8,0};
		long start = System.nanoTime();
		for(int i=0;i<count;i++){
			StringBuilder s = new StringBuilder(10).append((puzzle[0]==0)?"":puzzle[0])
				.append(puzzle[1]).append(puzzle[2])
				.append(puzzle[3]).append(puzzle[4]).append(puzzle[5])
				.append(puzzle[6]).append(puzzle[7]).append(puzzle[8]);
			Integer.parseInt(s.toString());

		}
		System.out.println(System.nanoTime()-start);
		start=System.nanoTime();
		for(int i=0;i<count;i++){
			int hash = Arrays.hashCode(puzzle);
		}
		System.out.println(System.nanoTime()-start);

		start=System.nanoTime();
		for(int i=0;i<count;i++){
			StringBuilder s = new StringBuilder(10).append((puzzle[0]==0)?"":puzzle[0])
					.append(puzzle[1]).append(puzzle[2])
					.append(puzzle[3]).append(puzzle[4]).append(puzzle[5])
					.append(puzzle[6]).append(puzzle[7]).append(puzzle[8]);
			int hash = Integer.parseInt(s.toString());

		}
		System.out.println(System.nanoTime()-start);

		start=System.nanoTime();
		for(int i=0;i<count;i++){
			int hash = 1*puzzle[8]+10*puzzle[7]+100*puzzle[6]+1000*puzzle[5]+10000*puzzle[4]
					+100000*puzzle[3]+1000000*puzzle[2]+10000000*puzzle[1]+100000000*puzzle[0];
		}

		System.out.println(System.nanoTime()-start);

	}

	public void testGetMoves() throws Exception {
		int[] data = new int[]{1,0,3,4,1,6,7,8,5};
		Puzzle p = new Puzzle(data);
		List<Puzzle.Move> moves= p.getPossibleMoves();
		for(Puzzle.Move m: moves){
			System.out.println(m);
		}
	}

	public void testMeetInMiddleRandom(){
		for(int i=0;i<100;i++){
			Puzzle p = Puzzle.generatePuzzle();
			Solver solver = new BiDirectional(p);
			Puzzle solved = solver.solve();
			solved.print(System.out);
			solved.printMoves(System.out);
			System.out.println(solver.getTime()/1000/1000 + " ms");
			System.out.println("******************");

		}
	}

	public void testRandomPuzzle(){
		for(int i=0;i<10;i++){
			Puzzle p = Puzzle.generatePuzzle();
			Solver solver = new BreadthFirst(p);
			Puzzle solved = solver.solve();
			solved.print(System.out);
			solved.printMoves(System.out);
			System.out.println(solver.getTime()/1000.0/1000.0 + " ms");
			System.out.println("expansions: " +solver.getNumExpansions());
			System.out.println("******************");


		}
	}

	public void testDepthFirstPuzzle(){
		for(int i=0;i<10;i++){
			Puzzle p = Puzzle.generatePuzzle();
			Solver solver = new DepthFirst(p);
			Puzzle solved = solver.solve();
			solved.print(System.out);
			solved.printMoves(System.out);
			System.out.println(solver.getTime() + " nanoseconds");
			System.out.println("expansions: " +solver.getNumExpansions());
			System.out.println("******************");
		}
	}

	public void testMeetInMiddle(){
		int[] data = new int[]{1,2,3,4,5,6,7,0,8};
		Puzzle p = new Puzzle(data);
		Solver solver = new BiDirectional(p);
		Puzzle solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println("expansions: " +solver.getNumExpansions());
		System.out.println(solver.getTime() + " nanoseconds");

		data = new int[]{1,2,3,0,5,6,4,7,8};
		p = new Puzzle(data);
		solver = new BiDirectional(p);
		solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println("expansions: " +solver.getNumExpansions());
		System.out.println(solver.getTime() + " nanoseconds");
	}

	public void testBreadthFirst2(){
		int[] state = new int[]{1,2,3,4,0,6,7,5,8};
		Puzzle p = new Puzzle(state);
		List<Puzzle.Move> moves = p.getPossibleMoves();
		for(Puzzle.Move m: moves){
			System.out.println(m);
		}
		Solver s = new BreadthFirst(p);
		Puzzle solved = s.solve();
		solved.printMoves(System.out);
	}


	public void testHeuristic(){
		//Generate heuristic
		int[] data = new int[]{1,2,3,4,5,6,7,0,8};
		Puzzle p = new Puzzle(data);
		System.out.println("hashcode: " + p.hashCode());


		BreadthFirst f = new BreadthFirst(p);
		Puzzle solved = f.solve();
		OptimalHeuristic h = new OptimalHeuristic();
		System.out.println("num moves: " + solved.getNumMoves());
		//OptimalHeuristic.m.put(solved.hashCode(),solved.getNumMoves());
		float cost = h.getCost(p);
		System.out.println(cost); //should be 1

	}

	public void testAStarOptimal(){
		int[] data = new int[]{1,2,3,4,0,6,7,5,8};
		OptimalHeuristic optimalHeuristic = new OptimalHeuristic();
		optimalHeuristic.init();
		Puzzle p = new Puzzle(data);
		Solver solver = new AStar(optimalHeuristic,p);
		Puzzle solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println("expansions: " +solver.getNumExpansions());
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");

		p = Puzzle.generatePuzzle();
		p.print(System.out);
		System.out.println("*******");
		solver = new AStar(optimalHeuristic, p);
		solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println("expansions: " +solver.getNumExpansions());
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");

		int[] worstCase = new int[]{6, 4, 7,
									8, 5,0,
									3, 2, 1};
		Puzzle worst = new Puzzle(worstCase);
		solver = new AStar(optimalHeuristic, worst);
		solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println("expansions: " +solver.getNumExpansions());
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");
	}

	public void testAStarManhattan(){
		int[] data = new int[]{1,2,3,4,0,6,7,5,8};
		Puzzle p = new Puzzle(data);
		Heuristic optimalHeuristic = new OptimalHeuristic();
		optimalHeuristic.init();
		Heuristic heuristic = new ManhattanHeuristic();

		Solver solver = new AStar(heuristic,p);
		Solver optimal = new AStar(optimalHeuristic,p);
		Puzzle optimalSolved = optimal.solve();

		Puzzle solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println("expansions: " + solver.getNumExpansions());
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");
		System.out.println("extra expansions: "+ (solver.getNumExpansions()-optimal.getNumExpansions()));
		System.out.println("num moves: " + solved.getNumMoves());
		System.out.println("extra moves: "+(solved.getNumMoves()- optimalSolved.getNumMoves()));

		p = new Puzzle(new int[]{0,7,1,3,6,8,5,4,2});
		p.print(System.out);
		solver = new AStar(heuristic, p);
		optimal = new AStar(optimalHeuristic,p);
		optimalSolved = optimal.solve();

		solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println("expansions: " +solver.getNumExpansions());
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");
		System.out.println("extra expansions: "+ (solver.getNumExpansions()-optimal.getNumExpansions()));
		System.out.println("num moves: " + solved.getNumMoves());
		System.out.println("extra moves: "+(solved.getNumMoves()- optimalSolved.getNumMoves()));

		int[] worstCase = new int[]{6, 4, 7,
				8, 5,0,
				3, 2, 1};
		Puzzle worst = new Puzzle(worstCase);
		solver = new AStar(heuristic, worst);
		optimal = new AStar(optimalHeuristic,worst);
		optimalSolved = optimal.solve();

		solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println("expansions: " + solver.getNumExpansions());
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");
		System.out.println("extra expansions: "+ (solver.getNumExpansions()-optimal.getNumExpansions()));
		System.out.println("num moves" + solved.getNumMoves());
		System.out.println("optimal moves: "+optimalSolved.getNumMoves());
		System.out.println("extra moves: "+(solved.getNumMoves()- optimalSolved.getNumMoves()));
	}

	public void testAverageExtraExpansions(){
		int maxNumStates = 181420;
		int mapSize = 262144;
		int state0 = 12345678;
		long start = System.nanoTime();
		Puzzle initial = new Puzzle(new int[]{1,2,3,4,5,6,7,8,0});
		Queue<Puzzle> queue = new LinkedList<Puzzle>();
		HashMap m = new HashMap(mapSize,1.0f);
		//HashMap m = new HashMap();
		queue.add(initial);

		while(!queue.isEmpty()){
			Puzzle next = queue.poll();

			if(!m.containsKey(next.hashCode())){
				m.put(next.hashCode(), next);
				//if(numMoves<20){
				for(Puzzle.Move move : next.getPossibleMoves()){
					queue.add(next.swap(move));
				}
				//}
			}
		}

		//Run a monte-carlo simulation to get the average extra swaps
		List options = new ArrayList(m.values());
		Heuristic h = new RyanHeuristic(31);
		h.init();
		Heuristic manhattan = new ManhattanHeuristic();
		Random r = new Random(System.nanoTime());
		long totalScore=0;
		long totalOver = 0;
		long time =0;
		int numTries=10000;
		long manScore=0;
		for(int i=0;i<numTries;i++){
	    	int idx = Math.abs(r.nextInt())%181420;
			Puzzle random = (Puzzle)options.get(idx);
			Puzzle p = new Puzzle(random.puzzle);
			int optimalMoves = random.getNumMoves();

			//Solver manSolver = new AStar(manhattan,p);
			//Puzzle mSolved = manSolver.solve();
			//manScore+=manSolver.getNumExpansions()-optimalMoves;
			Solver solver = new AStar(h,p);
			Puzzle solved = solver.solve();
			time+=solver.getTime();
			long extraExpansions = (solver.getNumExpansions()- optimalMoves);
			totalScore+=extraExpansions;
			long extraSteps =(solved.getNumMoves()- optimalMoves);
			totalOver+=extraSteps;
			if(extraSteps>3){
				System.out.println("extra steps: "+extraSteps);
			}
			if(i%1000==0){
				System.out.println(i);
			}
		}
		//System.out.println("Average extra manhattan expansions: "+manScore/(1.0*numTries));
		System.out.println("Average extra expansions: "+totalScore/(1.0*numTries));
		System.out.println("Average extra steps: "+ totalOver/(1.0*numTries));
		System.out.println("Average time: "+(time/1000.0/1000.0/numTries)+" ms");
		long elapsed = (System.nanoTime()-start)/1000/1000;
		System.out.println("time: "+ elapsed + " ms");

	}

	public void testAStarRyanHeuristic(){
		int[] data = new int[]{1,2,3,4,0,6,7,5,8};
		Puzzle p = new Puzzle(data);
		Heuristic optimalHeuristic = new OptimalHeuristic();
		optimalHeuristic.init();
		int depth=11;
		Heuristic ryan = new RyanHeuristic(depth);
		ryan.init();
		Solver solver = new AStar(ryan,p);
		Solver optimal = new AStar(optimalHeuristic,p);
		Puzzle optimalSolved = optimal.solve();

		Puzzle solved = solver.solve();
		solved.print(System.out);

		System.out.println("expansions: " +solver.getNumExpansions());
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");
		System.out.println("extra expansions: "+ (solver.getNumExpansions()-optimal.getNumExpansions()));
		System.out.println("extra moves: "+(solved.getNumMoves()- optimalSolved.getNumMoves()));

		p = new Puzzle(new int[]{0,7,1,3,6,8,5,4,2});
		p.print(System.out);
		solver = new AStar(ryan, p);
		optimal = new AStar(optimalHeuristic,p);
		optimalSolved = optimal.solve();

		solved = solver.solve();
		solved.print(System.out);

		System.out.println("expansions: " +solver.getNumExpansions());
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");
		System.out.println("extra expansions: "+ (solver.getNumExpansions()-optimal.getNumExpansions()));
		System.out.println("extra moves: "+(solved.getNumMoves()- optimalSolved.getNumMoves()));
		/*
		int[] worstCase = new int[]{6, 4, 7,
				8, 5,0,
				3, 2, 1};
		Puzzle worst = new Puzzle(worstCase);
		solver = new AStar(new RyanHeuristic(depth), worst);
		optimal = new AStar(optimalHeuristic,worst);
		optimalSolved = optimal.solve();

		solved = solver.solve();
		solved.print(System.out);
		System.out.println("expansions: " +solver.getNumExpansions());
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");
		System.out.println("extra expansions: "+ (solver.getNumExpansions()-optimal.getNumExpansions()));

		System.out.println("extra moves: "+(solved.getNumMoves()- optimalSolved.getNumMoves()));
		*/
	}


	public void testReadInput(){
		System.out.println("type board");
		Puzzle p = Puzzle.readBoard(System.in);
		System.out.println("solving breadth first");
		Solver s = new BreadthFirst(p);
		Puzzle solved = s.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
	}

	public void test(){
		/**
		 * _ 3 5
		 * 4 8 1
		 * 7 2 6
		 *
		 */
		int[] data = new int[]{0,3,5,4,8,1,7,2,6};
		Puzzle p = new Puzzle(data);
		Solver s = new DepthFirst(p);
		Puzzle solved = s.solve();
		solved.printMoves(System.out);
	}



	public void testIterativeDeepening(){
		int[] data = new int[]{1,2,3,4,5,6,7,0,8};
		Puzzle p = new Puzzle(data);
		Solver solver = new IterativeDeepening(p);
		Puzzle solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println(solver.getTime() + " nanoseconds");

		p = Puzzle.generatePuzzle();
		solver = new IterativeDeepening(p);
		solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println(solver.getTime() + " nanoseconds");
	}

	public void testDepthLimited(){
		int[] data = new int[]{1,2,3,4,5,6,7,0,8};
		Puzzle p = new Puzzle(data);
		Solver solver = new DepthLimited(p,31);
		Puzzle solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println(solver.getTime() + " nanoseconds");

		data = new int[]{1,2,3,0,5,6,4,7,8};
		p = new Puzzle(data);
		solver = new DepthLimited(p,31);
		solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println(solver.getTime() + " nanoseconds");

		data = new int[]{6, 4, 7,
				8, 5, 0,
				3, 2, 1};
		p = new Puzzle(data);
		solver = new DepthLimited(p,100);
		solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println("\nnum moves" + solved.getNumMoves());
		System.out.println(solver.getTime() + " nanoseconds");
	}


	public void testDepthFirst2(){
		int[] data = new int[]{1,2,3,4,5,6,7,0,8};
		Puzzle p = new Puzzle(data);
		Solver solver = new DepthFirst(p);
		Puzzle solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");

		data = new int[]{1,2,3,0,5,6,4,7,8};
		p = new Puzzle(data);
		solver = new DepthFirst(p);
		solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");
	}

	//@NOTE THIS CAUSES A STACK OVERFLOW, DepthFirst uses my own stack on the heap.
	public void testDepthFirst(){
		int[] data = new int[]{1,2,3,4,5,6,7,0,8};
		Puzzle p = new Puzzle(data);
		Solver solver = new DepthFirstRecursive(p);
		Puzzle solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println(solver.getTime() + " nanoseconds");

		data = new int[]{1,2,3,0,5,6,4,7,8};
		p = new Puzzle(data);
		solver = new DepthFirstRecursive(p);
		solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println(solver.getTime() + " nanoseconds");
	}


	public void testBreadthFirst(){
		int[] data = new int[]{1,2,3,4,5,6,7,0,8};
		Puzzle p = Puzzle.generatePuzzle();
		Solver solver = new BreadthFirst(p);
		Puzzle solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");

		data = new int[]{1,2,3,0,5,6,4,7,8};
		p = new Puzzle(data);
		solver = new BreadthFirst(p);
		solved = solver.solve();
		solved.print(System.out);
		solved.printMoves(System.out);
		System.out.println(solver.getTime()/1000.0/1000.0 + " ms");
	}

	public void testPrintMoves() throws Exception {

	}

	public void testEquals() throws Exception {

	}

	public void testIsSolved() throws Exception {
		int[] data = new int[]{1,2,3,4,5,6,7,8,0};
		Puzzle p = new Puzzle(data);
		assertTrue(p.isSolved());

		data = new int[]{1,2,3,4,5,6,7,0,8};
		p = new Puzzle(data);
		assertFalse(p.isSolved());

	}

	public void testSwap() throws Exception {
		int[] data = new int[]{1,2,3,4,5,6,7,0, 8};
		Puzzle p = new Puzzle(data);
		p.print(System.out);
		System.out.println("\n--------------");

		Puzzle next = p.swap(Puzzle.Move.RIGHT);
		next.print(System.out);

		System.out.println("\n**************");
		next = p.swap(Puzzle.Move.LEFT);
		next.print(System.out);

		System.out.println("\n**************");
		next = p.swap(Puzzle.Move.UP);
		next.print(System.out);

		System.out.println("\n**************");
		next = next.swap(Puzzle.Move.DOWN);
		next.print(System.out);

	}
}
