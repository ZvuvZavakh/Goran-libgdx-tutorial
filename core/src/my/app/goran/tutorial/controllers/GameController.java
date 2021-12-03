package my.app.goran.tutorial.controllers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import my.app.goran.tutorial.config.DifficultyLevel;
import my.app.goran.tutorial.config.GameConfig;
import my.app.goran.tutorial.entities.Obstacle;
import my.app.goran.tutorial.entities.Player;

public class GameController {

    private final Player player = new Player();
    private final float startPlayerX = GameConfig.WORLD_CENTER_X;
    private final float startPlayerY = 1f;

    private float obstacleTimer = 0f;
    private final Array<Obstacle> obstacles = new Array<>();
    private final Pool<Obstacle> obstaclePool = Pools.get(Obstacle.class, 20);

    private int lives = GameConfig.PLAYER_START_LIVES;
    private float scoreTimer = 0f;
    private int score = 0;
    private int displayScore = 0;

    private final DifficultyLevel difficultyLevel = DifficultyLevel.EASY;

    public GameController() {
        player.setPosition(startPlayerX, startPlayerY);
        player.setSize(Player.SIZE, Player.SIZE);
    }

    public void update(float delta) {
        if (isGameOver()) {
            return;
        }

        player.update();
        limitPlayerMovement();

        createNewObstacle(delta);
        updateObstacles();
        removeObstacles();

        updateScore(delta);
        updateDisplayScore(delta);

        if (isPlayerCollidingWithObstacle()) {
            lives--;
        }
    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    public Player getPlayer() {
        return player;
    }

    public int getLives() {
        return lives;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
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

            float obstacleX = MathUtils.random(Obstacle.BOUNDS_RADIUS, GameConfig.WORLD_WIDTH - Obstacle.BOUNDS_RADIUS);
            Obstacle obstacle = obstaclePool.obtain();

            obstacle.setPosition(obstacleX, GameConfig.WORLD_HEIGHT);
            obstacle.setSize(Obstacle.SIZE, Obstacle.SIZE);
            obstacle.setYSpeed(difficultyLevel.getSpeed());
            obstacles.add(obstacle);
        }
    }

    private void removeObstacles() {
        if (obstacles.size == 0) {
            return;
        }

        Obstacle obstacle = obstacles.first();
        float minObstacleY = - Obstacle.BOUNDS_RADIUS * 2;

        if (obstacle.getY() < minObstacleY) {
            obstacle.reset();
            obstaclePool.free(obstacle);
            obstacles.removeValue(obstacle, true);
        }
    }
}
