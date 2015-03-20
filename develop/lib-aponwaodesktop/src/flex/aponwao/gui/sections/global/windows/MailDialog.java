package flex.aponwao.gui.sections.global.windows;

import java.text.MessageFormat;
import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.global.events.BotonCancelarListener;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;

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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MailDialog extends Dialog {
    
    private Shell sShell = null;
    private Button bottonAceptar = null;
    private Button bottonCancelar = null;
    private Text txtContraseña, txtDestinatarios, txtCopias, txtCopiasOcultas, txtAsunto, txtMensaje;
    private String contraseña, destinatarios, copias, copiasOcultas, asunto, mensaje;
    String[] resp = null;

    /**
     * @param parent
     */
    public MailDialog(Shell parent) {
            super(parent);
    }

    //Retornará en el espacio 0 la contraseña, en el 1 los detinatarios, en el 2 el asunto y en el 3 el mensaje
    public String[] open(String adjuntos) {
        Shell parent = getParentShell();
        sShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        sShell.setImage(new Image(sShell.getDisplay(), ImagesResource.APLICATION_LOGO));
        sShell.setText(LanguageResource.getLanguage().getString("email.dialog.title"));

        GridLayout area = new GridLayout(5, true);
        area.verticalSpacing = 5;
        sShell.setLayout(area);

        //Linea de encabezado
        String encabezado = MessageFormat.format(
                    LanguageResource.getLanguage().getString("email.dialog.head"), PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_USER),
                    adjuntos);
        
        Label lblEncabezado = new Label(sShell, SWT.WRAP | SWT.NONE);
        lblEncabezado.setText(encabezado);
        lblEncabezado.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, area.numColumns, 1));

        //Linea de contraseña
        Label lblContraseña = new Label(sShell, SWT.NONE);
        lblContraseña.setText(LanguageResource.getLanguage().getString("email.dialog.password"));

        txtContraseña = new Text(sShell, SWT.PASSWORD | SWT.BORDER);
        txtContraseña.addKeyListener(new TextKeyListener(txtContraseña));
        String me = MessageFormat.format(
                    LanguageResource.getLanguage().getString("email.dialog.password.tooltiptext"),
                    PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_USER));
        txtContraseña.setToolTipText(me);
        txtContraseña.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, area.numColumns-1, 1));

        //Linea de destinatarios
        Label lblDestinatarios = new Label(sShell, SWT.NONE);
        lblDestinatarios.setText(LanguageResource.getLanguage().getString("email.dialog.addresses"));

        txtDestinatarios = new Text(sShell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.SCROLL_LINE);
        txtDestinatarios.setToolTipText(LanguageResource.getLanguage().getString("email.dialog.addresses.tooltiptext"));
        txtDestinatarios.addKeyListener(new TextKeyListener(txtDestinatarios));
        txtDestinatarios.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, area.numColumns-1, 2));
        Label lblrelleno = new Label(sShell, SWT.NONE);
        
        //Linea de copia
        Label lblCopia = new Label(sShell, SWT.NONE);
        lblCopia.setText(LanguageResource.getLanguage().getString("email.dialog.addresses.copy"));

        txtCopias = new Text(sShell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.SCROLL_LINE);
        txtCopias.setToolTipText(LanguageResource.getLanguage().getString("email.dialog.addresses.tooltiptext.copy"));
        txtCopias.addKeyListener(new TextKeyListener(txtCopias));
        txtCopias.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, area.numColumns-1, 2));
        Label lblrellenoCopia = new Label(sShell, SWT.NONE);
        
        //Linea de copia oculta
        Label lblCopiaOculta = new Label(sShell, SWT.NONE);
        lblCopiaOculta.setText(LanguageResource.getLanguage().getString("email.dialog.addresses.hiddencopy"));

        txtCopiasOcultas = new Text(sShell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.SCROLL_LINE);
        txtCopiasOcultas.setToolTipText(LanguageResource.getLanguage().getString("email.dialog.addresses.tooltiptext.hiddencopy"));
        txtCopiasOcultas.addKeyListener(new TextKeyListener(txtCopiasOcultas));
        txtCopiasOcultas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, area.numColumns-1, 2));
        Label lblrellenoCopiaOculta = new Label(sShell, SWT.NONE);
        
        //Linea de Asunto
        Label lblAsunto = new Label(sShell, SWT.NONE);
        lblAsunto.setText(LanguageResource.getLanguage().getString("email.dialog.subject"));
        
        
        txtAsunto = new Text(sShell, SWT.BORDER);
        txtAsunto.addKeyListener(new TextKeyListener(txtAsunto));
        txtAsunto.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, area.numColumns-1, 1));
        
        //Linea de Mensaje
        Label lblMensaje = new Label(sShell, SWT.NONE);
        lblMensaje.setText(LanguageResource.getLanguage().getString("email.dialog.message"));
        
        txtMensaje = new Text(sShell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
        txtMensaje.addKeyListener(new TextKeyListener(txtMensaje));
        txtMensaje.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, area.numColumns, 30));

        bottonAceptar = new Button(sShell, SWT.NONE);
        bottonAceptar.setText(LanguageResource.getLanguage().getString("button.accept"));

        bottonAceptar.setImage(new Image(sShell.getDisplay(), ImagesResource.ACEPTAR_IMG));
        bottonAceptar.addSelectionListener(new BotonAceptarListener());

        bottonCancelar = new Button(sShell, SWT.NONE);
        bottonCancelar.setText(LanguageResource.getLanguage().getString("button.cancel"));
        bottonCancelar.setImage(new Image(sShell.getDisplay(), ImagesResource.CANCEL_IMG));
        bottonCancelar.addSelectionListener(new BotonCancelarListener());

        sShell.pack();

        // to center the shell on the screen
        Monitor primary = sShell.getDisplay().getPrimaryMonitor();
        Rectangle bounds = primary.getBounds();
        Rectangle rect = sShell.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 3;
        sShell.setLocation(x, y);

        sShell.open();

        Display display = parent.getDisplay();

        while (!sShell.isDisposed()) {
                if (!display.readAndDispatch())
                        display.sleep();
        }

        return resp;
    }

    class BotonAceptarListener implements SelectionListener {
        public void widgetSelected(SelectionEvent event) {
            contraseña = txtContraseña.getText();
            destinatarios = txtDestinatarios.getText();
            copias = txtCopias.getText();
            copiasOcultas = txtCopiasOcultas.getText();
            asunto = txtAsunto.getText();
            mensaje = txtMensaje.getText();
            
            resp = new String[6];
            resp[0] = contraseña;
            resp[1] = destinatarios;
            resp[2] = copias;
            resp[3] = copiasOcultas;
            resp[4] = asunto;
            resp[5] = mensaje;
            
            sShell.dispose();
        }

        public void widgetDefaultSelected(SelectionEvent event) {
                widgetSelected(event);
        }
    }
    
    class TextKeyListener implements KeyListener {
        private Text texto;
        
        public TextKeyListener(Text texto) {
            this.texto = texto;
        }
        
        @Override
        public void keyPressed(KeyEvent ke) {
            //Si presionan tabulador
            if(ke.keyCode == 9) {
                ke.doit = false;
                if(texto.equals(txtContraseña)) txtDestinatarios.setFocus();
                if(texto.equals(txtDestinatarios)) txtCopias.setFocus();
                if(texto.equals(txtCopias)) txtCopiasOcultas.setFocus();
                if(texto.equals(txtCopiasOcultas)) txtAsunto.setFocus();
                if(texto.equals(txtAsunto)) txtMensaje.setFocus();
                if(texto.equals(txtMensaje)) bottonAceptar.setFocus();
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            //nada
        }
        
    }
}