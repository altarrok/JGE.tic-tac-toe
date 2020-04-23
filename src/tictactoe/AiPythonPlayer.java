package tictactoe;

import engine.GameContainer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Your pyhonn file should react if no additional args passed:
 * pyhon tic-tac-toe-ai.py SHOULD WORK
 * Your python file name should be: tic-tac-toe-ai.py
 * Your python file should take input as a String in form:
 * EMPTY EMPTY EMPTY X O X EMPY EMPTY EMPTY
 * Signifies board:
 * E E E
 * X 0 X
 * E E E
 * Your python file must print
 * x y
 * And a String:
 * X or O
 * Signifying Turn
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
            board = board.substring(0, board.length() - 1);
          System.out.println(board);
            working = true;
            try{
                pb = new ProcessBuilder("python","src/ai/tic-tac-toe-ai.py", board, selectedBlock.toString());
            } catch (Exception k) {
                try {
                    pb = new ProcessBuilder("python", "src/ai/tic-tac-toe-ai.py", board, selectedBlock.toString());
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
