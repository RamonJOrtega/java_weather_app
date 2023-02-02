
import java.util.ArrayList ;
import java.util.Scanner ;
import javax.swing.JOptionPane ;
import java.io.* ;

/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.12 10/03/2013
@assignment.number ArrayList - A190-06
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/

public class A19006 
{
	public static void main(String[] args) throws IOException //main method with IOExceptio
	{
	
		//declare and initialize variables
		String strAlt = "XXX" ;
		String strKey = "XXX" ;
		String altitudeWeather = "XX";
		String windDirection = "XX" ;
		String windSpeed = "XX" ;
		String windTemp = "XX" ;
		
		String strStationWeather = ("XX");	//declare and initialize the main variable
		
		//create file ouput and inputs
		PrintWriter outputFile = new PrintWriter("FBOUT.txt") ;
		File myFile = new File("FBIN.txt") ; //pg 246
		Scanner inputFile = new Scanner(myFile);
		
		//make sure the file exists
		if( !myFile.exists())
		{
			System.out.println("The file FBIN.txt cannot be found" ) ;
			System.exit(0) ;
		}
		//declare and initialize an AirportDB object and pass it the input file
		AirportDB db = new AirportDB(inputFile);
		//get the station ID from the user
		strKey = JOptionPane.showInputDialog("Enter the Station ID") ;
		
	   //get the station weather as a string using the db AirportDB object
		strStationWeather = db.getStationWeather(strKey)+" " ;
      
      //create an NWSFB object called fb and pass it the station weather string
		NWSFB fb = new NWSFB(strStationWeather);
		
      //Write a heading along with the number of how many records are in the
      //AirportDB object
      System.out.println("Black Mountain Aviation Center") ;
      outputFile.println("Black Mountain Aviation Center") ; 
      System.out.printf("There are %d airport records in the airport data base.\n\n", db.size()) ;
      outputFile.println(String.format("There are %d airport records in the airport data base.\n\n", db.size())) ;
      
		System.out.printf("Station ID: %s\n\n", strKey.toUpperCase()) ;
		outputFile.println(String.format("Station ID: %s", strKey.toUpperCase())+"\n") ;
			
		for (int alt = 0 ; alt < 9 ; alt++) //for each of the 9 altitudes of every airpot
		{												
			switch (alt)							//store the 9 altitudes as a string
			{
				case 0 :
            strAlt = "3" ;
            break;
        		case 1 :
            strAlt = "6" ;
            break;
         	case 2 :
            strAlt = "9" ;
            break;
         	case 3 :
            strAlt = "12" ;
            break;
         	case 4 :
            strAlt = "18" ;        
            break;
         	case 5 :
            strAlt = "24" ;
            break;
         	case 6 :
            strAlt = "30" ;
            break;
         	case 7 :
            strAlt = "34" ;
            break;   
         	case 8 :
            strAlt = "39" ;
            break;
         	default :
          	strAlt = "shouldn't be here!" ;
            break;
			}
			//get the weather at each altitude using the NWSFB05 class's object function
			//and store the individual weather pieces in variables
			altitudeWeather = fb.getAltitudeWeather(strAlt) ;
			windDirection = fb.getWindDirection(strAlt);
			windSpeed = fb.getWindSpeed(strAlt) ;
			windTemp = fb.getWindTemp(strAlt) ;
			//print the altitude weather string for every altitude
			if (altitudeWeather.substring(0,2).compareTo("  ") == 0 )	//if is not an altitude weather string
			{																				//print N/A to the file
				System.out.print("The altitude Weather for " + strAlt + ",000 feet is: N/A\n") ;
				outputFile.println("The altitude Weather for " + strAlt + ",000 feet is: N/A") ;
			}
			else
			{																				//otherwise print the altitude weather string to 
				System.out.printf("The altitude Weather for " + strAlt + ",000 feet is: %s\n", altitudeWeather) ;
				outputFile.println(String.format("The altitude Weather for " + strAlt + ",000 feet is: %s", altitudeWeather)) ;
			}
			//print the wind direction to file
			System.out.printf("Wind Direction: %s\n", windDirection) ;
			outputFile.println(String.format("Wind Direction: %s", windDirection)) ;
			//print the wind speed to file
  			System.out.printf("Wind Speed: %s\n", windSpeed) ;
			outputFile.println(String.format("Wind Speed: %s", windSpeed )) ;
      	//print the wind temp to file
			System.out.printf("Wind Temp: %s\n", windTemp ) ;
			outputFile.println(String.format("Wind Temp: %s", windTemp )) ;
			//print a carriage return to make data look nice
			System.out.println("") ;
			outputFile.println("") ;
		}
		// print who programed the code
      System.out.println("Program by Ramon Ortega") ;
		outputFile.println("Program by Ramon Ortega") ;
		// close the input and output files after we are done looping through the input file
		inputFile.close() ;
		outputFile.close() ;
	}
}