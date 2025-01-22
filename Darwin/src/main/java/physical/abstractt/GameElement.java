package physical.abstractt;
import physical.Vector2d;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import java.util.UUID;

abstract public class GameElement {

    protected final UUID id = UUID.randomUUID();

    protected Vector2d position;

    public GameElement(Vector2d position) {
        this.position = position;
    }
    abstract public Node getVisualization(Pane pane);
    public UUID getId() {
        return id;
    }

    public Vector2d getPosition() {
        return position;
    }
}