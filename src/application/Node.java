package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Node extends WorldObject{
	
	public int id;
	public Node(OrderedPair pos, int id) {
		super(null, new OrderedPair(pos.x, pos.y), new OrderedPair(0,0), false);
		this.id = id;

	}
	
	public Node[] getConnections(GM gm) {
		try {
			Scanner input = new Scanner(new File("Connected Nodes.txt"));
			boolean found = false;
			Node[] connectedNodes;
			while (!found) {
			while(input.hasNextLine()) {	
				String entireLine = input.nextLine();
				if (entireLine.charAt(0) == id + 48) {
					String connectedIDs = entireLine.substring(2, entireLine.length()-1);
					found = true;
					String[] seperateIDs = connectedIDs.split(" ");
					connectedNodes = new Node[seperateIDs.length];
					for (int i = 0; i < seperateIDs.length; i++) {
						connectedNodes[i] = gm.getNodes()[Integer.parseInt(seperateIDs[i])];
						System.out.println("Node " + id + " connected to Node " + connectedNodes[0].id);
					}
					return connectedNodes;
				}
				
			}
			break;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error finding file");
		}
		return null;
	}
}
