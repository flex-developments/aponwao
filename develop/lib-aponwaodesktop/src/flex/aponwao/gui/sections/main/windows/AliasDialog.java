package flex.aponwao.gui.sections.main.windows;

import java.util.List;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.global.events.BotonCancelarListener;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * @author zylk.net
 */
public class AliasDialog extends Dialog {

	private Shell sShell = null;
	
	private Combo aliasCombo;
	private String alias = null;
	
	private Composite ButtonsComposite = null;
	private Button bottonAceptar = null;
	private Button bottonCancelar = null;

	/**
	 * @param parent
	 */
	public AliasDialog(Shell parent) {
		super(parent);
	}

	/**
	 * @param storeName
	 * @param mensaje
	 * @return
	 */
	public String open(List<String> listaAliases) {

		Shell parent = getParentShell();

		this.sShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.sShell.setImage(new Image(sShell.getDisplay(), ImagesResource.APLICATION_LOGO));
		this.sShell.setText(LanguageResource.getLanguage().getString("alias.dialog.title"));

		GridLayout shellGridLayout = new GridLayout();
		shellGridLayout.numColumns = 1;
		shellGridLayout.verticalSpacing = 10;
		shellGridLayout.marginTop = 10;
		this.sShell.setLayout(shellGridLayout);

		Label textoLabel = new Label(this.sShell, SWT.NONE);
		textoLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textoLabel.setText(LanguageResource.getLanguage().getString("alias.dialog.message"));
		
		GridData gdTexto = new GridData();
		gdTexto.widthHint = 400;
		textoLabel.setLayoutData(gdTexto);
		
		this.aliasCombo = new Combo(this.sShell, SWT.NONE | SWT.READ_ONLY);
		this.aliasCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		for (String string : listaAliases) 
		{
			this.aliasCombo.add(string);
			this.aliasCombo.select(0);
		}
		
		
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
		
		this.sShell.pack();
		
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

		return alias;
	}

	class BotonAceptarListener implements SelectionListener {
		
		public void widgetSelected(SelectionEvent event) {

			alias = aliasCombo.getText();
			sShell.dispose();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}
}