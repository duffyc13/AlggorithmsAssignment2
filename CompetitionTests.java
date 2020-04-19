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
      CompetitionDijkstra comp = new CompetitionDijkstra(null, 50, 60, 90);
      assertEquals("Time required", -1, comp.timeRequiredforCompetition());
    }
    
    @Test
    public void testWrongSpeed()
    {
      CompetitionDijkstra comp = new CompetitionDijkstra("tinyEWD.txt", -20, 60, 90);
      assertEquals("Time required", -1, comp.timeRequiredforCompetition());
      CompetitionDijkstra stra1 = new CompetitionDijkstra("tinyEWD.txt", 50, 60, 101);
      assertEquals("Time required", -1, stra1.timeRequiredforCompetition());
    }

    @Test
    public void testInputs()
    {
      CompetitionDijkstra comp = new CompetitionDijkstra("input-K.txt", 51, 70, 88);
      assertEquals("Time required", 314, comp.timeRequiredforCompetition());
      System.out.println("input-K: Minimum time required: " + comp.timeRequiredforCompetition() + " seconds");
      CompetitionDijkstra comp2 = new CompetitionDijkstra("input-I.txt", 72, 70, 65);
      assertEquals("Time required", 185, comp2.timeRequiredforCompetition());
      System.out.println("input-I: Minimum time required: " + comp2.timeRequiredforCompetition() + " seconds");
      CompetitionDijkstra comp3 = new CompetitionDijkstra("input-B.txt", 60, 80, 50);
      assertEquals("Time required", 10000, comp3.timeRequiredforCompetition());
      System.out.println("input-I: Minimum time required: " + comp3.timeRequiredforCompetition() + " seconds");
    }
    
    @Test
    public void testWrongFile()
    {
      CompetitionDijkstra comp = new CompetitionDijkstra("ta.txt", -20, 60, 90);
      assertEquals("Time required", -1, comp.timeRequiredforCompetition());
      System.out.println("minimum time " + comp.timeRequiredforCompetition());
    }
    
    @Test
    public void testFWConstructor() {
        //TODO
    }
}
