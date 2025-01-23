package physical;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import physical.*;
import physical.abstractt.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class FireWorldTest {
    private FireWorld fireWorld;
    @BeforeEach
    void setUp() {
        fireWorld = new FireWorld(10, 10, 5, 3);
        fireWorld.placeNPlants(5);
    }
    @Test
    void testStartFire() {
        assertTrue(fireWorld.getFirePositions3().isEmpty());
        fireWorld.startFire();
        assertFalse(fireWorld.getFirePositions3().isEmpty());
    }
    @Test
    void testEatPlantsInFireArea() {
        fireWorld.startFire();
        fireWorld.endFire();
        fireWorld.eatPlants(10);
        for (Vector2d position : fireWorld.getFirePositions2()) {
            assertFalse(fireWorld.getPlants().containsKey(position));
        }
    }
    @Test
    public void testRemoveEatenPlant() {
        fireWorld.placeNPlants(3);
        Vector2d plantPosition = fireWorld.getRandomPlantPosition();
        assertTrue(fireWorld.getPlants().containsKey(plantPosition));
        Plant plant = fireWorld.getPlants().get(plantPosition);
        fireWorld.removeEaten(plant);
        assertFalse(fireWorld.getPlants().containsKey(plantPosition));
    }

    @Test
    public void testFireEffectOnAnimals() {
        Animal animal1 = new AnimalType1(Arrays.asList(1, 2, 3, 4, 3, 7), 2, new Vector2d(3, 3));
        fireWorld.placeAnimal(animal1);

        assertTrue(animal1.getEnergy() > 0);
        fireWorld.startFire();
        if (fireWorld.getFirePositions3().containsKey(animal1.getPosition())) {
            assertTrue(animal1.getEnergy() <= 0);
        }
    }

    @Test
    public void testFireDuration() {
        fireWorld.startFire();
        Map<Vector2d, Integer> firePositionsBeforeEnd = new HashMap<>(fireWorld.getFirePositions3());
        fireWorld.endFire();
        for (Map.Entry<Vector2d, Integer> entry : firePositionsBeforeEnd.entrySet()) {
            Vector2d position = entry.getKey();
            int previousDuration = entry.getValue();
            assertEquals((int) fireWorld.getFirePositions3().get(position), previousDuration - 1);
        }
    }
}
