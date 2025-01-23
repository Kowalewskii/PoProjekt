package physical;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import physical.NormalWorld;
import physical.Vector2d;
import physical.AnimalType2;
import enums.MoveDirection;

import java.util.Arrays;
import java.util.List;

public class AnimalType2Test {

    @Test
    public void testAnimalType2Initialization() {
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);
        AnimalType2 animal = new AnimalType2(genes, initialEnergy, position);
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

        AnimalType2 parent1 = new AnimalType2(genes1, initialEnergy, position);
        AnimalType2 parent2 = new AnimalType2(genes2, initialEnergy, position);
        AnimalType2 descendant = (AnimalType2) parent1.descendant(parent2, 50, 1, 2);

        assertNotNull(descendant);
        assertEquals(50, descendant.getEnergy());
        assertEquals(genes1.size(), descendant.getGenes().size());
    }

    @Test
    public void testDescendantGenes() {
        List<Integer> genes1 = Arrays.asList(0, 1, 2, 3, 4);
        List<Integer> genes2 = Arrays.asList(4, 3, 2, 1, 0);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);

        AnimalType2 parent1 = new AnimalType2(genes1, initialEnergy, position);
        AnimalType2 parent2 = new AnimalType2(genes2, initialEnergy, position);

        AnimalType2 descendant = (AnimalType2) parent1.descendant(parent2, 50, 1, 2);

        List<Integer> descendantGenes = descendant.getGenes();

        assertEquals(genes1.size(), descendantGenes.size());
    }

    @Test
    public void testMutateGeneDirection() {
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);

        AnimalType2 animal = new AnimalType2(genes, initialEnergy, position);

        List<Integer> initialGenes = animal.getGenes();

        animal.mutate(1, 3);
        for (int i = 0; i < initialGenes.size(); i++) {
            int initialGene = initialGenes.get(i);
            int mutatedGene = animal.getGenes().get(i);
            int direction = mutatedGene - initialGene;

            assertTrue(Math.abs(mutatedGene-initialGene)<=1, "Gene mutation direction is invalid");
        }
    }

    @Test
    public void testWellBeing() {
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);

        AnimalType2 animal = new AnimalType2(genes, initialEnergy, position);

        assertTrue(animal.wellBeing());

        animal.dailyUpdate(101);
        assertFalse(animal.wellBeing());
    }

    @Test
    public void testMove() {
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(5, 5);
        AnimalType2 animal = new AnimalType2(genes, initialEnergy, position);
        animal.move(new NormalWorld(10,10));
        assertNotEquals(position, animal.getPosition());
    }

    @Test
    public void testDailyUpdate() {
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);
        AnimalType2 animal = new AnimalType2(genes, initialEnergy, position);
        int initialEnergyBeforeUpdate = animal.getEnergy();
        animal.dailyUpdate(10);
        assertEquals(initialEnergyBeforeUpdate - 10, animal.getEnergy());
    }
}
