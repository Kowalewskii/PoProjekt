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
        // Tworzymy różne instancje wektorów
        vector1 = new Vector2d(2, 3);
        vector2 = new Vector2d(2, 3);
        vector3 = new Vector2d(4, 5);
    }

    @Test
    void testVectorCreation() {
        // Testowanie poprawności tworzenia wektora
        assertNotNull(vector1);  // Sprawdzamy, czy obiekt nie jest null
        assertEquals(2, vector1.getX());  // Sprawdzamy, czy wartość x to 2
        assertEquals(3, vector1.getY());  // Sprawdzamy, czy wartość y to 3
    }

    @Test
    void testToString() {
        // Testowanie metody toString
        assertEquals("(2,3)", vector1.toString());  // Sprawdzamy, czy metoda toString zwraca odpowiedni wynik
    }

    @Test
    void testEquals() {
        // Testowanie metody equals
        assertTrue(vector1.equals(vector2));  // Powinny być równe, bo mają te same wartości x i y
        assertFalse(vector1.equals(vector3));  // Powinny być różne, bo mają różne wartości x i y
        assertFalse(vector1.equals(null));  // Sprawdzamy, czy nie jest równe null
        assertFalse(vector1.equals("not a vector"));  // Powinno być false, bo porównujemy z obiektem innego typu
    }

    @Test
    void testHashCode() {
        // Testowanie metody hashCode
        assertEquals(vector1.hashCode(), vector2.hashCode());  // Dwa wektory o tych samych współrzędnych powinny mieć ten sam hashCode
        assertNotEquals(vector1.hashCode(), vector3.hashCode());  // Wektory o różnych współrzędnych powinny mieć różne hashCode
    }

    @Test
    void testMove() {
        // Testowanie metody move
        Vector2d mover = new Vector2d(1, -1);
        Vector2d movedVector = vector1.move(mover);  // Przesuwamy vector1 o (1, -1)

        assertEquals(new Vector2d(3, 2), movedVector);  // Oczekiwany wynik po przesunięciu
    }
}