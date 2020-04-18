import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
/*
 * A Contest to Meet (ACM) is a reality TV contest that sets three contestants at three random
 * city intersections. In order to win, the three contestants need all to meet at any intersection
 * of the city as fast as possible.
 * It should be clear that the contestants may arrive at the intersections at different times, in
 * which case, the first to arrive can wait until the others arrive.
 * From an estimated walking speed for each one of the three contestants, ACM wants to determine the
 * minimum time that a live TV broadcast should last to cover their journey regardless of the contestants’
 * initial positions and the intersection they finally meet. You are hired to help ACM answer this question.
 * You may assume the following:
 *     Each contestant walks at a given estimated speed.
 *     The city is a collection of intersections in which some pairs are connected by one-way
 * streets that the contestants can use to traverse the city.
 *
 * This class implements the competition using Dijkstra's algorithm
 */

public class CompetitionDijkstra {
	
	// Member variables...
	public int intersections;
	public int streets;
	public int speed1;
	public int speed2;
	public int speed3;
	public int SOURCE = 0;
	public int DESTINATION = 1;
	public int WEIGHT = 2;
	Map<Integer, Node> nodesMap = new HashMap<>();
	
	private class Node
	{
		private int index;
	    private List<Node> shortestPath = new LinkedList<>();
	    private Double distance = Double.MAX_VALUE;
	    private Map<Node, Double> adjacentNodes = new HashMap<>();

	    public void addDestination(Node destination, double distance)
	    {
	    	
	     	this.adjacentNodes.put(destination, distance);
	    }
	    public Node(int index)
	    {
	    	this.index = index;
	    }
	    public List<Node> getShortestPath()
	    {
	    	return shortestPath;
	    }
	    public void setShortestPath(List<Node> shortestPath)
	    {
	    	this.shortestPath = shortestPath;
	    }
	    public void setDistance(double a)
	    {
	    	this.distance=a;
	    }
	    public double getDistance()
	    {
	    	return this.distance;
	    }
	    public Map<Node, Double> getAdjacentNodes()
	    {
	    	return adjacentNodes;
	    }
	}
	public boolean doesNodeExist(int index)
	{
	  if (nodesMap.containsKey(index))
		  return true;
	  return false;
	}
	
    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
    */
    CompetitionDijkstra (String filename, int sA, int sB, int sC)
    {
    	if (filename != null)
    	{
	    	this.speed1 = sA;
	    	this.speed2 = sB;
	    	this.speed3 = sC;
	    	Node node1;
	        Node node2;
	        
	    	try 
			{
				FileReader fileReader = new FileReader(filename); // Text file submitted with java files
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String interNumber = bufferedReader.readLine(); // read number of intersections
	        	if (interNumber != null) 
	        	{
	        		String[] intersectionsData = interNumber.split(" ");
	        		this.intersections = Integer.parseInt (intersectionsData[0]);
	        	}
	        	String streetsNumber = bufferedReader.readLine(); // read number of streets
	        	if (streetsNumber != null) 
	        	{
	        		String[] streetsData = streetsNumber.split(" ");
	        		this.streets = Integer.parseInt (streetsData[0]);
	        	}
	        	
	        	boolean endOfFile = false;
	        	while(!endOfFile) // read all street connections and create API for weighted graph 
		        {
		        	String streetInfo = bufferedReader.readLine();
		        	if (streetInfo != null) {
		        		String[] streetData = streetInfo.split("\\s+");
		        		int a = Integer.parseInt (streetData[SOURCE]);
		        		int b = Integer.parseInt (streetData[DESTINATION]);
		        		double c = Double.parseDouble (streetData[WEIGHT]) * 1000;
		        		if (doesNodeExist(a))
		                {
		                  node1 = nodesMap.get(a);
		                  nodesMap.remove(a);
		                } 
		                else
		                  node1 = new Node(a);
	
		                if (doesNodeExist(b))
		                {
		                  node2 = nodesMap.get(b);
		                  nodesMap.remove(b);
		                } else
		                  node2 = new Node(b);
	
		                node1.addDestination(node2, c);
		                nodesMap.put(a, node1);
		                nodesMap.put(b, node2);
		        	}
		        	else
		        	{
		        		endOfFile = true;
		        	}
		        }
		        bufferedReader.close();    
		        fileReader.close();
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
    	}
    }
    
    private void allNodesReset()
    {
      for (int i=0; i<nodesMap.size();i++)
      {
        nodesMap.get(i).setDistance(Double.MAX_VALUE);
      }
    }
    
    


    /**
    * @return int: minimum minutes that will pass before the three contestants can meet
     */
    public int timeRequiredforCompetition()
    {

        //TO DO
        return -1;
    }
    
    public static void main(String[] args) 
    {
    	CompetitionDijkstra comp = new CompetitionDijkstra(null, 1, 1, 1);
    }

}