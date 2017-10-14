
public class BinaryTree {
	private BinaryNode root;

	
	public BinaryTree(){
	}
	
	public void addRoot(BinaryNode node){
		root = node;
	}
	
	public BinaryNode addLeft(BinaryNode node1, String s){
		BinaryNode node2 = new BinaryNode(s, node1);
		node1.setLeft(node2);
		return node2;
	}
	public BinaryNode addRight(BinaryNode node1, String s){
		BinaryNode node2 = new BinaryNode(s, node1);
		node1.setRight(node2);
		return node2;
	}
	public BinaryNode createNode(String s){
		BinaryNode node = new BinaryNode(s, null);
		return node;
	}
	public BinaryNode getRoot(){
		return root;
	}


}
