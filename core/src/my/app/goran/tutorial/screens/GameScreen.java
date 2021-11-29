package my.app.goran.tutorial.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import my.app.goran.tutorial.config.GameConfig;
import my.app.goran.tutorial.entities.Player;
import my.app.goran.tutorial.utils.DebugCameraController;
import my.app.goran.tutorial.utils.GraphicsUtils;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;
    private Player player;
    private DebugCameraController debugCameraController;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        player = new Player();
        player.setPosition(GameConfig.WORLD_CENTER_X, 1f);
    }

    @Override
    public void render(float delta) {
        debugCameraController.handleDebugInput();
        debugCameraController.applyTo(camera);
        player.update();

        GraphicsUtils.clearScreen();
        shapeRenderer.setProjectionMatrix(camera.combined);

        GraphicsUtils.renderShape(shapeRenderer, new Runnable() {
            @Override
            public void run() {
                player.drawDebug(shapeRenderer);
            }
        });

        GraphicsUtils.drawGrid(viewport, shapeRenderer);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        shapeRenderer.dispose();
    }
}
