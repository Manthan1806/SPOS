public class Optimal 
{
	public static void main(String[] args) 
	{
		int pageFault = 0;
	    int nFrames = 3;
	    int nPages = 11;
	    int pages[] = {5,9,1,5,4,7,4,2,6,7,9};
	    int frames[] = {-1,-1,-1};
	    int i,j,k,l;
	     
	    for(i=0;i<nPages;i++)
	    {
	       int isFrameEmpty = 0;
	        
	        for(j=0;j<nFrames;j++)
	        {
	            if(frames[j] == -1)
	            {  
	            	isFrameEmpty = 1;
	                break;
	            }
	        }
		    if(isFrameEmpty == 1)
		    {
		    	frames[j] = pages[i];
		    	pageFault++;
		    }
	        
	         
	    else
	    { 
	        int isPageAlreadyPresent = 0;
	         
	        for(j=0;j<nFrames;j++)
	        {
	            if(pages[i] == frames[j])
	            {
	                isPageAlreadyPresent = 1;
	                break;
	            }
	        }
	        if(isPageAlreadyPresent == 0)
	        {
	            pageFault++;
	            int temp[]=new int[nFrames];
	            
	            for(k=0;k<nFrames;k++)
	            {  
	            	temp[k]=-1;
	               	for(l=i+1;l<nPages;l++)
	                {
	                    if(frames[k] == pages[l])
	                    {
	                        temp[k] = l;
	                        break;
	                    }
	                }   
	            }

	        int pos = -1;
	        int flag=0;
	        for(l=0;l<nFrames;l++)
	        {
	            if(temp[l]==-1)
	              {
	                  pos = l;
	                  flag=1;
	                  break;
	              }
	        }
	         
	        if(flag==1)
	        { 
	        	frames[pos] = pages[i];
	        }
	        else
	        {
	          int max = temp[0];
	          pos = 0;
	           
	          for(k=1;k<nFrames;k++)
	          {
	              if(max<temp[k])
	              {
	                  pos = k;
	                  max=temp[k];   
	              }
	          }
	          frames[pos] = pages[i];
	        }
	      }  
	    }
	     for(j=0;j<nFrames;j++)
	        {
	    	 	System.out.print(frames[j]+" ");
	        }  
	     System.out.println("");    
	    }
	    System.out.println("Page Faults: "+pageFault);
	      
	}
}