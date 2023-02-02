import java.util.ArrayList ;
import java.util.Scanner ;

/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.12 10/03/2013
@assignment.number ArrayList - A190-06
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/

public class AirportDB                       //name of the class
{
	//Declare and initialize the array list that holds an airport weather line
	private ArrayList<String> aptList = new ArrayList<String>() ;
	//Declare and initialize the array list that holds the aiport ID as an index
	private ArrayList<String> aptIndex = new ArrayList<String>() ;
	
	/**
	This is the constructor for the AirportDB class. It reads an input file
	from the user and stores the airport weather and airport index in two
	respective array lists.
	  
   @param inputFile a scanner object containing national weather report
   */
	public AirportDB (Scanner inputFile)		//Constructor
	{
		while( inputFile.hasNext() )				//read the file line by line until the end
		{
			String line = inputFile.nextLine() ;//store the next line of the file in a string variable
			
			//only lines of greater than 65 characters are airport weather lines
			if (line.length() > 65 ) //if the next line of the file is an airport weather line
			{
			aptList.add(line) ;						//store the line into the aptList array list
			
			aptIndex.add(line.substring(0,3)) ;	//store the first 3 characters of the line into the
			}												//aptIndex array list
		}
	}
	/**
   This function returns the number of airports in the aptIndex array list
   for the user.
   
   @param none
   @return an integer representing the number of airports in the aptIndex array list
   */
	public int size() 
	{
		return aptIndex.size () ;              //use the size methode for array lists
	}
   /**
   This function returns the number of airports in the aptIndex array list
   for the user.
   
   @param strStationID a 3 letter string representing an airport code
   @return a string representing the full station weather
   */
	public String getStationWeather( String strStationID) 
		{
			String ID = strStationID.toUpperCase();
			return aptList.get(aptIndex.indexOf(ID)) ;
		}
}