import java.awt.*;
import java.awt.event.* ;
import javax.swing.*;
import java.util.StringTokenizer ;

/**
@author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
@version 1.01 11/10/2013
@assignment.number Dowloading XML Files - A190-11
@prgm.usage Called from the operating system
@see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
@see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
*/ 
public class WeatherForm11 extends JFrame
{
// =============================
// Class Level Variables
//============================== 
                                    
   JPanel panel;                    //a Jpanel used to place bottons and data on
   private String strAirport ;      //a string used for storing an aiport ID
   private String altitudeWeather;  //a string used for storing weather numbers at a certain altitude
   private String strAltitude ;     //a string used for storing the altitude
   private String windDir;          //a string used for storing the wind direction
   private String windSpeed;        //a string used for storing the wind speed
   private String windTemp;         //a string used for storing the wind temperatur
   private String latitude;         //a string used to store the latitude
   private String longitude ;       //a string used to store the longitude
   private String surfaceTemp ;     //a string used to store the surface Temperature
   private String relativeHumidity ;//a string used to store the relative humidity
   private JComboBox cboApt ;       //a JComboBox object used to hold the 3 letter station ID's of airports
   private JComboBox cboAlt ;       //a JComboBox object used to hold the altitudes of certain airport weather strings
   private AirportDB db ;           //an AirportDB object to store airport weather into arrays
   private NWSFB fb ;               //an NWSFB object to get station weather data from a long string


//panel is inside frame

   public WeatherForm11() 
   {
   // Set the JFrame Parameters
      setTitle("A19011");
      setSize(500, 700) ;
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   // Set the JPanel Parameters
   
      panel = new JPanel(null) ; //null gets x y axis placement
      panel.setPreferredSize(new Dimension(490,690)) ;
      panel.setBackground(Color.lightGray) ;
      getContentPane().add(panel);      
   
      JLabel lblTitle;         // JLabel object used to display the large title on the window
   	//The large label title is special and needs its own formating
      lblTitle = new JLabel("Black Mountain Aviation Center");
   	//Set the font to 24 points (24 points = 1 inch)
      lblTitle.setFont(new Font("Helvetica", Font.BOLD, 24)) ;
   	//Set the label box to 410 pixels wide and 20 pixels high)
      lblTitle.setSize(410,25) ;
   	//Set the label position to 15 pixels from the LEFT and 25 from the TOP     
      lblTitle.setLocation(45,25) ;
   	//Set the label color to blue
      lblTitle.setForeground(Color.blue) ;
   	//Add the label to the panel
      panel.add(lblTitle) ;
   	
      //set the lables for the form
      setLabel(25, 100, "Select Airport") ;
      setLabel(25, 200, "Select Altitude") ;
      setLabel(200, 100, "Wind Dir") ;
      setLabel(200, 150, "Wind Speed") ;	
      setLabel(200, 200, "Wind Temp") ;
      setLabel(200, 250, "Latitude") ;
      setLabel(200, 300 ,"Longitude") ;
      setLabel(200, 350 ,"Surface Temp") ;
      setLabel(200, 400 ,"Relative Humidity") ;
   	
      //set text fields with initial sting literals
      //these literals will be changed when the "updateTextBoxes()"
      //function is called, for now just place them on the Form.
      setTextField(325, 100, "");
      setTextField(325, 150, "");
      setTextField(325, 200, "");
      setTextField(325, 250, "");
      setTextField(325, 300, "");
      setTextField(325, 350, "");
      setTextField(325, 400, "");
   
      //load the altitudes and airports on the form
      loadAltitudes() ;
      loadAirports() ;
   	//set the form visible at last
      setVisible(true);
   }
    //Section 783 , Combo boxes and actoin listeners 

   /**
   @author Ramon Ortega (Email: <a href="ramon.justis.ortega@gmail.com"> ramon.justis.ortega@gmail.com</a>)
   @version 1.00 11/10/2013
   @assignment.number Dowloading XML Files - A190-11
   @prgm.usage Called from the operating system
   @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th ed."
   @see "<a href='http://java.sun.com/javase/6/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>"
   */ 
   private class MyComboBoxListenerClass implements ActionListener
   {
      /**
      This function is the required function for an action listener. It is used
      to call the updateTextBoxes function whenever a ComboBox Item is selected on 
      the frame by the user
      
      @param event an ActionPerformed type that happenes everytime the actionlistener is notified
      */ 
      public void actionPerformed(ActionEvent event)
      {  
         String strVar ;
         int intAirport ;
         String strAltitude ;
                  
         intAirport = cboApt.getSelectedIndex();            //Gaddis,777
         
         strAltitude = (String)cboAlt.getSelectedItem() ;   //set the strAltitude variable to whatever
                                                            //is clicked in the altitude ComboBox
         //only when both an aiport and altitude are seleced, update the text boxes                                                   
         if ((cboApt.getSelectedIndex() != 0) & (cboAlt.getSelectedIndex() != 0 ))                                                 
         updateTextBoxes(intAirport, strAltitude);          //call the updatTextBoxes function to deal
                                                            //with the event                                   
      }
   }    
	
