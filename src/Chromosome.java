import java.util.ArrayList;

public class Chromosome implements Comparable<Chromosome>{
	
	private ArrayList<String> chromo;
	private int fitnessScore;
	
	public Chromosome()
	{
		this.chromo = new ArrayList<String>();
		this.fitnessScore = 0;
	}
	
	public Chromosome(ArrayList<String> chromo)
	{
		this.chromo = chromo;
		this.fitnessScore = FitnessUtil.fitnessOf(chromo);
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
