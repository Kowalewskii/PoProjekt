package physical.abstractt;
import physical.*;
import physical.abstractt.Animal;
import physical.AnimalType1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import enums.MoveDirection;

public class AnimalTest {
    private Animal animal;
    private Animal partner;
    private World world;
    private Vector2d position;

    @BeforeEach
    public void setUp() {
        position = new Vector2d(5, 5);
        List<Integer> genes = Arrays.asList(1, 2, 3, 4,3,7);
        animal = new AnimalType1(genes, 100, position);
        partner = new AnimalType1(genes, 80, position);
        world = new NormalWorld(10,10);
    }

    @Test
    public void testEat() {
        int initialEnergy = animal.getEnergy();
        animal.eat(10);
        assertEquals(initialEnergy + 10, animal.getEnergy(), "Animal should gain energy after eating.");
        assertEquals(1, animal.getConsumedPlants(), "Consumed plants count should increase.");
    }

    @Test
    public void testMove() {
        Vector2d initialPosition = animal.getPosition();
        animal.move(world);
        assertNotEquals(initialPosition, animal.getPosition(), "Animal should move.");
    }

    @Test
    public void testWellBeing() {
        assertTrue(animal.wellBeing(), "Animal should be alive if energy is positive.");
        animal.dailyUpdate(100);
        assertFalse(animal.wellBeing(), "Animal should be dead if energy is negative.");
    }

    @Test
    public void testSex() {
        int initialEnergy = animal.getEnergy();
        animal.sex(world, partner, 50, 10, 1, 3);
        assertTrue(animal.getEnergy() < initialEnergy, "Energy should decrease after mating.");
        assertEquals(1, animal.getDescendants1(), "Animal should have 1 descendant after mating.");
    }

    @Test
    public void testDescendantGenesGenerator() {
        List<Integer> descendantGenes = animal.descendantGenesGenerator(partner);
        assertEquals(animal.getGenes().size(), descendantGenes.size(), "Descendant should have the correct number of genes.");
        assertTrue(descendantGenes.containsAll(animal.getGenes()) && descendantGenes.containsAll(partner.getGenes()), "Descendant genes should be from both parents.");
    }


    @Test
    public void testDescendant() {
        AnimalType1 descendant = (AnimalType1) animal.descendant(partner, 20, 1, 3);
        assertNotNull(descendant, "Descendant should be created after mating.");
        assertEquals(1, animal.getDescendants1(), "Animal should have one descendant.");
    }

    @Test
    public void testDailyUpdate() {
        int initialEnergy = animal.getEnergy();
        int initialAge = animal.getAge();
        animal.dailyUpdate(10);
        assertEquals(initialAge + 1, animal.getAge(), "Age should increase by 1.");
        assertEquals(initialEnergy - 10, animal.getEnergy(), "Energy should decrease by 10.");
    }

    @Test
    public void testDeath() {
        animal.dailyUpdate(110);
        animal.death(10);
        assertEquals(10, animal.getFinalDay(), "Final day of death should be recorded.");
    }

    @Test
    public void testRedirectMovement() {
        MoveDirection initialDirection = animal.moveDirection;
        animal.move(world);
        assertNotEquals(initialDirection, animal.moveDirection, "Move direction should be updated after movement.");
    }
}
