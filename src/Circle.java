public class Circle {
    //properties of a circle
    private double centerX;
    private double centerY;
    private double radius;

    //default constructor
    public Circle(){
        setValues(0, 0, 0);
    }

    //parameterized constructor
    public Circle(double centerX, double centerY, double radius){
        setValues(centerX, centerY, radius);
    }

    //setValues function
    public void setValues(double centerX, double centerY, double radius){
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    //set new center values
    public void setCenter(double centerX, double centerY){
        setValues(centerX, centerY, this.radius);
    }

    //set new radius values
    public void setRadius(double radius){
        setValues(this.centerX, this.centerY, radius);
    }

    //overlaps function - checks to see if two circles overlap
    //sqrt((x_{2} - x_{1})^{2} + (y_{2} - y_{1})^{2}) < r_{2} + r_{1} <- if true, then there is overlap, else: no
    public boolean overlaps(Circle other){
        boolean overlap = Math.sqrt(Math.pow(other.centerX - this.centerX, 2) + Math.pow(other.centerY - this.centerY, 2)) < other.radius + this.radius;
        return overlap;
    }

}
