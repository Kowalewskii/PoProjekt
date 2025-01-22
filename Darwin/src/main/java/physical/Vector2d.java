package physical;
import java.util.Objects;
public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y){
        this.x=x;
        this.y=y;
    }
    //gettery
    public int getY(){
        return y;
    }
    public int getX(){
        return x;
    }
    //zamiana na napis
    public String toString(){
        return "(" + x + "," + y + ")";
    }
    //czy jeden rowna sie drugiemu:
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector2d)) {
            return false;
        }
        return getX() == ((Vector2d) other).getX() && getY() == ((Vector2d) other).getY();
    }
    //hashcode
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    //ruch wektorowy, zmiana wartosci wektora o inny
    public Vector2d move(Vector2d mover){
        return new Vector2d(x + mover.getX(), y + mover.getY());
    }
}
