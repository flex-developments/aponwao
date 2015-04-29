package flex.aponwao.gui.sections.global.windows;


import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * @author zylk.net
 */
public class InfoDialog extends Dialog {

	private Shell	sShell			= null;
	private Button	bottonAceptar	= null;

	/**
	 * @param parent
	 */
	public InfoDialog(Shell parent) {
		super(parent);
	}

	/**
	 * @param storeName
	 * @param mensaje
	 * @return
	 */
	public boolean open(String mensaje) {

		Shell parent = getParentShell();

		this.sShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.sShell.setImage(new Image(sShell.getDisplay(), ImagesResource.APLICATION_LOGO));
		this.sShell.setText(LanguageResource.getLanguage().getString("info.dialog.title"));
		
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.verticalSpacing = 10;
		gridLayout.marginTop = 15;
		this.sShell.setLayout(gridLayout);

		Label textoLabel = new Label(this.sShell, SWT.WRAP | SWT.NONE);
		textoLabel.setText(mensaje);
		
		GridData gdTexto = new GridData();
		gdTexto.widthHint = 600;
		textoLabel.setLayoutData(gdTexto);
		
		this.bottonAceptar = new Button(this.sShell, SWT.NONE);
		this.bottonAceptar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		this.bottonAceptar.setText(LanguageResource.getLanguage().getString("button.accept"));
		this.bottonAceptar.setImage(new Image(this.sShell.getDisplay(), ImagesResource.ACEPTAR_IMG));
		this.bottonAceptar.addSelectionListener(new BotonAceptarListener());

		this.sShell.pack();
		
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

		return true;

	}

	class BotonAceptarListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
			InfoDialog.this.sShell.dispose();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

}
