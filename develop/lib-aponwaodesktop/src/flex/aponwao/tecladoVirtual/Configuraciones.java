package flex.aponwao.tecladoVirtual;

import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

/**
 *
 * @author Suscerte
 */
public class Configuraciones {
    private Hashtable<String, String> myConf = new Hashtable<String, String>();
    private Hashtable<String, Tecla> myKeys = new Hashtable<String, Tecla>();
    
    public Configuraciones() {
        readConf();
    }
    
    private void readConf() {
        BufferedReader confKeyBoard = null;
        BufferedReader confWindow = null;
        try {
            ConfParams c = new ConfParams();
            confKeyBoard = new BufferedReader(
                     new InputStreamReader(
                        new FileInputStream(
//                            c.getClass().getResource("conf" + File.separator + "keyboard.conf").getPath()
                            PreferencesHelper.PREFERENCES_PATH + File.separatorChar + "keyboard.conf"
                        ),
                        "UTF-8"
                     )
                 );
            confWindow = new BufferedReader(
                     new InputStreamReader(
                        new FileInputStream(
                            PreferencesHelper.PREFERENCES_PATH + File.separatorChar + "window.conf"
//                            c.getClass().getResource("conf" + File.separator + "window.conf").getPath()
                        ),
                        "UTF-8"
                     )
                 );
            c=null;
            String confLineKeyBoard;
            while((confLineKeyBoard = confKeyBoard.readLine()) != null) {
            Tecla keyItem = new Tecla();
            if(confLineKeyBoard.length() > 0) {                                           
               if(!confLineKeyBoard.substring(0,1).contains("#")) {                      
                  if(!confLineKeyBoard.contains("=") || !confLineKeyBoard.contains("\",,\"")) {    
                     System.err.println("Incompatible config line: " + confLineKeyBoard);
                  } else {                                                         
                     int spacePos   = confLineKeyBoard.indexOf("=");
                     int seperator  = confLineKeyBoard.indexOf("\",,\"");
                     int firstGFPos = confLineKeyBoard.indexOf("\"");
                     int lastGFPos  = confLineKeyBoard.lastIndexOf("\"");
                     keyItem.setLowerCase(confLineKeyBoard.substring(firstGFPos + 1, seperator));
                     keyItem.setUpperCase(confLineKeyBoard.substring(seperator + 4, lastGFPos));
                     myKeys.put(confLineKeyBoard.substring(0,spacePos).trim(),keyItem);
                  }
               }
            }
            }

            String confLineWindow;
            while((confLineWindow = confWindow.readLine()) != null) {
            ConfParams confItem = new ConfParams();
            if(confLineWindow.length() > 0) {                                          
               if(!confLineWindow.substring(0,1).contains("#")) {                        
                  if(confLineWindow.contains("=")) {                                    
                     int spacePos   = confLineWindow.indexOf("=");
                     int firstGFPos = confLineWindow.indexOf("\"");
                     int lastGFPos  = confLineWindow.lastIndexOf("\"");
                     confItem.setName(confLineWindow.substring(0,spacePos).trim());
                     confItem.setParam(confLineWindow.substring(firstGFPos + 1, lastGFPos));
                     myConf.put(confItem.getName(),confItem.getParam());
                  } else {
                     System.err.println("Incompatible config line: " + confLineWindow);
                  }
               }
            }
            }
        } catch(IOException e) {
            System.err.println("Error reading config file:\n" + e);
        }
    }

    public Hashtable<String, String> getMyConf() {
        return myConf;
    }

    public Hashtable<String, Tecla> getMyKeys() {
        return myKeys;
    }
}