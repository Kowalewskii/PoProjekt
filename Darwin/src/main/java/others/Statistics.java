package others;
import java.util.*;
public class Statistics {
    private int animalNumber = 0;
    private int plantsNumber = 0;
    private int notOccupiedFields = 0;
    private double averageEnergy =0.0;
    private double averageFinalDate =0.0;
    private double averageDescendantsNumber =0.0;
    private int dayNumber =0;
    private List<List<Integer>> mostPopularGenes = new ArrayList<>();

    public void setStatistics(int animalNumber,int plantsNumber, int notOccupiedFields
            , double averageEnergy, double averageYear, double averageDescendantsNumber,List<List<Integer>> mostPopularGenes,int dayNumber) {
        this.animalNumber = animalNumber;
        this.plantsNumber = plantsNumber;
        this.notOccupiedFields = notOccupiedFields;
        this.averageEnergy = averageEnergy;
        this.averageFinalDate = averageYear;
        this.averageDescendantsNumber = averageDescendantsNumber;
        this.mostPopularGenes = mostPopularGenes;
        this.dayNumber = dayNumber;
    }
    //gettery:
    public int getAnimalNumber(){
        return animalNumber;
    }
    public int getPlantsNumber(){
        return plantsNumber;
    }

    public int getDayNumber() {
        return dayNumber;
    }
    public int getNotOccupiedFields(){
        return notOccupiedFields;
    }

    public double getAverageDescendantsNumber() {
        return averageDescendantsNumber;
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    public double getAverageFinalDate() {
        return averageFinalDate;
    }
    public List<List<Integer>> getMostPopularGenes() {
        return mostPopularGenes;
    }
}
