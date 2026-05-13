import java.awt.*;
import java.util.ArrayList;
import java.util.List; 

public class Ship extends Entity {
    private boolean turningLeft;
    private boolean turningRight;
    private int     shootTimer;
    private Color   color;
    private final double  angle;
    
    private static final int    RADIUS = 15;
    private static final int    BULLET_COUNTDOWN = 15;
    private static final double FRICTION = 0.98;
    private static final double SPEED = 2;

    public Ship(int screenWidth, int screenHeight) {
        super(new Vector2D(screenWidth/2, 9*screenHeight/10), new Vector2D(0, 0), RADIUS);
        this.angle = -Math.PI / 2;
        this.shootTimer = 0;
        this.color = Color.WHITE;
    }

    public void setTurningLeft(boolean v) { turningLeft = v; }
    public void setTurningRight(boolean v) { turningRight = v; }

    public Bullet shoot() {
        if (shootTimer > 0) return null;
        
        shootTimer = BULLET_COUNTDOWN;
        return new Bullet(getPosition(), true);
    }
    
    @Override
    public void update(int width, int height) {
        if (getPosition().x < 570)
            if (turningRight) getPosition().x += SPEED;

        if (getPosition().x > 30)
            if (turningLeft) getPosition().x -= SPEED;

        getVelocity().scale(FRICTION);

        move();

        if (shootTimer > 0) shootTimer -= 1;
        System.out.println(getPosition());
    }

    @Override
    public List<Vector2D> getShape() {
        return new ArrayList<>(
            List.of(
                new Vector2D(15, 0),
                new Vector2D(-10, -8),
                new Vector2D(-10, 8)
            )
        );
    }

    @Override
    public Color getColor() { return color; }
    @Override
    public double getAngle() { return angle; }
    public void setColor(Color color) { this.color = color; }
}