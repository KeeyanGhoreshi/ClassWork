
public class SinglyLinkedList {
	private Listnode _head;
	private Listnode _tail;
	private int _size;
	public SinglyLinkedList(){
		_head = null;
		_size = 0;
		_tail = null;
	}
	public boolean isEmpty(){
		return (_size == 0);
		
	}
	public int getSize(){
		return _size;
	}
	public Listnode getHead(){
		return _head;
	}
	public Listnode getTail(){
		return _tail;
	}
	public void setTail(Listnode tail){
		tail = _tail;
	}
	public void setHead(Listnode head){
		head = _head;
	}
	public void addBottom(Object load){
		Listnode next = new Listnode(load, null);
		if(isEmpty()){
			_tail = next;
			_head = next;
		}
		_tail.setPointer(next);
		_tail = next;
		_size++;
	}
	public void addTop(Object load){
		Listnode next = new Listnode(load, _head);
		_head = next;
		if(isEmpty()){
			_tail = _head;
			
		}
		_size++;
		
	}
	public Object playCard(){
		if(isEmpty()){
			return null;
			
		}
		Object card = _head.getLoad();
		_head = _head.getPointer();
		_size--;
		if(_size == 0){
			_tail = null;
		}
		return card;
		
	}
	public void clear(SinglyLinkedList pile){
		while(!pile.isEmpty()){
			pile.playCard();
		}
	}
}
