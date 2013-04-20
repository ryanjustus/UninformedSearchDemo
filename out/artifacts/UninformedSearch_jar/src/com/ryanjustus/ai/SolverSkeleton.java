package com.ryanjustus.ai;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/29/12
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SolverSkeleton {
	int mapSize = 262144; //This size is large enough to never need a resize and is divisible by two for fast mod
	Puzzle current;
	IntIntHashMap visited = new IntIntHashMap(mapSize, 1.0f);
	int blank=-1;
	long time;
	long expansions=0;
	final Stack<Puzzle.Move> moves;

	public SolverSkeleton(){
		moves = new Stack<Puzzle.Move>();
	}

	public long getTime(){
		return time;
	}

	public long getNumExpansions(){
		return visited.size();
	}
}
