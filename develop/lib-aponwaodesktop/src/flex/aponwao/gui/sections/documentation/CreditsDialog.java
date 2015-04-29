/**
 *
 */
package flex.aponwao.gui.sections.documentation;

/**
 * @author zylk.net
 */

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
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

/**
 * @author zylk.net
 */
public class CreditsDialog extends Dialog {
	private static final String JOSEBA_MARTOS = "Joseba Martos Sánchez \t jmartos@zylk.net";
	private static final String GUSTAVO_FERNANDEZ = "Gustavo Fernández Gómez \t gus@zylk.net";
	private static final String IKER_SAGASTI = "Iker Sagasti Markina \t iker@irontec.com";
	private static final String ALFREDO_SANCHEZ = "Alfredo Sánchez Blanco \t asanchez@zylk.net";
	private static final String ELOY_GARCIA_BORREGUERO = "Eloy García-Borreguero Melero \t egarcia@zylk.net";
        
        private static final String FELIX_LOPEZ = "Félix López \t fdmarchena2003@hotmail.com";
        private static final String YESSICA_DE_ASCENCAO = "Yessica De Ascencao \t y@hotmail.com";
	
	private Shell sShell;

	/**
	 * @param parent
	 */
	public CreditsDialog(Shell parent) {
		super(parent);
	}

	/**
	 * @return
	 */
	@Override
	public int open() {

		Shell parent = getParentShell();

		this.sShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.sShell.setText(LanguageResource.getLanguage().getString("credits.windowtitle"));
		this.sShell.setSize(450, 300);
		this.sShell.setImage(new Image(this.sShell.getDisplay(), ImagesResource.APLICATION_LOGO));
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.verticalSpacing = 10;
		gridLayout.marginTop = 5;
		// gridLayout.marginLeft = 5;
		this.sShell.setLayout(gridLayout);

		// se añade el componente TabFolder

		TabFolder tabFolder = new TabFolder(sShell, SWT.BORDER);

		GridData gdFolder = new GridData();
		gdFolder.horizontalAlignment = GridData.FILL;
		gdFolder.verticalAlignment = GridData.FILL;
		gdFolder.grabExcessHorizontalSpace = true;
		gdFolder.grabExcessVerticalSpace = true;
		tabFolder.setLayoutData(gdFolder);

		TabItem tabItem = new TabItem(tabFolder, SWT.NULL);
		tabItem.setText(LanguageResource.getLanguage().getString("credits.writed"));
		Text text = new Text(tabFolder, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		String s = "\n";
		s += ELOY_GARCIA_BORREGUERO;
		s += "\t \t \n";
		s += ALFREDO_SANCHEZ;
		s += "\t \t \n";
		s += IKER_SAGASTI;
		s += "\t \t \n";
		s += GUSTAVO_FERNANDEZ;
                s += "\t \t \n";
		s += FELIX_LOPEZ;
                s += "\t \t \n";
		s += YESSICA_DE_ASCENCAO;

		text.setText(s);
		text.setEditable(false);
		tabItem.setControl(text);

		TabItem tabItem2 = new TabItem(tabFolder, SWT.NULL);
		tabItem2.setText(LanguageResource.getLanguage().getString("credits.documented"));
		Text text2 = new Text(tabFolder, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		
		s = "\n";
		s += ELOY_GARCIA_BORREGUERO;
		s += "\t \t \n";
		s += ALFREDO_SANCHEZ;
		s += "\t \t \n";
		s += IKER_SAGASTI;
		s += "\t \t \n";
		s += GUSTAVO_FERNANDEZ;
                s += "\t \t \n";
		s += FELIX_LOPEZ;
                s += "\t \t \n";
		s += YESSICA_DE_ASCENCAO;

		text2.setText(s);
		text2.setEditable(false);
		tabItem2.setControl(text2);

		TabItem tabItem3 = new TabItem(tabFolder, SWT.NULL);
		tabItem3.setText(LanguageResource.getLanguage().getString("credits.translated"));
		Text text3 = new Text(tabFolder, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		
		s = "\n";
		s += ELOY_GARCIA_BORREGUERO;
		s += " \t (English) \n";
		s += JOSEBA_MARTOS;
		s += " \t (Euskera) \n";
		
		text3.setText(s);
		text3.setEditable(false);
		tabItem3.setControl(text3);

		tabFolder.setSize(400, 200);

		// se añaden los botones

		Button buttonClose = new Button(sShell, SWT.NONE);
		buttonClose.setImage(new Image(this.sShell.getDisplay(), ImagesResource.BACK_IMG));
		buttonClose.setText(LanguageResource.getLanguage().getString("button.back"));
		buttonClose.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		buttonClose.addSelectionListener(new ButtonCloseListener());

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

	class ButtonCloseListener implements SelectionListener {

		public void widgetSelected(SelectionEvent event) {

			sShell.dispose();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			widgetSelected(event);
		}
	}
}