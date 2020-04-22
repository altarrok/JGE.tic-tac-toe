package tictactoe;

import engine.GameContainer;
import engine.Renderer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Board extends GameObject implements Iterable<Board.Block> {
    enum Block {
        X, O, EMPTY;
    }

    private Block[] boardArr = new Block[9];

    /**
     * Default ctr
     */
    public Board() {
        this.tag = "Board";
        clear();
    }

    /**
     * Copy ctr
     */
    public Board(Board othBoard, String tag) {
        this.tag = tag;
        for (int i = 0; i < boardArr.length; i++) {
            boardArr[i] = othBoard.getBlock(i);
        }
    }

    @Override
    public void update(GameContainer gc, float dt) {

    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        //Drawing vertical lines
        for(int y = 0; y < gc.getHeight(); y++) {
            r.setPixel(gc.getWidth() / 3, y, 0xFFFFFFFF);
            r.setPixel(2 * (gc.getWidth() / 3), y, 0xFFFFFFFF);
        }

        //Drawing horizontal lines
        for (int x = 0; x < gc.getWidth(); x++) {
            r.setPixel(x, gc.getHeight() / 3, 0xFFFFFFFF);
            r.setPixel(x, 2 * (gc.getHeight() / 3), 0xFFFFFFFF);
        }
    }

    /**
     * If given move is playable, plays it => return true
     * else return false
     */
    public boolean play(Block b, int x, int y) {
        if (boardArr[x + y * 3] == Block.EMPTY) {
            boardArr[x + y * 3] = b;
            return true;
        }
        return false;
    }

    public void print() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                System.out.print((boardArr[x + y * 3] == Block.EMPTY ? "E" : boardArr[x + y * 3]) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Clears the board
     */
    public void clear() {
        for (int i = 0; i < boardArr.length; i++) {
            boardArr[i] = Block.EMPTY;
        }
    }

    /**
     * Indexed block getter
     */
    public Block getBlock(int i) {
        return boardArr[i];
    }

    /**
     * Cartesian block getter
     */
    public Block getBlock(int x, int y) {
        return boardArr[x + y * 3];
    }


    @Override
    public Iterator<Block> iterator() {
        List<Block> l = Arrays.asList(boardArr);
        return l.iterator();
    }
}
