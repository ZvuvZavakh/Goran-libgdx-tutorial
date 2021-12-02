package my.app.goran.tutorial.screens;

import com.badlogic.gdx.Screen;
import my.app.goran.tutorial.controllers.GameController;
import my.app.goran.tutorial.renderers.GameRenderer;

public class GameScreen implements Screen {

    private GameController gameController;
    private GameRenderer gameRenderer;

    @Override
    public void show() {
        gameController = new GameController();
        gameRenderer = new GameRenderer(gameController);
    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }
}
