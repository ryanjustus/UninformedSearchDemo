package com.ryanjustus.ai;

import java.security.PublicKey;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 9/5/12
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class DepthLimited extends SolverSkeleton implements Solver{

	private final Puzzle p;
	private final int maxDepth;
	public DepthLimited(Puzzle p, int maxDepth){
		this.p=p;
		this.maxDepth=maxDepth;
	}

	public Puzzle solve(){
		long start = System.nanoTime();
		Puzzle solution;
		if(p.isSolved()){
			solution=p;
		}else{
			Stack<Puzzle> stack = new Stack<Puzzle>();
			stack.push(p);
			solution = solve_r(stack);
		}
		this.time = System.nanoTime()-start;
		return solution;
	}

	public Puzzle solve_r(Stack<Puzzle > stack){

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
