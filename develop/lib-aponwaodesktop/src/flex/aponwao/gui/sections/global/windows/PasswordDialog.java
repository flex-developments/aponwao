package flex.aponwao.gui.sections.global.windows;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.ResourceHelper;
import flex.aponwao.gui.sections.global.events.BotonCancelarListener;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import flex.aponwao.tecladoVirtual.Configuraciones;
import flex.aponwao.tecladoVirtual.Tecla;
import flex.aponwao.tecladoVirtual.TecladoVirtual;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import javax.swing.JButton;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
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
public class PasswordDialog extends Dialog {

	private Shell sShell = null;
	
	private Text passwordField;
	private String password = null;
	
	private Composite ButtonsComposite = null;
	private Button bottonAceptar = null;
	private Button bottonCancelar = null;

        private TecladoVirtual teclado = null;
        private boolean cancelar = false;

	/**
	 * @param parent
	 */
	public PasswordDialog(Shell parent) {
		
		super(parent);
	}

	/**
	 * @param storeName
	 * @param mensaje
	 * @return
	 */
	public String open(String mensaje) {
		Shell parent = getParentShell();
                Display display = parent.getDisplay();

                //OJO.. Modificacion Yessica
                if(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.VIRTUAL_KEYBOARD)) {
                    this.sShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
                    Configuraciones conf = new Configuraciones();
                    this.sShell.setSize(Integer.valueOf(conf.getMyConf().get("WindowSizeX")),Integer.valueOf(conf.getMyConf().get("WindowSizeY")));
                    this.sShell.setLayout(new FillLayout());
                    Composite composite = new Composite(this.sShell, SWT.NO_BACKGROUND | SWT.EMBEDDED);
                    composite.setBounds(0,0,Integer.valueOf(conf.getMyConf().get("WindowSizeX")),Integer.valueOf(conf.getMyConf().get("WindowSizeY")));
                    try {
                      System.setProperty("sun.awt.noerasebackground", "true");
                    } catch (NoSuchMethodError error) { }
System.out.println("a");
                    final Frame frame = SWT_AWT.new_Frame(composite);
System.out.println("b");
                    frame.setSize(200, 200);
System.out.println("c");
                    this.sShell.setText(LanguageResource.getLanguage().getString("password.dialog.title"));

                    this.teclado = new TecladoVirtual(conf.getMyConf(), conf.getMyKeys(), mensaje);

                    Integer linepos      = Integer.valueOf(conf.getMyConf().get("ButtonLine6"));
                    Integer ButtonStart  = Integer.valueOf(conf.getMyConf().get("AceptarButtonStart"));
                    Integer ButtonX      = Integer.valueOf(conf.getMyConf().get("AceptarButtonSizeX"));
                    Integer ButtonY      = Integer.valueOf(conf.getMyConf().get("AceptarButtonSizeY"));
                    Tecla thisKeys = conf.getMyKeys().get("AceptarButton");
                    
                    JButton AceptarButton = new JButton();
                    AceptarButton.setText(thisKeys.getUpperCase());
                    AceptarButton.setSize(new Dimension(ButtonX+30, ButtonY));
                    AceptarButton.setLocation(new Point(ButtonStart, linepos));
                    AceptarButton.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            password = teclado.getPassword();
                            cancelar = true;
                        }
                    });
                    this.teclado.add(AceptarButton);

                    linepos      = Integer.valueOf(conf.getMyConf().get("ButtonLine6"));
                    ButtonStart  = Integer.valueOf(conf.getMyConf().get("CancelarButtonStart"));
                    ButtonX      = Integer.valueOf(conf.getMyConf().get("CancelarButtonSizeX"));
                    ButtonY      = Integer.valueOf(conf.getMyConf().get("CancelarButtonSizeY"));
                    thisKeys = conf.getMyKeys().get("CancelarButton");

                    JButton CancelarButton = new JButton();
                    CancelarButton.setText(thisKeys.getUpperCase());
                    CancelarButton.setSize(new Dimension(ButtonX+30, ButtonY));
                    CancelarButton.setLocation(new Point(ButtonStart, linepos));
                    CancelarButton.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            cancelar = true;
                        }
                    });
                    this.teclado.add(CancelarButton);

                    frame.add(this.teclado);
                 
                } else {

                    this.sShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
                    this.sShell.setImage(new Image(sShell.getDisplay(), ImagesResource.APLICATION_LOGO));
                    this.sShell.setText(LanguageResource.getLanguage().getString("password.dialog.title"));

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

                    this.passwordField = new Text(this.sShell, SWT.BORDER | SWT.PASSWORD);
                    this.passwordField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

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

                    this.passwordField.addKeyListener(new PasswordFieldKeyListener());

                    this.sShell.pack();
                }
                ///////////////////////////////////////////////////////////////////////////////////

                // to center the shell on the screen
                Monitor primary = this.sShell.getDisplay().getPrimaryMonitor();
                Rectangle bounds = primary.getBounds();
                Rectangle rect = this.sShell.getBounds();
                int x = bounds.x + (bounds.width - rect.width) / 2;
                int y = bounds.y + (bounds.height - rect.height) / 3;
                this.sShell.setLocation(x, y);

		this.sShell.open();

		while (!this.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
                        if (cancelar) this.sShell.dispose();
		}

		return password;
	}

	class BotonAceptarListener implements SelectionListener {
		
		public void widgetSelected(SelectionEvent event) {

			password = passwordField.getText();
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
				password = passwordField.getText();
				sShell.dispose();
			}
		}
	}

}