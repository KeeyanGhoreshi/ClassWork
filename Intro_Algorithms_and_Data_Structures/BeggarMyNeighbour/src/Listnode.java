
public class Listnode {
	private Object _load;
	private Listnode _pointer;
	
	public Listnode(Object load, Listnode pointer){
		_load = load;
		_pointer = pointer;
	}
	
	public void setPointer(Listnode pointer){
		this._pointer = pointer;
		
	}
	public Object getLoad(){
		return _load;
		
	}
	public Listnode getPointer(){
		return _pointer;
		
	}

}
