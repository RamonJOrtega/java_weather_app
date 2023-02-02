/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.00 11/10/2013
@assignment.number Dowloading XML Files - A190-11
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/ 

public interface DBTemplate
{
	/**
   This function adds a record to the specified table using the specified
   key. It returns true if the record was added and false if not.
   
   @param strTable a string representing the name of the table in the database
   @param strKeyName a string representing the name of the key field in the table
   @param strKeyContents a string representing the contents to be placed in the key field
   @return blnStatus a boolean flag representing whether or not the record was added
   */ 
	public boolean addRecord(String strTable, String strKeyName, String strKeyContents) ;
   /**
   This function sets the db connection to null, therby closing the connection.
   This function is necessary for other users to access the database without problems
   
   @param none
   @return none
   */ 
	public void close() ;
   /**
   This function updates the database by deleting all of the records in
   a specific table.If the rows were deleted successfully, the function 
   returns true and false if not.
      
   @param strTable a string representing the name of the table in the database
   @return flag a boolean flag representing whether or not the records were deleted
   */ 
	public boolean deleteAll(String strTable) ;
   /**
   This function is used to retrieve data from a single field in a table. It is
   used in conjunction with the query function to desplay the database table field
   contents to the user.
   
   @param strFieldName a string representing the name of a field to be displayed
   @return strRet a string representing the contents of the specified field
   */ 
	public String getField(String strFieldName) ;
   /**
   This function is used to tell if a result set has more records. It returns true
   if there are more records, and false if not.
   
   @param none
   @return blnRet a boolean flag representing whether or not there are more records
   */ 
	public boolean moreRecords() ;
   /**
   This function opens the database connection between java and microsoft access.
   
   @param strDataSourceName a string the ODBC name you gave the operating system
                              under administrative tools
   @return none
   */
	public void openConnection(String strDataSourceName) ;
   /**
   This function passes an SQL statement to the database and creates an internal
   record set that can be accessed by the getField() function
   
   @param strSQL a string representing a valid SQL statement
   @return none
   */ 
	public void query(String strSQL) ;
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
	public boolean setField(String strTable, String strKeyName, String strKeyContents, String strFieldName, String strFieldContents) ;
	/**
   This function simply returns a message using System.out.println() for use in testing
   
   @param strVar a string representing the words you want displayed to the user
   @return none
   */
   public void status(String strVar) ;
	
}

