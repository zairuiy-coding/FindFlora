import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

public class FlowerTest {

    @Test
    public void testSimpleConstructor() {
        Flower flower = new Flower("Rose");
        assertEquals("Rose", flower.getPrimaryName());
        assertNull(flower.getAlias());
        assertNull(flower.getDesc());
    }

    @Test
    public void testComprehensiveConstructor() {
        List<String> aliases = Arrays.asList("Peruvian lily", "Lily of the Incas");
        List<String> plantTypes = Arrays.asList("Perennials", "Shrubs");
        List<String> colorList = Arrays.asList("yellow", "res");
        List<String> bloomsSeasons = Arrays.asList("Spring", "Summer");
        List<String> sunNeeds = Arrays.asList("Full sun", "Partial sun");

        Flower flower = new Flower("Alstroemeria", aliases, "Beautiful and resilient.", plantTypes,
                "Varied colors", colorList, 3, 5, bloomsSeasons,
                sunNeeds, "Low", "Easy");

        assertEquals("Alstroemeria", flower.getPrimaryName());
        assertEquals(aliases, flower.getAlias());
        assertEquals("Beautiful and resilient.", flower.getDesc());
        assertEquals(plantTypes, flower.getPlantTypes());
        assertEquals("Varied colors", flower.getColors());
        assertEquals(3, flower.getMinZone());
        assertEquals(5, flower.getMaxZone());
        assertEquals(bloomsSeasons, flower.getBloomsSeasons());
        assertEquals(sunNeeds, flower.getSunNeeds());
        assertEquals("Low", flower.getWaterNeeds());
        assertEquals("Easy", flower.getMaintenance());
    }

    @Test
    public void testSettersAndGetters() {
        Flower flower = new Flower("Carnation");
        flower.setPrimaryName("Dianthus");
        flower.setAlias(Arrays.asList("Pink", "Clove Pink"));
        flower.setDesc("A historically rich flower.");
        flower.setPlantTypes(Arrays.asList("Perennials"));
        flower.setColors("Pink");
        flower.setBloomsSeasons(Arrays.asList("Spring", "Summer"));
        flower.setSunNeeds(Arrays.asList("Full Sun"));
        flower.setWaterNeeds("Average");
        flower.setMaintenance("Medium");
        flower.setMinZone(2);
        flower.setMaxZone(6);


        assertEquals("dianthus", flower.getPrimaryName());
        assertEquals(Arrays.asList("pink", "clove pink"), flower.getAlias());
        assertEquals("a historically rich flower.", flower.getDesc());
        assertEquals(Arrays.asList("perennials"), flower.getPlantTypes());
        assertEquals("pink", flower.getColors());
        assertEquals(Arrays.asList("spring", "summer"), flower.getBloomsSeasons());
        assertEquals(Arrays.asList("full sun"), flower.getSunNeeds());
        assertEquals("average", flower.getWaterNeeds());
        assertEquals("medium", flower.getMaintenance());
    }
}
