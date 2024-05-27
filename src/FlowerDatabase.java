import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/*
 * Because the flowers in our dataset have not only a primary name but also
 * a few alias, so we create a map that hashing each name (primary and aliases) 
 * of a flower to its primary name. We also have a reverse map makes it easy to
 *  find all names that need to be removed when a primary name is deleted.
 */

public class FlowerDatabase implements IFlowerDatabase {
    private final Map<String, Flower> flowersMap; // Maps primary names to Flower objects
    private final Map<String, String> nameToPrimaryMap; // Maps any name (primary or alias) to primary name
    private final Map<String, List<String>> primaryToAliasesMap; // Maps primary name to list of aliases

    public FlowerDatabase() {
        this.flowersMap = new HashMap<>();
        this.nameToPrimaryMap = new HashMap<>();
        this.primaryToAliasesMap = new HashMap<>();
    }

    public void loadFlowersFromCSV(String filepath) {
        try (Reader reader = new FileReader(filepath)) {
            CSVFormat format = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();

            CSVParser csvParser = new CSVParser(reader, format);
            for (CSVRecord csvRecord : csvParser) {
                // get name
                String nameString = csvRecord.get("Name").toLowerCase().trim();
                ArrayList<String> names = parseName(nameString);
                String name = names.get(0).toLowerCase().trim(); // primary name
                names.remove(0);
                // get other info
                String desc = csvRecord.get("Desc").trim();
                List<String> plantTypes = Arrays.stream(csvRecord.get("PlantType").toLowerCase().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                String colors = csvRecord.get("Color").toLowerCase().trim();
                List<String> colorList = extractColors(colors);

                // get hardiness zone info
                String zoneRange = csvRecord.get("HardinessZones").trim();
                String[] zones = zoneRange.split("-");
                int minZone = Integer.parseInt(zones[0].trim());
                int maxZone = Integer.parseInt(zones[1].trim());

                List<String> bloomsSeasons = Arrays.stream(csvRecord.get("BloomsIn").toLowerCase().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                List<String> sunNeeds = Arrays.stream(csvRecord.get("SunNeeds").toLowerCase().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                String waterNeeds = csvRecord.get("WaterNeeds").toLowerCase().trim();
                String maintenance = csvRecord.get("Maintenance").toLowerCase().trim();

                // construct a flower instance
                Flower flower = new Flower(name, names, desc, plantTypes, colors, colorList, minZone,
                       maxZone, bloomsSeasons, sunNeeds, waterNeeds, maintenance);

                // ########### debug #######################
                // System.out.println(flower.getAllDetails());

                // add it into the map
                addFlower(name, names, flower);
            }
            csvParser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Helper function: names parsed from a string format: "Primary Name (Alias1; Alias2)"
     */
    private ArrayList<String> parseName(String name) {
        String[] primaryAndAliases = name.toLowerCase().split("\\(");
        ArrayList<String> names = new ArrayList<>();
        names.add(primaryAndAliases[0].trim());
        if (primaryAndAliases.length > 1) {
            String[] aliases = primaryAndAliases[1].replace(")", "").split(";");
            for (String alias : aliases) {
                names.add(alias.trim());
            }
        }
        return names;
    }

    /*
     *  Helper function: extract colors from color description string into a list,
     *  mapping variations of colors to standard color names.
     */
    public List<String> extractColors(String colorDescription) {
        // Dictionary to map similar color terms to standard color names
        Map<String, String> colorMap = new HashMap<>();
        colorMap.put("red", "red");
        colorMap.put("scarlet", "red");
        colorMap.put("rose", "red");
        colorMap.put("crimson", "red");
        colorMap.put("maroon", "red");
        colorMap.put("ruby", "red");
        colorMap.put("rust", "red");
        colorMap.put("burgundy", "red");
        colorMap.put("raspberry", "red");

        colorMap.put("purple", "purple");
        colorMap.put("plum", "purple");
        colorMap.put("violet", "purple");
        colorMap.put("lavender", "purple");
        colorMap.put("lilac", "purple");
        colorMap.put("mauve", "purple");
        colorMap.put("amethyst", "purple");

        colorMap.put("pink", "pink");
        colorMap.put("fuchsia", "pink");
        colorMap.put("salmon", "pink");
        colorMap.put("peach", "pink");
        colorMap.put("coral", "pink");
        colorMap.put("magenta", "pink");

        colorMap.put("blue", "blue");
        colorMap.put("teal", "blue");
        colorMap.put("cyan", "blue");
        colorMap.put("cerulean", "blue");
        colorMap.put("azure", "blue");
        colorMap.put("aqua", "blue");
        colorMap.put("turquoise", "blue");
        colorMap.put("cobalt", "blue");
        colorMap.put("sapphire", "blue");
        colorMap.put("indigo", "blue");
        colorMap.put("aquamarine", "blue");
        colorMap.put("navy", "blue");
        colorMap.put("denim", "blue");

        colorMap.put("green", "green");
        colorMap.put("peridot", "green");
        colorMap.put("emerald", "green");
        colorMap.put("jade", "green");
        colorMap.put("olive", "green");
        colorMap.put("lime", "green");

        colorMap.put("ochre", "yellow");
        colorMap.put("yellow", "yellow");
        colorMap.put("khaki", "yellow");
        colorMap.put("golden", "yellow");
        colorMap.put("mustard", "yellow");

        colorMap.put("orange", "orange");
        colorMap.put("amber", "orange");
        colorMap.put("saffron", "orange");
        colorMap.put("copper", "orange");
        colorMap.put("bronze", "orange");

        colorMap.put("brown", "brown");
        colorMap.put("beige", "brown");
        colorMap.put("chocolate", "brown");
        colorMap.put("tan", "brown");
        colorMap.put("sepia", "brown");

        colorMap.put("white", "white");
        colorMap.put("ivory", "white");
        colorMap.put("cream", "white");

        colorMap.put("grey", "grey");
        colorMap.put("silver", "grey");
        colorMap.put("charcoal", "grey");

        colorMap.put("black", "black");

        Set<String> knownColors = new HashSet<>(Arrays.asList(
                "white", "cream", "yellow", "purple", "orange", "pink", "red", "blue",
                "green", "black", "lavender", "maroon", "violet", "brown", "gold", "scarlet",
                "salmon", "rose", "lilac", "mauve", "crimson", "cyan", "magenta", "ivory",
                "beige", "turquoise", "teal", "grey", "silver", "peach", "coral", "fuchsia",
                "lime", "olive", "chocolate", "tan", "navy", "denim", "charcoal", "rust",
                "burgundy", "mustard", "indigo", "saffron", "aquamarine", "khaki", "golden",
                "raspberry", "plum", "ochre", "cerulean", "azure", "jade", "emerald", "amber",
                "ruby", "sepia", "copper", "bronze", "cobalt", "sapphire", "peridot", "amethyst"
        ));

        List<String> extractedColors = new ArrayList<>();
        String[] words = colorDescription.toLowerCase().replaceAll("[^a-z0-9\\s]+", " ").split("\\s+");
        for (String word : words) {
            if (colorMap.containsKey(word)) {
                word = colorMap.get(word); // Map to a standard color if applicable
            }
            if (knownColors.contains(word)) {
                extractedColors.add(word);
            }
        }
        return extractedColors;
    }



    public void addFlower(String primaryName, List<String> aliases, Flower flower) {
        primaryName = primaryName.toLowerCase();
        List<String> normalizedAliases = aliases.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        flowersMap.put(primaryName, flower);
        primaryToAliasesMap.put(primaryName, normalizedAliases);
        nameToPrimaryMap.put(primaryName, primaryName);
        for (String alias : normalizedAliases) {
            nameToPrimaryMap.put(alias, primaryName);
        }
    }

    public Flower getFlower(String name) {
        name = name.toLowerCase();
        String primaryName = nameToPrimaryMap.get(name);
        return primaryName != null ? flowersMap.get(primaryName) : null;
    }



    public void deleteFlower(String name) {
        name = name.toLowerCase();
        String primaryName = nameToPrimaryMap.get(name);
        if (primaryName != null && primaryToAliasesMap.containsKey(primaryName)) {
            List<String> aliases = primaryToAliasesMap.get(primaryName);
            for (String alias : aliases) {
                nameToPrimaryMap.remove(alias);
            }
            flowersMap.remove(primaryName);
            primaryToAliasesMap.remove(primaryName);
            nameToPrimaryMap.remove(primaryName);
        }
    }

    public boolean hasFlower(String name) {
        return nameToPrimaryMap.containsKey(name.toLowerCase());
    }

    public Map<String, Flower> getFlowersMap() {
        return flowersMap;
    }

    public String getPrimaryName(String primOrAlias) {
        return this.nameToPrimaryMap.get(primOrAlias);
    }
    
}   // Class FlowerDatabase
