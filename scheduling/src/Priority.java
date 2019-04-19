import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Priority
{
	int[] arrival;
	int[] burst;
	int[] burstCopy;
	int[] start;
	int[] end;
	int[] priority;
	int exec;
	List<Integer> process = new ArrayList<Integer>();
	List<Integer> timeline = new ArrayList<Integer>();
	
	public Priority(int n)
	{
		arrival = new int[n];
		burst = new int[n];
		burstCopy = new int[n];
		start = new int[n];
		end = new int[n];
		priority = new int[n];
		for(int i=0;i<n;i++)
		{
			end[i] = Integer.MAX_VALUE;
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
		System.out.println("Enter priority : ");
		for(int i=0;i<n;i++)
		{
			priority[i] = in.nextInt();
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
		System.out.println("Turn around time is : ");
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
	
	public void schedule(int n)
	{
		int min = Integer.MAX_VALUE;
		int t=0,index=0;
		while(!checkFinish(n))
		{
			min = Integer.MAX_VALUE;
			for(int i=0;i<n;i++)
			{
				if(t>=arrival[i] && priority[i]<min && burst[i]>0)
				{
					min = priority[i];
					index = i;
				}
			}
			priority[index] = Integer.MAX_VALUE;
			timeline.add(t);
			process.add(index+1);
			t = t + burst[index];
			burst[index] = 0;
			if(burst[index]==0)
			{
				end[index]=t;
			}
		}
	}
}