import java.awt.*;
import java.awt.event.* ;
import javax.swing.*;
import java.util.StringTokenizer ;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList ;

/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/16/2013
@assignment.number Final Project - A190-15
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/ 
public class WeatherForm extends JFrame
{
   // Class level variables
   JPanel panel ;                	//a panel that goes ont the window
   JComboBox lstWeather ; 	         //the combobox that will display the weather report on the form
   JButton btnClearDB ;             //button from spec
   JButton btnDownloadFB ;          //button from spec
   JButton btnUpdateDBFB;           //button from spec      
   JButton btnUpdateDBNames ;       //button from spec
   JButton btnLoadCombo ;           //button from spec
   JComboBox cboLocations ; 	      //the combobox that will hold the locatoins of the airports
   JLabel picWeather ;              //the label that will hold the weather pic
   JLabel lblDateTime ;             //the label for date and time
   JLabel lblDate ;	               //the label that will be changed as time and date change
   JLabel lblLocation ;             //the location label
   JLabel lblStatus ;					//create a status label to let the user know how the program is doing on the form
   final int WINDOW_WIDTH = 500 ;   //the width of the window
   final int WINDOW_HEIGHT = 550 ;  //the height of the window
   
   /**
   This function is the constructor. It creates the form and places all labels and buttons
	on a panel and adds it to the frame. Then it sets everything to visible. 
   
   @param none
   @return none
   */ 
   public WeatherForm()
   {  //set the window title
      setTitle("A19015");
      	//set the window size
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT)  ;
      	//let the X button close the program
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      	// section 20 - set up the panel
      	// Set the JPanel Parameters
      panel = new JPanel(null) ; //null parameter allows x y axis placement of components
      panel.setPreferredSize(new Dimension(490,540)) ; //make the panel a little smaller than the frame so that it fits
      panel.setBackground(Color.lightGray) ;//collor the panel
      getContentPane().add(panel);          //add the panel to the frame
      
      //make the labels for the form. Initialize it to , so that the us
      lblStatus = setLabel(25, 450, "ready", 350, 20) ;
      lblLocation = setLabel(25, 25, "Location", 350, 20);
      lblDateTime = setLabel(250, 25, "Date/Time", 350, 20) ;
      lblDate = setLabel(375, 25, "lblDate", 350, 20) ;
      picWeather = setLabel(300, 300, "weatherPic", 200, 200) ;
      
      //make the weather report combobox that will be displayed on the form		
      String[] weather = { "location", "observation" } ;
      lstWeather = new JComboBox(weather);
      lstWeather.setSize(400,20);
      lstWeather.setLocation(25, 100) ;
      panel.add(lstWeather) ;
   	//make the various buttons on the form and give them each an action listener class	
      btnClearDB = setButton("Clear the Database", 25, 300 ) ;
      btnClearDB.addActionListener(new btnClearDBListener()) ;
      btnDownloadFB = setButton("Download Winds Aloft/Save to Disk", 25, 325) ;
      btnDownloadFB.addActionListener(new btnDownloadFBListener()) ;
      btnUpdateDBFB = setButton("Update DB with Winds Aloft Data", 25, 350) ;
      btnUpdateDBFB.addActionListener(new btnUpdateDBFBListener()) ; 
      btnUpdateDBNames = setButton("Update DB Station Names", 25, 375) ;
      btnUpdateDBNames.addActionListener(new btnUpdateDBNamesListener()) ;
      btnLoadCombo = setButton("Clear and Load ComboBox from DB", 25, 400 ) ;
      btnLoadCombo.addActionListener(new btnLoadComboListener()) ;	
      //make the locatoins combobox and add an action listener
      cboLocations = setCombobox("cboLocations", 25, 50) ;
      cboLocations.addActionListener(new cboLocationsListener()) ;   //link the combobox to an actionlistenter
      try
      {  //load the NWS image as a label on the form
         ImageIcon weatherImage = new ImageIcon("nws image.png") ;
         picWeather.setText(null) ;
         picWeather.setIcon(weatherImage) ;
      	//picWeather.setText(null) ;
      }
      catch (Exception e)
      {
         System.out.println(e.getMessage()) ;
         System.out.println("Couldn't find nws image") ;
      }
      	//set the form visible at the end of the constructor to make sure that components have first been added
      setVisible(true);
   		
