   import java.sql.* ;
   
   /**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/01/2013
@assignment.number Schedule Database - A190-13
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/
   
   public class ShowCoffeeDescripitions
   {
      public static void main(String[] args)
      {
         System.out.println("Hello World") ;
         final String DB_URL = "jdbc:derby:Schedule" ;
         try
         {
            Connection conn = DriverManager.getConnection(DB_URL) ;
            Statement stmt = conn.createStatement();
            String strSQL = "SELECT * FROM schedule" ;
            ResultSet result = stmt.executeQuery(strSQL) ;
            while(result.next())
            {
               System.out.println(result.getString("CRN")) ;
					System.out.println(result.getString("ROOM")) ;
			
            }
				conn.close() ;
         }
            catch (Exception Ex)
            {
               System.out.println(Ex.getMessage());
            
            }
      
      }
   }
