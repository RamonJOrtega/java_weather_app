import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer ;
import java.util.ArrayList ;

/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.00 10/27/2013
@assignment.number RegEx and File Processing - A190-09
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/
   public class A19009
   {
      public static void main (String[] args)throws IOException
      {
			String firstFile = "World.txt" ;  //set the name of the text file we will output
   		String secondFile = "OutsideUSA.txt" ;   	
			String strPage;                  //allocate a string for a raw page of data
      
      	//This string is initialized to the URL address of the station location information
      	String SLIURL = "http://weather.noaa.gov/data/nsd_bbsss.txt" ;
      
      	Inet net = new Inet();           //instantiate an Inet object to perform file operations
                                       	//and URL operations defined in the class.
    
      	//if there is already a file, read that file, otherwise, create that file    
      	if (net.getFromFile(firstFile).isEmpty()) //check to see if there is an existing file
      	{
         	strPage = net.getURLRaw(SLIURL) ;   //put the station location information in a string
         	net.saveToFile(firstFile, strPage) ; //save the station locatoin info to a file 
      	}
      	else //otherwise tell the user that there is already a file
      	{
         	System.out.println("File Already On Disk") ;
      	}		
         
			//create a file that that reads the World.txt file
      	File readFile = new File(firstFile) ;
      	//Check to see if the file already exists, if it does continue
      	if (readFile.exists())
      	{
            //if the file exists do, the following
          	Scanner inputFile = new Scanner(readFile) ;     //create a scanner object to read the file
				StringBuilder stations = new StringBuilder(""); //create a stringBuilder object to store tokens in
         
            while (inputFile.hasNext())                     //read every line of the World.txt file
            {  //Create a string object to hold lines of the World.txt file
               String line = inputFile.nextLine();    
               //Tokenize the line from the World.txt file      
               String[] tokens = line.split(";",14);
               //declare and initialize exception flags to false
					boolean ICAOcode = false ;                  
					boolean UScountry = false ;
					boolean outsideLat = false ;
					boolean outsideLong = false ;
					boolean overTen = false ;
               //set exception flags to true if airports meet certain crieria in the specification
					ICAOcode = (tokens[2].compareTo("----")!=0) ;            //extract only airports with an ICAO code
					UScountry = (tokens[5].compareTo("United States")== 0) ; //extract only airports that belong to the US
               //extract only airports outside the latitude of the continental united states
					outsideLat = (tokens[7].endsWith("S") || (Integer.parseInt(tokens[7].substring(0,2)) > 49) ||(Integer.parseInt(tokens[7].substring(0,2)) < 24) );
               try 
               {
               //extract only airports outside the longetude of the continental united states
               outsideLong = (tokens[8].endsWith("E") || (Integer.parseInt(tokens[8].substring(0,3)) > 124) || (Integer.parseInt(tokens[8].substring(0,3)) <  67) );
               }
               catch (NumberFormatException e)
               {
               //handle the exception where there is only two digits of degrees of longitude, as opposed to three
               outsideLong = (tokens[8].endsWith("E") || (Integer.parseInt(tokens[8].substring(0,2)) > 124) || (Integer.parseInt(tokens[8].substring(0,2)) <  67) );
               }
               try
               {  //extract only airports that are above 10 meters (this excludes things like buies
                  overTen = Integer.parseInt(tokens[11]) > 10 ;
					}
               catch (NumberFormatException e) 
               {  //handle the exception where the altidude token is left blank
                  overTen = false ;
				   }    
               
               //if a line of the World.txt file meets all the exceptions in the specification, then format and
               //append the tokens to the stations stringbuilder object
               if (ICAOcode && UScountry && outsideLat && outsideLong && overTen)
				   {
                  stations.append(tokens[2] + " " + net.properCase(tokens[3]) + "\r\n") ;
                  stations.append("\t Latitude: " + tokens[7] + "\r\n");
                  stations.append("\t Longitude: " + tokens[8] + "\r\n") ;
                  stations.append("\t Elevation: " + tokens[11] + " meters\r\n") ;
                  stations.append("\t Block and Station Number: " + tokens[0] + "-" + tokens[1] + "\r\n") ;
                  stations.append("\r\n");
				   }		
            }
            //call the Inet object saveToFile method to create the OutsideUSA.txt file
            net.saveToFile(secondFile, stations.toString()) ;
         }
			else
			{
				System.out.println("File not found") ;
			}
      System.out.println("Done") ; // tell the user we are done
      }
   }