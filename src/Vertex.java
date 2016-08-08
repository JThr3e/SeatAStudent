import java.util.ArrayList;
import java.util.HashSet;

public class Vertex<T>{
	private T data;
	private HashSet<String> notNextTo = new HashSet<String>();
	private HashSet<Vertex<T>> oneWayNeighbors = new HashSet<Vertex<T>>();
	private HashSet<Vertex<T>> twoWayNeighbors = new HashSet<Vertex<T>>();
	private HashSet<Vertex<T>> allNeighbors = new HashSet<Vertex<T>>();
	private int groupPref = 0;
	
	public Vertex()
	{
		
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
	
	public void setPref(int p)
	{
		this.groupPref = p;
	}
	
	public int getPref()
	{
		return this.groupPref;
	}
	
	public void addNotNextTo(String str)
	{
		this.notNextTo.add(str);
	}
	
	public HashSet<Vertex<T>> getOneWayNeighbors(){
		return this.oneWayNeighbors;
	}
	
	public HashSet<Vertex<T>> getTwoWayNeighbors(){
		return this.twoWayNeighbors;
	}
	
	public HashSet<Vertex<T>> allNeighbors(){
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
	
	public HashSet<String> getNotNextTo()
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
