import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer ;
import java.util.ArrayList ;
  
/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.00 11/03/2013
@assignment.number Databases - A190-10
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/  
public class A19010
{
   public static void main(String [] args)throws IOException
   {
      
      System.out.println("A19010") ;      //display the assignment to the user
         
      String firstFile = "World.txt" ;    //set the name of the large station text file
      String secondFile = "CA.txt" ;   	//set the name of the california station text file
      String strPage;                     //allocate a string for a raw page of data
      
      //This string is initialized to the URL address of the station location information
      String SLIURL = "http://weather.noaa.gov/data/nsd_bbsss.txt" ;
      
      Inet net = new Inet();           //instantiate an Inet object to perform file operations
                                       //and URL operations defined in the class.
    
      //if there is already a file, read that file, otherwise, create that file    
      if (net.getFromFile(firstFile).isEmpty()) //check to see if there is an existing file
      {
         strPage = net.getURLRaw(SLIURL) ;   //put the station location information in a string
         net.saveToFile(firstFile, strPage) ; //save the station location info to a file 
      }
      else //otherwise tell the user that there is already a file
      {
         System.out.println("File Already On Disk") ;
      }		
         
      //create a file that that reads the World.txt file
      File readFile = new File(firstFile) ;
      	
      DBUpdt db = new DBUpdt() ;          //create a DBUpdt object called db
      db.openConnection("Weather") ;      // name of connection, not of file
      db.deleteAll("stations") ;          //wipe the rows of the stations table clean everytime
            
     //Check to see if the World.txt file already exists, if it does continue
      if (readFile.exists())
      {
            //if the file exists do, the following reading of the txt file
         Scanner inputFile = new Scanner(readFile) ;     //create a scanner object to read the file
         StringBuilder stations = new StringBuilder(""); //create a stringBuilder object to store tokens in
         
                       
         
         while (inputFile.hasNext())                     //read every line of the World.txt file
         {  //Create a string object to hold lines of the World.txt file
            String line = inputFile.nextLine();    
               //Tokenize the line from the World.txt file      
            String[] tokens = line.split(";",14);
               //declare and initialize exception flags to false
            boolean ICAOcode = false ;                  
            boolean hasState = false ;
               //set exception flags to true if airports meet certain crieria in the specification
            ICAOcode = (tokens[2].compareTo("----")!=0) ;   //extract only airports with an ICAO code
               //extract only airports that have a state field of CA (california)
            hasState = (tokens[4].compareTo("CA") == 0) ;					    
               
               //if a line of the World.txt file meets all the exceptions in the specification, then format and
               //append the tokens to the stations stringbuilder object. Also add the stationID, stationName, state,
               //latitude, longitude, and elevation fields to the stations table in the station weather database by
               //calling the addRecord and setField methodes in the db object.
            if (ICAOcode && hasState)
            {
               stations.append(tokens[2] + " " + net.properCase(tokens[3]) + "\r\n") ;
               stations.append("\t State: " + tokens[4] +"\r\n") ;
               stations.append("\t Latitude: " + tokens[7] + "\r\n");
               stations.append("\t Longitude: " + tokens[8] + "\r\n") ;
               stations.append("\t Elevation: " + tokens[11] + " meters\r\n") ;
               stations.append("\t Block and Station Number: " + tokens[0] + "-" + tokens[1] + "\r\n") ;
               stations.append("\r\n");
               db.addRecord("stations" , "stationID", tokens[2]) ;
               db.setField("stations", "stationID" , tokens[2], "stationName", net.properCase(tokens[3])) ;
               db.setField("stations", "stationID" , tokens[2], "state", tokens[4]) ;
               db.setField("stations", "stationID" , tokens[2], "latitude", tokens[7]) ;
               db.setField("stations", "stationID" , tokens[2], "longitude", tokens[8]) ;
               db.setField("stations", "stationID" , tokens[2], "elevation", tokens[11]);
            }		
         }
            //call the Inet object saveToFile method to create the CA.txt file
         net.saveToFile(secondFile, stations.toString()) ;
      }
      else
      {
         System.out.println("File not found") ;
      }
      System.out.println("Done") ; // tell the user we are done
         
      //To display the fields in the stations table of the database, uncomment the following block of code   
      
      String strSQL ;                     //make a sting variable to hold SQL commands
      strSQL = "SELECT * FROM stations" ; //use the SELECT statement in order to get Field values from db
      db.query(strSQL) ;                  //create a query to create a result set
      while (db.moreRecords())            //loop through each row in the result set
      {  //print the field values of every airport in california
         System.out.printf(" %7s %50s %5s %15s %15s %5s\n",
                              db.getField("stationID"),
                              db.getField("stationName"),
                              db.getField("state"),
                              db.getField("latitude"), 
                              db.getField("longitude"), 
                              db.getField("elevation")  ) ;
                              //filed name not case sensative in SQL
      }
      
      
      db.close() ;            //close the database so that others can use it
   }
}