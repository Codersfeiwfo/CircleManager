import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CircleData {
    private final DoubleProperty radius = new SimpleDoubleProperty();
    private final DoubleProperty area = new SimpleDoubleProperty();

    public CircleData(double radius) {
        setRadius(radius);
        setArea(Math.PI * radius * radius);
    }

    public double getRadius() {
        return radius.get();
    }

    public void setRadius(double value) {
        radius.set(value);
    }

    public DoubleProperty radiusProperty() {
        return radius;
    }

    public double getArea() {
        return area.get();
    }

    public void setArea(double value) {
        area.set(value);
    }

    public DoubleProperty areaProperty() {
        return area;
    }
}