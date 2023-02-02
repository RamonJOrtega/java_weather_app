import java.util.Scanner;
import java.io.* ;

/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.12 09/22/2013
@assignment.number Winds Aloft Report - A190-04
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/
public class A19004
{
	static String strStationWeather ;        //declare a class level variable to hold a line of station weather
   static String strAltitudeWeather ;       //declare a class level variable to hold a line of altitude weather
	
	public static void main(String[] args) throws IOException   //main method must handle IO exceptions
	{  //declare variables
	   String strAlt = " " ;
	   String strAirportID = " " ;
	   String strSFO = " " ;
	   Scanner keyboard = new Scanner(System.in);
      //verify file exists
      File myFile = new File("FBIN.txt");
      if (!myFile.exists())
      {
         System.out.println("The file FBIN.txt cannot be found." ) ;
         System.exit(0) ;
      }
      //read the file every time the user wants an altitude weather report     
      while (strAlt.compareTo("EXIT") != 0 || strAirportID.compareTo("EXIT") != 0)
      {
         myFile = new File("FBIN.txt") ;           //Open a new file every iteration
         Scanner inputFile = new Scanner(myFile) ; 
         //get the station code from the user
		   System.out.println("To get weather, enter Station Code as three letters.") ;
		   System.out.println("To exit, type exit") ;
         strAirportID = keyboard.nextLine().toUpperCase() ;
         //give the user the option to exit
         if (strAirportID.compareTo("EXIT")==0)
         {
            System.exit(0) ;
         }
         //get the altitude of interest
		   System.out.println("Enter altitude. Ex: input 18 for 18,000ft") ;
         strAlt = keyboard.nextLine() ;
         boolean flag = false ;           //initialize a flag to for checking valid station codes
     
	      while (inputFile.hasNext())      //read the file
         {
            String line = inputFile.nextLine()+" " ;  
            if (line.length() >= 2 )
            {  //when the file line station code matches the user input, store
               //the whole line in strStationWeather class variable and
               //store the 7 character altitude weather in the 
               //strAltitudeWeather class variable 
               if (line.substring(0,3).compareTo(strAirportID) == 0 )
               {
                  strStationWeather = line;
                  strAltitudeWeather = getAltitudeWeather(strAlt);
                  flag = true ;
               }
            }
         }
         //if the user entered a valid station code, display the weather for that airport
         //at the user specified altitude using the methodes created
         if (flag == true)
         {
            System.out.println("Black Mountain Aviation");
		      System.out.print("Weather for ");
		      System.out.print(strAirportID);
		      System.out.print(" at an altitude of ");
		      System.out.print(strAlt);
		      System.out.println(",000 feet");
		      System.out.println(" ") ;
		      System.out.println("Wind Direction: " + getWindDir(strAlt)) ;
		      System.out.println("Wind Speed: " + getWindSpeed(strAlt)) ;
		      System.out.println("Wind Temperature: " + getWindTemp(strAlt)) ;
		      System.out.println(" ") ;	
            System.out.println("Program by Ramon Ortega") ;
            System.out.println(" ") ;
         }     
         else
         {  //if the user didn't enter an airport code in the file, let him try again
            System.out.println("Airport code not found. Enter new airport code.") ;
         }
         inputFile.close(); //close the file at the end of every loop so that it can be opened again if needed.
      }
	}
   	
	/**
	This function accepts a string called strAlt representing an 
	altitude and returns an integer representing the starting
	character position of the altitude weather in the station weather string
	
	@param strAlt Altitude in thousands as a string
	@return an integer representing the character postion with the station weather
	*/
	public static int getPos(String strAlt)
	{
		int intRet = 0 ;

		switch (strAlt)
		{
			case "3" :
				intRet = 4;
				break;
			case "6" :
				intRet = 9;
				break;
			case "9" :
				intRet = 17;
				break;
			case "12" :
				intRet = 25;
				break;
			case "18" :
				intRet = 33;			
				break;
			case "24" :
				intRet = 41;
				break;
			case "30" :
				intRet = 49;
				break;
			case "34" :
				intRet = 56;
				break;	
			case "39" :
				intRet = 63;
				break;
			default :
				intRet = 4;
				break;
		}
		return intRet;
	}
	/**
	This function will call getpos to determine where to 
	start extracting seven characters from the class level variable
	containing the Station Weather.
	
	@param strAlt - string representing altitude in thousands of feet.
	@return seven character string representing the altitude weather.
	*/
	public static String getAltitudeWeather(String strAlt)
	{
		int intPos = getPos(strAlt);
		return strStationWeather.substring(intPos, intPos + 7) ;
	}
	/**
	This function will call getAltitudeWeather and extract the
	first two characters and concatenate a zero to the
	return value.
	
	@param strAlt String representing altitude in thousands of feet.
	@return windDir String representing wind direction at the specified altitude
	*/
	public static String getWindDir(String strAlt)
	{
      String windDir = "XX" ;
      if(getAltitudeWeather(strAlt).substring(0,2).compareTo("  ")== 0)
      {
         windDir = "N/A" ;
      }
      else if (getAltitudeWeather(strAlt).substring(0,4).compareTo("9900")== 0)
      {
         windDir = "Calm" ;
      }
      else if(Integer.parseInt(getAltitudeWeather(strAlt).substring(0,2)) > 36)
      {
         windDir = Integer.parseInt(getAltitudeWeather(strAlt).substring(0,2)) - 50 + "0 degrees";
      }
      else 
      {
         windDir = getAltitudeWeather(strAlt).substring(0,2)+ "0 degrees" ;
      }   
      return windDir;
	}
   /**
	This calls the method, getAltitudeWeather extract the 2nd and 3rd
   characters of the stationWeather string. The method returns them as
   the wind speed at the specified altitude.
	
	@param strAlt Altitude in thousands as a string
	@return windSpeed The wind speed at the specified altitude
	*/
	public static String getWindSpeed(String strAlt)
	{
      String windSpeed = "XX" ;
      if(getAltitudeWeather(strAlt).substring(2,4).compareTo("  ") == 0)
      {
         windSpeed = "N/A" ;
      }
      else if (getAltitudeWeather(strAlt).substring(0,4).compareTo("9900") == 0)
      {
         windSpeed = "Calm" ;      
      }
      else if (Integer.parseInt(getAltitudeWeather(strAlt).substring(0,2)) > 36)
      {
         windSpeed = Integer.parseInt(getAltitudeWeather(strAlt).substring(2,4)) + 100 + " knots" ;
      }
      else 
      {
         windSpeed = getAltitudeWeather(strAlt).substring(2,4)+ " knots" ;
      }
		return windSpeed;
	}
   /**
	This method calls the method, getAltitudeWeather extract final characters
   of the stationWeather string. The method returns them as
   the temperature at the specified altitude.
	
	@param strAlt Altitude in thousands as a string
	@return windTemp The temperature of the air at the specified altitude
	*/
	public static String getWindTemp(String strAlt)
	{
      String windTemp = "XX" ;
      if (getAltitudeWeather(strAlt).substring(4,5).compareTo(" ") == 0 )
      {
         windTemp = "N/A" ;
      }
      else if (Integer.parseInt(strAlt) > 24 )
      {
         windTemp = "-" + getAltitudeWeather(strAlt).substring(4,6) + " degrees Celsius" ;
      }
      else
      {
         windTemp = getAltitudeWeather(strAlt).substring(4,7) + " C" ;
      }
		return windTemp ;
	}
}