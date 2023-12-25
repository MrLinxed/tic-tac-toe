import java.util.Scanner;

public class Main {
    // TODO: Add an optional 3rd player
    // TODO: Add a read.me

    private static final Scanner cellInput = new Scanner(System.in);

    private static GameBoard gameBoard;

    public static void main(String[] args) {
        while (true) {
            int boardSize;
            do {
                System.out.println("===================");
                System.out.println("Board size? (3-10): ");
                boardSize = Main.cellInput.nextInt();

            } while (boardSize < 3 || boardSize > 10);

            handleGame(boardSize);

            String answer;
            do {
                System.out.println("===================");
                System.out.println("Play again? (Y/N): ");
                answer = Main.cellInput.nextLine();
                answer = answer.isBlank() ? "Y" : answer.toUpperCase();

            } while (!answer.equals("N") && !answer.equals("Y"));

            if(answer.equals("N")) {
                break;
            }
        }
    }

    public static void handleGame(int boardSize) {
        boolean isPlayerX = false;
        Main.gameBoard = new GameBoard(boardSize);

        while (true) {
            isPlayerX = !isPlayerX;
            Main.clearScreen();
            Main.gameBoard.render();

            int index = Main.askForCell(isPlayerX);
            if(isPlayerX) {
                Main.gameBoard.setCellState(CellState.X, index);
            } else {
                Main.gameBoard.setCellState(CellState.O, index);
            }

            if(Main.gameBoard.hasWinner()) {
                Main.clearScreen();
                Main.gameBoard.render();
                System.out.println("Game over! The winner is: " + Main.gameBoard.getWinner());
                break;
            } else if(!Main.gameBoard.hasEmptyCell()){
                Main.clearScreen();
                Main.gameBoard.render();
                System.out.println("DRAW! No winner.");
                break;
            }
        }
    }

    public static void clearScreen() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    public static int askForCell(Boolean isPlayerX) {
        System.out.println("Player - " + (isPlayerX ? "X" : "O") + " - Pick A Cell:");
        String cell = Main.cellInput.nextLine();

        if (cell.isBlank()) {
            System.out.println("No option picked");
            return askForCell(isPlayerX);
        }

        int index = Main.gameBoard.coordinatesToIndex(cell.toUpperCase());

        if(Main.gameBoard.getCellState(index) != CellState.EMPTY) {
            System.out.println("Incorrect cell picked");
            return askForCell(isPlayerX);
        }

        return index;
    }
}