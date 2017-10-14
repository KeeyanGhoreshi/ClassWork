
public class Operations {
	private BinaryTree tree;
	private String[] tokens;
	
	public Operations(BinaryTree t, String[] s){
		tree = t;
		tokens = s;
	}
	
	public String evaluate(){
		BinaryNode currentnode = tree.getRoot();
		int a;
		String token;
		for(int i=0;i<tokens.length;i++){
			token = tokens[i];
			a = condition(token);
			if(a==1){
				currentnode = stepDown(currentnode);
			}else if(a==2){
				currentnode = stepUp(currentnode);
			}else if(a==3){
				currentnode = stepUp(currentnode);
				currentnode.setLoad(token);
				currentnode= stepDown(currentnode);
			}else if(a==4){
				currentnode.setLoad(token);
			}
		}
		currentnode = tree.getRoot();
		BinaryNode theone = solve(currentnode);
		String finalstr = theone.getLoad();
		return finalstr;
		
	}
	
	public int condition(String st){
		if(st.equals("(")){
			return 1;
		}else if(st.equals(")")){
			return 2;
		}else if(st.equals("&") | st.equals("|")){
			return 3;
		}else{
			return 4;
		}
			
		
	}
	
	public BinaryNode stepDown(BinaryNode node){
		BinaryNode newnode = null;
		if(node.hasLeft()){
			newnode = tree.addRight(node, null);
		}else{
			newnode = tree.addLeft(node, null);
		}
		return newnode;
	}
	public BinaryNode stepUp(BinaryNode node){
		return node.getParent();
	}
	public BinaryNode solve(BinaryNode currentnode){
		if(currentnode.hasLeft() & currentnode.hasRight()){
		BinaryNode left = currentnode.getLeft();
		BinaryNode right = currentnode.getRight();
		if(left.hasLeft()){
			left = solve(left);
		}
		if(right.hasRight()){
			right = solve(right);
		}
		String leftelement = left.getLoad();
		String rightelement = right.getLoad();
		String operator = currentnode.getLoad();
		if(operator.equals("&")){
			if(leftelement.equals("T") & rightelement.equals("T")){
				currentnode.setLoad("T");
			}else{
				currentnode.setLoad("F");
			}
		}else if(operator.equals("|")){
			if(leftelement.equals("F") & rightelement.equals("F")){
				currentnode.setLoad("F");
			}else{
				currentnode.setLoad("T");
			}
		}
		}
		return currentnode;
	}

}
