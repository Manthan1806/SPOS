import java.util.*;

public class Banker
{
	public static void main(String[] args)
	{
		int resources,processes;
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter number of resources : ");
		resources = in.nextInt();
		
		System.out.println("Enter number of processes : ");
		processes = in.nextInt();
		
		int[][] max = new int[processes][resources];
		int[][] alloc = new int[processes][resources];
		int[] resource = new int[resources];
		int[] avail = new int[resources];
		
		int i,j;
		
		System.out.println("Enter the Maximum resources required for each process : ");
		for(i=0;i<processes;i++)
		{
			for(j=0;j<resources;j++)
			{
				max[i][j] = in.nextInt();
			}
		}
		
		System.out.println("Enter the allocated resources for each process : ");
		for(i=0;i<processes;i++)
		{
			for(j=0;j<resources;j++)
			{
				alloc[i][j] = in.nextInt();
			}
		}
		
		System.out.println("Enter the Maximum resources available : ");
		for(i=0;i<resources;i++)
		{
			resource[i] = in.nextInt();
			avail[i] = resource[i];			//calculate available resources
		}
		
		for(i=0;i<processes;i++)
		{
			for(j=0;j<resources;j++)
			{
				avail[j] = avail[j] - alloc[i][j];
			}
		}
		
		isSafe(avail,max,alloc);				//checks if there is a safe sequence or not
		
		in.close();
	}
	
	public static boolean isSafe(int[] avail,int[][] max,int[][] alloc)
	{
		boolean safe = false;
		
		int p,r;
		p = max.length;
		r = avail.length;
		
		int[][] need = new int[p][r];
		
		int i,j;
		
		//Calculate need matrix
		for(i=0;i<p;i++)
		{
			for(j=0;j<r;j++)
			{
				need[i][j] = max[i][j] - alloc[i][j];
			}
		}

		int[] work = new int[r];
		for(i=0;i<r;i++)
		{
			work[i] = avail[i];
		}
		
		int count = 0;
		int[] safeSequence = new int[p];
		boolean[] finish = new boolean[p];
		
		while(count < p)
		{
			boolean found = false;
			for(int k=0;k<p;k++)
			{
				if(!finish[k])
				{
					int l;
					for(l=0;l<r;l++)
					{
						if(need[k][l] > work[l])
						{
							break;
						}
					}
					
					if(l==r)
					{
						for(int m=0;m<r;m++)
						{
							work[m] = work[m] + alloc[k][m];
						}
						
						safeSequence[count++] = k+1;
						finish[k] = true;
						
						found = true;
					}
				}
			}
			
			if(!found)
			{
				System.out.println("System is not in safe state");
				return safe;
			}
			
		}
		
		System.out.println("\nSystem is in safe state\n\nSafe sequence is : ");
		for(i=0;i<p;i++)
		{
			System.out.print(safeSequence[i] + " ");
		}
		System.out.println();
		
		safe = true;
		return safe;
	}
}