
public class Node {
	private Entry load;
	private Node left;
	private Node right;
	private Node parent;
	
	public Node(Entry l){
		load = l;
		left = null;
		right = null;
		parent = null;
	}
	public Node(Entry l, Node p){
		load = l;
		left = null;
		right = null;
		parent = p;
	}
	public void setLeft(Node l){
		left = l;
	}
	public void setRight(Node r){
		right = r;
	}
	public void setLoad(Entry l){
		load = l;
	}
	public void setParent(Node p){
		parent = p;
	}
	public Node getLeft(){
		return left;
	}
	public Node getRight(){
		return right;
	}
	public Entry getLoad(){
		return load;
	}
	public Node getParent(){
		return parent;
	}
}
