import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bullet extends Entity {

    public  static final double   BULLET_SPEED = 10;
    private static final double   RADIUS = 3;
    private static final int      LIFETIME = 100;
    private int framesLeft;

    public Bullet(Vector2D position) {
        super(position, new Vector2D(0, -BULLET_SPEED), RADIUS);
        framesLeft = LIFETIME;
    }

    @Override
    public void update(int width, int height) {
        move();
        framesLeft--;

        if (framesLeft <= 0) {
            destroy();
            return;
        }

        wrapAround(width, height);
    }
    @Override
    public List<Vector2D> getShape() {
        return new ArrayList<>(
            List.of(
                new Vector2D(-3, -3), 
                new Vector2D(3, -3),
                new Vector2D(3, 3),
                new Vector2D(-3, 3)
            )
        );
    }
    @Override
    public Color getColor() { return Color.RED; }
    public void setFramesLeft(int frames) { framesLeft = frames; }
}