package com.ryanjustus.ai;


import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 9/10/12
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class CostGenerator {
	public CostGenerator(){

	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		int maxNumStates = 181420;
		int mapSize = 262144;
		int state0 = 12345678;
		long start = System.nanoTime();
		Puzzle initial = new Puzzle(new int[]{1,2,3,4,5,6,7,8,0});
		Queue<Puzzle> queue = new LinkedList<Puzzle>();
		IntHashMap m = new IntHashMap(mapSize,1.0f);
		//HashMap m = new HashMap();
		queue.add(initial);
		while(!queue.isEmpty()){
			Puzzle next = queue.poll();
                        
			if(!m.containsKey(next.hashCode())){

				int numMoves = next.getNumMoves();
                                if(numMoves==31){
                                    next.print(System.out);
                                }
				m.put(next.hashCode(),numMoves);
				//if(numMoves<20){
					for(Puzzle.Move move : next.getPossibleMoves()){
						queue.add(next.swap(move));
					}
				//}
			}
		}
		long elapsed = (System.nanoTime()-start)/1000/1000;
		System.out.println("time: "+ elapsed + " ms");
		System.out.println("map size: "+ m.size());

	}

}
