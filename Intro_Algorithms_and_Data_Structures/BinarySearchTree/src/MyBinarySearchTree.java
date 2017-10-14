public class MyBinarySearchTree implements BinarySearchTree {
	private Node root;
	
	public MyBinarySearchTree(){
		root = null;
	}

	@Override
	public String getRootValue() {
		if(root==null){
			return null;
		}else{
			return root.getLoad().getValue();
		}
	}

	@Override
	public String insert(int key, String value) {
		Entry newentry = new Entry(key, value);
		Node newnode = new Node(newentry);
		if(root==null){
			root = newnode;
			return null;
		}else{
			String n = Search(root, key, value);
			return n;
			
			
		}
		
		
	}

	@Override
	public String remove(int k) {
		Node currentnode = root;
		int result = 0;
		while(result ==0){
			if(currentnode.getLoad().getKey()>k){
				if(currentnode.getLeft()!=null){
					currentnode = currentnode.getLeft();
				}else{
					result = 2;
				}
			}else if(currentnode.getLoad().getKey()<k){
				if(currentnode.getRight()!=null){
					currentnode = currentnode.getRight();
				}else{
					result = 2;
				}
			}else if(currentnode.getLoad().getKey()==k){
				result = 1;
			}
		}
		if(result==2){
			return null;
		}else{
			String returnvalue = currentnode.getLoad().getValue();
			if(currentnode.getLeft()==null & currentnode.getRight() ==null){
				detach(currentnode);
			}else if(currentnode.getLeft()!=null & currentnode.getRight() == null){
				Node leftnode = currentnode.getLeft();
				if(currentnode.getParent()!=null){
					Node parent = currentnode.getParent();
					parent.setLeft(leftnode);
					leftnode.setParent(parent);
				}else{
					currentnode.setLeft(null);
					currentnode = leftnode;
					root = currentnode;
				}
				
			}else if(currentnode.getLeft()==null & currentnode.getRight()!=null){
				Node rightnode = currentnode.getRight();
				if(currentnode.getParent()!=null){
					Node parent = currentnode.getParent();
					parent.setRight(rightnode);
					rightnode.setParent(parent);
				}else{
					currentnode.setRight(null);
					currentnode = rightnode;
					root = currentnode;
				}
			}else if(currentnode.getLeft()!=null & currentnode.getRight()!=null){
				checkRemove(currentnode);
			}
			return returnvalue;
		}
	}

	@Override
	public Entry ceilingEntry(int k) {
		return ceiling(k,null,root);
	}
	
	public Entry ceiling(int k, Entry e, Node n){
		while(n!=null){
			if(k<=n.getLoad().getKey()){
				e = n.getLoad();
				n = n.getLeft();
			}else{
				n = n.getRight();
			}
		}
		return e;
	}

	@Override
	public Entry floorEntry(int k) {
		return floor(k,null,root);
	}
	public Entry floor(int k, Entry e, Node n){
		while(n!=null){
			if(k<n.getLoad().getKey()){
				n = n.getLeft();
			}else{
				e = n.getLoad();
				n = n.getRight();
			}
		}
		return e;
	}
	public String Search(Node currentnode, int key, String value){
		int result = 0;
		String returnvalue = null;
		while(result==0){
		if(currentnode.getLoad().getKey()>key){
			if(currentnode.getLeft()!=null){
				currentnode = currentnode.getLeft();
			}else{
				Entry newentry = new Entry(key,value);
				Node newnode = new Node(newentry,currentnode);
				currentnode.setLeft(newnode);
				rotation(currentnode.getLeft());
				result = 1;
			}
		}else if(currentnode.getLoad().getKey()<key){
			if(currentnode.getRight()!=null){
				currentnode = currentnode.getRight();
			}else{
				Entry newentry = new Entry(key,value);
				Node newnode = new Node(newentry,currentnode);
				currentnode.setRight(newnode);
				rotation(currentnode.getRight());
				result = 1;
			}
		}else if(currentnode.getLoad().getKey()==key){
			String oldvalue = currentnode.getLoad().getValue();
			currentnode.getLoad().setValue(value);
			rotation(currentnode);
			result = 1;
			returnvalue = oldvalue;
			
			}
		}
		return returnvalue;
	}
	
	public Node rotateRight(Node currentnode){
		Node leftsubtree = currentnode.getLeft();
		Node rightnodeofleft = leftsubtree.getRight();
		Node parent = currentnode.getParent();
		currentnode.setParent(leftsubtree);
		leftsubtree.setRight(currentnode);
		currentnode.setLeft(rightnodeofleft);
		if(currentnode.getLeft()!=null){
			currentnode.getLeft().setParent(currentnode);
		}
		if(parent!=null){
			leftsubtree.setParent(parent);
			if(leftsubtree.getLoad().getKey()<parent.getLoad().getKey()){
				parent.setLeft(leftsubtree);
			}else{
				parent.setRight(leftsubtree);
			}
		}else{
			leftsubtree.setParent(null);
			root = leftsubtree;
		}
		return leftsubtree;
	}
	public Node rotateLeft(Node currentnode){
		Node rightsubtree = currentnode.getRight();
		Node leftnodeofright = rightsubtree.getLeft();
		Node parent = currentnode.getParent();
		currentnode.setParent(rightsubtree);
		rightsubtree.setLeft(currentnode);
		currentnode.setRight(leftnodeofright);
		if(currentnode.getRight()!=null){
			currentnode.getRight().setParent(currentnode);
		}
		if(parent!=null){
			rightsubtree.setParent(parent);
			if(rightsubtree.getLoad().getKey()<parent.getLoad().getKey()){
				parent.setLeft(rightsubtree);
			}else{
				parent.setRight(rightsubtree);
			}
		}else{
			rightsubtree.setParent(null);
			root = rightsubtree;
		}
		return rightsubtree;
	}
	public void rotation(Node currentnode){
		
		while(currentnode.getParent()!=null){
			int rotationdone = 0;
			if(currentnode.getParent().getLeft()!=null){
				if(currentnode.getParent().getLeft().getLoad().getKey()==currentnode.getLoad().getKey()){
					currentnode = rotateRight(currentnode.getParent());
					rotationdone = 1;					
				}
			}
			if(currentnode.getParent()!=null){
			if(currentnode.getParent().getRight()!=null){
				if(currentnode.getParent().getRight().getLoad().getKey()==currentnode.getLoad().getKey() & rotationdone == 0){
					currentnode = rotateLeft(currentnode.getParent());
				}
			}
			}
			
		}
		root = currentnode;
	}
	
	public void detach(Node currentnode){
		if(currentnode.getParent().getLeft().getLoad().getKey() == currentnode.getLoad().getKey()){
			currentnode.getParent().setLeft(null);
		}
		if(currentnode.getParent().getRight().getLoad().getKey() == currentnode.getLoad().getKey()){
			currentnode.getParent().setRight(null);
		}
	}
	public void checkRemove(Node currentnode){
		Node rightnode = currentnode.getRight();
		while(rightnode.getLeft()!=null){
			rightnode = rightnode.getLeft();
		}
		int newkey = rightnode.getLoad().getKey();
		String newvalue = rightnode.getLoad().getValue();
		detach(rightnode);
		Entry l = new Entry(newkey, newvalue);
		currentnode.setLoad(l);
	}
	public void printTree(Node x){
		System.out.println(x.getLoad().getKey());
		if(x.getLeft()!=null){
			printTree(x.getLeft());
			
		}
		if(x.getRight()!=null){
			printTree(x.getRight());
		}
	}
	public Node getRoot(){
		return root;
	}

}
