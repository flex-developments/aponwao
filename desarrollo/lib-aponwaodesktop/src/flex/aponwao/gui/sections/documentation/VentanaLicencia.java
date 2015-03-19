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
package flex.aponwao.gui.sections.documentation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;

import flex.aponwao.gui.application.ResourceHelper;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


/**
 * @author zylk.net
 */
public class VentanaLicencia extends Dialog {
	private static final Logger	logger	= Logger.getLogger(VentanaLicencia.class.getName());
	private Shell		sShell			= null;
	private Text	textDesc		= null;
	private Button		bottonAceptar	= null;

	/**
	 * @param parent
	 */
	public VentanaLicencia(Shell parent) {
		super(parent);
	}

	/**
	 * @param storeName
	 * @param alias
	 * @return
	 */
	@Override
	public int open() {

		Shell parent = getParentShell();

		this.sShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

		this.sShell.setText(LanguageResource.getLanguage().getString("about.licencia"));
		this.sShell.setSize(new Point(600, 500));
		this.sShell.setImage(new Image(this.sShell.getDisplay(), ImagesResource.APLICATION_LOGO));
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.verticalSpacing = 10;
		this.sShell.setLayout(gridLayout);

		String textoDesc = "";

		try {
	        BufferedReader in = new BufferedReader(new FileReader(ResourceHelper.getRootPath() + "COPYRIGHT.txt"));
	        String str;
	        while ((str = in.readLine()) != null) {
	            textoDesc = textoDesc + str + "\n";
	        }
	        in.close();
	        
	    } catch (IOException e) {
	    	
	    	logger.log(Level.SEVERE, "", e);
		}


		this.textDesc = new Text(this.sShell, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER | SWT.WRAP);
		this.textDesc.setText(textoDesc);

		this.textDesc.setEditable(false);

		this.textDesc.setLayoutData(new GridData(GridData.FILL_BOTH));

		this.bottonAceptar = new Button(sShell, SWT.NONE);
		this.bottonAceptar.setImage(new Image(this.sShell.getDisplay(), ImagesResource.BACK_IMG));
		this.bottonAceptar.setText(LanguageResource.getLanguage().getString("button.back"));
		this.bottonAceptar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));

		this.bottonAceptar.addSelectionListener(new BotonAceptarListener());
		this.bottonAceptar.setFocus();

		// to center the shell on the screen
		Monitor primary = this.sShell.getDisplay().getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = this.sShell.getBounds();
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 3;
	    this.sShell.setLocation(x, y);
		
		this.sShell.open();
		
		Display display = parent.getDisplay();
		
		while (!this.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		return (0);
	}

	/**
	 *
	 */

	class BotonAceptarListener implements SelectionListener {
		
		public void widgetSelected(SelectionEvent event) {

			sShell.dispose();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

}
