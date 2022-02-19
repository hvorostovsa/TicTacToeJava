
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

    private boolean isValid(int j, int k) {
        return (j >= 0 && j < side) && (k >= 0 && k < side);
    }

    public Boolean value(int row, int column) {
        if (isValid(row, column)) return field[row][column];
        else throw new IndexOutOfBoundsException("Coordinate cannot be determined outside the field");
    }

    private void set(int j, int k, boolean value) {
        if (isValid(j, k)) {
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
        if (isValid(row, column)) {
            if (field[row][column] != null) field[row][column] = null;
            else throw new ArrayStoreException("Cell is already clear");
        } else throw new IndexOutOfBoundsException("Coordinate cannot be determined outside the field");
    }
    // надо исправлять
    private int oneSequence(boolean[][][] flags, int flag, int result, int j, int k, Boolean value) {
        int maxLength = 0;
        int border = side - 1;

        if (field[j][k] == null) return 0;

        if (flag == 0 || flag == 1) {
            if (k != border && !flags[j][k][1] && field[j][k] == value && field[j][k + 1] == value) {
                result += 1;
                flags[j][k][1] = true;
                flags[j][k + 1][1] = true; // ошибка постановки флага заранее, не идет проверять дальше, тк думает, что уже заходил
                flag = 1;
                oneSequence(flags, flag, result, j, k + 1, value);
            }
            maxLength = Math.max(result, maxLength);
            result = 1;
        }

        if (flag == 0 || flag == 2) {
            if (k != border && j != border && !flags[j][k][2] && field[j][k] == value && field[j + 1][k + 1] == value) {
                result += 1;
                flags[j][k][2] = true;
                flags[j + 1][k + 1][2] = true;
                flag = 2;
                oneSequence(flags, flag, result, j + 1, k + 1, value);
            }
            maxLength = Math.max(result, maxLength);
            result = 1;
        }

        if (flag == 0 || flag == 3) {
            if (j != border && !flags[j][k][3] && field[j][k] == value && field[j + 1][k] == value) {
                result += 1;
                flags[j][k][3] = true;
                flags[j + 1][k][3] = true;
                flag = 3;
                oneSequence(flags, flag, result, j + 1, k, value);
            }
            maxLength = Math.max(result, maxLength);
            result = 1;
        }

        if (flag == 0 || flag == 4) {
            if (k != 0 && j != border &&!flags[j][k][4] && field[j][k] == value && field[j + 1][k - 1] == value) {
                result += 1;
                flags[j][k][4] = true;
                flags[j + 1][k - 1][4] = true;
                flag = 4;
                oneSequence(flags, flag, result, j + 1, k - 1, value);
            }
            maxLength = Math.max(result, maxLength);
        }
        return maxLength;
    }



    private int longestSequence(Boolean value) {
        boolean[][][] flags = new boolean[side][side][5];
        int flag = 0;
        int result = 1;
        int maxLength = 0;
        for (int row = 0; row < side; row++) {
            for (int column = 0; column < side; column++) {
                maxLength = Math.max(oneSequence(flags, flag, result, row, column, value), maxLength);
            }
        }
        return maxLength;
    }

    public int longestSequenceX() {
        return longestSequence(true);
    }

    public int longestSequenceO() {
        return longestSequence(false);
    }


    public String sizeTest() {
        return "height and width is " + side;
    }


}
