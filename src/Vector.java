public class Vector implements Comparable<Vector> {
    public double x;
    public double y;

    public Vector() {
        setValues(0,0);
    }

    public Vector(double x, double y) {
        setValues(x, y);
    }

    public void setValues(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void addVector(Vector other){
        this.x += other.x;
        this.y += other.y;
    }

    public void addValues(double dx, double dy){
        this.x += dx;
        this.y += dy;
    }

    public void multiply(double scalar){
        this.x *= scalar;
        this.y *= scalar;
    }

    public double getLength(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    //note: this returns angles in degrees
    public double getAngle(){
        if (getLength() == 0){
            return 0;
        } else {
            return Math.toDegrees(Math.atan2(this.y, this.x));
        }

    }

    public void setLength(double length){
        double angleDeg = this.getAngle();
        double angleRad = Math.toRadians(angleDeg);
        this.x = length * Math.cos(angleRad);
        this.y = length * Math.sin(angleRad);
    }

    public void setAngle(double angleDeg){
        double length = this.getLength();
        double angleRad = Math.toRadians(angleDeg);
        this.x = length * Math.cos(angleRad);
        this.y = length * Math.sin(angleRad);
    }

    public String toString(){
        return "< " + this.x + " , " + this.y + " >";
    }

    public int compareTo(Vector other){
        if (this.getLength() < other.getLength()){
            return -1;
        } else if (this.getLength() > other.getLength()){
            return 1;
        } else {
            return 0;
        }
    }
}
