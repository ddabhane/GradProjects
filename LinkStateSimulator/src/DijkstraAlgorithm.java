import java.io.*;
import java.util.*;

 
public class DijkstraAlgorithm
{		
	 int topology_matrix[][];						//Weighted matrix that stores the cost from one router to every router in the topology.
     int number_of_routers=0;						//This is the number of routers in the topology i.e number of rows and columns in topology_matrix[][].
     int source=0;
     int destination=0;	
     int router=0;									// Router to be removed when topology is to be modified.
     int distance[];								// Used to store minimum cost from source to every router in the topology.
     int path[];									// Used to display connection table.
     int path1[];									// Used to display shortest path from source to destination.
	
     HashSet<Integer> set1= new HashSet<Integer>();	//It stores the routers who has not yet been processed. 
     HashSet<Integer> set2= new HashSet<Integer>(); //It stores the routers which were in set1 and have already been processed. 
    
     List<Integer> set3= new ArrayList<Integer>();  //It is used to store the content of path1[] while displaying shortest path. 
     List<Integer> router_removed= new ArrayList<Integer>();// It is used to store the routers which are removed from the topology. 
     
     public void Dijkstra_Shortest_Path() 
     {	
    	int i=1;
    	
    	distance= new int[number_of_routers + 1];
    	path=new int[number_of_routers +1];
      	path1=new int[number_of_routers +1];
        int processing_node;
    	for (i = 1; i <= number_of_routers; i++)
        {
        	distance[i] = Integer.MAX_VALUE; 	//Initially distance[] holds all infinite values. 
        	path[i]=0;						
        	path1[i]=0;
        } 
    	
    	set1.add(source); 						// Source router added into set1 before processing.
        distance[source] = 0;  					// Cost from source to source is always 0.
        
        if(set2.isEmpty()==false)		    	// It is ensured that set2 is empty before the processing starts.
    	{
    		set2.clear();
    	}
        while (!set1.isEmpty())					// The processing will continue until set1 contains no router.
        {	
        	
            processing_node = MinSet1Node(); 	// The router being processed 
            set1.remove(processing_node);		// Once the router has been processed it is removed from set1 and added to set2. 
            set2.add(processing_node);
            Neighbour_Cost(processing_node);	// Cost with the neighbor is evaluated for every router and distance[], path[] and path1[] are updated. 
        } 
        
     }
     
    //Finds node having minimum distance from set 1 
    public int MinSet1Node()														
    {	
    	int min ;
        int node = 0;
        int i=1;
        Iterator<Integer> iterator = set1.iterator();
        node = iterator.next();
        min = distance[node];					 // min holds distance of next router within set1.
        while(i <= distance.length)				 
        {
    	   if (set1.contains(i))
           {
               if (distance[i] <= min)			// Checks if the distance of subsequent routers within set1 less than current router(min).
               {
                   min = distance[i];
                   node = i;					// router within set1 having minimum distance becomes the processing node.
               }
           }
    	   i++;
        }
       return node;
    }
    
    /* Calculates the minimum distance from routers(processing_node) within set1 to its neighboring routers and
     * Updates the distance array, path array and path1 array.
     */
	public void Neighbour_Cost(int processing_node)
    {
		
        int edgeDistance = 0;
        int newDistance = 0;
 
        for (int dest_node = 1; dest_node <= number_of_routers; dest_node++) 
        {
            if (!set2.contains(dest_node))
            {
                if (topology_matrix[processing_node][dest_node] != Integer.MAX_VALUE)
                {   
                	edgeDistance = topology_matrix[processing_node][dest_node];  //cost between the processing node and its neighbor is evaluated.
                    
                    newDistance = distance[processing_node] + edgeDistance;
                    if (newDistance < distance[dest_node]) 						 //if newDistance less than the current distance is checked.
                    {
                        distance[dest_node] = newDistance;    					 //Updating distance array with the minimum distance.
                        path1[dest_node]=processing_node;						 // path1 is updated for calculating shortest path. 
                        
                        int i=processing_node;									
                        
                        //path is updated for displaying connection table. 
                        if(i==source)
                        path[dest_node] = processing_node;							
                        
                        else if(path[i]==source && path[dest_node]!=source)
                        path[dest_node] = processing_node;
                        
                        else
                        {	
                        	while(path[i]!=source)
                        	{	
                        	path[dest_node]=path[i];
                        	i=path[i];
                        	}
                        }
                        
                    }
                 // After evaluating the path and distance between processing node and its neighbor, the neighboring router is added to set1.
                    set1.add(dest_node); 
                }
            }
        }
    }
	
