public class TestRectangle {
    public static void main(String[] args){
        Rectangle r1 = new Rectangle(0,0,9,7);
        Rectangle r2 = new Rectangle(7,4,11,6);
        Rectangle r3 = new Rectangle(15,2,4,6);

        System.out.println(r1.overlaps(r2)); //true
        System.out.println(r2.overlaps(r3)); //true
        System.out.println(r3.overlaps(r1)); //false
    }
}
