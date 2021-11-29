package my.app.goran.tutorial.utils.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class DebugCameraController {

    private final Vector2 position = new Vector2();
    private final Vector2 startPosition = new Vector2();
    private final DebugCameraConfig config = new DebugCameraConfig();
    private float zoom = config.getDefaultZoom();

    private void setZoom(float zoom) {
        this.zoom = MathUtils.clamp(zoom, config.getMaxZoomIn(), config.getMaxZoomOut());
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
        zoom = config.getDefaultZoom();
    }

    private void log() {
        System.out.printf("CameraX: %f, CameraY: %f, Zoom: %f%n", position.x, position.y, zoom);
    }

    private void updateZoom(float zoomSpeed) {
        setZoom(zoom + zoomSpeed);
    }

    private void handleMovement(float delta) {
        float moveSpeed = config.getMoveSpeed() * delta;

        if (config.isUpPressed()) {
            moveUp(moveSpeed);
        } else if (config.isDownPressed()) {
            moveDown(moveSpeed);
        }

        if (config.isLeftPressed()) {
            moveLeft(moveSpeed);
        } else if (config.isRightPressed()) {
            moveRight(moveSpeed);
        }
    }

    private void handleZoom(float delta) {
        float zoomSpeed = config.getZoomSpeed() * delta;

        if (config.isZoomInPressed()) {
            updateZoom(zoomSpeed);
        } else if (config.isZoomOutPressed()) {
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
        if (config.isResetPressed()) {
            reset();
            return;
        }

        float delta = Gdx.graphics.getDeltaTime();

        handleMovement(delta);
        handleZoom(delta);

        if (config.isLogPressed()) {
            log();
        }
    }
}