	/* Takes topology matrix from the text file, displays it.  
	 * Calculates the number of routers.
	 */
	public void inputFile() throws IOException 
	{
		try{
		Scanner s= new Scanner(System.in);
		System.out.println("Input Original Network Topology matrix Data File");
		System.out.println("Input:");
		String file=s.nextLine(); // The file with .txt extension that contains topology matrix is given as input.
		StringBuffer path=new StringBuffer("C:/Users/Darshan/workspace/CN Project/bin/"); //Enter the path of the folder that contains the input text file.
		path.append(file);
		String filename = path.toString();     
		String text = new String();
		FileReader fr = new FileReader(filename);
		FileReader fr2 = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		BufferedReader br2 = new BufferedReader(fr2);
		
		
		int routers = 0;
		while (br.readLine() != null) routers++;	//number of routers are evaluated from the topology matrix				
		br.close();
		number_of_routers = routers;
		
		topology_matrix = new int[number_of_routers + 1][number_of_routers + 1];
		
		System.out.println("Review original topology matrix:");
		
		// Topology matrix within the text file  is displayed for review. 
		for(int i=1;i<=number_of_routers;i++)
			{
				text = br2.readLine();
			
				text = text + " ";
				for(int j=1;j<=number_of_routers;j++)
				{
					topology_matrix[i][j] = Integer.parseInt(text.substring(0,text.indexOf(' ')));
					text = text.substring(text.indexOf(' ')+1);
					System.out.print(topology_matrix[i][j] + "\t");
				}
				System.out.println();
			}
		br2.close();
		}
		catch(FileNotFoundException f)
		{
			System.out.println("Please enter valid file name.");
		}
	}
	
	/* Calls Dijkstra_Shortest_Path method and evaluates first hop from the source for every router in the topology.
	 * It makes use of path array for that purpose.
	 */
	public void ConnectionTable()
			{   
			if(number_of_routers!=0)
				{
				Scanner s = new Scanner(System.in);
				System.out.println("Enter the source router: ");
				source = s.nextInt();
				System.out.println();
				if(source<=number_of_routers && source!=0 && source>0)
				{
				
					for(int p=1;p<=number_of_routers;p++)
						for(int q=1;q<=number_of_routers;q++)
						{
							if (p == q)
							{
								topology_matrix[p][q] = 0;
								continue;
							}
							if (topology_matrix[p][q]==-1)	// The cost within the matrix having value -1 is replaced with Integer maximum value(Infinity). 
							{
								topology_matrix[p][q] = Integer.MAX_VALUE;
							}
						}	
					Dijkstra_Shortest_Path(); 
				//Connection table is displayed using the path array. 
					for(int i=1; i<=number_of_routers; i++)
					{
						if(topology_matrix[source][i]!=Integer.MAX_VALUE)
						{	
							path1[i]=i;
							path[i]=i;
						}
					}
				
					System.out.println("Router"+" "+source+" "+"Connection Table");
					System.out.println("Destination"+"\t"+"Interface");
					System.out.println("=========================");
			
					for (int i=1; i<= path.length-1; i++)
					{
						if(i!=source)
							System.out.println("      "+i+"             "+path[i]);
						else 
							System.out.println("      "+i+"             -");
					}
				
				}
			else
				System.out.println("No such router exists. Please enter a valid source.\n");
				}
			
			else
				System.out.println("Please select Menu 1 and provide valid input textfile which contains topology matrix.\n");
			}
	
	/*Makes use of the path1 array for displaying the shortest path from source to destination.
	 *Makes use of the distance array for displaying the minimum distance.
	 */
	public void SourceToDestination()
		{   
		if(source!=0)
			{
			if(set3.isEmpty()==false)
			{
				set3.clear();
			}
			int i=0,j=0;
			Scanner s = new Scanner(System.in);
			System.out.println("Enter the destination router:");
			destination = s.nextInt();	
			if(destination!=source)
			{	
			//Content of path1 is put into array list set3 in order to display shortest path and distance array is used for displaying total cost.
			if(destination<=number_of_routers && destination!=0 && destination>0)
			{
				 i=destination;
				while(path1[i]!=i)
				{
					set3.add(i);
					i=path1[i];
				
				}
				set3.add(i);
				set3.add(source);
				Collections.reverse(set3);
				Iterator<Integer> iter = set3.iterator();
				System.out.print("The shortest path from  " +source+ " to " +destination+ " is ");
				
				while (iter.hasNext()) 
				{
					j=iter.next();
					if(j!=destination)
						System.out.print(j+"-");
					else
						System.out.print(j);
				}
				System.out.print(", the total cost value is " + distance[destination]+".\n");
				}
			else
				System.out.println("No such router exists. Please enter a valid destination.\n");
			}
			else
				System.out.println("The shortest distance from "+source+" to "+destination+" is "+source+" ,total cost is 0.\n");
			}
		else
			System.out.println("Please select Menu 2 and enter the source router. \n");
			}	
			
