import java.util.ArrayList;

public class Edge<T>{
	private ArrayList<Vertex<T>> vertexPair;
	private int weight;
	public Edge()
	{
		this.vertexPair = new ArrayList<Vertex<T>>();
		this.weight = 0;
	}
	public Edge(Vertex<T> a, Vertex<T> b, int weight)
	{
		this.vertexPair = new ArrayList<Vertex<T>>();
		this.vertexPair.add(a);
		if(!a.equals(b))
		{
			this.vertexPair.add(b);
		}
		else
		{
			throw new IllegalArgumentException("Can't link a vertex to itself");
		}
		this.weight = weight;
	}
	
	
	public ArrayList<Vertex<T>> getVerticies()
	{
		return vertexPair;
	}
	
	public int getWeight()
	{
		return this.weight;
	}
	
	public int compareTo(Edge<T> that) {
		return this.weight-that.weight;
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object that)
	{
		if(that instanceof Edge<?>)
		{
			return this.vertexPair.containsAll(((Edge<T>)(that)).vertexPair);
		}
		else throw new IllegalArgumentException("That's not an edge");
		
	}
	
	public int hashCode()
	{
		return vertexPair.hashCode();
	}
	
	public String toString()
	{
		return "("+this.vertexPair.get(0)+", "+this.vertexPair.get(1)+")";
	}
	
	
}
