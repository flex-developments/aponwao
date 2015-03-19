package flex.aponwao.gui.sections.preferences.windows;


import java.awt.Rectangle;
import java.util.logging.Logger;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.global.events.BotonCancelarListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;


public class ImagePositionDialog extends Dialog {

	private static Logger	logger	= Logger.getLogger(ImagePositionDialog.class.getName());	
	
	private Shell	sShell	= null;

	private Button			bottonAceptar		= null;
	private Button			bottonCancelar		= null;
	
	private ImagePositionPanel imagePositionPanel = null;
	
	// datos a devolver
	private Rectangle rectangle = null;
	
	private String ruta = null;
	

	public ImagePositionDialog(Shell parent, String ruta, Rectangle rectangle) {
		
		super(parent);
		this.rectangle = rectangle;
		this.ruta = ruta;
	}
	

	public Rectangle createSShell() {
		
		Shell parent = getParent();
		sShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE );
		sShell.setImage(new Image(this.sShell.getDisplay(), ImagesResource.APLICATION_LOGO));
		sShell.setText(LanguageResource.getLanguage().getString("preferences.sign.appearance.stamp.position.tittle"));
		
		sShell.setLayout(new GridLayout());
		
		createCompositeCampos();
		
		createCompositeBotones();		

		sShell.setSize(new Point(478, 720));
		
		// to center the shell on the screen
		Monitor primary = this.sShell.getDisplay().getPrimaryMonitor();
	    org.eclipse.swt.graphics.Rectangle bounds = primary.getBounds();
	    org.eclipse.swt.graphics.Rectangle rect = this.sShell.getBounds();
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 3;
	    this.sShell.setLocation(x, y);
		
		sShell.open();
		
		Display display = parent.getDisplay();
		
		while (!sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		return rectangle;
		
	}
	
	private void createCompositeCampos() { 
		
		Composite compositeEmail = new Composite(sShell, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		compositeEmail.setLayoutData(gd);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.verticalSpacing = 5;
		gridLayout.horizontalSpacing = 50;
		compositeEmail.setLayout(gridLayout);
		
		compositeEmail.setBackground(new Color(Display.getDefault(), 255, 255, 255));
		
		imagePositionPanel = new ImagePositionPanel(compositeEmail, ruta, rectangle);
		
	}
	
	private void createCompositeBotones() {

		Composite compositeBotones = new Composite(sShell, SWT.NONE);
		GridData gd10 = new GridData();
		gd10.horizontalAlignment = GridData.CENTER;
		gd10.horizontalSpan = 1;
		compositeBotones.setLayoutData(gd10);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.horizontalSpacing = 30;
		compositeBotones.setLayout(gridLayout);

		bottonAceptar = new Button(compositeBotones, SWT.NONE);
		bottonAceptar.setText(LanguageResource.getLanguage().getString("button.accept"));
		bottonAceptar.setImage(new Image(sShell.getDisplay(), ImagesResource.ACEPTAR_IMG));
		bottonAceptar.addSelectionListener(new BotonAceptarListener());

		bottonCancelar = new Button(compositeBotones, SWT.NONE);
		bottonCancelar.setText(LanguageResource.getLanguage().getString("button.cancel"));
		bottonCancelar.setImage(new Image(sShell.getDisplay(), ImagesResource.CANCEL_IMG));
		bottonCancelar.addSelectionListener(new BotonCancelarListener());

	}
	
	class BotonAceptarListener implements SelectionListener {
		
		public void widgetSelected(SelectionEvent event) {
			
			// se setean los datos
			rectangle = imagePositionPanel.getImagePosition();
			sShell.dispose();

		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);			
		}
	}

}