import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class for a detailed flower information derived from the dataset.
 */
public class Flower {

    private String name; // primary name ,
    private List<String> alias; // a list of alias
                                //e.g. Alstroemeria (Peruvian lily; Lily of the Incas)
    private String desc; // Description of this flower
    private List<String> plantTypes; // Types of the flower, e.g. Climbers, Perennials, Shrubs
    private String colors; // Description of the colors of the flower
    private List<String> colorList; // a list of extracted colors from the colors String
    private int minZone;
    private int maxZone;
    private List<String> bloomsSeasons; // Seasons that the flower blooms in
    private List<String> sunNeeds; // Sun needs that the flower fits, e.g. [full sun, partial sun]
    private String waterNeeds; // Water needs of the flower
    private String maintenance; // Overall maintenance, e.g. hard, easy


    // a simple constructor
    public Flower(String name) {
        this.name = name;
    }

    /**
     * Comprehensive constructor to initialize all fields.
     */
    public Flower(String name, List<String> alias, String desc,
                List<String> plantTypes, String colors, List<String> colorList,
                  int minZone, int maxZone, List<String> bloomsSeasons,
                List<String> sunNeeds, String waterNeeds,
                String maintenance) {
        this.name = name;
        this.alias = alias;
        this.desc = desc;
        this.plantTypes = plantTypes;
        this.colors = colors;
        this.colorList = colorList;
        this.minZone = minZone;
        this.maxZone = maxZone;
        this.bloomsSeasons = bloomsSeasons;
        this.sunNeeds = sunNeeds;
        this.waterNeeds = waterNeeds;
        this.maintenance = maintenance;
    }


    // Getters and setters for all fields
    public String getPrimaryName() {
        return name;
    }

    public List<String> getAlias() {
        return alias;
    }

    public String getDesc() {
        return desc;
    }

    public List<String> getPlantTypes() {
        return plantTypes;
    }

    public String getColors() {
        return colors;
    }

    public List<String> getColorList() {
        return colorList;
    }

    public int getMinZone() {
        return minZone;
    }

    public int getMaxZone() {
        return maxZone;
    }

    public List<String> getBloomsSeasons() {
        return bloomsSeasons;
    }

    public List<String> getSunNeeds() {
        return sunNeeds;
    }

    public String getWaterNeeds() {
        return waterNeeds;
    }

    public String getMaintenance() {
        return maintenance;
    }

    // setters

    public void setPrimaryName(String name) {
        this.name = name.toLowerCase();
    }

    public void setAlias(List<String> alias) {
        this.alias = alias.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public void setDesc(String desc) {
        this.desc = desc.toLowerCase();
    }

    public void setPlantTypes(List<String> plantTypes) {
        this.plantTypes = plantTypes.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public void setColors(String colors) {
        this.colors = colors.toLowerCase();
    }

    public void setColorList(List<String> colorList) {
        this.colorList = colorList.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public void setMinZone(int minZone) {
        this.minZone = minZone;
    }

    public void setMaxZone(int maxZone) {
        this.maxZone = maxZone;
    }

    public void setBloomsSeasons(List<String> bloomsSeasons) {
        this.bloomsSeasons = bloomsSeasons.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public void setSunNeeds(List<String> sunNeeds) {
        this.sunNeeds = sunNeeds.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public void setWaterNeeds(String waterNeeds) {
        this.waterNeeds = waterNeeds.toLowerCase();
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance.toLowerCase();
    }

    // only for debugging usage
    public String getAllDetails() {
        ArrayList<String> res = new ArrayList<>();
        res.add("Name: " + this.name);
        res.add("Aliases: " + (this.alias != null ? String.join(", ", this.alias) : "None"));
        res.add("Description: " + this.desc);
        res.add("Plant Types: " + (this.plantTypes != null ? String.join(", ", this.plantTypes) : "None"));
        res.add("Colors: " + this.colors);
        res.add("ColorList: " + (this.colorList != null ? String.join(", ", this.colorList) : "None"));
        res.add("Min Zone: " + this.minZone);
        res.add("Max Zone: " + this.maxZone);
        res.add("Blooms Seasons: " + (this.bloomsSeasons != null ? String.join(", ", this.bloomsSeasons) : "None"));
        res.add("Sun Needs: " + (this.sunNeeds != null ? String.join(", ", this.sunNeeds) : "None"));
        res.add("Water Needs: " + this.waterNeeds);
        res.add("Maintenance: " + this.maintenance);
        return String.join("\n", res);
    }
}