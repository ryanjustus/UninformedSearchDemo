package com.ryanjustus.ai;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 9/12/12
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class OptimalHeuristic implements Heuristic{
	private IntIntHashMap m;
	private final int depthMemoized;

	public OptimalHeuristic(){
		this.depthMemoized = 31;
	}

	public void init(){
		int maxNumStates = 181420;
		int mapSize = 262144;
		long start = System.nanoTime();
		Puzzle initial = new Puzzle(new int[]{1,2,3,4,5,6,7,8,0});
		Queue<Puzzle> queue = new LinkedList<Puzzle>();
		m = new IntIntHashMap(mapSize,1.0f);

		queue.add(initial);
		while(!queue.isEmpty()){
			Puzzle next = queue.poll();

			if(!m.containsKey(next.hashCode())){

				int numMoves = next.getNumMoves();

				m.put(next.hashCode(),numMoves);
				if(numMoves<depthMemoized){
					for(Puzzle.Move move : next.getPossibleMoves()){
						queue.add(next.swap(move));
					}
				}
			}
		}
		long elapsed = (System.nanoTime()-start)/1000/1000;
		System.out.println("optimal heuristic initialization time: "+ elapsed + " ms");
		System.out.println("optimal heuristice map size: "+ m.size());
	}


	public float getCost(Puzzle p){
		int hashCode = new Integer(p.hashCode());
		int cost =  m.get(hashCode);
		return (cost*1.1f);
	}
}
