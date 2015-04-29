package flex.aponwao.gui.sections.preferences.windows;

import java.util.Map;
import java.util.logging.Logger;

import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import java.util.TreeMap;
import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.sections.preferences.helpers.TimeStampPreferences;

/**
 * @author zylk.net
 */

public class ValidatePreferences extends FieldEditorPreferencePage {

        private static Logger	logger	= Logger.getLogger(ValidatePreferences.class.getName());

        //OJO.. Modificacion Yessica
        private Composite compositeMain = null;
	private org.eclipse.swt.widgets.List visualList = null;
	private Combo comboDefault = null;

	private Map<String, TimeStampPreferences>  tempMap = null;
	private String tempDefault = null;

        private Button checkTS = null;
        private Button checkOCSP = null;
        private Button checkOCSPVerifyOnError = null;
//        private Combo comboOCSP = null;
//        private Map<String, String>  tempOCSP = null;
        //////////////////////////////////////

	/**
	 * @param messages
	 */
	public ValidatePreferences() {
		// Use the "flat" layout
		super(FLAT);

                //OJO.. Modificacion Yessica
                // inicializar la lista con los valores del fichero
		tempMap = new TreeMap<String, TimeStampPreferences>();
		for (String name: PreferencesHelper.getTimestampPreferencesNew().keySet()) {
			tempMap.put(name, PreferencesHelper.getTimestampPreferencesNew().get(name));
		}

//                tempOCSP = new TreeMap<String, String>();
//                tempOCSP.put( "NOT_CERTIFIED", ""+PdfSignatureAppearance.NOT_CERTIFIED );
//                tempOCSP.put( "NO_CHANGES_ALLOWED", ""+PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED );
//                tempOCSP.put( "FORM_FILLING", ""+PdfSignatureAppearance.CERTIFIED_FORM_FILLING );
//                tempOCSP.put( "FORM_FILLING_AND_ANNOTATIONS",  ""+PdfSignatureAppearance.CERTIFIED_FORM_FILLING_AND_ANNOTATIONS );

		tempDefault = PreferencesHelper.getPreferences().getString(PreferencesHelper.VALIDATE_TS_TSA);
                //////////////////////////////////////
	}

	/**
	 * Creates the field editors
	 */
	@Override
	protected void createFieldEditors() {
				
	}

        //OJO... Modificacion Yessica
        @Override
	protected Control createContents(Composite parent) {

		// composite que contiene todos los elementos de la pantalla
		this.compositeMain = new Composite(parent, SWT.NONE);
		GridLayout gridLayoutPrincipal = new GridLayout();
		gridLayoutPrincipal.numColumns = 1;
		gridLayoutPrincipal.verticalSpacing = 10;
		gridLayoutPrincipal.marginBottom = 50;
		this.compositeMain.setLayout(gridLayoutPrincipal);

		GridData gdPrincipal = new GridData();
		gdPrincipal.horizontalAlignment = GridData.FILL;
		gdPrincipal.verticalAlignment = GridData.FILL;
		gdPrincipal.grabExcessHorizontalSpace = true;
		gdPrincipal.grabExcessVerticalSpace = true;
		this.compositeMain.setLayoutData(gdPrincipal);

		// composite con la lista y los 3 botones
		createListArea();

		// combo composite
		createDefaultArea();

		return this.compositeMain;
	}

