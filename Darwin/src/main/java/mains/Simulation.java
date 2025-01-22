package mains;
import others.Statistics;
import records.*;
import enums.MoveDirection;
import physical.*;
import physical.abstractt.*;
import visualization.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation implements Runnable {

    private final World world;
    public final WorldStats worldStats;
    private boolean running = true;
    public final GameVisualizator presenter;
    private final Statistics statistics = new Statistics();
    private int dayCount = 0;
    private boolean paused = false;
    public Simulation(WorldStats worldStats, GameVisualizator presenter) {
        this.worldStats = worldStats;
        this.presenter = presenter;
        this.world = worldStats.fireMap() ?
                new FireWorld(worldStats.mapWidth(), worldStats.mapHeight(),worldStats.fireInterval(),worldStats.fireDuration()):
                new NormalWorld(worldStats.mapWidth(), worldStats.mapHeight());
        this.world.addObserver(presenter);

        List<Vector2d> randomAnimalPositions = this.generateRandomAnimalPositions();

        for (int i = 0; i < worldStats.startAnimalNumber(); i++) {
            List<Integer> genotype = this.generateRandomGenotype();
            Animal animal = worldStats.mutatedAnimal() ?
                    new AnimalType2(genotype, worldStats.startAnimalEnergy(), randomAnimalPositions.get(i)) :
                    new AnimalType1(genotype, worldStats.startAnimalEnergy(), randomAnimalPositions.get(i));

            world.placeAnimal(animal);
        }

        world.placeNPlants(worldStats.startPlantNumber());

        world.mapChanged(statistics);
    }

    public World getWorld() {
        return world;
    }

    private List<Vector2d> generateRandomAnimalPositions() {
        int maxX = worldStats.mapWidth();
        int maxY = worldStats.mapHeight();
        Random random = new Random();
        List<Vector2d> positions = new ArrayList<>();

        for (int i = 0; i < worldStats.startAnimalNumber(); i++) {
            positions.add(new Vector2d(random.nextInt(0, maxX), random.nextInt(0, maxY)));
        }

        return positions;
    }

    private List<Integer> generateRandomGenotype() {
        int maxGene = MoveDirection.values().length;
        Random random = new Random();
        List<Integer> genotype = new ArrayList<>();

        for (int j = 0; j < worldStats.animalGenesLength(); j++) {
            genotype.add(random.nextInt(0, maxGene));
        }

        return genotype;
    }

    @Override
    public void run() {
        while (running) {
            if (!paused) {
                dayCount++;
                world.removeDead(dayCount);
                world.moveAnimals();
                world.eatPlants(worldStats.onePlantEnergy());
                world.reproduction(worldStats.animalSexRequiredEnergy(), worldStats.animalSexEnergyLoss(), worldStats.animalMinMutation(), worldStats.animalMaxMutation());
                world.placeNPlants(worldStats.dailyPlantIncrease());
                world.updateStats(statistics, dayCount);

                try {
                    world.mapChanged(statistics);
                    Thread.sleep(300); // Simulation speed
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                } finally {
                    world.passedDay(worldStats.animalDailyEnergyLoss());
                }
            } else {
                // If paused, simply wait
                try {
                    Thread.sleep(100); // Reduce CPU usage while paused
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void stopRunning() {
        running = false;
    }

    public void pauseSimulation() {
        paused = true;
    }

    public void resumeSimulation() {
        paused = false;
    }
}

