package my.app.goran.tutorial.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import my.app.goran.tutorial.assets.AssetsPaths;
import my.app.goran.tutorial.config.GameConfig;
import my.app.goran.tutorial.entities.Obstacle;
import my.app.goran.tutorial.entities.Player;
import my.app.goran.tutorial.utils.debug.DebugCameraController;
import my.app.goran.tutorial.utils.GraphicsUtils;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private Viewport viewport;
    private Viewport uiViewport;

    private ShapeRenderer shapeRenderer;
    private Player player;
    private DebugCameraController debugCameraController;

    private SpriteBatch batch;
    private BitmapFont uiFont;
    private GlyphLayout layout;

    private float obstacleTimer = 0f;
    private final Array<Obstacle> obstacles = new Array<>();

    private int lives = GameConfig.PLAYER_START_LIVES;
    private float scoreTimer = 0f;
    private int score = 0;
    private int displayScore = 0;

    private void renderUi() {
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();

        String livesText = String.format("LIVES: %d", lives);
        layout.setText(uiFont, livesText);
        uiFont.draw(batch, layout, 20f, GameConfig.HUD_HEIGHT - layout.height);

        String scoreText = String.format("SCORE: %d", displayScore);
        layout.setText(uiFont, scoreText);
        uiFont.draw(batch, layout, GameConfig.HUD_WIDTH - layout.width - 20f,
                GameConfig.HUD_HEIGHT - layout.height);

        batch.end();
    }

    private void update(float delta) {
        player.update();
        limitPlayerMovement();

        updateObstacles();
        createNewObstacle(delta);

        updateScore(delta);
        updateDisplayScore(delta);

        if (isPlayerCollidingWithObstacle()) {
            lives--;
        }
    }

    private void updateScore(float delta) {
        scoreTimer += delta;

        if (scoreTimer >= GameConfig.SCORE_MAX_TIME) {
            scoreTimer = 0f;
            score += 5;
        }
    }

    private void updateDisplayScore(float delta) {
        if (displayScore < score) {
            displayScore = Math.min(score, Math.round(displayScore + (30 * delta)));
        }
    }

    private boolean isGameOver() {
        return lives <= 0;
    }

    private boolean isPlayerCollidingWithObstacle() {
        for (Obstacle obstacle: obstacles) {
            if (!obstacle.isHit() && obstacle.isCollidingWith(player)) {
                return true;
            }
        }

        return false;
    }

    private void limitPlayerMovement() {
        float playerX = MathUtils.clamp(
                player.getX(), Player.BOUNDS_RADIUS, GameConfig.WORLD_WIDTH - Player.BOUNDS_RADIUS
        );

        player.setX(playerX);
    }

    private void updateObstacles() {
        obstacles.forEach(Obstacle::update);
    }

    private void createNewObstacle(float delta) {
        obstacleTimer += delta;

        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME) {
            obstacleTimer = 0f;

            float obstacleX = MathUtils.random(0f, GameConfig.WORLD_WIDTH);
            Obstacle obstacle = new Obstacle();

            obstacle.setPosition(obstacleX, GameConfig.WORLD_HEIGHT);
            obstacles.add(obstacle);
        }
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);

        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, uiCamera);

        shapeRenderer = new ShapeRenderer();

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        player = new Player();
        player.setPosition(GameConfig.WORLD_CENTER_X, 1f);

        batch = new SpriteBatch();
        uiFont = new BitmapFont(Gdx.files.internal(AssetsPaths.PRIMARY_FONT));
        layout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        debugCameraController.handleDebugInput();
        debugCameraController.applyTo(camera);

        if (!isGameOver()) {
            update(delta);
        }

        GraphicsUtils.clearScreen();
        shapeRenderer.setProjectionMatrix(camera.combined);

        GraphicsUtils.renderShape(shapeRenderer, new Runnable() {
            @Override
            public void run() {
                player.drawDebug(shapeRenderer);
                obstacles.forEach(obstacle -> obstacle.drawDebug(shapeRenderer));
            }
        });

        renderUi();
        GraphicsUtils.drawGrid(viewport, shapeRenderer);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        uiViewport.update(width, height, true);
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
        batch.dispose();
        uiFont.dispose();
    }
}
