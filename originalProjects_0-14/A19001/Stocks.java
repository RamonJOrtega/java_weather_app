/*----------------------------------------------------------------
* This program calculates Joe's profit/loss from buying and selling stock
* Week 01 Assignemt
* Ramon Ortega   08/26/2013
*-----------------------------------------------------------------*/

public class Stocks
   {
      public static void main(String[] args)
      {
      
       // Display Headers
         System.out.println("Stock Transaction Program, pg. 108");
         System.out.println("Ramon Ortega \r");
      
      //declare the given constant variables of the problem:
         //number of shares Joe purchased
         final int int_shares = 1000;
         //purchasing price in dollars per share of stock
         final double dbl_initial_cost = 32.87;
         //selling price in dollars per share of stock
         final double dbl_end_cost = 33.92;
         //commision rate Joe had to pay
         final double dbl_com_rate = 0.02;
         //commission amount Joe had to pay
      
      //declare and calculate the amount of money joe paid for the stock   
      double dbl_initial_purchase = int_shares * dbl_initial_cost;
      //display the amount of money joe paid for the stock
      System.out.printf("Joe first baught his stocks for %.2f dollars.\n", dbl_initial_purchase);
      
      //declare and calculate the amount of commission Joe paid his broker when the stock was purchased
      double dbl_broker_cost1 = dbl_initial_purchase * dbl_com_rate;
      //display the amount of commission Joe paid his broker when the stock was purchased
      System.out.printf("But Joe paid a brokerage fee of %.2f dollars for buying stock.\n", dbl_broker_cost1);
      
      //declare and calculate the amount of money Joe sold the stock for
      double dbl_end_yeild = int_shares * dbl_end_cost;
      //display the amount of money Joe sold the stock for
      System.out.printf("Joe sold all his stock for %.2f dollars.\n", dbl_end_yeild);
      
      //declare and calculate the amount of commision Joe paid his broker when the stock was sold
      double dbl_broker_cost2 = dbl_end_yeild *dbl_com_rate;
      //display the amount of commision Joe paid his broker when the stock was sold
      System.out.printf("Joe paid a brokerage fee of %.2f dollars for selling stock.\n", dbl_broker_cost2);
      
      //declare and calculate the amount of profit Joe made overall
      double dbl_profit =   dbl_end_yeild
                          - dbl_initial_purchase
                          - dbl_broker_cost1
                          - dbl_broker_cost2;
      //display the amount of profit Joe made overall
      System.out.printf("Joe's overall profit/loss was %.2f dollars.", dbl_profit);
      
      System.out.println(dbl_profit.toString() ) ;
      
      //end the program
      System.exit(0);
      }
   }