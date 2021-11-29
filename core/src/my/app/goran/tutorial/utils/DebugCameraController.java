package my.app.goran.tutorial.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class DebugCameraController {

    private final static float MOVE_SPEED = 20f;
    private final static int UP_KEY = Input.Keys.W;
    private final static int DOWN_KEY = Input.Keys.S;
    private final static int LEFT_KEY = Input.Keys.A;
    private final static int RIGHT_KEY = Input.Keys.D;

    private final static float DEFAULT_ZOOM = 1f;
    private final static float ZOOM_SPEED = 2f;
    private final static int ZOOM_IN_KEY = Input.Keys.Z;
    private final static int ZOOM_OUT_KEY = Input.Keys.X;
    private final static float MAX_ZOOM_IN = 0.2f;
    private final static float MAX_ZOOM_OUT = 30f;

    private final static int LOG_KEY = Input.Keys.ENTER;
    private final static int RESET_KEY = Input.Keys.R;

    private final Vector2 position = new Vector2();
    private final Vector2 startPosition = new Vector2();
    private float zoom = DEFAULT_ZOOM;

    private void setZoom(float zoom) {
        this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
    }

    private void setPosition(float x, float y) {
        position.set(x, y);
    }

    private void moveCamera(float xSpeed, float ySpeed) {
        setPosition(position.x + xSpeed, position.y + ySpeed);
    }

    private void moveUp(float moveSpeed) {
        moveCamera(0f, moveSpeed);
    }

    private void moveDown(float moveSpeed) {
        moveCamera(0f, -moveSpeed);
    }

    private void moveLeft(float moveSpeed) {
        moveCamera(-moveSpeed, 0f);
    }

    private void moveRight(float moveSpeed) {
        moveCamera(moveSpeed, 0f);
    }

    private void reset() {
        setPosition(startPosition.x, startPosition.y);
        zoom = DEFAULT_ZOOM;
    }

    private void log() {
        System.out.printf("CameraX: %f, CameraY: %f, Zoom: %f%n", position.x, position.y, zoom);
    }

    private void updateZoom(float zoomSpeed) {
        setZoom(zoom + zoomSpeed);
    }

    private void handleMovement(float delta) {
        float moveSpeed = MOVE_SPEED * delta;

        if (Gdx.input.isKeyPressed(UP_KEY)) {
            moveUp(moveSpeed);
        } else if (Gdx.input.isKeyPressed(DOWN_KEY)) {
            moveDown(moveSpeed);
        }

        if (Gdx.input.isKeyPressed(LEFT_KEY)) {
            moveLeft(moveSpeed);
        } else if (Gdx.input.isKeyPressed(RIGHT_KEY)) {
            moveRight(moveSpeed);
        }
    }

    private void handleZoom(float delta) {
        float zoomSpeed = ZOOM_SPEED * delta;

        if (Gdx.input.isKeyPressed(ZOOM_IN_KEY)) {
            updateZoom(zoomSpeed);
        } else if (Gdx.input.isKeyPressed(ZOOM_OUT_KEY)) {
            updateZoom(-zoomSpeed);
        }
    }

    public void setStartPosition(float x, float y) {
        startPosition.set(x, y);
        setPosition(x, y);
    }

    public void applyTo(OrthographicCamera orthographicCamera) {
        orthographicCamera.position.set(position, 0f);
        orthographicCamera.zoom = zoom;
        orthographicCamera.update();
    }

    public void handleDebugInput() {
        if (Gdx.input.isKeyPressed(RESET_KEY)) {
            reset();
            return;
        }

        float delta = Gdx.graphics.getDeltaTime();

        handleMovement(delta);
        handleZoom(delta);

        if (Gdx.input.isKeyPressed(LOG_KEY)) {
            log();
        }
    }
}
