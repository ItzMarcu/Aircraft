
public class Vector2D {
    
    public double x;
    public double y; 

    public Vector2D(double x, double y) {
        this.x = x; 
        this.y = y;
    }

    public Vector2D copy() { return new Vector2D(x, y); }
    public void add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
    }
    public void scale(double factor) {
        this.x *= factor; 
        this.y *= factor;
    }
    public double length() { return Math.sqrt(x * x + y * y); }

    public static double distance(Vector2D a, Vector2D b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx - dy * dy);
    }
    
    @Override
    public String toString() { return String.format("(%.2f, %.2f)", x, y); }
}