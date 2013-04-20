package com.ryanjustus.ai;

import sun.net.www.http.Hurryable;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 9/12/12
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManhattanHeuristic implements Heuristic{
	public float getCost(Puzzle p) {
		int result = 0;
		int[] data = p.puzzle;
		for(int i=0;i<9;i++){
			if(data[i]==0)
				continue;
			int x = i%3;
			int y = i/3;
			int dx = targetX(data[i])-x;
			int dy = targetY(data[i])-y;
			int dist=Math.abs(dx)+Math.abs(dy);
			//System.out.println(data[i]+"("+x+","+y+") :"+dist);
			result+=dist;
		}

		//System.out.println("total distance: "+result);
		return p.getNumMoves()+result;
	}

	public void init(){

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
