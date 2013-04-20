package com.ryanjustus.ai;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 9/12/12
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class AStar extends SolverSkeleton implements Solver {
	private final Heuristic h;
	private final Puzzle p;
	int expansions;
	public AStar(Heuristic h, Puzzle p){
		this.h=h;
		this.p=p;
	}

	public Puzzle solve(){
		expansions=0;
		long start = System.nanoTime();

		Puzzle solution;
		if(p.isSolved()){
			solution=p;
		}else{
			//h.init();
			solution = solve_r();
		}
		this.time = System.nanoTime()-start;
		return solution;
	}

	private Puzzle solve_r(){
		Comparator<Puzzle> comparator = new Comparator<Puzzle>() {
			public int compare(Puzzle p1, Puzzle p2) {
				//System.out.println("c: "+h.getCost(p1)+"::"+h.getCost(p2));
				return (p1.getNumMoves()+h.getCost(p1))-(p2.getNumMoves()+h.getCost(p2))>=0.0f ? 1:-1;
			}
		};
		PriorityQueue<Puzzle> pq = new PriorityQueue<Puzzle>(10,comparator);
		pq.add(p);
		while(!pq.isEmpty()){

			Puzzle next = pq.poll();
			//System.out.println("current cost: " +h.getCost(next));
			int moveLength = next.getNumMoves();
			if(next.isSolved()){
				return next;
			}
			int hashCode = next.hashCode();
			if(!this.visited.containsKey(hashCode) || visited.get(hashCode)>moveLength){
				visited.put(hashCode,next.getNumMoves());
	            this.expansions++;
				for(Puzzle.Move m : next.getPossibleMoves()){
					pq.add(next.swap(m));
				}
			}
		}
		return null;
	}

	public long getNumExpansions(){
		return expansions;
	}
}
