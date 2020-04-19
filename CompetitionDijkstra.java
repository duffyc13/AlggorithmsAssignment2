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
    
    
    private static Node getLowestDistanceNode(Set <Node> unsettledNodes) 
    {
      Node lowestDistanceNode = null;
      double lowestDistance = Double.MAX_VALUE;
      for (Node node: unsettledNodes) 
      {
        double nodeDistance = node.getDistance();
        if (nodeDistance < lowestDistance) 
        {
          lowestDistance = nodeDistance;
          lowestDistanceNode = node;
        }
      }
      return lowestDistanceNode;
    }
    
    
    private static void calculateMinimumDistance(Node evaluationNode, Double edgeWeight, Node sourceNode)
    {
      Double sourceDistance = sourceNode.getDistance();
      if (sourceDistance + edgeWeight < evaluationNode.getDistance())
      {
        evaluationNode.setDistance(sourceDistance + edgeWeight);
        LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
        shortestPath.add(sourceNode);
        evaluationNode.setShortestPath(shortestPath);
      }
    }
    
    
    public static void calculateShortestPathFromSource(Node source)
    {
      source.setDistance(0);
      Set<Node> sorted = new HashSet<>();
      Set<Node> unsorted = new HashSet<>();

      unsorted.add(source);

      while (unsorted.size() != 0)
      {
        Node currentNode = getLowestDistanceNode(unsorted);
        unsorted.remove(currentNode);
        for (Entry<Node, Double> adjacencyPair : currentNode.getAdjacentNodes().entrySet())
        {
          Node adjacentNode = adjacencyPair.getKey();
          Double edgeWeight = adjacencyPair.getValue();
          if (!sorted.contains(adjacentNode))
          {
            calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
            unsorted.add(adjacentNode);
          }
        }
        sorted.add(currentNode);
      }
    }
    

    


    /**
    * @return int: minimum minutes that will pass before the three contestants can meet
     */
    public int timeRequiredforCompetition()
    {
    	if(this.nodesMap.size()==0)
    	    return -1;
    	if (this.speed1<0||this.speed2<0||this.speed3<0)
    		return -1;
    	double longestTime =0.0;
        int longestestD=0;
        int longestP=0;
        double dist1;
        double dist2;
        double dist3;
        for (int finalD = 0; finalD < nodesMap.size(); finalD++)
        {
          allNodesReset();
          calculateShortestPathFromSource(nodesMap.get(finalD));
          for (int indexA = 0; indexA < nodesMap.size(); indexA++)
          {
            dist1 = nodesMap.get(indexA).getDistance();
            if (dist1==Double.MAX_VALUE||dist1<0)
              return -1;
            dist1 = dist1/speed1; 
            if (dist1>longestTime)
            {  
              longestTime=dist1;
              longestestD=finalD;
              longestP=indexA;
            }  
            for (int indexB = 0; indexB < nodesMap.size(); indexB++)
            {
              dist2 = nodesMap.get(indexB).getDistance();
              if (dist2 == Double.MAX_VALUE||dist2<0)
                return -1;
              dist2 = dist2 / speed2;
              if (dist2 > longestTime)
              {
                longestTime = dist2;
                longestestD = finalD;
                longestP = indexB;
              }
              
              for (int indexC = 0; indexC < nodesMap.size(); indexC++)
              {
                dist3 = nodesMap.get(indexC).getDistance();
                if (dist3==Double.MAX_VALUE||dist3<0)
                  return -1;
                dist3 = dist3/speed3; 
                if (dist3>longestTime)
                {
                  longestTime=dist3;
                  longestestD=finalD;
                  longestP=indexC;
                }
              }
            }
          }
        }
        int ret=(int)Math.ceil(longestTime);
        return ret;
    }
    
    public static void main(String[] args) 
    {
    	CompetitionDijkstra comp = new CompetitionDijkstra(null, 1, 1, 1);
    }

}