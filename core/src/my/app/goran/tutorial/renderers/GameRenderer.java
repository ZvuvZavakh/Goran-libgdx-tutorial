package my.app.goran.tutorial.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import my.app.goran.tutorial.entities.Obstacle;
import my.app.goran.tutorial.entities.Player;
import my.app.goran.tutorial.utils.GraphicsUtils;
import my.app.goran.tutorial.utils.debug.DebugCameraController;

public class GameRenderer implements Disposable {

    private final GameController gameController;

    private final OrthographicCamera camera= new OrthographicCamera();
    private final OrthographicCamera uiCamera= new OrthographicCamera();
    private final Viewport viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
    private final Viewport uiViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, uiCamera);

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final DebugCameraController debugCameraController = new DebugCameraController();

    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont uiFont = new BitmapFont(Gdx.files.internal(AssetsPaths.PRIMARY_FONT_PATH));
    private final GlyphLayout layout = new GlyphLayout();

    private final Texture playerTexture = new Texture(Gdx.files.internal(AssetsPaths.PLAYER_TEXTURE_PATH));
    private final Texture obstacleTexture = new Texture(Gdx.files.internal(AssetsPaths.OBSTACLE_TEXTURE_PATH));
    private final Texture backgroundTexture = new Texture(Gdx.files.internal(AssetsPaths.BACKGROUND_TEXTURE_PATH));

    public GameRenderer(GameController gameController) {
        this.gameController = gameController;
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);
    }

    public void render() {
        debugCameraController.handleDebugInput();
        debugCameraController.applyTo(camera);

        GraphicsUtils.clearScreen();

        renderGameplay();
        renderDebug();
        renderUi();

        GraphicsUtils.drawGrid(viewport, shapeRenderer);
    }

    private void renderGameplay() {
        viewport.apply();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(backgroundTexture, 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        gameController.getPlayer().draw(batch, playerTexture);
        gameController.getObstacles().forEach(obstacle -> obstacle.draw(batch, obstacleTexture));

        batch.end();
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