public class TestCircle {
    public static void main(String[] args){
        //Create 3 circle objects
        Circle C1 = new Circle(0, 0, 5);
        Circle C2 = new Circle(2, 3, 7);
        Circle C3 = new Circle(7,7, 2);

        //print our the results to see if there is any overlap
        System.out.println("Does Circle 1 overlap Circle 2: " + C1.overlaps(C2)); //true
        System.out.println("Does Circle 2 overlap Circle 3: " + C2.overlaps(C3)); //true
        System.out.println("Does Circle 1 overlap Circle 3: " + C1.overlaps(C3)); //false


    }
}
