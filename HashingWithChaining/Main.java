package hashing;
import java.util.Random;

public class Main{
	public static void main(String[] args) throws NoSuchElementException{
		HwC array = new HwC();
		Random generator = new Random();
		array.add(0, "'just in case'");
		for(int i = 0; i < 100; i++) {
			array.add(generator.nextInt(100), i);
		}
		System.out.println("the number of elements is " +array.sizeOf());
		System.out.println("measuring get execution time in nano seconds:");
		System.out.println("thje element is "+ array.get(0));
		for(int i = 0; i < 100; i++) {
			array.add(generator.nextInt(100), i);
		}
		System.out.println("measuring get execution time in nano seconds:");
		System.out.println("thje element is "+ array.get(0));
		System.out.println("the number of elements is " +array.sizeOf());
		System.out.println("checking if empty:\n"+ array.isEmpty());
		System.out.println("removing an element");
		array.remove(0);
		System.out.println("the number of elements is " +array.sizeOf());
	}
}


