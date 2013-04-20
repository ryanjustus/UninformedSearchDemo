package com.ryanjustus.ai;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/29/12
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Puzzle {

	final int[] puzzle;
	int blank;
	final Puzzle parent;
	final Move move;
	final int numMoves;
	public static Puzzle generatePuzzle(){
		/**
		 *
		 */
		int[] puzzle = {1,2,3,4,5,6,7,8,0};
		Puzzle p = new Puzzle(puzzle);
		Random r = new Random();
		for(int i=0;i<30;i++){
			List<Move> options = new ArrayList<Move>(p.getPossibleMoves());
			int idx = r.nextInt(Integer.MAX_VALUE)%options.size();
			Move m = options.get(idx);
			p = p.swap(m);
		}

		return new Puzzle(p.getBoard());
	}

	/**
	 * Copy constructor
	 * @param other
	 */
	private Puzzle(Puzzle other, Move m){
		this.puzzle = new int[9];
		System.arraycopy(other.puzzle,0,this.puzzle,0,9);
		/*
		for(int i=0;i<9;i++){
			puzzle[i]=other.puzzle[i];
			if(other.puzzle[i]<=0){
				blank=i;
			}
		}
		*/
		this.blank=other.blank;
		this.move = m;
		this.parent=other;
		this.numMoves=parent.numMoves+1;
	}

	public Puzzle(int[] state){
		puzzle=state;
		for(int i=0;i<puzzle.length;i++){
			if(puzzle[i]<=0){
				blank=i;
				break;
			}
		}
		this.parent=null;
		this.numMoves=0;
		this.move = Move.START;
	}

	public Puzzle(){
		puzzle = new int[9];
		for(int i=0;i<8;i++){
			puzzle[i]=i+1;
		}
		blank=8;
		this.parent=null;
		this.move=Move.START;
		this.numMoves=0;
	}

	public enum Move{
		UP(-3),
		LEFT(-1),
		RIGHT(+1),
		DOWN(3),
		START(0);

		final int offset;
		Move(int offset){
			this.offset=offset;
		}

		int getPosition(int blank){
			return blank+offset;
		}

		public String toString(){
			return this.name();
		}
	}



	/**
	 * gets all the possible moves for a given blank spot.  assumes a 3x3 board
	 */
	public List<Move> getPossibleMoves(){
		List<Move> moves = new ArrayList<Move>(4);
		/*
		if(blank!=0 && blank!=3 && blank!=5 && move!=Move.LEFT){
			moves.add(Move.LEFT);
		}
		if(blank!=2 && blank!=5 && blank!=8 && move!=Move.RIGHT){
			moves.add(Move.RIGHT);
		}
		if(blank>2 && move!=Move.UP){
			moves.add(Move.UP);
		}
		if(blank<6 && move!=Move.DOWN){
			moves.add(Move.DOWN);
		}
                */
                /*
		if(blank%3!=0){
			moves.add(Move.LEFT);
		}
		if(blank%3!=2){
			moves.add(Move.RIGHT);
		}
		if(blank>2){
			moves.add(Move.UP);
		}
		if(blank<6){
			moves.add(Move.DOWN);
		}
                */
               // moves.remove(this.move);
                
        
                
		if(blank%3!=0 && move!=Move.RIGHT){
			moves.add(Move.LEFT);
		}
		if(blank%3!=2 && move!=Move.LEFT){
			moves.add(Move.RIGHT);
		}
		if(blank>2 && move!=Move.DOWN){
			moves.add(Move.UP);
		}
		if(blank<6 && move!=Move.UP){
			moves.add(Move.DOWN);
		}
                
		return moves;
	}

	public int[] getBoard(){
		return Arrays.copyOf(puzzle, 9);
	}


	public void printMoves(PrintStream out){
		Stack<Move> s = this.getMoves();
		while(!s.isEmpty()){
			Move m = s.pop();
			out.print(m.toString());
			if(!s.isEmpty()){
				out.print(", ");
			}
		}
		out.print("\n");
	}

	String[] read(InputStream in){
		Scanner s = new Scanner(in);
		Pattern p = Pattern.compile("[\\d_]{1}");
		String[] puzzle = new String[9];
		int idx=0;
		while(s.hasNext(p)){
			String c = s.next(p);
			puzzle[idx++]= (c.equals("_") ? "" : c);
		}
		return puzzle;
	}

	void print(PrintStream out){
		for(int i=0;i<puzzle.length;i++){
			if(i%3==0){
				out.print(System.getProperty("line.separator"));
			}
			System.out.print((puzzle[i]>0 ? puzzle[i] : "_")+" ");
		}
	}

	//Compares if the configuration of this puzzle is the same as another;
	public boolean equals(Object other){
		if(other instanceof Puzzle){
			Puzzle p = (Puzzle)other;
			if(this.hashCode()==p.hashCode()){
				for(int i=0;i<puzzle.length;i++){
					if(puzzle[i]!=p.puzzle[i])
						return false;
				}
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	public boolean isSolved(){
		for(int i=0;i<puzzle.length-1;i++){
			if(puzzle[i]!=i+1){
				return false;
			}
		}
		return true;
	}

	public Puzzle swap(Move m){
		Puzzle next = new Puzzle(this, m);

		int other = m.getPosition(blank);
		if(other<0)
			System.out.println("Move: "+m+" Blank: "+blank+ " Other: "+other);
		next.puzzle[blank] = puzzle[other]; //Move the number to the
		next.puzzle[other]=0; //Make the one it came from blank
		next.blank = other; //update the index of the blank
		return next;
	}

	Stack<Move> getMoves(){
		Stack<Move> path = new Stack<Move>();
		Puzzle current = this;
		while(current!=null){
			path.push(current.move);
			current=current.parent;
		}
		return path;
	}


	public int getNumMoves(){
		return this.numMoves;
	}

	public static Puzzle readBoard(InputStream in){
		Scanner s = new Scanner(in);
		int[] state = new int[9];
		String next=null;
		int index=0;
		Pattern p = Pattern.compile("[1-8_]");

		while(index<9 && (next=s.next(p))!=null){
			System.out.println("read input: "+next);
			if(next.equals("_")){
				next="0";
			}
			Integer i = Integer.parseInt(next);
			state[index] = i.intValue();
			index++;
		}
		System.out.println("input complete");
		return new Puzzle(state);
	}

	@Override
	public int hashCode(){
		return -12345678+1*puzzle[8]+10*puzzle[7]+100*puzzle[6]+1000*puzzle[5]+10000*puzzle[4]
				+100000*puzzle[3]+1000000*puzzle[2]+10000000*puzzle[1]+100000000*puzzle[0];
	}
}
