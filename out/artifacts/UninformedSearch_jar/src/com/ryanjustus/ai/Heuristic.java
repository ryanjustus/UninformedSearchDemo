package com.ryanjustus.ai;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

public interface Heuristic{
	public float getCost(Puzzle p);
	public void init();
}
