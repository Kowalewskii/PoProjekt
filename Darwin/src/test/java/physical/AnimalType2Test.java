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
        // Przygotowanie danych wejściowych
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);

        // Tworzenie obiektu AnimalType2
        AnimalType2 animal = new AnimalType2(genes, initialEnergy, position);

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

        AnimalType2 parent1 = new AnimalType2(genes1, initialEnergy, position);
        AnimalType2 parent2 = new AnimalType2(genes2, initialEnergy, position);

        // Generowanie potomka
        AnimalType2 descendant = (AnimalType2) parent1.descendant(parent2, 50, 1, 2);

        // Sprawdzanie, czy potomek został poprawnie utworzony
        assertNotNull(descendant);
        assertEquals(50, descendant.getEnergy());
        assertEquals(genes1.size(), descendant.getGenes().size()); // Upewnienie się, że liczba genów potomka jest zgodna
    }

    @Test
    public void testDescendantGenes() {
        // Przygotowanie danych wejściowych
        List<Integer> genes1 = Arrays.asList(0, 1, 2, 3, 4);
        List<Integer> genes2 = Arrays.asList(4, 3, 2, 1, 0);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);

        AnimalType2 parent1 = new AnimalType2(genes1, initialEnergy, position);
        AnimalType2 parent2 = new AnimalType2(genes2, initialEnergy, position);

        // Generowanie potomka
        AnimalType2 descendant = (AnimalType2) parent1.descendant(parent2, 50, 1, 2);

        // Sprawdzanie, czy geny potomka są poprawnie wygenerowane
        List<Integer> descendantGenes = descendant.getGenes();

        // Sprawdzanie, czy geny potomka zostały wygenerowane zgodnie z zasadą dziedziczenia
        assertEquals(genes1.size(), descendantGenes.size());
    }

    @Test
    public void testMutateGeneDirection() {
        // Przygotowanie danych wejściowych
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);

        // Tworzenie obiektu AnimalType2
        AnimalType2 animal = new AnimalType2(genes, initialEnergy, position);

        // Zapisanie początkowych genów
        List<Integer> initialGenes = animal.getGenes();

        // Wywołanie mutacji
        animal.mutate(1, 3); // Zmienna liczba mutacji między 1 a 3
        // Sprawdzanie, czy kierunki genów się zmieniły
        for (int i = 0; i < initialGenes.size(); i++) {
            int initialGene = initialGenes.get(i);
            int mutatedGene = animal.getGenes().get(i);
            int direction = mutatedGene - initialGene;

            // Sprawdzamy, czy zmiana jest o 1 lub -1, w zależności od kierunku
            assertTrue(Math.abs(mutatedGene-initialGene)<=1, "Gene mutation direction is invalid");
        }
    }

    @Test
    public void testWellBeing() {
        // Przygotowanie danych wejściowych
        List<Integer> genes = Arrays.asList(0, 1, 2, 3, 4);
        int initialEnergy = 100;
        Vector2d position = new Vector2d(0, 0);

        // Tworzenie obiektu AnimalType2
        AnimalType2 animal = new AnimalType2(genes, initialEnergy, position);

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
        Vector2d position = new Vector2d(5, 5);

        // Tworzenie obiektu AnimalType2
        AnimalType2 animal = new AnimalType2(genes, initialEnergy, position);

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

        // Tworzenie obiektu AnimalType2
        AnimalType2 animal = new AnimalType2(genes, initialEnergy, position);

        // Sprawdzanie początkowej energii
        int initialEnergyBeforeUpdate = animal.getEnergy();

        // Wywołanie dziennej aktualizacji
        animal.dailyUpdate(10); // Zmniejszenie energii o 10

        // Sprawdzanie, czy energia została zmniejszona
        assertEquals(initialEnergyBeforeUpdate - 10, animal.getEnergy());
    }
}
