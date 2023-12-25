public class GameCell {
    private CellState state = CellState.EMPTY;

    public void setState(CellState state) {
        if(this.isUsed()){
            return;
        }

        this.state = state;
    }
    public CellState getState() {
        return state;
    }

    public boolean notEquals(GameCell otherCell) {
        return !otherCell.getState().equals(state);
    }

    public boolean isUsed() {
        return !state.equals(CellState.EMPTY);
    }

    public String getValue() {
        return switch (this.state) {
            case O -> "O";
            case X -> "X";
            case Y -> "Y";
            default -> " ";
        };
    }

    public void render() {
        switch (this.state) {
            case O -> System.out.print(" O ");
            case X -> System.out.print(" X ");
            case Y -> System.out.print(" Y ");
            default -> System.out.print("   ");
        };
    }
}
