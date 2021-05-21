public class TestVector {
    public static void main(String[] args){
        System.out.println("Testing the vector class...");

        //Create new Vector Objects, A & B
        Vector a = new Vector(5, 6);
        Vector b = new Vector(7, 8);

        System.out.println(a); // <5, 6>
        System.out.println(b); // <7, 8>

        //Add vector B to vector A
        a.addVector(b); // <12, 14>

        //Output the results
        System.out.println(a);
    }
}
