import java.util.* ;

/*
 *  Utility class to create Edge for the graph.
 */
 class Edge {
	
	public Vertex destination;
	public int distance;
	
	public Edge(Vertex dest, int distance) { //this data structure consists of dest vertex 
		                                     //and the distance b/w source and this destination vertex. 
		this.destination = dest;
		this.distance = distance;
	}
}

 /*
  *  Utility class to create Vertex/Intersection for the graph.
  */
 class Vertex implements Comparable<Vertex>{  //implementing comparable so that rows are in ascending order already.
											  // basically for priority queue.
	public int x;  //x coordinate
	public int y;  //y coordinate
	
	public float weight = Float.POSITIVE_INFINITY;   //initial distance for every vertex is Infinity.
	public Vertex prev = null;
	public List<Edge> adjancyList =  new ArrayList<Edge>() ;
	
	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override    //Overriding the compareTo of Comparable to enable sorting later.
	public int compareTo(Vertex next) {
		return Float.compare(this.weight, next.weight);
	}
	
}
