package driver;

import java.util.Scanner;

public class Driver {

    private static String padString(String string, int paddingLength) {
        String paddedString = string;

        for (int i = string.length(); i < paddingLength + 1; i++) {
            paddedString += " ";
        }

        return paddedString;
    }

    public static void print(TwentyFortyEight game) {
        int size = game.getSize();
        int longestString = 0;

        String[][] board = new String[size][size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                String value = Integer.toString(game.getValue(y, x));
                if (value.length() > longestString) {
                    longestString = value.length();
                }

                String empty = Integer.toString(TwentyFortyEight.EMPTY);

                if (value.equals(empty)) {
                    board[y][x] = " ";
                } else {
                    board[y][x] = value;
                }
            }
        }

        for (int y = 0; y < size; y++) {
            String rowString = "";

            for (int x = 0; x < size; x++) {
                rowString += padString(board[y][x], longestString);
            }

            System.out.println(rowString);
        }
    }

    public static void main(String[] args) {
        TwentyFortyEight game = new TwentyFortyEight(8);

        Scanner input = new Scanner(System.in);

        while (game.gameEnded() == false) {
            print(game);

            char move = ' ';

            System.out.println("Input W/A/S/D:");
            move = input.next().charAt(0);

            while (move != TwentyFortyEight.INPUT_UP && move != TwentyFortyEight.INPUT_LEFT && move != TwentyFortyEight.INPUT_DOWN && move != TwentyFortyEight.INPUT_RIGHT) {
                System.out.println("Invalid input. Try again:");
                move = input.next().charAt(0);
            }

            game.play(move);
        }

        input.close();

        System.out.println("Game over! Score is " + game.getScore() + "!");
    }
}