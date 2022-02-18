
public final class TicTacToe {
    private final int side;
    private static Boolean[][] field;

    public TicTacToe(int side) {
        this.side = side;
        field = new Boolean[side][side];
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                field[i][j] = null;
            }
        }
    }



    public Boolean value(int row, int column) {
        if (row < side && column < side) return field[row][column];
        else throw new IndexOutOfBoundsException("Coordinate cannot be determined outside the field");
    }

    private void set(int j, int k, boolean value) {
        if (j < side && k < side) {
            if (field[j][k] == null) field[j][k] = value;
            else if (field[j][k]) throw new ArrayStoreException("X is already in this cell"); //idk what type of exception to use
            else if (!field[j][k]) throw new ArrayStoreException("O is already in this cell");
        } else throw new IndexOutOfBoundsException("Coordinate cannot be determined outside the field");
    }

    public void setX(int row, int column) {
        set(row, column, true);
    }

    public void setO(int row, int column) {
        set(row, column, false);
    }

    public void clear(int row, int column) {
        if (row < side && column < side) {
            if (field[row][column] != null) field[row][column] = null;
            else throw new ArrayStoreException("Cell is already clear");
        } else throw new IndexOutOfBoundsException("Coordinate cannot be determined outside the field");
    }

//    private int longestSequence(Boolean value) {
//        for (int row = 0; row < side; row++) {
//            for (int column = 0; column < side; column++) {
//                if (field[row][column] == null) break;
//
//                else ()
//
//
//            }
//        }
//    }



    public String sizeTest() {
        return "height and width is " + side;
    }



}
