package com.ryanjustus.ai;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 9/5/12
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class IterativeDeepening extends SolverSkeleton implements Solver {
	private final Puzzle p;
	private long numExpansions;

	public IterativeDeepening(Puzzle p){
		this.p=p;
	}

	public Puzzle solve(){
		long start = System.nanoTime();
		numExpansions=0;
		Puzzle solution = null;
		if(p.isSolved()){
			solution=p;
		}else{
			int depth=1;   //We start at 2 because I always have a move NONE at the start
			while(solution==null){
				this.visited.clear();
				solution = solve_r(p,depth++);
				numExpansions+=visited.size();
			}
		}
		this.time = System.nanoTime()-start;
		return solution;
	}

	public long getNumExpansions(){
		return numExpansions;
	}

	public Puzzle solve_r(Puzzle p, int maxDepth){
		//Add initial move options onto stack
		//Stack
		System.out.println("searching depth "+ maxDepth);
		Stack<Puzzle> stack = new Stack<Puzzle>();
		stack.push(p);

		while(!stack.empty()){
			Puzzle current = stack.pop();
			if(current.isSolved()){
				return current;
			}
            int hashCode = current.hashCode();
			if(
					current.getNumMoves()<maxDepth &&
							(!visited.containsKey(hashCode)||
									(visited.containsKey(hashCode) && visited.get(hashCode)>current.getNumMoves()))
			  )
			{
				visited.put(hashCode,current.getNumMoves());
				for(Puzzle.Move move: current.getPossibleMoves()){
					stack.push(current.swap(move));
				}
			}
		}
		return null;
	}
}

