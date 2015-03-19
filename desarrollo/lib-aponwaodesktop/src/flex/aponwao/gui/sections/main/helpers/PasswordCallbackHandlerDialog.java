package flex.aponwao.gui.sections.main.helpers;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.global.windows.PasswordDialog;

import org.eclipse.swt.widgets.Shell;

public class PasswordCallbackHandlerDialog implements CallbackHandler {
	private Shell sShell = null;
	private PasswordProtection passwordProtection = null;
	

	public PasswordCallbackHandlerDialog(Shell sShell) {
		super();
		this.sShell = sShell;
	}

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (Callback c : callbacks) {

			if (c instanceof PasswordCallback) {
				PasswordCallback pc = (PasswordCallback) c;
				this.initPassword();

				if (this.passwordProtection != null) {
					if (this.passwordProtection.getPassword() != null) {
						pc.setPassword(this.passwordProtection.getPassword());
					}
				}
			}
		}
	}

	private void initPassword() {
            //OJO... Modificacion Felix
            String password = null;
                PasswordDialog passwordDialog = new PasswordDialog(sShell);
		password = passwordDialog.open(LanguageResource.getLanguage().getString("password.dialog.message"));
		password.toCharArray();
            //******************************************************************
            this.passwordProtection = new KeyStore.PasswordProtection(password.toCharArray());
            passwordDialog = null;
            System.gc();
	}

	public PasswordProtection getPasswordProtection() {
                if(passwordProtection == null) {
                    try {
                        Callback[] c = new Callback[1];
                        c[0] = new PasswordCallback("#", false);
                        handle(c);
                        // Compruebo las preferencias y cargo el certificado del dispositivo hardware, o del el file-system
                    } catch (IOException ex) {
                        //
                    } catch (UnsupportedCallbackException ex) {
                        //
                    }
                }
		return this.passwordProtection;
	}
}