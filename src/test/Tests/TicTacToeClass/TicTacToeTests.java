package TicTacToeClass;

import org.junit.Test;

import static org.junit.Assert.*;

public final class TicTacToeTests {
    @Test
    public void valueTest() throws TicTacToe.CellOperationException{
        TicTacToe field = new TicTacToe(3); //create new field

        //few moves
        field.setX(1,2);
        field.setO(0,0);
        field.setO(2,2);
        field.setX(0,1);

        assertEquals(TicTacToe.TicTacToeValues.NOTHING ,field.get(1, 1));
        assertEquals(TicTacToe.TicTacToeValues.CROSS, field.get(1,2));
        assertEquals(TicTacToe.TicTacToeValues.ZERO, field.get(0, 0));
        assertEquals(TicTacToe.TicTacToeValues.CROSS, field.get(0,1));
        assertEquals(TicTacToe.TicTacToeValues.ZERO, field.get(2, 2));

        //clear the cells
        field.clear(2,2);
        field.clear(0,1);

        assertEquals(TicTacToe.TicTacToeValues.NOTHING ,field.get(2, 2));
        assertEquals(TicTacToe.TicTacToeValues.NOTHING ,field.get(0, 1));
    }

    // trying to put smth out of bounds
    @Test(expected = IndexOutOfBoundsException.class)
    public void setOutsideField() throws TicTacToe.CellOperationException{
        new TicTacToe(3).setX(3,1);
    }

    //trying to define value outside of bounds
    @Test(expected = IndexOutOfBoundsException.class)
    public void getOutsideField() {
        new TicTacToe(3).get(3,0);
    }

    //trying to put smth to a non-empty cell
    @Test(expected = TicTacToe.CellOperationException.class)
    public void setInNonEmptyCell() throws TicTacToe.CellOperationException {
        TicTacToe field = new TicTacToe(3);
        field.setO(1, 1);
        field.setX(1, 1);
    }

    //trying to clear empty cell
    @Test(expected = TicTacToe.CellOperationException.class)
    public void clearEmptyCell() throws TicTacToe.CellOperationException {
        new TicTacToe(3).clear(0,2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void incorrectSideLength() {
        new TicTacToe(0);
    }

    @Test
    public void longestSequenceTest() throws TicTacToe.CellOperationException {
        TicTacToe field1 = new TicTacToe(2);
        field1.setX(0,1);
        field1.setO(0,0);
        field1.setX(1,1);
        field1.setO(1,0);
        assertEquals(2, field1.longestSequenceX());

        TicTacToe field2 = new TicTacToe(4);
        field2.setX(0,2);
        field2.setX(1,2);
        field2.setX(2,2);
        field2.setX(3,2);
        field2.setX(1,1);
        field2.setX(2,0);
        field2.setO(0, 3);
        field2.setO(1, 3);
        field2.setO(2, 3);
        field2.setO(0, 0);
        field2.setO(0, 1);
        assertEquals(4, field2.longestSequenceX());
        assertEquals(3, field2.longestSequenceO());

        TicTacToe field3 = new TicTacToe(100);
        for (int i = 0; i < 10; i++) field3.setX(0, i);
        for (int i = 0; i < 90; i++) field3.setX(5, i);
        for (int i = 0; i < 15; i++) field3.setX(10, i);
        for (int i = 0; i < 67; i++) field3.setX(20, i);
        for (int i = 0; i < 8; i++) field3.setX(3, i);
        assertEquals(90, field3.longestSequenceX());

        TicTacToe field4 = new TicTacToe(5);
        assertEquals(0, field4.longestSequenceX());
        assertEquals(0, field4.longestSequenceO());

        TicTacToe field5 = new TicTacToe(5);
        field5.setX(2,0);
        field5.setX(1,2);
        field5.setX(2,2);
        field5.setO(0, 0);
        field5.setO(1, 0);
        field5.setO(3, 0);
        field5.setO(3, 1);
        field5.setO(3, 2);
        assertEquals(2, field5.longestSequenceX());
        assertEquals(3, field5.longestSequenceO());
        }
}
