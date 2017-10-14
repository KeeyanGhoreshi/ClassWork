import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SuffixTree {
	TreeNode root;
	ArrayList<String> destructor = new ArrayList<>();
	
	public SuffixTree(String s){
		root = new TreeNode();
		s = s  + "$";
		for(int i = s.length()-2; i>=0;i--){
			insert(s.substring(i,s.length()),root);
		}
		root.shrink();
	}
	
	public void insert(String a, TreeNode node){
		if(a.length()==1){
			TreeNode t = new TreeNode(a);
			node.babies.add(t);
		}else{
			for(TreeNode t : node.babies){
				if(t.key.equals(a.substring(0,1))){
				insert(a.substring(1, a.length()),t);
				return;
				}
			}
			TreeNode p = new TreeNode(a.substring(0,1));
			node.babies.add(p);
			insert(a.substring(1,a.length()), p);
		}
	}
	public void search(int floor, TreeNode node, String word){
		for(TreeNode nextnode : node.babies){
			String value = nextnode.key;
			if(value.length()<floor){
				int newfloor = floor - value.length();
				String newword = word + value;
				search(newfloor, nextnode, newword);
			}else{
				String newword2 = word + value.substring(0, floor);
				int numberwords = nextnode.leaves();
				if(numberwords>1){
					destructor.add(newword2 + " " + numberwords);
				}
				int newfloor = floor;
				while(newfloor!=value.length()){
					newfloor++;
					newword2 = word + value.substring(0,newfloor);
					if(numberwords>1){				
					destructor.add(newword2 + " " + numberwords);
					}
				}
				if(nextnode.leaves()>1 && floor == value.length()){
					search(1, nextnode,newword2);
				}
				
				
			}
		}
	}
}
	