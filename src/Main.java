import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    // TODO: Add replay-ability
    // TODO: Dynamic grid size (via question)
    // TODO: Add an optional 3rd player
    // TODO: Add a read.me

    private static final Scanner cellInput = new Scanner(System.in);

    private static final GameCell[][] gameState = {
        {new GameCell(), new GameCell(), new GameCell()},
        {new GameCell(), new GameCell(), new GameCell()},
        {new GameCell(), new GameCell(), new GameCell()}
    };

    private static final GameBoard gameBoard = new GameBoard(3);

    public static void main(String[] args) {



        boolean isPlayerX = false;
        boolean hasWinner;

        do {
            isPlayerX = !isPlayerX;
            Main.clearScreen();

            Main.gameBoard.render();

            Main.renderGameField();
            int[] coords = Main.askForCell(isPlayerX);
            if(isPlayerX) {
                Main.gameState[coords[0]][coords[1]].setState(CellState.X);
            } else {
                Main.gameState[coords[0]][coords[1]].setState(CellState.O);
            }

            String winner = Main.getWinner();
            hasWinner = winner.equals("X") || winner.equals("O");

            if(hasWinner) {
                Main.clearScreen();
                Main.renderGameField();
                System.out.println("The winner is: " + winner);
            } else if(Main.isDraw()){
                Main.clearScreen();
                Main.renderGameField();
                System.out.println("DRAW! No winner.");
                break;
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

        boolean success = Main.gameBoard.setCellState(isPlayerX ? CellState.X : CellState.O, cell);

        String[] coords = cell.split("");
        int x = Integer.parseInt(coords[1]) - 1;
        int y = Objects.equals(coords[0], "A") ? 0 : Objects.equals(coords[0], "B") ? 1 : 2;

        GameCell pickedCell = Main.gameState[x][y];

        if(pickedCell.isUsed()) {
            System.out.println("Invalid cell picked");
            return askForCell(isPlayerX);
        }

        return new int[]{x, y};
    }

    public static void renderGameField() {
        System.out.println("  A   B   C");
        for(int y = 0; y < Main.gameState.length; y++) {
            String[] rowValues = Arrays.stream(Main.gameState[y]).map(GameCell::getValue).toArray(String[]::new);
            System.out.printf("%s %s%n", y + 1, String.join(" | ", rowValues));
            System.out.println(" -----------");
        }
    }

    public static boolean isDraw() {
        boolean hasEmptySquare = false;
        for (int y = 1; y < Main.gameState.length; y++) {
            for (int x = 1; x < Main.gameState[y].length; x++) {
                GameCell gameCell = Main.gameState[y][x];
                if(!gameCell.isUsed()){
                    hasEmptySquare = true;
                    break;
                }
            }
        }

        return !hasEmptySquare;
    }

    public static String getWinner() {
        String winner = "";

        for (GameCell[] rowItems : Main.gameState) {
            GameCell firstItem = rowItems[0];

            if(!firstItem.isUsed()){
                continue;
            }

            boolean allSame = true;
            for (int i = 1; i < rowItems.length; i++) {
                if (rowItems[i].notEquals(firstItem)) {
                    allSame = false;
                    break;
                }
            }

            if(allSame) {
                winner = firstItem.getValue();
            }
        }

        if(!winner.isBlank()){
            return winner;
        }

        GameCell[] firstRowItems = Main.gameState[0];
        for(int x = 0; x < firstRowItems.length; x++){
            GameCell topItem = firstRowItems[x];

            if(!topItem.isUsed()){
                continue;
            }

            boolean allSame = true;
            //skip first row
            for(int y = 1; y < Main.gameState.length; y++){
                if (Main.gameState[y][x].notEquals(topItem)) {
                    allSame = false;
                    break;
                }
            }

            if(allSame) {
                winner = topItem.getValue();
            }
        }

        int gameSize = 3;
        int[] topLeftBottomRightDiagonal = new int[gameSize];
        int[] topRightBottomLeftDiagonal = new int[gameSize];

        for(int x = 0; x < gameSize; x++) {
            topLeftBottomRightDiagonal[x] = x;
            topRightBottomLeftDiagonal[x] = (gameSize - 1) - x;
        }

        GameCell firstItem = Main.gameState[0][0];
        boolean allSame = true;
        for (int y = 1; y < topLeftBottomRightDiagonal.length; y++) {
            int x = topLeftBottomRightDiagonal[y];
            if (Main.gameState[y][x].notEquals(firstItem)) {
                allSame = false;
                break;
            }
        }

        if(allSame) {
            return firstItem.getValue();
        }

        firstItem = Main.gameState[0][gameSize - 1];
        allSame = true;
        for (int y = 1; y < topRightBottomLeftDiagonal.length; y++) {
            int x = topRightBottomLeftDiagonal[y];
            if (Main.gameState[y][x].notEquals(firstItem)) {
                allSame = false;
                break;
            }
        }

        if(allSame) {
            return firstItem.getValue();
        }

        return winner;
    }
}