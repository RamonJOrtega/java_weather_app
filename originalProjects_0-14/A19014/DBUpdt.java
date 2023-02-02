import java.sql.* ;

/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/01/2013
@assignment.number Schedule Database - A190-13
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/
public class DBUpdt implements DBTemplate
{
 // Class Level Variables
   Connection dbConn;      //java needs a connection to the derby database
   Statement  dbCmdText;   //every data base application in java needs a statement object
   ResultSet  dbRecordset; //a record set is helpful for viewing the contents of a database
   String     dbTable;     //the string represents the name of the database table
   String     dbKeyField;  //the string represents the name of the key field, in this case, the CRN

    /**
   This function is the constructor. 
   
   @param none
   @return none
   */ 
   public DBUpdt()
   {
   }
   /**
   This function adds a record to the specified table using the specified
   key. It returns true if the record was added and false if not.
   
   @param strTable a string representing the name of the table in the database
   @param strKeyName a string representing the name of the key field in the table
   @param strKeyContents a string representing the contents to be placed in the key field
   @return blnStatus a boolean flag representing whether or not the record was added
   */  
   public boolean addRecord(String strTable,
      String strKeyName, String strKeyContents) 
   {
      String strSQL;
      boolean blnStatus;
      try
      {
         // check to see if the record exists
         dbCmdText = dbConn.createStatement();
         strSQL = "SELECT * FROM " + strTable + " WHERE " + strKeyName + "='" + strKeyContents + "'";
         status("***" + strSQL);
         dbRecordset = dbCmdText.executeQuery(strSQL);
         if(!moreRecords())
         {
            // the record does not exist, therefore add it to the database
            strSQL = "INSERT INTO " + strTable + " (" + strKeyName + ") VALUES ('" + strKeyContents + "')";
            status(strSQL);
            dbCmdText.executeUpdate(strSQL);
            dbRecordset.close();
            status("Record added");
            blnStatus = true;
         }
         else
         {
            status("Record NOT added");
            blnStatus = false;
         }
      }
      catch (SQLException e)
      {
         blnStatus = false;
         status("Add Record failed") ;
      }
      return blnStatus;
   }
	
