import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class SeatAStudent {
			
	//public static final int SAS.POPULATION_SIZE = 400;
	//public static final int SAS.GENERATIONS_NO_CHANGE = 200;
	//public static final double SAS.MUTATION_CHANCE = 0.8;
	//public static final double SAS.CROSSOVER_CHANCE = 0.8;
	//public static final int SAS.ELITES = 2;
	//public static final int SAS.AEONS = 5;
	//public static final String SAS.CHART_FNAME = "chart.txt";
	//public static final String SAS.STUDENTS_FNAME = "students.txt";
	//public static final String SAS.SUBGROUP_FNAME = "subchart.txt";
	//public static final String SAS.SUBGROUP_GRAPH_FNAME = "subchart_graph.txt";
	
	public static Graph<String> desired;
	public static Graph<Integer> target;
	public static Graph<String> subGraphs;
	public static HashMap<String, String[]> groupToIndexArr;
	public static HashMap<Integer, String> indexToGroup;
	public static HashMap<String, Vertex<String>> strToVertDesired;
	public static HashMap<Vertex<Integer>, Integer> vertToIntTarget;
	
	public static ArrayList<ArrayList<String>> permutationArray = new ArrayList<ArrayList<String>>();
	
	
	public static void main(String[] argz)
	{
		long startTime = System.currentTimeMillis();
		UIUtil.changeJOP();
		UIUtil.createUI();
		UIUtil.updateOutput("[000ms]: Operation started "+startTime+" milliseconds after the epoch.");
		ArrayList<Chromosome> bestODaBest = new ArrayList<Chromosome>();
		UIUtil.updateOutput("["+(System.currentTimeMillis()-startTime)+"ms]: Initializing graphs, maps, and config");
		SAS.readInAndInitConfig();
		initGraphs();
		String[] students = new String[desired.getAllVerticies().size()];
		for(int i = 0; i < desired.size(); i++){
			students[i] = desired.getAllVerticies2FindNeighbors().get(i).getData()+"";
		}
		UIUtil.updateOutput("["+(System.currentTimeMillis()-startTime)+"ms]: done");
		UIUtil.updateOutput("["+(System.currentTimeMillis()-startTime)+"ms]: Beginning evolutionary simulation"
				+ "(This takes a while)...");
		for(int ae = 0; ae < SAS.AEONS; ae++)
		{
			Chromosome c = runAeon(initPopulation(students), ae);
			System.out.println("[AEON "+(ae+1)+"]: [Fitness: " + c.getFitnessScore()+"], "+
					c.getGenes().toString());
			System.out.println(FitnessUtil.getSubScoreArrayList(c.getGenes(), c.getIndexMap()));
			System.out.println();
			UIUtil.updateOutput("["+(System.currentTimeMillis()-startTime)+"ms]: [AEON "+(ae+1)+
					"]~~~[Fitness: " + c.getFitnessScore()+"]:\n"+c.getGenes().toString());
			UIUtil.updateOutput(FitnessUtil.getSubScoreArrayList(c.getGenes(), c.getIndexMap()).toString()+"\n");
			bestODaBest.add(c);
		}
		Collections.sort(bestODaBest);
		
		System.out.println(bestODaBest.get(0).getGenes().toString()+" fitness: "+ bestODaBest.get(0).
				getFitnessScore());
		System.out.println("[OUTCOME]: "+FitnessUtil.getSubScoreArrayList(bestODaBest.get(0).getGenes(), bestODaBest.get(0).getIndexMap()));
		
		UIUtil.updateOutput("["+(System.currentTimeMillis()-startTime)+"ms]: Here's the best result I could find: \n"
				+bestODaBest.get(0).getGenes().toString());
		UIUtil.updateOutput("Fitness Info:");
		UIUtil.updateOutput(FitnessUtil.getSubScoreArrayList(bestODaBest.get(0).getGenes(), bestODaBest.get(0).getIndexMap()).toString());
		UIUtil.updateOutput("[Final Fitness Score]: "+ bestODaBest.get(0).getFitnessScore()+"\n");
		
		UIUtil.updateOutput("Seating chart for "+SAS.STUDENTS_FNAME+":");
		for(int i = 0; i < bestODaBest.get(0).getGenes().size(); i++)
		{
			UIUtil.updateOutput("Seat "+(i+1)+": "+bestODaBest.get(0).getGenes().get(i));
		}
		UIUtil.updateOutput("");
		long stopTime = System.currentTimeMillis();
		System.out.println("Operation took "+((stopTime-startTime)/1000L)+" seconds.");
		UIUtil.updateOutput("Operation took "+((stopTime-startTime)+" milliseconds"));
		UIUtil.updateOutput("Operation ended " + stopTime + " milliseconds after the epoch");
	}
	
	public static Chromosome runAeon(ArrayList<Chromosome> population, int ae)
	{
		int i = 0;
		String prev = "";
		while (i < SAS.GENERATIONS_NO_CHANGE) { 
			//System.out.print("[AEON "+ae+"][no change for " + i + " generations] gen: " + k);
			//if(SAS.DYNAMIC_POPULATION_SIZE > 100 && i > 0) SAS.DYNAMIC_POPULATION_SIZE-=2;
			Collections.sort(population);
			ArrayList<Chromosome> temp = new ArrayList<Chromosome>();
			temp.addAll(population.subList(0, SAS.ELITES));
			int pn = 0;
			while (temp.size() < SAS.DYNAMIC_POPULATION_SIZE) {
				if (Math.random() < SAS.CROSSOVER_CHANCE && pn < (SAS.POPULATION_SIZE) - 2) {
					List<Chromosome> parents = population.subList(pn, pn+2);
					ArrayList<Chromosome> children = new ArrayList<Chromosome>();
					children.addAll(haveSex(parents));
					for (int j = 0; j < children.size(); j++) {
						if (Math.random() < SAS.MUTATION_CHANCE) {
							children.add(mutate(children.get(j)));
							children.remove(j);
						}
					}
					temp.addAll(children);
					pn += 2;
				} 
				else if (Math.random() < SAS.MUTATION_CHANCE) {
						temp.add(mutate(population.get(pn)));
						pn++;
				}
				if (pn >= SAS.POPULATION_SIZE / 2)
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
		}
		return population.get(0);
	}
	
	public static void initGraphs(){
		
		//FILL DESIRED GRAPH/MAP
		desired = new Graph<String>();
		strToVertDesired = new HashMap<String, Vertex<String>>();
		ArrayList<String> studentsInClass = new ArrayList<String>();
		try {
			Scanner file = new Scanner(new File(SAS.STUDENTS_FNAME));
			while (file.hasNextLine()) {
				String student = file.nextLine();
				studentsInClass.add(student);
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
			UIUtil.showOutput("Error! Couldn't find the file \""+SAS.STUDENTS_FNAME+"\" Perhaps you forgot\nthe extension or the file name is incorrect.");
			System.exit(0);
		}
		System.out.println(studentsInClass);
		
		for(String str : studentsInClass)
		{
			Vertex<String> v = new Vertex<String>(str);
			strToVertDesired.put(str, v);
			desired.addVertex(v);
		}
		for(Vertex<String> str : desired.getAllVerticies())
		{
			int i = 0;
			try {
				Scanner file = new Scanner(new File(str.getData()+".txt"));
				while (file.hasNextLine()) {
					String s = file.nextLine();
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
						case(4): 
							if(s.toLowerCase().equals("yes") || s.toLowerCase().equals("y")) str.setPref(1);
							else if(s.toLowerCase().equals("no") || s.toLowerCase().equals("n")) str.setPref(2);
							else str.setPref(0); break;
						default: System.out.println("BROOOO -.-");
						}
					}
				}
				file.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Fill TARGET GRAPH/MAP
		target = new Graph<Integer>();
		vertToIntTarget = new HashMap<Vertex<Integer>, Integer>();
		try {
			Scanner file = new Scanner(new File(SAS.CHART_FNAME));
			while (file.hasNextLine()) {
				try {
					String nums = file.nextLine();
					String[] verticies = nums.split(" ");
					Vertex<Integer> v;
					Vertex<Integer> prev = null;
					for (int i = 0; i < verticies.length; i++) {
						v = new Vertex<Integer>(Integer.parseInt(verticies[i]));
						vertToIntTarget.put(v, Integer.parseInt(verticies[i]));
						target.addVertex(v);
						if (i > 0) {
							target.addEdge(new Edge<Integer>(prev, v, 1));
						}
						prev = v;
					}
				} catch (Exception e) {
					e.printStackTrace();
					UIUtil.showOutput("Fatal Error, is "+SAS.CHART_FNAME+" the correct name of the file?\nIf not change the name in config.txt.");
					System.exit(0);
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Account for empty seats 
		System.out.println(target.size());
		System.out.println(desired.size());
		int amt2Add = target.size()-desired.size();
		if(target.size() > desired.size()){
			for(int i = 0; i < amt2Add; i++)
			{
				String str = "EmptySeat"+(i+1);
				Vertex<String> v = new Vertex<String>(str);
				strToVertDesired.put(str, v);
				desired.addVertex(v);
			}
		}
		
		//FILL SUB CHART GRAPH
		subGraphs = new Graph<String>();
		try {
			Scanner file = new Scanner(new File(SAS.SUBGROUP_GRAPH_FNAME));
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
					UIUtil.showOutput("Fatal Error, is "+SAS.SUBGROUP_GRAPH_FNAME+" the correct name of the file?\nIf not change the name in config.txt.");
					System.exit(0);
				}

			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//INIT SUB GROUP MAPS
		groupToIndexArr = new HashMap<String, String[]>();
		indexToGroup = new HashMap<Integer, String>();
		try {
			Scanner file = new Scanner(new File(SAS.SUBGROUP_FNAME));
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
							indexToGroup.put(Integer.parseInt(readIn[i])-1, readIn[0]);
						}
						groupToIndexArr.put(readIn[0], seats);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					UIUtil.showOutput("Fatal Error, is "+SAS.SUBGROUP_FNAME+" the correct name of the file?\nIf not change the name in config.txt.");
					System.exit(0);
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//FILL ADJACENCY LISTS
		target.setNeighbors();
		desired.setNeighbors();
		subGraphs.setNeighbors();
		
		//SYSO
		System.out.println(target);
		System.out.println(desired);
		System.out.println(subGraphs);
		System.out.println(groupToIndexArr);
		System.out.println(indexToGroup);
	}
	
	public static ArrayList<Chromosome> initPopulation(String[] students)
	{
		ArrayList<Chromosome> pop = new ArrayList<Chromosome>();
		for(int i = 0; i < SAS.POPULATION_SIZE; i++)
		{
			ArrayList<String> genes = new ArrayList<String>();
			for(int j = 0; j < students.length; j++)
			{
				genes.add(students[j]);
			}
			ArrayList<String> newChromo = new ArrayList<String>();
			HashMap<String, Integer> indexMap = new HashMap<String, Integer>();
			while(!genes.isEmpty())
			{
				int index = (int)(Math.random()*genes.size());
				newChromo.add(genes.get(index));
				indexMap.put(genes.get(index), index);
				genes.remove(index);
			}
			pop.add(new Chromosome(newChromo, indexMap));
			
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
		ArrayList<String> retChromo = new ArrayList<String>();
		HashMap<String, Integer> indexMap = (HashMap)c.getIndexMap().clone();
		retChromo.addAll(c.getGenes());
		retChromo.set(n1, s2);
		retChromo.set(n2, s1);
		indexMap.put(s1, n2);
		indexMap.put(s2, n1);
		return new Chromosome(retChromo, indexMap);
	}
	
	@SuppressWarnings("unchecked") 
	public static ArrayList<Chromosome> haveSex(List<Chromosome> parents)
	{
		ArrayList<String> p1 = (ArrayList<String>)parents.get(0).getGenes().clone();
		ArrayList<String> p2 = (ArrayList<String>)parents.get(1).getGenes().clone();
		HashMap<String, Integer> pM1 = (HashMap<String, Integer>)parents.get(0).getIndexMap().clone();
		HashMap<String, Integer> pM2 = (HashMap<String, Integer>)parents.get(1).getIndexMap().clone();
		
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
			children.add(buildChild(p1, pM1, sub2, n1, n2));
			children.add(buildChild(p2, pM2, sub1, n1, n2));
		}
		else{
			ArrayList<String> sub1 = new ArrayList<String>();
			ArrayList<String> sub2 = new ArrayList<String>();
			sub1.addAll(p1.subList(n2, n1));
			sub2.addAll(p2.subList(n2, n1));
			children.add(buildChild(p1, pM1, sub2, n2, n1));
			children.add(buildChild(p2, pM2, sub1, n2, n1));
		}
		return children;
	}
	//start inclusive stop exclusive
	public static Chromosome buildChild(ArrayList<String> scanThru, HashMap<String, Integer> scanThruMap, ArrayList<String> subList, int start, int stop){
		for(int i = 0; i < subList.size(); i++)
		{
			scanThru.remove(scanThru.indexOf(subList.get(i)));
			
		}
		ArrayList<String> retList = new ArrayList<String>();
		retList.addAll(scanThru.subList(0, start));
		retList.addAll(subList);
		retList.addAll(scanThru.subList(start, scanThru.size()));
		for(int i = 0; i < retList.size(); i++)
		{
			if(i == start) i += (stop-start);
			scanThruMap.put(retList.get(i), i);
		}
		return new Chromosome(retList, scanThruMap);
	}
}
