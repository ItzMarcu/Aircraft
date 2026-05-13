import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {
    private int shootTimer;

    private static final Vector2D VELOCITY = new Vector2D(1.2, -0.65);
    private static final double   RADIUS = 25;
    private static final int      BULLET_COOLDOWN = 120;
    private static final int      ENEMY_SCORE = 75;
    private static final Random   rng = new Random();

    public Enemy(Vector2D position) {
        super(position, VELOCITY, RADIUS);
        this.shootTimer = 0;
    }
    
    public static Enemy spawnRandom(int screenWidth, int screenHeight) {
        int x = rng.nextInt(0, screenWidth);
        int y = rng.nextInt(screenHeight/2, screenHeight - 20);

        Vector2D randomPosition = new Vector2D(x, y);
        return new Enemy(randomPosition);
    }

    public Bullet shoot() {
        if (shootTimer > 0) return null;
        shootTimer = BULLET_COOLDOWN; 
        return new Bullet(getPosition().copy(), new Vector2D(0, VELOCITY.y));
    } 

    @Override
    public void update(int width, int height) {
        move();
        wrapAround(width, height);
        if (shootTimer > 0) shootTimer--;
    }


    @Override
    public List<Vector2D> getShape() {
        return new ArrayList<>(
            List.of(
                new Vector2D(0, rng.nextInt(20, 25)),
                new Vector2D(0, -rng.nextInt(20, 25)), 
                new Vector2D(rng.nextInt(20, 25), 0), 
                new Vector2D(-rng.nextInt(20, 25), 0)
            )
        );
    }
    @Override
    public Color getColor() { return Color.RED; }
    public int getScore() { return ENEMY_SCORE; }
}