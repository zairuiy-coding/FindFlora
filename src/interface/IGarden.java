import java.util.List;

public interface IGarden {

    void updateGardenInfo(double size, int hardinessZone, String sunExposure, String waterSupply);
    void addFlower(Flower flower);
    void removeFlower(Flower flower);
}
