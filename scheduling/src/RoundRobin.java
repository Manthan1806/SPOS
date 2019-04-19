import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class RoundRobin
{
	int[] arrival;
	int[] burst ;
	int[] burstCopy;
	int[] start;
	int[] end;
	int exec;
	Queue<Integer> readyQueue = new LinkedList<>();
	int timeSlice;
	List<Integer> process = new ArrayList<Integer>();
	List<Integer> timeline = new ArrayList<Integer>();
	
	public RoundRobin(int n)
	{
		end = new int[n];
		for(int i=0;i<n;i++)
		{
			end[i] = Integer.MAX_VALUE;
		}
		arrival = new int[n];
		burst = new int[n];
		burstCopy = new int[n];
		start = new int[n];
	}

	public void sort(int n)
	{
		int i,j,small;
		int temp;
		for(i=0;i<n;i++)
		{
			small=i;
			for(j=i+1;j<n;j++)
			{
				if(arrival[small]>arrival[j])
				{
					small=j;
				}
			}
			if(small!=i)
			{
				temp=arrival[i];
				arrival[i]=arrival[small];
				arrival[small]=temp;
				temp=burst[i];
				burst[i]=burst[small];
				burst[small]=temp;
				temp=burstCopy[i];
				burstCopy[i]=burstCopy[small];
				burstCopy[small]=temp;
			}
		}
	}
	
	public void input(int n,Scanner in)
	{
		System.out.println("Enter arrival time : ");
		for(int i=0;i<n;i++)
		{
			arrival[i] = in.nextInt();
		}
		System.out.println("Enter burst time : ");
		for(int i=0;i<n;i++)
		{
			burst[i] = in.nextInt();
			burstCopy[i] = burst[i];
		}
		System.out.println("Enter time slice : ");
		timeSlice = in.nextInt();
	}
	
	public void RR(int n)
	{
		int t=0,k,m=1;
		readyQueue.add(0);
		t = arrival[0];
		while(!checkFinish(n))
		{
			if(readyQueue.isEmpty())
			{
				t = arrival[m];
				readyQueue.add(m);
				if(m<n-1)
				{
					m++;
				}
			}
			else
			{
				k=readyQueue.poll();
				if(burst[k]<=timeSlice)
				{
					timeline.add(t);
					process.add(k+1);
					t = t + burst[k];
					burst[k]=0;
					end[k] = t;
					if(m<n-1)
					{
						while(arrival[m]<=t)
						{
							readyQueue.add(m);
							if(m<n-1)
								m++;
							else
								break;
						}	
					}
					
				}
				else
				{
					timeline.add(t);
					process.add(k+1);
					t = t + timeSlice;
					burst[k] = burst[k]-timeSlice;
					if(m<n-1)
					{
						while(arrival[m]<=t)
						{
							readyQueue.add(m);
							if(m<n-1)
								m++;
							else
								break;
						}	
					}
					readyQueue.add(k);
				}	
			}
		}
	}
	
	public boolean checkFinish(int n)
	{
		for(int i=0;i<n;i++)
		{
			if(end[i]==Integer.MAX_VALUE)
			{
				return false;
			}
		}
		return true;
	}
	
	public void output(int n)
	{
		System.out.println("Wait time is : ");
		for(int i=0;i<n;i++)
		{
			System.out.println(i + " : " +(end[i]-arrival[i]-burstCopy[i]));
			
		}
		System.out.println("Turn-around time is : ");
		for(int i=0;i<n;i++)
		{
			System.out.println(i + " : " +(end[i]-arrival[i]));
			
		}
		System.out.println("Timeline for each process is : ");
		Iterator<Integer> processIt = process.iterator();
		Iterator<Integer> time = timeline.iterator();
		while(time.hasNext())
		{
			System.out.println(time.next()+" : "+processIt.next());
		}
	}
}
