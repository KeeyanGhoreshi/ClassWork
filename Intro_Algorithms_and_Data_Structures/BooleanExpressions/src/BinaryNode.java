
public class BinaryNode {
	private String load;
	private BinaryNode leftpointer;
	private BinaryNode rightpointer;
	private BinaryNode parentnode;

	
	public BinaryNode(String l, BinaryNode p){
		load = l;
		leftpointer = null;
		rightpointer= null;
		parentnode = p;
	}
	
	public void setLeft(BinaryNode thing){
		leftpointer = thing;
	}
	public void setRight(BinaryNode thing){
		rightpointer = thing;
		
	}
	public void setParent(BinaryNode thing){
		parentnode = thing;
	}
	
	public void setLoad(String s){
		load = s;
	}
	public BinaryNode getLeft(){
		return leftpointer;
	}
	public BinaryNode getRight(){
		return rightpointer;
	}
	public BinaryNode getParent(){
		return parentnode;
	}
	public String getLoad(){
		return load;
	}
	public Boolean hasLeft(){
		if(leftpointer==null){
			return false;
		}else{
			return true;
		}
	}
	public Boolean hasRight(){
		if(rightpointer==null){
			return false;
		}else{
			return true;
		}
	}
	

}