	// Modifies topology by removing the router.
	public void ModifyTopology()
	{	
	  int p=0;
	  if(source!=0)
		{
		
		  Scanner s= new Scanner(System.in);
		  System.out.println("Select a router to be removed:");
		  router=s.nextInt();
		
		  Iterator<Integer> r = router_removed.iterator();
		  if(router_removed.isEmpty()==false)				
		  {
			  while(r.hasNext())
			  {
				  p=r.next();
				  if(p==router)
				  {
					  break;
				  }
			  }
		  }
		if(p!=router)
		{
		for(int i=1;i<=number_of_routers;i++)
			for(int j=1;j<=number_of_routers;j++)
			{
				if(i==router||j==router)
				{
					topology_matrix[i][j]=-1;			 //Router cost set to infinity. Thus removing it.
					router_removed.add(router);
				}
				if (i == j)
				{
					topology_matrix[i][j] = 0;
					continue;
				}
				if (topology_matrix[i][j]==-1)
				{
				topology_matrix[i][j] = Integer.MAX_VALUE;
				}
			}
		
		Dijkstra_Shortest_Path();						//Calculates Shortest path and distance for the updated topology.
		for(int i=1; i<=number_of_routers; i++)
		{
			if(topology_matrix[source][i]!=Integer.MAX_VALUE)
			{
				path[i]=i;
			}
		}
		// Displays connection table for updated topology.
		path[source]=0;
		System.out.println("Router"+" "+source+" "+"Connection Table");
		System.out.println("Destination"+"\t"+"Interface");
		System.out.println("=============================");
		
		for (int i=1; i<= path.length-1; i++)
		
		{		
			if(i!=router && path[i]!=0)
			System.out.println("      "+i+"             "+path[i]);
			
			else if(i==source)
			System.out.println("      "+i+"             -");
		    
			else
		    System.out.println("      "+i+"         No Connection");
		}
		if(destination!=source)
		{
		if(destination!=0)
		{	
			// Displays shortest path from source(entered in option 2) to destination(entered in option 3) and total cost between them. 
			if(set3.isEmpty()==false)
			{
				set3.clear();
			}
			
			int i=0,j=0;
			i=destination;
			while(path1[i]!=i)
			{	
				set3.add(i);
				i=path1[i];
			}
		
			Collections.reverse(set3);
			Iterator<Integer> iter = set3.iterator();
			System.out.print("The shortest path from  " +source+ " to " +destination+ " is ");
			
			while (iter.hasNext()) 
			{
				j=iter.next();
				if(j!=destination)
					System.out.print(j+"-");
				else
					System.out.print(j);
			}
			System.out.print(", the total cost value is " + distance[destination]+".\n");
		}
		else 
			ShortestPath();	//Displays shortest path when option 3 is not selected. Hence user is asked to enter destination in option 4.
		}
		else
		System.out.println("The shortest distance from "+source+" to "+destination+" is "+source+" ,total cost is 0.\n");
		
		}
		else
			System.out.println("Router already removed. Please select the menu option and try again.\n");
		
		}
	  else
		  System.out.println("Please select Menu 2 and enter the source router.\n");
	}
	
	public void ShortestPath()
	{
		int i,j;
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the destination router:");
		destination = s.nextInt();
		if(destination!=source)
		{
		if(destination<=number_of_routers && destination!=0 && destination>0)
		{
			i=destination;
			while(path1[i]!=i)
			{
				set3.add(i);
				i=path1[i];
			}
			Collections.reverse(set3);
			Iterator<Integer> iter = set3.iterator();
			System.out.print("The shortest path from  " +source+ " to " +destination+ " is ");
			
			while (iter.hasNext()) 
			{
				j=iter.next();
				if(j!=destination)
					System.out.print(j+"-");
				else
					System.out.print(j);
			}
			System.out.print(", the total cost value is " + distance[destination]+".\n");
		}
		else 
			System.out.println("No such router exists. Please enter a valid destination.\n");
		}
		else
			System.out.println("The shortest distance from "+source+" to "+destination+" is "+source+" ,total cost is 0.\n");
			
	}
	
	//Exits the Link State Routing Simulator.	
	public void exit()
		{
		System.out.println("Exit CS542 Project. Good Bye!");
		}
	
	
	public void mainMenu() throws IOException
	{
		
    	int menuChoice=0;	
    	
        
	  //Main menu that allows user to navigate through the control of the Link State Routing Simulator.
      do
        {
				try{
					Scanner s = new Scanner(System.in);
					String mainMenu = ("\nLink State Routing Simulator: \n" 
							+ "(1) Create Network Topology.\n" 
							+ "(2) Build Connection Table.\n"
							+ "(3) Shortest Path to Destination Router.\n"
							+ "(4) Modify a topology.\n"
							+ "(5) Exit.");

					System.out.println(mainMenu);
					System.out.println("Command:");
					menuChoice = s.nextInt();
			

					if(menuChoice > 0 && menuChoice <6) //menuChoice > 0 && menuChoice < 4
					{
						if(menuChoice==1)
						{
							inputFile();	//Command 1
						}
						if(menuChoice==2)
						{
							ConnectionTable();	//Command 2
						}
						if(menuChoice==3)
						{
							SourceToDestination();	//Command 3
						}									
						if(menuChoice==4)
						{
							ModifyTopology();  //Command 4
						}
						if(menuChoice==5)
						{
							exit(); 		//Command 5
						}
					}
					else
						System.out.println("Please select valid menu option, no menu option "+menuChoice+" exists.");
				}
				catch(Exception e)
				{
					System.out.println("Please enter valid data.\n");
				}
				
		}while(menuChoice!=5);

       	
	}
 
    public static void main(String [] args) throws IOException
    {   
    	DijkstraAlgorithm dij = new DijkstraAlgorithm();
    	dij.mainMenu();
    }
}