	/**
   This function loads the 9 altitudes into the altidude combobox on the form.
      
   */ 
   public void loadAltitudes()
   {
      String altArray[] =    {"03", "06", "09",   //an array of strings representing the 9 altitudes
                             "12", "18", "24", 
                             "30", "34", "39"} ;
      cboAlt = setComboBox(25, 250, altArray);
      cboAlt.addActionListener(new MyComboBoxListenerClass()) ;   //link the combobox to an actionlistenter
   }
   /**
      This function is loads the airports from the winds aloft webpage and puts them in the
      airport combobox on the form
      
      */ 
   public void loadAirports()
   {
      db = new AirportDB("weather.txt");        //hand the input file to be stored in a database
      String wea[] = new String[db.size()];    
      for (int i = 0 ; i<db.size() ; i++ )  //populate 21 airports starting with San Diego in an array
      {
         String strKey = db.getStationID(i) ;
         wea[i] = db.getStationWeather(strKey);
      }
      cboApt = setComboBox(25, 150, wea );
      cboApt.addActionListener(new MyComboBoxListenerClass());    //link the combobox to an actionlistenter
   }	
/**
This function is used by the constructor to create and position the airport and altitude 
ComboBoxes on the frame
  
@param fmLeft an integer representing the number of pixels from the left of the frame
@param fmTop an integer representing the number of pixels from the top of the frame
@param strArray an array of strings representing the contents of the ComboBox list
@return cbo a JComboBox object that is positioned on the panel
*/   
   public JComboBox setComboBox(int fmLeft, int fmTop, String[] strArray)
   {
      JComboBox cbo = new JComboBox() ;               //create a combobox
      cbo.setSize(170, 30) ;                          //width, height
      cbo.setLocation(fmLeft, fmTop);                 //from left, from top
      cbo.setForeground(Color.blue) ;                 //set the font color
   //populate the combobox with every index value in the strArray parameter
      if (strArray.length > 9)
      {
         cbo.addItem("Select Airport") ;
      }
      else
      {
         cbo.addItem("Select Altitude") ;
      }
      for (int i = 0; i < (strArray.length) ; i++)     
      {
         if (strArray.length > 9)                     //if the array contains airports
         {
            cbo.addItem(strArray[i].substring(0,3)) ; //add the aiports to the combobox
         }
         else                                         //if the array contains altitudes
         {
            cbo.addItem(strArray[i] + "000" ) ;       //add the altitudes to the combobox
         }
      }
      panel.add(cbo) ;                                //add the combobox to the panel
      return cbo ;                                    //also return the combobox
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
      TextField.setSize(150,30);                      // Length, Height
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
	This function is called whenever an a combobox item is selected. It makes
   sure that two combobox items, airport ID and altitude, must be selected in
   order for the wind direction, speed, and temperature text fields to update with
   correct weather information
	  
   @param strIndex an integer representing the index of the ariport in the airport
                     ComboBox.
   @param strAlt a 2 character string representing the weather altitude in thousands of
                  feet.
   */ 
   public void updateTextBoxes(int strIndex, String strAlt)
   {  
   
      String strKey = db.getStationID(strIndex);//hold a parameter
      String strStationWeather = db.getStationWeather(strKey)+" " ;//get the station Weather
      String icaoCode = "KSAN" ; //default airport if no others are found
      
      try
      {
         fb = new NWSFB(strStationWeather);  //use this NWSB object to get information for alt and ID combinations
         altitudeWeather = fb.getAltitudeWeather(strAlt.substring(0,2)) ;
         //update the wind direction, speed, and temp
         windDir = fb.getWindDirection(strAlt.substring(0,2));
         windSpeed = fb.getWindSpeed(strAlt.substring(0,2)) ;
         windTemp = fb.getWindTemp(strAlt.substring(0,2)) ;
         //use an inet object to read the translator from iata to icao aiport IDs
         Inet newNet = new Inet() ;
         String translator = newNet.getFromFile("iata to icao.txt");
         //Tokenize the translator
         StringTokenizer strTokenizer = new StringTokenizer(translator, "; \n \r");
         //System.out.println(translator) ;
         //read through the translator file. 
         while (strTokenizer.hasMoreTokens())
         {  //whenever one token in the file is the same as the 3 letter strKey of an airport
            //, the icao code will be the next token in the file
            if (strStationWeather.compareTo(strTokenizer.nextToken())==0)
            {
               icaoCode = strTokenizer.nextToken();
               System.out.println(icaoCode) ;
            }
         }
         //create an XMLRead object to read the NWS surface weather
         XMLRead myXMLReader = new XMLRead() ;
         //hand the XML object the icaoCode to put in it's URL
         String stationSurfaceWeather = "http://w1.weather.gov/xml/current_obs/" +icaoCode +".xml" ;
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
         
         //update the text boxes.
         setTextField(325, 100, windDir);
         setTextField(325, 150, windSpeed) ;
         setTextField(325, 200, windTemp) ;
         setTextField(325, 250, latitude);
         setTextField(325, 300, longitude);
         setTextField(325, 350, surfaceTemp);
         setTextField(325, 400, relativeHumidity);
      }
      catch (Exception e)
      {
         System.out.println(e.getMessage()) ;
      }
   } 
}