package flex.aponwao.gui.sections.preferences.helpers;

import com.itextpdf.text.pdf.PdfSignatureAppearance;
import flex.aponwao.gui.application.ExportHelper;
import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.ResourceHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jface.preference.PreferenceStore;

public class PreferencesHelper {
	private static final Logger logger = Logger.getLogger(PreferencesHelper.class.getName());

	// PATHS
        //OJO... Modificacion felix
        public static final String PREFERENCES_PATH = System.getProperty("user.home") + File.separatorChar + "aponwao-preferencias";
        //**********************************************************************
        
	private static final String USER_PREFERENCES_FILE = PREFERENCES_PATH + File.separatorChar + "preferences-aplication.properties";
	public static String HARDWARE_PREFERENCES_FILE = PREFERENCES_PATH + File.separatorChar + "hardware-preferences.csv";
	public static String SOFTWARE_PREFERENCES_FILE = PREFERENCES_PATH + File.separatorChar + "software-preferences.csv";
	public static String TIMESTAMP_PREFERENCES_FILE = PREFERENCES_PATH + File.separatorChar + "timestamp-preferences.csv";
        public static String IMAGE_PREFERENCES_FILE = PREFERENCES_PATH + File.separatorChar + "image-preferences.csv";

	public static String TRUSTED_KEYSTORE_FILE = PREFERENCES_PATH + File.separatorChar + "trusted.jks";
	public static String CACHE_KEYSTORE_FILE = PREFERENCES_PATH + File.separatorChar + "cache.jks";


	// STATIC ATRIBUTES
	private static PreferenceStore preferences = null;
	private static Map<String, String> softwareCert = null;

	private static KeyStore trustedKeystore = null;
	private static KeyStore cacheKeystore = null;
        

	// PREFERENCES KEYS

	// General
	public static final String IDIOMA = "idioma";
	public static final String TOKEN_LOCALE = "_";
	public static final String OUTPUT_AUTO_ENABLE= "output.auto.enable";
	public static final String SAVE_EXTENSION = "save.extension";


	// Proxy
	public static final String PROXY_HOST = "proxy.http.host";
	public static final String PROXY_PORT = "proxy.http.port";
	public static final String PROXY_USER = "proxy.http.user";
	public static final String PROXY_PASS = "proxy.http.pass";
	public static final String PROXY_NON_PROXY = "proxy.http.non_proxy";
	public static final String PROXY_ENABLE = "proxy.http.enable";


	// Certifications
	public static final String CERT_TYPE = "preferencias.radioCertType.active";
	public static final String CERT_SOFTWARE_TYPE = "0";
	public static final String CERT_HARDWARE_TYPE = "1";

	public static final String HARDWARE_DISPOSITIVE = "hardware.dispositive";
	public static final String SOFTWARE_DISPOSITIVE = "software.dispositive";

        //Images
        public static final String IMAGE_DISPOSITIVE = "image.dispositive";

	// Appearance
	public static final String APPEARANCE_VISIBLE = "appearance.visible";
	public static final String APPEARANCE_REASON = "appearance.reason";
	public static final String APPEARANCE_LOCATION = "appearance.location";
	public static final String APPEARANCE_STAMP_ENABLE = "appearance.stamp.enable";
	public static final String APPEARANCE_STAMP_PATH = "appearance.stamp.path";
	public static final String APPEARANCE_STAMP_WIDTH = "appearance.stamp.width";
	public static final String APPEARANCE_STAMP_HEIGHT = "appearance.stamp.height";
	public static final String APPEARANCE_STAMP_X = "appearance.stamp.x";
	public static final String APPEARANCE_STAMP_Y = "appearance.stamp.y";

	// Validate
	public static final String VALIDATE_TS_ENABLE = "validate.ts.enable";
	public static final String VALIDATE_TS_TSA = "validate.ts.tsa";
	public static final String VALIDATE_OCSP_ENABLE = "validate.ocsp.enable";
        public static final String VALIDATE_OCSP_VERIFY_ON_ERROR = "validate.ocsp.verify.on.error";
	public static final String VALIDATE_CERTIFIED = "validate.certified";


