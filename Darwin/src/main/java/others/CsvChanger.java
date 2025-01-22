package others;

import enums.*;
import physical.abstractt.*;


import java.io.PrintWriter;
import java.util.UUID;

public class CsvChanger {

    public static void setStatsTop(PrintWriter writer) {
        writer.println("WorldID,Day number,Date,All animals,All plants,All free fields,Most popular genotypes,Average animals energy,Average animals age,Average animals children,AnimalID,Genotype,Energy,Eaten plants,Children,Descendants,Age,Date of death");
    }

    public static void fillStatisticsDay(PrintWriter writer, UUID worldId, Statistics statistics, Animal animal) {
        writer.println(worldId + "," +
                // General statistics
                statistics.getDayNumber() + "," +
                GameStatic.convertDaysToDate(statistics.getDayNumber()) + "," +
                statistics.getAnimalNumber() + "," +
                statistics.getPlantsNumber() + "," +
                statistics.getNotOccupiedFields() + "," +
                (!statistics.getMostPopularGenes().isEmpty() ? GameStatic.toStringGenes(statistics.getMostPopularGenes().get(0)) : GameLabelText.NO_GENOTYPE.toString()) + " " +
                (statistics.getMostPopularGenes().size() > 1 ? GameStatic.toStringGenes(statistics.getMostPopularGenes().get(1)) : GameLabelText.NO_GENOTYPE.toString()) + " " +
                (statistics.getMostPopularGenes().size() > 2 ? GameStatic.toStringGenes(statistics.getMostPopularGenes().get(2)) : GameLabelText.NO_GENOTYPE.toString()) + "," +
                GameStatic.convertNumberToString(statistics.getAverageEnergy()) + "," +
                GameStatic.convertDaysToTime(statistics.getAverageFinalDate()) + "," +
                GameStatic.convertNumberToString(statistics.getAverageDescendantsNumber()) + "," +

                // Animal statistics
                (animal != null ? animal.getId() : GameLabelText.NO_SELECTED_ANIMAL.toString()) + "," +
                (animal != null ? GameStatic.toStringIDGenes(animal.getGenes(), animal.getGenesID()) : GameLabelText.NO_SELECTED_ANIMAL.toString()) + "," +
                (animal != null ? animal.getEnergy() : GameLabelText.NO_SELECTED_ANIMAL.toString()) + "," +
                (animal != null ? animal.getConsumedPlants() : GameLabelText.NO_SELECTED_ANIMAL.toString()) + "," +
                (animal != null ? animal.getDescendants1() : GameLabelText.NO_SELECTED_ANIMAL.toString()) + "," +
                (animal != null ? animal.getDescendants2() : GameLabelText.NO_SELECTED_ANIMAL.toString()) + "," +
                (animal != null ? GameStatic.convertDaysToTime(animal.getAge()) : GameLabelText.NO_SELECTED_ANIMAL.toString()) + "," +
                (animal != null ? GameStatic.convertDaysToDate(animal.getFinalDay()) : GameLabelText.NO_SELECTED_ANIMAL.toString())
        );
    }

    public static void setTopConfig(PrintWriter writer) {
        writer.println("Property,Value");
    }
}
