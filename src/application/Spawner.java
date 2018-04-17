package application;

import java.util.ArrayList;

public class Spawner extends WorldObject{
	ArrayList<WorldObject> objs;
	
	public Spawner(OrderedPair pos) {
		super(null, pos, new OrderedPair(0,0), false);
	}
}