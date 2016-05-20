import java.util.ArrayList;

public class Vertex<T>{
	private T data;
	private ArrayList<String> notNextTo;
	
	public Vertex()
	{
		notNextTo = new ArrayList<String>();
	}
	
	public Vertex(T data)
	{
		this.data = data;
		notNextTo = new ArrayList<String>();
	}
	
	public Object getVertex()
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
