package physical;
import physical.abstractt.GameElement;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
public class Plant extends GameElement {

    public Plant(Vector2d position) {
        super(position);
    }
    @Override
    public Node getVisualization(Pane pane) {
        Pane plantPane = new Pane();
        plantPane.setMaxSize(pane.getWidth(), pane.getHeight());
        plantPane.setStyle("-fx-background-color: green");
        return plantPane;
    }
    private boolean isOnFire = false;

    public boolean isOnFire() {
        return isOnFire;
    }

    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }
}
