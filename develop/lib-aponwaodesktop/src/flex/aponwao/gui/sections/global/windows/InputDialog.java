package flex.aponwao.gui.sections.global.windows;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.ResourceHelper;
import flex.aponwao.gui.sections.global.events.BotonCancelarListener;

import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author zylk.net
 */
public class InputDialog extends Dialog {

	private Shell sShell = null;
	
	private Text texto;
	private String result = "";
	
	private Composite ButtonsComposite = null;
	private Button bottonAceptar = null;
	private Button bottonCancelar = null;

	/**
	 * @param parent
	 */
	public InputDialog(Shell parent) {
		super(parent);
	}

	/**
	 * @param storeName
	 * @param mensaje
	 * @return
	 */
	public String open(String mensaje) {

		Shell parent = getParentShell();

		this.sShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.sShell.setImage(new Image(sShell.getDisplay(), ImagesResource.APLICATION_LOGO));
		this.sShell.setText(LanguageResource.getLanguage().getString("input.dialog.title"));
          
		GridLayout shellGridLayout = new GridLayout();
		shellGridLayout.numColumns = 1;
		shellGridLayout.verticalSpacing = 10;
		shellGridLayout.marginTop = 10;
		this.sShell.setLayout(shellGridLayout);

		Label textoLabel = new Label(this.sShell, SWT.WRAP | SWT.NONE);
		textoLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textoLabel.setText(mensaje);
		
		GridData gdTexto = new GridData();
		gdTexto.widthHint = 400;
		textoLabel.setLayoutData(gdTexto);
		
		this.texto = new Text(this.sShell, SWT.BORDER);
		this.texto.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));		
		
		this.ButtonsComposite = new Composite(this.sShell, SWT.NONE);
		this.ButtonsComposite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		GridLayout compositeGridLayout = new GridLayout();
		compositeGridLayout.numColumns = 2;
		compositeGridLayout.horizontalSpacing = 50;
		this.ButtonsComposite.setLayout(compositeGridLayout);

		this.bottonAceptar = new Button(this.ButtonsComposite, SWT.NONE);
                this.bottonAceptar.setText(LanguageResource.getLanguage().getString("button.accept"));
                
		this.bottonAceptar.setImage(new Image(this.sShell.getDisplay(), ImagesResource.ACEPTAR_IMG));
		this.bottonAceptar.addSelectionListener(new BotonAceptarListener());

		this.bottonCancelar = new Button(this.ButtonsComposite, SWT.NONE);
		this.bottonCancelar.setText(LanguageResource.getLanguage().getString("button.cancel"));
		this.bottonCancelar.setImage(new Image(this.sShell.getDisplay(), ImagesResource.CANCEL_IMG));
		this.bottonCancelar.addSelectionListener(new BotonCancelarListener());
		
		this.texto.addKeyListener(new PasswordFieldKeyListener());
                
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
                
		return result;
	}

	class BotonAceptarListener implements SelectionListener {
		
		public void widgetSelected(SelectionEvent event) {

			result = texto.getText();
			sShell.dispose();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

	class PasswordFieldKeyListener implements KeyListener {

		public void keyPressed(KeyEvent e) {

		}

		public void keyReleased(KeyEvent e) {

			if (LegacyActionTools.findKeyCode(ResourceHelper.INTROKEY) == e.character) {
				result = texto.getText();
				sShell.dispose();
			}
		}
	}

}