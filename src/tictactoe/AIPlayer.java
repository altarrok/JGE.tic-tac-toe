package tictactoe;

import engine.GameContainer;

public class AIPlayer extends Player {
    int depth;

    public AIPlayer(Board.Block b, int depth) {
        super(b, "AI");
        this.depth = depth;
    }

    @Override
    public void update(GameContainer gc, float dt) {
        GameManager gm = (GameManager) gc.getGame();
        Board b = (Board) gm.getObject("Board");
        if (gm.isTurn() ^ selectedBlock == Board.Block.O && b != null) {
            if (selectedBlock == Board.Block.X) {
                int nMove = -1;
                int maxV = Integer.MIN_VALUE;
                for (int i = 0; i < 9; i++) {
                    if (b.getBlock(i) == Board.Block.EMPTY) {
                        Board childB = new Board(b, depth + "_B");
                        childB.play(Board.Block.X, i % 3, i / 3);
                        int score = Math.max(maxV, minimax(childB, depth - 1, false));
                        if (score > maxV) {
                            maxV = score;
                            nMove = i;
                        }
                    }
                }
                // If there are no available moves, don't move
                if (nMove != -1) {
                    if (move(b, nMove % 3, nMove / 3)) {
                        gm.setTurn(selectedBlock == Board.Block.O);
                    }
                }
            } else {
                int nMove = -1;
                int minV = Integer.MAX_VALUE;
                for (int i = 0; i < 9; i++) {
                    if (b.getBlock(i) == Board.Block.EMPTY) {
                        Board childB = new Board(b, depth + "_B");
                        childB.play(Board.Block.O, i % 3, i / 3);
                        int score = Math.min(minV, minimax(childB, depth - 1, true));
                        if (score < minV) {
                            minV = score;
                            nMove = i;
                        }
                    }
                }
                // If there are no available moves, don't move
                if (nMove != -1) {
                    if (move(b, nMove % 3, nMove / 3)) {
                        gm.setTurn(selectedBlock == Board.Block.O);
                    }
                }
            }
        }
    }

    private int minimax(Board b, int depth, boolean isMaximizing) {
        Referee r = new Referee(b);
        if (depth == 0 || r.check() != null) {
                if (r.check() == null) {
                return (int) Math.random();
            }
            switch (r.check()) {
                case X:
                    return 100 + depth;
                case O:
                    return -100 - depth;
                case EMPTY:
                    return 0;
            }
        }
        if (isMaximizing) {
            int maxV = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (b.getBlock(i) == Board.Block.EMPTY) {
                    Board childB = new Board(b, depth + "_B");
                    childB.play(Board.Block.X, i % 3, i / 3);
                    maxV = Math.max(maxV, minimax(childB, depth - 1, false));
                }
            }
            return maxV;
        } else {
            int minV = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (b.getBlock(i) == Board.Block.EMPTY) {
                    Board childB = new Board(b, depth + "_B");
                    childB.play(Board.Block.O, i % 3, i / 3);
                    minV = Math.min(minV, minimax(childB, depth - 1, true));
                }
            }
            return minV;
        }
    }
}
