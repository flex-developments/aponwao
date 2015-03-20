package flex.aponwao.gui.sections.main.windows;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.ValidatorHelper;
import flex.aponwao.gui.sections.global.windows.InputDialog;
import flex.aponwao.gui.sections.global.windows.PasswordDialog;
import flex.aponwao.gui.sections.main.events.EnviarPDFListener;
import flex.aponwao.gui.sections.main.events.FirmarPDFListener;
import flex.aponwao.gui.sections.main.events.ValidarPDFListener;
import flex.aponwao.gui.sections.main.events.VisualizarPDFListener;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class PanelPDF extends Composite {

	private static final Logger logger = Logger.getLogger(PanelPDF.class.getName());
	
	
	private Button				buttonAddPDF			= null;
	private Button				buttonAddDir				= null;
	private Button				buttonSend				= null;
	private Button				buttonRemove				= null;
	private Button				buttonView				= null;
	
	private Button				buttonSign				= null;
        //OJO... Modifcacion Felix
        private Button				buttonDuplicarFirmar				= null;
        //**********************************************************************
	private Button				buttonValidate				= null;
	
	private TablePDF 			tablePDF  = null;
	
	
	public PanelPDF(Composite parent, int style) {
		
		super(parent, style);
		initialize();
	}

	private void initialize() {
		
		
		GridData gdPanel = new GridData();
		gdPanel.horizontalAlignment = GridData.FILL;
		gdPanel.verticalAlignment = GridData.FILL;
		gdPanel.grabExcessHorizontalSpace = true;
		gdPanel.grabExcessVerticalSpace = true;
		this.setLayoutData(gdPanel);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		this.setLayout(gridLayout);
		
		tablePDF = new TablePDF (this, SWT.NONE);
		GridData gdTable = new GridData();
		gdTable.horizontalAlignment = GridData.FILL;
		gdTable.verticalAlignment = GridData.FILL;
		gdTable.grabExcessHorizontalSpace = true;
		gdTable.grabExcessVerticalSpace = true;
		tablePDF.setLayoutData(gdTable);
		
		
		// Composite buttons
		Composite buttonsComposite = new Composite (this, SWT.NONE);
		GridData gdButtons = new GridData();
		gdButtons.horizontalAlignment = GridData.FILL;
		gdButtons.verticalAlignment = GridData.FILL;
		buttonsComposite.setLayoutData(gdButtons);
		
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 1;
		buttonsComposite.setLayout(gridLayout2);
		
		
		this.buttonAddPDF = new Button(buttonsComposite, SWT.NONE);
		this.buttonAddPDF.setText(ValidatorHelper.formatedTextButton(LanguageResource.getLanguage().getString(
				"section.sign.button.add.pdf")));
		this.buttonAddPDF.setImage(new Image(this.getDisplay(), ImagesResource.ADD_FILE_IMG));
		GridData gdAddFile = new GridData();
		gdAddFile.horizontalAlignment = GridData.FILL;
		this.buttonAddPDF.setLayoutData(gdAddFile);

		this.buttonAddDir = new Button(buttonsComposite, SWT.NONE);
		this.buttonAddDir.setText(ValidatorHelper.formatedTextButton(LanguageResource.getLanguage().getString(
				"section.sign.button.add.dir")));
		this.buttonAddDir.setImage(new Image(this.getDisplay(), ImagesResource.ADD_DIR_IMG));
		GridData gdUpdate = new GridData();
		gdUpdate.horizontalAlignment = GridData.FILL;
		this.buttonAddDir.setLayoutData(gdUpdate);

		this.buttonRemove = new Button(buttonsComposite, SWT.NONE);
		this.buttonRemove.setText(ValidatorHelper.formatedTextButton(LanguageResource.getLanguage().getString(
				"section.sign.button.remove")));
		this.buttonRemove.setImage(new Image(this.getDisplay(), ImagesResource.DELETE_FILE_IMG));
		GridData gdDelete = new GridData();
		gdDelete.horizontalAlignment = GridData.FILL;
		this.buttonRemove.setLayoutData(gdDelete);
		
		this.buttonView = new Button(buttonsComposite, SWT.NONE);
		this.buttonView.setText(ValidatorHelper.formatedTextButton(LanguageResource.getLanguage().getString(
				"section.sign.button.view")));
		this.buttonView.setImage(new Image(this.getDisplay(), ImagesResource.VIEW_IMG));
		GridData gdView = new GridData();
		gdView.horizontalAlignment = GridData.FILL;
		this.buttonView.setLayoutData(gdView);
		
		this.buttonSend = new Button(buttonsComposite, SWT.NONE);
		this.buttonSend.setText(ValidatorHelper.formatedTextButton(LanguageResource.getLanguage().getString(
				"section.sign.button.send")));
		this.buttonSend.setImage(new Image(this.getDisplay(), ImagesResource.SEND_FILE_IMG));
		GridData gdSend = new GridData();
		gdSend.horizontalAlignment = GridData.FILL;
		this.buttonSend.setLayoutData(gdSend);
		
		this.buttonSign = new Button(buttonsComposite, SWT.NONE);
		this.buttonSign.setText(ValidatorHelper.formatedTextButton(LanguageResource.getLanguage().getString(
				"section.sign.button.sign")));
		this.buttonSign.setImage(new Image(this.getDisplay(), ImagesResource.SIGN_FILE_IMG));
		GridData gdExportar = new GridData();
		gdExportar.horizontalAlignment = GridData.FILL;
		this.buttonSign.setLayoutData(gdExportar);
                
                //OJO... Firmar y Duplicar
                this.buttonDuplicarFirmar = new Button(buttonsComposite, SWT.NONE);
		this.buttonDuplicarFirmar.setText(ValidatorHelper.formatedTextButton(LanguageResource.getLanguage().getString(
				"section.sign.button.duplicar.firmar")));
		this.buttonDuplicarFirmar.setImage(new Image(this.getDisplay(), ImagesResource.SIGN_FILE_IMG));
		GridData gdDuplicarExportar = new GridData();
		gdDuplicarExportar.horizontalAlignment = GridData.FILL;
		this.buttonDuplicarFirmar.setLayoutData(gdDuplicarExportar);
                //**************************************************************
		
		this.buttonValidate = new Button(buttonsComposite, SWT.NONE);
		this.buttonValidate.setText(ValidatorHelper.formatedTextButton(LanguageResource.getLanguage().getString(
				"section.sign.button.validate")));
		this.buttonValidate.setImage(new Image(this.getDisplay(), ImagesResource.VALIDATE_FILE_IMG));
		GridData gdValidate = new GridData();
		gdValidate.horizontalAlignment = GridData.FILL;
		this.buttonValidate.setLayoutData(gdValidate);
		
		Label labelEsle = new Label(buttonsComposite, SWT.NONE);
		labelEsle.setImage(new Image(this.getDisplay(), ImagesResource.ORG_IMG));
		
		GridData gdLabel = new GridData();
		gdLabel.horizontalAlignment = GridData.CENTER;
		gdLabel.verticalAlignment = GridData.FILL;
		gdLabel.grabExcessVerticalSpace = true;
		labelEsle.setLayoutData(gdLabel);
		
		
		this.tablePDF.addSelectionListener(new ButtonControlerListener());
		
		this.buttonAddPDF.addSelectionListener(new ButtonAddPDFListener());
		this.buttonAddDir.addSelectionListener(new ButtonAddDirListener());
		this.buttonRemove.addSelectionListener(new ButtonRemoveListener());
		this.buttonView.addSelectionListener(new VisualizarPDFListener(tablePDF));
		this.buttonSend.addSelectionListener(new EnviarPDFListener(tablePDF));
                //OJO... Modificacion Felix
		this.buttonSign.addSelectionListener(new FirmarPDFListener(tablePDF, false));
                this.buttonDuplicarFirmar.addSelectionListener(new FirmarPDFListener(tablePDF, true));
                //**************************************************************
		this.buttonValidate.addSelectionListener(new ValidarPDFListener(tablePDF));
		
		
//		composite.addListener(SWT.KeyUp, new WindowKeyListener());
//		this.table.addListener(SWT.KeyUp, new WindowKeyListener());
//		this.botonAddArchivo.addListener(SWT.KeyUp, new WindowKeyListener());
//		this.botonAddDirectorio.addListener(SWT.KeyUp, new WindowKeyListener());
//		this.botonModificar.addListener(SWT.KeyUp, new WindowKeyListener());
//		this.botonEliminar.addListener(SWT.KeyUp, new WindowKeyListener());
                
                //OJO...Modificacion Felix
                if (PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APLICATION_PATH_SOURCE_ENABLED)) {
                    if (PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_SOURCE).equals(PreferencesHelper.APLICATION_PATH_SOURCE_FOLDER)) {
                        //Rutas desde carpetas locales
                        tablePDF.addFilesFromFolder(true, PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_SOURCE_FOLDER_VALUE));
                    } else {
                        //Aqui traer rutas de DB
                        String driver = PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_SOURCE_DB_DRIVER);
                        String url = PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_SOURCE_DB_URL);
                        String dbname = PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_SOURCE_DB_NAME);
                        String function = PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_SOURCE_DB_FUNCTION);
                        String user = PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_SOURCE_DB_USER);
                        String pass = PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_SOURCE_DB_PASSWORD);
                        String campoOrigen = PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_SOURCE_DB_ORIGEN_FIELD);
                        String campoDestino = PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_SOURCE_DB_DESTINO_FIELD);
                        int userType = PreferencesHelper.getPreferences().getInt(PreferencesHelper.APLICATION_PATH_SOURCE_DB_USER_TYPE);
                        
                        InputDialog inputDialog = new InputDialog(tablePDF.getShell());
                        String userSistema = null;
                        userSistema = inputDialog.open(LanguageResource.getLanguage().getString("db.connection.user"));
                        if (userSistema == null || userSistema.equals("")) userSistema = "";

                        String passwordSistema = null;
                        PasswordDialog passwordDialog = new PasswordDialog(tablePDF.getShell());
                        passwordSistema = passwordDialog.open(LanguageResource.getLanguage().getString("db.connection.pass"));
                        
                        MessageDigest md;
                        try {
                            md = MessageDigest.getInstance("MD5");
                            passwordSistema = userSistema + ":" + passwordSistema;
                            md.update(passwordSistema.getBytes());
                            byte[] digest = md.digest();
                            passwordSistema = new String(org.bouncycastle.util.encoders.Base64.encode(digest));
                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(PanelPDF.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        userSistema = "V-99999999-9";
//                        passwordSistema = "0zsCQGj8zgeEX+w5BpgQlQ==";
                        
                        tablePDF.addFilesFromDB(driver, url, dbname, function, user, pass, campoOrigen, campoDestino, userSistema, passwordSistema, userType);
                    }
                }
                //**************************************************************
	}
	
	
	
	
	private void changeButtonEnabled() {
		
		if (tablePDF.getSelectedPDFs().size() == 0) {
			
			buttonAddPDF.setEnabled(true);
			buttonAddDir.setEnabled(true);
			buttonRemove.setEnabled(false);
			buttonSign.setEnabled(false);
			buttonValidate.setEnabled(false);
			
		} else if (tablePDF.getSelectedPDFs().size() == 1) {
			
			buttonAddPDF.setEnabled(true);
			buttonAddDir.setEnabled(true);
			buttonRemove.setEnabled(true);
			buttonSign.setEnabled(true);
			buttonValidate.setEnabled(true);
			
		} else {
			
			buttonAddPDF.setEnabled(true);
			buttonAddDir.setEnabled(true);
			buttonRemove.setEnabled(true);
			buttonSign.setEnabled(true);
			buttonValidate.setEnabled(true);
		}
	}
	
	
	class ButtonControlerListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {
			
			changeButtonEnabled();

		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}
	
	class ButtonAddPDFListener implements SelectionListener {
		
		public void widgetSelected(SelectionEvent event) {
			
			tablePDF.addFiles(false);
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}
	
	class ButtonAddDirListener implements SelectionListener {
		
		public void widgetSelected(SelectionEvent event) {
			
			tablePDF.addFiles(true);
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

	
	class ButtonRemoveListener implements SelectionListener {
		
		public void widgetSelected(SelectionEvent event) {

			tablePDF.removeFile();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}
	
//	// Listener para las hotkeys generales
//	class WindowKeyListener implements Listener {
//
//		public void handleEvent(Event e) {
//
//			if (SWT.F1 == e.keyCode) {
//				WebBrowser.openDefaultBrowser(LanguageResource.getLanguage().getString("documentation.uri"));
//			}
//			if (SWT.F5 == e.keyCode) {
//				firmarArchivos();
//			}
//			if (((e.stateMask & SWT.CTRL) != 0) && (e.keyCode == 'o')) {
//				abrirArchivo();
//			}
//			if (((e.stateMask & SWT.CTRL) != 0) && (e.keyCode == 'd')) {
//				abrirDirectorio();
//			}
//			if (SWT.F2 == e.keyCode) {
//				// hotkey para pruebas
//
//			}
//		}
//	}

}