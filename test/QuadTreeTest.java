import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class QuadTreeTest {
    private QuadTree quadTree;
    private FlowerDatabase flowerDatabase;
    private TSNEProcessor tsneProcessor;
    private List<String> flowerNames;

    @Before
    public void setUp() throws Exception {
        // Initialize the FlowerDatabase
        flowerDatabase = new FlowerDatabase();
        // Load test data
        flowerDatabase.loadFlowersFromCSV("test/FlowerDatabase_test.csv");

        // Build the matrix and initialize the QuadTree
        FlowerMatrixBuilder flowerMatrixBuilder = new FlowerMatrixBuilder(flowerDatabase);
        int[][] flowerMatrix = flowerMatrixBuilder.buildMatrix();
        flowerNames = flowerMatrixBuilder.getFlowerNames();

        tsneProcessor = new TSNEProcessor(flowerMatrix, flowerNames);
        double[][] tsneResults = tsneProcessor.getTsneResults();
        tsneProcessor.printTSNEResults();

        // Initialize the QuadTree with boundary based on min and max t-SNE values
        double[] minValues = tsneProcessor.getMinTsneValues();
        double[] maxValues = tsneProcessor.getMaxTsneValues();
        quadTree = new QuadTree(minValues[0], maxValues[0], minValues[1], maxValues[1]);

        // Insert flowers into the QuadTree
        for (int i = 0; i < tsneResults.length; i++) {
            quadTree.insert(flowerNames.get(i), tsneResults[i][0], tsneResults[i][1]);
        }

        // debug only:
        System.out.println("##### total number of inserted node: " + quadTree.insertNum);
    }


    @Test
    public void testFindKApproximateNearestNeighbors() {
        // Insert a known flower at a specific location
        String targetFlower = "amsonia";

        // Test finding the nearest 3 neighbors
        List<String> neighbors = quadTree.findKApproximateNearestNeighbors(targetFlower, 3);

        // Assert
        assertNotNull("Neighbors list should not be null", neighbors);
        assertEquals(3, neighbors.size());

    }


    @Test
    public void testInsertAndSearch() {
        // Check insertion and search consistency
        double testX = (tsneProcessor.getMaxTsneValues()[0] + tsneProcessor.getMinTsneValues()[0]) / 2;
        double testY = (tsneProcessor.getMaxTsneValues()[1] + tsneProcessor.getMinTsneValues()[1]) / 2;
        quadTree.insert("TestFlower", testX, testY);
        List<String> neighbors = quadTree.findKApproximateNearestNeighbors("TestFlower", 1);
        assertEquals("1 neighbors should be found", 1, neighbors.size());
    }
}
