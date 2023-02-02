import java.awt.*;
import java.awt.event.* ;
import javax.swing.*;
import java.util.StringTokenizer ;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList ;

/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/01/2013
@assignment.number Schedule Database - A190-13
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/
public class ScheduleForm extends JFrame
{
// Class level variables
   JPanel panel ;                //a panel that goes ont the window
   JComboBox cboSub ;            //the class subject combobox
   JComboBox cboCourse ;         //the class course combobox
   JComboBox cboRoom ;           //the class room combobox
   JComboBox cboInst ;           //the class instructor combobox
   JComboBox cboList ;           //the combobox used to display search results. USED IN PLACE of LISTBOX
   JButton btnCreateTables ;     //the create tables button
   JButton btnLoadSchedule ;     //the load schedule button
   JButton btnLoadCriteria ;     //the load criteria button
   JButton btnLoadListbox ;      //the load listbox button that actually LOADS the cboList COMBOBOX
   final int WINDOW_WIDTH = 750 ;   //the width of the window
   final int WINDOW_HEIGHT = 550 ;  //the height of the window
   
   /**
   This function is the constructor. It creates the form and places all labels, buttons, comboboxes, and
   text fields on a panel and adds it to the frame. Then it sets everything to visible. 
   
   @param none
   @return none
   */ 
   public ScheduleForm()
   {  //set the window title
      setTitle("A19013");
      //set the window size
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT)  ;
      //let the X button close the program
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
      // section 20 - set up the panel
      // Set the JPanel Parameters
      panel = new JPanel(null) ; //null parameter allows x y axis placement of components
      panel.setPreferredSize(new Dimension(740,490)) ; //make the panel a little smaller than the frame so that it fits
      panel.setBackground(Color.lightGray) ;//collor the panel
      getContentPane().add(panel);          //add the panel to the frame
      
      //set the lables for the form by calling the setLabel() function
      setLabel(25, 100, "Subject") ;
      setLabel(200, 100, "Course") ;
      setLabel(375, 100, "Room") ;
      setLabel(550, 100, "Instructor") ;
      setLabel(25, 175, "CRN") ;
      setLabel(100, 175, "Subject") ;
      setLabel(175, 175, "Course") ;
      setLabel(100, 220, "Search Results") ;	
   
      //make the create tables button on the panel
      btnCreateTables = new JButton("Create Tables") ;
      btnCreateTables.setSize(150, 25) ;     // width, height
      btnCreateTables.setLocation(25,50) ;   //from left, from top
      panel.add(btnCreateTables);            //add the button to the panel
      btnCreateTables.addActionListener(new btnCreateTablesListener()) ; //add action listenter
      //make the load schedule button on the panel
      btnLoadSchedule = new JButton("Load Schedule") ;
      btnLoadSchedule.setSize(150, 25) ;      // width, height
      btnLoadSchedule.setLocation(200,50) ;   //from left, from top
      panel.add(btnLoadSchedule);             //add the button to the panel
      btnLoadSchedule.addActionListener(new btnLoadScheduleListener()) ; //add action listenter
      //make the load criteria button on the panel
      btnLoadCriteria = new JButton("Load Criteria") ;
      btnLoadCriteria.setSize(150, 25) ;     // width, height
      btnLoadCriteria.setLocation(375,50) ;  //from left, from top
      panel.add(btnLoadCriteria);            //add the button to the panel
      btnLoadCriteria.addActionListener(new btnLoadCriteriaListener()) ; //add action listenter
      //make the load listbox button on the panel
      btnLoadListbox = new JButton("Load Listbox Using Criteria") ;
      btnLoadListbox.setSize(300, 25) ;      //width height
      btnLoadListbox.setLocation(25, 250) ;  //from left, from top
      panel.add(btnLoadListbox) ;
      btnLoadListbox.addActionListener(new btnLoadListboxListener()) ;
   	 
