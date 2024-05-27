import java.util.List;

public interface IQuadTree {
    boolean insert(String flowerName, double x, double y);
    List<String> findKApproximateNearestNeighbors(String flowerName, int k);
}
