import java.util.*;

public class CosineSimilarityCalculator {

    /**
     * Calculates the cosine similarity between two vectors / flowers.
     */
    public static double cosineSimilarity(int[] vectorA, int[] vectorB) {
        if (vectorA.length != vectorB.length) {
            throw new IllegalArgumentException("Vectors must be of the same length");
        }

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }

        if (normA == 0 || normB == 0) {
            return 0;  // One of the vectors is zero vector, cosine similarity is not defined.
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    /**
     * Calculates cosine similarity matrix for all pairs of rows in the given matrix.
     * @param matrix the matrix of vectors.
     * @return a 2D array representing the cosine similarity scores between each pair of vectors.
     */
    public static double[][] calculateCosineSimilarityMatrix(int[][] matrix) {
        int numberOfRows = matrix.length;
        double[][] similarityMatrix = new double[numberOfRows][numberOfRows];

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = i; j < numberOfRows; j++) {
                if (i == j) {
                    similarityMatrix[i][j] = 0.0; // Cosine similarity of a vector with itself is 0
                } else {
                    double similarity = 1.0 - cosineSimilarity(matrix[i], matrix[j]);
                    similarityMatrix[i][j] = similarity;
                    similarityMatrix[j][i] = similarity; // symmetry in cosine similarity
                }
            }
        }

        return similarityMatrix;
    }

    /**
     * Prints the cosine similarity scores for each flower with every other flower.
     * @param similarityMatrix The matrix containing similarity scores.
     * @param flowerNames List of flower names corresponding to the matrix indices.
     */
    public static void printSimilarityScores(double[][] similarityMatrix, List<String> flowerNames) {
        System.out.println("Cosine Similarity Scores:");
        for (int i = 0; i < similarityMatrix.length; i++) {
            System.out.println("\nSimilarities for " + flowerNames.get(i) + ":");
            for (int j = 0; j < similarityMatrix[i].length; j++) {
                System.out.printf("%s: %.2f\n", flowerNames.get(j), similarityMatrix[i][j]);
            }
        }
    }

}
