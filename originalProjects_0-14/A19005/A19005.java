import java.util.Scanner;
import java.io.* ;

/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.12 09/29/2013
@assignment.number NWSFB class - A190-05
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/

public class A19005 	//This program reads an input file, organizes it, displays the
							//result to the user, and prints the results to an output file
{
	public static void main(String[] args)throws IOException	//main method with IOException
	{
		//create file ouput and inputs
		PrintWriter outputFile = new PrintWriter("FBOUT.txt") ;
		File myFile = new File("FBIN.txt") ; //pg 246
		Scanner inputFile = new Scanner(myFile);
		//declare and initialize variables
		String strAlt = "XX" ;
		String stationID = "XX" ;
		String altitudeWeather = "XX";
		String windDirection = "XX" ;
		String windSpeed = "XX" ;
		String windTemp = "XX" ;
		int whileLoopCount = 1 ;            //it's always good to count the iterationss in a loop
		
		String strStationWeather = ("XX");	//declare and initialize the main variable of the program
		
		//make sure the file exists
		if( !myFile.exists())
		{
			System.out.println("The file FBIN.txt cannot be found" ) ;
			System.exit(0) ;
		}
		
		//Create a header for the output file
		System.out.println("Black Mountain Aviation Center") ;	
		outputFile.println("Black Mountain Aviation Center") ;	
				
		//read the file to change it and outpute results
		while (inputFile.hasNext()) // read and write until the end of the input file.
		{
			if (whileLoopCount == 1 )// discard the first 7 lines of input file for the first iteration
			{
				for(int number = 0 ; number < 7 ; number++)
				{
					strStationWeather = inputFile.nextLine()+ " " ;
				}		
			}
			whileLoopCount++ ;
			
			strStationWeather = inputFile.nextLine() + " "; // read a line from the file
				
			NWSFB05 myWeather = new NWSFB05(strStationWeather); //instantiate the weather object
			
			if (strStationWeather.length() >= 65) //make sure we have a weather line and not a header or blank line
			{	
				//print the airport id to the file
				stationID = myWeather.getStationID(strStationWeather) ;
				System.out.printf("Station ID: %s\n", stationID) ;
				outputFile.println(String.format("Station ID: %s", stationID)) ;
				
				for (int alt = 0 ; alt < 9 ; alt++) //for each of the 9 altitudes of every airpot
				{												
					switch (alt)							//store the 9 altitudes as a string
					{
						case 0 :
            		strAlt = "3" ;;
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
					altitudeWeather =  myWeather.getAltitudeWeather(strAlt) ;
					windDirection = myWeather.getWindDirection(strAlt);
					windSpeed = myWeather.getWindSpeed(strAlt) ;
					windTemp = myWeather.getWindTemp(strAlt) ;
					
					//print the altitude weather string for every altitude
					if (altitudeWeather.substring(0,2).compareTo("  ") == 0 )	//if is not an altitude weather string
					{																				//print N/A to the file
						System.out.print("The altitude Weather for " + strAlt + ",000 feet is: N/n") ;
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
			}
			//print a seperator line between every airport to make data look nice
			System.out.println("=======================");
			outputFile.println("=======================");
			
		}
      // print who programed the code
      System.out.println("Program by Ramon Ortega") ;
		outputFile.println("Program by Ramon Ortega") ;
		// close the input and output files after we are done looping through the input file
		inputFile.close() ;
		outputFile.close() ;
	}
}