   		//set the status on the form to creating table
      lblStatus.setText("Creating Table"); 
         	//create a string to hold SQL commands
      String strSQL ;
         	//instantiate a database oject
      DBUpdt db = new DBUpdt() ;
         	//connect to the derby database using the openConnection method
      db.openConnection("Weather") ;
         	//Setup the SQL statement for the stations table in the WEather database
      strSQL = "CREATE TABLE stations (stationID varchar(3), stationName varchar(200), ";
      strSQL = strSQL + "city varchar(20), state varchar(4), latitude varchar(40), ";
      strSQL = strSQL + "longitude varchar(40), windsaloft varchar(200), ID varchar(20), ";		
      strSQL = strSQL + "temperature varchar(20), humidity varchar(20), windspeed varchar(20), ";
      strSQL = strSQL + "winddirection varchar(20), elevation varchar(20), pressure varchar(20), ";
      strSQL = strSQL + "dewpoint varchar(20) ) ";					
   			//display the strSQL command for debugging       
      System.out.println(strSQL) ;
         	//use the new updateQuery function in the DBUpdt class to make changes to the tables
      db.updateQuery(strSQL) ;
         	//close the database so others can use it
      db.close() ;
   			//set the staus on the form to table created
      status("Table Created"); 
   }
  
  /**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/16/2013
@assignment.number Final Project - A190-15
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/ 
       
   private class btnClearDBListener implements ActionListener
   {
      /**
      This function is the required function for an action listener. When the clear database button is
      pressed, the function clears the Weather database using a DBUpdt object.
      
      @param event an ActionPerformed type that happenes everytime the actionlistener is notified
      */ 
      public void actionPerformed(ActionEvent event)
      {
         status("Clearing the Database");
         cboLocations.removeAllItems() ;
         DBUpdt db = new DBUpdt();                    //create a new DBUpdt object
         db.openConnection("Weather") ;              	//open the connectio with the Derby database
         db.deleteAll("stations");                    	//wipe all records from the grades table
         db.close() ;
         status("Database Has Been Cleared") ;
      }
   }
 /**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/16/2013
@assignment.number Final Project - A190-15
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/   
   
   private class btnDownloadFBListener implements ActionListener
   {
      /**
      This function is the required function for an action listener. When the download weather button is
      presse, the function goes online using an INET object to downoad raw strings of data from the 
      specified URL, and then saves the data to file.
      
      @param event an ActionPerformed type that happenes everytime the actionlistener is notified
      */ 
      public void actionPerformed(ActionEvent event)
      {
         String rawPageText ;
         String windsAloftText ;
         String lblDateText ;
         status("Downoading Winds Aloft Data") ;
         Inet net = new Inet() ;
         rawPageText = net.getURLRaw("http://aviationweather.gov/products/nws/winds/?area=all&fint=06&lvl=lo") ;
         windsAloftText = net.getPREData(rawPageText);
         lblDateText = windsAloftText.substring(windsAloftText.indexOf("VALID"),windsAloftText.indexOf("FOR USE")) ;
         lblDate.setText(lblDateText) ;
         net.saveToFile("FBIN.txt",windsAloftText) ;
         status("Winds Aloft Data saved to file") ;
      }      
   }
/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/16/2013
@assignment.number Final Project - A190-15
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/ 
   private class btnUpdateDBFBListener implements ActionListener
   {
      /**
      This function is the required function for an action listener. When the update database
      button is pressed, the saved file is read and the weather is put into the windsaloft field in
      the stations table of the weather database using DBUpdt objects.
      
      @param event an ActionPerformed type that happenes everytime the actionlistener is notified
      */ 
      public void actionPerformed(ActionEvent event)      
      {
         try
         {
            DBUpdt db = new DBUpdt() ;
            db.openConnection("Weather") ;
            String strInputFile = "FBIN.txt" ;   		      //create a string to hold the grade text file's name
            File myFile = new File(strInputFile);        //create a new file object  from the grades text
            Scanner inputFile = new Scanner(myFile);     //create a scanner object to read the created file
            String strRecord= "" ;                            //create a temportary string to hold lines of text
            //String[] aryFields = new String[3];				//create an array hold the week, quiz, and asgn number strings
            if (myFile.exists())                         //if the file exists, begin reading it
            { 
               for (int i = 0 ; i<8 ; i++)
               {
                  strRecord = inputFile.nextLine();         //read the first 8 line of the fbin.txt file
               }
               while (inputFile.hasNext() && strRecord.substring(0,6).compareTo("</pre>")!=0 )               //read the rest of the file until it's finished
               {
                  strRecord = inputFile.nextLine();      //read one line of the file at a time
                  System.out.println(strRecord);         //display the line just read to the user
                  	
                  //add the weather to the windsaloft field in the db and display to the user  
                  db.addRecord("stations", "stationID", strRecord.substring(0,3));
                  db.setField("stations", "stationID", strRecord.substring(0,3), "windsaloft", strRecord);
               }
               	//close the file
               inputFile.close();
            }
            	//close the database so others can use it
            db.close();
         		//tell the user that the database was loaded
            status("Database Updated - Winds Aloft") ;
         }
         catch(Exception e)
         {
            status(e.getMessage()) ;
         }
      }
   }
