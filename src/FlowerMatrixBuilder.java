import java.util.*;

public class FlowerMatrixBuilder {
    private FlowerDatabase flowerDatabase;
    private Map<String, Set<String>> attributeOptions;
    private List<Flower> flowers;
    private List<String> attributes;
    private List<String> flowerNames; // List to store flower names in the order of matrix rows


    public FlowerMatrixBuilder(FlowerDatabase flowerDatabase) {
        this.flowerDatabase = flowerDatabase;
        this.flowerNames = new ArrayList<>();
        FlowerAttributesCollector collector = new FlowerAttributesCollector(flowerDatabase);
        this.attributeOptions = collector.collectAttributeOptions();
        initializeFlowersAndAttributes();
    }

    private void initializeFlowersAndAttributes() {
        this.flowers = new ArrayList<>(flowerDatabase.getFlowersMap().values());
        this.attributes = new ArrayList<>();

        // Adding specific zone attributes
        for (int i = 1; i <= 13; i++) {
            this.attributes.add("Zone" + i);
        }

        // Add other attributes sorted by category and then value
        attributeOptions.forEach((key, values) -> {
            List<String> sortedValues = new ArrayList<>(values);
            Collections.sort(sortedValues);
            sortedValues.forEach(value -> attributes.add(key + ":" + value));
        });
    }

    public int[][] buildMatrix() {
        int numFlowers = flowers.size();
        int numAttributes = attributes.size();
        int[][] matrix = new int[numFlowers][numAttributes];

        for (int i = 0; i < numFlowers; i++) {
            Flower flower = flowers.get(i);
            flowerNames.add(flower.getPrimaryName().toLowerCase()); // Store the order of the flower in matrix

            // ####### debugging
            //System.out.println(flower.getPrimaryName());

            for (int j = 0; j < numAttributes; j++) {
                String attribute = attributes.get(j);
                if (attribute.startsWith("Zone")) {
                    int zone = Integer.parseInt(attribute.substring(4));
                    matrix[i][j] = (zone >= flower.getMinZone() && zone <= flower.getMaxZone()) ? 1 : 0;
                } else {
                    if (isAttributePresent(flower, attribute)) {
                        matrix[i][j] = 1;
                    } else {
                        matrix[i][j] = 0;
                    }
                }
            }
        }

        // ### Debugging method call
        // printAttributeIndexes();

        return matrix;
    }

    private boolean isAttributePresent(Flower flower, String attribute) {
        String[] parts = attribute.split(":");
        if (parts.length < 2) return false; // guard against bad splits
        String category = parts[0];
        String value = parts[1];

        switch (category) {
            case "PlantType":
                return flower.getPlantTypes().contains(value);
            case "Colors":
                return flower.getColorList().contains(value);
            case "BloomsSeasons":
                return flower.getBloomsSeasons().contains(value);
            case "SunNeeds":
                return flower.getSunNeeds().contains(value);
            case "WaterNeeds":
                return flower.getWaterNeeds().equals(value);
            case "Maintenance":
                return flower.getMaintenance().equals(value);
            default:
                return false;
        }
    }

    public List<String> getAttributes() {
        return this.attributes;
    }

    public List<String> getFlowerNames() {
        return flowerNames;
    }

    // Debugging helper method
    public void printAttributeIndexes() {
        System.out.println("Attribute Indexes:");
        for (int i = 0; i < attributes.size(); i++) {
            System.out.println(i + " - " + attributes.get(i));
        }
    }

}
