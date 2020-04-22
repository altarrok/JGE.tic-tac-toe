package tictactoe;

public class Referee {
    private Board board;

    public Referee(Board board) {
        this.board = board;
    }

    /**
     * Returns the winner of the board, if its a draw returns Block.EMPTY
     * If board is still playable (not finished) returns null
     */
    public Board.Block check() {
        Board.Block b1;
        Board.Block b2;
        Board.Block b3;
        //Rows
        for (int y = 0; y < 3; y++) {
            b1 = board.getBlock(0, y);
            b2 = board.getBlock(1, y);
            b3 = board.getBlock(2, y);
            if (areEqual(b1, b2, b3) && b1 != Board.Block.EMPTY) {
                return b1;
            }
        }

        //Cols
        for (int x = 0; x < 3; x++) {
            b1 = board.getBlock(x, 0);
            b2 = board.getBlock(x, 1);
            b3 = board.getBlock(x, 2);
            if (areEqual(b1, b2, b3) && b1 != Board.Block.EMPTY) {
                return b1;
            }
        }

        //Diagonals
        b1 = board.getBlock(0, 0);
        b2 = board.getBlock(1, 1);
        b3 = board.getBlock(2, 2);
        if (areEqual(b1, b2, b3) && b1 != Board.Block.EMPTY) {
            return b1;
        }

        b1 = board.getBlock(0, 2);
        b2 = board.getBlock(1, 1);
        b3 = board.getBlock(2, 0);
        if (areEqual(b1, b2, b3) && b1 != Board.Block.EMPTY) {
            return b1;
        }

        boolean tie = true;
        for (int i = 0; i < 9; i++) {
            if (board.getBlock(i) == Board.Block.EMPTY) {
                tie = false;
                break;
            } else {
                tie = true;
            }
        }
        if (tie) {
            return Board.Block.EMPTY;
        }

        return null;
    }

    private boolean areEqual(Board.Block b1, Board.Block b2, Board.Block b3) {
        return b1 == b2 && b2 == b3;
    }
}
