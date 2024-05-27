import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class CosineSimilarityCalculatorTest {

    @Test
    public void testCosineSimilarityWithIdenticalVectors() {
        int[] vectorA = {1, 1, 1};
        int[] vectorB = {1, 1, 1};
        double expected = 1.0;
        double actual = CosineSimilarityCalculator.cosineSimilarity(vectorA, vectorB);
        assertEquals("Cosine similarity of identical vectors should be 1", expected, actual, 0.001);
    }

    @Test
    public void testCosineSimilarityWithOrthogonalVectors() {
        int[] vectorA = {1, 0, 0};
        int[] vectorB = {0, 1, 0};
        double expected = 0.0;
        double actual = CosineSimilarityCalculator.cosineSimilarity(vectorA, vectorB);
        assertEquals("Cosine similarity of orthogonal vectors should be 0", expected, actual, 0.001);
    }

    @Test
    public void testCosineSimilarityWithZeroVector() {
        int[] vectorA = {0, 0, 0};
        int[] vectorB = {1, 1, 1};
        double expected = 0.0;
        double actual = CosineSimilarityCalculator.cosineSimilarity(vectorA, vectorB);
        assertEquals("Cosine similarity involving a zero vector should be 0", expected, actual, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCosineSimilarityWithDifferentLengthVectors() {
        int[] vectorA = {1, 1};
        int[] vectorB = {1, 1, 1};
        CosineSimilarityCalculator.cosineSimilarity(vectorA, vectorB);
    }


    @Test
    public void testMatrixAndCosineSimilarityWithTestData() {

        // Initialize the database and load test data
        FlowerDatabase database = new FlowerDatabase();
        database.loadFlowersFromCSV("test/FlowerDatabase_test.csv");

        // Initialize the matrix builder with the test database
        FlowerMatrixBuilder matrixBuilder = new FlowerMatrixBuilder(database);

        // Build the matrix from the test data
        int[][] matrix = matrixBuilder.buildMatrix();
        List<String> flowerNames = matrixBuilder.getFlowerNames();

        // Calculate the cosine similarity matrix
        double[][] similarityMatrix = CosineSimilarityCalculator.calculateCosineSimilarityMatrix(matrix);

        CosineSimilarityCalculator.printSimilarityScores(similarityMatrix, flowerNames);

        assertNotNull("Matrix should not be null", matrix);
        assertTrue("Matrix should have at least one row", matrix.length > 0);
        assertNotNull("Similarity matrix should not be null", similarityMatrix);
        assertTrue("Similarity matrix should have at least one row", similarityMatrix.length > 0);
    }
}


