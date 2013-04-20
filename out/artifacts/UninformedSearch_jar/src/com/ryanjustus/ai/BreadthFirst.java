package com.ryanjustus.ai;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/29/12
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class BreadthFirst extends SolverSkeleton implements Solver{
	 	private Puzzle p;
		private Queue<Puzzle.Move> moves = new LinkedList<Puzzle.Move>();
		public BreadthFirst(Puzzle p){
			this.p=p;
		}

		public Puzzle solve(){
			long start = System.nanoTime();
			Puzzle solution;
			if(p.isSolved()){
				solution=p;
			}else{
				Queue<Puzzle> queue = new LinkedList<Puzzle>();
				queue.add(p);
				solution = solve_r(queue);
			}
			this.time = System.nanoTime()-start;
			return solution;
		}

		public Puzzle solve_r(Queue<Puzzle> queue){
			while(!queue.isEmpty()){
				Puzzle current = queue.poll();
				if(current.isSolved()){
					return current;
				}
                int hashCode = current.hashCode();
				if(!visited.containsKey(hashCode)){
					visited.put(hashCode, 0);
					for(Puzzle.Move m: current.getPossibleMoves()){
						queue.add(current.swap(m));
					}
				}
			}
			return null;
		}
}
