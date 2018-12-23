package alp;

public class DoubleEndedDynamicArr<E> implements DoubleEndedQueue<E>{

	private E[] dQueue;
	private int topR;
	private int topL;
	private int headR;
	private int headL;
	private int sizeR;
	private int sizeL;

/*
** We've decided to take the challenge and represent the double
** ended queue in a single dynamic array, although it may not be the
** wisest choice in terms of efficiency (because there will be potentially
** more calls to resizeIfFull than if we would have used two dynamic arrays).
** We've achieved that by storing every element that belongs to
** the right-sided queue in every index which fulfills the following
** predicate: (if x mod 2 == 0, where x is the index); i.e. every even index.
** Uneven indices belong to the left-sided queue.
*/

	public DoubleEndedDynamicArr(){
		dQueue = (E[]) new Object[6];
		topR = headR = 0;
		topL = headL = 0; // was assigned to one instead of zero for some reason when we printed our code, here it's fixed.
		sizeR = sizeL = 0;
	}

	public void sizeOfR(){
		System.out.println("The size of the right-sided queue is: "
				+ (sizeR));}

	public void sizeOfL() {
		System.out.println("The size of the left-sided queue is: "
				+ (sizeL));}

	public boolean isEmptyR() {return dQueue[topR]==null;}

	public boolean isEmptyL() {return dQueue[topL]==null;}

/*
** The resizeIfFull method intializes a new array with
** twice as much length as the original double ended queue
** and copies every element to the the new D.E. queue.
*/

	private void resizeIfFull(){
		E[] biggerQ = (E[]) new Object[dQueue.length * 2];
		System.out.println("The dynamic array's been resized. Old capacity: " + (dQueue.length) + ". New capacity: " + (biggerQ.length));

		for(int i = 0; i < dQueue.length; i++)
			biggerQ[i] = dQueue[i];

		dQueue = biggerQ;
	}

/*
** Enqueue: If the right-/left-sided queue is still empty,
** assign the enqueued element to its head and to its
** tail. If the capacity of the array's not big enough, call resizeIfFull.
** Then, put the new element in the topR+2 / topL+2 position.
*/

	public void enqueueR(E element){
		if(isEmptyR()){
			dQueue[headR] = dQueue[topR] = element;
			sizeR++;
			return;
		}
		else if(topR+2 == dQueue.length)
			resizeIfFull();
		topR+=2;
		dQueue[topR] = element;
		sizeR++;
	}

	public void enqueueL(E element){
		if(isEmptyL()){
			dQueue[headL] = dQueue[topL] = element;
			sizeL++;
			return;
		}
		else if(topL+2 == dQueue.length)
			resizeIfFull();
		topL+=2;
		dQueue[topL] = element;
		sizeL++;
	}

/*
** Before our dequeue method returns the head element of one side of the
** queue, it assigns the new head element as the head+2 index.
*/

	public E dequeueR() throws EmptyQueueException{
		if(isEmptyR())
			throw new EmptyQueueException();
		else if(sizeR == 1){
			sizeR--;
			return dQueue[headR];
		}else{
			sizeR--;
			headR+=2;
			return dQueue[headR-2];
		}
	}

	public E dequeueL() throws EmptyQueueException{
		if(isEmptyL())
			throw new EmptyQueueException();
		else if(sizeL == 1){
			sizeL--;
			return dQueue[headL];
		}else{
			sizeL--;
			headL+=2;
			return dQueue[headL-2];
		}
	}
}
