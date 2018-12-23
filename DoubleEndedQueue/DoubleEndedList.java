package alp;

public class DoubleEndedList<E> implements DoubleEndedQueue<E>{

	private ListNode<E> topR;
	private ListNode<E> topL;
	private int sizeR;
	private int sizeL;
	private ListNode<E> headR;
	private ListNode<E> headL;
/*
** This implementation of the double ended queue is done
** with a simple linked list. Each node consists of an
** element and with a pointer to the next element.
** We also initialize our D.E. queue with pointers
** to the head and tail (top) of each side of the queue.
*/

	public DoubleEndedList() {
		headR = null;
		headL = null;
		topR = null;
		topL = null;
		sizeR = 0;
		sizeL = 0;
	}

	class ListNode<E>{
		E element;
		ListNode<E> next;

		ListNode(E element, ListNode<E> next){
			this.element = element;
			this.next = next;
		}

		ListNode(E element){
			this.element = element;
			next = null;
		}
	}

	public boolean isEmptyR() {return headR == null;}

	public boolean isEmptyL() {return headL == null;}

	public void sizeOfR()
		{System.out.println("The size of the right-sided queue is: "
				+ (sizeR));}

	public void sizeOfL()
		{System.out.println("The size of the left-sided queue is: "
				+ (sizeL));}
/*
** Enqueue: call the ListNode constructor with the corresponding element,
** then enqueue it correctly to the relevant right-/left-sided queue by
** assigning the top pointer to it. If it is the first enqueued element,
** both the head and top pointers will be pointing it.
*/

	public void enqueueR(E element){
		if(isEmptyR()){
			topR = headR = new ListNode<E>(element);
			headR.next = topR;
			sizeR++;
		}else{
			ListNode<E> temp = new ListNode<E>(element, topR);
			topR = temp;
			sizeR++;
		}
	}

	public void enqueueL(E element){
		if(isEmptyL()){
			topL = headL = new ListNode<E>(element);
			headL.next = topL;
			sizeL++;
		}else{
			ListNode<E> temp = new ListNode<E>(element, topL);
			topL = temp;
			sizeL++;
		}
	}
/*
** Dequeue: unless the queue's empty (which in this case will throw a new
** EmptyQueueException), return the element, which the head pointer points to
** and then assign the next element in the queue as the new head.
*/
	public E dequeueR() throws EmptyQueueException{
		if(isEmptyR())
			throw new EmptyQueueException();
		E firstElement = headR.element;
		headR = headR.next;
		sizeR--;
		return firstElement;
	}

	public E dequeueL() throws EmptyQueueException{
		if(isEmptyL())
			throw new EmptyQueueException();
		E firstElement = headL.element;
		headL = headL.next;
		sizeL--;
		return firstElement;
	}
}

