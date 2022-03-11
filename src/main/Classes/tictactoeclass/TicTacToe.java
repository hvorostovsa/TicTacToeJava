package tictactoeclass;

public class TicTacToe {
    private final int side;
    private final TicTacToeType[][] field;
    private final String BORDER_ERROR = "Coordinate cannot be determined outside the field";

    public TicTacToe(int side) {
        if (side > 0) {
            this.side = side;
            field = new TicTacToeType[side][side];
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    field[i][j] = TicTacToeType.NOTHING;
                }
            }
        }
        else throw new IndexOutOfBoundsException("Side length cannot be less than 1");
    }

    private boolean isValid(int j, int k) {
        return (j >= 0 && j < side) && (k >= 0 && k < side);
    }

    public TicTacToeType get(int row, int column) {
        if (isValid(row, column)) return field[row][column];
        else throw new IndexOutOfBoundsException(BORDER_ERROR);
    }

    private void set(int j, int k, TicTacToeType value) throws CellOperationException {
        if (isValid(j, k)) {
            if (field[j][k] == TicTacToeType.NOTHING) field[j][k] = value;
            else throw field[j][k] == TicTacToeType.CROSS ? new CellOperationException("X is already in this cell")
                    : new CellOperationException("O is already in this cell");
        } else throw new IndexOutOfBoundsException(BORDER_ERROR);
    }

    public void setX(int row, int column) throws CellOperationException {
        set(row, column, TicTacToeType.CROSS);
    }

    public void setO(int row, int column) throws CellOperationException {
        set(row, column, TicTacToeType.ZERO);
    }

    public void clear(int row, int column) throws CellOperationException {
        if (isValid(row, column)) {
            if (field[row][column] != TicTacToeType.NOTHING) field[row][column] = TicTacToeType.NOTHING;
            else throw new CellOperationException("Cell is already clear");
        } else throw new IndexOutOfBoundsException(BORDER_ERROR);
    }

    private int oneSequence(boolean[][][] flags, int result, int row, int column, TicTacToeType value, int difRow, int difColumn, Sequence flag) {
        int border = side - 1;
        if (flag == Sequence.RIGHT && column != border || flag == Sequence.RIGHT_DIAGONAL&& column != border && row != border
            || flag == Sequence.BOTTOM && row != border || flag == Sequence.LEFT_DIAGONAL && column != 0 && row != border) {
            if (!flags[row][column][flag.getNumber()] && field[row][column] == value && field[row + difRow][column + difColumn] == value) {
                result += 1;
                result = oneSequence(flags, result, row + difRow, column + difColumn, value, difRow, difColumn, flag);
            }
        }
        flags[row][column][flag.getNumber()] = true;
        return result;
    }

    private int oneCellSequences(boolean[][][] flags, int result, int row, int column, TicTacToeType value) {
        if (field[row][column] == TicTacToeType.NOTHING) return 0;
        int maxLength = 0;

        final Triple[] array = {
                new Triple(0, 1, Sequence.RIGHT),
                new Triple(1, 1, Sequence.RIGHT_DIAGONAL),
                new Triple(1, 0, Sequence.BOTTOM),
                new Triple(1, -1, Sequence.LEFT_DIAGONAL)
        };

        for (Triple sequence : array)  {
            maxLength = Math.max(oneSequence(flags, result, row, column, value, sequence.difRow, sequence.difColumn, sequence.flag), maxLength);
        }
        return maxLength;
    }

    private int longestSequence(TicTacToeType value) {
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
        return longestSequence(TicTacToeType.CROSS);
    }

    public int longestSequenceO() {
        return longestSequence(TicTacToeType.ZERO);
    }

    // для задания не нужно. Сделал для себя
    public boolean isFull() {
        for (int row = 0; row < side; row++) {
            for (int column = 0; column < side; column++) {
                if (field[row][column] == TicTacToeType.NOTHING) return false;
            }
        }
        return true;
    }

    // также для задания не нужно. Сделал для себя
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\t  ");
        for (int column = 1; column < side + 1; column++) result.append(column).append("        ");
        result.append(System.lineSeparator());
        for (int i = 0; i < side; i++) {
            int row = i + 1;
            result.append("   ");
            result.append("⢰⠒⠒⠒⠒⠒⡆".repeat(side));
            result.append(System.lineSeparator());
            result.append(" ").append(row).append(" ");
            for (int j = 0; j < side; j++) {
                if (field[i][j] == TicTacToeType.NOTHING) result.append("⢸⠀     ⡇");
                else if (field[i][j] == TicTacToeType.ZERO) result.append("⢸zero O⡇");
                else result.append("⢸crss X⡇");
            }
            result.append(System.lineSeparator());
            result.append("   ");
            result.append("⠸⠤⠤⠤⠤⠤⠇".repeat(side));
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    private static class Triple {
        int difRow;
        int difColumn;
        Sequence flag;

        Triple(int difRow, int difColumn, Sequence flag) {
            this.difRow = difRow;
            this.difColumn = difColumn;
            this.flag = flag;
        }
    }

    public static class CellOperationException extends Exception { // static? checked or unchecked
        public CellOperationException(String message) {
            super(message);
        }
    }

    public enum TicTacToeType {
        CROSS, ZERO, NOTHING
    }

    private enum Sequence {
        RIGHT(1), RIGHT_DIAGONAL(2), BOTTOM(3), LEFT_DIAGONAL(4);
        private final int number;

        Sequence(int number){
            this.number = number;
        }

        private int getNumber() {
            return number;
        }
    }
}


