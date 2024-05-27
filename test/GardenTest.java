import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GardenTest {
    private Garden garden;
    private Flower rose;
    private Flower tulip;
    private SearchEngine searchEngine;
    private FlowerDatabase flowerDatabase;


    @Before
    public void setUp() {
        rose = new Flower("Rose");
        tulip = new Flower("Tulip");

        // mock a search engine
        flowerDatabase = new FlowerDatabase();
        flowerDatabase.loadFlowersFromCSV("test/FlowerDatabase_test.csv");
        searchEngine = new SearchEngine(flowerDatabase);

        garden = new Garden(100.0, 7, "full sun", "avarage", new ArrayList<>(Arrays.asList(rose)), searchEngine);
    }

    @Test
    public void testAddFlower() {
        garden.addFlower(tulip);
        assertTrue("Tulip should be added to the garden", garden.getExistingFlowers().contains(tulip));
    }

    @Test
    public void testRemoveFlower() {
        garden.removeFlower(rose);
        assertFalse("Rose should be removed from the garden", garden.getExistingFlowers().contains(rose));
    }

    @Test
    public void testUpdateGardenInfo() {
        garden.updateGardenInfo(150.0, 7, "full sun", "avarage");
        assertEquals("Garden size should be updated to 150.0", 150.0, garden.getGardenSize(), 0.01);
        assertEquals("hardinessZone should be updated to 7", 7, garden.getHardinessZone(), 0.01);
        assertEquals("Sun exposure should be updated to Partial Shade", "full sun", garden.getSunExposure());
        assertEquals("Water supply should be updated to Low", "avarage", garden.getWaterSupply());
    }

    @Test
    public void testFindSuitableFlowers() {
        // Execute the method to find suitable flowers
        List<Flower> suitableFlowers = garden.findSuitableFlowers();

        assertNotNull(suitableFlowers);
        assertEquals(14, suitableFlowers.size());


    }
}
