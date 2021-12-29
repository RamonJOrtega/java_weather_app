  
/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/16/2013
@assignment.number Final Project - A190-15
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/    
public class A19015
{
    /**
   This function is the main method for the A19015 class. It instantiates a WeatherForm object, thereby
   running a program that creates a weather database and displays a form. The form has buttons for clearing
   the database, downloading weather to a file, storing the file in the database, storing XML web data in the 
   database, updating comboboxes, and generating weather reports for selected airports.
   */
   public static void main (String[] args)
   {
      System.out.println("A19015") ;
      WeatherForm myWeather = new WeatherForm() ;
   }  
}