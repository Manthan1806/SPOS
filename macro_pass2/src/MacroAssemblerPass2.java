import java.io.*;

public class MacroAssemblerPass2
{
	public static void main(String args[]) throws IOException
	{
		String write = new String();
		write = write + "+   ";
		String aptab[] = null;
		boolean generate = false;
		boolean begin = false;
		
		BufferedReader mdt = new BufferedReader(new FileReader("/home/manthan/SPOS/macro_pass2/src/MDT.txt"));
		BufferedReader codeFile = new BufferedReader(new FileReader("/home/manthan/SPOS/macro_pass2/src/code.txt"));
		BufferedWriter out = new BufferedWriter(new FileWriter("/home/manthan/SPOS/macro_pass2/src/code2.txt"));
 		
		MNT mnt = new MNT();
		KPDTAB kpdtab = new KPDTAB();
		
		int MDTP,pp,kp,kpdtp;
		int aptabIndex = 0;
		MDTP = 1;
		String lineRead = codeFile.readLine();
		mnt.write();
		kpdtab.write();
		
		while(lineRead!=null)
		{
			String split[] = lineRead.split("\\s");
			if(mnt.callMatch(split[0])!=-1)
			{
				System.out.println(mnt.callMatch(split[0]));
				generate = true;
				int[] array = mnt.array(mnt.callMatch(split[0]));
				pp = array[0];
				kp = array[1];
				MDTP = array[2];
				kpdtp = array[3];
 				aptab = new String[kp+pp];
				kpdtab.fillDefault(aptab,pp,kp,kpdtp);
				int length = split.length;
				for(int j=1;j<length;j++)
				{
					if(!split[j].contains("&"))
					{
						if(split[j].contains(","))
						{
							split[j] = split[j].substring(0,split[j].length()-1);
						}
						aptab[aptabIndex++] = split[j];
					}
					else
					{
						int index = kpdtab.getIndex(split[j],kpdtp,kp);
						aptab[pp+index-kpdtp+1] = split[j].substring(split[j].indexOf("=")+1,split[j].length()); 
						System.out.println("aptab : "+(pp+index-kpdtp+1)+" "+aptab[pp+index-kpdtp+1]);
					}
				}
			}
			if(generate)
			{				
				String line = mdt.readLine();
				while(line!=null)
				{
					String[] split2 = line.split("\\s");
					if(split2[0].equals(Integer.toString(MDTP)))
					{
						begin = true;
					}	
					if(begin)
					{
						for(int i=1;i<split2.length;i++)
						{
							if(split2[i].contains(","))
							{
								String[] split3 = split2[i].split("\\)");
								for(int j=0;j<split3.length;j++)
								{
									if(split3[j].contains("(P,"))
									{
										int index;
										if(split3[j].length()==4)
										{
											index = Integer.parseInt(split3[j].substring(3, split3[j].length()));
										}
										else
										{
											index = Integer.parseInt(split3[j].substring(4, split3[j].length()));
										}
										if(j==0 && split3.length==2)
										{
											write = write + aptab[index-1] + ",";
										}
										else
										{  
											write = write + aptab[index-1] + "";	
										}
									}
									else
									{
										write = write + split3[j].substring(split3[j].indexOf(",")+1, split3[j].length()) + " ";
									}
								}	
							}
							else
							{
								if(!split2[i].equals("MEND"))
								write = write + split2[i] + " ";
							}
						}
						if(split2[1].equals("MEND") && begin == true)
						{
							begin = false;
							aptabIndex = 0;
						}
						else
						{
							write = write + "\n" + "+   ";
						}
					}
					line = mdt.readLine();
				}
			}
			if(generate)
			{
				// write = write.substring(0,write.length()-4);
				out.write(write);
				generate = false;
			}
			else
			{
				out.write(lineRead+"\n");
			}
			lineRead = codeFile.readLine();
		}
		mdt.close();
		codeFile.close();
		out.close();
	}	
}