        private void createListArea() {

		Composite compositeLista = new Composite(this.compositeMain, SWT.NONE);
		GridLayout gridLayoutLista = new GridLayout();
		gridLayoutLista.numColumns = 2;
		compositeLista.setLayout(gridLayoutLista);
		GridData gdListComposite = new GridData();
		gdListComposite.horizontalAlignment = GridData.FILL;
		gdListComposite.verticalAlignment = GridData.FILL;
		gdListComposite.grabExcessHorizontalSpace = true;
		gdListComposite.grabExcessVerticalSpace = true;
		compositeLista.setLayoutData(gdListComposite);

		this.visualList = new org.eclipse.swt.widgets.List(compositeLista, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		this.visualList.addKeyListener(new SupButtonKeyListener());

		reloadVisualList();

		GridData gdList = new GridData();
		gdList.verticalSpan = 3;
		gdList.horizontalAlignment = GridData.FILL;
		gdList.verticalAlignment = GridData.FILL;
		gdList.grabExcessHorizontalSpace = true;
		gdList.grabExcessVerticalSpace = true;
		this.visualList.setLayoutData(gdList);

		Button buttonAdd = new Button(compositeLista, SWT.NONE);
		GridData gdAdd = new GridData();
		gdAdd.horizontalAlignment = GridData.FILL;
		buttonAdd.setLayoutData(gdAdd);
		buttonAdd.setText(LanguageResource.getLanguage().getString("button.add"));
		buttonAdd.setImage(new Image(this.compositeMain.getDisplay(), ImagesResource.ADD_IMG));
		buttonAdd.addSelectionListener(new ButtonAddListener());

		Button buttonModificar = new Button(compositeLista, SWT.NONE);
		GridData gdMod = new GridData();
		gdMod.horizontalAlignment = GridData.FILL;
		buttonModificar.setLayoutData(gdMod);
		buttonModificar.setText(LanguageResource.getLanguage().getString("button.modify"));
		buttonModificar.setImage(new Image(this.compositeMain.getDisplay(), ImagesResource.EDIT_IMG));
		buttonModificar.addSelectionListener(new ButtonModifyListener());

		Button buttonRemove = new Button(compositeLista, SWT.NONE);
		GridData gdRemove = new GridData();
		gdRemove.horizontalAlignment = GridData.FILL;
		gdRemove.verticalAlignment = GridData.BEGINNING;
		buttonRemove.setLayoutData(gdRemove);
		buttonRemove.setText(LanguageResource.getLanguage().getString("button.remove"));
		buttonRemove.setImage(new Image(this.compositeMain.getDisplay(), ImagesResource.REMOVE_IMG));
		buttonRemove.addSelectionListener(new ButtonRemoveListener());

	}

        private void reloadVisualList() {
		// inicializar lista
		visualList.removeAll();
		for (String name : tempMap.keySet()) {
			visualList.add(name);
		}
	}

	private void createDefaultArea() {

                checkTS = new Button(this.compositeMain, SWT.CHECK);
		checkTS.setText(LanguageResource.getLanguage().getString("preferences.validate.ts.enable"));
		GridData gdTS = new GridData();
		gdTS.horizontalSpan = 4;
		gdTS.grabExcessHorizontalSpace = true;
		checkTS.setLayoutData(gdTS);
		checkTS.setSelection(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.VALIDATE_TS_ENABLE));
                checkTS.addSelectionListener(new CheckTimestampListener());

                Label labelDefaultAreaDesc = new Label(this.compositeMain, SWT.NONE);
		labelDefaultAreaDesc.setText(LanguageResource.getLanguage().getString("preferences.validate.ts.tsa"));
		comboDefault = new Combo(this.compositeMain, SWT.NONE | SWT.READ_ONLY);
		comboDefault.addSelectionListener(new ComboDefaultChangeListener());
		reloadComboDefault();
		comboDefault.setText(PreferencesHelper.getPreferences().getString(PreferencesHelper.VALIDATE_OCSP_ENABLE));

                if (checkTS.getSelection()) comboDefault.setEnabled(true);
                else comboDefault.setEnabled(false);

                checkOCSP = new Button(this.compositeMain, SWT.CHECK);
		checkOCSP.setText(LanguageResource.getLanguage().getString("preferences.validate.ocsp.enable"));
		GridData gdOCSP = new GridData();
		gdOCSP.horizontalSpan = 4;
		gdOCSP.grabExcessHorizontalSpace = true;
		checkOCSP.setLayoutData(gdOCSP);
		checkOCSP.setSelection(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.VALIDATE_OCSP_ENABLE));

