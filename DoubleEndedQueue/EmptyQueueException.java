package alp;

public class EmptyQueueException extends Exception{
	public EmptyQueueException(){
		super("error. the queue's empty :(");
	}
}
