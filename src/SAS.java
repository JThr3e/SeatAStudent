import java.io.File;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class SAS {
	
	public static int POPULATION_SIZE = 400;
	public static int DYNAMIC_POPULATION_SIZE = 400;
	public static int GENERATIONS_NO_CHANGE = 200;
	public static double MUTATION_CHANCE = 0.8;
	public static double CROSSOVER_CHANCE = 0.8;
	public static int ELITES = 2;
	public static int EPOCHS = 5;
	public static String CHART_FNAME = "chart.txt";
	public static String STUDENTS_FNAME = "students.txt";
	public static String SUBGROUP_FNAME = "subchart.txt";
	public static String SUBGROUP_GRAPH_FNAME = "subchart_graph.txt";
	
	public static int SAME_GROUP_AS_ENEMY = 0;
	public static int NEXT_GROUP_HAS_ENEMY = 0;
	public static int NEXT_TO_FRIEND = 0;
	public static int NEXT_TO_2WFRIEND = 0;
	public static int NO_PREF = 0;
	public static int NOT_NO_PREF = 0;
	public static String PREF_GROUP = "NOTHING_HERE";
	public static int IN_WANT_GROUP = 0;
	public static int IN_NOT_WANT_GROUP = 0;
	
	public static String isolateGoodStuff(String str)
	{
		String ret = "";
		boolean startRead = false;
		boolean stopRead = false;
		for(int i = 0; i < str.length(); i++)
		{
			if(startRead && !stopRead)
			{
				ret += str.charAt(i);
			}
			if(str.charAt(i) == '"'){
				if(startRead) stopRead = true;
				else startRead = true;
			}
		}
		return ret.substring(0, ret.length()-1);
	}
	
	public static void readInAndInitConfig()
	{
		String studentFile = JOptionPane.showInputDialog("Please input the file that contains the names of the students:");
		STUDENTS_FNAME = studentFile;
		try {
			Scanner file = new Scanner(new File("config.txt"));
			while (file.hasNextLine()) {
				String s = file.nextLine();
				s = s.replaceAll(" ", "");
				if(s.equals(""))
				{
					/*do nothing*/
				}
				else if(s.charAt(0) == '#')
				{
					/*do more nothing*/
				}
				else{
					if(s.contains("EPOCHS")){
						EPOCHS = Integer.parseInt(isolateGoodStuff(s));
						System.out.println("EPOCHS");
					}
					if(s.contains("POPULATION_SIZE")){
						POPULATION_SIZE  = Integer.parseInt(isolateGoodStuff(s));
						DYNAMIC_POPULATION_SIZE = POPULATION_SIZE;
						System.out.println("POPULATION_SIZE");
					}
					if(s.contains("GENERATIONS_NO_CHANGE")){
						GENERATIONS_NO_CHANGE  = Integer.parseInt(isolateGoodStuff(s));
						System.out.println("GENERATIONS_NO_CHANGE");
					}
					if(s.contains("MUTATION_CHANCE")){
						MUTATION_CHANCE  = Double.parseDouble(isolateGoodStuff(s));
						System.out.println("MUTATION_CHANCE");
					}
					if(s.contains("CROSSOVER_CHANCE")){
						CROSSOVER_CHANCE  = Double.parseDouble(isolateGoodStuff(s));
						System.out.println("CROSSOVER_CHANCE");
					}
					if(s.contains("ELITES")){
						ELITES  = Integer.parseInt(isolateGoodStuff(s));
						System.out.println("ELITES");
					}
					if(s.contains("CHART_FNAME")){
						CHART_FNAME  = isolateGoodStuff(s);
						System.out.println("CHART_FNAME");
					}
					if(s.contains("SUBGROUP_FNAME")){
						SUBGROUP_FNAME  = isolateGoodStuff(s);
						System.out.println("SUBGROUP_FNAME");
					}
					if(s.contains("SUBGROUP_GRAPH_FNAME")){
						SUBGROUP_GRAPH_FNAME  = isolateGoodStuff(s);
						System.out.println("SUBGROUP_GRAPH_FNAME");
					}
					
					if(s.contains("NEXT_TO_FRIEND")){
						NEXT_TO_FRIEND  = Integer.parseInt(isolateGoodStuff(s));
						System.out.println("NEXT_TO_FRIEND");
					}
					if(s.contains("NEXT_TO_2WFRIEND")){
						NEXT_TO_2WFRIEND  = Integer.parseInt(isolateGoodStuff(s));
						System.out.println("NEXT_TO_2WFRIEND");
					}
					if(s.contains("NO_PREF") && !s.contains("NOT_NO_PREF")){
						NO_PREF  = Integer.parseInt(isolateGoodStuff(s));
						System.out.println("NO_PREF");
					}
					if(s.contains("NOT_NO_PREF")){
						NOT_NO_PREF  = Integer.parseInt(isolateGoodStuff(s));
						System.out.println("NOT_NO_PREF");
					}
					if(s.contains("SAME_GROUP_AS_ENEMY")){
						SAME_GROUP_AS_ENEMY  = Integer.parseInt(isolateGoodStuff(s));
						System.out.println("SAME_GROUP_AS_ENEMY");
					}
					if(s.contains("NEXT_GROUP_HAS_ENEMY")){
						NEXT_GROUP_HAS_ENEMY  = Integer.parseInt(isolateGoodStuff(s));
						System.out.println("NEXT_GROUP_HAS_ENEMY");
					}
					if(s.contains("PREF_GROUP")){
						PREF_GROUP  = isolateGoodStuff(s);
						System.out.println("PREF_GROUP");
					}
					if(s.contains("IN_WANT_GROUP")){
						IN_WANT_GROUP  = Integer.parseInt(isolateGoodStuff(s));
						System.out.println("IN_WANT_GROUP");
					}
					if(s.contains("IN_NOT_WANT_GROUP")){
						IN_NOT_WANT_GROUP  = Integer.parseInt(isolateGoodStuff(s));
						System.out.println("IN_NOT_WANT_GROUP");
					}
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
			UIUtil.showOutput("Fatal Error, config.txt is corrupted or missing!");
			System.exit(0);
		}
	}
}
