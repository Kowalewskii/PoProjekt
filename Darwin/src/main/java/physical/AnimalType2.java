package physical;
import enums.MoveDirection;
import physical.abstractt.Animal;
import java.util.List;
import java.util.Random;

public class AnimalType2 extends Animal {

    public AnimalType2(List<Integer> genes, int startEnergy, Vector2d position) {
        super(genes, startEnergy, position);
    }

    @Override
    public void mutate(int minMutations, int maxMutations) {
        Random random = new Random();
        int mutations = random.nextInt(minMutations, maxMutations + 1);
        List<Integer> mutatedIndexes = this.randomIDsToMutate(mutations);

        for (int ID : mutatedIndexes) {
            int currentGene = this.genes.get(ID);
            int direction = random.nextBoolean() ? 1 : -1;
            int newGene = (currentGene + direction + MoveDirection.values().length) % MoveDirection.values().length;
            this.genes.set(ID, newGene);
        }
    }

    @Override
    public Animal descendant(Animal parent, int dEnergy, int minMutations, int maxMutations) {
        List<Integer> descendantGenes = descendantGenesGenerator(parent);
        AnimalType2 child = new AnimalType2(descendantGenes, dEnergy, this.position);
        child.mutate(minMutations, maxMutations);
        this.addDescendant(child);
        parent.addDescendant(child);
        return child;
    }
}
