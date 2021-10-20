package my.app.goran.tutorial.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GraphicsUtils {

    public static void clearScreen(float red, float green, float blue, float alpha) {
        Gdx.gl.glClearColor(red, green, blue, alpha);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static void clearScreen() {
        clearScreen(0f, 0f, 0f, 1f);
    }

    public static void drawCircle(ShapeRenderer shapeRenderer, Circle circle, int segments) {
        shapeRenderer.circle(circle.x, circle.y, circle.radius, segments);
    }

    public static void renderShape(ShapeRenderer shapeRenderer, Runnable runnable) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        runnable.run();
        shapeRenderer.end();
    }

    public static void drawGrid(Viewport viewport, ShapeRenderer shapeRenderer) {
        drawGrid(viewport, shapeRenderer, 1);
    }

    public static void drawGrid(Viewport viewport, ShapeRenderer shapeRenderer, int cellSize) {
        Color oldColor = shapeRenderer.getColor().cpy();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float doubleWorldWidth = worldWidth * 2;
        float doubleWorldHeight = worldHeight * 2;


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // GRID
        shapeRenderer.setColor(Color.GREEN);

        float x = -doubleWorldWidth;

        while (x < doubleWorldWidth) {
            shapeRenderer.line(x, -doubleWorldHeight, x, doubleWorldHeight);
            x += cellSize;
        }

        float y = -doubleWorldHeight;

        while (y < doubleWorldHeight) {
            shapeRenderer.line(-doubleWorldHeight, y, doubleWorldHeight, y);
            y += cellSize;
        }

        // COORDINATES
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(0f, -doubleWorldHeight, 0f, doubleWorldHeight);
        shapeRenderer.line(-doubleWorldWidth, 0f, doubleWorldWidth, 0f);

        // BOUNDS
        shapeRenderer.line(0f, worldHeight, worldWidth, worldHeight);
        shapeRenderer.line(worldWidth, 0f, worldWidth, worldHeight);

        shapeRenderer.end();

        shapeRenderer.setColor(oldColor);
    }
}
