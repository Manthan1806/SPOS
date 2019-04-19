import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MNT
{
	int index;
	String[] name = new String[10];
	int[] pp = new int[10];
	int[] kp = new int[10];
	int[] MDTP = new int[10];
	int[] KPDTP = new int[10];
	
	public MNT()
	{
		index = 0;
	}
	
	public void write() throws IOException
	{
		BufferedWriter out = new BufferedWriter(new FileWriter("/home/manthan/SPOS/macro_pass1/src/MNT.txt"));
		int i = 0;
		while(i<this.index)
		{
			String write = new String();
			write = write + (i+1) + " " + this.name[i] + " " + this.pp[i] + " " + this.kp[i] + " " + this.MDTP[i] + " " + this.KPDTP[i] + "\n";
			out.write(write);
			i++;
		}
		out.close();
	}
}