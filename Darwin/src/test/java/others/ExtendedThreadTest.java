package others;

import mains.Simulation;
import records.WorldStats;
import visualization.GameVisualizator;
import physical.abstractt.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.TimeUnit;

class ExtendedThreadTest {

    private Simulation simulation;
    private ExtendedThread extendedThread;
    private WorldStats worldStats;

    @BeforeEach
    public void setUp() {
        // Tworzymy przykładowe wartości dla WorldStats
        worldStats = new WorldStats(
                10,  // mapHeight
                10,  // mapWidth
                5,   // startPlantNumber
                2,   // dailyPlantIncrease
                10,  // onePlantEnergy
                3,   // startAnimalNumber
                50,  // startAnimalEnergy
                5,   // animalSexEnergyLoss
                3,   // animalDailyEnergyLoss
                30,  // animalSexRequiredEnergy
                1,   // animalMinMutation
                5,   // animalMaxMutation
                10,  // animalGenesLength
                false,  // fireMap
                false,  // mutatedAnimal
                5,   // fireInterval
                10   // fireDuration
        );

        GameVisualizator presenter = new GameVisualizator();  // Zakładając, że ta klasa jest dobrze zaimplementowana
        simulation = new Simulation(worldStats, presenter);
        extendedThread = new ExtendedThread(simulation);
    }

    @Test
    public void testExtendedThreadCreation() {
        // Testujemy, czy ExtendedThread został poprawnie utworzony
        assertNotNull(extendedThread, "ExtendedThread should not be null.");
        assertEquals(simulation, extendedThread.simulation, "Simulation in the thread should match.");
    }

    @Test
    public void testRestartSimulation() {
        // Wywołujemy restart symulacji
        ExtendedThread newThread = extendedThread.restart();

        // Sprawdzamy, czy oryginalny wątek został zatrzymany, a nowy został uruchomiony
        assertNotNull(newThread, "New ExtendedThread should be created.");
        assertNotEquals(extendedThread, newThread, "The new thread should be different from the original thread.");
    }

    @Test
    public void testPauseSimulation() throws InterruptedException {
        // Pauzujemy symulację
        extendedThread.pauseSimulation();

        // Sprawdzamy, czy symulacja jest wstrzymana
        assertTrue(simulation.paused, "Simulation should be paused.");

        // Czekamy chwilę, aby upewnić się, że wątek został zatrzymany
        TimeUnit.MILLISECONDS.sleep(150);

        // Po pauzie symulacja powinna mieć wstrzymane działanie, więc sprawdzamy, czy dzień się nie zwiększył
        int currentDayCount = simulation.dayCount;
        TimeUnit.MILLISECONDS.sleep(150);  // Czekamy ponownie
        assertEquals(currentDayCount, simulation.dayCount, "Day count should not increase while simulation is paused.");
    }

    @Test
    public void testResumeSimulation() throws InterruptedException {
        // Pauzujemy symulację
        extendedThread.pauseSimulation();

        // Sprawdzamy, czy symulacja jest pauzowana
        assertTrue(simulation.paused, "Simulation should be paused.");

        // Wznawiamy symulację
        extendedThread.resumeSimulation();

        // Sprawdzamy, czy symulacja została wznowiona
        assertFalse(simulation.paused, "Simulation should be resumed.");

        // Sprawdzamy, czy dzień się zmienił po wznowieniu
        int previousDayCount = simulation.dayCount;
        TimeUnit.MILLISECONDS.sleep(300);  // Symulujemy upływ czasu
        assertTrue(simulation.dayCount > previousDayCount, "Day count should increase after simulation is resumed.");
    }

    @Test
    public void testStopSimulation() throws InterruptedException {
        // Tworzymy nowy wątek dla symulacji
        extendedThread.start();

        // Czekamy chwilę, aby symulacja mogła zacząć działać
        TimeUnit.MILLISECONDS.sleep(500);

        // Zatrzymujemy symulację
        extendedThread.simulation.stopRunning();

        // Sprawdzamy, czy symulacja została zatrzymana
        assertFalse(extendedThread.simulation.running, "Simulation should be stopped.");
    }
}
