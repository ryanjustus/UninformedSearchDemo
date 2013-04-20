package com.ryanjustus.ai;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 9/12/12
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class RyanHeuristic implements Heuristic{
	private IntIntHashMap m=null;
	//private IntIntHashMap visited=null;
	private final int depthMemoized;
	boolean useMemo;

	public RyanHeuristic(int depthMemoized){
		this.depthMemoized = depthMemoized;
		//this.visited = new IntIntHashMap(10000,0.75f);
	}

	public void init(){
		//visited.clear();
		long start = System.nanoTime();
		if(m==null){
		useMemo=false;
		int mapSize = 262144;
		Puzzle initial = new Puzzle(new int[]{1,2,3,4,5,6,7,8,0});
		Queue<Puzzle> queue = new LinkedList<Puzzle>();
		m = new IntIntHashMap(mapSize,1.0f);
		//HashMap m = new HashMap();
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
			long elapsed = System.nanoTime()-start;
			System.out.println("RyanHeuristic Map size: "+m.size());
			System.out.println("RyanHeuristice init time: "+elapsed/1000.0/1000.0);
		}
	}

	public float getCost(Puzzle p) {
		int hashCode = p.hashCode();
		int length = p.getNumMoves();
		float result;
		//If we have already visited this state with a shorter path return a high cost
		/*
		if(visited.containsKey(hashCode) && length>visited.get(hashCode)){
			System.out.println("visited");
			return 1000;
		}else{
			visited.put(hashCode, length);
		}

		//Don't ever go longer than 31
		if(length>35){
			System.out.println("over");
			return 1000;
		}
		*/

		if(m.containsKey(hashCode)){
			//System.out.println("using memo");
			int remaining = m.get(hashCode);
			result = remaining*1.1f;
		}else {
	//System.out.println("depth not memo");
			result = 31-depthMemoized;
			int[] data = p.puzzle;
			for(int i=0;i<9;i++){
				if(data[i]==0)
					continue;
				int x = i%3;
				int y = i/3;
				int dx = targetX(data[i])-x;
				int dy = targetY(data[i])-y;
				int dist=Math.abs(dx)+Math.abs(dy);
				result+=dist;
			}
		}
		return result;
	}

	int targetX(int num){
		switch(num){
			case 1: return 0;
			case 2: return 1;
			case 3: return 2;
			case 4: return 0;
			case 5: return 1;
			case 6: return 2;
			case 7: return 0;
			case 8: return 1;
			case 0: return 2;
			default: System.err.println("shouldn't reach"); return -1;
		}
	}

	int targetY(int num){
		switch(num){
			case 1: return 0;
			case 2: return 0;
			case 3: return 0;
			case 4: return 1;
			case 5: return 1;
			case 6: return 1;
			case 7: return 2;
			case 8: return 2;
			case 0: return 2;
			default: System.err.println("shouldn't reach"); return -1;
		}
	}
}
