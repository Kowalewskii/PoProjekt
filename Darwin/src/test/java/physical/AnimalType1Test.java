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
        // Przygotowanie danych wejściowych
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);

        // Tworzenie obiektu AnimalType1
        AnimalType1 animal = new AnimalType1(genes, initialEnergy, position);

        // Sprawdzanie, czy obiekt został poprawnie zainicjalizowany
        assertNotNull(animal);
        assertEquals(genes, animal.getGenes());
        assertEquals(initialEnergy, animal.getEnergy());
        assertEquals(position, animal.getPosition());
    }

    @Test
    public void testDescendantGeneration() {
        // Przygotowanie danych wejściowych
        List<Integer> genes1 = Arrays.asList(0, 1, 2, 3, 4);
        List<Integer> genes2 = Arrays.asList(4, 3, 2, 1, 0);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);

        AnimalType1 parent1 = new AnimalType1(genes1, initialEnergy, position);
        AnimalType1 parent2 = new AnimalType1(genes2, initialEnergy, position);

        // Generowanie potomka
        AnimalType1 descendant = (AnimalType1) parent1.descendant(parent2, 50, 1, 2);

        // Sprawdzanie, czy potomek został poprawnie utworzony
        assertNotNull(descendant);
        assertEquals(50, descendant.getEnergy());
        assertEquals(genes1.size(), descendant.getGenes().size()); // Upewnienie się, że liczba genów potomka jest zgodna
    }
    @Test
    public void testWellBeing() {
        // Przygotowanie danych wejściowych
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);

        // Tworzenie obiektu AnimalType1
        AnimalType1 animal = new AnimalType1(genes, initialEnergy, position);

        // Testowanie, czy zwierzak jest zdrowy
        assertTrue(animal.wellBeing());

        // Zmniejszenie energii
        animal.dailyUpdate(101); // Energia mniejsza niż 0

        // Testowanie, czy zwierzak nie jest już zdrowy
        assertFalse(animal.wellBeing());
    }

    @Test
    public void testMove() {
        // Przygotowanie danych wejściowych
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(1, 1);

        // Tworzenie obiektu AnimalType1
        AnimalType1 animal = new AnimalType1(genes, initialEnergy, position);

        // Ruch zwierzaka w nową pozycję
        animal.move(new NormalWorld(10,10)); // Musisz zapewnić implementację klasy World, jeśli jej nie masz

        // Sprawdzanie, czy pozycja została zaktualizowana
        assertNotEquals(position, animal.getPosition());
    }

    @Test
    public void testDailyUpdate() {
        // Przygotowanie danych wejściowych
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);

        // Tworzenie obiektu AnimalType1
        AnimalType1 animal = new AnimalType1(genes, initialEnergy, position);

        // Sprawdzanie początkowej energii
        int initialEnergyBeforeUpdate = animal.getEnergy();

        // Wywołanie dziennej aktualizacji
        animal.dailyUpdate(10); // Zmniejszenie energii o 10

        // Sprawdzanie, czy energia została zmniejszona
        assertEquals(initialEnergyBeforeUpdate - 10, animal.getEnergy());
    }
}
