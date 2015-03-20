package flex.aponwao.gui.sections.preferences.windows;

import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;


public class DBPreferences extends FieldEditorPreferencePage {
    private StringFieldEditor dbUser = null;
    private StringFieldEditor dbPass = null;
    private StringFieldEditor dbURL = null;
    private StringFieldEditor dbName = null;
    private StringFieldEditor dbFunction = null;
    private StringFieldEditor dbOrigenField = null;
    private StringFieldEditor dbDestinoField = null;
    private StringFieldEditor dbUserType = null;
	
    public DBPreferences() {
            super(GRID);
    }


    @Override
    protected void createFieldEditors() {
//        dbUser = new StringFieldEditor(PreferencesHelper.APLICATION_PATH_SOURCE_DB_USER, LanguageResource.getLanguage()
//                        .getString("preferences.path.source.db.user"), getFieldEditorParent());
//        addField(dbUser);
//        
//        dbPass = new StringFieldEditor(PreferencesHelper.APLICATION_PATH_SOURCE_DB_PASSWORD, LanguageResource.getLanguage()
//                        .getString("preferences.path.source.db.password"), getFieldEditorParent());
//        addField(dbPass);
        
        dbUserType = new StringFieldEditor(PreferencesHelper.APLICATION_PATH_SOURCE_DB_USER_TYPE, LanguageResource.getLanguage()
                        .getString("preferences.path.source.db.user.type"), getFieldEditorParent());
        addField(dbUserType);
        
        dbURL = new StringFieldEditor(PreferencesHelper.APLICATION_PATH_SOURCE_DB_URL, LanguageResource.getLanguage()
                        .getString("preferences.path.source.db.url"), getFieldEditorParent());
        addField(dbURL);
        
        dbName = new StringFieldEditor(PreferencesHelper.APLICATION_PATH_SOURCE_DB_NAME, LanguageResource.getLanguage()
                        .getString("preferences.path.source.db.name"), getFieldEditorParent());
        addField(dbName);
        
        dbFunction = new StringFieldEditor(PreferencesHelper.APLICATION_PATH_SOURCE_DB_FUNCTION, LanguageResource.getLanguage()
                        .getString("preferences.path.source.db.function"), getFieldEditorParent());
        addField(dbFunction);
        
        dbOrigenField = new StringFieldEditor(PreferencesHelper.APLICATION_PATH_SOURCE_DB_ORIGEN_FIELD, LanguageResource.getLanguage()
                        .getString("preferences.path.source.db.source.field"), getFieldEditorParent());
        addField(dbOrigenField);
        
        dbDestinoField = new StringFieldEditor(PreferencesHelper.APLICATION_PATH_SOURCE_DB_DESTINO_FIELD, LanguageResource.getLanguage()
                        .getString("preferences.path.source.db.destination.field"), getFieldEditorParent());
        addField(dbDestinoField);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {

            super.propertyChange(event);

    }

    @Override
    public boolean performOk() {

            boolean ok = true;
            //mailClientEditor.setStringValue(mailClient.toString());

//		if (proxyEnabled != null && proxyEnabled.getBooleanValue()
//				&& (proxyHost.getStringValue() == null || proxyHost.getStringValue().equals("")
//						|| proxyPort.getStringValue() == null || proxyPort.getStringValue().equals(""))) {
//
//			InfoDialog id = new InfoDialog(this.getShell());
//			id.open(LanguageResource.getLanguage().getString("error.proxy_configuration"));
//			logger.severe(LanguageResource.getLanguage().getString("error.proxy_configuration"));
//			
//		} else {
                    ok = super.performOk();
//			ResourceHelper.configureProxy();
//		}
//		
            return ok;
    }
}