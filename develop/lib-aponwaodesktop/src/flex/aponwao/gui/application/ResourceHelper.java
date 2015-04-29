/*
 * # Copyright 2008 zylk.net # # This file is part of Sinadura. # # Sinadura is free software: you can redistribute it
 * and/or modify # it under the terms of the GNU General Public License as published by # the Free Software Foundation,
 * either version 2 of the License, or # (at your option) any later version. # # Sinadura is distributed in the hope
 * that it will be useful, # but WITHOUT ANY WARRANTY; without even the implied warranty of # MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the # GNU General Public License for more details. # # You should have received a copy
 * of the GNU General Public License # along with Sinadura. If not, see <http://www.gnu.org/licenses/>. [^] # # See
 * COPYRIGHT.txt for copyright notices and details. #
 */
package flex.aponwao.gui.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import java.io.FileInputStream;

/**
 * @author zylk.net
 */
public class ResourceHelper {
	private static final Logger							logger						= Logger.getLogger(ResourceHelper.class.getName());
	
	public static final String						APPLICATION_TITLE			= PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_TITLE);
        
        private static final String RESOURCES_PATH = "resources";
        
	private static final String CONFIGURATION_FILE = PreferencesHelper.PREFERENCES_PATH + File.separatorChar + "configuration.properties";
	
	private static Properties						configuration = null;

	public static String							INTROKEY					= "RETURN";


	public static Properties getConfiguration() {

		if (configuration == null) {
			configuration = new Properties();
			try {
                                InputStream is = new FileInputStream(CONFIGURATION_FILE);
				configuration.load(is);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "", e);
			}
		}
		return configuration;
	}

	
	public static void configureProxy() {
		
		// Configuracion del proxy
		String host = PreferencesHelper.getPreferences().getString(PreferencesHelper.PROXY_HOST);
		String port = PreferencesHelper.getPreferences().getString(PreferencesHelper.PROXY_PORT);
		String user = PreferencesHelper.getPreferences().getString(PreferencesHelper.PROXY_USER);
		String pass = PreferencesHelper.getPreferences().getString(PreferencesHelper.PROXY_PASS);
		String nonProxy = PreferencesHelper.getPreferences().getString(PreferencesHelper.PROXY_NON_PROXY);
		Boolean enable = PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.PROXY_ENABLE);
		
		if (enable) {

			System.getProperties().put("http.proxySet", "true");
			System.getProperties().put("http.proxyHost", host);
			System.getProperties().put("http.proxyPort", port);

			if (user != null && !user.equals("")) {

				System.getProperties().put("http.proxyUser", user);
				if (pass != null) {
					System.getProperties().put("http.proxyPassword", pass);
				}
			}
			
			if (nonProxy != null && !nonProxy.equals("")) {
				System.getProperties().put("http.nonProxyHosts", nonProxy);
			}
			
		} else {
			
			System.getProperties().put("http.proxySet", "null");
			System.getProperties().put("http.proxyHost", "null");
			System.getProperties().put("http.proxyPort", "null");
			System.getProperties().put("http.proxyUser", "null");
			System.getProperties().put("http.proxyPassword", "null");
		}
		
	}
        
        //OJO... Modificacion Felix
	private static String rootPath = null;

        public static String getRootPath() {
            confRootPath();
            return rootPath;
        }        
        
        private static void confRootPath() {
            if(rootPath==null) {
                rootPath = flex.aponwao.gui.Aponwao.class.getResource("").getPath();
                rootPath = rootPath.substring(rootPath.indexOf(":")  + 1, rootPath.lastIndexOf("!"));
                rootPath = rootPath.substring(0, rootPath.lastIndexOf("/"));
                rootPath = rootPath.substring(0, rootPath.lastIndexOf("/"));
                rootPath = rootPath + File.separator;
            }
        }
        
        public static String getRESOURCES_PATH() {
            confRootPath();
            return rootPath + RESOURCES_PATH;
        }
        //**********************************************************************
}