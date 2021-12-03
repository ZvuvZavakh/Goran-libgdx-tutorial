package my.app.goran.tutorial.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import my.app.goran.tutorial.utils.GraphicsUtils;

public abstract class BaseEntity {

    public final static float BOUNDS_RADIUS = 0.4f;
    protected final static float SIZE = BOUNDS_RADIUS * 2;

    protected float x = 0f;
    protected float y = 0f;
    protected final Circle bounds = new Circle(x, y, BOUNDS_RADIUS);
    protected float width = 1f;
    protected float height = 1f;

    protected void updateBounds() {
        bounds.setPosition(x + width / 2f, y + height / 2f);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        GraphicsUtils.drawCircle(shapeRenderer, bounds, 30);
    }

    public void draw(Batch batch, Texture texture) {
        batch.draw(texture, x, y, width, height);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;

        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;

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

    public void setWidth(float width) {
        this.width = width;
        updateBounds();
    }

    public void setHeight(float height) {
        this.height = height;
        updateBounds();
    }
}