	// FileDialogs path
	public static final String FILEDIALOG_PATH = "filedialog.path";
        
        //OJO... Modificacion Felix
        public static final String APPEARANCE_FONT_SIZE = "appearance.font.size";
        public static final String APPEARANCE_CODBARRA_X = "appearance.codbarra.x";
        public static final String APPEARANCE_CODBARRA_Y = "appearance.codbarra.y";
        public static final String APPEARANCE_CODBARRA_WIDTH = "appearance.codbarra.width";
	public static final String APPEARANCE_CODBARRA_HEIGHT = "appearance.codbarra.height";
        public static final String APPEARANCE_CODBARRA_ENABLE = "appearance.codbarra.enable";
        public static final String APPEARANCE_CODBARRA_PAGE = "appearance.codbarra.page=1";
        public static final String APPEARANCE_CODBARRA_PATH = "appearance.codbarra.path";
        public static final String DUPLICADO_CSV_ENABLE = "duplicado.csv.enable";
        public static final String APPEARANCE_CORRELATIVO_ENABLE = "appearance.correlativo.enable";
        public static final String APPEARANCE_CORRELATIVO_PAGE = "appearance.correlativo.page";
        public static final String APPEARANCE_CORRELATIVO_FONT_SIZE = "appearance.correlativo.font.size";
        public static final String APPEARANCE_CORRELATIVO_FONT_BOLD = "appearance.correlativo.font.bold";
        public static final String APPEARANCE_CORRELATIVO_FONT_ITALICS = "appearance.correlativo.font.italics";
        public static final String APPEARANCE_CORRELATIVO_POS_X = "appearance.correlativo.posX";
        public static final String APPEARANCE_CORRELATIVO_POS_Y = "appearance.correlativo.posY";
        public static final String APPEARANCE_COLETILLA_FACTURA = "appearance.coletilla.factura";
        public static final String APPEARANCE_COLETILLA_FACTURA_X = "appearance.coletilla.factura.x";
        public static final String APPEARANCE_COLETILLA_FACTURA_SUFIX_FILE = PREFERENCES_PATH + File.separatorChar + "coletilla.conf";
        
        public static boolean loaded = false;
        public static final String APPEARANCE_PAGE = "appearance.page";
        public static final String APPEARANCE_STAMP_STATIC_VISIBLE = "appearance.stamp.static.visible";
        public static final String APPEARANCE_STAMP_STATIC_DIRTEMP = "appearance.stamp.static.temp";
        public static final String PDF_VIEWER = "pdf.viewer";
//        public static final String OUTPUT_DIR_BACKUP = "output.dir.backup";
        public static final String APPEARANCE_TITLE = "appearance.title";
        public static final String OUTPUT_DIR_BACKUP_ENABLE = "output.dir.backup.enable";
        public static final String APPEARANCE_STAMP_ADOBE_VISIBLE = "appearance.stamp.adobe.visible";
        public static final String IDIOMA_INDEX = "idioma.index";
        public static final String VIRTUAL_KEYBOARD = "virtual.keyboard";
        public static final String EMAIL_SMTP_HOST = "email.smtp.host";
        public static final String EMAIL_SMTP_STARTTLS_ENABLE = "email.smtp.starttls.enable";
        public static final String EMAIL_SMTP_PORT = "email.smtp.port";
        public static final String EMAIL_SMTP_USER = "email.smtp.user";
        public static final String EMAIL_SMTP_AUTH = "email.smtp.auth";
        public static final String EMAIL_ADDRESSES_SEPARATOR = "email.addresses.separator";
        public static final String EMAIL_CLIENT = "email.client";
        public static final String EMAIL_CLIENT_APONWAO = "APONWAO";
        public static final String EMAIL_CLIENT_EXTERNAL = "EXTERNAL";
        public static final String EMAIL_USER_SUFIX_FILE = PREFERENCES_PATH + File.separatorChar + "mailSign.conf";
        public static final String EMAIL_SIGN = "email.sign";
        public static final String SIGN_HASH = "sign.hash";
        
