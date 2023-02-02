//This program calculates Joe's profit/loss from buying and selling stock

public class Stocks
   {
      public static void main(String[] args)
      {
      
       // Display Headers
         System.out.println("Stock Transaction Program, pg 108");
         System.out.println("Ramon Ortega \r");
      
      //declare the given variables of the problem:
         //number of shares Joe purchased
         int int_shares = 1000;
         //purchasing price in dollars per share of stock
         double dbl_initial_cost = 32.87;
         //selling price in dollars per share of stock
         double dbl_end_cost = 33.92;
         //commision rate Joe had to pay
         double dbl_com_rate = 0.02;
         //commission amount Joe had to pay
         
      //display the amount of money joe paid for the stock
      double dbl_initial_purchase = int_shares * dbl_initial_cost;
      System.out.println ("Joe first baught his stocks for " + dbl_initial_purchase + " dollars.");
      
      //display the amount of commission Joe paid his broker when the stock was purchased
      double dbl_broker_cost1 = dbl_initial_purchase * dbl_com_rate;
      System.out.println ("But Joe paid a brokerage fee of " + dbl_broker_cost1 + " dollars for buying stock.");
      
      //display the amount of money Joe sold the stock for
      double dbl_end_yeild = int_shares * dbl_end_cost;
      System.out.println ("Joe sold all his stock for " + dbl_end_yeild + " dollars.");
      
      //display the amount of commision Joe paid his broker when the stock was sold
      double dbl_broker_cost2 = dbl_end_yeild *dbl_com_rate;
      System.out.println ("Joe paid a brokerage fee of " + dbl_broker_cost2 + " dollars for selling stock.");
      
      //display the amount of profit Joe made overall
      double dbl_profit =   dbl_end_yeild
                          - dbl_initial_purchase
                          - dbl_broker_cost1
                          - dbl_broker_cost2;
      System.out.println ("Joe's overall profit/loss was " + dbl_profit + " dollars.");
      
      //end the program
      System.exit(0);
      }
   }