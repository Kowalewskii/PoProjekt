package physical;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physical.Vector2d;
import physical.Plant;
import physical.NormalWorld;
import physical.abstractt.Animal;
import java.util.List;

public class NormalWorldTest {
    private NormalWorld world;
    private int width = 10;
    private int height = 10;

    @BeforeEach
    public void setUp() {
        world = new NormalWorld(width, height);
    }

    @Test
    public void testInitialWorldSize() {
        assertEquals(width, world.getBoundary().topRight().getX());
        assertEquals(height, world.getBoundary().topRight().getY());
    }

    @Test
    public void testPlantPlacement() {
        world.placeNPlants(5);
        assertEquals(5, world.getPlants().size());
    }

    @Test
    public void testRemoveEatenPlant() {
        Plant plant = new Plant(new Vector2d(3, 3));
        world.getPlants().put(plant.getPosition(), plant);
        world.removeEaten(plant);
        assertFalse(world.getPlants().containsKey(plant.getPosition()));
    }

    @Test
    public void testRemoveEatenInJungle() {
        Plant plant = new Plant(new Vector2d(5, 5));
        world.getPlants().put(plant.getPosition(), plant);
        world.removeEaten(plant);
        assertTrue(world.getPositions1().contains(plant.getPosition()));
    }

    @Test
    public void testRemoveEatenOutOfJungle() {
        Plant plant = new Plant(new Vector2d(8, 8));
        world.getPlants().put(plant.getPosition(), plant);
        world.removeEaten(plant);
        assertTrue(world.getPositions2().contains(plant.getPosition()));
    }

    @Test
    public void testIsInJungle() {
        Vector2d positionInJungle = new Vector2d(5, 5);
        Vector2d positionOutOfJungle = new Vector2d(8, 8);
        assertTrue(world.isInJungle(positionInJungle));
        assertFalse(world.isInJungle(positionOutOfJungle));
    }
}
