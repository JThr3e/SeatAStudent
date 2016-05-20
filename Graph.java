import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Graph<T>{
	private T data;
	private Set<Vertex<T>> verticies;
	private Set<Edge<T>> edges;
	
	public Graph()
	{
		verticies = new HashSet<Vertex<T>>();
		edges = new HashSet<Edge<T>>();
	}
	
	public Graph(T type)
	{
		this.data = type;
		verticies = new HashSet<Vertex<T>>();
		edges = new HashSet<Edge<T>>();

	}
	
	public Set<Vertex<T>> getAllVerticies()
	{
		return this.verticies;
	}
	
	public Set<Edge<T>> getAllEdges()
	{
		return this.edges;
	}
	
	public ArrayList<Vertex<T>> getNeighborsOf(Vertex<T> v)
	{
		ArrayList<Vertex<T>> neighbors = new ArrayList<Vertex<T>>();
		for(Edge<T> edge : edges)
		{
			if(edge.getVerticies().contains(v))
			{
				neighbors.add(edge.getVerticies().get(Math.abs(edge.getVerticies().indexOf(v)-1)));
			}
		}
		return neighbors;
	}
	
	public int size()
	{
		return this.verticies.size();
	}
	
	public void addVertex(Vertex<T> v)
	{
		if(verticies.add(v));
		else{
			throw new IllegalArgumentException();
		}
	}
	
	public T getData()
	{
		return this.data;
	}
	
	public void addEdge(Edge<T> e)
	{
		System.out.println(this.verticies.contains(e.getVerticies().get(1)));
		if(this.verticies.containsAll(e.getVerticies()))
		{
			if(edges.add(e));
			else{
				throw new IllegalArgumentException("Duplicate Edge");
			}
		}
		else
		{
			throw new IllegalArgumentException("Undefined Vertex");
		}
		
	}
	
	public void removeEdge(Vertex<T> a, Vertex<T> b, int weight){
		edges.remove(new Edge<T>(a, b, weight));
	}
	
	public void removeVertex(Vertex<T> v){
		verticies.remove(v);
	}
}