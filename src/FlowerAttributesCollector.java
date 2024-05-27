import java.util.*;
import java.util.stream.Collectors;

public class FlowerAttributesCollector {
    private FlowerDatabase flowerDatabase;

    public FlowerAttributesCollector(FlowerDatabase flowerDatabase) {
        this.flowerDatabase = flowerDatabase;
    }

    public Map<String, Set<String>> collectAttributeOptions() {
        Map<String, Set<String>> attributeOptions = new HashMap<>();

        // Initialize the map with sets for each attribute,
        // excluding zones (which will be handled in matrix builder)
        attributeOptions.put("PlantType", new HashSet<>());
        attributeOptions.put("Colors", new HashSet<>());
        attributeOptions.put("BloomsSeasons", new HashSet<>());
        attributeOptions.put("SunNeeds", new HashSet<>());
        attributeOptions.put("WaterNeeds", new HashSet<>());
        attributeOptions.put("Maintenance", new HashSet<>());

        // Iterate through each flower in the database
        for (Flower flower : flowerDatabase.getFlowersMap().values()) {
            addToSet(attributeOptions.get("PlantType"), flower.getPlantTypes());
            addToSet(attributeOptions.get("Colors"), flower.getColorList());
            addToSet(attributeOptions.get("BloomsSeasons"), flower.getBloomsSeasons());
            addToSet(attributeOptions.get("SunNeeds"), flower.getSunNeeds());
            addToSet(attributeOptions.get("WaterNeeds"), Collections.singletonList(flower.getWaterNeeds()));
            addToSet(attributeOptions.get("Maintenance"), Collections.singletonList(flower.getMaintenance()));
        }

        return attributeOptions;
    }

    private void addToSet(Set<String> set, List<String> elements) {
        set.addAll(elements.stream().map(String::toLowerCase).collect(Collectors.toSet()));
    }

    // Debugging method to print attribute options
    public void printAttributeOptions(Map<String, Set<String>> attributeOptions) {
        System.out.println("### Collected Attribute Options:");
        attributeOptions.forEach((key, values) -> System.out.println(key + ": " + values));
    }
}
