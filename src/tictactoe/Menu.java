package tictactoe;

import engine.GameContainer;
import engine.Input;
import engine.Renderer;

public class Menu extends GameObject {
    public Board.Block chosen = Board.Block.EMPTY;

    public Menu() {
        this.tag = "Menu";
    }

    @Override
    public void update(GameContainer gc, float dt) {
        Input input = gc.getInput();
        if (input.isButtonDown(1)) {
            int x = input.getMouseX();
            int y = input.getMouseY();
            if (x >= gc.getWidth() /2 - 30 && x <= gc.getWidth() / 2 - 15 && y >= gc.getHeight() / 2 && y <= gc.getHeight() / 2 + 22 ) {
                // CHOSE X
                chosen = Board.Block.X;
            }
            if (x >= gc.getWidth() /2 + 10 && x <= gc.getWidth() / 2 + 27 && y >= gc.getHeight() / 2 && y <= gc.getHeight() / 2 + 22 ) {
                // CHOSE O
                chosen = Board.Block.O;
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.setAmbientColor(0xFFFFFFFF);
        r.drawText("Choose", gc.getWidth() / 2 - 43, gc.getHeight() / 2 - 45, 0xFF000000);

        // X button
        r.drawText("X", gc.getWidth() / 2 - 30, gc.getHeight() / 2, 0xFF000000);
        r.drawRect(gc.getWidth() / 2 - 30, gc.getHeight() / 2, 15, 22, 0xFF000000);

        // O button
        r.drawText("O", gc.getWidth() / 2 + 10, gc.getHeight() / 2, 0xFF000000);
        r.drawRect(gc.getWidth() / 2 + 10, gc.getHeight() / 2, 17, 22, 0xFF000000);
    }
}
