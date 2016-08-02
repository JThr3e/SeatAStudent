import java.io.File;
import java.util.Scanner;

public class Tester{
	
	public static final String allStudents = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
	public static final String wanted = "AXSU,BSL,CFBW,DYP,ERQ,FWUC,GFVW,HQBN,IFBC,JWQ,KJS,LYUO,MLUC,NER,OVUC,PIYA,QWJU,RCSK,SLG,TRXP,UCD,VAR,WUNM,UIVM,XOI,YEIB,ZRXU";
	
	public static Graph<String> desired;
	public static Graph<Integer> target;
	
	public static void main(String[] args)
	{
		initGraphs();
		
		System.out.println(desired.getOneWayNeighborsOf(new Vertex<String>("A")));
		
		System.out.println(desired.getTwoWayNeighborsOf(new Vertex<String>("C")));
		
	}
	public static void initGraphs(){
		// Fill DESIRED
		desired = new Graph<String>();
		String[] students = allStudents.split(",");
		for (int i = 0; i < students.length; i++) {
			desired.addVertex(new Vertex<String>(students[i]));
		}
		String[] studentPref = wanted.split(",");

		for (int i = 0; i < studentPref.length; i++) {
			String ssp = studentPref[i];
			String student = ssp.charAt(0) + "";
			for (int j = 1; j < ssp.length(); j++) {
				desired.addEdge(
						new Edge<String>(new Vertex<String>(student), new Vertex<String>(ssp.charAt(j) + ""), 0));
			}
		}

		// Fill TARGET
		target = new Graph<Integer>();
		try {
			Scanner file = new Scanner(new File("chart2.txt"));
			while (file.hasNextLine()) {
				try {
					String nums = file.nextLine();
					String[] verticies = nums.split(" ");
					Vertex<Integer> v;
					Vertex<Integer> prev = null;
					for (int i = 0; i < verticies.length; i++) {
						v = new Vertex<Integer>(Integer.parseInt(verticies[i]));
						target.addVertex(v);
						if (i > 0) {
							target.addEdge(new Edge<Integer>(prev, v, 1));
						}
						prev = v;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(target);
		System.out.println(desired);
	}

}
