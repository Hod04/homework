package alp;

public class Main{
	public static void main(String[] args) throws EmptyQueueException{
		DoubleEndedDynamicArr queue = new DoubleEndedDynamicArr();
		DoubleEndedList listo = new DoubleEndedList();
		System.out.println("testing the first implementation:\n");
		System.out.println("enquing 20 elements in each side of the queue\n");
		for(int i = 0; i < 10; i++){
			queue.enqueueR(5);
			queue.enqueueR(2);
			queue.enqueueL(5);
			queue.enqueueL(2);
		}

		queue.sizeOfL();
		queue.sizeOfR();

		System.out.println("\ndequing 10 elements from each side\n");
		for(int i = 0; i < 10; i++){
			queue.dequeueR();
			queue.dequeueL();
		}
		queue.sizeOfL();
		queue.sizeOfR();

		System.out.println("\n---\ntesting the other implementation...\n");
		System.out.println("enquing 20 elements in each side\n");
		for(int i = 0; i < 10; i++){
			listo.enqueueR(5);
			listo.enqueueR(2);
			listo.enqueueL(5);
			listo.enqueueL(2);
		}
		listo.sizeOfR();
		listo.sizeOfL();

		System.out.println("\ndequing 10 elements from each side\n");
		for(int i = 0; i < 10; i++){
			listo.dequeueR();
			listo.dequeueL();
		}
		listo.sizeOfR();
		listo.sizeOfL();
	}
}
