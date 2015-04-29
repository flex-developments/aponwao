/*
# Copyright 2008 zylk.net
#
# This file is part of Sinadura.
#
# Sinadura is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 2 of the License, or
# (at your option) any later version.
#
# Sinadura is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with Sinadura.  If not, see <http://www.gnu.org/licenses/>. [^]
#
# See COPYRIGHT.txt for copyright notices and details.
#
*/
package flex.aponwao.gui.sections.preferences.windows;

import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.ResourceHelper;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;

import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author zylk.net
 */
public class GeneralPreferences extends FieldEditorPreferencePage {
	private static final Logger logger = Logger.getLogger(GeneralPreferences.class.getName());
	private static final String DISPLAY_LANGUAGE	= "EN";

        //OJO... Modificacion Yessica y Felix
        private Composite compositeMain = null;
        private Combo lenguage = null;
        private String[][] comboFields;
        private Text pdfViewer = null;
        private Text saveExtension = null;
        private Button checkVirtualKeyboard = null;
        private Button checkValidateOverwriting = null;
        /////////////////////////////////////

	/**
	 * @param messages
	 */
	public GeneralPreferences() {
		// Use the "flat" layout
		super(FLAT);
	}

	/**
	 * Creates the field editors
	 */
	@Override
	protected void createFieldEditors() {

	}

        //OJO... Modificacion Yessica y Felix
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
            StringTokenizer stringTokenizer = new StringTokenizer(ResourceHelper.getConfiguration().getProperty("idiomas.soportados"), ",");

            int numIdiomas = stringTokenizer.countTokens();

            comboFields = new String[numIdiomas][2];
            String[] comboLeng = new String[numIdiomas];

            int index = 0;
            while (stringTokenizer.hasMoreElements()) {
                    String idioma_value = (String) stringTokenizer.nextElement();
                    StringTokenizer tokenizerIdiomaValue = new StringTokenizer(idioma_value, PreferencesHelper.TOKEN_LOCALE);
                    String idioma = (String) tokenizerIdiomaValue.nextElement();
                    String pais = (String) tokenizerIdiomaValue.nextElement();

                    String idioma_label = new Locale(idioma, pais).getDisplayLanguage(new Locale(idioma, pais)) + " - "
                                    + new Locale(idioma, pais).getDisplayLanguage(new Locale(DISPLAY_LANGUAGE));

                    String[] campo = { idioma_label.toLowerCase(), idioma_value };
                    comboLeng[index] = idioma_label.toLowerCase();
                    comboFields[index] = campo;
                    index++;
            }

            Label label1 = new Label(this.compositeMain, SWT.NONE);
            label1.setText(LanguageResource.getLanguage().getString("preferences.main.idiom"));
            
            //Lenguaje
            lenguage = new Combo(this.compositeMain, SWT.DROP_DOWN | SWT.READ_ONLY);
            GridData gdlenguage = new GridData();
            gdlenguage.horizontalSpan = 3;
            gdlenguage.horizontalAlignment = SWT.FILL;
            gdlenguage.grabExcessHorizontalSpace = true;
            lenguage.setLayoutData(gdlenguage);
            lenguage.setItems(comboLeng);
            lenguage.select(PreferencesHelper.getPreferences().getInt(PreferencesHelper.IDIOMA_INDEX));
            
            //Sufijo
            Label label5 = new Label(this.compositeMain, SWT.NONE);
            label5.setText(LanguageResource.getLanguage().getString("preferences.main.extension"));

            saveExtension = new Text(this.compositeMain, SWT.BORDER);
            GridData gdsaveExtension = new GridData();
            gdsaveExtension.horizontalSpan = 3;
            gdsaveExtension.horizontalAlignment = SWT.FILL;
            gdsaveExtension.grabExcessHorizontalSpace = true;
            saveExtension.setLayoutData(gdsaveExtension);
            saveExtension.setText(PreferencesHelper.getPreferences().getString(PreferencesHelper.SAVE_EXTENSION));
            
