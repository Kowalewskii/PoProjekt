package physical.abstractt;
import physical.Vector2d;
import enums.MoveDirection;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract public class Animal extends GameElement {
    public final List<Integer> genes;
    protected List<Animal> descendants1 = new ArrayList<>();
    public MoveDirection moveDirection;

    protected int energy;

    protected int age = 1;

    protected int genesID;
    protected int childrenCount =0;
    protected int consumedPlants = 0;

    public int finalDay;

    public Animal(List<Integer> genes,int startEnergy, Vector2d position){
        super(position); //aby abstrakcja sie zgadzala
        Random random = new Random();
        this.genes=genes;
        genesID = random.nextInt(0,genes.size());
        this.moveDirection = MoveDirection.values()[random.nextInt(0,MoveDirection.values().length)];
        energy = startEnergy;
    }
    //geterry:
    public void setMoveDirection(int x){
        moveDirection = MoveDirection.values()[x];
    }
    public int getAge(){
        return age;
    }
    public int getDescendants1(){
        return descendants1.size();
    }
    public int getEnergy(){
        return energy;
    }
    public int getConsumedPlants(){
        return consumedPlants;
    }
    public int getGenesID(){
        return genesID;
    }
    public List<Integer> getGenes() {
        return genes;
    }
    public int getFinalDay(){
        return finalDay;
    }
    public Node getVisualization(Pane pane) {
        return new Circle(pane.getWidth()/ 2.5);
    }

    //inne metody:
    //czy zwierzak zyje?
    public boolean wellBeing(){
        return energy>0;
    }
    //jedzenie
    public void eat(int energy){
        consumedPlants+=1;
        this.energy +=energy;
    }
    //update numeru genu:
    protected void changeGenesID(int current){
        genesID = (current + 1) % genes.size();
    }
    // poruszenie sie
    public void move(World world){
        moveDirection =moveDirection.Redirect(genes.get(genesID));
        this.changeGenesID(genesID);
        Vector2d nextPosition = position.move(moveDirection.movement());
        int width = world.getBoundary().topRight().getX();
        int height = world.getBoundary().topRight().getY();

        if(nextPosition.getY() == -1 || nextPosition.getY()==height){
            moveDirection = moveDirection.Redirect(MoveDirection.values().length/2);
        }
        else{
            position = nextPosition;
            if(position.getX() == -1){
                position = new Vector2d(width -1,position.getY());

            }else if(position.getX()==width){
                position = new Vector2d(0,position.getY());

            }
        }
    }
    protected void eating(int energy){
        consumedPlants++;
        this.energy+=energy;
    }
    public void addDescendant(Animal descendant){
        descendants1.add(descendant);
    }
    abstract protected Animal descendant (Animal parent, int dEnergy,int minMutations,int maxMutations);
    public void sex(World world,Animal partner, int sexEnergy, int energyLoss,int minMutations,int maxMutations){
        if(Math.min(energy,partner.energy)< sexEnergy){
            return;
        }
        Animal descendant = this.descendant(partner,2* energyLoss,minMutations,maxMutations);
        world.placeAnimal(descendant);
        this.energy -=energyLoss;
        partner.energy-=energyLoss;
    }
    public List<Integer> descendantGenesGenerator(Animal partner){
        double dominantParent = (double) energy/(energy+partner.energy);
        Random random = new Random();
        int dominantSide = random.nextInt(2);
        List<Integer> descendantGenes = new ArrayList<>();

        if(dominantSide ==0 ){
            int selector = (int) Math.round(genes.size()*dominantParent);

            for(int i=0;i<selector;i++){
                descendantGenes.add(genes.get(i));
            }
            for(int i = selector;i<genes.size();i++){
                descendantGenes.add(partner.genes.get(i));

            }
        }else{
            int selector = (int) Math.round(genes.size()*(1-dominantParent));
            for(int i =0;i<selector;i++){
                descendantGenes.add(partner.genes.get(i));
            }
            for(int i =selector;i<genes.size();i++){
                descendantGenes.add(genes.get(i));
            }
        }
        return descendantGenes;
    }
    protected List<Integer> randomIDsToMutate(int mutations) {
        if (genes.isEmpty()) {
            return new ArrayList<>();  // Jeśli lista genów jest pusta, zwróć pustą listę
        }

        List<Integer> allIDs = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < genes.size(); i++) {
            allIDs.add(i);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < mutations; i++) {
            if (allIDs.isEmpty()) break;  // Zapobiega błędowi, jeśli wszystkie ID zostały już wybrane
            int randomIDx = random.nextInt(allIDs.size());
            result.add(allIDs.get(randomIDx));
            allIDs.remove(randomIDx);
        }
        return result;
    }

    public void mutate(int minMutations, int maxMutations) {
        if (genes.isEmpty()) {
            return;
        }

        Random random = new Random();
        int mutations = random.nextInt(minMutations, maxMutations + 1);
        List<Integer> mutatedIndexes = this.randomIDsToMutate(mutations);

        for (int ID : mutatedIndexes) {
            this.genes.set(ID, random.nextInt(0, MoveDirection.values().length));
        }
    }

    public int getDescendants2(){
        return getDescendants().size();
    }
    public List<Animal> getDescendants() {
        List<Animal> descendants = new ArrayList<>(descendants1);

        for (Animal child : descendants1) {
            List<Animal> childDescendants = child.getDescendants();
            for (Animal descendant : childDescendants) {
                if (!descendants.contains(descendant)) {
                    descendants.add(descendant);
                }
            }
        }

        return descendants;
    }

     public void dailyUpdate(int dailyEnergyLoss){
        age++;
        energy-=dailyEnergyLoss;
    }
    public void death(int currentDay){
        this.finalDay = currentDay;
    }
}
