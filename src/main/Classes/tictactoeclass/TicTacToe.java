package tictactoeclass;

import java.util.Arrays;
import java.util.Objects;

public class TicTacToe {
    private final int size;
    private final TicTacToeType[][] field;
    private final String BORDER_ERROR = "Coordinate cannot be determined outside the field";

    public TicTacToe(int size) {
        if (size > 0) {
            this.size = size;
            field = new TicTacToeType[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    field[i][j] = TicTacToeType.NOTHING;
                }
            }
        }
        else throw new IndexOutOfBoundsException("Side length cannot be less than 1");
    }

    private boolean isValid(int j, int k) {
        return (j >= 0 && j < size) && (k >= 0 && k < size);
    }

    public TicTacToeType get(int row, int column) {
        if (isValid(row, column)) return field[row][column];
        else throw new IndexOutOfBoundsException(BORDER_ERROR);
    }

    private boolean set(int j, int k, TicTacToeType value) {
        if (isValid(j, k)) {
            if (field[j][k] == TicTacToeType.NOTHING && value != TicTacToeType.NOTHING ||
                field[j][k] != TicTacToeType.NOTHING && value == TicTacToeType.NOTHING)
            {
                field[j][k] = value;
                return true;
            }
        } else throw new IndexOutOfBoundsException(BORDER_ERROR);
        return false;
    }

    public boolean setX(int row, int column) {
        return set(row, column, TicTacToeType.CROSS);
    }

    public boolean setO(int row, int column)  {
        return set(row, column, TicTacToeType.ZERO);
    }

    public boolean clear(int row, int column) {
        return set(row, column, TicTacToeType.NOTHING);
    }

    private int oneSequence(boolean[][][] flags, int result, int row, int column, TicTacToeType value, int difRow, int difColumn, Sequence flag) {
        int border = size - 1;
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

    private int cellMaxSequence(boolean[][][] flags, int result, int row, int column, TicTacToeType value) {
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
        boolean[][][] flags = new boolean[size][size][Sequence.values().length];

        int result = 1;
        int maxLength = 0;
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                maxLength = Math.max(cellMaxSequence(flags, result, row, column, value), maxLength);
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

    public boolean isFull() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (field[row][column] == TicTacToeType.NOTHING) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("      ");
        for (int column = 1; column < size + 1; column++) {
            result.append(column);
            if (column != size) result.append("        ");
        }
        result.append("\n");
        for (int i = 0; i < size; i++) {
            int row = i + 1;
            result.append("   ");
            result.append("⢰⠒⠒⠒⠒⠒⡆".repeat(size));
            result.append("\n");
            result.append(" ").append(row).append(" ");
            for (int j = 0; j < size; j++) {
                if (field[i][j] == TicTacToeType.NOTHING) result.append("⢸⠀     ⡇");
                else if (field[i][j] == TicTacToeType.ZERO) result.append("⢸zero O⡇");
                else result.append("⢸crss X⡇");
            }
            result.append("\n");
            result.append("   ");
            result.append("⠸⠤⠤⠤⠤⠤⠇".repeat(size));
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicTacToe ticTacToe = (TicTacToe) o;
        return size == ticTacToe.size && Arrays.deepEquals(field, ticTacToe.field);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.deepHashCode(field);
        return result;
    }

    private record Triple(int difRow, int difColumn, Sequence flag) {
    }

    public enum TicTacToeType {
        CROSS, ZERO, NOTHING
    }

    private enum Sequence {
        RIGHT(0), RIGHT_DIAGONAL(1), BOTTOM(2), LEFT_DIAGONAL(3);
        private final int number;

        Sequence(int number){
            this.number = number;
        }

        private int getNumber() {
            return number;
        }
    }
}