        public static final String APLICATION_PATH_SOURCE_ENABLED = "aplication.path.source.enabled";
        public static final String APLICATION_PATH_SOURCE = "aplication.path.source";
        public static final String APLICATION_PATH_SOURCE_FOLDER = "FOLDER";
        public static final String APLICATION_PATH_SOURCE_DB = "DB";
        public static final String APLICATION_PATH_SOURCE_FOLDER_VALUE = "aplication.path.source.folder.value";
        public static final String APLICATION_PATH_FINAL_FOLDER_VALUE = "aplication.path.final.folder.value";
        public static final String APLICATION_PATH_BACKUP_FOLDER_VALUE = "aplication.path.backup.folder.value";
        
        public static final String APLICATION_PATH_SOURCE_DB_DRIVER="aplication.path.source.db.driver";
        public static final String APLICATION_PATH_SOURCE_DB_URL="aplication.path.source.db.url";
        public static final String APLICATION_PATH_SOURCE_DB_NAME="aplication.path.source.db.name";
        public static final String APLICATION_PATH_SOURCE_DB_FUNCTION="aplication.path.source.db.function";
        public static final String APLICATION_PATH_SOURCE_DB_USER="aplication.path.source.db.user";
        public static final String APLICATION_PATH_SOURCE_DB_PASSWORD="aplication.path.source.db.password";
        public static final String APLICATION_PATH_SOURCE_DB_ORIGEN_FIELD="aplication.path.source.db.source.field";
        public static final String APLICATION_PATH_SOURCE_DB_DESTINO_FIELD="aplication.path.source.db.destination.field";
        public static final String APLICATION_PATH_SOURCE_DB_USER_TYPE="aplication.path.source.db.user.type";
        
        public static final String APLICATION_VALIDATE_OVERWRITING="aplication.validate.overwriting";
        public static final String APLICATION_COPIAR_DUPLICADOS="aplication.validate.copiar.duplicados";

        public static final String VIEW_CONTENT_FOLDER="view.content.folder";
        //**********************************************************************
        
	public static PreferenceStore getPreferences() {
            
            //OJO... Modificacion Felix
            //if (preferences == null) {
            if (!loaded) {
            //**********************************************************************
			try {
				preferences = new PreferenceStore(USER_PREFERENCES_FILE);
				preferences.load();

				// TODO definir valores por defecto

				// General
				preferences.setDefault(IDIOMA, "esES");
				preferences.setDefault(OUTPUT_AUTO_ENABLE, "true");
				preferences.setDefault(APLICATION_PATH_FINAL_FOLDER_VALUE, ResourceHelper.getRESOURCES_PATH());
				preferences.setDefault(SAVE_EXTENSION, "-signed");

				// Proxy
				preferences.setDefault(PROXY_HOST, "");
				preferences.setDefault(PROXY_PORT, "");
				preferences.setDefault(PROXY_USER, "");
				preferences.setDefault(PROXY_PASS, "");
				preferences.setDefault(PROXY_NON_PROXY, "");
				preferences.setDefault(PROXY_ENABLE, "false");

				// Appearance
				preferences.setDefault(APPEARANCE_VISIBLE, "true");
				preferences.setDefault(APPEARANCE_REASON, "Aprobacion");
				preferences.setDefault(APPEARANCE_LOCATION, "Caracas");
				preferences.setDefault(APPEARANCE_STAMP_ENABLE, "true");
				preferences.setDefault(APPEARANCE_STAMP_PATH, ImagesResource.APLICATION_LOGO);
				preferences.setDefault(APPEARANCE_STAMP_X, "20");
				preferences.setDefault(APPEARANCE_STAMP_Y, "20");
				preferences.setDefault(APPEARANCE_STAMP_WIDTH, "50");
				preferences.setDefault(APPEARANCE_STAMP_HEIGHT, "50");

				// Validate
				preferences.setDefault(VALIDATE_TS_ENABLE, "true");
				preferences.setDefault(VALIDATE_TS_TSA, "izenpe");
				preferences.setDefault(VALIDATE_OCSP_ENABLE, "true");
                                preferences.setDefault(VALIDATE_OCSP_VERIFY_ON_ERROR, "false");
				preferences.setDefault(VALIDATE_CERTIFIED, "" + PdfSignatureAppearance.NOT_CERTIFIED);

				// Cert
				preferences.setDefault(CERT_TYPE, CERT_HARDWARE_TYPE);
				preferences.setDefault(HARDWARE_DISPOSITIVE, "generic"); // cambiar a izenpe en win!!!
                                
                                // Static Image
                                preferences.setDefault(APPEARANCE_STAMP_STATIC_VISIBLE,  "false");
                                preferences.setDefault(VIRTUAL_KEYBOARD,  "false");

                                // Modificacion Yessica
                                preferences.setDefault(VIEW_CONTENT_FOLDER,  "true");
                                //**********************************************
                                loaded = true;
			} catch (IOException e) {

				logger.log(Level.SEVERE, "", e);
			}
		}
		return preferences;
	}

