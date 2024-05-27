import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FlowerMatrixBuilderTest {

    private FlowerDatabase flowerDatabase;
    private FlowerMatrixBuilder matrixBuilder;

    @Before
    public void setUp() {
        flowerDatabase = new FlowerDatabase();
        flowerDatabase.loadFlowersFromCSV("test/FlowerDatabase_test.csv");
        matrixBuilder = new FlowerMatrixBuilder(flowerDatabase);
    }

    @Test
    public void testMatrixSize() {
        int[][] matrix = matrixBuilder.buildMatrix();
        int expectedNumFlowers = flowerDatabase.getFlowersMap().size();
        int expectedNumAttributes = matrixBuilder.getAttributes().size();

        assertEquals("Num of rows should match num of flowers", expectedNumFlowers, matrix.length);
        assertEquals("Num of columns should match num of attributes", expectedNumAttributes, matrix[0].length);
    }

    /*
    Attribute Indexes:
    0 - Zone1
    1 - Zone2
    2 - Zone3
    3 - Zone4
    4 - Zone5
    5 - Zone6
    6 - Zone7
    7 - Zone8
    8 - Zone9
    9 - Zone10
    10 - Zone11
    11 - Zone12
    12 - Zone13
    13 - BloomsSeasons:autumn
    14 - BloomsSeasons:spring
    15 - BloomsSeasons:summer
    16 - BloomsSeasons:winter
    17 - WaterNeeds:avarage
    18 - WaterNeeds:low
    19 - Colors:black
    20 - Colors:blue
    21 - Colors:brown
    22 - Colors:green
    23 - Colors:orange
    24 - Colors:pink
    25 - Colors:purple
    26 - Colors:red
    27 - Colors:white
    28 - Colors:yellow
    29 - PlantType:annuals
    30 - PlantType:bulbs
    31 - PlantType:perennials
    32 - PlantType:shrubs
    33 - PlantType:trees
    34 - SunNeeds:full sun
    35 - SunNeeds:partial sun
    36 - SunNeeds:shade
    37 - Maintenance:hard
    38 - Maintenance:low
    39 - Maintenance:medium

    First 3 flowers/rows: aconitum, anthurium, aster
    * */

    @Test
    public void testMatrixContent() {
        int[][] matrix = matrixBuilder.buildMatrix();
        // test aconitum
        assertTrue(matrix[0][0] == 0);
        assertTrue(matrix[0][2] == 1);
        // test anthurium
        assertTrue(matrix[1][4] == 0);
        assertTrue(matrix[1][9] == 1);
        // test aster
        assertTrue(matrix[2][38] == 0);
        assertTrue(matrix[2][39] == 1);
    }
}
