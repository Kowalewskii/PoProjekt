package physical;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import physical.Vector2d;

class Vector2dTest {

    private Vector2d vector1;
    private Vector2d vector2;
    private Vector2d vector3;

    @BeforeEach
    void setUp() {
        vector1 = new Vector2d(2, 3);
        vector2 = new Vector2d(2, 3);
        vector3 = new Vector2d(4, 5);
    }

    @Test
    void testVectorCreation() {
        assertNotNull(vector1);
        assertEquals(2, vector1.getX());
        assertEquals(3, vector1.getY());
    }

    @Test
    void testToString() {
        assertEquals("(2,3)", vector1.toString());
    }

    @Test
    void testEquals() {
        // Testowanie metody equals
        assertTrue(vector1.equals(vector2));
        assertFalse(vector1.equals(vector3));
        assertFalse(vector1.equals(null));
        assertFalse(vector1.equals("not a vector"));
    }

    @Test
    void testHashCode() {
        assertEquals(vector1.hashCode(), vector2.hashCode());
        assertNotEquals(vector1.hashCode(), vector3.hashCode());
    }

    @Test
    void testMove() {
        Vector2d mover = new Vector2d(1, -1);
        Vector2d movedVector = vector1.move(mover);

        assertEquals(new Vector2d(3, 2), movedVector);
    }
}