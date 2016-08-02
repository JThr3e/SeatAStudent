import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

public class SeatAStudentNoML {
	
	public static void main(String[] idc)
	{
		//DESIRED GRAPH
		Graph<String> desired = new Graph<String>();
		
		//TARGET GRAPH
		Graph<Integer> target = new Graph<Integer>();
		
		//Fill TARGET
		try{
			Scanner file = new Scanner(new File("chart.txt"));
			while(file.hasNextLine())
			{
				try{
					String nums = file.nextLine();
					String[] verticies = nums.split(" ");
					Vertex<Integer> v;
					Vertex<Integer> prev = null;
					for(int i = 0; i < verticies.length; i++)
					{
						v = new Vertex<Integer>(Integer.parseInt(verticies[i]));
						target.addVertex(v);
						System.out.println("Vertex added: "+verticies[i]);
						if(i>0)
						{
							System.out.println("Edge added");
							target.addEdge(new Edge<Integer>(prev,v,1));
						}
						prev = v;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			file.close();
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		//...TEST/DEBUG...//
		System.out.println("V: "+target.getAllVerticies());
		System.out.println("E: "+target.getAllEdges());
		//...TEST/DEBUG...//
		
		
		//ADD VERTICIES TO DESIRED
		try{
			Scanner file = new Scanner(new File("students.txt"));
			while(file.hasNextLine())
			{
				try{
					desired.addVertex(new Vertex<String>(file.nextLine()));
				}catch(Exception e){
					System.out.println("Duplicate Vertex");
				}
				
			}
			file.close();
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
		
		//...TEST/DEBUG...//
		System.out.println(desired.getAllVerticies());
		//...TEST/DEBUG...//
		
		
		//ADD EDGES  TO DESIRED
		for(Vertex<String> v: desired.getAllVerticies())
		{
			try{
				Scanner file = new Scanner(new File(v.toString()+".txt"));
				file.nextLine();
				String name;
				do{
					name = file.nextLine();
					if(!name.equals("I DON'T WANT TO SIT NEXT TO:"))
					{
						desired.addEdge(new Edge<String>(v, new Vertex<String>(name), 1));
					}
				}while(!name.equals("I DON'T WANT TO SIT NEXT TO:"));
				while(file.hasNextLine())
				{
					v.addNotNextTo(file.nextLine());
				}
				file.close();
			}
			catch(Exception e){
				e.printStackTrace();			
			}
		}
		
		//...TEST/DEBUG...//
		System.out.println(desired.getAllEdges());
		//...TEST/DEBUG...//
		
		//...TEST/DEBUG...//
		desired.removeVertex(new Vertex<String>("Joseph"));
		System.out.println(desired.getAllEdges());
		//...TEST/DEBUG...//
		
		//...TEST/DEBUG...//
		for(Vertex<String> v: desired.getAllVerticies())
		{
			System.out.println(v.toString()+" doesn't want to sit next to: "+v.getNotNextTo().toString());
		}
		//...TEST/DEBUG...//
		
		
		//LINEARIZE
		HashSet<Graph<String>> linearDesire = new HashSet<Graph<String>>();
		HashSet<Vertex<String>> tempVerticies = new HashSet<Vertex<String>>();
		tempVerticies.addAll(desired.getAllVerticies());
		for(Vertex<String> v : tempVerticies)
		{
			
		}
		
		
		
		//CHOP
		
		
		
		//SPLICE
		
		
		
		
	}

}
