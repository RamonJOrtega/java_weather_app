import java.net.*;   /** URL, URLConnection */
import java.io.*;    /** File,InputStream,BufferedReader,InputStreamReader */
import java.util.regex.Pattern;
import java.util.regex.Matcher;
    
public class Inet implements InetTemplate
{
   public  Inet() // constructor
   {
   }
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
     
   public String getRegEx(String strInput, String strPattern)
   {
      String strRet = "";
      Pattern pattern = Pattern.compile(strPattern,
      Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
      Matcher matcher = pattern.matcher(strData);

      while (matcher.find())
      {
         strRet = strRet + "\n" + matcher.group();
      } 
      if (strRet.length() < 1 )
      {
         strRet = "String Not Found";
      } 
      return strRet.trim();
   }
   
   public String getPREData(String strPage)
   {
      return getRegEx(strPage) ;
   }
   
   public String properCase(String strInput)
   {
      return "" ;
     
   }
   
   public void saveToFile(String strFilename, String strContent)
   {
      try
      {
   
      }
      catch (FileNotFoundException e)
      {
   
      }
   }
   
   String getFromFile(String strFileName)
   {
   
   }
   
}