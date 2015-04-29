package flex.aponwao.gui.sections.preferences.windows;

import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.PropertyChangeEvent;


public class FolderPreferences extends FieldEditorPreferencePage {
    private DirectoryFieldEditor folderSourcePath = null;
    private DirectoryFieldEditor folderFinalPath = null;
    private DirectoryFieldEditor folderBackUpPath = null;
    private BooleanFieldEditor vieDirEnabled = null;
	
    public FolderPreferences() {
            super(GRID);
    }


    @Override
    protected void createFieldEditors() {//
        folderSourcePath = new DirectoryFieldEditor(
		PreferencesHelper.APLICATION_PATH_SOURCE_FOLDER_VALUE,
		LanguageResource.getLanguage().getString("preferences.path.source.folder.value"),
 		getFieldEditorParent());
	addField(folderSourcePath);

        folderFinalPath = new DirectoryFieldEditor(
		PreferencesHelper.APLICATION_PATH_FINAL_FOLDER_VALUE,
		LanguageResource.getLanguage().getString("preferences.main.output_dir"),
 		getFieldEditorParent());
	addField(folderFinalPath);

        folderBackUpPath = new DirectoryFieldEditor(
		PreferencesHelper.APLICATION_PATH_BACKUP_FOLDER_VALUE,
		LanguageResource.getLanguage().getString("preferences.main.backup_dir"),
 		getFieldEditorParent());
	addField(folderBackUpPath);
        vieDirEnabled = new BooleanFieldEditor(PreferencesHelper.VIEW_CONTENT_FOLDER, LanguageResource.getLanguage().getString(
                        "view.content.folder"), getFieldEditorParent());
        addField(vieDirEnabled);
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