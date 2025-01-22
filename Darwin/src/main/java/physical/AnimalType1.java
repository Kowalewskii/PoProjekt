package physical;
import physical.abstractt.Animal;
import java.util.*;

public class AnimalType1 extends Animal {
    public AnimalType1(List<Integer> genes, int initialEnergy, Vector2d position) {
        super(genes, initialEnergy, position);
    }

    @Override
    public Animal descendant(Animal parent, int dEnergy, int minMutations, int maxMutations) {
        List<Integer> descendantGenes = this.descendantGenesGenerator(parent);

        AnimalType1 descendant = new AnimalType1(descendantGenes, dEnergy, position);
        this.addDescendant(descendant);
        AnimalType1 animal2 = (AnimalType1) parent;
        animal2.addDescendant(descendant);
        descendant.mutate(minMutations, maxMutations);
        return descendant;
    }
}