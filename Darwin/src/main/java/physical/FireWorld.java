package physical;

import physical.abstractt.*;
import java.util.*;

public class FireWorld extends World {

    private int dayNumber;
    private List<Vector2d> firePositions1;
    private List<Vector2d> firePositions2;
    private Map<Vector2d, Integer> firePositions3;
    private Map<Vector2d, Integer> firePositions4;
    private int fireDuration;
    private Random random;

    public FireWorld(int width, int height, int fireInterval, int fireDuration) {
        super(width, height);
        this.random = new Random();
        this.dayNumber = 0;
        this.firePositions1 = new ArrayList<>();
        this.firePositions2 = new ArrayList<>();
        this.firePositions3 = new HashMap<>();
        this.firePositions4 = new HashMap<>();
        this.fireDuration = fireDuration;
        int lowerCenterBound = 2 * height / 5;
        int upperCenterBound = 3 * height / 5;

        for (int i = 0; i < width; i++){
            for (int j = 0; j < lowerCenterBound; j++){
                positions2.add(new Vector2d(i, j));
            }

            for (int j = lowerCenterBound; j < upperCenterBound; j++){
                positions1.add(new Vector2d(i, j));
            }

            for (int j = upperCenterBound; j < height; j++){
                positions2.add(new Vector2d(i, j));
            }
        }
    }

    public List<Vector2d> getFirePositions1() {
        return firePositions1;
    }
    public List<Vector2d> getFirePositions2() {
        return firePositions2;
    }
    public Map<Vector2d, Integer> getFirePositions3() {
        return firePositions3;
    }
    public Map<Vector2d, Integer> getFirePositions4() {
        return firePositions4;
    }
    public int getDayNumber() {
        return dayNumber;
    }
    @Override
    public void passedDay(int dailyEnergyLoss) {
        for (physical.abstractt.Animal animal : this.getAllAnimals()) {
            animal.dailyUpdate(dailyEnergyLoss);
        }
        dayNumber++;
    }
    @Override
    public void startFire() {
        Vector2d startPos = this.getRandomPlantPosition();
        this.firePositions3.put(startPos,fireDuration);
        List<Vector2d> neighbours = getPlantNeighbours(startPos);
        for (Vector2d neighbour : neighbours) {
            this.firePositions3.put(neighbour, fireDuration);
        }
        for (Map.Entry<Vector2d, Integer> entry : firePositions3.entrySet()) {
            Vector2d position = entry.getKey();
            List<Animal> allAnimals = this.getAllAnimals();
            for (Animal animal : allAnimals) {
                if (animal.getPosition().equals(position)) {
                    animal.death(dayNumber);
                }
            }

        }

    }
    @Override
    public void endFire() {
        for (Map.Entry<Vector2d, Integer> entry : firePositions3.entrySet()) {
            Vector2d position = entry.getKey();
            Integer fireDuration2 = entry.getValue();
            fireDuration2--;
            if (fireDuration2 == 0) {
                firePositions2.add(position);
            }
            firePositions3.put(position, fireDuration2);

        }
        firePositions3.clear();
    }
    @Override
   public void eatPlants(int plantEnergy) {
       for (List<physical.abstractt.Animal> allAnimals : animals.values()) {
           Vector2d currentPos = allAnimals.getFirst().getPosition();
           if (plants.containsKey(currentPos)) {
               physical.abstractt.Animal eater = this.dominatingAnimals(allAnimals, 1).get(0);
               eater.eat(plantEnergy);
               this.removeEaten(plants.get(currentPos));
           }
       }
       for (Vector2d position: this.firePositions2) {
           Animal eater = new AnimalType1(Arrays.asList(1, 2, 3, 4,3,7),2,position);
           if (plants.containsKey(position)) {
               eater.eat(0);
               this.removeEaten(plants.get(position));
           }//costam
       }
       firePositions2.clear();
   }
    @Override
    public void removeEaten(Plant eatenPlant){
        super.removeEaten(eatenPlant);
        Vector2d availablePosition = eatenPlant.getPosition();

        if (this.isInJungle(availablePosition)){
            positions1.add(availablePosition);
        }else{
            positions2.add(availablePosition);
        }
    }

    boolean isInJungle(Vector2d position) {
        int height = boundary.topRight().getY();
        int lowerJungleBoundary = 2 * height / 5;
        int upperJungleBoundary = 3 * height / 5;

        return lowerJungleBoundary <= position.getY() && position.getY() < upperJungleBoundary;
    }
}


