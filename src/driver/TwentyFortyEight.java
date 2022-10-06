package driver;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class TwentyFortyEight {

    public static final int EMPTY = 0;
    public static final char INPUT_UP = 'w';
    public static final char INPUT_LEFT = 'a';
    public static final char INPUT_DOWN = 's';
    public static final char INPUT_RIGHT = 'd';

    private final int[][] board;
    private final int size;

    public TwentyFortyEight(int size) {
        this.board = new int[size][size];
        this.size = size;

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                setValue(row, column, EMPTY);
            }
        }

        placeNumber();
        placeNumber();
    }

    public boolean canPlay() {
        // If there is atleast one empty square, the game can continue
        if (getEmptySquares().isEmpty() == false) {
            return true;
        }

        // Searching for possible merges
        int[][] columns = getColumns();
        int[][] rows = getRows();
        int[][] lines = Arrays.copyOf(columns, columns.length + rows.length);
        System.arraycopy(rows, 0, lines, columns.length, rows.length);

        for (int[] line : lines) {
            for (int i = 0; i + 1 < line.length; i++) {
                if (line[i] == line[i + 1]) {
                    return true;
                }
            }
        }

        return false;
    }

    public void play(char input) {
        ArrayList<Point> before = getEmptySquares();
        updateBoard(input);
        ArrayList<Point> after = getEmptySquares();

        // Checking for change
        if (before.equals(after) == false) {
            placeNumber();
        }
    }

    private void updateBoard(char input) {
        int[][] axes;

        if (input == TwentyFortyEight.INPUT_UP) {
            axes = getColumns();
            updateAxes(axes);
            setColumns(axes);
        } else if (input == TwentyFortyEight.INPUT_LEFT) {
            axes = getRows();
            updateAxes(axes);
            setRows(axes);
        } else if (input == TwentyFortyEight.INPUT_DOWN) {
            axes = getColumns();
            reverseAxes(axes);
            updateAxes(axes);
            reverseAxes(axes);
            setColumns(axes);
        } else if (input == TwentyFortyEight.INPUT_RIGHT) {
            axes = getRows();
            reverseAxes(axes);
            updateAxes(axes);
            reverseAxes(axes);
            setRows(axes);
        }
    }

    private void placeNumber() {
        ArrayList<Point> emptySquares = getEmptySquares();
        int randomNumber = getNumber();
        int randomIndex = (int) (Math.random() * emptySquares.size());
        Point randomSquare = emptySquares.get(randomIndex);
        setValue(randomSquare.y, randomSquare.x, randomNumber);
    }

    private void reverseAxes(int[][] axes) {
        for (int[] axis : axes) {
            int[] newAxis = new int[axis.length];

            for (int i = 0; i < axis.length; i++) {
                newAxis[axis.length - i - 1] = axis[i];
            }

            for (int i = 0; i < axis.length; i++) {
                axis[i] = newAxis[i];
            }
        }
    }

    private void updateAxes(int[][] axes) {
        for (int[] axis : axes) {
            compressArray(axis);
            mergeArray(axis);
            compressArray(axis);
        }
    }

    private void compressArray(int[] array) {
        ArrayList<Integer> tempList = new ArrayList();

        for (int value : array) {
            if (value != EMPTY) {
                tempList.add(value);
            }
        }

        for (int i = 0; i < array.length; i++) {
            if (tempList.size() > i) {
                array[i] = tempList.get(i);
            } else {
                array[i] = EMPTY;
            }
        }
    }

    private void mergeArray(int[] array) {
        for (int i = 0; i + 1 < array.length; i++) {
            Integer first = array[i];
            Integer second = array[i + 1];

            if (first.equals(second)) {
                array[i] = first + second;
                array[i + 1] = EMPTY;
            }
        }
    }

    private int[][] getColumns() {
        int[][] columns = new int[size][size];

        for (int x = 0; x < size; x++) {
            int[] column = new int[size];

            for (int y = 0; y < size; y++) {
                column[y] = getValue(y, x);
            }

            columns[x] = column;
        }

        return columns;
    }

    private ArrayList<Point> getEmptySquares() {
        ArrayList<Point> emptySquares = new ArrayList();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (getValue(y, x) == EMPTY) {
                    emptySquares.add(new Point(x, y));
                }
            }
        }

        return emptySquares;
    }

    private ArrayList<Point> getNeighbors(int row, int column) {
        ArrayList<Point> neighbors = new ArrayList();

        if (isInBounds(row - 1, column)) { // North
            neighbors.add(new Point(column, row - 1));
        }

        if (isInBounds(row, column + 1)) { // East
            neighbors.add(new Point(column + 1, row));
        }

        if (isInBounds(row + 1, column)) { // South
            neighbors.add(new Point(column, row + 1));
        }

        if (isInBounds(row, column - 1)) { //West
            neighbors.add(new Point(column - 1, row));
        }

        return neighbors;
    }

    private int getNumber() {
        int chance = (int) (Math.random() * 10);

        if (chance == 0) {
            return 4;
        }

        return 2;
    }

    private int[][] getRows() {
        return board;
    }

    public int getScore() {
        int highest = 0;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int value = getValue(y, x);
                if (value > highest) {
                    highest = value;
                }
            }
        }

        return highest;
    }

    public int getSize() {
        return size;
    }

    public int getValue(int row, int column) {
        return board[row][column];
    }

    public boolean isInBounds(int row, int column) {
        return isInBounds(row) && isInBounds(column);
    }

    public boolean isInBounds(int axis) {
        return axis >= 0 && axis < size;
    }

    private void setColumns(int[][] columns) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int value = columns[x][y];
                setValue(y, x, value);
            }
        }
    }

    private void setRows(int[][] rows) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int value = rows[y][x];
                setValue(y, x, value);
            }
        }
    }

    private void setValue(int row, int column, int value) {
        board[row][column] = value;
    }
}
