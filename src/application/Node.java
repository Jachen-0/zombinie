package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Node extends WorldObject{
	
	
	public Node(OrderedPair pos, int id) {
		super(null, new OrderedPair(pos.x, pos.y), new OrderedPair(0,0), false);
		
	}
}
