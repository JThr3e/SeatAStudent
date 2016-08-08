import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FitnessUtil {
	
	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////
	private static int calcSubScore(int i, ArrayList<String> chromo, HashMap<String, Integer> indexMap)
	{
		HashSet<Vertex<Integer>> viableNeighbors = SeatAStudent.target.getAllVerticies2FindNeighbors().get(i)
				.allNeighbors();
		Vertex<String> student = SeatAStudent.strToVertDesired.get(chromo.get(i));
		HashSet<Vertex<String>> wannaBNexTo = student.getOneWayNeighbors();
		HashSet<Vertex<String>> friends2Way = student.getTwoWayNeighbors();
		HashSet<String> dontWannaBNexTo = student.getNotNextTo();
		int pref = student.getPref();
	    
		if(chromo.get(i).toLowerCase().contains("emptyseat"))
		{
			return SAS.NO_PREF;
		}
		else{
			int subScore = 0;
		    String groupMe = SeatAStudent.indexToGroup.get(i);
		    
		    if(pref == 1 && SAS.PREF_GROUP.toLowerCase().equals(groupMe.toLowerCase()))
		    {
		    	subScore += SAS.IN_WANT_GROUP;
		    }
		    else if(pref == 2 && SAS.PREF_GROUP.toLowerCase().equals(groupMe.toLowerCase()))
		    {
		    	subScore -= SAS.IN_NOT_WANT_GROUP;
		    }
		    else if(pref == 0){
		    	/*DO NOTHING YAY*/
		    }
		    for(String s : dontWannaBNexTo)
		    {
		    	int indexNotMe = indexMap.get(s)+1;
		    	String groupNotMe = SeatAStudent.indexToGroup.get(indexNotMe);
		    	if(groupMe.equals(groupNotMe))
		    	{
		    		subScore -= SAS.SAME_GROUP_AS_ENEMY;
		    	}
		    	else
		    	{
		    		if(SeatAStudent.subGraphs.isNextTo(new Vertex<String>(groupMe), new Vertex<String>(groupNotMe)))
		    		{
		    			subScore -= SAS.NEXT_GROUP_HAS_ENEMY;
		    		}	
		    	}
		    	
		    }
		    boolean hasFriend = false;
			for(Vertex<Integer> n : viableNeighbors)
			{
				int index = (Integer)n.getData()-1;
				if(wannaBNexTo.contains(new Vertex<String>(chromo.get(index))))
				{
					subScore += SAS.NEXT_TO_FRIEND;
					hasFriend = true;
				}
				if(friends2Way.contains(new Vertex<String>(chromo.get(index))))
				{
					subScore += SAS.NEXT_TO_2WFRIEND;
					hasFriend = true;
				}
				if(wannaBNexTo.isEmpty() && friends2Way.isEmpty())
				{
					subScore += SAS.NO_PREF;
					hasFriend = true;
				}
				if(chromo.get(index).toLowerCase().contains("emptyseat")){
					subScore -=2;
				}
			}
			if(!hasFriend) subScore -= SAS.NOT_NO_PREF;
			
			return subScore;
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////
	
	public static int fitnessOf(ArrayList<String> chromo, HashMap<String, Integer> chromoMap)
	{
		int score = 0;
		for(int i = 0; i < chromo.size(); i++)
		{
			int subScore = calcSubScore(i, chromo, chromoMap);
			score+=subScore;
		}
		return score;
	}
	
	public static int fitnessNScoreOf(ArrayList<String> chromo, HashMap<String, Integer> chromoMap)
	{
		int score = 0;
		for(int i = 0; i < chromo.size(); i++)
		{
			int subScore = calcSubScore(i, chromo, chromoMap);
			if(subScore <= 0)
			{
				score+=subScore;
			}
		}
		return score;
	}
	
	public static ArrayList<Integer> getSubScoreArrayList(ArrayList<String> chromo, HashMap<String, Integer> chromoMap)
	{
		ArrayList<Integer> subScores = new ArrayList<Integer>();
		for(int i = 0; i < chromo.size(); i++)
		{
			subScores.add(calcSubScore(i, chromo, chromoMap));
		}
		return subScores;
	}
	
}