package com.ryanjustus.ai;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/29/12
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DepthFirst extends SolverSkeleton implements Solver {
	Puzzle p;
	Stack<Puzzle.Move> stack;
	DepthFirst(Puzzle p){
		stack = new Stack<Puzzle.Move>();
		this.p=p;
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
		//Add initial move options onto stack
		//Stack
		int i=0;
		while(!stack.empty()){
			Puzzle current = stack.pop();
			if(current.isSolved()){
				return current;
			}
                        int hashCode = current.hashCode();
			if(!visited.containsKey(hashCode)){
				visited.put(hashCode, 0);
				for(Puzzle.Move move: current.getPossibleMoves()){
					stack.push(current.swap(move));
				}
			}
		}
		return null;
	}
}
