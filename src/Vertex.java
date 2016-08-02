import java.util.ArrayList;

public class Vertex<T>{
	private T data;
	private ArrayList<String> notNextTo = new ArrayList<String>();
	private ArrayList<Vertex<T>> oneWayNeighbors = new ArrayList<Vertex<T>>();
	private ArrayList<Vertex<T>> twoWayNeighbors = new ArrayList<Vertex<T>>();
	private ArrayList<Vertex<T>> allNeighbors = new ArrayList<Vertex<T>>();
	
	public Vertex()
	{
		notNextTo = new ArrayList<String>();
	}
	
	public Vertex(T data)
	{
		this.data = data;
	}
	
	public Object getData()
	{
		return this.data;
	}
	public void setVertex(T data)
	{
		this.data = data;
	}
	
	public void addNotNextTo(String str)
	{
		this.notNextTo.add(str);
	}
	
	public ArrayList<Vertex<T>> getOneWayNeighbors(){
		return this.oneWayNeighbors;
	}
	
	public ArrayList<Vertex<T>> getTwoWayNeighbors(){
		return this.twoWayNeighbors;
	}
	
	public ArrayList<Vertex<T>> allNeighbors(){
		return this.allNeighbors;
	}
	
	public void addOneWayNeighbor(Vertex<T> v)
	{
		this.oneWayNeighbors.add(v);
	}
	
	public void addTwoWayNeighbor(Vertex<T> v)
	{
		this.twoWayNeighbors.add(v);
	}
	
	public void addToAllNeighbor(Vertex<T> v)
	{
		this.allNeighbors.add(v);
	}
	
	public ArrayList<String> getNotNextTo()
	{
		return this.notNextTo;
	}
	
	public String toString(){
		return ""+this.data;
	}
	
	public int compareTo(Vertex<T> that) {
		//lexicographic comparison
		return this.toString().compareTo(that.toString());
	}
	
	public boolean equals(Object that)
	{
		 return this.toString().equals(that.toString());
	}
	
	public int hashCode()
	{
		return data.hashCode();
	}
}
