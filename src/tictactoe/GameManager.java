package tictactoe;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameManager extends AbstractGame {
    /**
     * GAME CONSTANTS =========================
     */
    private static final int tileSize = 96;
    private static final int tiledWidth = 1;
    private static final int tiledHeight = 1;
    private static final int width = tileSize*tiledWidth;
    private static final int height = tileSize*tiledHeight;
    private static final float scale = 8f;
    private static final double wantedFps = 240.0;


    private ArrayList<GameObject> objects = new ArrayList<>();
    // if true => turn of x
    private boolean turn = true;
    private boolean playing = false;
    private Referee ref;

    public GameManager() {
        objects.add(new Menu());
    }

    @Override
    public void update(GameContainer gc, float dTime) {
        if (gc.getInput().isKeyDown(KeyEvent.VK_ESCAPE) && getObject("Menu") == null) {
            getObject("Board").setDead(true);
            getObject("Player").setDead(true);
            getObject("AI").setDead(true);
            playing = false;
            objects.add(new Menu());
            turn = true;
        }
        for (int i = 0; i < objects.size(); i++) {
            // Updating objects
            objects.get(i).update(gc, dTime);
            // Removing objects
            if (objects.get(i).isDead()) {
                objects.remove(i);
                i--;
            }
        }
        if (getObject("go") != null) {
            getObject("go").setDead(true);
            gc.sleep(1000);
            objects.add(new Menu());
        }
        if (getObject("Menu") != null) {
            Menu menu = (Menu) getObject("Menu");
            if (menu.chosen != Board.Block.EMPTY) {
                // Player chosen
                objects.add(new Board());
                objects.add(new Player(menu.chosen, "Player"));
                // ## AI PLAYER INIT ##
                try {
                    GameObject aiPlayer = new AiPythonPlayer(menu.chosen == Board.Block.X ? Board.Block.O : Board.Block.X, 9);
                    objects.add(aiPlayer);
                } catch (Exception e) {
                    objects.add(new AIPlayer(menu.chosen == Board.Block.X ? Board.Block.O : Board.Block.X, 9));
                }
                ref = new Referee((Board) getObject("Board"));
                menu.setDead(true);
                playing = true;
            }
        }
        if (playing) {
            gc.getRenderer().setAmbientColor(0xFF000000);
            Board.Block decision = ref.check();
            if (decision != null) {
                // Game Over..
                // decision == winner
                // if draw decision == EMPTY
                getObject("Board").setDead(true);
                getObject("Player").setDead(true);
                getObject("AI").setDead(true);
                playing = false;
                objects.add(new GameOver(decision));
                turn = true;
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
        for (GameObject object : objects) {
            object.render(gc, renderer);
        }
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.setWidth(width);
        gc.setHeight(height);
        gc.setScale(scale);
        gc.setFPS(wantedFps);
        gc.setDrawFPS(false);
        gc.setTileSize(tileSize);
        gc.start();
    }

    public GameObject getObject(String tag) {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getTag().equals(tag)) {
                return objects.get(i);
            }
        }
        return null;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
