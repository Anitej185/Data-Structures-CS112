package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2. Chain is returned as a
	 * sequence of names starting with p1, and ending with p2. Each pair (n1,n2) of
	 * consecutive names in the returned chain is an edge in the graph.
	 * 
	 * @param g  Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there
	 *         is no path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {

		Queue<Person> q = new Queue<>();
		int location = g.map.get(p1);

		q.enqueue(g.members[location]);
		
		boolean[] visited = new boolean[g.members.length];
		
		Queue<ArrayList<String>> correspondingList = new Queue<>();
		
		ArrayList<String> shortestPath = new ArrayList<>();

		shortestPath.add(g.members[location].name);
		correspondingList.enqueue(shortestPath);

		while (!q.isEmpty()) 
		{
			Person person = q.dequeue();
			
			int perInd = g.map.get(person.name);
			
			visited[perInd] = true;
			
			ArrayList<String> list = correspondingList.dequeue();
			Friend fr = g.members[perInd].first;

			while (!(fr == null)) 
			{
				if (!visited[fr.fnum]) 
				{
					ArrayList<String> path = new ArrayList<>(list);
					String name = g.members[fr.fnum].name;

					path.add(name);

					if (name.equals(p2)) 
						return path;

					q.enqueue(g.members[fr.fnum]);
					correspondingList.enqueue(path);
				}
				fr = fr.next;
			}
		}
		return null;
	}

	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g      Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there
	 *         is no student in the given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {

		boolean[] visited = new boolean[g.members.length];
		ArrayList<ArrayList<String>> masterList = new ArrayList<>();
		
		for(int v = 0; v < g.members.length; v++) 
		{
			if(visited[v] || !g.members[v].student)
				continue;
			
			ArrayList<String> smallList = new ArrayList<>();
			helper(g, visited, smallList, school, v);
			
			if(smallList != null && smallList.size() > 0)
				masterList.add(smallList);
			
		}
		return masterList;
	}
	
	private static void helper(Graph g, boolean[] visited1, ArrayList<String> smallList, 
			String schoolName, int indexOfNode) 
	{
		if(!visited1[indexOfNode] && g.members[indexOfNode].student && 
				g.members[indexOfNode].school.equals(schoolName))
			
			smallList.add(g.members[indexOfNode].name);
		
		visited1[g.map.get(g.members[indexOfNode].name)] = true;
		Friend curr = g.members[indexOfNode].first;
		
		while(!(curr == null)) 
		{
			int num = curr.fnum;
			
			if(visited1[num] != true && g.members[num].student && g.members[num].school.equals(schoolName)) 
			{
				helper(g, visited1, smallList, schoolName, num);
			}
			curr = curr.next;
		}
	}

	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no
	 *         connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {

		if (g == null)
			return null;

		boolean[] visit = new boolean[g.members.length];

		ArrayList<String> listConnectors = new ArrayList<>();

		ArrayList<String> backTrack = new ArrayList<String>();

		int[] numsDFS = new int[g.members.length];

		int[] before = new int[g.members.length];

		for (int i = 0; i < g.members.length; i++) {
			boolean b = visit[i];
			
			if (!b)
				listConnectors = dfs(listConnectors, g, g.members[i], visit, new int[] { 0, 0 }, 
						numsDFS, before, backTrack, true);
		}
		return listConnectors;
	}

	private static ArrayList<String> dfs(ArrayList<String> listOfConnectors, Graph graph, 
			Person p, boolean[] visit, int[] nums, int[] dfsnum, int[] back, 
			ArrayList<String> backward, boolean started) {
		
		visit[graph.map.get(p.name)] = true;

		Friend curr = p.first;

		dfsnum[graph.map.get(p.name)] = nums[0];
		
		back[graph.map.get(p.name)] = nums[1];

		while (!(curr == null)) {

			if (visit[curr.fnum] != true) {
				
				nums[0]++;
				nums[1]++;

				listOfConnectors = dfs(listOfConnectors, graph, graph.members[curr.fnum], 
						visit, nums, dfsnum, back,
						backward, false);

				if (back[curr.fnum] >= dfsnum[graph.map.get(p.name)]) 
				{
					if ((listOfConnectors.contains(p.name) != true && started != true)
							|| (listOfConnectors.contains(p.name) != true 
							&& backward.contains(p.name)))
					{
						listOfConnectors.add(p.name);
					}
				}

				else 
				{
					int num1 = back[graph.map.get(p.name)];

					int num2 = back[curr.fnum];

					if (num2 < num1)
						back[graph.map.get(p.name)] = num2;
						

					else
						back[graph.map.get(p.name)] = num1;
				}
				
				backward.add(p.name);
			}

			else 
			{
				int num3 = back[graph.map.get(p.name)];

				int num4 = dfsnum[curr.fnum];

				if (num4 < num3)
					back[graph.map.get(p.name)] = num4;
					

				else
					back[graph.map.get(p.name)] = num3;
			}
			curr = curr.next;
		}
		return listOfConnectors;
	}
}