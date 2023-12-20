import java.util.Objects;
import java.util.Scanner;

public class Main {

    // TODO: Implement draw check
    // TODO: Add replay-ability
    // TODO: Dynamic grid size (via question)
    // TODO: Add an optional 3rd player

    private static final Scanner cellInput = new Scanner(System.in);

    private static final String[][] gameState = {
        {" ", " ", " "},
        {" ", " ", " "},
        {" ", " ", " "}
    };

    public static void main(String[] args) {

        boolean isPlayerX = false;
        boolean hasWinner;

        do {
            isPlayerX = !isPlayerX;
            Main.clearScreen();

            Main.renderGameField();
            int[] coords = Main.askForCell(isPlayerX);
            if(isPlayerX) {
                Main.gameState[coords[0]][coords[1]] = "X";
            } else {
                Main.gameState[coords[0]][coords[1]] = "O";
            }

            String winner = Main.getWinner();
            hasWinner = winner.equals("X") || winner.equals("O");

            if(hasWinner) {
                Main.clearScreen();
                Main.renderGameField();
                System.out.println("The winner is: " + winner);
            }

        } while (!hasWinner);

    }

    public static void clearScreen() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    public static int[] askForCell(Boolean isPlayerX) {
        System.out.println("Player - " + (isPlayerX ? "X" : "O") + " - Pick A Cell:");
        String cell = Main.cellInput.nextLine();

        if (cell.isBlank()) {
            System.out.println("No option picked");
            return askForCell(isPlayerX);
        }

        cell = cell.toUpperCase();

        if (!cell.matches("([ABC]).*") || !cell.matches(".*([123])")) {
            System.out.println("Incorrect cell picked");
            return askForCell(isPlayerX);
        }

        String[] coords = cell.split("");
        int x = Integer.parseInt(coords[1]) - 1;
        int y = Objects.equals(coords[0], "A") ? 0 : Objects.equals(coords[0], "B") ? 1 : 2;

        String currentValue = Main.gameState[x][y];

        if(!currentValue.isBlank()) {
            System.out.println("Invalid cell picked");
            return askForCell(isPlayerX);
        }

        return new int[]{x, y};
    }

    public static void renderGameField() {
        System.out.println("  A   B   C");
        for(int y = 0; y < Main.gameState.length; y++) {
            System.out.printf("%s %s%n", y + 1, String.join(" | ", Main.gameState[y]));
            System.out.println(" -----------");
        }
    }

    public static String getWinner() {
        String winner = "";

        for(int y = 0; y < Main.gameState.length; y++) {
            // Vertical check
            String firstValue = "";
            for (int x = 0; x < Main.gameState[y].length; x++) {
                if(x == 0) {
                    firstValue = Main.gameState[y][x];
                } else if (!Objects.equals(firstValue, Main.gameState[y][x])) {
                    firstValue = "";
                    break;
                } else {
                    winner = firstValue;
                }
            }

            if(!firstValue.isEmpty()){
                break;
            }
        }

        return winner;
    }
}