	public static void savePreferences() {

		try {
			// Save the preferences
			preferences.save();

		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}

	}


	public static Map<String, String> getHardwarePreferences() {

		Map<String, String> map = new TreeMap<>();

		// leer el csv
		List<List<String>> array = ExportHelper.importCSV(HARDWARE_PREFERENCES_FILE);

		// generar el map
		for (int i = 1; i < array.size(); i++) {

			List<String> list = array.get(i);
			String name = list.get(0);
			String path = list.get(1);
			map.put(name, path);
		}

		return map;
	}


	private static Map<String, String> loadSoftwarePreferences() {

		Map<String, String> map = new TreeMap<>();

		// leer el csv
		List<List<String>> array = ExportHelper.importCSV(SOFTWARE_PREFERENCES_FILE);

		// generar el map
		for (int i = 1; i < array.size(); i++) {

			List<String> list = array.get(i);
			String name = list.get(0);
			String path = list.get(1);
			map.put(name, path);
		}

		return map;
	}


	public static Map<String, String> getSoftwarePreferences() {

		if (softwareCert == null) {
			softwareCert = loadSoftwarePreferences();
		}

		return softwareCert;
	}

	public static void saveSoftwarePreferences(Map<String, String> map) {

		List<List<String>> array = new ArrayList<>();

		// header
		List<String> list = new ArrayList<>();
		list.add(0, "name");
		list.add(1, "path");
		array.add(list);

		for (String name : map.keySet()) {

			String path = map.get(name);
			list = new ArrayList<>();
			list.add(0, name);
			list.add(1, path);
			array.add(list);
		}

		ExportHelper.exportCSV(SOFTWARE_PREFERENCES_FILE, array);

		softwareCert = map;

	}

        //OJO.. Modificacion Yessica
        public static void saveHardwarePreferences(Map<String, String> map) {

		List<List<String>> array = new ArrayList<>();

		// header
		List<String> list = new ArrayList<>();
		list.add(0, "name");
		list.add(1, "path");
		array.add(list);

		for (String name : map.keySet()) {

			String path = map.get(name);
			list = new ArrayList<>();
			list.add(0, name);
			list.add(1, path);
			array.add(list);
		}

		ExportHelper.exportCSV(HARDWARE_PREFERENCES_FILE, array);

	}

        public static void saveTimestampPreferences(Map<String, TimeStampPreferences> map) {

                List<List<String>> array = new ArrayList<>();

		// header
		List<String> list = new ArrayList<>();
		list.add(0, "name");
		list.add(1, "url");
                list.add(2, "user");
                list.add(3, "password");
		array.add(list);

		for (String name : map.keySet()) {

			list = new ArrayList<>();
                        String url = map.get(name).getUrl(); if(url==null) url="null";
                        String user = map.get(name).getUser(); if(user==null) user="null";
                        String password = map.get(name).getPassword(); if(password==null) password="null";
			list.add(0, name);
			list.add(1, url);
                        list.add(2, user);
                        list.add(3, password);
			array.add(list);
		}

		ExportHelper.exportCSV(TIMESTAMP_PREFERENCES_FILE, array);


	}

