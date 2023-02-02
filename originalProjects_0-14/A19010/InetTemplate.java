/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.00 11/03/2013
@assignment.number Databases - A190-10
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/

public interface InetTemplate //This templete is a contract for how the Inet Class will work
                              //It contains the functions that Inet will contain.
{
/**
This function instantiates a StringBuilder object, strConten,t that holds the content
of a web page. It instaniates a URL object, myWebAddress, to hold the web URL string.
It instantiates an InputStream object, myStrIn, in order to read from an internet stream
of data. It instantiates a BufferedReader object, myINputFile, in order to read the URL.
   
@param strURL a string representing the webpage URL address
@return a string representing what is on the webpage at the URL address
*/ 
public String getURLRaw(String strURL);
/**
This function converts calls getRegEx and hands it a page of data
to be searched with a pattern argument string
   
@param strPage a string representing the data we must search through  
@return a string representing the data we searched for
*/
public String getPREData(String strPage);
/**
This function uses regular expressions in order to search for patterns
in text strings.
   
@param strInput a string representing the data to be searched
@param strInput a string representing the pattern to search by
@return a string representing the important data we searched for
*/
public String getRegEx(String strInput, String strPattern);
/**
This function converts string data to proper case formate. Such
as the name: "ProperCase"
   
@param strInput a string representing the data to be converted to proper case  
@return a string shown in proper case format
*/
public String properCase(String strInput);
/**
This function creates a file, and saves an input string to that
file
   
@param strFilename a string representing the file we want to be created
@param strContent a string representing the content we want to saved in the file
@return none
*/ 
public void saveToFile(String strFileName, String strContent);
/**
This function reads a file and returns it's contents as a string
   
@param strFileName a string representing the name of the file to be read
@return a string representing the contents of the file
*/
public String getFromFile(String strFileName);
} // end of interface