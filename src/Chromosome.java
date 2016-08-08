import java.util.ArrayList;
import java.util.HashMap;

public class Chromosome implements Comparable<Chromosome>{
	
	private ArrayList<String> chromo;
	private HashMap<String, Integer> chromoMap;
	private int fitnessScore;
	
	public Chromosome()
	{
		this.chromo = new ArrayList<String>();
		this.fitnessScore = 0;
	}
	
	public Chromosome(ArrayList<String> chromo, HashMap<String, Integer> chromoMap)
	{
		this.chromo = chromo;
		this.chromoMap = chromoMap;
		this.fitnessScore = FitnessUtil.fitnessOf(chromo, chromoMap);
	}
	
	public static HashMap<String, Integer> generateHashMap(ArrayList<String> chromo)
	{
		HashMap<String, Integer> h = new HashMap<String, Integer>();
		for(int i = 0; i < chromo.size(); i++)
		{
			h.put(chromo.get(i), i);
		}
		return h;
	}
	
	public HashMap<String, Integer> getIndexMap()
	{
		return this.chromoMap;
	}
	
	public void setGenes(int index, String element){
		this.chromo.set(index, element);
	}
	
	public void setFitnessScore(int fitnessScore){
		this.fitnessScore = fitnessScore;
	}
	
	public int getFitnessScore()
	{
		return this.fitnessScore;
	}
	
	public String toString()
	{
		return this.chromo.toString();
	}
	
	public ArrayList<String> getGenes()
	{
		return this.chromo;
	}

	@Override
	public int hashCode() {
		return this.chromo.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (this.chromo.toString()).equals(((Chromosome)(obj)).chromo.toString());
	}

	@Override
	public int compareTo(Chromosome c) {
		return c.fitnessScore - this.fitnessScore;
	}
	
	
	
	
	
	

}