        public static void saveImagePreferences(Map<String, ImageSignPreferences> map) {

                List<List<String>> array = new ArrayList<>();

		List<String> list = new ArrayList<>();
		list.add(0, "name");
		list.add(1, "type");
                list.add(2, "path");
                list.add(3, "imageVisible");
                list.add(4, "posX");
                list.add(5, "posY");
                list.add(6, "height");
                list.add(7, "width");
                list.add(8, "page");
                list.add(9, "reason");
                list.add(10, "locate");
		array.add(list);

		for (String name : map.keySet()) {

			list = new ArrayList<>();
                        String type = map.get(name).getType();
                        String imageVisible = map.get(name).getImageVisible();
                        String path = map.get(name).getPath();
                        String posX = map.get(name).getPosX();
                        String posY = map.get(name).getPosY(); 
                        String height = map.get(name).getHeight(); 
                        String width = map.get(name).getWidth(); 
                        String page = map.get(name).getPage(); 
                        String reason = map.get(name).getReason();
                        String locate = map.get(name).getLocate();
			list.add(0, name);
			list.add(1, type);
                        list.add(2, imageVisible);
                        list.add(3, path);
                        list.add(4, posX);
                        list.add(5, posY);
                        list.add(6, height);
                        list.add(7, width);
                        list.add(8, page);
                        list.add(9, reason);
                        list.add(10, locate);
			array.add(list);
		}

		ExportHelper.exportCSV(IMAGE_PREFERENCES_FILE, array);
	}

        public static Map<String, ImageSignPreferences> getImagePreferences() {
            Map<String, ImageSignPreferences> map = new TreeMap<>();
            // leer el csv
            List<List<String>> array = ExportHelper.importCSV(IMAGE_PREFERENCES_FILE);
            for (int i = 1; i < array.size(); i++) {
                    List<String> list = array.get(i);
                    map.put(list.get(0), new ImageSignPreferences(list.get(0), list.get(1), list.get(2),
                            list.get(3), list.get(4), list.get(5), list.get(6), list.get(7), list.get(8), 
                            list.get(9), list.get(10)));
            }

            return map;
	}
        ///////////////////////////


	private static KeyStore loadKeystorePreferences(String path) {

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);

		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "", e);
		}
		char [] password = {'s','i','n','a','d','u','r','a'};

		KeyStore ks = null;
		try {
			ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(fis, password);

		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			logger.log(Level.SEVERE, "", e);
		}

		return ks;
	}


	public static KeyStore getTrustedKeystorePreferences() {

		if (trustedKeystore == null) {
			trustedKeystore = loadKeystorePreferences(TRUSTED_KEYSTORE_FILE);
		}

		return trustedKeystore;
	}

	public static KeyStore getCacheKeystorePreferences() {

		if (cacheKeystore == null) {
			cacheKeystore = loadKeystorePreferences(CACHE_KEYSTORE_FILE);
		}

		return cacheKeystore;
	}

	private static void saveKeystorePreferences(KeyStore ks, String path) {

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);

		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "", e);
		}
		char [] password = {'s','i','n','a','d','u','r','a'};

		try {
			ks.store(fos, password);

		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	public static void setTrustedKeystorePreferences(KeyStore ks) {

		saveKeystorePreferences(ks, TRUSTED_KEYSTORE_FILE);
		trustedKeystore = ks;
	}

	public static void setCacheKeystorePreferences(KeyStore ks) {

		saveKeystorePreferences(ks, CACHE_KEYSTORE_FILE);
		cacheKeystore = ks;
	}
        
        public static Map<String, TimeStampPreferences> getTimestampPreferencesNew() {
            Map<String, TimeStampPreferences> map = new TreeMap<>();
            // leer el csv
            List<List<String>> array = ExportHelper.importCSV(TIMESTAMP_PREFERENCES_FILE);
            for (int i = 1; i < array.size(); i++) {
                    List<String> list = array.get(i);
                    map.put(list.get(0), new TimeStampPreferences(list.get(0), list.get(1), list.get(2), list.get(3)));
            }

            return map;
	}
}