package enums;

import enums.MoveDirection;
import physical.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoveDirectionTest {

    @Test
    public void testMovement() {
        assertEquals(new Vector2d(0, 1), MoveDirection.N.movement());
        assertEquals(new Vector2d(1, 1), MoveDirection.NE.movement());
        assertEquals(new Vector2d(1, 0), MoveDirection.E.movement());
        assertEquals(new Vector2d(1, -1), MoveDirection.SE.movement());
        assertEquals(new Vector2d(0, -1), MoveDirection.S.movement());
        assertEquals(new Vector2d(-1, -1), MoveDirection.SW.movement());
        assertEquals(new Vector2d(-1, 0), MoveDirection.W.movement());
        assertEquals(new Vector2d(-1, 1), MoveDirection.NW.movement());
    }

    @Test
    public void testRedirect() {
        assertEquals(MoveDirection.NE, MoveDirection.N.Redirect(1));
        assertEquals(MoveDirection.SW, MoveDirection.NE.Redirect(4));
        assertEquals(MoveDirection.E, MoveDirection.N.Redirect(2));
        assertEquals(MoveDirection.W, MoveDirection.E.Redirect(4));
        assertEquals(MoveDirection.NW, MoveDirection.W.Redirect(1));
        assertEquals(MoveDirection.NW, MoveDirection.SW.Redirect(2));
        assertEquals(MoveDirection.E, MoveDirection.SW.Redirect(5));
        assertEquals(MoveDirection.S, MoveDirection.SW.Redirect(7));
    }
}
