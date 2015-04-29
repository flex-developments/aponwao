/*
 * # Copyright 2008 zylk.net # # This file is part of Sinadura. # # Sinadura is free software: you can redistribute it
 * and/or modify # it under the terms of the GNU General Public License as published by # the Free Software Foundation,
 * either version 2 of the License, or # (at your option) any later version. # # Sinadura is distributed in the hope
 * that it will be useful, # but WITHOUT ANY WARRANTY; without even the implied warranty of # MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the # GNU General Public License for more details. # # You should have received a copy
 * of the GNU General Public License # along with Sinadura. If not, see <http://www.gnu.org/licenses/>. [^] # # See
 * COPYRIGHT.txt for copyright notices and details. #
 */
package flex.aponwao.gui.sections.documentation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.DesktopHelper;
import flex.aponwao.gui.application.ResourceHelper;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * @author zylk.net
 */
public class AboutUsDialog extends Dialog {
	private static final Logger		logger				= Logger.getLogger(AboutUsDialog.class.getName());
	private static final String	VERSION	= "v2.0.0";
	private static final String	IMAGES_PATH			= ResourceHelper.getRESOURCES_PATH() + File.separatorChar + "images";
	private static final String	CREDITS_IMG_FILE	= "credits.png";
	private static final String	CREDITS_IMG_PATH	=  IMAGES_PATH + File.separatorChar + CREDITS_IMG_FILE;

	private Label				labelImage			= null;
	private Shell				sShell				= null;
	private Button				buttonClose			= null;
	private Button				buttonCredits		= null;
	private StyledText			textTitle			= null;
	private StyledText			textDesc			= null;
	private StyledText			textCopyRight		= null;

	/**
	 * @param parent
	 */
	public AboutUsDialog(Shell parent) {
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

		this.sShell.setText(LanguageResource.getLanguage().getString("about.windowtitle"));
		this.sShell.setSize(new Point(425, 315));
		this.sShell.setImage(new Image(this.sShell.getDisplay(), ImagesResource.APLICATION_LOGO));
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.verticalSpacing = 10;
		gridLayout.marginTop = 10;
		this.sShell.setLayout(gridLayout);

		this.labelImage = new Label(sShell, SWT.NONE);
		this.labelImage.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(ImagesResource.APLICATION_FULL_IMG);
			Image imageAplication = new Image(sShell.getDisplay(), inputStream);
			this.labelImage.setImage(imageAplication);
			
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "", e);
		}

		String textoTitle = "Aponwao " + VERSION;
		String textoDesc = LanguageResource.getLanguage().getString("about.descripcion");

		String textoLicencia = "\n";
		for (int i = 0; i < (textoDesc.length() / 2); i++) {
			textoLicencia = textoLicencia + " ";
		}
		textoLicencia = textoLicencia + LanguageResource.getLanguage().getString("about.licencia");
		textoDesc = textoDesc + textoLicencia;

		String textoCopyRight = LanguageResource.getLanguage().getString("about.copyright");
		String textoURL = LanguageResource.getLanguage().getString("about.url");
		this.textTitle = new StyledText(this.sShell, SWT.NONE);
		this.textTitle.setText(textoTitle);
		this.textTitle.setEditable(false);
		this.textTitle.setBackground(this.sShell.getBackground());
		this.textDesc = new StyledText(this.sShell, SWT.NONE);
		this.textDesc.setText(textoDesc);
		this.textDesc.setEditable(false);
		this.textDesc.setBackground(this.sShell.getBackground());
		this.textCopyRight = new StyledText(this.sShell, SWT.NONE);
		this.textCopyRight.setText(textoCopyRight);
		this.textCopyRight.setEditable(false);
		this.textCopyRight.setBackground(this.sShell.getBackground());

		StyleRange stylerangeTxtTitle = new StyleRange();
		stylerangeTxtTitle.start = 0;
		stylerangeTxtTitle.length = textoTitle.length();
		stylerangeTxtTitle.font = new Font(this.textTitle.getDisplay(), "", 16, SWT.BOLD);
		this.textTitle.setStyleRange(stylerangeTxtTitle);

		StyleRange stylerangeTxtDesc = new StyleRange();
		stylerangeTxtDesc.start = 0;
		stylerangeTxtDesc.length = textoDesc.length();
		stylerangeTxtDesc.font = new Font(this.textDesc.getDisplay(), "", 8, SWT.CENTER);
		stylerangeTxtDesc.foreground = new Color(this.textDesc.getDisplay(), new RGB(100, 100, 100));
		this.textDesc.setStyleRange(stylerangeTxtDesc);

		StyleRange stylerangeTxtCopyRight = new StyleRange();
		stylerangeTxtCopyRight.start = 0;
		stylerangeTxtCopyRight.length = textoCopyRight.length();
		stylerangeTxtCopyRight.font = new Font(this.textCopyRight.getDisplay(), "", 9, SWT.NONE);
		stylerangeTxtCopyRight.foreground = new Color(this.textCopyRight.getDisplay(), new RGB(80, 80, 80));
		this.textCopyRight.setStyleRange(stylerangeTxtCopyRight);

		Link link = new Link(sShell, SWT.NONE);

		String text = "<a>" + textoURL + "</a>";
		link.setText(text);
		link.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				DesktopHelper.openDefaultBrowser(LanguageResource.getLanguage().getString("documentation.uri"));
			}
		});

		link.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		this.textTitle.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		this.textDesc.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		this.textCopyRight.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));

		// buttons composite
		Composite composite = new Composite(this.sShell, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		GridLayout gridLayoutButtons = new GridLayout();
		gridLayoutButtons.numColumns = 2;
		gridLayoutButtons.horizontalSpacing = 100;
		composite.setLayout(gridLayoutButtons);

		// this.textURL.addMouseListener(new URLClickListener());

		this.buttonCredits = new Button(composite, SWT.NONE);
		this.buttonCredits.setText(LanguageResource.getLanguage().getString("about.creditsbutton"));
		this.buttonCredits.setImage(new Image(this.sShell.getDisplay(), CREDITS_IMG_PATH));
		this.buttonCredits.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		this.buttonCredits.addSelectionListener(new ButtonCreditsListener());

		this.buttonClose = new Button(composite, SWT.NONE);
		this.buttonClose.setText(LanguageResource.getLanguage().getString("button.back"));
		this.buttonClose.setImage(new Image(this.sShell.getDisplay(), ImagesResource.BACK_IMG));
		this.buttonClose.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		this.buttonClose.addSelectionListener(new BotonCloseListener());

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

	class BotonCloseListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {

			sShell.dispose();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

	class ButtonCreditsListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
			CreditsDialog creditsDialog = new CreditsDialog(sShell);
			creditsDialog.open();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

}