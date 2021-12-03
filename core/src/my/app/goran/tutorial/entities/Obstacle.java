package my.app.goran.tutorial.entities;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;

public class Obstacle extends BaseEntity implements Pool.Poolable {

    private float width = 0.6f;
    private float height = 0.6f;
    private float ySpeed = 0.1f;
    private boolean hit = false;
    public final static float BOUNDS_RADIUS = 0.3f;
    public final static float SIZE = BOUNDS_RADIUS * 2f;

    public Obstacle() {
        super();
        bounds.setRadius(BOUNDS_RADIUS);
    }

    @Override
    public void reset() {
        x = 0f;
        y = 0f;
        width = 1f;
        height = 1f;
        ySpeed = 0.1f;
        hit = false;
    }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isCollidingWith(BaseEntity entity) {
        boolean overlaps = Intersector.overlaps(entity.getBounds(), bounds);
        hit = overlaps;

        return overlaps;
    }

    public void update() {
        setY(y - ySpeed);
    }
}
