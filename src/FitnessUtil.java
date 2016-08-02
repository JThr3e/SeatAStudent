import java.util.ArrayList;

public class FitnessUtil {
	
	/*WIEGHTES*/
	private static final int SAME_GROUP_AS_ENEMY = 8;
	private static final int NEXT_GROUP_HAS_ENEMY = 2;
	private static final int NEXT_TO_FRIEND = 1;
	private static final int NEXT_TO_2WFRIEND = 2;
	private static final int NO_PREF = 2;
	private static final int NOT_NO_PREF = 4;
	
	
	private static int calcSubScore(int i, ArrayList<String> chromo)
	{
		ArrayList<Vertex<Integer>> viableNeighbors = new ArrayList<Vertex<Integer>>();

		viableNeighbors.addAll(SeatAStudent.target.getAllVerticies2FindNeighbors().get
	    		(i).allNeighbors());
		
		ArrayList<Integer> checkIndexes = new ArrayList<Integer>();
		for(Vertex<Integer> n : viableNeighbors)
		{
			checkIndexes.add((Integer)n.getData()-1);
		}
		//System.out.println(checkIndexes);
		ArrayList<String> neighbors = new ArrayList<String>();
		for(Integer index : checkIndexes)
		{
			neighbors.add(chromo.get(index));
		}
		
		ArrayList<Vertex<String>> wannaBNexTo = new ArrayList<Vertex<String>>();
		ArrayList<Vertex<String>> friends2Way = new ArrayList<Vertex<String>>();
		ArrayList<String> dontWannaBNexTo = new ArrayList<String>();
	    
	    wannaBNexTo.addAll(SeatAStudent.desired.getAllVerticies2FindNeighbors().get
	    		(SeatAStudent.desired.getAllVerticies2FindNeighbors().indexOf
	    				((new Vertex<String>(chromo.get(i))))).getOneWayNeighbors());
	    
	    friends2Way.addAll(SeatAStudent.desired.getAllVerticies2FindNeighbors().get
	    		(SeatAStudent.desired.getAllVerticies2FindNeighbors().indexOf
	    				((new Vertex<String>(chromo.get(i))))).getTwoWayNeighbors());
	    
	    dontWannaBNexTo.addAll(SeatAStudent.desired.getAllVerticies2FindNeighbors().get
	    		(SeatAStudent.desired.getAllVerticies2FindNeighbors().indexOf
	    				((new Vertex<String>(chromo.get(i))))).getNotNextTo());
	    
	    int subScore = 0;
	    int me = i+1;
	    String groupMe = SeatAStudent.indexToGroup.get(me);
	    //System.out.println("gm: "+groupMe);
	    for(String s : dontWannaBNexTo)
	    {
	    	int indexNotMe = chromo.indexOf(new Vertex<String>(s))+1;
	    	String groupNotMe = SeatAStudent.indexToGroup.get(indexNotMe);
	    	//System.out.println("gnm: "+groupNotMe);
	    	//System.out.println("me "+me+" indexNotMe "+indexNotMe+" groupMe "+groupMe + " groupNotMe "+ groupNotMe);
	    	if(groupMe.equals(groupNotMe))
	    	{
	    		subScore -= SAME_GROUP_AS_ENEMY;
	    	}
	    	else
	    	{
	    		if(SeatAStudent.subGraphs.isNextTo(new Vertex<String>(groupMe), new Vertex<String>(groupNotMe)))
	    		{
	    			subScore -= NEXT_GROUP_HAS_ENEMY;
	    		}	
	    	}
	    	
	    	
	    }
	    
	    boolean hasFriend = false;
		for(String n : neighbors)
		{
			if(wannaBNexTo.contains(new Vertex<String>(n)))
			{
				subScore += NEXT_TO_FRIEND;
				hasFriend = true;
			}
			if(friends2Way.contains(new Vertex<String>(n)))
			{
				subScore += NEXT_TO_2WFRIEND;
				hasFriend = true;
			}
			if(wannaBNexTo.isEmpty() && friends2Way.isEmpty())
			{
				subScore += NO_PREF;
				hasFriend = true;
			}
		}
		if(!hasFriend) subScore -= NOT_NO_PREF;
		
		return subScore;
	}
	
	public static int fitnessOf(ArrayList<String> chromo)
	{
		int score = 0;
		for(int i = 0; i < chromo.size(); i++)
		{
			int subScore = calcSubScore(i, chromo);
			score+=subScore;
		}
		return score;
	}
	
