import java.awt.*;
import java.util.List;

public abstract class Entity {

    private Vector2D position;
    private Vector2D velocity;
    private double   radius;
    private boolean  alive;

    public Entity(Vector2D position, Vector2D velocity, double radius) {
        this.position = position;
        this.velocity = velocity; 
        this.radius = radius;
        this.alive = true;
    }

    public abstract void update(int width, int height);
    public abstract List<Vector2D> getShape();
    public abstract Color getColor();

    public double getAngle() { return 0; }
    public void move() { position.add(velocity); }
    public boolean collidesWith(Entity other) { return Vector2D.distance(this.position, other.position) < (this.radius + other.radius); }
    public boolean isAlive() { return alive; }
    public Vector2D getPosition() { return position; }
    public Vector2D getVelocity() { return velocity; }
    public double getRadius() { return radius; }
    public void destroy() { this.alive = false; }

    protected void wrapAround(int width, int height) {
        if (position.x < 0)       position.x += width;
        if (position.x > width)   position.x -= width;
        if (position.y < 0)       position.y += height;
        if (position.y > height)  position.y -= height;
    }

    public final void draw(Graphics g) {
        List<Vector2D> shape = getShape();
        int n = shape.size();
        int[] px = new int[n];
        int[] py = new int[n];

        double angle = getAngle();
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        for (int i = 0; i < n; i++) {
            Vector2D v = shape.get(i);

            px[i] = (int) Math.round(position.x + v.x * cos - v.y * sin);
            py[i] = (int) Math.round(position.y + v.x * sin + v.y * cos);
        }

        g.setColor(getColor());
        g.drawPolygon(px, py, n);
    }

}