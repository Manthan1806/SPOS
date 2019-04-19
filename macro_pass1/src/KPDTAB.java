import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class KPDTAB
{
	int index;
	String[] name = new String[10];
	String[] value = new String[10];
	
	public KPDTAB()
	{
		index = 0;
	}
	
	public void write() throws IOException
	{
		BufferedWriter out = new BufferedWriter(new FileWriter("/home/manthan/SPOS/macro_pass1/src/KPDTAB.txt"));
		int i=0;
		while(i<index)
		{
			String write = new String();
			write = write + (i+1) + " " + name[i] + " ";
			if(value[i].length() == 0)
			{
				write = write + "----" + "\n";
			}
			else
			{
				write = write + value[i] + "\n";
			}
			i++;
			out.write(write);
		}
		out.close();
	}
}