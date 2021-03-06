import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Assembler
{
	public static void main(String[] args) throws IOException
	{
		HashMap<String, Integer> opcode = new HashMap<String, Integer>();
		HashMap<String, Integer> opcodeSize = new HashMap<String, Integer>();
		add(opcode,opcodeSize);
		BufferedReader in = new BufferedReader(new FileReader("/home/manthan/SPOS/pass_1/src/code.txt"));
		BufferedWriter out = new BufferedWriter(new FileWriter("/home/manthan/SPOS/pass_1/src/pass1.txt"));
		Hashtable<String, Integer> symbolTable = new Hashtable<String, Integer>();
		Hashtable<String, Integer> symbolLocation = new Hashtable<String, Integer>();
		Hashtable<String, Integer> LiteralTable = new Hashtable<String, Integer>();
		Hashtable<Integer, String> LiteralLocation = new Hashtable<Integer, String>();
		Hashtable<String, String> label = new Hashtable<String, String>();
		Hashtable<Integer, Hashtable<String, Integer>> poolTable = new Hashtable<Integer, Hashtable<String, Integer>>();
		int LC=0,location=1,tokens=0,lLocation=1,poolPtr=1;
		String line = in.readLine();
		while(line!=null)
		{
			String write = new String();
			String[] split = line.split("\\s");
			tokens = split.length;
			for(int k=0;k<split.length;k++)
			{
				String s = split[k];
				String l = removeComma(s);
				if(opcode.get(l)!=null)
				{
					int code = opcode.get(l);
					if(code/10 == 0)
					{
						write = write + "(IS, " + Integer.toString(code%10) + ")";
					}
					else if(code/10 ==1 && code%10 == 0)
					{
						write = write + "(IS, " + Integer.toString(10) + ")";
					}
					else if(code/10 == 1)
					{
						if(l.equals("EQU"))
						{
							
						}
						else
						{
							write = write + "(AD, " + Integer.toString(code%10) + ")";
							if(l.equals("START"))
							{
								if(split.length==2)
								{
									LC = Integer.parseInt(split[1])-1;
								}
							}
							else if(l.equals("ORIGIN"))
							{
								
							}
							else if(l.equals("LTORG"))
							{
								write = new String();
								for(int i=0;i<lLocation;i++)
								{
									String a = LiteralLocation.get(i);
									System.out.println(a);
									if(a!=null)
									{
										write = write + "(DL,1)(C," + a.substring(2,a.length()-1) + ")" + "\n";
									}
								}
								poolTable.put(poolPtr, LiteralTable);
								poolPtr++;
								LiteralTable = new Hashtable<String, Integer>();
								LiteralLocation = new Hashtable<Integer, String>();
								lLocation = 1;
							}
							else if(l.equals("END"))
							{
								write = write + "\n";
								if(!LiteralTable.isEmpty())
								{
									for(int i=0;i<lLocation;i++)
									{
										String a = LiteralLocation.get(i);
										System.out.println(a);
										if(a!=null)
										{
											write = write + "(DL,1)(C," + a.substring(2,a.length()-1) + ")" + "\n";
										}
									}
									poolTable.put(poolPtr, LiteralTable);
								}
							}
						}
					}
					else if(code/10 == 2)
					{
						write = write + "(" + Integer.toString(code%10) + ")";
					}
					else if(code/10 == 3)
					{
						write = write + "(DL, " + Integer.toString(code%10) + ")";
					}
					else if(code/10 == 4)
					{
						write = write + "(" + Integer.toString(code%10) + ")";
					}
				}
				else
				{
					if(symbolTable.containsKey(l))
					{
						if(symbolTable.get(l) != -1)
						{
							if(label.get(l)==null)
							{
								System.out.println("Redeclaration of a variable");
							}
							else
							{
								write = write + "(S," + symbolLocation.get(l) + ")";
							}		
						}
						else
						{
							if(k==0)
							{
								symbolTable.put(l, LC);
							}
							else
							{
								write = write + "(S," + symbolLocation.get(l) + ")";
							}
						}
					}
					else
					{
						if(check(split,tokens,k,opcode))
						{
							int lORs = 0;
							try
							{
								Integer.parseInt(l);
							}
							catch(Exception e)
							{
								lORs = 1;
							}
							if(lORs == 1)
							{
								if(checkLiteral(l))
								{
									LiteralTable.put(l, LC);
									LiteralLocation.put(lLocation,l);
									write = write + "(L," + lLocation + ")";
									lLocation++;
								}
								else if(checkSingleQuote(l))
								{
									write = write + "(C," + l.substring(1,s.length()-1) + ")";
								}
								else
								{
									if(k==0)
									{
										symbolTable.put(l, LC);
										symbolLocation.put(l, location);
										location++;
										label.put(l, "Label");
									}
									else
									{
										if(k==1)
										{
											if(split[0].equals("ORIGIN"))
											{
												String t = l.substring(0,l.indexOf("+"));
												if(symbolLocation.get(t)!=null)
												{
													write = write + "(C," + Integer.toString(symbolLocation.get(t)) + ")" + l.substring(l.length()-2,l.length());
													LC = symbolTable.get(t)+Integer.parseInt(l.substring(l.indexOf("+")+1,l.length()));
												}
												else
												{
													System.out.println("Error! No backward reference");
												}
											}
											else
											{
												symbolTable.put(l,-1);
												symbolLocation.put(l, location);
												write = write + "(S," + Integer.toString(location) + ")";
												location++;
											}
										}
										else
										{
											symbolTable.put(l,-1);
											symbolLocation.put(l, location);
											write = write + "(S," + Integer.toString(location) + ")";
											location++;
										}
									}
								}
							}
							else
							{
								write = write + "(C," + l + ")";
							}
						}
						else
						{
							System.out.println("Undefined symbol " + l);
						}
					}
				}				
			}
			LC++;
			line = in.readLine();
			if(split.length>=2)
			{
				if(split[1].equals("EQU") || split[0].equals("ORIGIN"))
				{
					write = null;
				}
				else
				{
					write = write + "\n";
					out.write(write);
				}
			}
			else
			{
				write = write + "\n";
				out.write(write);
			}
		}
		String un = undefined(symbolTable);
		if(un != null)
		{
			System.out.println("Undefined symbol " + un);
		}
		System.out.println(label.get("AGAIN"));
		in.close();
		out.close();
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
	
	public static void add(HashMap<String, Integer> op,HashMap<String, Integer> ops)
	{
		op.put("STOP",0);
		op.put("ADD",1);
		op.put("SUB",2);
		op.put("MULT",3);
		op.put("MOVER",4);
		op.put("MOVEM",5);
		op.put("COMP",6);
		op.put("BC",7);
		op.put("DIV",8);
		op.put("READ",9);
		op.put("PRINT",10);
		op.put("START",11);
		op.put("END",12);
		op.put("ORIGIN",13);
		op.put("EQU",14);
		op.put("LTORG",15);
		op.put("LT",21);
		op.put("LE",22);
		op.put("EQ",23);
		op.put("GT",24);
		op.put("GE",25);
		op.put("ANY",26);
		op.put("DC",31);
		op.put("DS",32);
		op.put("AREG",41);
		op.put("BREG",42);
		op.put("CREG",43);
		op.put("DREG",44);
		
		//instruction size
		ops.put("STOP",1);
		ops.put("ADD",3);
		ops.put("SUB",3);
		ops.put("MULT",3);
		ops.put("MOVER",3);
		ops.put("MOVEM",3);
		ops.put("COMP",3);
		ops.put("BC",3);
		ops.put("DIV",3);
		ops.put("READ",2);
		ops.put("PRINT",2);
		ops.put("START",2);
		ops.put("END",1);
		ops.put("ORIGIN",2);
		ops.put("EQU",3);
		ops.put("LTORG",1);
		ops.put("DC",3);
		ops.put("DS",3);
	}
	
	public static String removeComma(String s)
	{
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
	
	//to check if instruction mentioned is correctly spelled
	public static boolean check(String[] l,int tokens,int k,HashMap<String, Integer> op)
	{
		if(k<l.length-1)
		{
			String s = removeComma(l[k+1]);
			int code = -1;
			if(op.get(s)!=null)
			{
				code = op.get(s);
				if(tokens == 3 && code/10 != 3)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public static String undefined(Hashtable<String, Integer> ht)
	{
		Enumeration<String> sym;		
		sym = ht.keys();
		while(sym.hasMoreElements())
		{
			String str = (String) sym.nextElement();
			if(ht.get(str)==-1)
			{
				return str;
			}
		}
		return null;
	}

	public static boolean checkSingleQuote(String l)
	{
		if(l.contains("'"))
		{
			return true;
		}
		return false;
	}
	
	public static boolean checkLiteral(String l)
	{
		if(l.contains("='"))
		{
			return true;
		}
		return false;
	}
}

