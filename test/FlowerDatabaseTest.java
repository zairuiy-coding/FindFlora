import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class FlowerDatabaseTest {

    private FlowerDatabase database;
    private static final String TEST_CSV_PATH = "test/FlowerDatabase_test.csv";

    @Before
    public void setUp() {
        database = new FlowerDatabase();
        // Load test data from CSV for consistent testing environment
        database.loadFlowersFromCSV(TEST_CSV_PATH);
    }

    @Test
    public void testHasFlower() {
        assertTrue(database.hasFlower("Blue Star"));
        assertTrue(database.hasFlower("Lily of the Nile"));
        assertFalse(database.hasFlower("Tulip"));
    }

    @Test
    public void testGetFlower() {
        Flower blueStar = database.getFlower("Blue Star");
        assertNotNull(blueStar);

        Flower tulip = database.getFlower("Tulip");
        assertNull(tulip);
    }

    @Test
    public void testGetFlowerByDifferentCases() {
        Flower blueStar1 = database.getFlower("blue star");
        assertNotNull(blueStar1);
        Flower blueStar2 = database.getFlower("blue Star");
        assertNotNull(blueStar2);
        Flower blueStar3 = database.getFlower("BLUE star");
        assertNotNull(blueStar3);
    }

    @Test
    public void testGetFlowerByDifferentNames() {
        Flower blueStar = database.getFlower("Blue Star");
        Flower amsonia = database.getFlower("Amsonia");
        assertSame(blueStar, amsonia);

        Flower flamingoFlower = database.getFlower("Flamingo Flower");
        Flower anthurium = database.getFlower("Anthurium");
        assertSame(flamingoFlower, anthurium);
    }


    @Test
    public void testDeleteFlower() {
        database.deleteFlower("Blue Star");
        assertFalse(database.hasFlower("Blue Star"));

        database.deleteFlower("Flamingo Flower");
        assertFalse(database.hasFlower("Flamingo Flower"));
    }

    @Test
    public void testDeleteFlowerByDifferentNames() {
        database.deleteFlower("Amsonia");
        assertFalse(database.hasFlower("Blue Star"));

        database.deleteFlower("Anthurium");
        assertFalse(database.hasFlower("Flamingo Flower"));
    }
}