      //set initial combo boxes on the panel and add a listener for them 
      //make the class subject combobox on the form 
      String subArray[]={"Select Subject"} ; //put only one item in the box for now
      cboSub = new JComboBox(subArray) ;     //create a combobox
      cboSub.setSize(150, 20) ;              //width, height
      cboSub.setLocation(25, 125);           //from left, from top
      cboSub.setForeground(Color.blue) ;     //set the font color
      panel.add(cboSub) ;                    //add the combobox to the pannel
      cboSub.addActionListener(new MyComboBoxListenerClass()) ;   //link the combobox to an actionlistenter
      //make the class course combobox on the form      
      String courseArray[] = {"Select Course"} ;//put only one item in the box for now
      cboCourse = new JComboBox(courseArray) ;  //create a combobox
      cboCourse.setSize(150, 20) ;              //width, height
      cboCourse.setLocation(200, 125);          //from left, from top
      cboCourse.setForeground(Color.blue) ;     //set the font color
      panel.add(cboCourse) ;                    //add the combobox to the pannel
      //make the class room combobox on the form 
      String roomArray[] = {"Select Room"} ;    //put only one item in the box for now
      cboRoom = new JComboBox(roomArray) ;     //create a combobox
      cboRoom.setSize(150, 20) ;                //width, height
      cboRoom.setLocation(375, 125);            //from left, from top
      cboRoom.setForeground(Color.blue) ;       //set the font color
      panel.add(cboRoom) ;                      //add the combobox to the pannel
      cboRoom.addActionListener(new MyComboBoxListenerClass()) ;   //link the combobox to an actionlistenter
      //make the class instructor combobox on the form 
      String instArray[] = {"Select Instructor"} ;//put only one item in the box for now
      cboInst = new JComboBox(instArray) ;      //create a combobox
      cboInst.setSize(150, 20) ;                //width, height
      cboInst.setLocation(550, 125);            //from left, from top
      cboInst.setForeground(Color.blue) ;       //set the font color
      panel.add(cboInst) ;                      //add the combobox to the pannel
      cboInst.addActionListener(new MyComboBoxListenerClass()) ;   //link the combobox to an actionlistenter
   	
      //create the search result combobox IN PLACE OF A LISTBOX or TEXTAREA
      String listArray[] = {""} ;                 //at first make the box empty
      cboList = new JComboBox(listArray) ;        //create the combobox
      cboList.setSize(300, 20) ;                  //width, height
      cboList.setLocation(25, 200);               //from left, from top
      cboList.setForeground(Color.blue) ;         //set the font color
      cboList.setFont(new Font("Courier New", Font.BOLD, 15)) ;
      panel.add(cboList) ;                        //add the combobox to the pannel
      cboList.addActionListener(new MyComboBoxListenerClass()) ;   //link the combobox to an actionlistenter
   
