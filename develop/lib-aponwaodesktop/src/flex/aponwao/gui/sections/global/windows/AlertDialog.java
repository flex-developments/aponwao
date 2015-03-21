package flex.aponwao.gui.sections.global.windows;


import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * @author zylk.net
 */
public class AlertDialog extends Dialog {

	private Shell	sShell			= null;
	private Button	bottonAceptar	= null;
	private Button	bottonCancelar	= null;
	private Label	textoLabel		= null;
	private boolean	result			= false;

	/**
	 * @param parent
	 */
	public AlertDialog(Shell parent) {
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
		this.sShell.setText(LanguageResource.getLanguage().getString("alert.dialog.title"));
		
		GridLayout shellGridLayout = new GridLayout();
		shellGridLayout.numColumns = 1;
		shellGridLayout.verticalSpacing = 10;
		shellGridLayout.marginTop = 15;
		this.sShell.setLayout(shellGridLayout);

		this.textoLabel = new Label(this.sShell, SWT.WRAP | SWT.NONE);
		this.textoLabel.setText(mensaje);
		
		GridData gdTexto = new GridData();
		gdTexto.widthHint = 600;
		textoLabel.setLayoutData(gdTexto);

		Composite ButtonsComposite = new Composite(this.sShell, SWT.NONE);
		ButtonsComposite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		GridLayout compositeGridLayout = new GridLayout();
		compositeGridLayout.numColumns = 2;
		compositeGridLayout.horizontalSpacing = 50;
		ButtonsComposite.setLayout(compositeGridLayout);

		
		this.bottonAceptar = new Button(ButtonsComposite, SWT.NONE);
		this.bottonAceptar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		this.bottonAceptar.setText(LanguageResource.getLanguage().getString("button.accept"));
		this.bottonAceptar.setImage(new Image(this.sShell.getDisplay(), ImagesResource.ACEPTAR_IMG));
		this.bottonAceptar.addSelectionListener(new BotonAceptarListener());

		this.bottonCancelar = new Button(ButtonsComposite, SWT.NONE);
		this.bottonCancelar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		this.bottonCancelar.setText(LanguageResource.getLanguage().getString("button.cancel"));
		this.bottonCancelar.setImage(new Image(this.sShell.getDisplay(), ImagesResource.CANCEL_IMG));
		this.bottonCancelar.addSelectionListener(new BotonCancelarListener());

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

		return this.result;

	}

	class BotonAceptarListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
			result = true;
			sShell.dispose();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

	class BotonCancelarListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
			sShell.dispose();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

}