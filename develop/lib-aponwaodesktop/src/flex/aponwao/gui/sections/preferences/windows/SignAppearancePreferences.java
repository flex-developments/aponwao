package flex.aponwao.gui.sections.preferences.windows;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.preferences.helpers.ImageSignPreferences;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class SignAppearancePreferences extends FieldEditorPreferencePage {

	private static Logger logger = Logger.getLogger(SignAppearancePreferences.class.getName());

	private Composite compositeMain = null;
	private org.eclipse.swt.widgets.List visualList = null;
	private Combo comboDefault = null;
	
	private Map<String, ImageSignPreferences>  tempMap = null;
	private String tempDefault = null;
	

	/**
	 * @param messages
	 */
	public SignAppearancePreferences() {
		
		// Use the "flat" layout
		super(FLAT);
		
		// inicializar la lista con los valores del fichero
		tempMap = new TreeMap<String, ImageSignPreferences>();
		for (String name: PreferencesHelper.getImagePreferences().keySet()) {
			tempMap.put(name, PreferencesHelper.getImagePreferences().get(name) );
		}
		
		tempDefault = PreferencesHelper.getPreferences().getString(PreferencesHelper.IMAGE_DISPOSITIVE);
	}

	@Override
	protected void createFieldEditors() {
	}

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

		Label labelDefaultAreaTitle = new Label(this.compositeMain, SWT.NONE);
		labelDefaultAreaTitle.setText(LanguageResource.getLanguage().getString("preferences.image.default_title"));
		labelDefaultAreaTitle.setFont(new Font(compositeMain.getDisplay(), Display.getDefault().getSystemFont().getFontData()[0].getName(),
				Display.getDefault().getSystemFont().getFontData()[0].getHeight(), SWT.BOLD));
		Label labelDefaultAreaDesc = new Label(this.compositeMain, SWT.NONE);
		labelDefaultAreaDesc.setText(LanguageResource.getLanguage().getString("preferences.image.default_image"));

		comboDefault = new Combo(this.compositeMain, SWT.NONE | SWT.READ_ONLY);
		comboDefault.addSelectionListener(new ComboDefaultChangeListener());
		reloadComboDefault();	
		
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

		// TODO validar not selected (solo una borrable???)
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

			SignAppearanceUpdateDialog imageDialog = new SignAppearanceUpdateDialog(compositeMain.getShell(), tempMap);
			imageDialog.open();
			
			reloadVisualList();
			reloadComboDefault();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}
	
	class ButtonModifyListener implements SelectionListener {
		
		public void widgetSelected(SelectionEvent event) {

			// TODO validar not selected
			SignAppearanceUpdateDialog imageDialog = new SignAppearanceUpdateDialog(compositeMain.getShell(), tempMap,
					visualList.getSelection()[0]);
			imageDialog.open();
			
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
	
	private void savePreferences() {

		if (comboDefault != null) {
			
			PreferencesHelper.saveImagePreferences(tempMap);
			PreferencesHelper.getPreferences().setValue(PreferencesHelper.IMAGE_DISPOSITIVE, comboDefault.getText());
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

}






