
public class powers
{   
   public static void main(String[] args)
   {
      double num1 = 0 ;
      double num2 = 0 ;
      for (int j=0 ; j<8 ; j++)
	   {
		   num1=Math.pow(2,j+1)-Math.pow(2.0,j) ;
         
	   }

      
      
      System.out.println(num1) ;
   }
}