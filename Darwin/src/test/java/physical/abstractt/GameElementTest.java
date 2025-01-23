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
        position = new Vector2d(5, 5);
        plant = new Plant(position);
    }

    @Test
    void testPlantCreation() {
        assertNotNull(plant);
        assertEquals(position, plant.getPosition());
        assertNotNull(plant.getId());
    }

    @Test
    void testGetVisualization() {
        Pane pane = new Pane();
        Node visualization = plant.getVisualization(pane);

        assertNotNull(visualization);
        assertInstanceOf(Pane.class, visualization);
        assertEquals("-fx-background-color: green", visualization.getStyle());
    }


}