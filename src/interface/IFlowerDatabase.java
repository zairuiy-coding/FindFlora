import java.util.List;

public interface IFlowerDatabase {
    /**
     * Loads flowers from a specified file path into the database.
     * @param filepath The path to the CSV file containing flower data.
     */
    void loadFlowersFromCSV(String filepath);

    /**
     * Adds a new flower to the database.
     * @param primaryName The primary name of the flower.
     * @param aliases A list of aliases for the flower.
     * @param flower The flower object to add.
     */
    void addFlower(String primaryName, List<String> aliases, Flower flower);

    /**
     * Deletes a flower from the database by its name (primary or alias).
     * @param name The name of the flower to delete.
     */
    void deleteFlower(String name);

    /**
     * Checks if a flower with the specified name exists in the database.
     * @param name The name of the flower to check.
     * @return true if the flower exists, otherwise false.
     */
    boolean hasFlower(String name);

    /**
     * Retrieves a flower from the database by its name (primary or alias).
     * @param name The name of the flower to retrieve.
     * @return The flower object if found, otherwise null.
     */
    Flower getFlower(String name);
}
