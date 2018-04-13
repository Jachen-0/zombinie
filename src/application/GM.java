package application;


public class GM {
	private int score;
	private Node[] nodeNetwork;
	private int round;
	private Player player;
	private int zombieLimit;
	private int zombiesAlive;
	public GM() {
		score = 0;
		round = 1;
		SetZombieLimit();
		zombiesAlive = 1;
	}
	public int ZombiesLeft() {
		return zombiesAlive;
	}
	public void IncrementZombie() {
		zombiesAlive ++;
	}
	
	public void DecrementZombie() {
		zombiesAlive --;
	}
	
	public int GetZombieLimit() {
		return zombieLimit;
	}
	public void SetZombieLimit() {
		zombieLimit = 3*round ;
	}
	public void initializePlayer(Player player) {
		this.player = player;	
	}
	public Player GetPlayer() {
		return player;
	}
	public void initializeNodeNetwork(Node[] nodes) {
		nodeNetwork = nodes;	
		for (Node n: nodeNetwork) {
			n.getConnections(this);
		}
	}
	public Node[] getNodes() {
		return nodeNetwork;
	}
	public int GetRound() {
		return round;
	}
	public void IncrementRound() {
		round++;
		SetZombieLimit();
	}
	
	public int GetScore() {
		return score;
	}
	public int nearestToPlayer() {
		return nearestTo(player);
	}
	public int nearestTo(WorldObject obj) {
		int nearestIndex = 0;
		double currentNearest = Math.sqrt(Math.pow(nodeNetwork[0].pos.x - obj.pos.x, 2) + Math.pow(nodeNetwork[0].pos.y - obj.pos.y, 2)); 
		for(int i = 0; i < nodeNetwork.length; i++) {
			double dist = Math.sqrt(Math.pow(nodeNetwork[i].pos.x - obj.pos.x, 2) + Math.pow(nodeNetwork[i].pos.y - obj.pos.y, 2));
			if (dist < currentNearest) {
				currentNearest = dist;
				nearestIndex = i;
				
			}
		}
		return nearestIndex;
	}
}
