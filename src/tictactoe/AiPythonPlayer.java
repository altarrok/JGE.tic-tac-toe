package tictactoe;

import engine.GameContainer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Your python file name should be: tic-tac-toe-ai.py
 * Your python file should take input as a String in form:
 * EMPTY EMPTY EMPTY X O X EMPY EMTY EMPTY
 * Signifies board:
 * E E E
 * X 0 X
 * E E E
 * Your python file must print
 * x y
 */
public class AiPythonPlayer extends Player {
    private int depth;
    ProcessBuilder pb;
    Process p;
    BufferedReader in;
    boolean working = false;

    public AiPythonPlayer(Board.Block b, int depth) throws Exception {
        super(b, "AI");
        this.depth = depth;
        try{
            pb = new ProcessBuilder("python","src/ai/tic-tac-toe-ai.py");
        } catch (Exception k) {
            try {
            pb = new ProcessBuilder("python", "src/ai/tic-tac-toe-ai.py");
            } catch (Exception e) {
                throw new Exception();
            }
        }

    }

    @Override
    public void update(GameContainer gc, float dt) {
        GameManager gm = (GameManager) gc.getGame();
        Board b = (Board) gm.getObject("Board");
    if (gm.isTurn() ^ selectedBlock == Board.Block.O && b != null) {
      try {
        if (!working) {
            String board = "";
            for (Board.Block s : b) {
                board += s.toString() + " ";
            }
            board = board.substring(0, board.length() - 2);
            working = true;
            try{
                pb = new ProcessBuilder("python","src/ai/tic-tac-toe-ai.py", board);
            } catch (Exception k) {
                try {
                    pb = new ProcessBuilder("python", "src/ai/tic-tac-toe-ai.py", board);
                } catch (Exception e) {
                    throw new Exception();
                }
            }
          p = pb.start();
          in = new BufferedReader(new InputStreamReader(p.getInputStream()));
          String next = in.readLine();
          int x = Integer.parseInt(next.substring(0,1));
          int y = Integer.parseInt(next.substring(2,3));
            if (move(b, x, y)) {
                gm.setTurn(selectedBlock == Board.Block.O);
            }
          working = false;
        }
      } catch (Exception e) {
        System.out.println("error" + e);
      }
        }
    }
}
