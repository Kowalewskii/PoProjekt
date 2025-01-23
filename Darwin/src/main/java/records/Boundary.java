package records;
import physical.Vector2d;
public record Boundary(Vector2d bottomLeft, Vector2d topRight) {
    public boolean isInside(Vector2d position) {
        return position.getX() >= bottomLeft.getX() &&
                position.getX() <= topRight.getX() &&
                position.getY() >= bottomLeft.getY() &&
                position.getY() <= topRight.getY();
    }
}