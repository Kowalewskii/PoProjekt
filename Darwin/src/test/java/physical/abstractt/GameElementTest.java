package physical.abstractt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import physical.Vector2d;
import physical.Plant;
import javafx.scene.layout.Pane;
import javafx.scene.Node;

class GameElementTest {

    private Plant plant;
    private Vector2d position;

    @BeforeEach
    void setUp() {
        position = new Vector2d(5, 5);  // Ustalamy przykładową pozycję
        plant = new Plant(position);  // Tworzymy nową roślinę
    }

    @Test
    void testPlantCreation() {
        // Testowanie poprawności tworzenia rośliny
        assertNotNull(plant);  // Sprawdzamy, czy roślina została poprawnie utworzona
        assertEquals(position, plant.getPosition());  // Sprawdzamy, czy pozycja rośliny jest zgodna z oczekiwaniami
        assertNotNull(plant.getId());  // Sprawdzamy, czy ID rośliny zostało wygenerowane
    }

    @Test
    void testGetVisualization() {
        // Testowanie metody getVisualization
        Pane pane = new Pane();  // Tworzymy panel, w którym roślina będzie wyświetlana
        Node visualization = plant.getVisualization(pane);  // Pobieramy wizualizację rośliny

        assertNotNull(visualization);  // Sprawdzamy, czy wizualizacja została utworzona
        assertTrue(visualization instanceof Pane);  // Sprawdzamy, czy wizualizacja jest instancją klasy Pane
        assertEquals("-fx-background-color: green", visualization.getStyle());  // Sprawdzamy, czy roślina ma odpowiedni kolor
    }

    @Test
    void testPlantFireState() {
        // Testowanie stanu ognia rośliny
        assertFalse(plant.isOnFire());  // Początkowo roślina nie powinna być na ogniu

        plant.setOnFire(true);  // Ustawiamy roślinę na ogniu
        assertTrue(plant.isOnFire());  // Sprawdzamy, czy roślina jest na ogniu

        plant.setOnFire(false);  // Ustawiamy roślinę z powrotem na brak ognia
        assertFalse(plant.isOnFire());  // Sprawdzamy, czy roślina nie jest na ogniu
    }
}