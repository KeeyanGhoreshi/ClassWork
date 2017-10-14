import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Graph {
	public Vertex entrance;
	public HashMap<Integer, Vertex> vertices;
	
	public Graph(){
		entrance = new Vertex(0);
		vertices = new HashMap<>();
		vertices.put(0, entrance);
	}
	
	public void addEdge(int a, int b, String load){
		Vertex v1;
		Vertex v2;
		if(vertices.containsKey(a)){
			v1 = vertices.get(a);
		}else{
			v1 = new Vertex(a);
			vertices.put(a, v1);
		}
		if(vertices.containsKey(b)){
			v2 = vertices.get(b);
		}else{
			v2 = new Vertex(b);
			vertices.put(b, v2);
		}
		v1.addEdge(v2, load);
		v2.addEdge(v1, load);
		
	}
	
	public Boolean searchGraph(String secretstring, int number){
		Vertex currentvertex = entrance;
		Edge currentedge;
		ArrayList<String> s = new ArrayList<>();
		String[] s2 = secretstring.split("");
		for(int i=1;i<s2.length;i++){
			if(s2[i]!=" " | s2[i]!=""){
			s.add(s2[i]);
			}
		}
		int currentletter = 0;
		Stack<Edge> tracker = new Stack<>();
		Stack<Integer> letters = new Stack<>();
		for(int i=0;i<currentvertex.edges.size()-1;i++){
			tracker.push(currentvertex.edges.get(i));
			letters.push(0);
		}
		while(!tracker.isEmpty()){
			currentedge = tracker.pop();
			currentletter = letters.pop();
			
			//System.out.println("Letter" + currentletter);
			//System.out.println("Size" + s.size());

			if(currentletter==s.size()){
				//System.out.println(currentvertex.position);
				if(currentvertex.equals(vertices.get(number))){
				return true;
				}
			}else{
			if(currentedge.load.equals(s.get(currentletter))){
				//System.out.println("Yes");
				currentvertex = currentedge.next(currentvertex);
				currentletter++;
				for(int i=0;i<currentvertex.edges.size()-1;i++){
					tracker.push(currentvertex.edges.get(i));
					
					letters.push(currentletter);
					
					//System.out.println(currentletter);
				}
				
				
			}
			}
		}
		return false;
	}

}