/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/16/2013
@assignment.number Final Project - A190-15
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/ 
  
   private class btnUpdateDBNamesListener implements ActionListener
   {
      /**
      This function is the required function for an action listener. When the update database names
      button is pressed, the function goes online with the XML read object and extracts xml fields 
      from each station ID to aquire names of airports and put them in the database.
      
      @param event an ActionPerformed type that happenes everytime the actionlistener is notified
      */ 
      public void actionPerformed(ActionEvent event)
      {
         String latitude = "" ;
         String longitude = "" ;
         String stationID= "";
         String addressXML = "" ;
         String stationName ="" ;
         String strSQL = "SELECT * FROM stations" ; 
         DBUpdt db = new DBUpdt() ;
         db.openConnection("Weather") ;
         db.query(strSQL) ;
         while (db.moreRecords())
         {
            stationID = db.getField("stationID");
            addressXML = "http://w1.weather.gov/xml/current_obs/K"+stationID+".xml" ;
            try
            {
               //create an XMLRead object to read the NWS surface weather
               XMLRead myXMLReader = new XMLRead() ;
               myXMLReader.loadPage(addressXML);
               myXMLReader.setTable("current_observation");
               stationName = myXMLReader.getField("location") ;  
               //Get the latitude, longitude, surface temp, relative humidity, from the XML object
               latitude = myXMLReader.getField("latitude"); 
               //if latitude is negative, it is in the South
               if (latitude.substring(0,1).compareTo("-")==0)
               {
                  latitude=latitude.substring(1)+ " S" ;
               }
               //if latitude is positive, it is in the North
               else
               {
                  latitude=latitude + " N" ;
               }
               longitude = myXMLReader.getField("longitude");
            //if longitude is negative, it is in the West,
               if(longitude.substring(0,1).compareTo("-")==0)
               {
                  longitude=longitude.substring(1)+ " W" ;
               }
               //if longitude is positive, it is in the East,
               else
               {
                  longitude=longitude + " E" ;
               }
               DBUpdt dbUpdate = new DBUpdt();
               dbUpdate.openConnection("Weather") ;
               dbUpdate.setField("stations", "stationID", stationID, "stationName", (String)stationName) ;
               dbUpdate.setField("stations", "stationID", stationID, "latitude", latitude) ;
               dbUpdate.setField("stations", "stationID", stationID, "longitude", longitude) ;
               /*
               strSQL = "UPDATE stations SET stationName = '"+stationName+ "' WHERE stationID = '"+stationID+"'" ;
               System.out.println(strSQL) ; 
               dbUpdate.updateQuery(strSQL) ;
               strSQL = "UPDATE stations SET latitude = '"+latitude+ "' WHERE stationID = '"+stationID+"'" ; 
               System.out.println(strSQL) ;
               dbUpdate.updateQuery(strSQL) ;
               strSQL = "UPDATE stations SET longitude = '"+longitude+ "' WHERE stationID = '"+stationID+"'" ; 
               System.out.println(strSQL) ; 
               dbUpdate.updateQuery(strSQL) ;
               */
               dbUpdate.close() ;
            }
            catch(Exception e)
            {  //if the XML object cant find a station, then tell the database just that
               status(e.getMessage()) ;
               DBUpdt dbUpdate = new DBUpdt();
               dbUpdate.openConnection("Weather") ;
               strSQL = "UPDATE stations SET stationName = 'not found WHERE stationID' = '"+stationID+"'" ; 
               dbUpdate.updateQuery(strSQL) ;
               dbUpdate.close();
            }
         }
         db.close();
         status("DB Station Names Updated");
      }
   }
