import java.util.*;

public class SearchEngine implements ISearchEngine {
    private Map<String, Flower> nameIndex;
    private TreeMap<Integer, List<Flower>> hardinessZoneIndex;
    private Map<String, Set<Flower>> categoryIndex;

    public SearchEngine(FlowerDatabase flowerDatabase) {
        this.nameIndex = new HashMap<>();
        hardinessZoneIndex = new TreeMap<>();
        categoryIndex = new HashMap<>();
        indexFlowers(flowerDatabase.getFlowersMap().values());
    }


    private void indexFlowers(Collection<Flower> flowers) {
        for (Flower flower : flowers) {
            indexByName(flower);
            indexByHardinessZone(flower);
            // Index other attributes as previously done
            indexByCategory("PlantType", flower.getPlantTypes(), flower);
            indexByCategory("BloomSeasons", flower.getBloomsSeasons(), flower);
            indexByCategory("SunNeeds", flower.getSunNeeds(), flower);
            indexByCategory("WaterNeeds", flower.getWaterNeeds(), flower);
            indexByCategory("Maintenance", flower.getMaintenance(), flower);
        }
    }


    ///////////////////////////////////////////////////////////////
    //                    Indexing methods                       //
    ///////////////////////////////////////////////////////////////
    private void indexByName(Flower flower) {
        // Index by primary name
        String primaryName = flower.getPrimaryName().toLowerCase();
        nameIndex.put(primaryName, flower);
        // Also index by aliases
        for (String alias : flower.getAlias()) {
            nameIndex.put(alias.toLowerCase(), flower);
        }
    }

    private void indexByHardinessZone(Flower flower) {
        for (int zone = flower.getMinZone(); zone <= flower.getMaxZone(); zone++) {
            hardinessZoneIndex.computeIfAbsent(zone, k -> new ArrayList<>()).add(flower);
        }
    }

    private void indexByCategory(String categoryKey, List<String> categories, Flower flower) {
        for (String category : categories) {
            String normalizedCategory = category.toLowerCase();
            categoryIndex.computeIfAbsent(categoryKey + ":" + normalizedCategory, k -> new HashSet<>()).add(flower);
        }
    }

    // overload the indexing method to make it allow taking single String categories
    private void indexByCategory(String categoryKey, String category, Flower flower) {
        String normalizedCategory = category.toLowerCase();
        categoryIndex.computeIfAbsent(categoryKey + ":" + normalizedCategory, k -> new HashSet<>()).add(flower);
    }

    ///////////////////////////////////////////////////////////////
    //                    Search methods                         //
    ///////////////////////////////////////////////////////////////
    public Flower searchByName(String name) {
        return nameIndex.get(name.toLowerCase());
    }

    public List<Flower> searchByZone(int zone) {
        return hardinessZoneIndex.getOrDefault(zone, new ArrayList<>());
    }

    public Set<Flower> searchByCategory(String category, String value) {
        return categoryIndex.getOrDefault(category + ":" + value.toLowerCase(), Collections.emptySet());
    }

    public Set<Flower> searchByMultipleCategories(Map<String, String> criteria) {
        Set<Flower> resultSet = null;
        for (Map.Entry<String, String> entry : criteria.entrySet()) {
            Set<Flower> currentSet = searchByCategory(entry.getKey(), entry.getValue());
            if (resultSet == null) {
                resultSet = new HashSet<>(currentSet);
            } else {
                resultSet.retainAll(currentSet);
            }
            if (resultSet.isEmpty()) {
                return resultSet;
            }
        }
        return resultSet == null ? Collections.emptySet() : resultSet;
    }

} // Class SearchEngine
