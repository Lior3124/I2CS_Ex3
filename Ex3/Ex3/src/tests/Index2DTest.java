package assignments.Ex3;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * This Junit class represents Junit testing for Ex2 - Index2D
 * It contains testing functions for the class Index2D
 */

public class Index2DTest {

    Index2D p1 = new Index2D(0,0);
    Index2D p2 = new Index2D(2,2);
    Index2D p3 = new Index2D(2,5);
    Index2D p4 = new Index2D(10,7);

    /**
     * Tests that the copy Constructor of Index2D works
     */
    @Test
    void test_copyConstructor() {
        Index2D new_p =  new Index2D(p1);
        Index2D new_p2 =  new Index2D(p2);
        Index2D new_p4 =  new Index2D(p4);

        assertEquals(new_p.getX(), p1.getX());
        assertEquals(new_p.getY(), p1.getY());

        assertEquals(new_p2.getX(),p2.getX());
        assertEquals(new_p2.getY(),p2.getY());

        assertEquals(new_p4.getX(),p4.getX());
        assertEquals(new_p4.getY(),p4.getY());
    }

    /**
     * Tests that getX returns the right values
     */
    @Test
    void test_getX() {
        assertEquals(0,p1.getX());
        assertEquals(2,p2.getY());
        assertEquals(10,p4.getX());
    }

    /**
     * Tests that gety returns the right values
     */
    @Test
    void test_getY() {
        assertEquals(0,p1.getY());
        assertEquals(5,p3.getY());
        assertEquals(7,p4.getY());
    }

    /**
     * Tests the distance between two points
     */
    @Test
    void test_distance2D() {
        assertEquals(Math.sqrt(2*2+2*2), p1.distance2D(p2));
        assertEquals(2*Math.sqrt(17), p4.distance2D(p3));
        assertEquals(3, p2.distance2D(p3));
    }

    /**
     * Tests the distance if p2 is null(Should throw a runtime exception)
     */
    @Test
    void test_distance2D2() {
        assertThrows(RuntimeException.class, () -> p1.distance2D(null));
        assertThrows(RuntimeException.class, () -> p1.distance2D(null));
    }

    /**
     * Tests the ToString method in Index2D, should return "(x,y)"
     */
    @Test
    void test_ToString(){
        String expected1 = "0,0";
        String expected2 = "2,2";
        String expected3 = "10,7";
        assertEquals(expected1, p1.toString());
        assertEquals(expected2, p2.toString());
        assertEquals(expected3, p4.toString());
    }

    /**
     * Tests if two points are equal
     */
    @Test
    void test_equals(){
        Index2D p1_copy = new Index2D(0,0);
        Index2D p2_copy = new Index2D(2,2);
        Index2D p3_copy = new Index2D(2,5);
        Index2D p4_copy = new Index2D(10,7);

        assertTrue(p1.equals(p1_copy));
        assertTrue(p2.equals(p2_copy));
        assertTrue(p3.equals(p3_copy));
        assertTrue(p4.equals(p4_copy));
    }

    /**
     * Tests if two points are equal but one object is not of Index2D class or null
     */
    @Test
    void test_equals2(){
        assertFalse(p2.equals(null));
        assertFalse(p2.equals("hello"));
        assertFalse(p4.equals("(10,7)"));
        assertFalse(p4.equals(10));
    }
}
