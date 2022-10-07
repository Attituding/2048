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

        int width = (longestString + 1) * size;

        String seperator = "";

        while (seperator.length() < width) {
            seperator += "~";
        }

        System.out.println("");
        System.out.println(seperator);

        for (int y = 0; y < size; y++) {
            String rowString = "";

            for (int x = 0; x < size; x++) {
                rowString += padString(board[y][x], longestString);
            }

            System.out.println(rowString);
        }

        System.out.println(seperator);
    }
    
    public static char generateMove() {
        int randomInt = (int) (Math.random() * 4);
        
        if (randomInt == 0) {
            return 'w';
        } else if (randomInt == 1) {
            return 'a';
        } else if (randomInt == 2) {
            return 's';
        } else {
            return 'd';
        }
    }

    public static void main(String[] args) {
        System.out.println(
                "  ____     ___    _  _      ___  \n"
                + " |___ \\   / _ \\  | || |    ( _ ) \n"
                + "   __) | | | | | | || |_   / _ \\ \n"
                + "  / __/  | |_| | |__   _| | (_) |\n"
                + " |_____|  \\___/     |_|    \\___/ \n"
                + "                                 "
        );

        TwentyFortyEight game = new TwentyFortyEight(4);

        Scanner input = new Scanner(System.in);
        
        print(game);

        while (game.canPlay()) {
            char move = ' ';

            System.out.println("Input W/A/S/D:");
            move = input.next().charAt(0);
            // move = generateMove();
            
            while (move != TwentyFortyEight.INPUT_UP && move != TwentyFortyEight.INPUT_LEFT && move != TwentyFortyEight.INPUT_DOWN && move != TwentyFortyEight.INPUT_RIGHT) {
                System.out.println("Invalid input. Try again:");
                move = input.next().charAt(0);
            }

            game.play(move);

            print(game);
        }

        input.close();

        System.out.println("Game over! You got a score of " + game.getScore() + "!");
    }
}
