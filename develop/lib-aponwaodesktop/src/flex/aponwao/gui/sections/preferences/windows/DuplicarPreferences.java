package flex.aponwao.gui.sections.preferences.windows;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.global.windows.FileDialogs;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;

import javax.swing.JOptionPane;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DuplicarPreferences extends FieldEditorPreferencePage {
        private Button checkaddCorrelativo = null;
        private Button checkaddCodigoBarras = null;
        private Text pathCodigoBarras = null;
        private Button checkCopiarDuplicados = null;
        
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
            GridData gdcheckAddCorrelativo = new GridData();
            gdcheckAddCorrelativo.horizontalSpan = 4;
            gdcheckAddCorrelativo.grabExcessHorizontalSpace = true;
            checkaddCorrelativo.setLayoutData(gdcheckAddCorrelativo);
            checkaddCorrelativo.setSelection(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APPEARANCE_CORRELATIVO_ENABLE));

            //Agregar Serial
            checkaddCodigoBarras = new Button(this.compositeMain,SWT.CHECK);
            checkaddCodigoBarras.setText(LanguageResource.getLanguage().getString("preferences.duplicar.codigobarras.enable"));
            GridData gdcheckAddCodigoBarras = new GridData();
            gdcheckAddCodigoBarras.horizontalSpan = 4;
            gdcheckAddCodigoBarras.grabExcessHorizontalSpace = true;
            checkaddCodigoBarras.setLayoutData(gdcheckAddCodigoBarras);
            checkaddCodigoBarras.setSelection(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APPEARANCE_CODBARRA_ENABLE));
            
            //Archivo de seriales
            Label label = new Label(this.compositeMain, SWT.NONE);
            label.setText(LanguageResource.getLanguage().getString("preferences.duplicar.codigobarras.path"));

            pathCodigoBarras = new Text(this.compositeMain, SWT.BORDER);
            GridData gdpathCodigoBarras = new GridData();
            gdpathCodigoBarras.horizontalSpan = 1;
            gdpathCodigoBarras.horizontalAlignment = SWT.FILL;
            gdpathCodigoBarras.grabExcessHorizontalSpace = true;
            pathCodigoBarras.setText(PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_CODBARRA_PATH));
            pathCodigoBarras.setLayoutData(gdpathCodigoBarras);
            
            Button buttonExaminar = new Button(this.compositeMain, SWT.NONE);
            buttonExaminar.setText(LanguageResource.getLanguage().getString("button.browse"));
            buttonExaminar.addSelectionListener(new ButtonBrowseListener());
            buttonExaminar.setLayoutData(gdpathCodigoBarras);
                
            //Copiar duplicados
            checkCopiarDuplicados = new Button(this.compositeMain,SWT.CHECK);
            checkCopiarDuplicados.setText(LanguageResource.getLanguage().getString("preferences.main.duplicados.copiar"));
            GridData gdcheckCopiarDuplicados = new GridData();
            gdcheckCopiarDuplicados.horizontalSpan = 4;
            gdcheckCopiarDuplicados.grabExcessHorizontalSpace = true;
            checkCopiarDuplicados.setLayoutData(gdcheckCopiarDuplicados);
            checkCopiarDuplicados.setSelection(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APLICATION_COPIAR_DUPLICADOS));
            
            Button buttonProbar = new Button(this.compositeMain, SWT.NONE);
            GridData gdAdd = new GridData();
            gdAdd.horizontalAlignment = GridData.FILL;
            buttonProbar.setLayoutData(gdAdd);
            buttonProbar.setText(LanguageResource.getLanguage().getString("button.probar"));
            buttonProbar.setImage(new Image(this.compositeMain.getDisplay(), ImagesResource.EDIT_IMG));
            buttonProbar.addSelectionListener(new ButtonProbarListener());
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
            }
	}
        
        class ButtonProbarListener implements SelectionListener {
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
        
        class ButtonBrowseListener implements SelectionListener {

        @Override
		public void widgetSelected(SelectionEvent event) {

			String filePath = FileDialogs.openFileDialog(sShell, FileDialogs.BIN_TYPE);

			if (filePath != null) {
				pathCodigoBarras.setText(filePath);
			}
		}

        @Override
		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}
}