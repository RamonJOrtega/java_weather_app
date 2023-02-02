import java.awt.*;
import java.awt.event.* ;
import javax.swing.*;
import java.util.StringTokenizer ;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList ;
/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 11/18/2013
@assignment.number GUI w/Airport Status - A190-12
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/ 
public class WeatherForm12 extends JFrame
{
// =============================
// Class Level Variables
//============================== 
                                 
   private JPanel panel;            //a Jpanel used to place bottons and data on
   private JComboBox cboApt ;       //a JComboBox object used to hold the 3 letter station ID's of airports
   private JComboBox cboAlt ;       //a JComboBox object used to hold the altitudes of certain airport weather strings
   private JButton btnLoadStations ;//a JButton object used to call the btnLoadStations() function
   private JButton btnLoadWeather ; //a JButton object used to call the btnLoadWeather() function
   private JLabel txtAptStatus;     //a JLabel object that will serve as the header to the airport status

//panel is inside frame


   /**
   This function is the constructor. It creates the form and places all labels, buttons, comboboxes, and
   text fields on a panel and adds it to the frame. Then it sets everything to visible. The constructor
   also calls loadAirports() and loadAltitudes to pobulate the comboboxes
   
   @param none
   @return none
   */ 
   public WeatherForm12() 
   {
   // Set the JFrame Parameters
      setTitle("A19012 Fall 2013");
      setSize(500, 700) ;
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   // Set the JPanel Parameters
      panel = new JPanel(null) ; //null gets x y axis placement
      panel.setPreferredSize(new Dimension(490,690)) ;
      panel.setBackground(Color.lightGray) ;
      getContentPane().add(panel);      
   // JLabel object used to display the large title on the window
      JLabel lblTitle;         
   //The large label title is special and needs its own formating
      lblTitle = new JLabel("Black Mountain Aviation Center");
   //Set the font to 24 points (24 points = 1 inch)
      lblTitle.setFont(new Font("Helvetica", Font.BOLD, 24)) ;
   //Set the label box to 410 pixels wide and 25 pixels high)
      lblTitle.setSize(410,25) ;
   //Set the label position to 45 pixels from the LEFT and 25 from the TOP     
      lblTitle.setLocation(45,25) ;
   //Set the label color to blue
      lblTitle.setForeground(Color.blue) ;
   //Add the label to the panel
      panel.add(lblTitle) ;
   
   //set the lables for the form by colling the setLabel() function
      setLabel(25, 100, "Select Airport") ;
      setLabel(25, 150, "Select Altitude") ;
      setLabel(25, 200, "Wind Dir") ;
      setLabel(25, 225, "Wind Speed") ;	
      setLabel(25, 250, "Wind Temp") ;
      setLabel(25, 275, "StationID") ;
      setLabel(225, 200, "Latitude") ;
      setLabel(225, 225 ,"Longitude") ;
      setLabel(225, 250 ,"Surface Temp") ;
      setLabel(225, 275 ,"Relative Humidity") ;
   //set text fields with initial sting literals
   //these literals will be changed when the "updateTextBoxes()"
   //function is called, for now just place them on the Form.
      setTextField(110, 200, "");
      setTextField(110, 225, "");
      setTextField(110, 250, "");
      setTextField(110, 275, "");
      setTextField(325, 200, "");
      setTextField(325, 225, "");
      setTextField(325, 250, "");
      setTextField(325, 275, "");
   //create the btnLoadStations button needed to load airports
      btnLoadStations = new JButton("Load Stations") ;
      btnLoadStations.setSize(150, 30) ; // width, height
      btnLoadStations.setLocation(25,300) ; //from left, from top
      panel.add(btnLoadStations); 
      btnLoadStations.addActionListener(new btnLoadStationsListener()) ;
    //create the btnLoadWeather button needed to load the weather from file
      btnLoadWeather = new JButton("Load Weather") ;
      btnLoadWeather.setSize(150, 30) ; // width, height
      btnLoadWeather.setLocation(200,300) ; //from left, from top
      panel.add(btnLoadWeather); 
      btnLoadWeather.addActionListener(new btnLoadWeatherListener()) ;
     
   //set initial combo boxes on the panel and add a listener for them  
      String emptyArray[]={"Select Airport"} ;  //put only one item in the box for now
      cboApt = new JComboBox(emptyArray) ;      //create a combobox
      cboApt.setSize(250, 20) ;                 //width, height
      cboApt.setLocation(25, 125);              //from left, from top
      cboApt.setForeground(Color.blue) ;        //set the font color
      panel.add(cboApt) ;
      cboApt.addActionListener(new MyComboBoxListenerClass()) ;   //link the combobox to an actionlistenter
   //set initial combo boxes on the panel and add a listener for them 
      String blankArray[] = {"Select Altitude"} ;//put only one item in the box for now
      cboAlt = new JComboBox(blankArray) ;       //create a combobox
      cboAlt.setSize(250, 20) ;                  //width, height
      cboAlt.setLocation(25, 175);               //from left, from top
      cboAlt.setForeground(Color.blue) ;         //set the font color
      panel.add(cboAlt) ;
      cboAlt.addActionListener(new MyComboBoxListenerClass()) ;   //link the combobox to an actionlistenter
   //Set status label on panel using the setLabel() function
      txtAptStatus = setLabel(25, 350,"Airport Status") ;
      setTextFieldlarge(25, 375, "");
   //load the altitudes and airports on the form
      loadAltitudes() ;
      loadAirports() ;
   //set the form visible at last
      setVisible(true);
   }
 //Section 783 , Combo boxes and actoin listeners 

/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 11/18/2013
@assignment.number GUI w/Airport Status - A190-12
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/  
   private class MyComboBoxListenerClass implements ActionListener
   {
   /**
   This function is the required function for an action listener. It is used
   to call the updateTextBoxes function whenever a both Comboboxes on the form are
   selected to be an index other than the 0 index
   
   @param event an ActionPerformed type that happenes everytime the actionlistener is notified
   */ 
      public void actionPerformed(ActionEvent event)
      {  
         String strVar ;         //make a temporary string variable for this function's call
         String strAirport ;     //make a string to hold the stationName field for an airport
         String strAltitude ;    //make a string to hold the altitude of an airport
         //get whatever is selected from the comboboxes and store the items as strings
         strAirport = (String)cboApt.getSelectedItem();     //Gaddis,777
         strAltitude = (String)cboAlt.getSelectedItem() ;   //set the strAltitude variable to whatever
         //only when both an aiport and altitude are seleced, update the text boxes                                                   
         if ((cboApt.getSelectedIndex() != 0) & (cboAlt.getSelectedIndex() != 0 ))                                                 
            updateTextBoxes(strAirport, strAltitude);          //call the updatTextBoxes function to deal
                                                               //with the event                                   
      }
   }
/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 11/18/2013
@assignment.number GUI w/Airport Status - A190-12
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/  
   private class btnLoadStationsListener implements ActionListener
   {
      /**
      This function is the required function for an action listener. It is used
      to call the btnLoadStations() function whenever the Load Stations button is pressed
   
      @param event an ActionPerformed type that happenes everytime the actionlistener is notified
      */ 
      public void actionPerformed(ActionEvent event)
      {
         btnLoadStations();
      }
   }
   /**
   @author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
   @version 1.01 11/18/2013
   @assignment.number GUI w/Airport Status - A190-12
   @prgm.usage Called from the operating system
   @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
   @see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
   */  
   private class btnLoadWeatherListener implements ActionListener
   {
      /**
      This function is the required function for an action listener. It is used
      to call the btnLoadWeather() function whenever the Load Weather button is pressed
   
      @param event an ActionPerformed type that happenes everytime the actionlistener is notified
      */ 
      public void actionPerformed(ActionEvent event)
      {
         btnLoadWeather();
      }
   }
   /**
   This function is loads the cboApt ComboBox with the airport names from the stations table in the
   Weather.mdb database. It creates an Inet object to download the stations.txt file form the 
   faculty.sdmirimar.edu website and then creates a DBUpdt object to store the txt file in the database.
   Finally, the stationName field is extracted for every field in the database and inputed into the
   cboApt Combobox
   
   @param none
   */      
   public void btnLoadStations()
   {	
      //create a string to hold the URL of the airport file, stations.txt
      String URL = "http://faculty.sdmiramar.edu/jcouture/2013fl/cisc190/webct/manual/stations.txt";
      //create a string to hold the name of the file
      String myFile = "stations.txt" ;
      //create a temporary string to hold the contents of the page on the URL
      String strVar ;
      //create an Inet object to put the stations.txt file contents into a string
      Inet loadNet = new Inet() ;
      strVar = loadNet.getFromFile(myFile) ;
      //if the file is empty, go online and get page to create the file
      if (strVar.compareTo("")==0 )
      {
         //get the weather page
         strVar = loadNet.getURLRaw(URL) ;
         //create the file
         loadNet.saveToFile(myFile, strVar) ;
      }
      else //otherwise tell the user that there is already a stations.txt file
      {
         System.out.println("File Already On Disk") ;
      }	
    //create a file that that reads the stations.txt file
      File readFile = new File(myFile) ;
   	
      DBUpdt db = new DBUpdt() ;          //create a DBUpdt object called db
      db.openConnection("Weather") ;      // name of connection, not of file
      db.deleteAll("stations") ;          //wipe the rows of the stations table clean everytime
         
   //Check to see if the stations.txt file already exists, if it does continue
      if (readFile.exists())
      {
      //if the file exists do, the following reading of the txt file
         try
         {
            Scanner inputFile = new Scanner(readFile) ;     //create a scanner object to read the file
            StringBuilder stations = new StringBuilder(""); //create a stringBuilder object to store tokens in
            inputFile.nextLine();                           //get rid of headers in faculty stations.txt file
            while (inputFile.hasNext())                     //read every line of the World.txt file
            {  //Create a string object to hold lines of the stations.txt file
               String line = inputFile.nextLine();    
            //Tokenize the line from the stations.txt file      
               String[] tokens = line.split(",",3); // the tokens are separated by comas
               //add the stationID, stationName, and state from the statoins.txt file to the database
               //in their respective fields.	    
               db.addRecord("stations" , "stationID", tokens[0]) ;
               db.setField("stations", "stationID" , tokens[0], "stationName", loadNet.properCase(tokens[1])) ;
               db.setField("stations", "stationID" , tokens[0], "state", tokens[2]) ; 	
            }
         }
         catch (Exception e)//this catch will handle any exceptions that are generated from making objects
         {
            System.out.println(e.getMessage()) ;//displa the exception to the user
         }
      }	
      db.close() ;            //close the database so that others can use it
      loadAirports() ;        //call loadAirports to put the stationName field records into the ComboBox, cboApt. 
      System.out.println("uploaded stations") ;//tell the user the stations have been uploaded
   }
   /**
   This function is loads the winds aloft weather from the NWS website into a text tile using the Inet class.
   The text file then is read using the Inet class, and the station weather for each station is read from the file
   and put into the Weather.mdb database using the DBUpdt class.
   
   @param none
   */   
   public void btnLoadWeather()
   {
      //Create a icaocode String to be used as a default if no other icaoCodes are found
      String icaoCode = "KSAN" ; //default, but will be changed
      //Create a string to hold the name of the winds aloft data file
      String inputFile = "weather.txt" ;
      //Create a string to hold the winds aloft URL
      String windWeatherURL = "http://www.aviationweather.gov/products/nws/all" ;
      try
      {  //create a file to be read
         File myFile = new File(inputFile) ; //pg 246
         Scanner inFile = new Scanner(myFile);
         //create a DBUpdt object to connect to the database
         DBUpdt weatherLoad = new DBUpdt() ;          //create a DBUpdt object called weatherLoad
         weatherLoad.openConnection("Weather") ;      // name of connection, not of file  
         
         while( inFile.hasNext() )				//read the file line by line until the end
         {
            String line = inFile.nextLine() ;//store the next line of the file in a string variable
         	//only lines of greater than 65 characters are airport weather lines
            if ( (line.length() > 65) && (line.substring(2,3).compareTo(" ") != 0) ) //if the next line of the file is an airport weather line
            {
               //use the DBUpdt object to put each line of weather from the weather.txt file into the database for each airport
               weatherLoad.setField("stations", "stationID" , "K"+line.substring(0,3), "windsaloft", line) ;
            }											
         }
         weatherLoad.close() ;// close the database so others can use it
         System.out.println("uploaded weathers") ;	//tell the user that the weather was updated
      }
      catch (FileNotFoundException e)
      {
         //if the weather.txt file does not yet exist, go online and download the data to create the file
         //using the Inet class method: getPreData
         //Display the exception to the user
         System.out.println(e.getMessage()) ;
         //Tell the user that the weather.txt file doesn't yet exist
         System.out.println("The file " + inputFile + " does not yet exist" ) ;
         //Tell the user that the winds aloft data will be downloaded
         System.out.println("Downloading NCEP Winds Aloft Forecasts from the internet" );
         //Create an Inet object to store webpage data in a string
         Inet net = new Inet() ;
         String rawWebPage ;
         rawWebPage = net.getURLRaw(windWeatherURL) ;
         //create a string to put the preData in 
         String preData ;
         preData = net.getPREData(rawWebPage);
         //save the preData to a file called weather.txt
         net.saveToFile(inputFile, preData) ;
         //tell the user the save is complete
         System.out.println("Saved winds aloft to file");
      }	
      
   }	 

