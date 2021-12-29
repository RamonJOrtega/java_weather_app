
/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/16/2013
@assignment.number Final Project - A190-15
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/ 

public class NWSFB            	 //name of the class
{
	private String strWeather ;   //the private class level variable only NWSFB can see
	
   /**
   This construtor function sets a line of airport weather to the private class
   variable, strWeather, for use in this class.
   
   @param strVar a line of weather for an airport
   @return nothing   */
	public NWSFB(String strVar)
	{
		strWeather = strVar ;
	}
	
	public String getStationID(String strAlt)
	{
		return strAlt.substring(0,3) ;
	}
	/**
   This private function accepts a string called strAlt representing an 
   altitude and returns an integer representing the starting
   character position of the altitude weather in the station weather string
   by use of a switch statement for all of the 9 altitudes
   
   @param strAlt Altitude in thousands as a string
   @return an integer representing the character postion with the strWeather string
   */
	private int getPos(String strAlt)
	{
		int intRet = 0 ;

      switch (strAlt)
      {
        case "03" :
            intRet = 4;
            break;
         case "06" :
            intRet = 9;
            break;
         case "09" :
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
   This public function will call getpos to determine where to 
   start extracting seven characters from the class level variable
	containing the station weather.
   
   @param strAlt - string representing altitude in thousands of feet.
   @return seven character string representing the altitude weather.
   */
	public String getAltitudeWeather(String strAlt)
	{
		String altitudeWeather = "XX";
		int intPos = getPos(strAlt);
		
		if (strAlt.compareTo("03") == 0 )
		{
      	altitudeWeather = strWeather.substring(intPos, intPos + 5) ;
		}
		else
		{
      	altitudeWeather = strWeather.substring(intPos, intPos + 7) ;
		}
		
		return altitudeWeather ;
	}
	/**
   This function will call getAltitudeWeather and extract the
   first two characters and concatenate a zero to the
   return value to show the user the direction in degrees that the
   wind is coming from
   
   @param strAlt String representing altitude in thousands of feet.
   @return windDir String representing wind direction at the specified altitude in degrees.
   */
	public String getWindDirection(String strAlt)
	{
		String windDir = "XX" ; //declare and initialize a wind direction variable string
      if(getAltitudeWeather(strAlt).substring(0,2).compareTo("  ")== 0)
      {//if the first two spaces of the altitude weather are blank, there is no wind direction
         windDir = "N/A" ;
      }
      else if (getAltitudeWeather(strAlt).substring(0,4).compareTo("9900")== 0)
      {//if the first for spaces of the altitude weather are 9900, the wind is calm
        windDir = "Calm" ;
      }
      else if(Integer.parseInt(getAltitudeWeather(strAlt).substring(0,2)) > 36)
      {//if the first two spaces of the altitude weather are greater than 36, subtract 50 from the direction
         windDir = Integer.parseInt(getAltitudeWeather(strAlt).substring(0,2)) - 50 + "0 degrees";
      }
      else 
      {//otherwise the wind direction is the first two characters of the altide weather times 10
         windDir = getAltitudeWeather(strAlt).substring(0,2)+ "0 degrees" ;
      }   
      return windDir;
	}
	 /**
   This calls the method, getAltitudeWeather extract the 3nd and 4th
   characters of the strWeather string. The method returns them as
   the wind speed at the specified altitude.
   
   @param strAlt Altitude in thousands as a string
   @return windSpeed The wind speed at the specified altitude in knots
   */
	public String getWindSpeed(String strAlt)
	{
		String windSpeed = "XX" ; //declare/initialize a windSpeed string variable
      if(getAltitudeWeather(strAlt).substring(2,4).compareTo("  ") == 0)
      {//if the 3rd and 4th character of strWeather are blank, there is no wind speed
         windSpeed = "N/A" ;
      }
      else if (getAltitudeWeather(strAlt).substring(0,4).compareTo("9900") == 0)
      {//if the first four characters of strWeather are 9900, the wind speed is calm or 0 knots
         windSpeed = "Calm" ;      
      }
      else if (Integer.parseInt(getAltitudeWeather(strAlt).substring(0,2)) > 36)
      {//if the 3rd and 4th character of strWeather are a number greater than 36, the wind speed is that number plus 100
         windSpeed = Integer.parseInt(getAltitudeWeather(strAlt).substring(2,4)) + 100 + " knots" ;
     }
      else 
      {// otherwise the windspeed is always the 3rd and 4th characters of the strWeather string
         windSpeed = getAltitudeWeather(strAlt).substring(2,4)+ " knots" ;
      }
      return windSpeed;
	}
	 /**
   This public method calls the method, getAltitudeWeather to extract final characters
   of the stationWeather string. The method returns them as
   the temperature at the specified altitude.
   
   @param strAlt Altitude in thousands as a string
   @return windTemp The temperature of the air at the specified altitude
   */
	public String getWindTemp(String strAlt)
	{
		String windTemp = "XX" ; //declare/initialize a windTemp varable string
      if (getAltitudeWeather(strAlt).substring(4,5).compareTo(" ") == 0 )
      {//if the character at position 4 is blank in the strWeather string, there is no wind temperature
         windTemp = "N/A" ;
      }//if the altitude we want temperature for is greater than 24000 feet, the temperature is always negative
      else if (Integer.parseInt(strAlt) > 24 )
      {
         windTemp = "-" + getAltitudeWeather(strAlt).substring(4,6) + " degrees Celsius" ;
      }
      else
      {//otherwise the wind temperature is a alwayst the last 3 characters of strWeather in degrees C
         windTemp = getAltitudeWeather(strAlt).substring(4,7) + " C" ;
      }// return the wind temperature string
      return windTemp ;
	}
} // end of class