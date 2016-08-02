//import java.util.ArrayList;
//import java.util.Collections;
//
//public class HelloWorld {
//	
//	//public static final String TARGET = "";
//	public static final String TARGET = "hello world! My name is Joseph. I come from planet earth. I like icecream!";
//	public static final int T_SIZE = TARGET.length();
//	public static final int POPULATION_SIZE = 100;
//	public static final int GENERATIONS = 50000;
//	public static final double MUTATION_CHANCE = 0.5;
//	public static final double CROSSOVER_CHANCE = 0.8;
//	public static final int ELITES = 2;
//	
//	public static void main(String idgaf[])
//	{
//		long startTime = System.currentTimeMillis();
//		ArrayList<Chromosome> population = new ArrayList<Chromosome>();
//		initPopulation(population);
//		//for(int i = 0; i < GENERATIONS; i++)
//		while(!population.get(0).toString().equals(TARGET))
//		{
//			//System.out.println("Generation " + (i+1));
//			Collections.sort(population);
//			ArrayList<Chromosome> temp = new ArrayList<Chromosome>();
//			temp.addAll(population.subList(0, ELITES));
//			int pn = 0;
//			while(temp.size() < POPULATION_SIZE){
//				if(Math.random() < CROSSOVER_CHANCE && pn < (POPULATION_SIZE/2)-2)
//				{
//					ArrayList<Chromosome> parents = new ArrayList<Chromosome>();
//					ArrayList<Chromosome> children = new ArrayList<Chromosome>();
//					parents.add(population.get(pn));
//					parents.add(population.get(pn+1));
//					children.addAll(haveSex(parents));
//					for(int j = 0; j < children.size(); j++)
//					{
//						if(Math.random() < MUTATION_CHANCE){
//							children.add(mutate(children.get(j)));
//							children.remove(j);
//						}
//					}
//					temp.addAll(children);
//					pn+=2;
//				}
//				else
//				{
//					if(Math.random() < MUTATION_CHANCE)
//					{
//						temp.add(mutate(population.get(pn)));
//					}
//					pn++;
//				}
//				if(pn >= POPULATION_SIZE/2) pn = 0;
//			}
//			population.clear();
//			population.addAll(temp);
//			System.out.println(population.get(0).toString());
//		}
//		long stopTime = System.currentTimeMillis();
//		System.out.println("Operation took "+ ((stopTime-startTime)/1000L) + " seconds");
//		
//	}
//	
//	public static void initPopulation(ArrayList<Chromosome> population){
//		for(int i = 0; i < POPULATION_SIZE; i++)
//		{
//			char[] temp = TARGET.toCharArray();
//			ArrayList<Character> genes = new ArrayList<Character>();
//			for(int j = 0; j < temp.length; j++)
//			{
//				genes.add(temp[j]);
//			}
//			String newChromo = "";
//			while(!genes.isEmpty())
//			{
//				int index = (int)(Math.random()*genes.size());
//				newChromo += genes.get(index);
//				genes.remove(index);
//			}
//			population.add(new Chromosome(newChromo));
//		}
//	}
//	
//	public static Chromosome mutate(Chromosome c){
//		int n1 = (int)(Math.random()*T_SIZE);
//		int n2;
//		do{
//			n2 = (int)(Math.random()*T_SIZE);
//		}while(n1 == n2);
//		char c1 = c.toString().charAt(n1);
//		char c2 = c.toString().charAt(n2);
//		String ret = c.toString();
//		char[] retArr = ret.toCharArray();
//		retArr[n1] = c2;
//		retArr[n2] = c1;
//		ret = String.valueOf(retArr);
//		return new Chromosome(ret);
//	}
//	
//	public static ArrayList<Chromosome> haveSex(ArrayList<Chromosome> parents)
//	{
//		String p1 = parents.get(0).toString();
//		String p2 = parents.get(1).toString();
//		ArrayList<Chromosome> children = new ArrayList<Chromosome>();
//		int n1 = (int)(Math.random()*T_SIZE);
//		int n2;
//		do{
//			n2 = (int)(Math.random()*T_SIZE);
//		}while(n1 == n2);
//		if(n1 < n2)
//		{
//			String sub1 = p1.substring(n1, n2);
//			String sub2 = p2.substring(n1, n2);
//			String c1 = buildChild(p1, sub2, n1, n2);
//			String c2 = buildChild(p2, sub1, n1, n2);
//			children.add(new Chromosome(c1));
//			children.add(new Chromosome(c2));
//		}
//		else{
//			String sub1 = p1.substring(n2, n1);
//			String sub2 = p2.substring(n2, n1);
//			String c1 = buildChild(p1, sub2, n2, n1);
//			String c2 = buildChild(p2, sub1, n2, n1);
//			children.add(new Chromosome(c1));
//			children.add(new Chromosome(c2));
//		}
//		return children;
//	}
//	
//	public static String buildChild(String scanThru, String subString, int start, int stop){
//		for(int i = 0; i < subString.length(); i++)
//		{
//			switch(subString.charAt(i)){
//			case('.'): scanThru = scanThru.replaceFirst("\\.", ""); break;
//			case('*'): scanThru = scanThru.replaceFirst("/*/", ""); break;
//			default: scanThru = scanThru.replaceFirst(subString.charAt(i)+"", "");
//			}
//			
//		}
//		return scanThru.substring(0,start) + subString + scanThru.substring(start, scanThru.length());
//	}
//
//}
