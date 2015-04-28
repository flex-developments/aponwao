package flex.aponwao.gui.sections.main.windows;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.LoggingDesktopController;
import flex.aponwao.gui.application.ResourceHelper;
import flex.aponwao.gui.sections.global.windows.InfoDialog;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class WindowMain {

	private static Logger logger = Logger.getLogger(WindowMain.class.getName());
	
	public WindowMain () {
		//Display.setAppName(ResourceHelper.APPLICATION_TITLE);
                //OJO... Modificacion Yesica
                Display.setAppName(PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_TITLE));
                //**************************************************************
		Display display = new Display();

		Shell mainShell = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		
		try {
			mainShell.setSize(new Point(950, 720));
			mainShell.setMaximized(true);
			//OJO... Modificacion Felix
                        mainShell.setText(PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_TITLE));
                        //******************************************************
			mainShell.setImage(new Image(display, ImagesResource.APLICATION_LOGO));
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 1;
			gridLayout.verticalSpacing = 15;
			gridLayout.marginTop = 5;
			mainShell.setLayout(gridLayout);
	
			
			// Crea el panel central donde se integraran los distintos paneles.
			Composite compositeCentro = new Composite(mainShell, SWT.NONE);
			GridData gd = new GridData();
			gd.horizontalAlignment = GridData.FILL;
			gd.verticalAlignment = GridData.FILL;
			gd.grabExcessHorizontalSpace = true;
			gd.grabExcessVerticalSpace = true;
			compositeCentro.setLayoutData(gd);
			GridLayout gridLayoutCentro = new GridLayout();
			gridLayoutCentro.numColumns = 1;
			compositeCentro.setLayout(gridLayoutCentro);
		
		
			// MENU
			MenuMain menuPrincipal = new MenuMain(mainShell, compositeCentro);
	
			
			// INICIALIZA EL PANEL CON EL SIGNPANEL
			PanelPDF panelPDF = new PanelPDF(compositeCentro, SWT.NONE);
			
			
			// CONSOLA DE EVENTOS
			LoggingDesktopController.initialize(mainShell);
			
			
			mainShell.open();
			mainShell.layout();
			
			// INIT PROXY
			ResourceHelper.configureProxy();
			
			
			while (!mainShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		
			
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, "Error general de la aplicacion (runtime)", e);
			InfoDialog id = new InfoDialog(mainShell);
			id.open(LanguageResource.getLanguage().getString("error.runtime"));
		}
		
		display.dispose();
	}	
}