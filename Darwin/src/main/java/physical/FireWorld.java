package physical;

import physical.abstractt.*;
import java.util.*;

public class FireWorld extends World {
    private int fireInterval;
    private int fireDuration;
    private int dayNumber =0;
    private List<Vector2d> firePositions; // Lista pozycji ognia
    private Map<Vector2d, Integer> fireTimers; // Mapa pozycji ognia z liczbą tur, przez które ogień tam trwa
    private Random random;

    public FireWorld(int width, int height, int fireInterval, int fireDuration) {
        super(width, height);
        this.fireInterval = fireInterval;
        this.fireDuration = fireDuration;
        this.firePositions = new ArrayList<>();
        this.fireTimers = new HashMap<>();
        this.random = new Random();
    }

    @Override
    public void passedDay(int dailyEnergyLoss) {
        for (physical.abstractt.Animal animal : this.getAllAnimals()) {
            animal.dailyUpdate(dailyEnergyLoss);
        }

        if ( dayNumber% fireInterval == 0) {
            startFire();
        }

        spreadFire();

        updateFireTimers();

        checkAnimalsInFire();
        dayNumber++;
    }

    private void startFire() {
        Vector2d startPos = getRandomPlantPosition();
        firePositions.add(startPos);
        fireTimers.put(startPos, fireDuration);
    }

    private Vector2d getRandomPlantPosition() {
        List<Vector2d> plantPositions = new ArrayList<>(positions1);
        plantPositions.addAll(positions2);
        return plantPositions.get(random.nextInt(plantPositions.size()));
    }

    private void spreadFire() {
        List<Vector2d> newFirePositions = new ArrayList<>();
        for (Vector2d firePos : firePositions) {
            int x = firePos.getX();
            int y = firePos.getY();

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (Math.abs(dx) + Math.abs(dy) == 1) {
                        int nx = x + dx;
                        int ny = y + dy;

                        Vector2d neighborPos = new Vector2d(nx, ny);
                        if (!firePositions.contains(neighborPos)) {
                            if (hasPlantAtPosition(neighborPos)) {
                                newFirePositions.add(neighborPos);
                            }
                        }
                    }
                }
            }
        }
        firePositions.addAll(newFirePositions);
    }
    private void updateFireTimers() {
        Iterator<Map.Entry<Vector2d, Integer>> iterator = fireTimers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Vector2d, Integer> entry = iterator.next();
            Vector2d position = entry.getKey();
            int remainingTime = entry.getValue();
            if (remainingTime <= 1) {
                removePlantAtPosition(position);
                iterator.remove();
                firePositions.remove(position);
            } else {
                fireTimers.put(position, remainingTime - 1);
            }
        }
    }

    private void removePlantAtPosition(Vector2d position) {
        if (positions1.contains(position)) {
            positions1.remove(position);
        } else if (positions2.contains(position)) {
            positions2.remove(position);
        }
    }
    private boolean hasPlantAtPosition(Vector2d position) {
        return positions1.contains(position) || positions2.contains(position);
    }
    private void checkAnimalsInFire() {
        List<Animal> animals = getAllAnimals();
        for (Animal animal : animals) {
            Vector2d animalPosition = animal.getPosition();
            if (firePositions.contains(animalPosition)) {
                animal.death(dayNumber);
            }
        }
    }
}
