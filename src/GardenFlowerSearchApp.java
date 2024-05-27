import javax.swing.*;
import java.util.*;

public class GardenFlowerSearchApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static Garden garden;
    private static SearchEngine searchEngine;
    private static FlowerDatabase flowerDatabase;
    private static FlowerMatrixBuilder flowerMatrixBuilder;
    private static QuadTree quadTree;
    private static TSNEProcessor tsneProcessor;



    public static void main(String[] args) {
        // Initialize the FlowerDatabase and SearchEngine
        flowerDatabase = new FlowerDatabase();
        flowerDatabase.loadFlowersFromCSV("src/resource/FlowerDatabase.csv");
        searchEngine = new SearchEngine(flowerDatabase);
//        recommendationEngine = new RecommendationEngine(flowerDatabase);

        // initialize cosine similarity matrix
        flowerMatrixBuilder = new FlowerMatrixBuilder(flowerDatabase);
        int[][] flowerMatrix = flowerMatrixBuilder.buildMatrix();
        List<String> flowerNames = flowerMatrixBuilder.getFlowerNames(); // the order of flowers in matrix

        // #### debug usage:
//        cosineSimilarityCalculator.printSimilarityScores(cosineSimilarityMatrix, flowerNames);

        // initialize the quadTree
        tsneProcessor = new TSNEProcessor(flowerMatrix, flowerNames);
        double[][] tsneResults = tsneProcessor.getTsneResults();

        // Initialize the QuadTree with boundary based on min and max t-SNE values
        double[] minValues = tsneProcessor.getMinTsneValues();
        double[] maxValues = tsneProcessor.getMaxTsneValues();
        quadTree = new QuadTree(minValues[0], maxValues[0], minValues[1], maxValues[1]);

        // Insert flowers into the QuadTree
        for (int i = 0; i < tsneResults.length; i++) {
            quadTree.insert(flowerNames.get(i), tsneResults[i][0], tsneResults[i][1]);
        }

        int totalFlowers = flowerNames.size();
        int storedFlowers = quadTree.countNodes(quadTree.getRoot());// Assume getRoot() exposes the root node
        System.out.println("Total flowers: " + totalFlowers + ", Stored in QuadTree: " + storedFlowers);

        System.out.println("");
        System.out.println("✽ ✾ ✿ ❀ ❁ ❃ ❊ ❋ ✣ ✤ ✽ ✾ ✿ ❀ ❁ ❃ ❊ ❋ ✣ ✤ ✽ ✾ ✿ ❀ ❁ ❃ ❊ ❋");
        System.out.println("Welcome to FindFlora - the Garden Flower Search App!");

        while (true) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Search Flowers");
            System.out.println("2. Build Your Garden and Get Recommendations");
            System.out.println("3. Exit");

            System.out.println("");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline left-over

            switch (choice) {
                case 1:
                    searchFlowers();
                    break;
                case 2:
                    buildGardenAndGetRecommendations();
                    break;
                case 3:
                    System.out.println("Exiting application.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option, please choose again.");
            }
        }
    }

    private static void buildGardenAndGetRecommendations() {
        System.out.println("Let's build your garden!");
        System.out.print("Enter garden size (in square meters): ");
        double size = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter hardinessZone (3-11) (find your hardinessZone: https://planthardiness.ars.usda.gov/):");
        int hardinessZone = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Select sun exposure:");
        List<String> sunOptions = Arrays.asList("full sun", "partial sun", "shade");
        for (int i = 0; i < sunOptions.size(); i++) {
            System.out.println((i + 1) + ". " + sunOptions.get(i));
        }
        System.out.print("Choose an option (1-" + sunOptions.size() + "): ");
        int sunChoice = validateChoice(sunOptions.size());
        String sunExposure = sunOptions.get(sunChoice);

        System.out.println("Select water supply:");
        List<String> waterOptions = Arrays.asList("avarage", "low");
        for (int i = 0; i < waterOptions.size(); i++) {
            System.out.println((i + 1) + ". " + waterOptions.get(i));
        }
        System.out.print("Choose an option (1-" + waterOptions.size() + "): ");
        int waterChoice = validateChoice(waterOptions.size());
        String waterSupply = waterOptions.get(waterChoice);

        garden = new Garden(size, hardinessZone, sunExposure, waterSupply, new ArrayList<>(), searchEngine);
        List<Flower> suitableFlowers = garden.findSuitableFlowers();

        if (suitableFlowers.isEmpty()) {
            System.out.println("No suitable flowers can be grown in your garden. Try updating the garden conditions!");
            return;
        }

        System.out.println("Select one favorite flower to get recommended for similar:");
        displayFlowers(suitableFlowers);
        System.out.println("Enter the index of your favorite flower to get similar recommendations:");
        int firstChoice = validateFlowerChoice(suitableFlowers.size());

        Flower targetFlower = suitableFlowers.get(firstChoice);
        System.out.println("You choose: " + targetFlower.getPrimaryName());
        while (true) {
            System.out.println("How many flowers do you want to get recommended to plant? (0-200)");
            int k = scanner.nextInt();
            if (k < 0 || k > 200) {
                System.out.println("Out of range! Choose an int between 0 and 200");
                continue;
            }

            List<String> neighbors = quadTree.findKApproximateNearestNeighbors(targetFlower.getPrimaryName(), k);
            System.out.printf("Here are the %d flowers recommended for you:%n", k);

            for (String neighbor : neighbors) {
                System.out.println(neighbor);
            }
            break;
        }
    }

    private static void displayFlowers(List<Flower> flowers) {
        for (int i = 0; i < flowers.size(); i++) {
            System.out.println((i + 1) + ". " + flowers.get(i).getPrimaryName());
        }
    }

    private static int validateFlowerChoice(int size) {
        int choice = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        while (choice < 0 || choice >= size) {
            System.out.println("Invalid choice. Please select a number between 1 and " + size + ":");
            choice = scanner.nextInt() - 1;
            scanner.nextLine(); // consume newline
        }
        return choice;
    }

     static int validateChoice(int size) {
        int choice = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        while (choice < 0 || choice >= size) {
            System.out.println("Invalid choice. Please select a number between 1 and " + size + ":");
            choice = scanner.nextInt() - 1;
            scanner.nextLine(); // consume newline
        }
        return choice;
    }



    private static void searchFlowers() {

        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Search by flower name (primary or alias)");
            System.out.println("2. Search by hardiness zone");
            System.out.println("3. Search by category");
            System.out.println("4. Search by multiple categories");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline left-over

            switch (choice) {
                case 1:
                    searchByName();
                    break;
                case 2:
                    searchByZone();
                    break;
                case 3:
                    searchByCategory();
                    break;
                case 4:
                    searchByMultipleCategories();
                    break;
                case 5:
                    System.out.println("Returning to Main Menu.");
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option, please choose again.");
            }
        }

    }



    private static void searchByName() {
        System.out.print("Enter the name of the flower you are searching for: ");
        String name = scanner.nextLine().toLowerCase();
        Flower flower = searchEngine.searchByName(name);
        if (flower != null) {
            System.out.println("Flower found: " + flower.getAllDetails());
        } else {
            System.out.println("No flower found with the name " + name);
        }
    }

    private static void searchByZone() {
        System.out.print("Enter the hardiness zone to search for flowers: ");
        int zone = scanner.nextInt();
        List<Flower> flowers = searchEngine.searchByZone(zone);
        if (!flowers.isEmpty()) {
            System.out.println("Flowers suitable for zone " + zone + ":");
            flowers.forEach(flower -> System.out.println(flower.getPrimaryName()));
        } else {
            System.out.println("No flowers found for zone " + zone);
        }
    }

    private static void searchByCategory() {
        System.out.println("Select the category to search:");
        Map<Integer, String> categories = new HashMap<>();
        categories.put(1, "PlantType");
        categories.put(2, "BloomSeasons");
        categories.put(3, "SunNeeds");
        categories.put(4, "WaterNeeds");
        categories.put(5, "Maintenance");

        categories.forEach((key, value) -> System.out.println(key + " - " + value));

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume the remaining newline

        String category = categories.get(choice);
        if (category == null) {
            System.out.println("Invalid choice. Please select a valid category.");
            return;
        }

        List<String> options = getOptionsForCategory(category);
        if (options.isEmpty()) {
            System.out.println("No options available for this category.");
            return;
        }

        System.out.println("Available options for " + category + ":");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + " - " + options.get(i));
        }

        System.out.print("Select an option: ");
        int optionIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline left-over

        if (optionIndex < 0 || optionIndex >= options.size()) {
            System.out.println("Invalid option selected.");
            return;
        }

        String value = options.get(optionIndex);
        Set<Flower> flowers = searchEngine.searchByCategory(category, value);
        if (!flowers.isEmpty()) {
            System.out.println("Flowers matching " + category + ": " + value);
            flowers.forEach(flower -> System.out.println(flower.getPrimaryName()));
        } else {
            System.out.println("No flowers found matching the criteria.");
        }
    }

    private static List<String> getOptionsForCategory(String category) {
        switch (category) {
            case "PlantType":
                return Arrays.asList("perennials", "annuals", "shrubs", "trees", "bulbs", "climbers", "herbs", "cactus");
            case "BloomSeasons":
                return Arrays.asList("spring", "summer", "autumn", "winter");
            case "SunNeeds":
                return Arrays.asList("full sun", "partial sun", "shade");
            case "WaterNeeds":
                return Arrays.asList("average", "low");
            case "Maintenance":
                return Arrays.asList("hard", "medium", "low");
            default:
                return new ArrayList<>();
        }
    }

    private static void searchByMultipleCategories() {
        Map<String, String> criteria = new HashMap<>();
        boolean addingMore = true;

        while (addingMore) {
            System.out.println("Select the category to add to your search criteria:");
            System.out.println("1 - PlantType");
            System.out.println("2 - BloomSeasons");
            System.out.println("3 - SunNeeds");
            System.out.println("4 - WaterNeeds");
            System.out.println("5 - Maintenance");
            System.out.println("0 - Done adding criteria");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the remaining newline

            if (choice == 0) {
                addingMore = false;
                continue;
            }

            String category = getOptionFromChoice(choice);
            if (category == null) {
                System.out.println("Invalid choice. Please select a valid category.");
                continue;
            }

            List<String> options = getOptionsForCategory(category);
            System.out.println("Available options for " + category + ":");
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + " - " + options.get(i));
            }

            System.out.print("Select an option: ");
            int optionIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // consume newline left-over

            if (optionIndex < 0 || optionIndex >= options.size()) {
                System.out.println("Invalid option selected.");
                continue;
            }

            String value = options.get(optionIndex);

            if (!criteria.containsKey(category)) {
                criteria.put(category, value);
            } else {
                System.out.println("Category already added. Please choose another.");
            }
        }

        if (!criteria.isEmpty()) {
            Set<Flower> resultSet = searchEngine.searchByMultipleCategories(criteria);
            if (!resultSet.isEmpty()) {
                System.out.println("Flowers matching your criteria:");
                resultSet.forEach(flower -> System.out.println(flower.getPrimaryName()));
            } else {
                System.out.println("No flowers found matching the criteria.");
            }
        } else {
            System.out.println("No criteria entered.");
        }
    }

    private static String getOptionFromChoice(int choice) {
        switch (choice) {
            case 1: return "PlantType";
            case 2: return "BloomSeasons";
            case 3: return "SunNeeds";
            case 4: return "WaterNeeds";
            case 5: return "Maintenance";
            default: return null;
        }
    }
}
