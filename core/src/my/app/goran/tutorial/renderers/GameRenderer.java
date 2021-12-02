package my.app.goran.tutorial.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import my.app.goran.tutorial.assets.AssetsPaths;
import my.app.goran.tutorial.config.GameConfig;
import my.app.goran.tutorial.controllers.GameController;
import my.app.goran.tutorial.utils.GraphicsUtils;
import my.app.goran.tutorial.utils.debug.DebugCameraController;

public class GameRenderer implements Disposable {

    private final GameController gameController;

    private final OrthographicCamera camera;
    private final OrthographicCamera uiCamera;
    private final Viewport viewport;
    private final Viewport uiViewport;

    private final ShapeRenderer shapeRenderer;
    private final DebugCameraController debugCameraController;

    private final SpriteBatch batch;
    private final BitmapFont uiFont;
    private final GlyphLayout layout;

    public GameRenderer(GameController gameController) {
        this.gameController = gameController;

        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);

        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, uiCamera);

        shapeRenderer = new ShapeRenderer();

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        batch = new SpriteBatch();
        uiFont = new BitmapFont(Gdx.files.internal(AssetsPaths.PRIMARY_FONT));
        layout = new GlyphLayout();
    }

    public void render() {
        debugCameraController.handleDebugInput();
        debugCameraController.applyTo(camera);

        GraphicsUtils.clearScreen();
        renderDebug();
        renderUi();
        GraphicsUtils.drawGrid(viewport, shapeRenderer);
    }

    private void renderDebug() {
        viewport.apply();
        shapeRenderer.setProjectionMatrix(camera.combined);

        GraphicsUtils.renderShape(shapeRenderer, () -> {
            gameController.getPlayer().drawDebug(shapeRenderer);
            gameController.getObstacles().forEach(obstacle -> obstacle.drawDebug(shapeRenderer));
        });
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        uiViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        uiFont.dispose();
    }

    private void renderUi() {
        uiViewport.apply();
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();

        String livesText = String.format("LIVES: %d", gameController.getLives());
        layout.setText(uiFont, livesText);
        uiFont.draw(batch, layout, 20f, GameConfig.HUD_HEIGHT - layout.height);

        String scoreText = String.format("SCORE: %d", gameController.getDisplayScore());
        layout.setText(uiFont, scoreText);
        uiFont.draw(batch, layout, GameConfig.HUD_WIDTH - layout.width - 20f,
                GameConfig.HUD_HEIGHT - layout.height);

        batch.end();
    }
}