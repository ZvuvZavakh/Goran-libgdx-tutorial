package my.app.goran.tutorial.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import my.app.goran.tutorial.utils.GraphicsUtils;

public abstract class BaseEntity {

    public final static float BOUNDS_RADIUS = 0.4f;
    protected final static float SIZE = BOUNDS_RADIUS * 2;

    protected float x = 0f;
    protected float y = 0f;
    protected final Circle bounds = new Circle(x, y, BOUNDS_RADIUS);

    protected void updateBounds() {
        bounds.setPosition(x, y);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        GraphicsUtils.drawCircle(shapeRenderer, bounds, 30);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;

        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public Circle getBounds() {
        return bounds;
    }
}
