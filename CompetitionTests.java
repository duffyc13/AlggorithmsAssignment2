import static org.junit.Assert.*;
import java.io.BufferedReader;
import org.junit.Test;

public class CompetitionTests {

    @Test
    public void testDijkstraConstructor() {
    	String a="tinyEWD.txt";
        CompetitionDijkstra stra = new CompetitionDijkstra(a, 50, 60, 90);
        System.out.println(stra.timeRequiredforCompetition());
    }
    
    @Test
    public void testNoIn()
    {
      CompetitionDijkstra stra = new CompetitionDijkstra(null, 50, 60, 90);
      assertEquals("Time required", -1, stra.timeRequiredforCompetition());
      System.out.println("minimum time " + stra.timeRequiredforCompetition());
      CompetitionFloydWarshall fw = new CompetitionFloydWarshall(null, 50, 60, 90);
      assertEquals("Time required", -1, fw.timeRequiredforCompetition());
      System.out.println("minimum time " + fw.timeRequiredforCompetition());
    }

    @Test
    public void testFWConstructor() {
        //TODO
    }

    //TODO - more tests
    
}
