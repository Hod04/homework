package hashing;
import java.util.Random;
import java.util.LinkedList;

/*
 * An instance of the 'Hashing /w Chaining class is comprised of a linked list
 * that holds nodes. Those nodes are linked lists themselves with extra information:
 * V for value and K for key. We first initialize the table with an arbitrary size of 6 buckets,
 * create a node for each one of them in the linked list, and keep track of the size,
 * i.e. number of elements in order to give information to the add function, if the
 * loadfactor thershold has been succeeded, and thus the resize function should be called.
*/

public class HwC<K, V>{
	private LinkedList<Node<K, V>> array;
	private int size;
	private int tableSize;

	public HwC(){
		array = new LinkedList<>();
		size = 0;
		tableSize = 6;

		for(int i = 0; i < tableSize; i++)
			array.add(null);
	}

	class Node<K, V>{
		V value;
		K key;
		Node<K, V> next;
		Node<K, V> prev;

		Node(K key, V value, Node<K, V> next, Node<K, V> prev){
			this.value = value;
			this.key = key;
			this.next = next;
			this.prev = prev;
		}

		Node(K key, V value, Node<K, V> next){
			this.value = value;
			this.key = key;
			this.next = next;
			prev = null;
		}

		Node(K key, V value){
			this.value = value;
			this.key = key;
			next = null;
			prev = null;
		}
	}

	public int sizeOf() {return size;}

	public boolean isEmpty() {return sizeOf() == 0;}

/*
 * These are the two hash functions we've experimented with.
 * They first convert the K object to an integer,
 * and then perform modulo and other operations on it.
*/

/*
	private int hash(K key) {
		int integerValue = (Integer) key;
		return integerValue % tableSize;
	}
*/
	public int hash(K key) {
		Random generator = new Random();
		int randy = generator.nextInt(7);
		int integerValue = (Integer) key;
		return ((integerValue*randy) % 7) % tableSize;
	}


	public V remove(K key) throws NoSuchElementException{
/*
 * The remove function first calculates the hashed key value of the
 * input key, then creates a temporary node which represents the
 * node that we wish to remove.
*/
		int h = hash(key);

		Node<K, V> temp = array.get(h);
/*
 * We now look for the that node by iterating over the buckets.
 * in case that the input key value indeed exists in our table
 * -> break; in case it doesn't we receive a null pointer and
 * terminate the operation by throwing a new NoSuchElementException.
*/
		while(temp != null){
			if(temp.key.equals(key))
				break;
			temp = temp.next;
		}

		if(temp == null)
			throw new NoSuchElementException();

		size--;
/*
 * The actual deletion process: we set the next- and prev- pointers
 * to connect between the previous node to the successor node and thus
 * we let the garbage collection do the actual removal process.
*/
		if(temp.prev != null){
			Node<K, V> helperNode = temp.prev;
			helperNode.next = temp.next;
			temp.next.prev = helperNode;
		}
		else
			array.set(h, temp.next);

		return temp.value;
	}

	public V get(K key) throws NoSuchElementException{
/*
 * Same as with the remove function, we
 * first calculates the hashed key value of the input key, then creates a
 * temporary node which represents the node that we wish to get.
*/
		int h = hash(key);
		long start =  System.nanoTime();
		Node<K, V> headerNode = array.get(h);
/*
 * We now iterate over the header nodes in order to find the
 * relevant node with the corresponding key.
 * In case we found one -> return its value; otherwise
 * throw a new NoSuchElementException.
*/
		while(headerNode != null){
			if(headerNode.key.equals(key)) {
				long finito = System.nanoTime() - start;
				System.out.println(finito);
				return headerNode.value;
			}
			headerNode = headerNode.next;
		}

		throw new NoSuchElementException();
	}

	public void add(K key, V value){
/*
 * We first check whether the loadfactor threshold has been
 * succeeded; in which case we call the resize function.
 * Side node: we've set the threshold to be 0.75, same as
 * Java 10's default threshold value, which is mentioned to be a
 * 'good tradeoff between space and time costs'.
*/
		if((double)size/tableSize >= 0.75)
			resize();
/*
 * Same as with the remove and get functions, we
 * first calculates the hashed key value of the input key, then creates a
 * temporary node which represents the node that we wish to get.
*/
		int h = hash(key);

		Node<K, V> headerNode = array.get(h);
/*
 * We now iterate over the header nodes in order to find the
 * relevant node with the corresponding key.
 * In case we found one -> overwrite its value, since keys should be
 * unique; otherwise we proceed to creating a new node and attach it to
 * the existing structure.
*/
		while(headerNode != null){
			if(headerNode.key.equals(key)){
				headerNode.value = value;
				return;
			}
			headerNode = headerNode.next;
		}

		size++;
		headerNode = array.get(h);

		Node<K, V> insertedNode = new Node<K, V>(key, value, headerNode);

		array.set(h, insertedNode);
	}

	public void resize(){
/*
 * We go about resizing the table with first cloning our linked list,
 * yhen assign double as much table size and copy all the existing
 * nodes into the newly created linked list.
*/
		LinkedList<Node<K, V>> temp = array;
		array = new LinkedList<>();
		tableSize *= 2;
		size = 0;

		for(int i = 0; i < tableSize; i++) {array.add(null);}

		for(Node<K, V> helperNode : temp){
				while(helperNode != null){
					add(helperNode.key, helperNode.value);
					helperNode = helperNode.next;
				}
			}
		}
}