   /**
   This function loads the 9 altitudes into the altidude combobox on the form.
   
   */ 
   public void loadAltitudes()
   {
      String altArray[] = {"03", "06", "09",   //an array of strings representing the 9 altitudes
                          "12", "18", "24", 
                          "30", "34", "39"} ;
      for (int i =0 ; i < 9 ; i++ )
      {
         cboAlt.addItem(altArray[i]+"000" ) ;
      } 
   }
	/**
   This function is loads the airports from the winds aloft webpage and puts them in the
   airport combobox on the form
   
   */ 
   public void loadAirports()
   {
   	//first, clear all previous items rom the airport combobox
      cboApt.removeAllItems() ;
      DBUpdt dbLoad = new DBUpdt() ;          //create a DBUpdt object called dbLoad
      dbLoad.openConnection("Weather") ;      // name of connection, not of file
      String strSQL ;                         //make a sting variable to hold SQL commands
      strSQL = "SELECT StationName FROM stations" ; //use the SELECT statement in order to get Field values from dbLoad
      dbLoad.query(strSQL) ;                  //create a query to create a result set
      cboApt.addItem("Select Airport") ;      //add the zero index to the airport combobox
      while (dbLoad.moreRecords())            //loop through each row in the result set
      {  //add the station name of each airport in the data base to the airport combobox
         cboApt.addItem(dbLoad.getField("StationName")) ;
      }  
      dbLoad.close() ;            //close the database so that others can use it
   }	
  /**
   This function is used by the constructor to create and position the airport status
   on th eframe. It must be large enough to hold a long status

   @param fmLeft an integer representing the number of pixels from the left of the frame
   @param fmTop an integer representing the number of pixels from the top of the frame
   @param strVar a string object representing the contents that will be placed in the text field
   @return TextField a JTextField object that will contain weather information
   */ 
   public JTextField setTextFieldlarge(int fmLeft, int fmTop, String strVar)
   {
      JTextField TextField = new JTextField(strVar) ; //create the text Field
      TextField.setFont(new Font("Helvetica", Font.BOLD,12)) ; //set font
      TextField.setEditable(false) ;                  //not editable
      TextField.setText(strVar) ;                     //place text in the field
      TextField.setSize(450,20);                      // Length, Height
      TextField.setLocation(fmLeft,fmTop) ;           //from left, from top
      TextField.setForeground(Color.blue) ;           //Colors pg 77
      TextField.setBorder(BorderFactory.createLineBorder(Color.black,1)) ; //add border
      panel.add(TextField) ;                          //add the TextField to the panel   
      return TextField ;                              //also return a Text Field object
   }

