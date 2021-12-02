package my.app.goran.tutorial.entities;

import com.badlogic.gdx.math.Intersector;

public class Obstacle extends BaseEntity {

    private float ySpeed = 0.1f;
    private boolean hit = false;
    public final static float BOUNDS_RADIUS = 0.3f;

    public Obstacle() {
        super();
        bounds.setRadius(BOUNDS_RADIUS);
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
