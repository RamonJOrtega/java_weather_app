import java.io.*;
import java.util.Scanner;


public class A19008
{
   public static void main (String[] args) throws IOException
   {
      String strPage;
      String strData;
      String NWSURL = "http://aviationweather.gov/products/nws/winds/?area=sanfrancisco&fint=06&lvl=lo";
      System.out.println("Starting TestRegEx");
      Inet net = new Inet();
      strPage = net.getURLRaw(NWSURL);
      getPREData( strPage ) ;
      //strData = net.getRegEx(strPage,"<PRE>(.)*</PRE>");
      System.out.println(strData);
   }
}
