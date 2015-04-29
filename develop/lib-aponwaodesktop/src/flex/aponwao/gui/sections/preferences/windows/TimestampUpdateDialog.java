/*
 * # Copyright 2008 zylk.net # # This file is part of Sinadura. # # Sinadura is free software: you can redistribute it
 * and/or modify # it under the terms of the GNU General Public License as published by # the Free Software Foundation,
 * either version 2 of the License, or # (at your option) any later version. # # Sinadura is distributed in the hope
 * that it will be useful, # but WITHOUT ANY WARRANTY; without even the implied warranty of # MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the # GNU General Public License for more details. # # You should have received a copy
 * of the GNU General Public License # along with Sinadura. If not, see <http://www.gnu.org/licenses/>. [^] # # See
 * COPYRIGHT.txt for copyright notices and details. #
 */
package flex.aponwao.gui.sections.preferences.windows;

import java.util.Map;
import java.util.logging.Logger;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.global.events.BotonCancelarListener;
import flex.aponwao.gui.sections.global.windows.InfoDialog;

import flex.aponwao.gui.sections.preferences.helpers.TimeStampPreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author zylk.net
 */
public class TimestampUpdateDialog extends Dialog {

	private static Logger	logger	= Logger.getLogger(TimestampUpdateDialog.class.getName());

	private Shell				sShell				= null;

	private Text				textName			= null;
	private Text				textURL			= null;
        private Text				textUser			= null;
        private Text				textPassword			= null;

	private Map<String, TimeStampPreferences>  tempMap = null;
	private String 			selectedName = null;


	public TimestampUpdateDialog(Shell parent, Map<String, TimeStampPreferences> map) {

		super(parent);
		this.tempMap = map;

	}

	public TimestampUpdateDialog(Shell parent, Map<String, TimeStampPreferences> map, String selectedName) {
		super(parent);
		this.tempMap = map;
		this.selectedName = selectedName;
	}


	public void open() {

		Shell parent = getParent();

		sShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		sShell.setText(LanguageResource.getLanguage().getString("preferences.timestamp.dialog.title"));
		sShell.setImage(new Image(sShell.getDisplay(), ImagesResource.APLICATION_LOGO));
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.verticalSpacing = 10;
		gridLayout.horizontalSpacing = 10;
		gridLayout.marginTop = 5;
		sShell.setLayout(gridLayout);

		createCompositeFields();
		createCompositeButtons();

		sShell.pack();

		// to center the shell on the screen
		Monitor primary = this.sShell.getDisplay().getPrimaryMonitor();
                Rectangle bounds = primary.getBounds();
                Rectangle rect = this.sShell.getBounds();
                int x = bounds.x + (bounds.width - rect.width) / 2;
                int y = bounds.y + (bounds.height - rect.height) / 3;
                this.sShell.setLocation(x, y);

		sShell.open();

		Display display = parent.getDisplay();

		while (!sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

	}


	private void createCompositeFields() {

		Label labelName = new Label(sShell, SWT.NONE);
		labelName.setText(LanguageResource.getLanguage().getString("preferences.timestamp.dialog.name"));
		textName = new Text(sShell, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.minimumWidth = 400;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		textName.setLayoutData(gd);

                Label labelURL = new Label(sShell, SWT.NONE);
		labelURL.setText(LanguageResource.getLanguage().getString("preferences.timestamp.dialog.path"));
		textURL = new Text(sShell, SWT.BORDER);
                GridData gdURL = new GridData();
		gdURL.horizontalSpan = 2;
		gdURL.minimumWidth = 400;
		gdURL.grabExcessHorizontalSpace = true;
		gdURL.horizontalAlignment = GridData.FILL_HORIZONTAL;
		textURL.setLayoutData(gdURL);
                
                Label labelUser = new Label(sShell, SWT.NONE);
		labelUser.setText(LanguageResource.getLanguage().getString("preferences.timestamp.dialog.user"));
		textUser = new Text(sShell, SWT.BORDER);
		GridData gduser = new GridData();
		gduser.horizontalSpan = 2;
		gduser.minimumWidth = 400;
		gduser.grabExcessHorizontalSpace = true;
		gduser.horizontalAlignment = GridData.FILL;
		textUser.setLayoutData(gduser);
                
                Label labelPassword = new Label(sShell, SWT.NONE);
		labelPassword.setText(LanguageResource.getLanguage().getString("preferences.timestamp.dialog.password"));
		textPassword = new Text(sShell, SWT.BORDER);
		GridData gdPassword = new GridData();
		gdPassword.horizontalSpan = 2;
		gdPassword.minimumWidth = 400;
		gdPassword.grabExcessHorizontalSpace = true;
		gdPassword.horizontalAlignment = GridData.FILL;
		textPassword.setLayoutData(gdPassword);

		if (selectedName != null) {
			textName.setText(selectedName);
			textURL.setText(tempMap.get(selectedName).getUrl());
                        if(tempMap.get(selectedName).getUser()!=null) textUser.setText(tempMap.get(selectedName).getUser());
                        if(tempMap.get(selectedName).getPassword()!=null) textPassword.setText(tempMap.get(selectedName).getPassword());
		}

	}


	private void createCompositeButtons() {

		Composite compositeButtons = new Composite(sShell, SWT.NONE);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.horizontalSpan = 4;
		compositeButtons.setLayoutData(gd);

		GridLayout gridLayout5 = new GridLayout();
		gridLayout5.numColumns = 2;
		gridLayout5.horizontalSpacing = 50;
		compositeButtons.setLayout(gridLayout5);

		Button buttonAceptar = new Button(compositeButtons, SWT.NONE);
		buttonAceptar.setText(LanguageResource.getLanguage().getString("button.accept"));
		buttonAceptar.setImage(new Image(this.sShell.getDisplay(), ImagesResource.ACEPTAR_IMG));
		buttonAceptar.setLayoutData(new GridData(GridData.END));
		buttonAceptar.addSelectionListener(new ButtonOkListener());

		Button buttonCancel = new Button(compositeButtons, SWT.NONE);
		buttonCancel.setText(LanguageResource.getLanguage().getString("button.cancel"));
		buttonCancel.setImage(new Image(this.sShell.getDisplay(), ImagesResource.CANCEL_IMG));
		buttonCancel.setLayoutData(new GridData(GridData.END));
		buttonCancel.addSelectionListener(new BotonCancelarListener());

	}


	class ButtonOkListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {


			if (!textName.getText().equals("") && !textURL.getText().equals("")) {
                                TimeStampPreferences t = new TimeStampPreferences();
                                
				if (selectedName == null) {
                                    t.setName(textName.getText());
                                    t.setUrl(textURL.getText());
                                    t.setUser(textUser.getText());
                                    t.setPassword(textPassword.getText());

                                    tempMap.put(t.getName(), t);
                                } else {
                                    t.setName(textName.getText());
                                    t.setUrl(textURL.getText());
                                    t.setUser(textUser.getText());
                                    t.setPassword(textPassword.getText());
                                    
                                    tempMap.remove(selectedName);
                                    tempMap.put(t.getName(), t);
				}

				sShell.dispose();

			} else {

				InfoDialog id = new InfoDialog(sShell);
				id.open(LanguageResource.getLanguage().getString("info.campos_obligatorios"));
			}

		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

}

