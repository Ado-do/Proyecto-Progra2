package geometricas;

public class Circle {
    public float x, y;
    protected int radius;
    protected int diam;

    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.diam = radius * 2;
    }

    public int getRadius() {
        return radius;
    }

    public float getArea() {
        return (float)(Math.PI * (radius * radius));
    }

    public float getPerimeter() {
        return (float)(2 * Math.PI * radius);
    }

    protected float distance(Circle c2) {
        float xSq = c2.x - x;
        float ySq = c2.y - y;
        float dist = (float)(Math.sqrt((xSq * xSq) + (ySq * ySq)));
        return dist;
    }
}
