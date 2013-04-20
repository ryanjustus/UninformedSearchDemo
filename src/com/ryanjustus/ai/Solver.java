package com.ryanjustus.ai;

/**
 * Created with IntelliJ IDEA.
 * User: ryan
 * Date: 8/29/12
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Solver {
	public Puzzle solve();
	public long getTime();
	public long getNumExpansions();
}
