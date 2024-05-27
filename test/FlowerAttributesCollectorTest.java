import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.Map;

public class FlowerAttributesCollectorTest {
    private FlowerDatabase database;
    private FlowerAttributesCollector collector;

    @Before
    public void setUp() {
        database = new FlowerDatabase();
        database.loadFlowersFromCSV("test/FlowerDatabase_test.csv");
        collector = new FlowerAttributesCollector(database);
    }

    @Test
    public void testCollectAttributeOptions_PlantTypes() {
        Map<String, Set<String>> attributeOptions = collector.collectAttributeOptions();
        // ##### for debugging only
        // collector.printAttributeOptions(attributeOptions);
        Set<String> plantTypes = attributeOptions.get("PlantType");
        assertNotNull("PlantType should not be null", plantTypes);
        assertTrue("PlantType should contain 'perennials'", plantTypes.contains("perennials"));
        assertTrue("PlantType should contain 'bulbs'", plantTypes.contains("bulbs"));
    }

    @Test
    public void testCollectAttributeOptions_Colors() {
        Map<String, Set<String>> attributeOptions = collector.collectAttributeOptions();
        Set<String> colors = attributeOptions.get("Colors");
        assertNotNull("Colors should not be null", colors);
        assertTrue("Colors should contain 'blue'", colors.contains("blue"));
        assertTrue("Colors should contain 'red'", colors.contains("red"));
    }

    @Test
    public void testCollectAttributeOptions_BloomsSeasons() {
        Map<String, Set<String>> attributeOptions = collector.collectAttributeOptions();
        Set<String> bloomsSeasons = attributeOptions.get("BloomsSeasons");
        assertNotNull("BloomsSeasons should not be null", bloomsSeasons);
        assertTrue("BloomsSeasons should contain 'spring'", bloomsSeasons.contains("spring"));
        assertTrue("BloomsSeasons should contain 'summer'", bloomsSeasons.contains("summer"));
    }

    @Test
    public void testCollectAttributeOptions_SunNeeds() {
        Map<String, Set<String>> attributeOptions = collector.collectAttributeOptions();
        Set<String> sunNeeds = attributeOptions.get("SunNeeds");
        assertNotNull("SunNeeds should not be null", sunNeeds);
        assertTrue("SunNeeds should contain 'full sun'", sunNeeds.contains("full sun"));
        assertTrue("SunNeeds should contain 'partial sun'", sunNeeds.contains("partial sun"));
    }

    @Test
    public void testCollectAttributeOptions_WaterNeeds() {
        Map<String, Set<String>> attributeOptions = collector.collectAttributeOptions();
        Set<String> waterNeeds = attributeOptions.get("WaterNeeds");
        assertNotNull("WaterNeeds should not be null", waterNeeds);
    }

    @Test
    public void testCollectAttributeOptions_Maintenance() {
        Map<String, Set<String>> attributeOptions = collector.collectAttributeOptions();
        Set<String> maintenance = attributeOptions.get("Maintenance");
        assertNotNull("Maintenance should not be null", maintenance);
        assertTrue("Maintenance should contain 'hard'", maintenance.contains("hard"));
        assertTrue("Maintenance should contain 'medium'", maintenance.contains("medium"));
    }
}
