package flex.aponwao.gui.sections.preferences.windows;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.global.windows.FileDialogs;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DuplicarPreferences extends FieldEditorPreferencePage {
        private Button checkaddCorrelativo = null;
        private Button checkaddCodigoBarras = null;
        private Text pathCodigoBarras = null;
        private Button checkCopiarDuplicados = null;
        private Button checkGenArchivoCanaimitas = null;
        private Text pathArchivoCanaimitas = null;
        
        private Composite compositeMain = null;
        private Shell sShell = null;
	
	public DuplicarPreferences() {
		super(FLAT);
	}

	@Override
	protected Control createContents(Composite parent) {
		// composite que contiene todos los elementos de la pantalla
		this.compositeMain = new Composite(parent, SWT.NONE);
		GridLayout gridLayoutPrincipal = new GridLayout();
		gridLayoutPrincipal.numColumns = 4;
		gridLayoutPrincipal.verticalSpacing = 10;
		gridLayoutPrincipal.marginBottom = 50;
		this.compositeMain.setLayout(gridLayoutPrincipal);

		GridData gdPrincipal = new GridData();
		gdPrincipal.horizontalAlignment = GridData.FILL;
		gdPrincipal.verticalAlignment = GridData.FILL;
		gdPrincipal.grabExcessHorizontalSpace = true;
		gdPrincipal.grabExcessVerticalSpace = true;
		this.compositeMain.setLayoutData(gdPrincipal);

		createArea();

		return this.compositeMain;
	}
        
	private void createArea() {
            sShell = new Shell(this.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
            sShell.setText(LanguageResource.getLanguage().getString("preferences.imagesign.dialog.title"));
            sShell.setImage(new Image(sShell.getDisplay(), ImagesResource.APLICATION_LOGO));
            
            //Agregar Correlativo
            checkaddCorrelativo = new Button(this.compositeMain,SWT.CHECK);
            checkaddCorrelativo.setText(LanguageResource.getLanguage().getString("preferences.duplicar.correlativo"));
//            checkaddCorrelativo.setLayoutData(gdcheckAddCorrelativo);
            checkaddCorrelativo.setSelection(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APPEARANCE_CORRELATIVO_ENABLE));
                
            //Copiar duplicados
            GridData gdcheckCopiarDuplicados = new GridData();
            gdcheckCopiarDuplicados.horizontalSpan = 4;
            gdcheckCopiarDuplicados.grabExcessHorizontalSpace = true;
            
            checkCopiarDuplicados = new Button(this.compositeMain,SWT.CHECK);
            checkCopiarDuplicados.setText(LanguageResource.getLanguage().getString("preferences.duplicar.copiar"));
            checkCopiarDuplicados.setLayoutData(gdcheckCopiarDuplicados);
            checkCopiarDuplicados.setSelection(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APLICATION_COPIAR_DUPLICADOS));
            
            //Boton probar copia de duplicados
            GridData gdButtonCopiaDuplicados = new GridData();
            gdButtonCopiaDuplicados.horizontalSpan = 4;
            gdButtonCopiaDuplicados.grabExcessHorizontalSpace = true;
            
            Button buttonProbarCopiaDuplicados = new Button(this.compositeMain, SWT.NONE);
            buttonProbarCopiaDuplicados.setLayoutData(gdButtonCopiaDuplicados);
            buttonProbarCopiaDuplicados.setText(LanguageResource.getLanguage().getString("button.duplicado.probar"));
            buttonProbarCopiaDuplicados.setImage(new Image(this.compositeMain.getDisplay(), ImagesResource.EDIT_IMG));
            buttonProbarCopiaDuplicados.addSelectionListener(new ButtonProbarCopiaDuplicadosListener());
            
            //Canaimitas
            if(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APPEARANCE_CANAIMITAS_VISIBLE)) {
                //Agregar Serial
                checkaddCodigoBarras = new Button(this.compositeMain,SWT.CHECK);
                checkaddCodigoBarras.setText(LanguageResource.getLanguage().getString("preferences.duplicar.codigobarras.enable"));
                checkaddCodigoBarras.setSelection(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APPEARANCE_CODBARRA_ENABLE));
                
                GridData gdcheckAddCodigoBarras = new GridData();
                gdcheckAddCodigoBarras.horizontalSpan = 2;
                gdcheckAddCodigoBarras.horizontalAlignment = GridData.FILL;
                gdcheckAddCodigoBarras.grabExcessHorizontalSpace = true;
                pathCodigoBarras = new Text(this.compositeMain, SWT.BORDER);
                pathCodigoBarras.setText(PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_CODBARRA_PATH));
                pathCodigoBarras.setLayoutData(gdcheckAddCodigoBarras);

                Button buttonExaminar = new Button(this.compositeMain, SWT.NONE);
                buttonExaminar.setText(LanguageResource.getLanguage().getString("button.browse"));
                buttonExaminar.addSelectionListener(new ButtonBrowseFileListener());
                
                //Generar archivo canaimitas
                checkGenArchivoCanaimitas = new Button(this.compositeMain,SWT.CHECK);
                checkGenArchivoCanaimitas.setText(LanguageResource.getLanguage().getString("preferences.duplicar.archivo.canaimitas.crear"));
                checkGenArchivoCanaimitas.setSelection(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.ARCHIVOS_CANAIMITAS_CREAR));
                
                GridData gdcheckGenArchivoCanaimitas = new GridData();
                gdcheckGenArchivoCanaimitas.horizontalSpan = 2;
                gdcheckGenArchivoCanaimitas.horizontalAlignment = GridData.FILL;
                gdcheckGenArchivoCanaimitas.grabExcessHorizontalSpace = true;
                pathArchivoCanaimitas = new Text(this.compositeMain, SWT.BORDER);
                pathArchivoCanaimitas.setText(PreferencesHelper.getPreferences().getString(PreferencesHelper.ARCHIVOS_CANAIMITAS_PATH));
                pathArchivoCanaimitas.setLayoutData(gdcheckGenArchivoCanaimitas);

                Button buttonExaminarArchivoCanaimitas = new Button(this.compositeMain, SWT.NONE);
                buttonExaminarArchivoCanaimitas.setText(LanguageResource.getLanguage().getString("button.browse"));
                buttonExaminarArchivoCanaimitas.addSelectionListener(new ButtonBrowseFolderListener());
                
                //Boton probar generar archivo canaimitas
                GridData gdButtonArchivoCanaimitas = new GridData();
                gdButtonArchivoCanaimitas.horizontalSpan = 4;
                gdButtonArchivoCanaimitas.grabExcessHorizontalSpace = true;
                
                Button buttonProbarArchivoCanaimitas = new Button(this.compositeMain, SWT.NONE);
                buttonProbarArchivoCanaimitas.setLayoutData(gdButtonArchivoCanaimitas);
                buttonProbarArchivoCanaimitas.setText(LanguageResource.getLanguage().getString("button.duplicado.archivo.canaimitas.probar"));
                buttonProbarArchivoCanaimitas.setImage(new Image(this.compositeMain.getDisplay(), ImagesResource.EDIT_IMG));
                buttonProbarArchivoCanaimitas.addSelectionListener(new ButtonProbarArchivoCanaimitasListener());
            }
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
	}
        
        @Override
    	public boolean performOk() {
                boolean ok = super.performOk();
                LanguageResource.reloadLanguage();
                savePreferences();
                return ok;

    	}

        @Override
        protected void createFieldEditors() {

        }
	
        private void savePreferences() {
            if(checkaddCorrelativo != null) {
                PreferencesHelper.getPreferences().setValue(PreferencesHelper.APPEARANCE_CORRELATIVO_ENABLE, checkaddCorrelativo.getSelection());
                PreferencesHelper.getPreferences().setValue(PreferencesHelper.APPEARANCE_CODBARRA_ENABLE, checkaddCodigoBarras.getSelection());
                PreferencesHelper.getPreferences().setValue(PreferencesHelper.APPEARANCE_CODBARRA_PATH, pathCodigoBarras.getText());
                PreferencesHelper.getPreferences().setValue(PreferencesHelper.APLICATION_COPIAR_DUPLICADOS, checkCopiarDuplicados.getSelection());
                PreferencesHelper.getPreferences().setValue(PreferencesHelper.ARCHIVOS_CANAIMITAS_CREAR, checkGenArchivoCanaimitas.getSelection());
                PreferencesHelper.getPreferences().setValue(PreferencesHelper.ARCHIVOS_CANAIMITAS_PATH, pathArchivoCanaimitas.getText());
            }
	}
        
        class ButtonProbarCopiaDuplicadosListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
                    //Preguntar si desea mover los archivos a respaldos
                    String m = LanguageResource.getLanguage().getString("info.copiar.duplicados.respaldar");
                    int respaldar = JOptionPane.showOptionDialog(
                        null,
                        m,
                        LanguageResource.getLanguage().getString("info.dialog.title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null);
                                
                    if(respaldar == JOptionPane.YES_OPTION) flex.aponwao.gui.sections.main.helpers.CopiarDuplicadosHelper.copiarDuplicados(sShell, true);
                    else flex.aponwao.gui.sections.main.helpers.CopiarDuplicadosHelper.copiarDuplicados(sShell, false);
		}

		public void widgetDefaultSelected(SelectionEvent event) {
                    widgetSelected(event);
		}
	}
        
        class ButtonProbarArchivoCanaimitasListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
                    try {
                        flex.aponwao.gui.sections.main.helpers.ArchivoCanaimita.probarArchivoCanaimita(sShell);
                        
                    } catch (IOException ex) {
                        Logger.getLogger(DuplicarPreferences.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}

		public void widgetDefaultSelected(SelectionEvent event) {
                    widgetSelected(event);
		}
	}
        
        class ButtonBrowseFileListener implements SelectionListener {

        @Override
		public void widgetSelected(SelectionEvent event) {

			String filePath = FileDialogs.openFileDialog(sShell, FileDialogs.ALL_TYPE);

			if (filePath != null) {
				pathCodigoBarras.setText(filePath);
			}
		}

        @Override
		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}
        
        class ButtonBrowseFolderListener implements SelectionListener {

        @Override
		public void widgetSelected(SelectionEvent event) {

			String filePath = FileDialogs.openFolderDialog(sShell);

			if (filePath != null) {
				pathArchivoCanaimitas.setText(filePath);
			}
		}

        @Override
		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}
}