                checkOCSPVerifyOnError = new Button(this.compositeMain, SWT.CHECK);
		checkOCSPVerifyOnError.setText(LanguageResource.getLanguage().getString("preferences.validate.ocsp.verify.on.error"));
		GridData gdOCSPVerifyOnError = new GridData();
		gdOCSP.horizontalSpan = 4;
		gdOCSP.grabExcessHorizontalSpace = true;
		checkOCSPVerifyOnError.setLayoutData(gdOCSPVerifyOnError);
		checkOCSPVerifyOnError.setSelection(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.VALIDATE_OCSP_VERIFY_ON_ERROR));
//                Label labelComboOCSP = new Label(this.compositeMain, SWT.NONE);
//		labelComboOCSP.setText(LanguageResource.getLanguage().getString("preferences.validate.certified"));
//		comboOCSP = new Combo(this.compositeMain, SWT.NONE | SWT.READ_ONLY);
//		comboOCSP.addSelectionListener(new ComboDefaultChangeListener());
//                for (String name : tempOCSP.keySet()) {
//			comboOCSP.add(name);
//		}
//		comboOCSP.setText(PreferencesHelper.getPreferences().getString(PreferencesHelper.VALIDATE_OCSP_ENABLE));
	}

	private void reloadComboDefault() {

		// cargar combo
		comboDefault.removeAll();
		for (String name : tempMap.keySet()) {
			comboDefault.add(name);
		}
		comboDefault.getParent().layout();

		comboDefault.setText(tempDefault);
		if (comboDefault.getText().equals("")) {
			if (comboDefault.getItemCount() != 0 ) {
				comboDefault.select(0);
				tempDefault = comboDefault.getText();
			}
		}
	}


	private void removeTableFile() {

		tempMap.remove(visualList.getSelection()[0]);
		reloadVisualList();
		reloadComboDefault();
	}

	class ComboDefaultChangeListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {

			tempDefault = comboDefault.getText();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

	class ButtonAddListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {

			TimestampUpdateDialog timestampStoreDialog = new TimestampUpdateDialog(compositeMain.getShell(), tempMap);
			timestampStoreDialog.open();

			reloadVisualList();
			reloadComboDefault();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

	class ButtonModifyListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {
                        
			TimestampUpdateDialog timestampStoreDialog = new TimestampUpdateDialog(compositeMain.getShell(), tempMap,
					visualList.getSelection()[0]);
			timestampStoreDialog.open();

			reloadVisualList();
			reloadComboDefault();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

	class ButtonRemoveListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {

			removeTableFile();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

	class SupButtonKeyListener implements KeyListener {

		public void keyPressed(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) {
			if (SWT.DEL == e.character) {
				removeTableFile();
			}
		}
	}

        class CheckTimestampListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {
			if (!checkTS.getSelection())
                            comboDefault.setEnabled(false);
                        else comboDefault.setEnabled(true);
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

	private void savePreferences() {

		if (comboDefault != null) {
			PreferencesHelper.saveTimestampPreferences(tempMap);
			PreferencesHelper.getPreferences().setValue(PreferencesHelper.VALIDATE_TS_TSA, comboDefault.getText());
//                        PreferencesHelper.getPreferences().setValue(PreferencesHelper.VALIDATE_CERTIFIED, comboOCSP.getText());
                        PreferencesHelper.getPreferences().setValue(PreferencesHelper.VALIDATE_TS_ENABLE, checkTS.getSelection());
                        PreferencesHelper.getPreferences().setValue(PreferencesHelper.VALIDATE_OCSP_ENABLE, checkOCSP.getSelection());
                        PreferencesHelper.getPreferences().setValue(PreferencesHelper.VALIDATE_OCSP_VERIFY_ON_ERROR, checkOCSPVerifyOnError.getSelection());
		}
	}

	@Override
	protected void performApply() {

		savePreferences();
		super.performApply();
	}

	@Override
	public boolean performOk() {

		savePreferences();
		return super.performOk();
	}
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
}