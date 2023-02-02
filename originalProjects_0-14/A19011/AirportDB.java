import java.util.ArrayList ;
import java.util.*;
import java.io.* ;

/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.00 11/17/2013
@assignment.number GUI w/Airport Status - A190-12
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/  

public class AirportDB                       //name of the class
{
	private final String NCEPFileURL = "http://www.aviationweather.gov/products/nws/all" ;
	//Declare and initializ e the array list that holds an airport weather line
	private ArrayList<String> aptList = new ArrayList<String>() ;
	//Declare and initialize the array list that holds the aiport ID as an index
	private ArrayList<String> aptIndex = new ArrayList<String>() ;
	
	/**
	This is the constructor for the AirportDB class. It reads an input file
	from the user and stores the airport weather and airport index in two
	respective array lists. If the file doesn't exist, the function goes onine to the
   correct url and downloads the data to create the file.
	  
   @param inputFile a string object containing the file name of the winds aloft report
   */
	public AirportDB (String inputFile)		//Constructor
	{
	
		try
		{
			File myFile = new File(inputFile) ; //pg 246
			Scanner inFile = new Scanner(myFile);
	
			while( inFile.hasNext() )				//read the file line by line until the end
			{
				String line = inFile.nextLine() ;//store the next line of the file in a string variable
			
				//only lines of greater than 65 characters are airport weather lines
				if ( (line.length() > 65) && (line.substring(2,3).compareTo(" ") != 0) ) //if the next line of the file is an airport weather line
				{
				aptList.add(line) ;						//store the line into the aptList array list
			
				aptIndex.add(line.substring(0,3)) ;	//store the first 3 characters of the line into the
				}												//aptIndex array list
			}	
		}
		catch (FileNotFoundException e)
		{
		   //if the file does not yet exist, go online and download the data to create the file
         //using the Inet class method: getPreData
			System.out.println(e.getMessage()) ;
			System.out.println("The file " + inputFile + " does not yet exist" ) ;
			System.out.println("Downloading NCEP Winds Aloft Forecasts from the internet" );
			Inet net = new Inet() ;
			String rawWebPage ;
			rawWebPage = net.getURLRaw(NCEPFileURL) ;
			String preData ;
			preData = net.getPREData(rawWebPage);
			net.saveToFile(inputFile, preData) ;
			System.out.println("Saved winds aloft to file");
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
    /**
   This function uses the aptIndex ArrayList method, get(), to retrieve the
   contents of the array list when we know it's index.
   
   @param intPosition an integer representing the index of an ArrayList
   @return a 3 letter string representing station ID
   */ 
   public String getStationID(int intPosition)
      {
         return aptIndex.get(intPosition) ;
      }
   /**
   This function uses the aptIndex ArrayList method, get(), to retrieve the
   contents of the array list when we know it's index.
   
   @param strStationID a string representing the 3 letter station ID
   @return an integer representing the index of the station ID in the ArrayList, aptIndex
   */    
   public int getStationIndex(String strStationID)
      {
         return aptIndex.indexOf(strStationID);
      } 
}