	public static ArrayList<Integer> getSubScoreArrayList(ArrayList<String> chromo)
	{
		ArrayList<Integer> subScores = new ArrayList<Integer>();
		for(int i = 0; i < chromo.size(); i++)
		{
			subScores.add(calcSubScore(i, chromo));
		}
		return subScores;
	}
	
	public static ArrayList<Integer> tester(ArrayList<String> chromo)
	{
		ArrayList<Integer> subScores = new ArrayList<Integer>();
		for(int i = 0; i < chromo.size(); i++)
		{
			System.out.println();
			System.out.println(chromo.get(i));
			ArrayList<Vertex<Integer>> viableNeighbors = new ArrayList<Vertex<Integer>>();

			viableNeighbors.addAll(SeatAStudent.target.getAllVerticies2FindNeighbors().get
		    		(i).allNeighbors());
			
			ArrayList<Integer> checkIndexes = new ArrayList<Integer>();
			for(Vertex<Integer> n : viableNeighbors)
			{
				checkIndexes.add((Integer)n.getData()-1);
			}
			//System.out.println(checkIndexes);
			ArrayList<String> neighbors = new ArrayList<String>();
			for(Integer index : checkIndexes)
			{
				neighbors.add(chromo.get(index));
			}
			
			ArrayList<Vertex<String>> wannaBNexTo = new ArrayList<Vertex<String>>();
			ArrayList<Vertex<String>> friends2Way = new ArrayList<Vertex<String>>();
			ArrayList<String> dontWannaBNexTo = new ArrayList<String>();
		    
		    wannaBNexTo.addAll(SeatAStudent.desired.getAllVerticies2FindNeighbors().get
		    		(SeatAStudent.desired.getAllVerticies2FindNeighbors().indexOf
		    				((new Vertex<String>(chromo.get(i))))).getOneWayNeighbors());
		    
		    friends2Way.addAll(SeatAStudent.desired.getAllVerticies2FindNeighbors().get
		    		(SeatAStudent.desired.getAllVerticies2FindNeighbors().indexOf
		    				((new Vertex<String>(chromo.get(i))))).getTwoWayNeighbors());
		    
		    dontWannaBNexTo.addAll(SeatAStudent.desired.getAllVerticies2FindNeighbors().get
		    		(SeatAStudent.desired.getAllVerticies2FindNeighbors().indexOf
		    				((new Vertex<String>(chromo.get(i))))).getNotNextTo());
		    
		    System.out.println(checkIndexes);
		    System.out.println(neighbors);
		    System.out.println(wannaBNexTo);
		    System.out.println(friends2Way);
		    System.out.println(dontWannaBNexTo);
		    int subScore = 0;
		    int me = i+1;
		    String groupMe = SeatAStudent.indexToGroup.get(me);
		    System.out.println("gm: "+groupMe);
		    for(String s : dontWannaBNexTo)
		    {
		    	int indexNotMe = chromo.indexOf(new Vertex<String>(s))+1;
		    	String groupNotMe = SeatAStudent.indexToGroup.get(indexNotMe);
		    	System.out.println("gnm: "+groupNotMe);
		    	System.out.println("me "+me+" indexNotMe "+indexNotMe+" groupMe "+groupMe + " groupNotMe "+ groupNotMe);
		    	if(groupMe.equals(groupNotMe))
		    	{
		    		subScore -= SAME_GROUP_AS_ENEMY;
		    		System.out.println("SAME_GROUP_AS_ENEMY");
		    	}
		    	else
		    	{
		    		if(SeatAStudent.subGraphs.isNextTo(new Vertex<String>(groupMe), new Vertex<String>(groupNotMe)))
		    		{
		    			subScore -= NEXT_GROUP_HAS_ENEMY;
		    			System.out.println("NEXT_GROUP_HAS_ENEMY");
		    		}	
		    	}
		    	
		    	
		    }
		    
			for(String n : neighbors)
			{
				if(wannaBNexTo.contains(new Vertex<String>(n))){
					subScore += NEXT_TO_FRIEND;
					System.out.println("NEXT_TO_FRIEND");
				}
				if(friends2Way.contains(new Vertex<String>(n))){
					subScore += NEXT_TO_2WFRIEND;
					System.out.println("NEXT_TO_2WFRIEND");
				}
				if(wannaBNexTo.isEmpty() && friends2Way.isEmpty()){
					subScore += NO_PREF;
					System.out.println("NO_PREF");
				}
			}
			if(!(wannaBNexTo.isEmpty() && friends2Way.isEmpty()) && subScore == 0){
				subScore -= NOT_NO_PREF;
				System.out.println("NOT_NO_PREF");
			}
			
			subScores.add(subScore);
			
		}
		return subScores;
		
	}

}
