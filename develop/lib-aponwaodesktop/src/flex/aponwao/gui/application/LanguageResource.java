package flex.aponwao.gui.application;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LanguageResource {
	private static Logger logger = Logger.getLogger(LanguageResource.class.getName());
	private static final String I18N_PATH = "i18n";
	private static ResourceBundle language;
        
        //OJO... Modificacion Felix
        //private static final String LANGUAGE_FILE_PATH = ResourceHelper.getRESOURCES_PATH() + File.separatorChar + I18N_PATH + File.separatorChar + "language";
	//**********************************************************************
        
	public static ResourceBundle getLanguage() {
            if (language == null) reloadLanguage();
            return language;
	}
	
        private static String getLanguageFilePath() {
            String idioma = PreferencesHelper.getPreferences().getString(PreferencesHelper.IDIOMA);
            if((idioma == null)||(idioma.equals(""))) {
                return ResourceHelper.getRESOURCES_PATH() + File.separatorChar + I18N_PATH + File.separatorChar + "language" + idioma + ".properties";
            } else {
                return ResourceHelper.getRESOURCES_PATH() + File.separatorChar + I18N_PATH + File.separatorChar + "language_" + idioma + ".properties";
            }
        }
        
	public static void reloadLanguage() {

		StringTokenizer tokenizerIdiomaValue = new StringTokenizer(PreferencesHelper.getPreferences().getString("idioma"), "_");
		
		String idioma = (String) tokenizerIdiomaValue.nextElement();
		String pais = (String) tokenizerIdiomaValue.nextElement();

		Locale currentLocale = new Locale(idioma, pais);
		
		Locale.setDefault(currentLocale);
		
                //OJO... Modificacion Felix
                File file = new File(getLanguageFilePath());
                URL resourceURL = null;
                try {
                    resourceURL = file.getParentFile().toURL();
                } catch (MalformedURLException e) {
                   e.printStackTrace();
                }  
                URLClassLoader urlLoader = new URLClassLoader(new java.net.URL[]{resourceURL});
                language = ResourceBundle.getBundle("language_es_ES", currentLocale, urlLoader);
                
                //language = ResourceBundle.getBundle(getLanguageFilePath(), currentLocale);
                //**************************************************************
	}

	public static SimpleDateFormat getShortFormater() {

		if (Locale.getDefault().getCountry().equals("ES") && Locale.getDefault().getLanguage().equals("es")) {
			return (new SimpleDateFormat("dd-MM-yyyy"));
			
		} else if (Locale.getDefault().getCountry().equals("ES") && Locale.getDefault().getLanguage().equals("eu")) {
			return (new SimpleDateFormat("yyyy-MM-dd"));
			
		} else {
			return (new SimpleDateFormat("MM-dd-yyyy"));
		}
	}
	
	public static SimpleDateFormat getFullFormater() {

		if (Locale.getDefault().getCountry().equals("ES") && Locale.getDefault().getLanguage().equals("es")) {
			return (new SimpleDateFormat("HH:mm:ss dd-MM-yyyy"));
			
		} else if (Locale.getDefault().getCountry().equals("ES") && Locale.getDefault().getLanguage().equals("eu")) {
			return (new SimpleDateFormat("HH:mm:ss yyyy-MM-dd"));
			
		} else {
			return (new SimpleDateFormat("HH:mm:ss MM-dd-yyyy"));
		}
	}
	
	public static SimpleDateFormat getTimeFormater() {

		if (Locale.getDefault().getCountry().equals("ES") && Locale.getDefault().getLanguage().equals("es")) {
			return (new SimpleDateFormat("HH:mm:ss"));
			
		} else if (Locale.getDefault().getCountry().equals("ES") && Locale.getDefault().getLanguage().equals("eu")) {
			return (new SimpleDateFormat("HH:mm:ss"));
			
		} else {
			return (new SimpleDateFormat("HH:mm:ss"));
		}
	}
	
}
