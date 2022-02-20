package TicTacToeClass;

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

    private int oneSequence(boolean[][][] flags, int flag, int result, int j, int k, Boolean value, int difJ, int difK, int flagNum) {
        int border = side - 1;
        if (k != border && !flags[j][k][flagNum] && field[j][k] == value && field[j + difJ][k + difK] == value) {
            result += 1;
            flag = flagNum;
            oneCellSequences(flags, flagNum, result, j + difJ, k + difK, value);
        }
        flags[j][k][flagNum] = true;
        return result;
    }

    // надо исправлять
    private int oneCellSequences(boolean[][][] flags, int flag, int result, int j, int k, Boolean value) {
        int maxLength1 = 0;
        int maxLength2 = 0;
        int maxLength3 = 0;
        int maxLength4 = 0;
        int border = side - 1;

        if (field[j][k] == null) return 0;

        if (flag == 0 || flag == 1) {
            maxLength1 = oneSequence(flags, flag, result, j, k, value, 0, 1, 1);
        }

        if (flag == 0 || flag == 2) {
            if (k != border && j != border && !flags[j][k][2] && field[j][k] == value && field[j + 1][k + 1] == value) {
                result += 1;
                flags[j][k][2] = true;
                flag = 2;
                oneCellSequences(flags, flag, result, j + 1, k + 1, value);
            }
            flags[j][k][2] = true;
            maxLength2 = Math.max(result, maxLength2);
            result = 1;
        }

        if (flag == 0 || flag == 3) {
            if (j != border && !flags[j][k][3] && field[j][k] == value && field[j + 1][k] == value) {
                result += 1;
                flags[j][k][3] = true;
                flag = 3;
                oneCellSequences(flags, flag, result, j + 1, k, value);
            }
            flags[j][k][3] = true;
            maxLength3 = Math.max(result, maxLength3);
            result = 1;
        }

        if (flag == 0 || flag == 4) {
            if (k != 0 && j != border &&!flags[j][k][4] && field[j][k] == value && field[j + 1][k - 1] == value) {
                result += 1;
                flags[j][k][4] = true;
                flag = 4;
                oneCellSequences(flags, flag, result, j + 1, k - 1, value);
            }
            flags[j][k][4] = true;
            maxLength4 = Math.max(result, maxLength4);
        }
        if (maxLength1 >= maxLength2 && maxLength1 >= maxLength3 && maxLength1 >= maxLength4) return maxLength1;
        else if (maxLength2 >= maxLength3 && maxLength2 >= maxLength4) return maxLength2;
        else return Math.max(maxLength3, maxLength4);
    }



    private int longestSequence(Boolean value) {
        boolean[][][] flags = new boolean[side][side][5];
        int flag = 0;
        int result = 1;
        int maxLength = 0;
        for (int row = 0; row < side; row++) {
            for (int column = 0; column < side; column++) {
                maxLength = Math.max(oneCellSequences(flags, flag, result, row, column, value), maxLength);
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
