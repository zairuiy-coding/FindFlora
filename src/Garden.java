import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Garden implements IGarden {
    // Attributes
    private double gardenSize = 0.0;
    private int hardinessZone;
    private String sunExposure = "";
    private String waterSupply = "";
    private List<Flower> existingFlowers;

    // Search engine reference
    private SearchEngine searchEngine;

    // constructors

    /**
     * defualt simple constructor
     */
    public Garden(SearchEngine searchEngine) {
        this.existingFlowers = new ArrayList<>();
        this.searchEngine = searchEngine;
    }

    /**
     * comprehensive constructor
     */
    public Garden(double gardenSize, int hardinessZone, String sunExposure, String waterSupply, List<Flower> existingFlowers, SearchEngine searchEngine) {
        this.gardenSize = gardenSize;
        this.hardinessZone = hardinessZone;
        this.sunExposure = sunExposure;
        this.waterSupply = waterSupply;
        this.existingFlowers = new ArrayList<>(existingFlowers);
        this.searchEngine = searchEngine;
    }

    @Override
    public void updateGardenInfo(double size, int hardinessZone, String sunExposure, String waterSupply) {
        this.gardenSize = size;
        this.hardinessZone = hardinessZone;
        this.sunExposure = sunExposure;
        this.waterSupply = waterSupply;
    }

    @Override
    public void addFlower(Flower flower) {
        this.existingFlowers.add(flower);
    }

    @Override
    public void removeFlower(Flower flower) {
        this.existingFlowers.remove(flower);
    }

    // Getters and Setters
    public double getGardenSize() {
        return gardenSize;
    }

    public double getHardinessZone() {
        return hardinessZone;
    }

    public String getSunExposure() {
        return sunExposure;
    }

    public String getWaterSupply() {
        return waterSupply;
    }

    public List<Flower> getExistingFlowers() {
        return new ArrayList<>(existingFlowers); // Return a copy to prevent external modifications
    }


    /**
     * Filters flowers from a given database that match the garden's conditions.
     * @return A list of flowers suitable for the garden.
     */
    public List<Flower> findSuitableFlowers() {
        List<Flower> zoneSuitableFlowers = searchEngine.searchByZone(this.hardinessZone);

        return zoneSuitableFlowers.stream()
                .filter(flower -> flower.getSunNeeds().contains(this.sunExposure.toLowerCase()) &&
                        flower.getWaterNeeds().equalsIgnoreCase(this.waterSupply.toLowerCase()))
                .collect(Collectors.toList());
    }

}