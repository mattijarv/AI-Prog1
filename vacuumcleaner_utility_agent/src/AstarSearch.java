import java.util.*;

public class AstarSearch
{
    public static int heuristic(WeightedState check) {
        int returnValue = 0;
        for(Point p : check.dirt) {
            int temp = Math.abs(p.x-check.curPos.x)+Math.abs(p.y-check.curPos.y); //manhattan distance í næsta punkt
            returnValue += temp;
        }
        return returnValue;
    }
    
	public static Stack<Point> getPath(Point home, Point size, ArrayList<Point> dirt, ArrayList<Point> bumps)
	{
		WeightedState cur = new WeightedState(home, null,new HashSet<Point>(dirt),new HashSet<Point>(bumps));

		//Shearch for the path.
		PriorityQueue<WeightedState> frontier = new PriorityQueue<WeightedState>();
		HashSet<WeightedState> visited = new HashSet<WeightedState>();
		while (cur.dirt.size() > 0)
		{
			if (!visited.contains(cur))
			{
				cur.weight += heuristic(cur);
				visited.add(cur);
				for (WeightedState p : cur.legalMoves(size.x,size.y)){
					if (!frontier.contains(p)) 
						frontier.add(p);
					else{// Need to update the exsting state.
						boolean needUpdate = false;
						for(WeightedState s : frontier) {
							if (s.equals(p) && s.weight > p.weight)
								needUpdate = true;
						}
						if (needUpdate) {// Update the weight. 
							frontier.remove(p); // This contains the old weight
							frontier.add(p);	// and this the new one.
						}
					}
				}
			}
			if  (frontier.size() > 0)
				cur = frontier.remove();
			else
				break; // No solution found.
		}
		//Get home ! :)
		frontier = new PriorityQueue<WeightedState>();
		visited = new HashSet<WeightedState>();
		while (!cur.curPos.equals(home))
		{
			if (!visited.contains(cur))
			{
				visited.add(cur);
				for (WeightedState p : cur.legalMoves(size.x,size.y)){
					if (!frontier.contains(p)) 
						frontier.add(p);
					else{// Need to update the exsting state.
						boolean needUpdate = false;
						for(WeightedState s : frontier) {
							if (s.equals(p) && s.weight > p.weight)
								needUpdate = true;
						}
						if (needUpdate) {// Update the weight. 
							frontier.remove(p); // This contains the old weight
							frontier.add(p);	// and this the new one.
						}
					}
				}
			}
			if (frontier.size() > 0)
				cur = frontier.remove();
			else
				break; // No solution found.
		}
		//Building the path.
		Stack<Point> path = new Stack<Point>();	
        System.out.println("Path:");
		for	(;cur != null; cur = cur.prevPos){			
            System.out.println("x: " + cur.curPos.x + " y:" + cur.curPos.y);
			path.push(cur.curPos);
		}
		path.pop();
		return path;
	}
}
