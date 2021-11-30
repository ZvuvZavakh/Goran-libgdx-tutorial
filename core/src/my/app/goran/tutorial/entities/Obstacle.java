package my.app.goran.tutorial.entities;

public class Obstacle extends BaseEntity {

    public final static float BOUNDS_RADIUS = 0.3f;

    public Obstacle() {
        super();
        bounds.setRadius(BOUNDS_RADIUS);
    }

    private float ySpeed = 0.1f;

    public void update() {
        setY(y - ySpeed);
    }
}