      //set the form visible at the end of the constructor to make sure that components have first been added
      setVisible(true);
   
   }
	
   /**
   @author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
   @version 1.01 12/01/2013
   @assignment.number Schedule Database - A190-13
   @prgm.usage Called from the operating system
   @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
   @see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
   */
   private class MyComboBoxListenerClass implements ActionListener
   {
   /**
   This function is the required function for an action listener. It performs no function other than
   letting the user know he or she has made a selection from a combobox.
   
   @param event an ActionPerformed type that happenes everytime the actionlistener is notified
   */ 
      public void actionPerformed(ActionEvent event)
      {  
         System.out.println("MyComboBoxListenerClass");                                
      }
   }
   /**
   @author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
   @version 1.01 12/01/2013
   @assignment.number Schedule Database - A190-13
   @prgm.usage Called from the operating system
   @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
   @see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
   */ 
   private class btnCreateTablesListener implements ActionListener
   {
   /**
   This function is the required function for an action listener. It is used
   to call the create the FALL2013 database in the project folder. The function puts
   a schedule, students, and classes table inside the database. If the database is already
   created, nothing happens.This function is fired when the "CREATE TABlES" button is pressed
   in the main program.
   
   @param event an ActionPerformed type that happenes everytime the actionlistener is notified
   */ 
      public void actionPerformed(ActionEvent event)
      {  //create a string to hold SQL commands
         String strSQL ;
         //let the user know that that button has indeed been clicked
         System.out.println("btnCreateTablesListener");
         //instantiate a database oject
         DBUpdt db = new DBUpdt() ;
         //connect to the FALL2013 derby database using the openConnection method
         db.openConnection("FALL2013") ;
         //Setup the SQL statement for the SCHEDULE table
         strSQL = "CREATE TABLE schedule (CRN varchar(5), SUBJ varchar(4), ";
         strSQL = strSQL + "CRS varchar(4), TITLE varchar(30), DAYS varchar(10), ";
         strSQL = strSQL +"START_TIME varchar(255), END_TIME varchar(255), ";
         strSQL = strSQL + "BLDG varchar(255), ROOM varchar(255), INSTRUCTOR varchar(255), ";
         strSQL = strSQL + "START_DATE varchar(255), END_DATE varchar(255), ";
         strSQL = strSQL + "WEEKS varchar(255), SEATS_LEFT varchar(255), STATUS varchar(255) )";
         System.out.println(strSQL) ;
         //use the new updateQuery function in the DBUpdt class to make changes to the tables
         db.updateQuery(strSQL) ;
         //close the database so others can use it
         db.close() ;
      	
         //create a second DBUpdt object to create the second table for students
         DBUpdt dbTwo = new DBUpdt() ;
         //connect to the FALL2013 derby database using the openConnection method
         dbTwo.openConnection("FALL2013") ;
         //Setup the SQL statement for the students table
         strSQL = "CREATE TABLE students (stuID varchar(7), stuLastName varchar(30), ";
         strSQL = strSQL + "stuFirstName varchar(30) )";
         System.out.println(strSQL);
         //use the new updateQuery function in the DBUpdt class to make changes to the tables
         dbTwo.updateQuery(strSQL) ;
         //close the database so others can use it
         dbTwo.close() ;
         
      	//create a third DBUpdt object to create the third database table for classes
         DBUpdt dbThree = new DBUpdt() ;
         //connect to the FALL2013 derby database using the openConnection method
         dbThree.openConnection("FALL2013") ;
         //Setup the SQL statement for the classes table
         strSQL = "CREATE TABLE classes (stuID varchar(7), CRN varchar(5) ) ";
         System.out.println(strSQL);
         //use the new updateQuery function in the DBUpdt class to make changes to the tables
         dbThree.updateQuery(strSQL) ;
         //close the database so others can use it
         dbThree.close() ;
      }
   }
   /**
   @author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
   @version 1.01 12/01/2013
   @assignment.number Schedule Database - A190-13
   @prgm.usage Called from the operating system
   @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
   @see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
   */ 
   private class btnLoadScheduleListener implements ActionListener
   {
   /**
   This function is the required function for an action listener. It is used
   to read the schedule text file and put the contents into the FALL2013 database's schedule table
   when the load schedule button is pressed on the form
   
   @param event an ActionPerformed type that happenes everytime the actionlistener is notified
   */ 
      public void actionPerformed(ActionEvent event)
      { 
         System.out.println("btnLoadScheduleListener");  //let the user know that the schedule is being loaded
         try
         {
            DBUpdt db = new DBUpdt();                    //create a new DBUpdt object
            db.openConnection("FALL2013") ;              //open the connectio with the FALL2013 database
            db.deleteAll("schedule");                    //wipe all records from the schedule table
            String strInputFile = "schedule.txt.csv" ;   //create a string to hold the schedule text file's name
            File myFile = new File(strInputFile);        //create a new file object  from the schedule text
            Scanner inputFile = new Scanner(myFile) ;    //create a scanner object to read the created file
            String strRecord;                            //create a temportary string to hold lines of text
            String[] aryFields = new String[16];
            if (myFile.exists())                         //if the file exists, begin reading it
            { 
               strRecord = inputFile.nextLine();         //read the first line of the schedule text file
               System.out.println(strRecord);            //display the line to the user to show the file headers
               while (inputFile.hasNext())               //read the file until it's finished
               {
                  strRecord = inputFile.nextLine();      //read one line of the file at a time
                  System.out.println(strRecord);         //display the line just read to the user
                  //parse record to see if there are quotes
                  if (strRecord.indexOf(",\"") > 0 )
                  {
                     //extract the quotes from the line before trying to split it
                     strRecord = cleanupField(strRecord);
                     //show the user that the line was cleaned up
                     System.out.println(strRecord);
                  }
                  //split record into fields that the user can search later
                  aryFields = strRecord.split( ",", 16);
                  db.addRecord("schedule", "crn", aryFields[0]);
                  db.setField("schedule", "crn", aryFields[0], "crs", aryFields[2]);
                  db.setField("schedule", "crn", aryFields[0], "subj", aryFields[1]);
                  db.setField("schedule", "crn", aryFields[0], "room", aryFields[8]);
                  db.setField("schedule", "crn", aryFields[0], "INSTRUCTOR", aryFields[9]);
               }
               //close the file
               inputFile.close();
            }
            //close the database
            db.close();
         }//catch any exceptions and notify the user if this function failed
         catch (Exception ex)
         {
            System.out.println("load schedule failed") ;
            System.out.println(ex.getMessage()) ;
         }
      }
   }
   /**
   @author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
   @version 1.01 12/01/2013
   @assignment.number Schedule Database - A190-13
   @prgm.usage Called from the operating system
   @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
   @see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
   */  
   private class btnLoadCriteriaListener implements ActionListener
   {
   /**
   This function is the required function for an action listener. It is used
   to populate the search criteria comboboxes when the load criteria button on the form is pressed
   
   @param event an ActionPerformed type that happenes everytime the actionlistener is notified
   */ 
      public void actionPerformed(ActionEvent event)
      {
         //let the user know that the load criteria button has been pressed and is responding
         System.out.println("btnLoadCriteriaListener");
         //create separate sql statements for each of the 4 search criteria from the comboboxes on the form
         String strSQLsubj = "SELECT DISTINCT subj FROM schedule";   //make sure to use DISTINCT so that
         String strSQLcrs = "SELECT DISTINCT crs FROM schedule";     //multiple records don't show up in the
         String strSQLroom = "SELECT DISTINCT room FROM schedule";   //recordset
         String strSQLinstructor = "SELECT DISTINCT INSTRUCTOR FROM schedule" ;
         try
         {  //update subject combobox
            DBUpdt subjDB = new DBUpdt() ;               //create a DBUpdt object
            subjDB.openConnection("FALL2013");           //create a connection with the FALL2013 Database
            subjDB.query(strSQLsubj) ;                   //perform a query with the class subject sql statment
            while (subjDB.moreRecords())                 //go through every record in the record set
            {
               cboSub.addItem(subjDB.getField("subj")) ;//add the subject field in the record to the form's subject combobox
            }
            subjDB.close() ;                             //close the database so others can use it
            //update course combobox
            DBUpdt crsDB = new DBUpdt();                 //create a DBUpdt object
            crsDB.openConnection("FALL2013");            //create a connection with the FALL2013 Database
            crsDB.query(strSQLcrs) ;                     //perform a query with the class course sql statment
            while (crsDB.moreRecords())                  //go through every record in the record set
            {
               cboCourse.addItem(crsDB.getField("crs")) ;//add the class field in the record to the form's class combobox
            }
            crsDB.close() ;                              //close the database so others can use it
            //update room combobox
            DBUpdt roomDB = new DBUpdt() ;               //create a DBUpdt object
            roomDB.openConnection("FALL2013");           //create a connection with the FALL2013 Database
            roomDB.query(strSQLroom) ;                   //perform a query with the class course sql statment
            while (roomDB.moreRecords())                 //go through every record in the record set
            {
               cboRoom.addItem(roomDB.getField("room")) ;//add the class field in the record to the form's class combobox
            }
            roomDB.close() ;                             //close the database so others can use it
             //update instructor combobox
            DBUpdt instDB = new DBUpdt() ;               //create a DBUpdt object
            instDB.openConnection("FALL2013");           //create a connection with the FALL2013 Database
            instDB.query(strSQLinstructor) ;             //perform a query with the class instructor sql statment
            while (instDB.moreRecords())                 //go through every record in the record set
            {
               cboInst.addItem(instDB.getField("INSTRUCTOR"));//add the class instructor in the record to the form's instructor combobox
            }
            instDB.close() ;                             //close the database so others can use it
         }
         catch (Exception e)
         {  //catch any exceptions and notify the user if there was  problem updating the comboboxes
            System.out.println(e.getMessage() ) ;
            System.out.println("ComboBoxes not updated") ;
         }     
      }
   }

   /**
   @author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
   @version 1.01 12/01/2013
   @assignment.number Schedule Database - A190-13
   @prgm.usage Called from the operating system
   @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
   @see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
   */
   private class btnLoadListboxListener implements ActionListener
   {
   /**
   This function is the required function for an action listener. When the "load listbox using 
   criteria" button is pressed, the method  is used
   to call the btnLoadListbox function which updates the search result COMBOBOX, NOT A LISTBOX
   
   @param event an ActionPerformed type that happenes everytime the actionlistener is notified
   */ 
      public void actionPerformed(ActionEvent event)
      {  
         //print to the display that the button has been clicked
         System.out.println("btnLoadListboxListener");
         //call the btnLoadListbox function
         btnLoadListbox() ;  
      }
   }
		
   /**
   This function is used by the constructor to create and position all the various small
   labels on the frame
   
   @param fmLeft an integer representing the number of pixels from the left of the frame
   @param fmTop an integer representing the number of pixels from the top of the frame
   @param strVar a string object representing the contents that will be placed in the label
   @return label a JLabel object to label the components of the frame
   */ 
   public JLabel setLabel(int fmLeft, int fmTop, String strVar)
   {  
      JLabel label = new JLabel(strVar) ;                   //create the label
      label.setFont(new Font("Helvetica", Font.BOLD,12)) ;  //set the font
      label.setSize(100,20);                                // Length, Height
      label.setLocation(fmLeft,fmTop) ;                     //from left, from top
      label.setForeground(Color.blue) ;                     //Colors pg 778
      if(strVar.length() < 1 )
      {
      //BorderFactory pg 817
         label.setBorder(BorderFactory.createLineBorder(Color.black,1)) ;
      }
      panel.add(label) ;                                    //add the label to the panel
      return label ;                                        //also return a JLabel object    
   }

   /**
   This function is used to remove commas and spaces from fields in the schedule text file.
   it uses the replace() method of string objects to replace commas with  underscores and
   remove spaces.
   
   @param strVar a string representing a line of comma-delimated fields from the
   schedule text file
   @return strRet a string reperesenting a line of comma-delimated fields from the schedule
    text file where the commas in the name field have been replaced with underscores and the
    spaces in the name field have been removed.
   */ 
   public String cleanupField(String strVar)
   {
      //tell the user that the function has been called
      System.out.println("inside cleanupField()" ) ;
      int intStart = 0 ;                  //create and initialize two string index integers
      int intEnd = 0 ;
      String strName ;                    //create a string for the field we want to clean up
      String strRet ;                     //create a string for an entire line with the now clean feield
      intStart = strVar.indexOf(",\"") ;  //initialize the start index of the string to where the first instance of ", symbols are
      intEnd = strVar.indexOf("\",") ;    //initialize the end index of the string to where the first instence of ", symbols are
      //extract a substring of a line representing the name field. We don't want the starting quote and comma, 
      //so add 2 to the substring index
      strName = strVar.substring(intStart+2, intEnd); 
      //take the substring and replace commas in the name field with underscores
      strName = strName.replace(",","_") ;
      //take the substring and remove the spaces in the name field
      strName = strName.replace(" ", "") ;
      //recombine the now-clean substring with the original string line parameter by concatenating
      //substrings and shifting the substring index so that there are no quotes in the name field
      strRet = strVar.substring(0, intStart+1) + strName + strVar.substring(intEnd+1) ;
      //return the recbombined line of text
      return strRet ;
   }

   /**
   This function is used to populate the search result combobox on the form. NOTE THAT WE ARE
   POPULATING A COMBOBOX IN THE STEAD OF A LIST BOX. The function checks the contents of the
   search criteria comboboxes and uses the selections to create an sql statement that is passed
   to the FALL2013 derby data base and retrieve information from the schedule table using an
   instance of the DBUpdt class.
   
   @param none
   @return none
   */ 

   private void btnLoadListbox()
   {
      //createe a temporary string to hold substrings as needed
      String strTemp ;
      //create a string to hold SQL commands
      String strSQL = "";
      //create a string to build search criteria from the comboboxes on the form
      String strCriteria = "";
      //create a DBUpdt object to access the database
      DBUpdt db = new DBUpdt() ;          
      //cleare the combobox of all previous class search results
      cboList.removeAllItems();
      //Build the criteria
      strSQL = "SELECT * FROM schedule "; //this is teh general sql command that can be added to
      if (cboSub.getSelectedIndex()>0) 
      {  //if a subject in the the subject combobox is selected,
         //build piece of SQL statement that relfects the subject as search criteria
         strCriteria = " AND " + " subj = '" + cboSub.getSelectedItem() + "'" ;
      }
      else
      {
         strCriteria = "" ;
      }
      if (cboCourse.getSelectedIndex() > 0)
      {  //if a course in the the course combobox is selected,
         //build piece of SQL statement that relfects the course as search criteria
         strCriteria = strCriteria + " AND " + " crs = '" + cboCourse.getSelectedItem() + "'" ;
      }
      else
      {
         strCriteria = strCriteria ;
      }
      if (cboRoom.getSelectedIndex() > 0 )
      {  //if a room in the the room combobox is selected,
         //build piece of SQL statement that relfects the room as search criteria
         strCriteria = strCriteria + " AND " + " room = '" + cboRoom.getSelectedItem() + "'" ;
      }
      else
      {
         strCriteria = strCriteria ;
      }
      if (cboInst.getSelectedIndex() > 0 )
      {  //if an instructor in the the instructor combobox is selected,
         //build piece of SQL statement that relfects the instructor as search criteria
         strCriteria = strCriteria + " AND " + " instructor = '" + cboInst.getSelectedItem() + "'" ;
      }
      else
      {
         strCriteria = strCriteria ;
      }
      if (strCriteria.length() > 0 )
      {  //if the criteria string has been added to (eg at least one combobox item
         //has been selected), add there WHERE clause to the sql statment and trim the
         //criteria string. Then concatonate the two to make a final sql statement
         strSQL = strSQL + " WHERE " + strCriteria.substring(5) ;
      }       
      try
      {
         //open a connection to the FALL2013 derby database with the db object
         db.openConnection("FALL2013") ;
         //print the sql command we will hand the db object to query
         System.out.println(strSQL) ;
         //run a query to get a recordset from the database
         db.query(strSQL) ;
         while (db.moreRecords())
         {  //read every record in the recordset
            //put the results of the recordset in a string
            strTemp = db.getField("crn") + "   " + db.getField("subj") + "   " + db.getField("crs") ;
            System.out.println(strTemp) ;
            //add the string result to the search results combobox (INSTEAD of A LISTBOX)
            cboList.addItem(strTemp) ;  
         }
         //close the database so others can use it
         db.close() ;
      }
      catch (Exception e) 
      {
         //tell the user if there was an exception
         System.out.println(e.getMessage()) ;
      }
   }
}