/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/16/2013
@assignment.number Final Project - A190-15
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/          
        
     
   private class btnLoadComboListener implements ActionListener
   {
      
      public void actionPerformed(ActionEvent event)
      {
         /**
      This function is the required function for an action listener. When the load combobox button is
      pressed, the funtion loads the locations combobox with the locations of the airports from the
      database stationName field and the stationID field.
      
      @param event an ActionPerformed type that happenes everytime the actionlistener is notified
      */ 
         
         String strSQL = "SELECT * FROM stations" ;
         cboLocations.removeAllItems() ;
         DBUpdt db = new DBUpdt() ;
         db.openConnection("Weather") ;
         db.query(strSQL) ;
         while (db.moreRecords())
         {
            cboLocations.addItem(db.getField("stationID") + " " + db.getField("stationName")) ;
         }
         db.close();
         status("Combo Box Updated") ;   
        
      }
   }	
/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 12/16/2013
@assignment.number Final Project - A190-15
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/    
   
   private class cboLocationsListener implements ActionListener
   {
      /**
      This function is the required function for an action listener. When a combobox location is selected,
      this function creates a weather report on the form and saves taht weather report to a file called
      OUTPUT.txt. 
      
      @param event an ActionPerformed type that happenes everytime the actionlistener is notified
      */ 
      public void actionPerformed(ActionEvent event)
      {
         System.out.println("selection made");
         int loopCounter = 0 ;
         lstWeather.removeAllItems() ;
         
         if(cboLocations.getItemCount() != 0)
         {
            String comboSelection = (String)cboLocations.getSelectedItem() ;
            String strSQL = "SELECT * FROM stations WHERE stationID = '"+ comboSelection.substring(0,3)+"'" ;
            try
            {	
            	//create a PrintWriter Object to write the database to a output.txt File
               PrintWriter outputFile = new PrintWriter("OUTPUT.txt") ;
            
            	//create a DBUpdt object to access the database
               DBUpdt db = new DBUpdt() ;   
            	//open a connection to the stations Weather database with the db object
               db.openConnection("Weather") ;
            	//print the sql command we will hand the db object to query
               System.out.println(strSQL) ;
            	 //run a query to get a recordset from the database
               db.query(strSQL) ;
            	// loop through all the records in the record set from the query
               while (db.moreRecords())
               {  
                  String stationID = db.getField("stationID");
                  //get the xml url from the station id in the database
                  String addressXML = "http://w1.weather.gov/xml/current_obs/K"+stationID+".xml" ;
                  XMLRead myXMLReader = new XMLRead() ;
                  myXMLReader.loadPage(addressXML);
                  myXMLReader.setTable("current_observation");
                  //get the fields from the xml page that need to be displayed in the weather report
                  String location = myXMLReader.getField("location") ;  
                  String observation_time = myXMLReader.getField("observation_time");
                  String latitude = myXMLReader.getField("latitude") ;
                  String longitude = myXMLReader.getField("longitude") ;
                  String weather = myXMLReader.getField("weather") ;
                  String temperature_string = myXMLReader.getField("temperature_string") ;
                  String relative_humidity = myXMLReader.getField("relative_humidity") ;
                  String pressure_string = myXMLReader.getField("pressure_string") ;
                  String dewpoint_string = myXMLReader.getField("dewpoint_string") ;
                  String visibility = myXMLReader.getField("visibility") ;
                  
                  //print the weather fields to the output.txt file and the form combobox
                  outputFile.println(lblDate.getText()) ;
                  lstWeather.addItem(lblDate.getText()) ;
                  outputFile.println("location: "+db.getField("stationName"));
                   System.out.println("location: "+(String)db.getField("stationName"));
                  lstWeather.addItem("location: "+db.getField("stationName"));
                  outputFile.println("observation_time: " + observation_time);
                  lstWeather.addItem("observation_time: " + observation_time);
                  outputFile.println("latitude: " + latitude);
                  lstWeather.addItem("latitude: " + latitude);
                  outputFile.println("longitude: " + longitude);
                  lstWeather.addItem("longitude: " + longitude);
                  outputFile.println("weather: " + weather);
                  lstWeather.addItem("weather: " + weather);
                  outputFile.println("temperature_string: " + temperature_string);
                  lstWeather.addItem("temperature_string: " + temperature_string);
                  outputFile.println("relative_humidity: " + relative_humidity);
                  lstWeather.addItem("relative_humidity: " + relative_humidity);
                  outputFile.println("pressure_string: " + pressure_string);
                  lstWeather.addItem("pressure_string: " + pressure_string);
                  outputFile.println("dewpoint_string: " + dewpoint_string);
                  lstWeather.addItem("dewpoint_string: " + dewpoint_string);
                  outputFile.println("visibility: " + visibility);
                  lstWeather.addItem("visibility: " + visibility);
                  //print the windsaloft weather to the output.txt file and the form combobox
                  String altitudes[] = {"03","06","09","12","18","24","30","36","39"} ;
                  String windsaloft = db.getField("windsaloft")+" " ;
                  NWSFB fb = new NWSFB(windsaloft) ;
                  for (int i = 0; i<9 ; i++)
                  {
                     System.out.println("Altitude Weather at " + altitudes[i]+",000 feet:\tDir " + fb.getWindDirection(altitudes[i])+" Speed " + fb.getWindSpeed(altitudes[i])+ " Temperature " + fb.getWindTemp(altitudes[i]));
                     lstWeather.addItem("Altitude Weather at " + altitudes[i]+",000 feet:\tDir " + fb.getWindDirection(altitudes[i])+" Speed " + fb.getWindSpeed(altitudes[i])+ " Temperature " + fb.getWindTemp(altitudes[i]));
                     outputFile.println("Altitude Weather at " + altitudes[i]+",000 feet:\tDir " + fb.getWindDirection(altitudes[i])+" Speed " + fb.getWindSpeed(altitudes[i])+ " Temperature " + fb.getWindTemp(altitudes[i]));
                  }
                  try
                  {  //set the weather image to the XML image for each selected station on the form
                     ImageIcon weatherImage = new ImageIcon(myXMLReader.getField("icon_url_base")) ;
                     picWeather.setText(null) ;
                     picWeather.setIcon(weatherImage) ;
                  }
                  catch (Exception e)
                  {
                     System.out.println(e.getMessage()) ;
                     System.out.println("Couldn't find nws image") ;
                  }
               }
              
               outputFile.close(); 
            	 //close the database so others can use it
               db.close() ;
            }
            catch (Exception e) 
            {
            //tell the user if there was an exception
               System.out.println(e.getMessage()) ;
               lblStatus.setText("Failed to Create Weather Report");
            }
            status("List Box Updated") ;
         }
         else
         {
            System.out.println("nothing in combobox selected") ;
         }
      }
   }

   	/**
   	This function is used by the constructor to create and position all the various buttons
         
   	@param fmLeft an integer representing the number of pixels from the left of the frame
   	@param fmTop an integer representing the number of pixels from the top of the frame
   	@param title a string object representing the title that will be place on the button
   	@return btn a JButton object to be placed on the form
   	*/ 
   		
   private JButton setButton(String title, int fmLeft, int fmTop)
   {
      JButton btn = new JButton(title) ;
      btn.setSize(250,25);
      btn.setLocation(fmLeft,fmTop) ;
      panel.add(btn) ;
      return btn ;
   }
   	/**
   	This function is used to create a combobox on the form.
   
   	@param fmLeft an integer representing the number of pixels from the left of the frame
   	@param fmTop an integer representing the number of pixels from the top of the frame
   	@param firstItem a string object representing the contents that will be placed in the label
   	@return cbo a JComboBox that will be placed at the specified location
   	*/ 
   private JComboBox setCombobox(String firstItem, int fmLeft, int fmTop)
   {
   	//create the search result combobox IN PLACE OF A LISTBOX or TEXTAREA
      String listArray[] = {firstItem} ;                 //at first make the box empty
      JComboBox cbo = new JComboBox(listArray) ;        				//create the combobox
      cbo.setSize(400, 20) ;                  			//width, height
      cbo.setLocation(fmLeft, fmTop);               		//from left, from top
      cbo.setForeground(Color.blue) ;         				//set the font color
      cbo.setFont(new Font("Courier New", Font.BOLD, 15)) ;
      panel.add(cbo) ; 
      return cbo ;                       				//add the combobox to the pannel
   }
		/**
   	This function is used by the constructor to create and position all the various small
   	labels on the frame
   
   	@param fmLeft an integer representing the number of pixels from the left of the frame
   	@param fmTop an integer representing the number of pixels from the top of the frame
   	@param strVar a string object representing the contents that will be placed in the label
   	@return label a JLabel object to label the components of the frame
   	*/ 
   public JLabel setLabel(int fmLeft, int fmTop, String strVar, int length, int height)
   {  
      JLabel label = new JLabel(strVar) ;                   //create the label
      label.setFont(new Font("Helvetica", Font.BOLD,12)) ;  //set the font
      label.setSize(length,height);                                // Length, Height
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
   	This function is used to update the status label on the form.
   
   	@param strMessage a string consisting of the the message that will
      be dispayed by the status label on the form
   	@return none
   	*/ 	
   private void status(String strMessage)
   {
      lblStatus.setText(strMessage) ;
   }
   

		
}
