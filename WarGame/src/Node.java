

public class Node implements Comparable<Node>{
	private int score;
	private int ownership;
	private int x;
	private int y;
	int maxrecordSearchedX = 0;
	int maxrecordSearchedY = 0;
	private int accumulatedScore = 0;
	public boolean comquared = false;
	public Node parentNode;
	static public int ev;
	static public int enemy;
	static boolean noPoll=false;
	
	
	
	public Node(Integer score,int x,int y, int ownership){
		this.score = score;
		this.ownership = ownership; //(0,1,2) = (free, P1, P2)
		this.x = x;
		this.y = y;
		this.enemy = 0;
		this.nodeExpanded = 0;
	}
	
	 public int compareTo(Node arg0) 
     {
         if(this.getAccumulated() < arg0.getAccumulated())
         {
             return -1;
         }
         else if(this.getAccumulated()> arg0.getAccumulated())
         {
             return 1;
         }
         

         return 0;
     }
	
	public int getAccumulated()
	{
		return accumulatedScore;
	}

	public void setAccumulated(int newScore)
	{
		accumulatedScore=newScore;
	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void setX(int value)
	{
		x = value;
	}
	public void setY(int value)
	{
		y = value;
	}
	static long nodeExpanded;
	
	

	
	public int getScore(){
		return score;
	}
	
	public void setScore(int s)
	{
		score = s;
	}
	public int getOwnership(){
		return ownership;
	}
	
	public void setOwnership(int playerID){
		ownership = playerID;
	}
}
