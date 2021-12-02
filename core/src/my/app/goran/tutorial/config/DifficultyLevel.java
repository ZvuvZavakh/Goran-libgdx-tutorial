package my.app.goran.tutorial.config;

public enum DifficultyLevel {

    EASY(GameConfig.EASY_OBSTACLE_SPEED),
    MEDIUM(GameConfig.MEDIUM_OBSTACLE_SPEED),
    HARD(GameConfig.HARD_OBSTACLE_SPEED);

    private final float speed;

    private DifficultyLevel(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
}
