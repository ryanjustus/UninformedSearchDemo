package com.ryanjustus.ai;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/29/12
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DepthFirstRecursive extends SolverSkeleton implements Solver {

	Puzzle p;
	public DepthFirstRecursive(Puzzle p){
		this.p=p;
	}

	public Puzzle solve(){
		long start = System.nanoTime();
		Puzzle solution = solve_r(p);
		this.time = System.nanoTime()-start;
		return solution;
	}

	public Puzzle solve_r(Puzzle p){

		if(p.isSolved()){
			return p;
		}
		for(Puzzle.Move move: p.getPossibleMoves()){
			Puzzle next = p.swap(move);
                        int hashCode = next.hashCode();
			if(!visited.containsKey(hashCode)){
				visited.put(hashCode,0);
				if(!next.isSolved()){
					next = this.solve_r(next);
				}

				if(next!=null && next.isSolved()){
					return next;
				}
			}else{
				continue;
			}
		}
		//System.out.println("No solutions found");
		return null;
	}
}
