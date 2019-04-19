import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MacroAssemblerPass1
{
	public static void main(String[] args) throws IOException
	{
		PNTAB obj1 = new PNTAB();
		MNT mnt = new MNT();
		KPDTAB kpd = new KPDTAB();
		BufferedReader in = new BufferedReader(new FileReader("/home/manthan/SPOS/macro_pass1/src/code2.txt"));
		BufferedWriter out = new BufferedWriter(new FileWriter("/home/manthan/SPOS/macro_pass1/src/MDT.txt"));
		String line = in.readLine();
		boolean begin = false,generate = false,now = false;
		int tokens = 0,pntIndex = 1,kpdtp = 1,pp,kp,mdtp = 1;
		int mdtIndex = 1;
		pp = kp = 0;
		while(line!=null)
		{
			String write = new String();
			String[] split1 = line.split("\\s");
			tokens = split1.length;
			for(int k=0;k<tokens;k++)
			{
				String l = removeComma(split1[k]);
				// System.out.println(l);
				if(l.equalsIgnoreCase("MACRO"))
				{
					begin = true;
					System.out.println("7");
					break;
				}
				if(l.equalsIgnoreCase("MEND"))
				{
					write = write + mdtIndex + " MEND";
					pntIndex = 1;
					mnt.index++;
					pp = kp = 0;
					mdtp = mdtIndex + 1;
					kpdtp = kpd.index + 1;
					generate = false;
					System.out.println("HERE");
					obj1 = new PNTAB();
				}
				if(now)
				{
					System.out.println(l);
					if(k==0)
					{
						mnt.name[mnt.index] = l;
						System.out.println(5);
					}
					else
					{
						if(l.contains("=") && !l.contains("='"))
						{
							int indexOfKP = l.indexOf('=');
							String keyword,actual;
							keyword = actual = null;
							keyword = l.substring(1,indexOfKP);
							actual = l.substring(indexOfKP+1,l.length());
							kpd.name[kpd.index] = keyword;
							kpd.value[kpd.index] = actual;
							kpd.index++;
							obj1.pntab.put(keyword, pntIndex);
							System.out.println(1);
							pntIndex++;
							kp++;
						}
						else
						{
							l = l.substring(1,l.length());
							obj1.pntab.put(l, pntIndex);
							pntIndex++;
							pp++;
							System.out.println(2);
						}
					}
				}
				if(generate)
				{
					if(k == 0)
					{
						write = write + mdtIndex + " ";
						write = write + l + " ";
					}
					else
					{
						if(l.contains("&"))
						{
							l = l.substring(1,l.length());
							System.out.println("l : " + l);
							write = write + "(P," +obj1.index(l) + ")";
						}
						else
						{
							write = write + l;
						}
					}
					if(k>0 && k == 1)
					{
						write = write + ",";
					}
				}
			}
			if(now)
			{
				generate = true;
				now = false;
				mnt.pp[mnt.index] = pp;
				mnt.kp[mnt.index] = kp;
				mnt.MDTP[mnt.index] = mdtp;
				mnt.KPDTP[mnt.index] = kpdtp;
			}
			if(begin)
			{
				now = true;
				begin = false;
			}
			line = in.readLine();
			System.out.println(write + "hey");
			if(write.length()!=0)
			{
				write = write + "\n";
				out.write(write);
				mdtIndex++;
			}
		}
		kpd.write();
		mnt.write();
		kpdtp = kpdtp + kpd.index;
		print(obj1.pntab);
		in.close();
		out.close();
	}
	
	public static String removeComma(String s)
	{
		s = s.trim();
		int len = s.length();
		if(len!=0)
		{
			if(s.charAt(len-1) == ',')
			{
				return s.substring(0, len-1);
			}
			else
			{
				return s;
			}
		}
		return s;
	}
	
	public static void print(Hashtable<String, Integer> ht)
	{
		Enumeration<String> sym;		
		sym = ht.keys();
		while(sym.hasMoreElements())
		{
			String str = (String) sym.nextElement();
			System.out.println(str + " : " + ht.get(str));
		}
	}
}

class PNTAB
{
	Hashtable<String, Integer> pntab = new Hashtable<String, Integer>();
	
	public int index(String s)
	{
		return this.pntab.get(s);
	}
}
