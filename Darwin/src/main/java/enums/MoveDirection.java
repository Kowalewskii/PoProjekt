package enums;
import physical.Vector2d;
public enum MoveDirection {
    N(0),
    NE(1),
    E(2),
    SE(3),
    S(4),
    SW(5),
    W(6),
    NW(7);

    private final  int movementvalue;
    MoveDirection(int movementvalue) {
        this.movementvalue = movementvalue;
    }

    public Vector2d movement(){
        return switch(this.movementvalue){
            case 0 -> new Vector2d(0,1);
            case 1 -> new Vector2d(1,1);
            case 2 -> new Vector2d(1,0);
            case 3 -> new Vector2d(1,-1);
            case 4 -> new Vector2d(0,-1);
            case 5 -> new Vector2d(-1,-1);
            case 6 -> new Vector2d(-1,0);
            case 7 -> new Vector2d(-1,1);
            default -> throw new IllegalStateException();
        };
    }
    public MoveDirection Redirect(int genes){
        int redirectedValue = (movementvalue + genes) % values().length;

        return switch(redirectedValue){
            case 0 -> N;
            case 1 -> NE;
            case 2 -> E;
            case 3 -> SE;
            case 4 -> S;
            case 5 -> SW;
            case 6 -> W;
            case 7 -> NW;
            default -> throw new IllegalStateException();
        };
    }
}
