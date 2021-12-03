package my.app.goran.tutorial.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import my.app.goran.tutorial.utils.GraphicsUtils;

public class Player extends BaseEntity {

    public final static float BOUNDS_RADIUS = 0.4f;
    public final static float SIZE = BOUNDS_RADIUS * 2f;
    private static final float MAX_X_SPEED = 0.2f;

    public Player() {
        super();
        bounds.setRadius(BOUNDS_RADIUS);
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
