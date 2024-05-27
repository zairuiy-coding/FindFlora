import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISearchEngine {
    // Search a flower by its name, which could be a primary name or an alias.
    Flower searchByName(String name);

    // Search for flowers that can thrive within a specific hardiness zone.
    List<Flower> searchByZone(int zone);

    // Search for flowers based on a specific category and value, like "PlantType" or "Color".
    Set<Flower> searchByCategory(String category, String value);

    // Search based on multiple categories, combining several filters.
    Set<Flower> searchByMultipleCategories(Map<String, String> criteria);

}
