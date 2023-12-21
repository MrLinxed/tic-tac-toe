import java.util.Objects;
import java.util.Scanner;

public class Main {

    // TODO: Implement draw check
    // TODO: Add replay-ability
    // TODO: Dynamic grid size (via question)
    // TODO: Add an optional 3rd player
    // TODO: Add a read.me

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

        for (String[] rowItems : Main.gameState) {
            String firstItem = rowItems[0];

            if(firstItem.equals(" ")){
                continue;
            }

            boolean allSame = true;
            for (int i = 1; i < rowItems.length; i++) {
                if (!rowItems[i].equals(firstItem)) {
                    allSame = false;
                    break;
                }
            }

            if(allSame) {
                winner = firstItem;
            }
        }

        if(!winner.isBlank()){
            return winner;
        }

        String[] firstRowItems = Main.gameState[0];
        for(int x = 0; x < firstRowItems.length; x++){
            String topItem = firstRowItems[x];

            if(topItem.equals(" ")){
                continue;
            }

            boolean allSame = true;
            //skip first row
            for(int y = 1; y < Main.gameState.length; y++){
                if (!Main.gameState[y][x].equals(topItem)) {
                    allSame = false;
                    break;
                }
            }

            if(allSame) {
                winner = topItem;
            }
        }

        int gameSize = 3;
        int[] topLeftBottomRightDiagonal = new int[gameSize];
        int[] topRightBottomLeftDiagonal = new int[gameSize];

        for(int x = 0; x < gameSize; x++) {
            topLeftBottomRightDiagonal[x] = x;
            topRightBottomLeftDiagonal[x] = (gameSize - 1) - x;
        }

        String firstItem = Main.gameState[0][0];
        boolean allSame = true;
        for (int y = 1; y < topLeftBottomRightDiagonal.length; y++) {
            int x = topLeftBottomRightDiagonal[y];
            if (!Main.gameState[y][x].equals(firstItem)) {
                allSame = false;
                break;
            }
        }

        if(allSame) {
            return firstItem;
        }

        firstItem = Main.gameState[0][gameSize - 1];
        allSame = true;
        for (int y = 1; y < topRightBottomLeftDiagonal.length; y++) {
            int x = topRightBottomLeftDiagonal[y];
            System.out.print(y);
            System.out.print(x);
            System.out.print("=");
            System.out.print(Main.gameState[y][x]);
            System.out.println();
            if (!Main.gameState[y][x].equals(firstItem)) {
                allSame = false;
                break;
            }
        }

        if(allSame) {
            return firstItem;
        }

        return winner;
    }
}