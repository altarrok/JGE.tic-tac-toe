package tictactoe;

import engine.GameContainer;
import engine.Input;
import engine.Renderer;

import java.awt.*;
import java.util.ArrayList;

public class Player extends GameObject {
    protected ArrayList<Point> blocks = new ArrayList<>();
    protected Board.Block selectedBlock;

    /**
     * Default ctr
     * Player is X
     */
    public Player() {
        selectedBlock = Board.Block.X;
    }

    /**
     * Player is b
     * @param b => X or O && b != EMPTY
     */
    public Player(Board.Block b, String tag) {
        this.tag = tag;
        this.selectedBlock = b;
    }

    @Override
    public void update(GameContainer gc, float dt) {
        GameManager gm = (GameManager) gc.getGame();
        Board b = (Board) gm.getObject("Board");
        Input inp = gc.getInput();
        if (inp.isButtonDown(1)) {
            if (gm.isTurn() ^ selectedBlock == Board.Block.O) {
                int x = 3 * inp.getMouseX() / gc.getWidth();
                int y = 3 * inp.getMouseY() / gc.getHeight();
                // Cautions..
                if (x >= 3) x = 2;
                if (x < 0) x = 0;
                if (y >= 3) y = 2;
                if (y < 0) y = 0;
                if (move(b, x, y)) {
                    gm.setTurn(selectedBlock == Board.Block.O);
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        for (Point block : blocks) {
            r.drawText((selectedBlock == Board.Block.X ? "X" : "O"), block.x * (gc.getWidth() / 3) + (gc.getWidth() / 6) - 9, block.y * (gc.getHeight() / 3) + (gc.getHeight() / 6) - 11, 0xFFFFFFFF);
        }
    }

    /**
     * Makes the given move if its possible and adds to the blocks arr
     * Return true if move made
     * Return false otherwise
     */
    public boolean move(Board b, int x, int y) {
        if (b.play(selectedBlock, x, y)) {
            blocks.add(new Point(x, y));
            return true;
        }
        return false;
    }

    public void clear() {
        blocks.clear();
    }
}
