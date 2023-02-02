
import java.util.Scanner;

/**
	The purpose of this program is to calculate the ramp weight of an aircraft.

    @author Ramon J. Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
    @version 1.01 08/27/2013
    @assignment.02 Ramp Weight Calculator - A
    @prgm.usage Called from the operating system
    @see "Gaddis, 2013, Starting Out with Java, From Control Structures through Objects, 5th Ed."
    @see <a href="http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html">JavaDoc Documentation</a>
*/

public class A19002
{
	public static void main(String[] args)
	{
		//Print Black Mountain Aviation Cener
		System.out.println("Black Mountain Aviation Center") ;
		//Print creator's name
		System.out.println("Programmed by Ramon J. Ortega \n") ;
		
		//declare variables
		double rampWeight ;
		double emptyWeight ;
		double bagWeight ;
		double gallons ;
		int passengersFront ;
		int passengersBack ;
		
		//declare constants
		final double manWeight = 170.0 ;
		final int fuelWeight = 6 ;
		final int pilot = 1 ;					//dont forget to add the pilot as a person!
		
		//Create a Scanner object to read input.
		Scanner keyboard = new Scanner(System.in);
				
		//Prompt the user for Aircraft Empty Weight
		System.out.println("Enter the Aircraft Empty Weight") ;
		//Get Empty Weight from user
		emptyWeight = keyboard.nextDouble();
		
		//Prompt the user for the Number of Passengers n the Front Seat
		System.out.println("Enter the Number of Passengers in the Front Seat") ;
		//Get Number of Passenters in the Front SEat from user
		passengersFront = keyboard.nextInt() ;
		
		// Prompt the user for the Number of Pssengers in the Back Seat
		System.out.println("Enter the Number of Passengers in the Back Seat") ;
		//Get Number of Passengers in the Back Seat from user
		passengersBack = keyboard.nextInt() ;
		
		// Promt the user for the Number of Gallons of Fuel
		System.out.println("Enter the Number of Gallons of Fuel") ;
		//Get Number of Gallons of Fuel from the user
		gallons = keyboard.nextDouble();
		
		//Prompt the user for the Baggage Weight
		System.out.println("Enter the Baggage Weight") ;
		//Get the Baggage Weight from the user
		bagWeight = keyboard.nextDouble() ;
		
		//Calculate Ramp Weight
		rampWeight = (manWeight * (passengersFront + pilot + passengersBack)) + (fuelWeight * gallons) + (emptyWeight) + (bagWeight) ;
		//Display the Ramp Weight
		System.out.printf("The Ramp Weight of the Aircraft is %.2f pounds", rampWeight) ;

	}
}
