package assignments.Ex3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Intro2CS, 2026A, this is a very
 */
class MapTest {
    /**
     */
    private int[][] _map_3_3 = {{0,1,0}, {1,0,1}, {0,1,0}};
    private Map2D _m0, _m1, _m3_3;
    @BeforeEach
    public void setuo() {
        _m3_3 = new Map(_map_3_3);
        _m0 = new Map(5,5,0);
        _m1 = new Map(5,5,0);
    }
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int [500][500];
        _m1.init(bigarr);
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3,2);
        _m1.setCyclic(true);
        _m1.fill(p1,1);
    }

    @Test
    void testInit() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
    }

    @Test
    void testEquals() {
        assertEquals(_m0,_m1);
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0,_m1);
    }

    private final int[][] arr1 = {
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0}
    };
    private final int[][] arr2 = {
            {1,2,3,4},
            {2,3,4,5},
    };
    private final int[][] arr3 = {
            {3,3,3},
            {3,3,3},
            {3,3,3}
    };
    private Map map1 = new Map(arr1);
    private Map map2 = new Map(arr2);
    private Map map3 = new Map(arr3);

    /**
     * Test the map constructor
     * gets width,height and value
     */
    @Test
    void test_map1(){
        _m0 = new Map(5,5,0);
        assertEquals(_m0,map1);
        _m1 = new Map(3,3,3);
        assertEquals(_m1,map3);
    }

    /**
     * Test the map constructor
     * gets size
     */
    @Test
    void test_map2() {
        _m0 = new Map(5);
        assertEquals(_m0,map1);
    }

    /**
     * Test map constructor with cases that should return runtime exception(given arr is ragged,null or empty)
     */
    @Test
    void test_map3() {
        int [][] empty = new int[][]{};
        int [][] ragged = new int[][]{
                {1,2,3,4},
                {2,3,4,}
        };
        Assertions.assertThrows(RuntimeException.class, () -> {_m0 = new Map(null);});
        Assertions.assertThrows(RuntimeException.class, () -> {_m0 = new Map(empty);});
        Assertions.assertThrows(RuntimeException.class, () -> {_m0 = new Map(ragged);});
    }

    @Test
    void test_sameDimensnions(){
        assertTrue(map3.sameDimensions(_m3_3));
        assertTrue(map1.sameDimensions(map1));
        assertFalse(map1.sameDimensions(map2));
    }

    /**
     * Tests getMap
     */
    @Test
    void test_getMap() {
        int[][] arr = new int [2][2];
        arr = map1.getMap();
        _m0 = new Map(arr);
        assertEquals(map1,_m0);
    }

    /**
     * Test getWidth
     */
    @Test
    void test_getWidth(){
        assertEquals(5,map1.getWidth());
        assertEquals(3,map3.getWidth());
        assertEquals(4,map2.getWidth());
    }

    /**
     * Test getHeight
     */
    @Test
    void test_getHeight(){
        assertEquals(5,map1.getHeight());
        assertEquals(2,map2.getHeight());
        assertEquals(3,map3.getHeight());
    }

    /**
     * Test getPixel
     * gets x and y values (int x, int y)
     */
    @Test
    void test_getPixel(){
        assertEquals(0,map1.getPixel(0,0));
        assertEquals(5,map2.getPixel(1,3));
        assertEquals(map2.getPixel(1,1),map2.getPixel(0,2));
    }

    /**
     * Test getPixel
     * gets a point(Pixel2D)
     */
    @Test
    void test_getPixel2(){
        Index2D p1 = new Index2D(0,0);
        Index2D p2 = new Index2D(1,3);
        Index2D p3 = new Index2D(0,1);
        Index2D p4 = new Index2D(1,0);

        assertEquals(0,map1.getPixel(p1));
        assertEquals(5,map2.getPixel(p2));
        assertEquals(map2.getPixel(p4),map2.getPixel(p3));
    }

    /**
     * Test set pixel
     * gets 3 integers x value y value and a value to insert(int x, int y, int v)
     */
    @Test
    void test_setPixel(){
        map1.setPixel(1,1,1);
        assertEquals(1,map1.getPixel(1,1));

        map2.setPixel(1,2,0);
        assertEquals(0,map2.getPixel(1,2));
    }

    /**
     * Test set pixel
     * gets a point(Pixel2D) and a value to insert (Pixel2D p, int v)
     */
    @Test
    void test_setPixel2(){
        Index2D p1 = new Index2D(0,0);
        Index2D p2 = new Index2D(3,1);
        Index2D p3 = new Index2D(2,0);

        map1.setPixel(p1,60);
        map1.setPixel(p2,120);
        map3.setPixel(p3,5);

        assertEquals(60,map1.getPixel(0,0));
        assertEquals(120,map1.getPixel(3,1));
        assertEquals(5,map3.getPixel(2,0));
    }

    /**
     * Test isInside
     * return true if point(Pixel2D) is inside the map
     */
    @Test
    void test_isInside(){
        Index2D p1 = new Index2D(10,10);
        Index2D p2 = new Index2D(4,4);
        Index2D p3 = new Index2D(2,0);

        assertFalse(map1.isInside(p1));
        assertFalse(map2.isInside(p2));
        assertTrue(map1.isInside(p2));
        assertTrue(map3.isInside(p3));

        int[][] new_arr ={
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0}
        };
        Map2D new_map = new Map(new_arr);

        Index2D new_p1 = new Index2D(-1,0);

        assertFalse(map1.isInside(new_p1));

        Index2D new_p2 = new Index2D(4,4);
        assertFalse(new_map.isInside(new_p2));

    }

    @Test
    void test_fillNotCyclic(){
        int[][] test1 = {
                {0,0,0,0,0},
                {0,1,1,1,0},
                {0,1,1,1,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        Map test_1 = new Map(test1);
        Index2D p1 = new Index2D(2,2);
        test_1.setCyclic(false);
        int num1 =test_1.fill(p1,0);
        assertEquals(6,num1);
        assertEquals(test_1,map1);


        int[][] test2 = {
                {0,0,0,0,0},
                {0,1,1,1,0},
                {0,1,0,1,0},
                {0,1,0,1,0},
                {0,1,1,1,0},
        };
        Map test_2 = new Map(test2);
        test_2.setCyclic(false);
        int num2 = test_2.fill(p1,5);
        assertEquals(2,num2);

        int[][] result2 = {
                {0,0,0,0,0},
                {0,1,1,1,0},
                {0,1,5,1,0},
                {0,1,5,1,0},
                {0,1,1,1,0},
        };
        Map result_2 = new Map(result2);
        assertEquals(result_2,test_2);

        int[][] test3 = {
                {0,0,0,0,0},
                {0,1,1,1,0},
                {0,1,1,1,0},
                {0,1,1,1,0},
                {0,1,1,1,0},
        };
        Map test_3 = new Map(test3);
        test_3.setCyclic(false);
        int num3 =  result_2.fill(p1,1);
        assertEquals(test_3,result_2);
        assertEquals(2,num3);
    }

    /**
     * Test the fill function on a cyclic matrix
     */
    @Test
    void test_fillCyclic(){
        int[][] test1 = {
                {1,0,0,0,0},
                {1,1,1,1,0},
                {0,2,0,0,0},
        };
        int[][] result1 = {
                {1,5,5,5,5},
                {1,1,1,1,5},
                {5,2,5,5,5},
        };
        Map test_1 = new Map(test1);
        Map result_1 = new Map(result1);
        Index2D p1 = new Index2D(2,2);
        test_1.setCyclic(true);
        int num1 =test_1.fill(p1,5);
        assertEquals(9,num1);

        assertEquals(result_1,test_1);

    }

    /**
     * Test shortestPath when not cyclic
     */
    @Test
    void shortestPath_notCyclic(){
        int[][] test1 = {
                {0,0,0,0,0},
                {0,1,1,1,0},
                {0,1,1,1,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        Map test_1 = new Map(test1);
        Index2D p1 = new Index2D(2,0);
        Index2D p2 = new Index2D(2,4);

        test_1.setCyclic(false);
        Pixel2D[] ans = test_1.shortestPath(p1,p2,1);

        Pixel2D[] expected = new Pixel2D[7];
        expected[0] = p1;
        expected[1] = new Index2D(3,0);
        expected[2] = new Index2D(3,1);
        expected[3] = new Index2D(3,2);
        expected[4] = new Index2D(3,3);
        expected[5] = new Index2D(3,4);
        expected[6] = p2;
        assertEquals(expected[0],ans[0]);
        assertEquals(expected[1],ans[1]);
        assertEquals(expected[2],ans[2]);
        assertEquals(expected[3],ans[3]);
        assertEquals(expected[4],ans[4]);
        assertEquals(expected[5],ans[5]);
        assertEquals(expected[6],ans[6]);
    }

    /**
     * Test shortestPath when cyclic
     */
    @Test
    void shortestPath_cyclic() {
        int[][] test1 = {
                {0, 0, 0, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
        Map test_1 = new Map(test1);
        Index2D p1 = new Index2D(2, 0);
        Index2D p2 = new Index2D(2, 2);

        test_1.setCyclic(true);
        Pixel2D[] ans = test_1.shortestPath(p1, p2, 1);

        Pixel2D[] expected = new Pixel2D[8];
        expected[0] = p1;
        expected[1] = new Index2D(3, 0);
        expected[2] = new Index2D(3, 1);
        expected[3] = new Index2D(3, 2);
        expected[4] = new Index2D(2, 2);
        assertEquals(expected[0], ans[0]);
        assertEquals(expected[1], ans[1]);
        assertEquals(expected[2], ans[2]);
        assertEquals(expected[3], ans[3]);
        assertEquals(expected[4], ans[4]);
    }

    /**
     * Test shortestPath when there is no path,should return null
     */
    @Test
    void shortestPath_null(){
        int[][] test1 = {
                {0, 0, 0, 1, 0},
                {1, 1, 1, 1, 1},
                {0, 1, 0, 1, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
        Map test_1 = new Map(test1);
        Index2D p1 = new Index2D(2, 0);
        Index2D p2 = new Index2D(2, 2);

        test_1.setCyclic(false);
        Pixel2D[] ans = test_1.shortestPath(p1, p2, 1);
        assertNull(ans);
    }

    /**
     * Test allDistance for multiple cases (cyclic, not cyclic, everything is not accessible)
     */
    @Test
    void allDistance(){
        int[][] test1 = {
                {0,0,0,0,0},
                {0,1,1,1,0},
                {0,1,1,1,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };
        Map test_1 = new Map(test1);
        Index2D p = new Index2D(0,0);

        Map ans1  = null;
        test_1.setCyclic(false);
        ans1 = (Map)test_1.allDistance(p,1);

        int[][] expected1={
                {0,1,2,3,4},
                {1,-1,-1,-1,5},
                {2,-1,-1,-1,6},
                {3,4,5,6,7},
                {4,5,6,7,8}
        };
        Map expected_1 = new Map(expected1);
        assertEquals(expected_1,ans1);

        Map ans2 = null;
        test_1.setCyclic(true);
        ans2 = (Map)test_1.allDistance(p,1);

        int[][] expected2={
                {0,1,2,2,1},
                {1,-1,-1,-1,2},
                {2,-1,-1,-1,3},
                {2,3,4,4,3},
                {1,2,3,3,2}
        };

        int[][] test3 = {
                {1,1,1,1,1},
                {1,1,1,1,1},
                {1,1,1,1,1},
                {1,1,1,1,1},
                {1,1,1,1,1}
        };
        Map test_3 = new Map(test3);
        Map ans3 = null;
        test_3.setCyclic(true);
        ans3 = (Map)test_3.allDistance(p,1);

        int[][] expected3 = {
                {-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1}
        };
        Map expected_3 = new Map(expected3);

        for(int i=0; i< ans1.getHeight();i++){
            for(int j=0; j< ans1.getWidth();j++){
                System.out.print(ans3.getPixel(j,i));
            }
            System.out.println();
        }

        assertEquals(expected_3,ans3);
    }


}