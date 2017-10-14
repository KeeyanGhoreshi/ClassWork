import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class TreeNode {
	ArrayList<TreeNode> babies = new ArrayList<>();
	String key;
	
	
	public TreeNode(String val){
		key = val;
	}
	public TreeNode(){
		key = null;
	}
	public void shrink(){
		Stack<TreeNode> callStack = new Stack<>();
		for(TreeNode t : babies){
			callStack.push(t);
		}
		while(!callStack.isEmpty()){
			TreeNode node = callStack.pop();
			ArrayList<TreeNode>_babies = node.babies;
		if(_babies.size()==1){
			TreeNode nextnode = _babies.get(0);
			callStack.push(node);
			
			node.key = node.key + nextnode.key;
			node.babies = nextnode.babies;
			
			
			
		}else if(_babies.size() > 1){
			for(TreeNode n : _babies){
				callStack.push(n);
			}
		}
	}
	}
	public int leaves(){
		if(babies.size()==0){
			return 1;
		}else{
			int next = 0;
			for(TreeNode t: babies){
				next = next + t.leaves();
			}
			return next;
		}
	}
}
