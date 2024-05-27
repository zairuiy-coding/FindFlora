import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class SearchEngineTest {
    private FlowerDatabase database;
    private SearchEngine searchEngine;

    @Before
    public void setUp() {
        database = new FlowerDatabase();
        database.loadFlowersFromCSV("test/FlowerDatabase_test.csv");
        searchEngine = new SearchEngine(database);
    }

    @Test
    public void testSearchByName() {
        assertNotNull(searchEngine.searchByName("Amsonia"));
        assertNull(searchEngine.searchByName("Nonexistent Flower"));
    }

    @Test
    public void testSearchByAlias() {
        Flower expectedFlower = searchEngine.searchByName("Amsonia");
        Flower aliasSearchResult = searchEngine.searchByName("Blue Star");
        assertSame(expectedFlower, aliasSearchResult);
    }

    @Test
    public void testNameSearchCaseInsensitivity() {
        Flower result1 = searchEngine.searchByName("aster");
        Flower result2 = searchEngine.searchByName("ASTER");
        Flower result3 = searchEngine.searchByName("Aster");
        assertSame(result1, result2);
        assertSame(result1, result3);
    }



    @Test
    public void testSearchByZone() {
        assertFalse(searchEngine.searchByZone(5).isEmpty());
        assertTrue(searchEngine.searchByZone(100).isEmpty());
    }

    @Test
    public void testSearchByCategory() {
        assertFalse(searchEngine.searchByCategory("SunNeeds", "Full sun").isEmpty());
        assertTrue(searchEngine.searchByCategory("Color", "Invisible").isEmpty());
    }

    @Test
    public void testSearchByMultipleCategories() {
        Map<String, String> criteria = new HashMap<>();
        criteria.put("SunNeeds", "Full sun");
        criteria.put("WaterNeeds", "low");

        Set<Flower> results = searchEngine.searchByMultipleCategories(criteria);
        assertFalse("Search by multiple categories should return results", results.isEmpty());

        criteria.put("SunNeeds", "Full Sun");
        criteria.put("Color", "Invisible");
        results = searchEngine.searchByMultipleCategories(criteria);
        assertTrue("Search by multiple categories should return empty for conflicting criteria", results.isEmpty());
    }


    // Add more tests for edge cases and complex scenarios
}
