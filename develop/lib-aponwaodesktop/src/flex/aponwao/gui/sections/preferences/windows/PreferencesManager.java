/*
# Copyright 2008 zylk.net
#
# This file is part of Sinadura.
#
# Sinadura is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 2 of the License, or
# (at your option) any later version.
#
# Sinadura is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with Sinadura.  If not, see <http://www.gnu.org/licenses/>. [^]
#
# See COPYRIGHT.txt for copyright notices and details.
#
*/
package flex.aponwao.gui.sections.preferences.windows;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Shell;


public class PreferencesManager {
	
	private static Logger logger = Logger.getLogger(PreferencesManager.class.getName());

	/**
	 * @param mainShell
	 */
	public void run(Shell mainShell) {

		// Create the preference manager
		PreferenceManager mgr = new PreferenceManager();

		// Create the nodes
                SignAppearancePreferences signAppearancePreferences = new SignAppearancePreferences();
                signAppearancePreferences.setTitle(LanguageResource.getLanguage().getString("preferences.image.title"));
                signAppearancePreferences.setDescription(LanguageResource.getLanguage().getString("preferences.image.description") + "\n");

			ValidatePreferences validatePreferences = new ValidatePreferences();
			validatePreferences.setTitle(LanguageResource.getLanguage().getString("preferences.validate.title"));
			validatePreferences.setDescription(LanguageResource.getLanguage().getString("preferences.validate.description") + "\n");

		CertPreferences certPreferences = new CertPreferences();
		certPreferences.setTitle(LanguageResource.getLanguage().getString("preferences.cert.title"));
		certPreferences.setDescription(LanguageResource.getLanguage().getString("preferences.cert.description") + "\n");
		
			SoftwareCertPreferences softwareCertPreferences = new SoftwareCertPreferences();
			softwareCertPreferences.setTitle(LanguageResource.getLanguage().getString("preferences.cert.software.title"));
			softwareCertPreferences.setDescription(LanguageResource.getLanguage().getString("preferences.cert.software.description") + "\n");
			
			HardwareCertPreferences hardwareCertPreferences = new HardwareCertPreferences();
			hardwareCertPreferences.setTitle(LanguageResource.getLanguage().getString("preferences.cert.hardware.title"));
			hardwareCertPreferences.setDescription(LanguageResource.getLanguage().getString("preferences.cert.hardware.description") + "\n");

		TrustedCertsPreferences trustedPreferences = new TrustedCertsPreferences();
		trustedPreferences.setTitle(LanguageResource.getLanguage().getString("preferences.trusted.title"));
		trustedPreferences.setDescription(LanguageResource.getLanguage().getString("preferences.trusted.description") + "\n");
		
			CacheCertsPreferences cachePreferences = new CacheCertsPreferences();
			cachePreferences.setTitle(LanguageResource.getLanguage().getString("preferences.cache.title"));
			cachePreferences.setDescription(LanguageResource.getLanguage().getString("preferences.cache.description") + "\n");	
		
                InitPreferences initPreferences = new InitPreferences();
		initPreferences.setTitle(LanguageResource.getLanguage().getString("preferences.init.title"));
		initPreferences.setDescription(LanguageResource.getLanguage().getString("preferences.init.description") + "\n");
                        FolderPreferences folderPreferences = new FolderPreferences();
                        folderPreferences.setTitle(LanguageResource.getLanguage().getString("preferences.folder.title"));
                        folderPreferences.setDescription(LanguageResource.getLanguage().getString("preferences.folder.description") + "\n");
                        
                        DBPreferences dbPreferences = new DBPreferences();
                        dbPreferences.setTitle(LanguageResource.getLanguage().getString("preferences.db.title"));
                        dbPreferences.setDescription(LanguageResource.getLanguage().getString("preferences.db.description") + "\n");
                
                GeneralPreferences generalPreferences = new GeneralPreferences();
		generalPreferences.setTitle(LanguageResource.getLanguage().getString("preferences.main.title"));
		generalPreferences.setDescription(LanguageResource.getLanguage().getString("preferences.main.description") + "\n");
                
                MailPreferences mailPreferences = new MailPreferences();
		mailPreferences.setTitle(LanguageResource.getLanguage().getString("preferences.email.title"));
		mailPreferences.setDescription(LanguageResource.getLanguage().getString("preferences.email.description") + "\n");
                
                DuplicarPreferences duplicarPreferences = new DuplicarPreferences();
                duplicarPreferences.setTitle(LanguageResource.getLanguage().getString("preferences.duplicar.title"));
                duplicarPreferences.setDescription(LanguageResource.getLanguage().getString("preferences.duplicar.description") + "\n");

		ProxyPreferences proxyPreferences = new ProxyPreferences();
		proxyPreferences.setTitle(LanguageResource.getLanguage().getString("preferences.proxy.title"));
		proxyPreferences.setDescription(LanguageResource.getLanguage().getString("preferences.proxy.description") + "\n");

                PreferenceNode signAppearanceNode = new PreferenceNode("signAppearanceNode", signAppearancePreferences);
			PreferenceNode validateNode = new PreferenceNode("validateNode", validatePreferences);
		PreferenceNode certNode = new PreferenceNode("certNode", certPreferences);
			PreferenceNode softwareCertNode = new PreferenceNode("softwareCertNode", softwareCertPreferences);
			PreferenceNode hardwareCertNode = new PreferenceNode("hardwareCertNode", hardwareCertPreferences);
		PreferenceNode trustedNode = new PreferenceNode("trustedNode", trustedPreferences);
			PreferenceNode cacheNode = new PreferenceNode("cacheNode", cachePreferences);
                PreferenceNode initNode = new PreferenceNode("initNode", initPreferences);
                        PreferenceNode folderNode = new PreferenceNode("folderNode", folderPreferences);
                        PreferenceNode dbNode = new PreferenceNode("dbNode", dbPreferences);
                PreferenceNode generalNode = new PreferenceNode("generalNode", generalPreferences);
                PreferenceNode mailNode = new PreferenceNode("mailNode", mailPreferences);
		PreferenceNode proxyNode = new PreferenceNode("proxyNode", proxyPreferences);
                PreferenceNode duplicarNode = new PreferenceNode("duplicarNode", duplicarPreferences);

		//OJO... Modificacion Felix
                mgr.addToRoot(signAppearanceNode);
			mgr.addTo(signAppearanceNode.getId(), validateNode);
		mgr.addToRoot(certNode);
			mgr.addTo(certNode.getId(), softwareCertNode);
			mgr.addTo(certNode.getId(), hardwareCertNode);
		mgr.addToRoot(trustedNode);
			mgr.addTo(trustedNode.getId(), cacheNode);
                mgr.addToRoot(initNode);
                        mgr.addTo(initNode.getId(), folderNode);
                        mgr.addTo(initNode.getId(), dbNode);
                mgr.addToRoot(generalNode);
                mgr.addToRoot(mailNode);
		mgr.addToRoot(proxyNode);
                mgr.addToRoot(duplicarNode);
                
                //**************************************************************
                
		// Create the preferences dialog
		PreferenceDialog dlg = new PreferenceDialog(mainShell, mgr);

		// Set the preference store
		PreferenceStore ps = PreferencesHelper.getPreferences();
		try {
			ps.load();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}
		dlg.setPreferenceStore(ps);

		// Open the dialog
		dlg.open();
                
		try {
			// Save the preferences
			ps.save();

		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}

	}

	/**
	 * The application entry point
	 *
	 * @param mainShell
	 */
	public void abrirVentana(Shell mainShell) {
		this.run(mainShell);
	}

}