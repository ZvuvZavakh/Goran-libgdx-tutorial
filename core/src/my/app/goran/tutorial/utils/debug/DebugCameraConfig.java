package my.app.goran.tutorial.utils.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class DebugCameraConfig {

    private final static String FILE_PATH = "debug/camera-config.json";

    private final static String MOVE_SPEED = "MOVE_SPEED";
    private final static String UP_KEY = "UP_KEY";
    private final static String DOWN_KEY = "DOWN_KEY";
    private final static String LEFT_KEY = "LEFT_KEY";
    private final static String RIGHT_KEY = "RIGHT_KEY";

    private final static String DEFAULT_ZOOM = "DEFAULT_ZOOM";
    private final static String ZOOM_SPEED = "ZOOM_SPEED";
    private final static String ZOOM_IN_KEY = "ZOOM_IN_KEY";
    private final static String ZOOM_OUT_KEY = "ZOOM_OUT_KEY";
    private final static String MAX_ZOOM_IN = "MAX_ZOOM_IN";
    private final static String MAX_ZOOM_OUT = "MAX_ZOOM_OUT";

    private final static String LOG_KEY = "LOG_KEY";
    private final static String RESET_KEY = "RESET_KEY";

    private final static float DEFAULT_MOVE_SPEED = 20f;
    private final static int DEFAULT_UP_KEY = Input.Keys.W;
    private final static int DEFAULT_DOWN_KEY = Input.Keys.S;
    private final static int DEFAULT_LEFT_KEY = Input.Keys.A;
    private final static int DEFAULT_RIGHT_KEY = Input.Keys.D;

    private final static float DEFAULT_DEFAULT_ZOOM = 1f;
    private final static float DEFAULT_ZOOM_SPEED = 2f;
    private final static int DEFAULT_ZOOM_IN_KEY = Input.Keys.Z;
    private final static int DEFAULT_ZOOM_OUT_KEY = Input.Keys.X;
    private final static float DEFAULT_MAX_ZOOM_IN = 0.2f;
    private final static float DEFAULT_MAX_ZOOM_OUT = 30f;

    private final static int DEFAULT_LOG_KEY = Input.Keys.ENTER;
    private final static int DEFAULT_RESET_KEY = Input.Keys.R;

    private final FileHandle fileHandle = Gdx.files.internal(FILE_PATH);

    private float moveSpeed = DEFAULT_MOVE_SPEED;
    private int upKey = DEFAULT_UP_KEY;
    private int downKey = DEFAULT_DOWN_KEY;
    private int leftKey = DEFAULT_LEFT_KEY;
    private int rightKey = DEFAULT_RIGHT_KEY;

    private float defaultZoom = DEFAULT_DEFAULT_ZOOM;
    private float zoomSpeed = DEFAULT_ZOOM_SPEED;
    private int zoomInKey = DEFAULT_ZOOM_IN_KEY;
    private int zoomOutKey = DEFAULT_ZOOM_OUT_KEY;
    private float maxZoomIn = DEFAULT_MAX_ZOOM_IN;
    private float maxZoomOut = DEFAULT_MAX_ZOOM_OUT;

    private int logKey = DEFAULT_LOG_KEY;
    private int resetKey = DEFAULT_RESET_KEY;

    public DebugCameraConfig() {
        if (fileHandle.exists()) {
            load();
        } else {
            System.out.println("Debug config file not found, loading default configuration...");

            setupDefaults();
        }

        System.out.println(toString());
    }

    private int getInputKeyValue(JsonValue root, String key, int defaultInputKey) {
        String keyString = root.getString(key, Input.Keys.toString(defaultInputKey));

        return Input.Keys.valueOf(keyString);
    }

    private void load() {
        try {
            JsonValue root = new JsonReader().parse(fileHandle);

            moveSpeed = root.getFloat(MOVE_SPEED, DEFAULT_MOVE_SPEED);
            upKey = getInputKeyValue(root, UP_KEY, DEFAULT_UP_KEY);
            downKey = getInputKeyValue(root, DOWN_KEY, DEFAULT_DOWN_KEY);
            leftKey = getInputKeyValue(root, LEFT_KEY, DEFAULT_LEFT_KEY);
            rightKey = getInputKeyValue(root, RIGHT_KEY, DEFAULT_RIGHT_KEY);

            defaultZoom = root.getFloat(DEFAULT_ZOOM, DEFAULT_DEFAULT_ZOOM);
            zoomSpeed = root.getFloat(ZOOM_SPEED, DEFAULT_ZOOM_SPEED);
            zoomInKey = getInputKeyValue(root, ZOOM_IN_KEY, DEFAULT_ZOOM_IN_KEY);
            zoomOutKey = getInputKeyValue(root, ZOOM_OUT_KEY, DEFAULT_ZOOM_OUT_KEY);
            maxZoomIn = root.getFloat(MAX_ZOOM_IN, DEFAULT_MAX_ZOOM_IN);
            maxZoomOut = root.getFloat(MAX_ZOOM_OUT, DEFAULT_MAX_ZOOM_OUT);

            logKey = getInputKeyValue(root, LOG_KEY, DEFAULT_LOG_KEY);
            resetKey = getInputKeyValue(root, RESET_KEY, DEFAULT_RESET_KEY);

            System.out.println("Config loaded...");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Invalid config file not found, loading default configuration...");

            setupDefaults();
        }
    }

    private void setupDefaults() {
        moveSpeed = DEFAULT_MOVE_SPEED;
        upKey = DEFAULT_UP_KEY;
        downKey = DEFAULT_DOWN_KEY;
        leftKey = DEFAULT_LEFT_KEY;
        rightKey = DEFAULT_RIGHT_KEY;

        defaultZoom = DEFAULT_DEFAULT_ZOOM;
        zoomSpeed = DEFAULT_ZOOM_SPEED;
        zoomInKey = DEFAULT_ZOOM_IN_KEY;
        zoomOutKey = DEFAULT_ZOOM_OUT_KEY;
        maxZoomIn = DEFAULT_MAX_ZOOM_IN;
        maxZoomOut = DEFAULT_MAX_ZOOM_OUT;

        logKey = DEFAULT_LOG_KEY;
        resetKey = DEFAULT_RESET_KEY;
    }

    public float getDefaultZoom() {
        return defaultZoom;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getZoomSpeed() {
        return zoomSpeed;
    }

    public float getMaxZoomIn() {
        return maxZoomIn;
    }

    public float getMaxZoomOut() {
        return maxZoomOut;
    }

    public boolean isUpPressed() {
        return Gdx.input.isKeyPressed(upKey);
    }

    public boolean isDownPressed() {
        return Gdx.input.isKeyPressed(downKey);
    }

    public boolean isLeftPressed() {
        return Gdx.input.isKeyPressed(leftKey);
    }

    public boolean isRightPressed() {
        return Gdx.input.isKeyPressed(rightKey);
    }

    public boolean isZoomInPressed() {
        return Gdx.input.isKeyPressed(zoomInKey);
    }

    public boolean isZoomOutPressed() {
        return Gdx.input.isKeyPressed(zoomOutKey);
    }

    public boolean isLogPressed() {
        return Gdx.input.isKeyPressed(logKey);
    }

    public boolean isResetPressed() {
        return Gdx.input.isKeyPressed(resetKey);
    }

    @Override
    public String toString() {
        return "moveSpeed: " + moveSpeed + "\n" +
                "upKey: " + upKey + "\n" +
                "downKey: " + downKey + "\n" +
                "leftKey: " + leftKey + "\n" +
                "rightKey: " + rightKey + "\n" +
                "defaultZoom: " + defaultZoom + "\n" +
                "zoomSpeed: " + zoomSpeed + "\n" +
                "zoomInKey: " + zoomInKey + "\n" +
                "zoomOutKey: " + zoomOutKey + "\n" +
                "maxZoomIn: " + maxZoomIn + "\n" +
                "maxZoomOut: " + maxZoomOut + "\n" +
                "logKey: " + logKey + "\n" +
                "resetKey: " + resetKey + "\n";
    }
}
