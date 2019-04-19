import java.util.Scanner;

class FCFS
{
	int[] id = new int[5];
	int[] arrival = new int[5];
	int[] burst = new int[5];
	int[] wait = new int[5];
	int[] start = new int[5];
	int f,r,exec;
	
	public FCFS()
	{
		f=r=-1;
		start[0]=0;
	}
	
	public void input(int n,Scanner in)
	{
		int i;
		System.out.println("Enter the process id's : ");
		for(i=0;i<n;i++)
		{
			r++;
			id[r]=in.nextInt();
		}
		System.out.println("Enter the arrival time for each process : ");
		for(i=0;i<n;i++)
		{
			arrival[i]=in.nextInt();
		}
		System.out.println("Enter the burst time for each process : ");
		for(i=0;i<n;i++)
		{
			burst[i]=in.nextInt();
		}
	}
	
	public void schedule(int n)
	{
		exec = burst[0];
		for(int i=1;i<n;i++)
		{
			if(exec>arrival[i])
			{
				start[i]=exec;
			}
			else
			{
				start[i]=arrival[i];
			}
			exec = start[i]+burst[i];
		}
		for(int i=0;i<n;i++)
		{
			wait[i]=start[i]-arrival[i];
		}
	}
	
	public void output(int n)
	{
		int i;
		float avg_wait,avg_turn_around;
		avg_wait = avg_turn_around = 0;
		System.out.println("The timeline for each process is : ");
		for(i=0;i<n;i++)
		{
			System.out.println(id[i] + " : " + start[i]);
		}
		System.out.println("Waiting time for each process : ");
		for(i=0;i<n;i++)
		{
			System.out.println(id[i] + " : " + wait[i]);
			avg_wait = avg_wait+wait[i];
		}
		System.out.println("Average waiting time is : " + (double)(avg_wait/n));
		System.out.println("Turn-around time for each process : ");
		for(i=0;i<n;i++)
		{
			System.out.println(id[i] + " : " + (wait[i]+burst[i]));
			avg_turn_around = avg_turn_around + wait[i]+burst[i];
		}
		System.out.println("Average Turn-around time is : " + (double)(avg_turn_around/n));
	}
}