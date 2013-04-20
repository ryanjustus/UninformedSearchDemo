package com.ryanjustus.ai;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 9/6/12
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class BiDirectional extends SolverSkeleton implements Solver{
	final Puzzle p;
	long numExpansions;
	public BiDirectional(Puzzle p){
		this.p=p;
	}

	public Puzzle solve(){
		long start = System.nanoTime();
		Puzzle solution;
		if(p.isSolved()){
			solution=p;
		}else{
			solution = solve_r(p);
		}
		this.time = System.nanoTime()-start;
		return solution;
	}

	private Puzzle solve_r(Puzzle p){
		Puzzle solved = new Puzzle(new int[]{1,2,3,4,5,6,7,8,0});
		//Set up the set of puzzles from the solved direction
		//Map<Puzzle,Puzzle> fromSolvedVisited = new HashMap<Puzzle,Puzzle>();
		IntHashMap fromSolvedVisited = new IntHashMap(this.mapSize,1.0f);
		fromSolvedVisited.put(solved.hashCode(), solved);
		Queue<Puzzle> fromSolvedQueue = new LinkedList<Puzzle>();
		fromSolvedQueue.add(solved);

		//Set up the queue from the unsolved direction
		Queue<Puzzle> queue = new LinkedList<Puzzle>();
		queue.add(p);

		while(!queue.isEmpty()){

			//Do the moves from the solved direction
			if(!fromSolvedQueue.isEmpty()){
				Puzzle fromSolvedCurrent = fromSolvedQueue.poll();
				int fromSolvedHashCode = fromSolvedCurrent.hashCode();
				if(!fromSolvedVisited.containsKey(fromSolvedHashCode)){
					fromSolvedVisited.put(fromSolvedHashCode,fromSolvedCurrent);
					for(Puzzle.Move m: fromSolvedCurrent.getPossibleMoves()){
						Puzzle next = fromSolvedCurrent.swap(m);
						fromSolvedQueue.add(next);
					}
				}
			}

			//Do the moves in the unsolved direction
			Puzzle current = queue.poll();
			int hashCode = current.hashCode();
			if(!visited.containsKey(hashCode)){
				visited.put(hashCode, 0);
				for(Puzzle.Move m: current.getPossibleMoves()){
					Puzzle next = current.swap(m);
					int nextHashCode = next.hashCode();
					if(fromSolvedVisited.containsKey(nextHashCode)){
						Puzzle fromSolved = (Puzzle)fromSolvedVisited.get(nextHashCode);
						return join(fromSolved,next);
					}else if(next.isSolved()){
						this.numExpansions = visited.size();
						return next;
					}else{
						queue.add(next);
					}
				}
			}
		}
		return null;
	}

	Puzzle join(Puzzle fromSolved, Puzzle current){
		Stack<Puzzle.Move> fromSolvedMoves = fromSolved.getMoves();
		this.numExpansions = fromSolvedMoves.size() + this.visited.size();
		while(!fromSolvedMoves.isEmpty()){
			Puzzle.Move m = fromSolvedMoves.pop();
			if(m!= Puzzle.Move.START){
				current=current.swap(m);
			}
		}
		return current;
	}

	public long getNumExpansions(){
		return numExpansions;
	}

}
