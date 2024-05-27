// import com.opencsv.bean.FieldAccess;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class TSNEProcessorTest {
    private FlowerDatabase flowerDatabase;
    private TSNEProcessor tsneProcessor;

    @Before
    public void setUp() throws Exception {
        // Initialize the database and load data from CSV
        flowerDatabase = new FlowerDatabase();
        flowerDatabase.loadFlowersFromCSV("test/FlowerDatabase_test.csv");

        // Prepare matrix data for TSNE processing
        FlowerMatrixBuilder flowerMatrixBuilder = new FlowerMatrixBuilder(flowerDatabase);
        int[][] flowerMatrix = flowerMatrixBuilder.buildMatrix();
        List<String> flowerNames = flowerMatrixBuilder.getFlowerNames();

        // Initialize the TSNEProcessor with the matrix data
        tsneProcessor = new TSNEProcessor(flowerMatrix, flowerNames);
    }

    @Test
    public void testTSNEResultsNotNull() {
        // for debugging only
        tsneProcessor.printTSNEResults();
//        System.out.println("Min values: [x: " + tsneProcessor.getMinTsneValues()[0] + ", y: " + tsneProcessor.getMinTsneValues()[1] + "]");
//        System.out.println("Max values: [x: " + tsneProcessor.getMaxTsneValues()[0] + ", y: " + tsneProcessor.getMaxTsneValues()[1] + "]");
        // Ensure that the t-SNE results are not null
        assertNotNull("t-SNE results should not be null", tsneProcessor.getTsneResults());
    }

    @Test
    public void testTSNEResultsDimensions() {
        // Check that the t-SNE results have correct dimensions (2D reduction)
        double[][] results = tsneProcessor.getTsneResults();
        assertTrue("There should be results for each flower", results.length > 0);
        assertEquals("Each result should have two dimensions", 2, results[0].length);
    }

    @Test
    public void testTSNEResultsValues() {
        // Test specific values or ranges if applicable; this might depend on the expected dataset characteristics
        double[][] results = tsneProcessor.getTsneResults();
        for (double[] result : results) {
            assertTrue("t-SNE coordinates should be valid numbers",
                    Double.isFinite(result[0]) && Double.isFinite(result[1]));
        }
    }
}
