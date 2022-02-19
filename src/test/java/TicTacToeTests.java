
import org.junit.Test;

import static org.junit.Assert.*;

public final class TicTacToeTests {
    @Test
    public void size() {
        assertEquals("height and width is 3", new TicTacToe(3).sizeTest());
    }

    @Test
    public void valueTest() {
        TicTacToe field = new TicTacToe(3); //create new field

        //few moves
        field.setX(1,2);
        field.setO(0,0);
        field.setO(2,2);
        field.setX(0,1);

        assertNull(field.value(1, 1));
        assertEquals(true, field.value(1,2));
        assertEquals(false, field.value(0, 0));
        assertEquals(true, field.value(0,1));
        assertEquals(false, field.value(2, 2));

        //clear the cells
        field.clear(2,2);
        field.clear(0,1);

        assertNull(field.value(2, 2));
        assertNull(field.value(0, 1));
    }

    // trying to put smth out of bounds
    @Test(expected = IndexOutOfBoundsException.class)
    public void firstExceptionTest() {
        new TicTacToe(3).setX(3,1);
    }

    //trying to define value outside of bounds
    @Test(expected = IndexOutOfBoundsException.class)
    public void secondExceptionTest() {
        new TicTacToe(3).value(3,0);
    }

    //trying to put smth to a non-empty cell
    @Test(expected = ArrayStoreException.class)
    public void thirdExceptionTest() {
        TicTacToe field = new TicTacToe(3);
        field.setO(1, 1);
        field.setX(1, 1);
    }

    //trying to clear cell outside of bounds
    @Test(expected = ArrayStoreException.class)
    public void fourthExceptionTest() {
        new TicTacToe(3).clear(0,2);
    }

    @Test
    public void longestSequenceTest() { // не проходит
        TicTacToe field1 = new TicTacToe(3);
        field1.setX(0,1);
        field1.setX(1,1);
        field1.setX(2,1);
        assertEquals(3, field1.longestSequenceX());
    }

}
