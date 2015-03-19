package flex.aponwao.gui.sections.main.windows;

import flex.aponwao.gui.application.DesktopHelper;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.documentation.AboutUsDialog;
import flex.aponwao.gui.sections.documentation.VentanaLicencia;
import flex.aponwao.gui.sections.preferences.windows.PreferencesManager;
import java.util.logging.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;


public class MenuMain {

	private static Logger logger = Logger.getLogger(MenuMain.class.getName());

	private Shell sShell = null;
	

	private Menu				menuBar						= null;
	private Menu				aplicationMenu, ayudaMenu = null;
	private MenuItem			aplicationMenuHeader, ayudaMenuHeader = null;
	private MenuItem			salirItem = null;
	private MenuItem			preferenciasItem	= null;
	private MenuItem			documentacionItem			= null;
	private MenuItem			licenciaItem				= null;
	private MenuItem			acercaDeItem				= null;
	
	private Composite compositeCentro = null;

	public MenuMain(Shell sShell, Composite compositeCentro) {

		this.sShell = sShell;
		this.compositeCentro = compositeCentro; 
		initialize();
	}

	private void initialize() {

		// Creación del menu
		this.menuBar = new Menu(sShell, SWT.BAR);
		this.sShell.setMenuBar(this.menuBar);
		this.aplicationMenuHeader = new MenuItem(this.menuBar, SWT.CASCADE);
		this.aplicationMenu = new Menu(sShell, SWT.DROP_DOWN);
		this.aplicationMenuHeader.setMenu(this.aplicationMenu);
		this.preferenciasItem = new MenuItem(this.aplicationMenu, SWT.PUSH);
		this.salirItem = new MenuItem(this.aplicationMenu, SWT.PUSH);
		this.ayudaMenuHeader = new MenuItem(this.menuBar, SWT.CASCADE);
		this.ayudaMenu = new Menu(sShell, SWT.DROP_DOWN);
		this.ayudaMenuHeader.setMenu(this.ayudaMenu);
		this.documentacionItem = new MenuItem(this.ayudaMenu, SWT.PUSH);
		this.licenciaItem = new MenuItem(this.ayudaMenu, SWT.PUSH);
		this.acercaDeItem = new MenuItem(this.ayudaMenu, SWT.PUSH);
		
		
		this.salirItem.setImage(new Image(this.sShell.getDisplay(), ImagesResource.MENU_EXIT));
		this.preferenciasItem.setImage(new Image(this.sShell.getDisplay(), ImagesResource.MENU_PREFERENCES));
		this.licenciaItem.setImage(new Image(this.sShell.getDisplay(), ImagesResource.MENU_LICENSE));
		this.acercaDeItem.setImage(new Image(this.sShell.getDisplay(), ImagesResource.MENU_ABOUT));
		this.documentacionItem.setImage(new Image(this.sShell.getDisplay(), ImagesResource.MENU_DOCUMENTATION));
		
		
//		this.botonModificar.addSelectionListener(new botonModificarItemListener());
		this.salirItem.addSelectionListener(new SalirItemListener());
		this.preferenciasItem.addSelectionListener(new PreferenciasItemListener());
		this.acercaDeItem.addSelectionListener(new AboutItemListener());
		this.licenciaItem.addSelectionListener(new LicenciaItemListener());
		this.documentacionItem.addSelectionListener(new DocumentacionItemListener());

		changeLanguage();

	}

	private void changeLanguage() {

		this.salirItem.setText(LanguageResource.getLanguage().getString("submenu.exit"));
		this.aplicationMenuHeader.setText(LanguageResource.getLanguage().getString("menu.file"));
		this.preferenciasItem.setText(LanguageResource.getLanguage().getString("submenu.preferences"));
		this.ayudaMenuHeader.setText(LanguageResource.getLanguage().getString("menu.help"));
		this.licenciaItem.setText(LanguageResource.getLanguage().getString("submenu.license"));
		this.acercaDeItem.setText(LanguageResource.getLanguage().getString("submenu.about"));
		this.documentacionItem.setText(LanguageResource.getLanguage().getString("submenu.documentation"));

	}
	
	private void disposeChildren(Composite composite) {

		Control[] array = composite.getChildren();
		for (int i = 0; i < array.length; i++) {
			array[i].dispose();
		}

	}
	

//	class SignPanelItemListener implements SelectionListener {
//		
//		public void widgetSelected(SelectionEvent event) {
//			
//			disposeChildren(compositeCentro);
//
//			PanelPDF panelPDF = new PanelPDF(compositeCentro, SWT.NONE);
//
//			compositeCentro.layout();
//		}
//
//		public void widgetDefaultSelected(SelectionEvent event) {
//			widgetSelected(event);
//		}
//	}
	
	class PreferenciasItemListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {
			
			PreferencesManager ventanaPreferencias = new PreferencesManager();
			ventanaPreferencias.abrirVentana(sShell);

			changeLanguage();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

	// listener del item acerca de
	class AboutItemListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {
			
			AboutUsDialog aboutUsDialog = new AboutUsDialog(sShell);
			aboutUsDialog.open();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}
		
	class AyudaItemListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {
			
			DesktopHelper.openDefaultBrowser(LanguageResource.getLanguage().getString("help.url"));
			
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}
	
	// listener del menu cerrar
	class SalirItemListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
			Display.getCurrent().dispose();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

	// listener del menu de documentacion
	class DocumentacionItemListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {

			DesktopHelper.openDefaultBrowser(LanguageResource.getLanguage().getString("documentation.uri"));
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}

	// listener del menu de Licencia
	class LicenciaItemListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {

			VentanaLicencia licenciaVentana = new VentanaLicencia(sShell);
			licenciaVentana.open();

		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}
	

}