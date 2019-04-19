import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Assembler
{
	public static void main(String[] args)throws IOException
	{
		BufferedReader in = new BufferedReader(new FileReader("/home/TE/3261_SPOS/pass2/src/pass1.txt"));
		BufferedWriter out = new BufferedWriter(new FileWriter("/home/TE/3261_SPOS/pass2/src/pass2.txt"));
		
		boolean generate = false;
		boolean declarative = false;
		int countSym = 0, countLit = 0, countCst = 0;
		
		String line = in.readLine();
		while(line!=null)
		{
			generate = false;
			declarative = false;
			String write = new String();
			String[] split1 = line.split("\\)");
			for(int i=0;i<split1.length;i++)
			{
				countSym = 0;
				countLit = 0;
				countCst = 0;
				String[] split2 = split1[i].split("\\,");
				if(i == 0 &&  split2[0].contains("IS"))
				{
					generate = true;
				}
				else if(i == 0 && split2[0].contains("DL"))
				{
					declarative = true;
				}
				if(generate)
				{
					for(int j=0;j<split2.length;j++)
					{
						if(split2[j].charAt(0) == '(')
						{
							split2[j] = split2[j].substring(1,split2[j].length());
						}
						split2[j] = split2[j].trim();
						if(split2[0].equals("S"))
						{
							if(countSym == 0)
							{
								String address = symbolAddress(Integer.parseInt(split2[1]));
								write = write + address + " ";
								countSym++;
							}
						}
						else if(split2[0].equals("L"))
						{
							if(countLit == 0)
							{
								String address = LiteralAddress(Integer.parseInt(split2[1]));
								write = write + address + " ";
								countLit++;
							}
						}
						else
						{
							int lORs=0;
							try
							{
								Integer.parseInt(split2[j]);
							}
							catch(Exception e)
							{
								lORs = 1;
							}
							if(lORs == 0)
							{
								write = write + Integer.parseInt(split2[j]) + " ";
							}
						}
						System.out.println(split2[j]);
					}
				}
				if(declarative)
				{
					for(int j=0;j<split2.length;j++)
					{
						if(split2[j].charAt(0) == '(')
						{
							split2[j] = split2[j].substring(1,split2[j].length());
						}
						split2[j] = split2[j].trim();
						if(split2[0].equals("C"))
						{
							if(countCst == 0)
							{
								String constant = split2[1].trim();
								write = write + constant + " ";
								countCst++;
							}
						}
						else
						{
							write = write + "0 ";
						}
					}
				}
				//System.out.println(split1[i]);
			}
			line = in.readLine();
			write = write + "\n";
			System.out.println(write);
			out.write(write);
		}
		in.close();
		out.close();
	}
	
	public static String symbolAddress(int index) throws IOException
	{
		BufferedReader sym = new BufferedReader(new FileReader("/home/TE/3261_SPOS/pass2/src/symTab.txt"));
		String result = null;
		String line;
		line = sym.readLine();
		while(line != null)
		{
			String[] split = line.split("\\s");
			if(Integer.parseInt(split[0].trim()) == index)
			{
				result = split[2];
			}
			line = sym.readLine();
		}
		sym.close();
		return result;
	}
	
	public static String LiteralAddress(int index) throws IOException
	{
		BufferedReader sym = new BufferedReader(new FileReader("/home/TE/3261_SPOS/pass2/src/litTab.txt"));
		String result = null;
		String line;
		line = sym.readLine();
		while(line != null)
		{
			String[] split = line.split("\\s");
			if(Integer.parseInt(split[0].trim()) == index)
			{
				result = split[2];
			}
			line = sym.readLine();
		}
		sym.close();
		return result;
	}
}