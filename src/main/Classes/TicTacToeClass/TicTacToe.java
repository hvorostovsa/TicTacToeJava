package TicTacToeClass;

public final class TicTacToe {
    private final int side;
    private final TicTacToeValues[][] field;
    private final String borderError = "Coordinate cannot be determined outside the field";

    public TicTacToe(int side) {
        if (side > 0) {
            this.side = side;
            field = new TicTacToeValues[side][side];
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    field[i][j] = TicTacToeValues.NOTHING;
                }
            }
        }
        else throw new IndexOutOfBoundsException("Side length cannot be less than 1");
    }

    private boolean isValid(int j, int k) {
        return (j >= 0 && j < side) && (k >= 0 && k < side);
    }

    public TicTacToeValues get(int row, int column) {
        if (isValid(row, column)) return field[row][column];
        else throw new IndexOutOfBoundsException(borderError);
    }

    private void set(int j, int k, TicTacToeValues value) throws CellOperationException {
        if (isValid(j, k)) {
            if (field[j][k] == TicTacToeValues.NOTHING) field[j][k] = value;
            else if (field[j][k] == TicTacToeValues.CROSS) throw new CellOperationException("X is already in this cell"); //idk what type of exception to use
            else throw new CellOperationException("O is already in this cell");
        } else throw new IndexOutOfBoundsException(borderError);
    }

    public void setX(int row, int column) throws CellOperationException {
        set(row, column, TicTacToeValues.CROSS);
    }

    public void setO(int row, int column) throws CellOperationException {
        set(row, column, TicTacToeValues.ZERO);
    }

    public void clear(int row, int column) throws CellOperationException {
        if (isValid(row, column)) {
            if (field[row][column] != TicTacToeValues.NOTHING) field[row][column] = TicTacToeValues.NOTHING;
            else throw new CellOperationException("Cell is already clear");
        } else throw new IndexOutOfBoundsException(borderError);
    }

    private int oneSequence(boolean[][][] flags, int result, int j, int k, TicTacToeValues value, int difJ, int difK, int flag) {
        int border = side - 1;
        if (flag == 1 && k != border || flag == 2 && k != border && j != border
            || flag == 3 && j != border || flag == 4 && k != 0 && j != border) {
            if (!flags[j][k][flag] && field[j][k] == value && field[j + difJ][k + difK] == value) {
                result += 1;
                result = oneSequence(flags, result, j + difJ, k + difK, value, difJ, difK, flag);
            }
        }
        flags[j][k][flag] = true;
        return result;
    }

    private int oneCellSequences(boolean[][][] flags, int result, int j, int k, TicTacToeValues value) {
        if (field[j][k] == TicTacToeValues.NOTHING) return 0;

        int maxLength1 = oneSequence(flags, result, j, k, value, 0, 1, 1);
        int maxLength2 = oneSequence(flags, result, j, k, value, 1, 1, 2);
        int maxLength3 = oneSequence(flags, result, j, k, value, 1, 0, 3);
        int maxLength4 = oneSequence(flags, result, j, k, value, 1, -1, 4);

        if (maxLength1 >= maxLength2 && maxLength1 >= maxLength3 && maxLength1 >= maxLength4) return maxLength1;
        else if (maxLength2 >= maxLength3 && maxLength2 >= maxLength4) return maxLength2;
        else return Math.max(maxLength3, maxLength4);
    }

    private int longestSequence(TicTacToeValues value) {
        // для проверки, входил ли знак в какую-либо из последовательностей
        // если входил, то нет смысла считать для него эту последовательность заново
        boolean[][][] flags = new boolean[side][side][5];

        int result = 1;
        int maxLength = 0;
        for (int row = 0; row < side; row++) {
            for (int column = 0; column < side; column++) {
                maxLength = Math.max(oneCellSequences(flags, result, row, column, value), maxLength);
            }
        }
        return maxLength;
    }

    public int longestSequenceX() {
        return longestSequence(TicTacToeValues.CROSS);
    }

    public int longestSequenceO() {
        return longestSequence(TicTacToeValues.ZERO);
    }

    public String sizeTest() {
        return "height and width is " + side;
    }

    public static class CellOperationException extends Exception {
        public CellOperationException(String message) {
            super(message);
        }
    }

    public enum TicTacToeValues {
        CROSS, ZERO, NOTHING
    }
}
