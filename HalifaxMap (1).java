import java.util.*;


public class HalifaxMap {  //main class for the program
	
	
	private PriorityQueue<Vertex> unsettled;	  //queue for Dijkstra algorithm
	private List<Vertex> vList = new ArrayList<Vertex>();	//vertices list
	
	/*
	 *  Internal function to check whether Vertex exists or not.
	 *  
	 *  I/P : Vertices list and new Vertex object.
	 *  O/P : returns the Vertex object if found in list 
	 */
	private Vertex contains(List<Vertex> list, Vertex obj) {
		
		if(list.isEmpty()) {
			return null ;
		}
		
		for(Vertex element : list) {
			if((element.x == obj.x) && (element.y == obj.y)) {
				return element;
			}
		}
		return null;
	}
	
	/*
	 * Internal function to print vertices/intersections.
	 */
	public void printVertices() {

		for (Vertex element : vList) {
			System.out.println("x coordinate is : " + element.x + "; y coordinate is : " + element.y + "; weight is : " + element.weight);
		}
	}
	
	/*
	 *  Internal function to print Adjancies for a node.
	 */
	public void printAdjancies() {
		
		for(Vertex v : vList) {
			System.out.println("source X : " + v.x + " source Y : " + v.y);
			for(Edge e : v.adjancyList) {
				System.out.println("	destination.X : " + e.destination.x + "; destination.Y : " + e.destination.y + "; distance is : " + e.distance);			
			}
		}
	}
	
	/*
	 *  Internal function to calculate distance b/w two vertices.
	 *  
	 *  I/P : coordinates for two vertices.
	 *  O/P : return calculated distance or weight for a vertex.
	 */
	private long calculateLength(int x1, int y1, int x2, int y2) {
		
		double initialCalc = (Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
		return Math.round((Math.sqrt(initialCalc)));
	}
	
	/*
	 *  Internal function to refresh the weights and prev pointer of all vertices, 
	 *  before applying algorithm.
	 */
	private void refreshVertices() {
		
		for(Vertex v : vList) {
			v.weight = Float.POSITIVE_INFINITY;
			v.prev   = null;
		}
		
	}

	/*
	 *  Function to add an intersection or vertex for a graph.
	 *  
	 *  I/P : integer values of x and y coordinate for an intersection.
	 *  O/P : boolean whether this new intersection is considerable to add. 
	 */
	public boolean newIntersection(int x, int y) {

		try {
			Vertex obj = new Vertex(x, y);

			if (contains(vList, obj) != null) {
				return false;
			} else {
				vList.add(obj);
				return true;
			}
		} catch (Exception e) {
			return false;
		}

	}

	/*
	 *  Function to define new road b/w two intersections.
	 *  
	 *  I/P : Integer values for coordinates of source and destination.
	 *  O/P : boolean value of whether it is a new road and has been added successfully.
	 */
	public boolean defineRoad(int x1, int y1, int x2, int y2) {

		try {
			Vertex src = new Vertex(x1, y1);
			Vertex dest = new Vertex(x2, y2);

			// if any intersection/vertex is non-existent , simply return false.
			if ((contains(vList, src) == null) || (contains(vList, dest) == null)) {
				return false;
			}

			if ((contains(vList, src) != null) && (contains(vList, dest) != null)) {

				src = contains(vList, src);
				dest = contains(vList, dest);
				for (Edge e : src.adjancyList) {

					if (e.destination == dest) {
						return false;
					}
				}

				int distance = (int) (calculateLength(x1, y1, x2, y2));
				
				if(distance < 0) { // case when Integer overflows
					distance = distance + Integer.MAX_VALUE;
				}
				
				src.adjancyList.add(new Edge(dest, distance));
				dest.adjancyList.add(new Edge(src, distance));

				return true;

			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 *  Private function to execute Dijkstra's algorithm starting from a source Vertex.
	 *  
	 *  I/P : source vertex/intersection
	 *  O/P : void (it updates the weight of all connected intersections 
	 *              with the smallest distance from the source.)
	 */
	private void executeDijkstra(Vertex source) {

		try {

			refreshVertices(); // refresh the weights and prev pointers of all vertices.

			unsettled = new PriorityQueue<Vertex>(); // priority queue for nodes still to be processed
			source.weight = 0; // initialize the source weight to the minimum positive.
			unsettled.add(source);

			while (unsettled.size() > 0 ) { // loop through the queue.

				Vertex sourceTobeChecked = unsettled.poll();

				for (Edge e : sourceTobeChecked.adjancyList) { // check for every edge with a node

					Vertex neighbor = e.destination;
					
					if((neighbor.x == source.x) && (neighbor.y == source.y)) {
						continue;  //avoid inner cycle or looping.
					}
					
					float newWeight = sourceTobeChecked.weight + e.distance;

					if (neighbor.weight >= newWeight) { // update the weight if it is larger than the current one.

						neighbor.weight = newWeight;
						unsettled.remove(sourceTobeChecked);
						neighbor.prev = sourceTobeChecked;
						unsettled.add(neighbor);
					}
				}
			}
		} catch (Exception e) {
			return;
		}
	}

	/*
	 *  Internal function to print the O/P in required format.
	 *  
	 *  I/P : destination vertex.
	 *  O/P : Print to screen , the complete path from source to destination.
	 */
	private void output(Vertex target) { 
		
		try {
			
			if (target == null) {
				return;
			}

			if (Float.isInfinite(target.weight)) {
				System.out.println("no path");
				return;
			}

			output(target.prev);  // recursion hack to print in reverse.
			System.out.println(target.x + "\t" + target.y);
			
		} catch (Exception e) {
			return;
		}
	}
	
	/*
	 *  Function to navigate and return the shortest path from source 
	 *  intersection to destination intersection.
	 *  
	 *  I/P : Source and Destination intersection coordinates.
	 *  O/P : Complete path in desired format. (tab separated coordinates on every line)
	 */
	public void navigate(int x1, int y1, int x2, int y2) {

		try {
			Vertex src = new Vertex(x1, y1);
			Vertex dest = new Vertex(x2, y2);

			if ((contains(vList, src) == null) || (contains(vList, dest) == null)) {
				System.out.println("no path");
				return;
			}

			if (contains(vList, src) == contains(vList, dest)) {

				System.out.println(src.x + "\t" + src.y);
				System.out.println(dest.x + "\t" + dest.y);
				return;
			}

			src = contains(vList, src);
			dest = contains(vList, dest);

			executeDijkstra(src);
			output(dest);
		} catch (Exception e) {
			return;
		}

	}
		
}
