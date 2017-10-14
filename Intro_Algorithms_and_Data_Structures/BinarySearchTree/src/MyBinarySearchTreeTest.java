
public class MyBinarySearchTreeTest {

	public static void main(String[] args) {
		MyBinarySearchTree tree = new MyBinarySearchTree();
		test4(tree);
	}
	
	public static void test1(MyBinarySearchTree t){
		t.insert(1, "A");
		t.insert(2, "B");
		t.insert(3, "C");
		t.insert(4, "D");
		t.insert(5, "E");
		t.insert(6, "F");
		t.insert(7, "G");
		t.insert(8, "H");
		t.insert(9, "I");
		t.printTree(t.getRoot());
		System.out.print(t.getRootValue());
	}
	public static void test2(MyBinarySearchTree t){
		t.insert(1, "A");
		t.insert(2, "B");
		t.insert(3, "C");
		t.insert(4, "D");
		t.insert(5, "E");
		t.insert(6, "F");
		t.insert(7, "G");
		t.insert(8, "H");
		t.insert(9, "I");
		t.remove(9);
		System.out.print(t.getRootValue());
	}
	public static void test3(MyBinarySearchTree t){
		
		t.insert(4, "A");
		t.insert(2, "B");
		t.insert(1, "C");
		t.insert(3, "D");
		t.insert(6, "E");
		t.insert(6, "F");
		t.insert(5, "G");
		t.insert(7, "G");
		t.insert(11, "G");
		t.insert(13, "G");
		t.insert(9, "G");
		t.remove(4);
		t.remove(7);
		System.out.println(t.getRoot().getLoad().getKey());
		System.out.println(t.getRoot().getLeft().getLoad().getKey());
	}
	
	public static void test4(MyBinarySearchTree t){
		t.insert(4, "A");
		t.insert(2, "B");
		t.insert(1, "C");
		t.insert(3, "D");
		t.insert(6, "E");
		t.insert(6, "F");
		t.insert(5, "G");
		t.insert(7, "G");
		t.insert(11, "G");
		t.insert(13, "G");
		t.insert(9, "G");
		t.remove(4);
		t.remove(7);
		System.out.println(t.floorEntry(8).getKey());
		System.out.println(t.ceilingEntry(10).getKey());
		System.out.println(t.ceilingEntry(15));
	}

}
