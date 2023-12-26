import java.util.Scanner;

public class Main {
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

            int playerAmount;
            do {
                System.out.println("===================");
                System.out.println("Amount of players? (2-3): ");
                playerAmount = Main.cellInput.nextInt();

            } while (playerAmount < 2 || playerAmount > 3);

            handleGame(boardSize, playerAmount);

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

    public static void handleGame(int boardSize, int playerAmount) {
        CellState currentPlayer = CellState.Y;
        Main.gameBoard = new GameBoard(boardSize);

        while (true) {
            // Next player
            currentPlayer = switch(currentPlayer) {
                case X -> CellState.O;
                case O -> playerAmount == 3 ? CellState.Y : CellState.X;
                default -> CellState.X;
            };

            Main.clearScreen();
            Main.gameBoard.render();

            int index = Main.askForCell(currentPlayer);
            Main.gameBoard.setCellState(currentPlayer, index);

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

    public static int askForCell(CellState currentPlayer) {
        System.out.println("Player - " + currentPlayer.toString() + " - Pick A Cell:");
        String cell = Main.cellInput.nextLine();

        if (cell.isBlank()) {
            System.out.println("No option picked");
            return askForCell(currentPlayer);
        }

        int index = Main.gameBoard.coordinatesToIndex(cell.toUpperCase());

        if(Main.gameBoard.getCellState(index) != CellState.EMPTY) {
            System.out.println("Incorrect cell picked");
            return askForCell(currentPlayer);
        }

        return index;
    }
}