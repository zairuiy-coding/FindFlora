import smile.manifold.TSNE;
import java.util.List;

public class TSNEProcessor {
    private double[][] tsneResults;
    private List<String> flowerNames;
    private double[] minTsneValues = {Double.MAX_VALUE, Double.MAX_VALUE};
    private double[] maxTsneValues = {Double.MIN_VALUE, Double.MIN_VALUE};

    public TSNEProcessor(int[][] flowerMatrix, List<String> flowerNames) {
        this.flowerNames = flowerNames;
        runTSNE(flowerMatrix);
        scaleTSNEResults(0, 100);  // Scale results to [0, 100]
    }

    private void runTSNE(int[][] matrix) {
        // Convert the integer matrix to double for processing in TSNE
        double[][] data = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                data[i][j] = (double) matrix[i][j];
            }
        }

        // Configuring TSNE
        TSNE tsne = new TSNE(
                data, // data to be processed
                2, // number of dimensions to reduce down to
                50, // perplexity, often between 5-50
                200.0, // learning rate
                1000 // maximum iterations
        );

        this.tsneResults = tsne.coordinates; // store the results
        trackMinMaxValues(); // Track min and max values before scaling for more accurate range control
    }


    private void scaleTSNEResults(double newMin, double newMax) {
        double newRange = newMax - newMin;
        for (int j = 0; j < tsneResults[0].length; j++) {
            double range = maxTsneValues[j] - minTsneValues[j];
            for (int i = 0; i < tsneResults.length; i++) {
                tsneResults[i][j] = ((tsneResults[i][j] - minTsneValues[j]) / range) * newRange + newMin;
                // debug only:
                // System.out.println("Scaled tsne result at index (" + i + ", " + j + "): " + tsneResults[i][j]);
            }
        }
        // Update min and max values after scaling
        minTsneValues = new double[]{newMin, newMin};
        maxTsneValues = new double[]{newMax, newMax};
    }


    private void trackMinMaxValues() {
        for (double[] coord : tsneResults) {
            if (coord[0] < minTsneValues[0]) {
                minTsneValues[0] = coord[0];
            }
            if (coord[1] < minTsneValues[1]) {
                minTsneValues[1] = coord[1];
            }
            if (coord[0] > maxTsneValues[0]) {
                maxTsneValues[0] = coord[0];
            }
            if (coord[1] > maxTsneValues[1]) {
                maxTsneValues[1] = coord[1];
            }
        }
    }

    public double[][] getTsneResults() {
        return tsneResults;
    }

    public double[] getMinTsneValues() {
        return minTsneValues;
    }

    public double[] getMaxTsneValues() {
        return maxTsneValues;
    }

    public void printTSNEResults() {
        System.out.println("t-SNE Results:");
        for (int i = 0; i < tsneResults.length; i++) {
            System.out.printf("%s: (%.2f, %.2f)\n", flowerNames.get(i), tsneResults[i][0], tsneResults[i][1]);
        }
        System.out.println("Min values: [x: " + getMinTsneValues()[0] + ", y: " + getMinTsneValues()[1] + "]");
        System.out.println("Max values: [x: " + getMaxTsneValues()[0] + ", y: " + getMaxTsneValues()[1] + "]");
    }
}
