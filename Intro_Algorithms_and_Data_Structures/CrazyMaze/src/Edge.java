import java.util.ArrayList;

public class Edge {
	
	public String load;
	public ArrayList<Vertex> vertexes = new ArrayList<>();
	
	public Edge(String s){
		load = s;
		
	}
	public void addVertex(Vertex v){
		vertexes.add(v);
	}
	public Vertex next(Vertex v){
		Vertex v1 = vertexes.get(0);
		Vertex v2 = vertexes.get(1);
		if(v1==v){
			return v2;
		}else{
			return v1;
		}
	}
}
