import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class KPDTAB 
{
	int[] index = new int[10];
	String[] name = new String[10];
	String[] value = new String[10];
	int count;
	
	public KPDTAB()
	{
		count = 0;
	}
	
	public void write() throws IOException
	{
		BufferedReader kpdtab = new BufferedReader(new FileReader("/home/manthan/SPOS/macro_pass2/src/KPDTAB.txt"));
		String readLine = kpdtab.readLine();
		while(readLine!=null)
		{
			String split[] = readLine.split("\\s");
			index[count] = Integer.parseInt(split[0]);
			name[count] = split[1];
			value[count] = split[2];
			count++;
			readLine=kpdtab.readLine();
		}
		kpdtab.close();
	}
	
	public int getIndex(String s,int kpdtp,int kp)
	{
		s = s.substring(s.indexOf("&")+1,s.indexOf("="));
		System.out.println("kpdtp : "+kpdtp);
		for(int i=kpdtp-1;i<kpdtp+kp-1;i++)
		{
			if(s.equals(name[i]))
			{
				return i;
			}
		}
		return -1;
	}
	
	public void fillDefault(String[] aptab,int pp,int kp,int kpdtp)
	{
		for(int i=0;i<count;i++)
		{
			System.out.println(index[i] + " " + name[i] + " " + value[i]);
		}
		// System.out.println("kpdtp : "+kpdtp+"\nkp : "+kp);
		for(int i=kpdtp-1;i<kpdtp+kp-1;i++)
		{
			if(!value[i].contains("---"))
			{
				aptab[pp+i-kpdtp+1] = value[i];
				System.out.println("index : " + (pp+i-kpdtp));
			}
		}
	}
}