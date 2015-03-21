package flex.aponwao.gui.sections.preferences.windows;


import java.util.logging.Logger;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.ResourceHelper;
import flex.aponwao.gui.sections.global.windows.InfoDialog;
import flex.aponwao.gui.sections.preferences.components.PasswordStringFieldEditor;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;


public class ProxyPreferences extends FieldEditorPreferencePage {
	
	private static final Logger	logger	= Logger.getLogger(ProxyPreferences.class.getName());
        
	private StringFieldEditor proxyHost = null;
	private StringFieldEditor proxyPort = null;
	private BooleanFieldEditor proxyEnabled = null;
        private StringFieldEditor proxyUser = null;
        private PasswordStringFieldEditor proxyPassword = null;
        private StringFieldEditor proxyExceptions = null;
	
	public ProxyPreferences() {
		super(GRID);
	}

	
	@Override
	protected void createFieldEditors() {

		
		proxyHost = new StringFieldEditor(PreferencesHelper.PROXY_HOST, LanguageResource.getLanguage()
				.getString("preferences.proxy.host"), getFieldEditorParent());
		addField(proxyHost);

		proxyPort = new StringFieldEditor(PreferencesHelper.PROXY_PORT, LanguageResource.getLanguage()
				.getString("preferences.proxy.port"), getFieldEditorParent());
		addField(proxyPort);

		proxyUser = new StringFieldEditor(PreferencesHelper.PROXY_USER, LanguageResource.getLanguage()
				.getString("preferences.proxy.user"), getFieldEditorParent());
		addField(proxyUser);
		
		proxyPassword = new PasswordStringFieldEditor(PreferencesHelper.PROXY_PASS, LanguageResource.getLanguage()
				.getString("preferences.proxy.pass"), getFieldEditorParent());
		addField(proxyPassword);
		
		proxyExceptions = new StringFieldEditor(PreferencesHelper.PROXY_NON_PROXY, LanguageResource.getLanguage()
				.getString("preferences.proxy.non_proxy"), getFieldEditorParent());
		addField(proxyExceptions);
		
		proxyEnabled = new BooleanFieldEditor(PreferencesHelper.PROXY_ENABLE, LanguageResource.getLanguage().getString(
				"preferences.proxy.enable"), getFieldEditorParent());
		addField(proxyEnabled);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {

		super.propertyChange(event);
		
	}
	
	@Override
	public boolean performOk() {
		
		boolean ok = false;
		
		if (proxyEnabled != null && proxyEnabled.getBooleanValue()
				&& (proxyHost.getStringValue() == null || proxyHost.getStringValue().equals("")
						|| proxyPort.getStringValue() == null || proxyPort.getStringValue().equals(""))) {

			InfoDialog id = new InfoDialog(this.getShell());
			id.open(LanguageResource.getLanguage().getString("error.proxy_configuration"));
			logger.severe(LanguageResource.getLanguage().getString("error.proxy_configuration"));
			
		} else {
			ok = super.performOk();
			ResourceHelper.configureProxy();
		}
		
		return ok;
	}
	
}