package physical;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import physical.Vector2d;
import physical.AnimalType1;
import enums.MoveDirection;

import java.util.Arrays;
import java.util.List;

public class AnimalType1Test {

    @Test
    public void testAnimalType1Initialization() {
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);
        AnimalType1 animal = new AnimalType1(genes, initialEnergy, position);
        assertNotNull(animal);
        assertEquals(genes, animal.getGenes());
        assertEquals(initialEnergy, animal.getEnergy());
        assertEquals(position, animal.getPosition());
    }

    @Test
    public void testDescendantGeneration() {
        List<Integer> genes1 = Arrays.asList(0, 1, 2, 3, 4);
        List<Integer> genes2 = Arrays.asList(4, 3, 2, 1, 0);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);

        AnimalType1 parent1 = new AnimalType1(genes1, initialEnergy, position);
        AnimalType1 parent2 = new AnimalType1(genes2, initialEnergy, position);
        AnimalType1 descendant = (AnimalType1) parent1.descendant(parent2, 50, 1, 2);
        assertNotNull(descendant);
        assertEquals(50, descendant.getEnergy());
        assertEquals(genes1.size(), descendant.getGenes().size());
    }
    @Test
    public void testWellBeing() {
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);
        AnimalType1 animal = new AnimalType1(genes, initialEnergy, position);
        assertTrue(animal.wellBeing());
        animal.dailyUpdate(101);
        assertFalse(animal.wellBeing());
    }

    @Test
    public void testMove() {
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(1, 1);
        AnimalType1 animal = new AnimalType1(genes, initialEnergy, position);
        animal.move(new NormalWorld(10,10));
        assertNotEquals(position, animal.getPosition());
    }

    @Test
    public void testDailyUpdate() {
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);
        AnimalType1 animal = new AnimalType1(genes, initialEnergy, position);
        int initialEnergyBeforeUpdate = animal.getEnergy();
        animal.dailyUpdate(10);
        assertEquals(initialEnergyBeforeUpdate - 10, animal.getEnergy());
    }
}
