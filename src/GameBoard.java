import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoard {
    private final int boardSize;
    private final GameCell[] board;

    public GameBoard(int boardSize) {
        this.boardSize = boardSize;

        this.board = new GameCell[boardSize * boardSize];
        for(int i = 0; i < this.board.length; i++) {
            this.board[i] = new GameCell();
        }
    }

    public int coordinatesToIndex(String coordinates) {
        String fixedLengthString = coordinates.substring(0, 2).toUpperCase();

        char columnIdentifier = fixedLengthString.charAt(0);
        char rowIdentifier = fixedLengthString.charAt(1);

        if(
            !Character.isAlphabetic(columnIdentifier)
            || !Character.isDigit(rowIdentifier)
        ){
            return -1;
        }

        int columnToNumber = columnIdentifier - 'A';
        int rowToNumber = rowIdentifier - '0' - 1;
        return columnToNumber + (this.boardSize * rowToNumber);
    }

    public boolean setCellState(CellState state, String coordinates) {
        return this.setCellState(state, this.coordinatesToIndex(coordinates));
    }

    public boolean setCellState(CellState state, int index) {
        if(index >= this.board.length) {
            return false;
        }

        return this.board[index].setState(state);
    }

    private boolean checkArrayOfCellsTheSame(GameCell[] cells) {
        GameCell firstCell = cells[0];

        for (int i = 1; i < cells.length; i++) {
            if (firstCell.notEquals(cells[i])) {
                return false;
            }
        }

        return true;
    }

    private String getHorizontalWinner() {
        for(int i = 0; i < this.board.length; i += this.boardSize) {
            GameCell[] cells = Arrays.copyOfRange(this.board, i, this.boardSize);
            if(this.checkArrayOfCellsTheSame(cells)){
                return cells[0].getValue();
            }
        }

        return "";
    }

    private String getVerticalWinner() {
        for(int i = 0; i < this.boardSize; i++) {
            List<GameCell> cells = new ArrayList<>();
            for(int j = i; j < this.board.length; j += this.boardSize) {
                cells.add(this.board[j]);
            }

            GameCell[] convertedCells = cells.toArray(new GameCell[0]);
            if(this.checkArrayOfCellsTheSame(convertedCells)){
                return convertedCells[0].getValue();
            }
        }

        return "";
    }

    public String getWinner() {
        String horizontalWinner = this.getHorizontalWinner();
        if(!horizontalWinner.isBlank()) return horizontalWinner;

        String verticalWinner = this.getVerticalWinner();
        if(!verticalWinner.isBlank()) return verticalWinner;

        return "";
    }

    public boolean hasWinner() {
        return !this.getWinner().isBlank();
    }

    public GameState getState() {

    }

    public void render() {
        // Print Top Row (A / B / C etc.)
        for(int i = 0; i < this.boardSize; i++) {
            System.out.print("  " + ((char) ('A' + i)) + " ");
        }
        System.out.println();

        int row = 1;
        boolean nextLine = true;
        for(int i = 0; i < this.board.length; i++){
            if(nextLine) {
                nextLine = false;
                System.out.print(row);
                row++;
            }

            GameCell cell = this.board[i];
            cell.render();

            if((i + 1) % this.boardSize == 0) {
                nextLine = true;
                System.out.println();
                System.out.print(" ");
                for (int j = 0; j < this.boardSize; j++) {
                    System.out.print("---");
                    System.out.print(" ");
                }
                System.out.println();
            } else {
                System.out.print("|");
            }
        }

        System.out.println();
        System.out.println();
        System.out.println();
    }
}
