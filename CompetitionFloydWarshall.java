import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


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
 * This class implements the competition using Floyd-Warshall algorithm
 */

public class CompetitionFloydWarshall {

	// Member variables...
	public int intersections;
	public int streets;
	public int speed1;
	public int speed2;
	public int speed3;
	public int SOURCE = 0;
	public int DESTINATION = 1;
	public int WEIGHT = 2;
	double[][] graph=null;

	private void initGraph(int intersections)
	{
		if (intersections!= 0)
		{
			this.graph = new double[intersections][intersections];
			for (int i = 0; i < graph.length; i++)
			{

				for (int j = 0; j < graph.length; j++)
				{
					if (i == j)
					{
						graph[i][j] = 0;
					} 
					else
					{
						graph[i][j] = Double.MAX_VALUE;
					}
				}
			}
		}
	}

	private void addEdge(int source, int destination, double weight)
	{
		graph[source][destination] = weight;
	}
	
	public double[][] floydWarshall()
	{
		double[][] weights;
		int n = this.graph.length;
		weights = Arrays.copyOf(this.graph, n);

		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				for (int k = 0; k < n; k++)
				{
					weights[j][k] = Math.min(weights[j][k], weights[j][i] + weights[i][k]);
				}
			}
		}
		return weights;
	}

	/**
	 * @param filename: A filename containing the details of the city road network
	 * @param sA, sB, sC: speeds for 3 contestants
	 */
	CompetitionFloydWarshall (String filename, int sA, int sB, int sC){

		if (filename != null)
		{
			this.speed1 = sA;
			this.speed2 = sB;
			this.speed3 = sC;

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

				initGraph(this.intersections);

				boolean endOfFile = false;
				while(!endOfFile) // read all street connections and create API for weighted graph 
				{
					String streetInfo = bufferedReader.readLine();
					if (streetInfo != null) {
						String[] streetData = streetInfo.split("\\s+");
						int a = Integer.parseInt (streetData[SOURCE]);
						int b = Integer.parseInt (streetData[DESTINATION]);
						double c = Double.parseDouble (streetData[WEIGHT]) * 1000;
						addEdge(a, b, c);
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


	/**
	 * @return int: minimum minutes that will pass before the three contestants can meet
	 */
	public int timeRequiredforCompetition(){
		if (this.speed1>100||this.speed2>100||this.speed3>100)
			return -1;
		if (this.speed1<50||this.speed2<50||this.speed3<50)
			return -1;
		if (this.graph==null)
			return -1;
		double[][] table = floydWarshall();
		double time =0.0;
		int greatestDist=0;
		int greatestP=0;
		double distanceA;
		double distanceB;
		double distanceC;
		for (int i = 0; i< table.length; i++)
		{
			for (int indexA = 0; indexA < table.length; indexA++)
			{
				distanceA = table[i][indexA];
				if (distanceA == Double.MAX_VALUE || distanceA < 0)
					return -1;
				distanceA = distanceA/speed1; 
				if (distanceA > time)
				{  
					time = distanceA;
					greatestDist = i;
					greatestP = indexA;
				}  
				for (int indexB = 0; indexB < table.length; indexB++)
				{
					distanceB = table[indexB][i];
					if (distanceB == Double.MAX_VALUE || distanceB < 0)
						return -1;
					distanceB = distanceB / speed2;
					if (distanceB > time)
					{
						time = distanceB;
						greatestDist = i;
						greatestP = indexB;
					}

					for (int indexC = 0; indexC < table.length; indexC++)
					{
						distanceC = table[indexC][i];
						if (distanceC == Double.MAX_VALUE || distanceC < 0)
							return -1;
						distanceC = distanceC/speed3; 
						if (distanceC > time)
						{
							time = distanceC;
							greatestDist = i;
							greatestP = indexC;
						}
					}
				}
			}
		}
		int result=(int)Math.ceil(time);
		return result;
	}
}