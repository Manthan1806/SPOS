import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MNT 
{
	String index[] = new String[10];
	String name[] = new String[10];
	String PP[] = new String[10];
	String KP[] = new String[10];
	String MDTP[] = new String[10];
	String KPDTP[] = new String[10];
	int count;
	
	public MNT()
	{
		count=0;
	}
	
	public void write() throws IOException
	{
		BufferedReader mnt = new BufferedReader(new FileReader("/home/manthan/SPOS/macro_pass2/src/MNT.txt"));
		String readLine = mnt.readLine();
		int i=0;
		while(readLine!=null)
		{
			String split[] = readLine.split("\\s");
			index[i] = split[0];
			name[i] = split[1];
			PP[i] = split[2];
			KP[i] = split[3];
			MDTP[i] = split[4];
			KPDTP[i] = split[5];
			i++;
			count++;
			readLine=mnt.readLine();
		}
		mnt.close();
	}
	
	public int callMatch(String callName)
	{
		for(int i=0;i<count;i++)
		{
			if(callName.equals(name[i]))
			{
				return i;
			}
		}
		return -1;
	}
	
	public int[] array(int i)
	{
		int[] returningArray = new int[4];
		returningArray[0] = Integer.parseInt(PP[i]);
		returningArray[1] = Integer.parseInt(KP[i]);
		returningArray[2] = Integer.parseInt(MDTP[i]);
		returningArray[3] = Integer.parseInt(KPDTP[i]);
		return returningArray;
	}
}






