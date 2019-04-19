import java.util.Scanner;

public class schedule
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int choice,n;
		System.out.println("Enter choice of algorithm : \n1.FCFS\n2.SJF\n3.Priority\n4.Round-Robin\n");
		choice = in.nextInt();
		switch(choice)
		{
		case 1:
			FCFS f = new FCFS();
			System.out.println("Enter number of processes : ");
			n = in.nextInt();
			f.input(n,in);
			f.schedule(n);
			f.output(n);
			break;
		case 2:
			System.out.println("Enter number of processes : ");
			n = in.nextInt();
			SJF s = new SJF(n);
			s.input(n,in);
			s.sjf(n);
			s.output(n);
			break;
		case 3:
			System.out.println("Enter number of processes : ");
			n = in.nextInt();
			Priority p = new Priority(n);
			p.input(n,in);
			p.schedule(n);
			p.output(n);
			break;
		case 4:
			System.out.println("Enter number of processes : ");
			n = in.nextInt();
			RoundRobin r = new RoundRobin(n);
			r.input(n, in);
			r.sort(n);
			r.RR(n);
			r.output(n);
			break;
		}
		in.close();
	}
}