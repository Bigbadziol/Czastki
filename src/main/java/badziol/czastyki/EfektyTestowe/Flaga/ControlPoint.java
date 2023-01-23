package badziol.czastyki.EfektyTestowe.Flaga;

public class ControlPoint {
    public float x;
    public float y;

    public ControlPoint(float  x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float  x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "ControlPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
