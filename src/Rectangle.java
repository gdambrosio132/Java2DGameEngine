import java.util.Arrays;

public class Rectangle {
    //properties for our rectangle
    private double left;
    private double top;
    private double width;
    private double height;
    private double right;
    private double bottom;

    //default constructor
    public Rectangle(){
        setValues(0,0,0,0);
    }

    //parameterized constructor
    public Rectangle(double left, double top, double width, double height){
        setValues(left, top, width, height);
    }

    //set values for our rectangle - returns void
    public void setValues(double left, double top, double width, double height){
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.right = left + width;
        this.bottom = top + height;
    }

    //set the position of the rectangle, based off left and top, returns void
    public void setPosition(double left, double top){
        setValues(left, top, this.width, this.height);
    }

    //checks to see if rectangle overlaps another rectangle object - returns boolean value
    public boolean overlaps(Rectangle other){
        boolean noOverlap = (other.right <= this.left)
                            || (this.right <= other.left)
                            || (other.bottom <= this.top)
                            || (this.bottom <= other.top);
        return !noOverlap;
    }

    //set size for rectangle based off width and height input, returns void
    public void setSize(double width, double height){
        setValues(this.left, this.top, width, height);
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public double getLeft() {
        return left;
    }

    public double getTop() {
        return top;
    }

    public double getRight() {
        return right;
    }

    public double getBottom() {
        return bottom;
    }

    /*
     * getMinimumTranslationVector()
     * acquire minimum translation vector between two objects
     */
    public Vector getMinimumTranslationVector(Rectangle other){
        Vector[] differences = {
                new Vector(other.right - this.left, 0),
                new Vector(other.left - this.right, 0),
                new Vector(0, other.bottom - this.top),
                new Vector (0, other.top - this.bottom)
        };

        Arrays.sort(differences);
        return differences[0];
    }
}
