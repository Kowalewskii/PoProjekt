package physical.abstractt;
import physical.Vector2d;
import physical.Plant;
import observers.MapChangeListener;
import records.Boundary;
import others.Statistics;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

abstract public class World {
    protected final UUID id = UUID.randomUUID();

    protected final Boundary boundary;

    protected long deadAnimals = 0;

    protected final List<MapChangeListener> observers = new ArrayList<>();

    protected int deadAnimalsYears = 0;

    protected final Map<Vector2d, Plant> plants = new ConcurrentHashMap<>();

    protected final Map<Vector2d, List<physical.abstractt.Animal>> animals = new ConcurrentHashMap<>();

    protected final Set<Vector2d> positions2 = new HashSet<>();

    protected final Set<Vector2d> positions1 = new HashSet<>();


    public World(int width, int height) {
        boundary = new Boundary(new Vector2d(0, 0), new Vector2d(width, height));
    }

    //gettery:
    public UUID getId() {
        return id;
    }

    public Set<Vector2d> getPositions1() {
        return positions1;
    }

    public Set<Vector2d> getPositions2() {
        return positions2;
    }

    public Map<Vector2d, List<physical.abstractt.Animal>> getAnimals() {
        return animals;
    }

    public Map<Vector2d, Plant> getPlants() {
        return plants;
    }

    public int getDeadAnimalsYears() {
        return deadAnimalsYears;
    }

