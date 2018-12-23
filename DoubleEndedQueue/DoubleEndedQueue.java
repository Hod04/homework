package alp;

public interface DoubleEndedQueue<E>{
	void enqueueR(E element);
	void enqueueL(E element);
	E dequeueR() throws EmptyQueueException;
	E dequeueL() throws EmptyQueueException;
}
