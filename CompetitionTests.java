import static org.junit.Assert.*;
import java.io.BufferedReader;
import org.junit.Test;

public class CompetitionTests {

	@Test
	public void testDijkstraConstructor() {
		String a="tinyEWD.txt";
		CompetitionDijkstra comp = new CompetitionDijkstra(a, 50, 60, 90);
	}

	@Test
	public void testNoIn()
	{
		CompetitionDijkstra compD = new CompetitionDijkstra(null, 50, 60, 90);
		assertEquals("Time required", -1, compD.timeRequiredforCompetition());
		CompetitionFloydWarshall compF = new CompetitionFloydWarshall(null, 50, 60, 90);
		assertEquals("Time required", -1, compF.timeRequiredforCompetition());
	}

	@Test
	public void testWrongSpeed()
	{
		CompetitionDijkstra compD = new CompetitionDijkstra("tinyEWD.txt", -20, 60, 90);
		assertEquals("Time required", -1, compD.timeRequiredforCompetition());
		CompetitionDijkstra compD1 = new CompetitionDijkstra("tinyEWD.txt", 50, 60, 101);
		assertEquals("Time required", -1, compD1.timeRequiredforCompetition());
		CompetitionFloydWarshall compF = new CompetitionFloydWarshall(null, 50, 60, 90);
		assertEquals("Time required", -1, compF.timeRequiredforCompetition());
		CompetitionFloydWarshall compF1 = new CompetitionFloydWarshall(null, 50, 60, 101);
		assertEquals("Time required", -1, compF1.timeRequiredforCompetition());
	}

	@Test
	public void testInputs()
	{
		CompetitionDijkstra comp = new CompetitionDijkstra("input-K.txt", 51, 70, 88);
		assertEquals("Time required", 314, comp.timeRequiredforCompetition());
		System.out.println("input-K: Minimum time required: " + comp.timeRequiredforCompetition());
		CompetitionDijkstra comp2 = new CompetitionDijkstra("input-I.txt", 72, 70, 65);
		assertEquals("Time required", 185, comp2.timeRequiredforCompetition());
		System.out.println("input-I: Minimum time required: " + comp2.timeRequiredforCompetition());
		CompetitionDijkstra comp3 = new CompetitionDijkstra("input-B.txt", 60, 80, 50);
		assertEquals("Time required", 10000, comp3.timeRequiredforCompetition());
		System.out.println("input-B: Minimum time required: " + comp3.timeRequiredforCompetition());
		
		CompetitionFloydWarshall compF = new CompetitionFloydWarshall("input-K.txt", 51, 70, 88);
		assertEquals("Time required", 314, compF.timeRequiredforCompetition());
		System.out.println("input-K: Minimum time required: " + compF.timeRequiredforCompetition());
		CompetitionFloydWarshall compF2 = new CompetitionFloydWarshall("input-I.txt", 72, 70, 65);
		assertEquals("Time required", 185, compF2.timeRequiredforCompetition());
		System.out.println("input-I: Minimum time required: " + compF2.timeRequiredforCompetition());
		CompetitionFloydWarshall compF3 = new CompetitionFloydWarshall("input-B.txt", 60, 80, 50);
		assertEquals("Time required", 10000, compF3.timeRequiredforCompetition());
		System.out.println("input-B: Minimum time required: " + compF3.timeRequiredforCompetition());
		
	}

	@Test
	public void testWrongFile()
	{
		CompetitionDijkstra comp = new CompetitionDijkstra("ta.txt", -20, 60, 90);
		assertEquals("Time required", -1, comp.timeRequiredforCompetition());
		CompetitionFloydWarshall compF = new CompetitionFloydWarshall("ta.txt", 50, 60, 90);
		assertEquals("Time required", -1, compF.timeRequiredforCompetition());
	}

	@Test
	public void testFWConstructor() {
		String a="tinyEWD.txt";
		CompetitionFloydWarshall compF = new CompetitionFloydWarshall(a, 50, 60, 90);
	}
}
