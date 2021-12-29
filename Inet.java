import java.net.*;  
import java.io.*;    
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;

/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/16/2013
@assignment.number Final Project - A190-15
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/ 
    
public class Inet implements InetTemplate
{
   /**
   This function is the constructor. It is not used to initialize variables
   because there are no class level variables to initialize in this class.
   
   @param none
   @return none
   */  
   public  Inet() 
   {
   }
   /**
   This function instantiates a StringBuilder object, strConten,t that holds the content
   of a web page. It instaniates a URL object, myWebAddress, to hold the web URL string.
   It instantiates an InputStream object, myStrIn, in order to read from an internet stream
   of data. It instantiates a BufferedReader object, myINputFile, in order to read the URL.
   
   @param strURL a string representing the webpage URL address
   @return a string representing what is on the webpage at the URL address
   */  
   public String getURLRaw(String strURL)
   {
      StringBuilder strContent = new StringBuilder("");
      try
      {
         URL myWebAddress= new URL(strURL);
         URLConnection myConn = myWebAddress.openConnection(); 
         InputStream myStrIn = myConn.getInputStream();
         BufferedReader myInputFile = new BufferedReader(new InputStreamReader(myStrIn));
         //strContent="";
         for(String strLine=null; (strLine=myInputFile.readLine()) !=null;)
         {
            // read a line and append it to strContent
            strContent.append(strLine + "\r\n");
         }
      }
      catch (MalformedURLException errnum)
      {
      // display error if URL is messed up
      }
      catch (IOException errnum)
      {
      // display error if Internet connection is flaky
      }
      // At this point strContent contains the
      //    raw HTML code of your web page 
      //    or just a blank
      return strContent.toString();
   }
   /**
   This function uses regular expressions in order to search for patterns
   in text strings.
   
   @param strInput a string representing the data to be searched
   @param strInput a string representing the pattern to search by
   @return a string representing the important data we searched for
   */    
   public String getRegEx(String strInput, String strPattern)
   {
      String strRet = "";
      Pattern pattern = Pattern.compile(strPattern,
      Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
      Matcher matcher = pattern.matcher(strInput);

      while (matcher.find())
      {
         strRet = strRet + "\n" + matcher.group();
      } 
      if (strRet.length() < 1 )
      {
         strRet = "String Not Found";  
      } 
      return strRet.trim(); //remove whitespace
   }
   
   /**
   This function converts calls getRegEx and hands it a page of data
   to be searched with a pattern argument string
   
   @param strPage a string representing the data we must search through  
   @return a string representing the data we searched for
   */
   public String getPREData(String strPage)
   {
      return getRegEx(strPage,"<PRE>(.)*</PRE>") ;   
   }
   /**
   This function converts string data to proper case formate. Such
   as the name: "ProperCase"
   
   @param strInput a string representing the data to be converted to proper case  
   @return a string shown in proper case format
   */  
   public String properCase(String strInput)
   {
		Pattern p = Pattern.compile("(^|\\W)([a-z])");
		Matcher m = p.matcher(strInput.toLowerCase());
		StringBuffer sb = new StringBuffer(strInput.length());
		while(m.find()) 
      {
			m.appendReplacement(sb, m.group(1) + m.group(2).toUpperCase() );
		}
		m.appendTail(sb);
		return sb.toString();   
   }
   /**
   This function creates a file, and saves an input string to that
   file
   
   @param strFilename a string representing the file we want to be created
   @param strContent a string representing the content we want to saved in the file
   @return none
   */  
   public void saveToFile(String strFilename, String strContent)
   {
      try
      {
         //Create a printwriter object of the specified file name      
         PrintWriter outputFile = new PrintWriter(strFilename) ;
         //Transfer the content string to the file
         outputFile.println(strContent) ;
         //close the file
         outputFile.close() ;
      }
      catch (FileNotFoundException e) //handles the exception of a nonexistent file
      {
         System.out.println("File not found." );
      }
   }
   /**
   This function reads a file and returns it's contents as a string
   
   @param strFileName a string representing the name of the file to be read
   @return a string representing the contents of the file
   */  
   public String getFromFile(String strFileName)
   {
      //create a stringbuilder object to hold the file contents
      //initialize the fileLine to null!
      StringBuilder fileLine = new StringBuilder("") ;
      //create a file of the specificed name
      File myFile = new File(strFileName) ;
      //Check to see if the file already exists
      if (myFile.exists())
      {
         try // if the file exists do, the following
         {  //create a scanner object to read the file
            Scanner inputFile = new Scanner(myFile) ;
            //fore every line scanned in the file, add it to the stringbuilder object
            while (inputFile.hasNext())
            {
               fileLine.append(inputFile.nextLine()+ '\n') ;
            }
         }
         //handle the exception that the file doesn't exist yet
         catch (FileNotFoundException e) 
         {
            System.out.println("file not found") ;
         }
      }
      else
      {  //If the specified file doesn't exist, tell the user that it will be created
         System.out.println("Must create txt file");
      }
      return fileLine.toString() ; //depending on it's existence, the function returns
                                   // the file's contents as a string, or null (initialization)						  
   }   
}