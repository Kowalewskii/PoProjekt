package physical;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;
import physical.abstractt.GameElement;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
public class Plant extends GameElement {

    public Plant(Vector2d position) {
        super(position);
    }
    @Override
    public Node getVisualization(Pane pane) {
        Ellipse ellipse = new Ellipse(pane.getWidth() / 2, pane.getHeight() / 3);
        ellipse.setCenterX(pane.getWidth() / 2);
        ellipse.setCenterY(pane.getHeight() / 2);

        ellipse.setFill(Color.GREEN);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(15);
        shadow.setOffsetX(10);
        shadow.setOffsetY(10);
        shadow.setColor(Color.rgb(18, 46, 20, 0.5));
        ellipse.setEffect(shadow);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), ellipse);
        scaleTransition.setFromX(1);
        scaleTransition.setToX(1.2);
        scaleTransition.setFromY(1);
        scaleTransition.setToY(1.2);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();

        return ellipse;
    }

    private boolean isOnFire = false;

    public boolean isOnFire() {
        return isOnFire;
    }

    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }
}
