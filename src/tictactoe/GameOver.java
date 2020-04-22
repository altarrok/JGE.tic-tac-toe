package tictactoe;

import engine.GameContainer;
import engine.Renderer;

public class GameOver extends GameObject{
    Board.Block block;

    public GameOver(Board.Block b) {
        this.tag = "go";
        this.block = b;
    }

    @Override
    public void update(GameContainer gc, float dt) {
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.fillRect(gc.getWidth() / 2 - 40, gc.getHeight() / 2 - 40, 80, 60, 0xFFFFFFFF);
        if (block == Board.Block.EMPTY) {
            //DRAW
            r.drawText("DRAW", gc.getWidth() / 2 - 36, gc.getHeight() / 2 - 20, 0xFF00FFFF);
        } else {
            r.drawText(block.toString(), gc.getWidth() / 2 - 10, gc.getHeight() / 2 - 36, 0xFF0000FF);
            r.drawText("WON!", gc.getWidth() / 2 - 30, gc.getHeight() / 2 - 10, 0xFF00FFFF);

        }
    }
}