    public long getDeadAnimals() {
        return deadAnimals;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public List<physical.abstractt.Animal> getAllAnimals() {
        List<physical.abstractt.Animal> allAnimals = new ArrayList<>();
        for (List<physical.abstractt.Animal> animals1 : animals.values()) {
            allAnimals.addAll(animals1);
        }
        return allAnimals;
    }

    public physical.abstractt.Animal getAnimalID(UUID id) {
        List<physical.abstractt.Animal> allAnimals = getAllAnimals();

        for (physical.abstractt.Animal animal : allAnimals) {
            if (animal.getId().equals(id)) {
                return animal;
            }
        }
        return null;
    }

    public int getNumberofAnimals() {
        return this.getAllAnimals().size();
    }

    public int getNumberofPlants() {
        return plants.size();
    }

    protected int getNotOccupiedFields() {
        Set<Vector2d> objectPositions = new HashSet<>(animals.keySet());
        objectPositions.addAll(plants.keySet());
        int width = boundary.topRight().getX();
        int height = boundary.topRight().getY();
        return width * height - objectPositions.size();
    }

    public double getAverageEnergy() {
        List<physical.abstractt.Animal> animalsList = this.getAllAnimals();
        if (animalsList.isEmpty()) {
            return 0.0;
        }
        int totalEnergy = 0;
        for (physical.abstractt.Animal animal : animalsList) {
            totalEnergy += animal.energy;
        }
        return (double) totalEnergy / animalsList.size();
    }

    protected double getAverageDeathYear() {
        if (deadAnimals == 0) {
            return 0;
        }
        return (double) deadAnimalsYears / deadAnimals;
    }

    public double getAverageDescendantsNumber() {
        List<physical.abstractt.Animal> animalsList = this.getAllAnimals();

        if (animalsList.isEmpty()) {
            return 0.0;
        }
        int totalDescendants = 0;
        for (physical.abstractt.Animal animal : animalsList) {
            totalDescendants += animal.descendants1.size();
        }
        return (double) totalDescendants /animals.size();

    }

    public void updateStats(Statistics stats, int newDay) {
        stats.setStatistics(
                this.getNumberofAnimals(),
                this.getNumberofPlants(),
                this.getNotOccupiedFields(),
                this.getAverageEnergy(),
                this.getAverageDeathYear(),
                this.getAverageDescendantsNumber(),
                this.getMostPopularGenes(),
                newDay);
    }

    public void addObserver(MapChangeListener observer) {
        this.observers.add(observer);
    }

    public void mapChanged(Statistics stats) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, stats);
        }
    }

    public void placeAnimal(physical.abstractt.Animal animal) {
        List<physical.abstractt.Animal> currentAnimalList = animals.get(animal.getPosition());
        if (currentAnimalList == null) {
            currentAnimalList = new ArrayList<>();
        }
        currentAnimalList.add(animal);
        animals.put(animal.getPosition(), currentAnimalList);
    }

    public void removeDead(int currentDay) {
        List<physical.abstractt.Animal> allAnimals = this.getAllAnimals();
        for (physical.abstractt.Animal animal : allAnimals) {
            if (!animal.wellBeing()) {
                animal.finalDay = currentDay;
                deadAnimalsYears += animal.age;
                deadAnimals++;

                Vector2d position = animal.getPosition();
                List<physical.abstractt.Animal> animalsPostions = animals.get(position);

                if (animalsPostions != null) {
                    animalsPostions.remove(animal);
                }
                if (animalsPostions.isEmpty()) {
                    animals.remove(position);
                }
            }
            // Usunięcie martwego zwierzęcia z potomków jego rodziców
            for (physical.abstractt.Animal potentialParent : allAnimals) {
                potentialParent.getDescendants().remove(animal);


            }
        }
    }

    public void moveAnimals() {
        List<physical.abstractt.Animal> movingAnimals = new ArrayList<>();

        for (List<physical.abstractt.Animal> allAnimals : animals.values()) {
            Vector2d currentPos = allAnimals.get(0).getPosition();

            for (physical.abstractt.Animal animal : allAnimals) {
                animal.move(this);
                movingAnimals.add(animal);
            }
            animals.remove(currentPos);
        }
        for (physical.abstractt.Animal movingAnimal : movingAnimals) {
            placeAnimal(movingAnimal);
        }
    }

    public void passedDay(int dailyEnergyLoss) {
        for (physical.abstractt.Animal animal : this.getAllAnimals()) {
            animal.dailyUpdate(dailyEnergyLoss);
        }
    }

   protected List<Vector2d> newPlantSpots(int plantCtr) {
        List<Vector2d> nPlantSpots = new ArrayList<>();
        double paretoProb = 0.8;
        Random random = new Random();
        for (int i = 0; i < plantCtr; i++) {
            double propab = random.nextDouble();

            if ((propab < paretoProb && !positions1.isEmpty()) || positions2.isEmpty()) {
                Vector2d plantPosition = null;

                int randomIndex = random.nextInt(0, positions1.size());
                int currentIndex = 0;

                for (Vector2d position : positions1) {
                    if (currentIndex == randomIndex) {
                        plantPosition = position;
                        break;
                    }
                    currentIndex++;
                }
                positions1.remove(plantPosition);
                nPlantSpots.add(plantPosition);
            } else if (!positions2.isEmpty()) {
                Vector2d plantPosition = null;

                int randomIndex = random.nextInt(0, positions2.size());
                int currentIndex = 0;

                for (Vector2d position : positions2) {
                    if (currentIndex == randomIndex) {
                        plantPosition = position;
                        break;
                    }
                    currentIndex++;
                }
                positions2.remove(plantPosition);
                nPlantSpots.add(plantPosition);
            }
        }
        for (Vector2d position : nPlantSpots) {
            plants.put(position, new Plant(position));
        }
        return nPlantSpots;
    }

    public void placeNPlants(int plantNumber) {
        this.newPlantSpots(plantNumber);
    }

    public List<physical.abstractt.Animal> dominatingAnimals(List<physical.abstractt.Animal> allAnimalsConsidered, int howMany) {
        List<physical.abstractt.Animal> sortedAnimals = new ArrayList<>(allAnimalsConsidered);
        sortedAnimals.sort((a, b) -> {
            int energyComparison = Integer.compare(b.getEnergy(), a.getEnergy());
            if (energyComparison != 0) return energyComparison;

            int ageComparison = Integer.compare(b.getAge(), a.getAge());
            if (ageComparison != 0) return ageComparison;

            int childrenComparison = Integer.compare(b.getDescendants1(), a.getDescendants1());
            if (childrenComparison != 0) return childrenComparison;

            return b.getId().compareTo(a.getId());
        });


        List<physical.abstractt.Animal> result = new ArrayList<>();
        for (int i = 0; i < howMany && i < sortedAnimals.size(); i++) {
            result.add(sortedAnimals.get(i));
        }
        return result;
    }


    public void removeEaten(Plant eaten) {
        Vector2d position1 = eaten.getPosition();
        plants.remove(position1);
    }

    public void eatPlants(int plantEnergy) {
        for (List<physical.abstractt.Animal> allAnimals : animals.values()) {
            Vector2d currentPos = allAnimals.get(0).getPosition();
            if (plants.containsKey(currentPos)) {
                physical.abstractt.Animal eater = this.dominatingAnimals(allAnimals, 1).get(0);
                eater.eat(plantEnergy);
                this.removeEaten(plants.get(currentPos));

            }

        }
    }

    public void reproduction(int sexEnergy, int energyLoss, int minMutations, int maxMutations) {
        for (List<physical.abstractt.Animal> allAnimals : animals.values()) {
            if (allAnimals.size() > 1) {
                List<physical.abstractt.Animal> animalsBreeding = this.dominatingAnimals(allAnimals, 2);
                physical.abstractt.Animal dominant = animalsBreeding.get(0);
                physical.abstractt.Animal other = animalsBreeding.get(1);

                dominant.sex(this, other, sexEnergy, energyLoss, minMutations, maxMutations);

            }
        }
    }
    public List<List<Integer>> getMostPopularGenes() {
        List<Animal> allAnimals = this.getAllAnimals();
        Map<List<Integer>, Integer> genesPopularity = new HashMap<>();


        for (Animal animal : allAnimals) {
            List<Integer> genotype = animal.genes;
            if (genesPopularity.containsKey(genotype)) {
                genesPopularity.put(genotype, genesPopularity.get(genotype) + 1);
            } else {
                genesPopularity.put(genotype, 1);
            }
        }


        List<Map.Entry<List<Integer>, Integer>> sortedGenes = new ArrayList<>(genesPopularity.entrySet());
        sortedGenes.sort((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));


        List<List<Integer>> mostPopularGenes = new ArrayList<>();
        for (int i = 0; i < Math.min(3, sortedGenes.size()); i++) {
            mostPopularGenes.add(sortedGenes.get(i).getKey());
        }

        return mostPopularGenes;
    }
}
