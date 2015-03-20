package flex.aponwao.gui.sections.preferences.windows;

import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;


public class InitPreferences extends FieldEditorPreferencePage {
    private BooleanFieldEditor pathSourceEnabled = null;
    private BooleanFieldEditor checkOutput = null;
    private BooleanFieldEditor checkBackUp = null;
    private RadioGroupFieldEditor pathSource = null;
	
    public InitPreferences() {
            super(GRID);
    }


    @Override
    protected void createFieldEditors() {
        pathSourceEnabled = new BooleanFieldEditor(PreferencesHelper.APLICATION_PATH_SOURCE_ENABLED, LanguageResource.getLanguage().getString(
                        "preferences.path.source.enabled"), getFieldEditorParent());
        addField(pathSourceEnabled);
        
        String[][] client = new String[2][2];
        client[0][0]=LanguageResource.getLanguage().getString("preferences.path.source.folder");
        client[0][1]=PreferencesHelper.APLICATION_PATH_SOURCE_FOLDER;
        client[1][0]=LanguageResource.getLanguage().getString("preferences.path.source.db");
        client[1][1]=PreferencesHelper.APLICATION_PATH_SOURCE_DB;
        
        pathSource = new RadioGroupFieldEditor(PreferencesHelper.APLICATION_PATH_SOURCE, LanguageResource.getLanguage().getString(
                        "preferences.path.source"), 1, client, getFieldEditorParent(), true);
        addField(pathSource);

        checkOutput = new BooleanFieldEditor(PreferencesHelper.OUTPUT_AUTO_ENABLE, LanguageResource.getLanguage().getString(
                        "preferences.main.output.auto.enable"), getFieldEditorParent());
        addField(checkOutput);

        checkBackUp = new BooleanFieldEditor(PreferencesHelper.OUTPUT_DIR_BACKUP_ENABLE, LanguageResource.getLanguage().getString(
                        "preferences.main.backup.enable"), getFieldEditorParent());
        addField(checkBackUp);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {

            super.propertyChange(event);

    }

    @Override
    public boolean performOk() {
        boolean ok = true;
        ok = super.performOk();
	
        return ok;
    }
}