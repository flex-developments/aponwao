package flex.aponwao.gui.sections.preferences.windows;


import java.util.logging.Logger;

import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;

/**
* This class demonstrates field editors
*/

public class CertPreferences extends FieldEditorPreferencePage {
	
	private static Logger	logger	= Logger.getLogger(CertPreferences.class.getName());
	

	public CertPreferences() {
		// Use the "flat" layout
		super(FLAT);
	}

	@Override
	protected void createFieldEditors() {
		
		String[][] values = {
				{ LanguageResource.getLanguage().getString("preferences.cert.type.software"), PreferencesHelper.CERT_SOFTWARE_TYPE },
				{ LanguageResource.getLanguage().getString("preferences.cert.type.hardware"), PreferencesHelper.CERT_HARDWARE_TYPE } };
		
		RadioGroupFieldEditor checkBoxSoftware = new RadioGroupFieldEditor(PreferencesHelper.CERT_TYPE, 
				LanguageResource.getLanguage().getString("preferences.cert.type"), 2, values, getFieldEditorParent());
		
		addField(checkBoxSoftware);
		
	}
}