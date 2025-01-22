package physical.abstractt;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physical.Vector2d;
import physical.Plant;
import physical.abstractt.Animal;
import physical.abstractt.World;
import others.Statistics;
import java.util.List;
import physical.NormalWorld;
import physical.*;
import observers.MapChangeListener;
public class WorldTest {
    private World world;
    private int width = 10;
    private int height = 10;

    @BeforeEach
    public void setUp() {
        world = new NormalWorld(width, height); // Używamy NormalWorld, ponieważ jest dziedziczone po World
    }

    @Test
    public void testInitialWorldSize() {
        assertEquals(width, world.getBoundary().topRight().getX());
        assertEquals(height, world.getBoundary().topRight().getY());
    }

    @Test
    public void testGetAverageEnergy() {
        // Testujemy, czy średnia energia zwierząt jest obliczana poprawnie
        Animal animal1 = new AnimalType1(List.of(1, 2, 3), 100, new Vector2d(0, 0));
        Animal animal2 = new AnimalType1(List.of(1, 2, 3), 200, new Vector2d(1, 1));
        world.placeAnimal(animal1);
        world.placeAnimal(animal2);
        assertEquals(150, world.getAverageEnergy());
    }

    @Test
    public void testMoveAnimals() {
        Animal animal = new AnimalType1(List.of(1, 2, 3), 100, new Vector2d(0, 0));
        world.placeAnimal(animal);
        world.moveAnimals();
        assertTrue(world.getAnimals().containsKey(animal.getPosition()));
    }

    @Test
    public void testReproduction() {
        Animal parent1 = new AnimalType1(List.of(1, 2, 3), 100, new Vector2d(0, 0));
        Animal parent2 = new AnimalType1(List.of(1, 2, 3), 100, new Vector2d(0, 0));
        world.placeAnimal(parent1);
        world.placeAnimal(parent2);
        world.reproduction(50, 20, 1, 5);
        assertTrue(world.getAllAnimals().size() > 2);
    }
}
