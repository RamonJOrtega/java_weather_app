import java.util.Scanner ; // Needed for Scanner class
import java.io.* ;         // Needed for File and IOException

/**
   This program reads an input file called Ch01-text.txt, formats the file with tabs, and writes formated lines 
   to an output file called Ch01-tab.txt

    @author Ramon J. Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
    @version 1.01 09/13/2013
    @assignment.03 Quiz Reader App - A190-03
    @prgm.usage Called from the operating system
    @see "Gaddis, 2013, Starting Out with Java, From Control Structures through Objects, 5th Ed."
    @see <a href="http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html">JavaDoc Documentation</a>  
*/

public class A19003A
{
   public static void main(String[] args) throws IOException
   {
      File myFile = new File("Ch01-text.txt") ;       //Open inpute file
       
      if (myFile.exists() == true)                    //Does file even exist? If it does then we execute main program

      {
         //Declare variables and objects
         int count = 1 ;                              //Make a counter to keep track of which question we are on
         System.out.println("The file exists!") ;     //Tell the user if the input file exists
         Scanner inputFile = new Scanner(myFile) ;    //Create a scanner object that will contain the input file 
         String line = inputFile.nextLine();          //Create a string to contain 1 line of the inpute file
                                                     
         PrintWriter outputFile = new PrintWriter("Ch01-tab.txt");   //create the output file to write to
        
         for (int i=1 ; i<5 ; i++)                          //ignore/use up the first 5 lines of the text file
         {
            line = inputFile.nextLine() ;
         }
         while (inputFile.hasNext())                        //This loop iterates until the end of the file
         {
            line = inputFile.nextLine() ;                   //Read the file
            line = line.trim() ;                            //Trim the spaces off of the front of every line
            if (line.length() > 1)                          //If the line isn't all whitespace, begin comparing
            {
               if (line.substring(0,1).compareTo("_") == 0) //If the line is a question, begin writing to file
               {
                  System.out.println("question " + line) ;  //Display to the user that the line is a question
                  if (count != 1)
                  { 
                     outputFile.println("") ;                //Print blank to the new file so seperate questions appear on different lines, except for the first time
                  }
                  if (count < 10)                           //All questions before number 10 must be printed starting at index 7
                  {
                  outputFile.print( "MC\t" + line.substring(7));  //Write the multiple choice question to the file line
                  }
                  else                                      //All questions after number 10 must be printed starting at index 8
                  {                                                               
                  outputFile.print( "MC\t" + line.substring(8));  //Write the multiple choice question to the file line
                  }
                  count = count + 1 ;                       //Keep track of which question we are on so we know where to start substrings
               }
               if (line.substring(1,2).compareTo(".") == 0) //If the line is an answer, begin writing to file
               {
                  System.out.println("answer " + line) ;    //Dispay  to the user that the line is an answer     
                  outputFile.print( "\t" + line.substring(3)); //Write the answer to the file line
                  outputFile.print( "\tIncorrect");            //Write that the answer is incorrect after the answer
               }
               if (line.equals("Answer Page Reference"))    //At this line of the input file,we know we found the answer key
               {   
                  while (!(line.equals("Starting Out with Visual Basic 2012, 6thEd by Tony Gaddis/Kip Irvine")))
                  {                                         //Until we read the book title line, we are in the answer key
                     line = inputFile.nextLine();           //Get the next line of the key   
                     line = line.trim() ;                   //Get rid of the whitespace in the key
                     System.out.println ("key" + line) ;    //Display to the user the lines that are part of the answer key
                  }
               }                
            }
            else 
            {
               System.out.println("not important" + line) ;    //Display what lines aren't going to be written to file
            }

         }  
         inputFile.close();   //Always close the input file
         outputFile.close();  //Always close the outpuf file
      }
      else
      {
         System.out.println("The file doesn't even exist!") ;  //Tell the user if the input file doesn't exist
      }    
   }
}