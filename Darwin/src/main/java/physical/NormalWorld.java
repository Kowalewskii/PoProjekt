package physical;
import physical.abstractt.World;

public class NormalWorld extends World {
    public NormalWorld(int width, int height){
        super(width, height);

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
