package my.app.goran.tutorial.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import my.app.goran.tutorial.utils.GraphicsUtils;

public class Player {

    private final float BOUNDS_RADIUS = 0.4f;
    private final float SIZE = BOUNDS_RADIUS * 2;
    private final float MAX_X_SPEED = 0.2f;

    private float x = 0f;
    private float y = 0f;

    private final Circle bounds;

    public Player() {
        bounds = new Circle(x, y, BOUNDS_RADIUS);
    }

    public Circle getBounds() {
        return bounds;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;

        updateBounds();
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        GraphicsUtils.drawCircle(shapeRenderer, bounds, 30);
    }

    private void updateBounds() {
        bounds.setPosition(x, y);
    }

    public void update() {
        float xSpeed = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xSpeed = MAX_X_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xSpeed = -MAX_X_SPEED;
        }

        x += xSpeed;
        updateBounds();
    }
}
