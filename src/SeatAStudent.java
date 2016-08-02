import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class SeatAStudent {
	
	//public static final String allStudents = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d";
	//public static final String wanted = "AXSUM,BSL,CFBW,DYPac,ERQd,FWUG,GFVW,HQBN,IFBC,JWQ,KJS,LYUO,MLUC,NER,OVUC,PIYA,QWJU,RCSK,SLG,TRXP,UCD,VAR,WUNM,UIVM,XOI,YEIB,ZRXU,abc,bcd,ca,dAD";
	//public static final String allStudents = "A,B,C,D,E";
	//public static final String wanted =	"ABDC,BA,CE,D,E";
			
	public static final int POPULATION_SIZE = 400;
	public static final int GENERATIONS_NO_CHANGE = 200;
	public static final double MUTATION_CHANCE = 0.8;
	public static final double CROSSOVER_CHANCE = 0.8;
	public static final int ELITES = 2;
	public static final int AEONS = 10;
	public static final String CHART_FNAME = "chart4.txt";
	public static final String STUDENTS_FNAME = "students.txt";
	public static final String SUBGROUP_FNAME = "subchart2.txt";
	public static final String SUBGROUP_GRAPH_FNAME = "subchart_graph2.txt";
	
	public static Graph<String> desired;
	public static Graph<Integer> target;
	public static Graph<String> subGraphs;
	public static HashMap<String, String[]> groupToIndexArr;
	public static HashMap<Integer, String> indexToGroup;
	
	public static ArrayList<ArrayList<String>> permutationArray = new ArrayList<ArrayList<String>>();
	
	
	public static void main(String[] argz)
	{
		long startTime = System.currentTimeMillis();
		ArrayList<Chromosome> bestODaBest = new ArrayList<Chromosome>();
		initGraphs();
		String[] students = new String[desired.getAllVerticies().size()];
		for(int i = 0; i < desired.size(); i++){
			students[i] = desired.getAllVerticies2FindNeighbors().get(i).getData()+"";
		}
		
		for(int ae = 0; ae < AEONS; ae++)
		{
			Chromosome c = runAeon(initPopulation(students), ae);
			Chromosome omega = runPermutate(c);
			System.out.println("[AEON "+(ae+1)+"]: [Fitness: " + omega.getFitnessScore()+"], "+omega.getGenes().toString());
			System.out.println(FitnessUtil.getSubScoreArrayList(omega.getGenes()));
			System.out.println();
			//System.out.println("[AEON "+(ae+1)+"]: [Fitness: " + c.getFitnessScore()+"], "+c.getGenes().toString());
			//System.out.println(FitnessUtil.getSubScoreArrayList(c.getGenes()));
			//System.out.println();
			bestODaBest.add(omega);
		}
		Collections.sort(bestODaBest);
		System.out.println(bestODaBest.get(0).getGenes().toString()+" fitness: "+ bestODaBest.get(0).getFitnessScore());
		System.out.println("[OUTCOME]: "+FitnessUtil.getSubScoreArrayList(bestODaBest.get(0).getGenes()));
		long stopTime = System.currentTimeMillis();
		System.out.println("Operation took "+((stopTime-startTime)/1000L)+" seconds.");
		//Chromosome c = runAeon(bestODaBest, -1000);
		//System.out.println(c.getGenes().toString()+" fitness: "+c.getFitnessScore());
	}
	
	public static Chromosome runPermutate(Chromosome c)
	{
		ArrayList<Integer> scores = new ArrayList<Integer>();
		ArrayList<Integer> indicies = new ArrayList<Integer>();
		ArrayList<String> permuteThese = new ArrayList<String>();
		ArrayList<Chromosome> permutated = new ArrayList<Chromosome>();
		scores.addAll(FitnessUtil.getSubScoreArrayList(c.getGenes()));
		for(int s = 0; s < scores.size(); s++)
		{
			if(scores.get(s) <= 0)
			{
				indicies.add(s);
				permuteThese.add(c.getGenes().get(s));
			}
		}
		permutation(permuteThese);
		for(ArrayList<String> p : permutationArray)
		{
			ArrayList<String> chromo = new ArrayList<String>();
			int prev = 0;
			for(int i = 0; i < indicies.size(); i++)
			{
				chromo.addAll(c.getGenes().subList(prev, indicies.get(i)));
				chromo.add(p.get(i));
				prev = indicies.get(i)+1;
			}
			chromo.addAll(c.getGenes().subList(prev, c.getGenes().size()));
			Chromosome perm = new Chromosome(chromo);
			//System.out.println(perm.getGenes());
			//System.out.println(FitnessUtil.fitnessOf(perm.getGenes()));
			permutated.add(perm);
		}
		Collections.sort(permutated);
		
		return permutated.get(0);
	}
	
	public static void permutation(ArrayList<String> str) { 
		permutationArray.clear();
	    permutation(new ArrayList<String>(), str); 
	}

	private static void permutation(ArrayList<String> prefix, ArrayList<String> str) {
	    int n = str.size();
	    if (n == 0) permutationArray.add(prefix);
	    else {
	        for (int i = 0; i < n; i++){
	        	ArrayList<String> pre = new ArrayList<String>();
	        	ArrayList<String> st = new ArrayList<String>();
	        	pre.addAll(prefix);
	        	pre.add(str.get(i));
	        	st.addAll(str.subList(0, i));
	        	st.addAll(str.subList(i+1, n));
	        	permutation(pre , st);
	        }
	            
	    }
	}
	
	public static Chromosome runAeon(ArrayList<Chromosome> population, int ae)
	{
		//int k = 0;
		int i = 0;
		String prev = "";
		while (i < GENERATIONS_NO_CHANGE) { 
			//System.out.print("[AEON "+ae+"][no change for " + i + " generations] gen: " + k);
			Collections.sort(population);

			ArrayList<Chromosome> temp = new ArrayList<Chromosome>();
			temp.addAll(population.subList(0, ELITES));
			int pn = 0;
			while (temp.size() < POPULATION_SIZE) {
				if (Math.random() < CROSSOVER_CHANCE && pn < (POPULATION_SIZE / 2) - 2) {
					ArrayList<Chromosome> parents = new ArrayList<Chromosome>();
					ArrayList<Chromosome> children = new ArrayList<Chromosome>();
					parents.add(population.get(pn));
					parents.add(population.get(pn + 1));
					children.addAll(haveSex(parents));
					for (int j = 0; j < children.size(); j++) {
						if (Math.random() < MUTATION_CHANCE) {
							children.add(mutate(children.get(j)));
							children.remove(j);
						}
					}
					temp.addAll(children);
					pn += 2;
				} 
				else if (Math.random() < MUTATION_CHANCE) {
						temp.add(mutate(population.get(pn)));
						pn++;
				}
				if (pn >= POPULATION_SIZE / 2)
					pn = 0;
			}
			population.clear();
			population.addAll(temp);
			//System.out.println(population.get(0).getGenes().toString() + " fitness: " + population.get(0).getFitnessScore());
			if (!prev.equals(population.get(0).getGenes().toString()))
				i = 0;
			else
				i++;
			prev = population.get(0).getGenes().toString();
			//k++;
		}
		return population.get(0);
	}
	
	public static void initGraphs(){
		
		// FILL DESIRED V2
		desired = new Graph<String>();
		ArrayList<String> studentsInClass = new ArrayList<String>();
		try {
			Scanner file = new Scanner(new File(STUDENTS_FNAME));
			while (file.hasNextLine()) {
				String student = file.nextLine();
				studentsInClass.add(student);
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(studentsInClass);
		
		for(String str : studentsInClass)
		{
			desired.addVertex(new Vertex<String>(str));
		}
		for(Vertex<String> str : desired.getAllVerticies())
		{
			int i = 0;
			try {
				Scanner file = new Scanner(new File(str.getData()+".txt"));
				while (file.hasNextLine()) {
					String s = file.nextLine();
					//System.out.println(s);
					s = s.replaceAll(" ", "");
					if(s.equals(""))
					{
						/*do nothing*/
					}
					else if(s.charAt(0) == '#')
					{
						i++;
					}
					else{
						switch(i){
						case(0): /*do nothing*/ ;break;
						case(1): /*do nothing*/ ;break;
						case(2): desired.addEdge(new Edge<String>(str, new Vertex<String>(s), 0));break;
						case(3): str.addNotNextTo(s);break;
						//case(4): Window Seat
						default: System.out.println("BROOOO -.-");
						}
					}
				}
				file.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Fill TARGET
		target = new Graph<Integer>();
		try {
			Scanner file = new Scanner(new File(CHART_FNAME));
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
		//SUB CHART GRAPH
		subGraphs = new Graph<String>();
		try {
			Scanner file = new Scanner(new File(SUBGROUP_GRAPH_FNAME));
			while (file.hasNextLine()) {
				try {
					String groups = file.nextLine();
					for(int i = 0; i < groups.length(); i++)
					{
						Vertex<String> v = new Vertex<String>(groups.charAt(i)+"");
						if(!subGraphs.getAllVerticies().contains(v))
						{
							subGraphs.addVertex(v);
						}
					}
					if(groups.length() >= 2)
					for(int i = 1; i < groups.length(); i++)
					{
						subGraphs.addEdge(new Edge<String>(new Vertex<String>(groups.charAt(0)+""), new Vertex<String>(groups.charAt(i)+""), 0));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//SUB GROUP MAP g->i
		groupToIndexArr = new HashMap<String, String[]>();
		indexToGroup = new HashMap<Integer, String>();
		try {
			Scanner file = new Scanner(new File(SUBGROUP_FNAME));
			while (file.hasNextLine()) {
				try {
					String groups = file.nextLine();
					String[] readIn = groups.split(" ");	
					String[] seats = new String[readIn.length-1];
					if(readIn.length >=2)
					{
						for(int i = 1; i < readIn.length; i++)
						{
							seats[i-1] = readIn[i];
							indexToGroup.put(Integer.parseInt(readIn[i]), readIn[0]);
						}
						groupToIndexArr.put(readIn[0], seats);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		target.setNeighbors();
		desired.setNeighbors();
		subGraphs.setNeighbors();
		
		System.out.println(target);
		System.out.println(desired);
		System.out.println(subGraphs);
		System.out.println(groupToIndexArr);
		System.out.println(indexToGroup);
	}
	
	public static ArrayList<Chromosome> initPopulation(String[] students)
	{
		ArrayList<Chromosome> pop = new ArrayList<Chromosome>();
		for(int i = 0; i < POPULATION_SIZE; i++)
		{
			ArrayList<String> genes = new ArrayList<String>();
			for(int j = 0; j < students.length; j++)
			{
				genes.add(students[j]);
			}
			ArrayList<String> newChromo = new ArrayList<String>();
			while(!genes.isEmpty())
			{
				int index = (int)(Math.random()*genes.size());
				newChromo.add(genes.get(index));
				genes.remove(index);
			}
			pop.add(new Chromosome(newChromo));
			
		}
		return pop;
	}
	
	public static Chromosome mutate(Chromosome c){
		int n1 = (int)(Math.random()*c.getGenes().size());
		int n2;
		do{
			n2 = (int)(Math.random()*c.getGenes().size());
		}while(n1 == n2);
		String s1 = c.getGenes().get(n1);
		String s2 = c.getGenes().get(n2);
		ArrayList<String> retArrL = new ArrayList<String>();
		retArrL.addAll(c.getGenes());
		retArrL.set(n1, s2);
		retArrL.set(n2, s1);
		return new Chromosome(retArrL);
	}
	
	public static ArrayList<Chromosome> haveSex(ArrayList<Chromosome> parents)
	{
		ArrayList<String> p1 = new ArrayList<String>();
		ArrayList<String> p2 = new ArrayList<String>();
		p1.addAll(parents.get(0).getGenes());
		p2.addAll(parents.get(1).getGenes());
		ArrayList<Chromosome> children = new ArrayList<Chromosome>();
		int n1 = (int)(Math.random()*p1.size());
		int n2;
		do{
			n2 = (int)(Math.random()*p1.size());
		}while(n1 == n2);
		if(n1 < n2)
		{
			ArrayList<String> sub1 = new ArrayList<String>();
			ArrayList<String> sub2 = new ArrayList<String>();
			sub1.addAll(p1.subList(n1, n2));
			sub2.addAll(p2.subList(n1, n2));
			ArrayList<String> c1 = new ArrayList<String>();
			ArrayList<String> c2 = new ArrayList<String>();
			c1.addAll(buildChild(p1, sub2, n1));
			c2.addAll(buildChild(p2, sub1, n1));
			children.add(new Chromosome(c1));
			children.add(new Chromosome(c2));
		}
		else{
			ArrayList<String> sub1 = new ArrayList<String>();
			ArrayList<String> sub2 = new ArrayList<String>();
			sub1.addAll(p1.subList(n2, n1));
			sub2.addAll(p2.subList(n2, n1));
			ArrayList<String> c1 = new ArrayList<String>();
			ArrayList<String> c2 = new ArrayList<String>();
			c1.addAll(buildChild(p1, sub2, n2));
			c2.addAll(buildChild(p2, sub1, n2));
			children.add(new Chromosome(c1));
			children.add(new Chromosome(c2));
		}
		return children;
	}
	
	public static ArrayList<String> buildChild(ArrayList<String> scanThru, ArrayList<String> subList, int start){
		for(int i = 0; i < subList.size(); i++)
		{
			scanThru.remove(scanThru.indexOf(subList.get(i)));
		}
		ArrayList<String> retList = new ArrayList<String>();
		retList.addAll(scanThru.subList(0, start));
		retList.addAll(subList);
		retList.addAll(scanThru.subList(start, scanThru.size()));
		return retList;
	}
}