            //Visor de documentos pdf
            Label label6 = new Label(this.compositeMain, SWT.NONE);
            label6.setText(LanguageResource.getLanguage().getString("preferences.main.pdf.viewer"));

            pdfViewer = new Text(this.compositeMain, SWT.BORDER);
            GridData gdpdfViewer = new GridData();
            gdpdfViewer.horizontalSpan = 3;
            gdpdfViewer.horizontalAlignment = SWT.FILL;
            gdpdfViewer.grabExcessHorizontalSpace = true;
            pdfViewer.setLayoutData(gdpdfViewer);
            pdfViewer.setText(PreferencesHelper.getPreferences().getString(PreferencesHelper.PDF_VIEWER));
            
            //Teclado Virtual
            checkVirtualKeyboard = new Button(this.compositeMain,SWT.CHECK);
            checkVirtualKeyboard.setText(LanguageResource.getLanguage().getString("preferences.main.virtualkeyboard.enabled"));
            GridData gdcheckVirtualKeyboard = new GridData();
            gdcheckVirtualKeyboard.horizontalSpan = 4;
            gdcheckVirtualKeyboard.grabExcessHorizontalSpace = true;
            checkVirtualKeyboard.setLayoutData(gdcheckVirtualKeyboard);
            checkVirtualKeyboard.setSelection(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.VIRTUAL_KEYBOARD));
            
            //Sobreescritura
            checkValidateOverwriting = new Button(this.compositeMain,SWT.CHECK);
            checkValidateOverwriting.setText(LanguageResource.getLanguage().getString("preferences.main.validate.overwriting"));
            GridData gdcheckValidateOverwriting = new GridData();
            gdcheckValidateOverwriting.horizontalSpan = 4;
            gdcheckValidateOverwriting.grabExcessHorizontalSpace = true;
            checkValidateOverwriting.setLayoutData(gdcheckValidateOverwriting);
            checkValidateOverwriting.setSelection(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APLICATION_VALIDATE_OVERWRITING));
        }

        private void savePreferences() {
            if (saveExtension != null) {
                PreferencesHelper.getPreferences().setValue(PreferencesHelper.IDIOMA_INDEX, lenguage.getSelectionIndex());
                PreferencesHelper.getPreferences().setValue(PreferencesHelper.IDIOMA, comboFields[lenguage.getSelectionIndex()][1]);
                PreferencesHelper.getPreferences().setValue(PreferencesHelper.SAVE_EXTENSION, saveExtension.getText());
                PreferencesHelper.getPreferences().setValue(PreferencesHelper.PDF_VIEWER, pdfViewer.getText());
                PreferencesHelper.getPreferences().setValue(PreferencesHelper.VIRTUAL_KEYBOARD, checkVirtualKeyboard.getSelection());
                PreferencesHelper.getPreferences().setValue(PreferencesHelper.APLICATION_VALIDATE_OVERWRITING, checkValidateOverwriting.getSelection());
            }
	}
        //////////////////////////////////////////////////////////////
        
	@Override
	protected void performApply() {
                
		savePreferences();

		super.performApply();
	}
 	
    	@Override
    	public boolean performOk() {

//    		boolean ok = false;
//
//    		if (saveExtension != null && (saveExtension.getText() == null || saveExtension.getText().equals(""))) {
//
//    			logger.severe(LanguageResource.getLanguage().getString("error.save_extension.empty"));
//    			InfoDialog id = new InfoDialog(this.getShell());
//    			id.open(LanguageResource.getLanguage().getString("error.save_extension.empty"));
//                        savePreferences();
//    		} else {
//    			ok = super.performOk();
//    			LanguageResource.reloadLanguage();
//                        savePreferences();
//    		}
//
//                return ok;
                boolean ok = super.performOk();
                LanguageResource.reloadLanguage();
                savePreferences();
                return ok;

    	}
}