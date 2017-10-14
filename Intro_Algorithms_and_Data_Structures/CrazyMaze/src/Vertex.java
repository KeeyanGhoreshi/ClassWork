import java.util.ArrayList;

public class Vertex {
	public int position;
	public ArrayList<Edge> edges = new ArrayList<>();
	
	public Vertex(int p){
		position = p;
		
	}
	public void addEdge(Vertex v, String load){
		Edge e = new Edge(load);
		e.vertexes.add(v);
		e.vertexes.add(this);
		edges.add(e);
		v.edges.add(e);
	}
	

}