   /**
   This function is used by the constructor to create and position the wind direction,
   speed, and temperature text fields on the frame

   @param fmLeft an integer representing the number of pixels from the left of the frame
   @param fmTop an integer representing the number of pixels from the top of the frame
   @param strVar a string object representing the contents that will be placed in the text field
   @return TextField a JTextField object that will contain weather information
   */ 
   public JTextField setTextField(int fmLeft, int fmTop, String strVar)
   {
      JTextField TextField = new JTextField(strVar) ; //create the text Field
      TextField.setFont(new Font("Helvetica", Font.BOLD,12)) ; //set font
      TextField.setEditable(false) ;                  //not editable
      TextField.setText(strVar) ;                     //place text in the field
      TextField.setSize(100,20);                      // Length, Height
      TextField.setLocation(fmLeft,fmTop) ;           //from left, from top
      TextField.setForeground(Color.blue) ;           //Colors pg 77
      TextField.setBorder(BorderFactory.createLineBorder(Color.black,1)) ; //add border
      panel.add(TextField) ;                          //add the TextField to the panel   
      return TextField ;                              //also return a Text Field object
   
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
   //Section 770, display the text Boxes and their data

   /**
   This function is called whenever a combobox item is selected. It makes
   sure that two combobox items, station name and altitude, must be selected in
   order for the text fields to update with
   correct weather information
  
   @param strApt a string representing the name of the airport
   @param strAlt a character string representing the weather altitude in thousands of
                  feet.
   */ 
   public void updateTextBoxes(String strApt, String strAlt)
   {  
      String aptStatus = "";   //a string used for storing the airport status
      String strKey = "" ;     //a string used for storing the icao code
      String strStationWeather = "";   //a string used for storing a line of weather
      String windDir;          //a string used for storing the wind direction
      String windSpeed;        //a string used for storing the wind speed
      String windTemp;         //a string used for storing the wind temperatur
      String latitude;         //a string used to store the latitude
      String longitude ;       //a string used to store the longitude
      String surfaceTemp ;     //a string used to store the surface Temperature
      String relativeHumidity ;//a string used to store the relative humidity
      //Make a connection to the database to get the stationID
      DBUpdt IDfind = new DBUpdt() ;          //create a DBUpdt object
      IDfind.openConnection("Weather") ;      // name of connection, not of file
      String IDcommand = "SELECT stationID FROM stations WHERE stationName = '" + strApt + "'" ;
      //execute the above SQL command
      IDfind.query(IDcommand) ;                  //create a query to create a result set 
      //read the resultset to get the station ID for the selected airport in the ComboBox
      while(IDfind.moreRecords())
      {
         strKey = IDfind.getField("stationID");
         System.out.println(strKey);
      } 
      IDfind.close() ;//close the database so that others can use it
      //make a connection to the database to get the weather for the airport selected in the combobox
      DBUpdt weatherFind =new DBUpdt() ;     //create a DBUpdt object
      weatherFind.openConnection("Weather") ;//name of connection, not of file
      String weatherCommand ="SELECT windsaloft FROM stations WHERE stationName = '" +strApt+ "'" ;
      //execute the above SQL statement
      weatherFind.query(weatherCommand) ;
      //read the resultset to get the station weather for the selected airport in the Combobox
      while(weatherFind.moreRecords())
      {  //weather is found in the windsaloft field
         strStationWeather = weatherFind.getField("windsaloft");
         System.out.println(strStationWeather);
      } 
      weatherFind.close() ;   //clse the database so that others can use it
      try
      {
         NWSFB fb = new NWSFB(strStationWeather);  //use this NWSB object to get information for alt and airport combinations
         //update the wind direction, speed, and temp
         windDir = fb.getWindDirection(strAlt.substring(0,2));
         windSpeed = fb.getWindSpeed(strAlt.substring(0,2)) ;
         windTemp = fb.getWindTemp(strAlt.substring(0,2)) ;      
      //create an XMLRead object to read the NWS surface weather
         XMLRead myXMLReader = new XMLRead() ;
      //hand the XML object the icaoCode to put in it's URL
         String stationSurfaceWeather = "http://w1.weather.gov/xml/current_obs/" +strKey +".xml" ;
      //Load the page
         myXMLReader.loadPage(stationSurfaceWeather);
         myXMLReader.setTable("current_observation");
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
         surfaceTemp = myXMLReader.getField("temp_c") + " C";
         relativeHumidity = myXMLReader.getField("relative_humidity")+" %";
         
         try{
         //create an XMLRead object to read airport status
            XMLRead secondXMLReader = new XMLRead() ;
         //hand the XML object the stationID to put in it's URL
            System.out.println(strKey.substring(1,4));
            String aptStatusURL = "http://services.faa.gov/airport/status/"+strKey.substring(1,4)+"?format=application/xml";
         //Load the page
            secondXMLReader.loadPage(aptStatusURL);
            secondXMLReader.setTable("Status");
            aptStatus = secondXMLReader.getField("Reason");
         }
         catch (Exception e) // in case there is no webpage on the internet, tell the user that no status is there.
         {
            System.out.println(e.getMessage()) ;
            aptStatus = "no status currently available" ;
         }
         //update all the text fields on the form
         setTextField(110, 200, windDir);
         setTextField(110, 225, windSpeed);
         setTextField(110, 250, windTemp);
         setTextField(110, 275, strKey);
         setTextField(325, 200, latitude);
         setTextField(325, 225, longitude);
         setTextField(325, 250, surfaceTemp);
         setTextField(325, 275, relativeHumidity);  
         setTextFieldlarge(25, 375, aptStatus);
      }
      catch (Exception e)
      {
         System.out.println(e.getMessage()) ;
         //if there is an exception to any objects created, tell the user
      }
   } 
}