   /**
   This function sets the db connection to null, therby closing the connection.
   This function is necessary for other users to access the database without problems
   
   @param none
   @return none
   */  
   public void close()
   {
   
      if (dbRecordset != null)
      {
         try 
         {
            dbRecordset.close();
         } 
         catch (SQLException e) 
         {
            status(e.getMessage() + "resultset failed to close");
         }
      }
      if (dbCmdText != null) 
      {
         try 
         {
            dbCmdText.close();
         } 
         catch (SQLException e) 
         {
            status(e.getMessage() + "statement failed to close"); 
         }
      }
      if (dbConn != null) 
      {
         try 
         {
            dbConn.close();
         } 
         catch (SQLException e) 
         {
            status(e.getMessage() + "connection failed to close"); 
         }
      }
   
      //try
     // {
         //dbRecordset.close(); 
         //dbConn.close() ;
   	//	System.out.println("database is closed") ;
     // }
     // catch (SQLException e)
     // {
      //   status(e.getMessage() + "database failed to close"); 
     // }  	
   }
	/**
   This function updates the database by deleting all of the records in
   a specific table.If the rows were deleted successfully, the function 
   returns true and false if not.
      
   @param strTable a string representing the name of the table in the database
   @return flag a boolean flag representing whether or not the records were deleted
   */  
   public boolean deleteAll(String strTable)
   {  
      boolean flag = false ;
      try
      {
         String sqlStatement ="DELETE FROM " + strTable ;
         dbCmdText = dbConn.createStatement();
         dbCmdText.execute(sqlStatement);
         flag = true ;
         System.out.println("Wiped all rows in database clean!") ;
      }
      catch (Exception e)
      {
         flag = false;
         status("delete failed") ;
         System.out.println(e.getMessage()) ;
      }
      return flag ;
   } 
	/**
   This function is used to retrieve data from a single field in a table. It is
   used in conjunction with the query function to desplay the database table field
   contents to the user.
   
   @param strFieldName a string representing the name of a field to be displayed
   @return strRet a string representing the contents of the specified field
   */  
   public String getField(String strFieldName)
   {
      String strRet=""; //initialize a string to null
      try
      {
         //crete a recordset object using the specified field
         strRet = dbRecordset.getString(strFieldName);
      }
      catch (SQLException e) 
      {
         status(e.getMessage()) ;
         status("getfield failed");
      }
      return strRet; //return the field contents
   }
	/**
   This function is used to tell if a result set has more records. It returns true
   if there are more records, and false if not.
   
   @param none
   @return blnRet a boolean flag representing whether or not there are more records
   */  
   public boolean moreRecords()
   {
      Boolean blnRet = false; //boolean flag in case of success
      try
      {  //set flag to successful if there is more records in the recordset
         blnRet = dbRecordset.next();
      }
      catch (Exception e)
      {
         blnRet = false;
         status("more Records failed") ;
      }
      return blnRet; // only one RETURN in each function!
   }
	/**
   This function opens the database connection between java and microsoft access.
   
   @param strDataSourceName a string the ODBC name you gave the operating system
                              under administrative tools
   @return none
   */  
   public void openConnection(String strDataSourceName)
   {
      boolean blnStatus;
      blnStatus = false;
      try
      {  //protocal, subrprotocal, etc
         //Class.forName("jdbc:derby:CoffeeDB;create=true");
         //open a conection to the weather 
         dbConn=DriverManager.getConnection("jdbc:derby:" + strDataSourceName +";create=true") ;
         if (dbConn == null)
         {  
            status("Connection Failed");
         }
         else 
         {
            status("Connection Successful");
            //create a statement if the connection is successful
            dbCmdText = dbConn.createStatement();
            blnStatus = true;
         }
      }
      catch (Exception e)
      {
         status("Problems opening connection");
         status(e.getMessage()) ;
      }
   }
	/**
   This function passes an SQL statement to the database and creates an internal
   record set that can be accessed by the getField() function
   
   @param strSQL a string representing a valid SQL statement
   @return none
   */  
   public void query(String strSQL)
   {
      try
      {  //create a recordset for the query
         dbRecordset = dbCmdText.executeQuery(strSQL);
      }
      catch (Exception ex)
      {
         status("query failed") ;
      }
   }
	/**
   This function passes an SQL statement to the database object and uses the 
   statement, dbCmdText's executeUpdate function to make changes to the database.
   This function is used to create tables in the FALL2013 database
   
   @param strSQL a string representing a valid SQL statement
   @return none
   */  
   public void updateQuery(String strSQL)
   {
      try
      {  //pass an SQL statment to the DBUpdt statement's executeUpdate() methode
         //to make changes to the database
         dbCmdText.executeUpdate(strSQL);
      }
      catch (Exception ex)
      {  //tell the user if the method failed. 
         status(ex.getMessage() + " Update Query failed") ;
      }
   }
	/**
   This function updates a single field in the database table with the specified contents.
   It returns true the field was added, and false if not.
   
   @param strTable a string representing the database table name
   @param strKeyName a string representing key field name
   @param strKeyContents a string representing key field contents
   @param strFieldName a string representing the field name to be updated
   @param strFieldContents a string representing the contents to be added to the field name
   @return returnFlag a boolean flag telling whethere the update was successful or not
   */  
   public boolean setField(String strTable, String strKeyName, String strKeyContents, String strFieldName, String strFieldContents) 
   {
      boolean returnFlag ;
      try
      {  //create an SQL statement to update fields in the database
         String sqlStatement =   "UPDATE " + strTable + 
                                 " SET " + strFieldName + 
                                 " = '" + strFieldContents + 
                                 "' WHERE " + strKeyName + 
                                 " = '" + strKeyContents + "'" ;
         status(sqlStatement) ;
         //create a statement
         dbCmdText = dbConn.createStatement();
         //execute the query with the SQL command
         dbCmdText.executeUpdate(sqlStatement) ;
         //return true if successful
         returnFlag = true ;
      }               
      catch (Exception e)
      {
         returnFlag = false;
      }         
      return returnFlag ;
   }
	/**
   This function simply returns a message using System.out.println() for use in testing
   
   @param strVar a string representing the words you want displayed to the user
   @return none
   */
   public void status(String strVar) 
   {  
      //print what we handed the function
      System.out.println(strVar) ;
   }
   
}