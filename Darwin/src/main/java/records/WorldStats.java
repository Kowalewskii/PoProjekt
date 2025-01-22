package records;

public record WorldStats(int mapHeight,
                         int mapWidth,
                         int startPlantNumber,
                         int dailyPlantIncrease,
                         int onePlantEnergy,
                         int startAnimalNumber,
                         int startAnimalEnergy,
                         int animalSexEnergyLoss,
                         int animalDailyEnergyLoss,
                         int animalSexRequiredEnergy,
                         int animalMinMutation,
                         int animalMaxMutation,
                         int animalGenesLength,
                         boolean fireMap,
                         boolean mutatedAnimal,
                         int fireInterval,
                         int fireDuration) {
}