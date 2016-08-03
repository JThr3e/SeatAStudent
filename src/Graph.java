import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Graph<T>{
	private T data;
	private Set<Vertex<T>> verticies;
	private Set<Edge<T>> edges;
	private ArrayList<Vertex<T>> neighborFindV = new ArrayList<Vertex<T>>();
	
	
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
	
	public ArrayList<Vertex<T>> getAllVerticies2FindNeighbors()
	{
		return this.neighborFindV;
	}
	
	public Set<Edge<T>> getAllEdges()
	{
		return this.edges;
	}
	
	public boolean isNextTo(Vertex<T> a, Vertex<T> b){
		for(Edge<T> e : this.edges)
		{
			if(e.getVerticies().contains(a) && e.getVerticies().contains(b))return true;
		}
		return false;
	}
	
	public void setNeighbors(){
		
		for(Vertex<T> v : this.verticies)
		{
			//System.out.println(v);
			for(Edge<T> edge : edges)
			{
				if(edge.getVerticies().get(0).equals(v) && edge.getWeight() == 0)
				{
					v.addOneWayNeighbor(edge.getVerticies().get(Math.abs(edge.getVerticies().indexOf(v)-1)));
					v.addToAllNeighbor(edge.getVerticies().get(Math.abs(edge.getVerticies().indexOf(v)-1)));
					//System.out.println("W0: "+edge);
				}
			}
			for(Edge<T> edge : edges)
			{
				if(edge.getVerticies().contains(v) && edge.getWeight() == 1)
				{
					v.addTwoWayNeighbor(edge.getVerticies().get(Math.abs(edge.getVerticies().indexOf(v)-1)));
					v.addToAllNeighbor(edge.getVerticies().get(Math.abs(edge.getVerticies().indexOf(v)-1)));
					//System.out.println("W1: "+edge);
				}
			}
			//System.out.println("W2: "+v.getNotNextTo());
		}
		this.neighborFindV.addAll(verticies);
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
	
	public ArrayList<Vertex<T>> getOneWayNeighborsOf(Vertex<T> v)
	{
		ArrayList<Vertex<T>> neighbors = new ArrayList<Vertex<T>>();
		for(Edge<T> edge : edges)
		{
			if(edge.getVerticies().get(0).equals(v) && edge.getWeight() == 0)
			{
				neighbors.add(edge.getVerticies().get(Math.abs(edge.getVerticies().indexOf(v)-1)));
			}
		}
		return neighbors;
	}
	
	public ArrayList<Vertex<T>> getTwoWayNeighborsOf(Vertex<T> v)
	{
		ArrayList<Vertex<T>> neighbors = new ArrayList<Vertex<T>>();
		for(Edge<T> edge : edges)
		{
			if(edge.getVerticies().contains(v) && edge.getWeight() == 1)
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
		if(verticies.add(v)){
			this.neighborFindV.add(v);
		}
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
		if(this.verticies.containsAll(e.getVerticies()))
		{
			boolean addEdgeOk = true;
			for(Edge<T> ed : this.edges)
			{
				if(ed.getVerticies().get(0).equals(e.getVerticies().get(0)) && ed.getVerticies().get(1).equals(e.getVerticies().get(1)))
				{
					throw new IllegalArgumentException("Duplicate Edge");
				}
				else if(ed.getVerticies().get(0).equals(e.getVerticies().get(1)) && ed.getVerticies().get(1).equals(e.getVerticies().get(0)))
				{
					ed.setWeight(ed.getWeight()+1);
					addEdgeOk = false;
				}
			}
			if(addEdgeOk) this.edges.add(e);
			
		}
		else
		{
			System.out.println(e+" , "+this.verticies.contains(e.getVerticies().get(1)));
			throw new IllegalArgumentException("Undefined Vertex");
		}
		
	}
	
	public void removeEdge(Vertex<T> a, Vertex<T> b){
		edges.remove(new Edge<T>(a, b, 0));
	}
	
	public void removeVertex(Vertex<T> v){
		verticies.remove(v);
		Set<Edge<T>> temp = new HashSet<Edge<T>>();
		for(Edge<T> e : edges)
		{
			if(!e.getVerticies().contains(v))
			{
				temp.add(e);
			}
		}
		edges = temp;
	}
	
	public String toString(){
		return "{ "+this.verticies.toString()+" ||| "+this.edges.toString()+" }";
	}
}