package flex.aponwao.core.password;

import java.io.Console;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * @author alfredo
 */
public class ConsoleCallbackHandler implements CallbackHandler, PasswordExtractor {
    private PasswordProtection passwordProtection = null;
    
    public ConsoleCallbackHandler(String preferencesPath) {}
	
    @Override
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
        // open up standard input
        String pin_s = null;
        try {
            Console terminal = System.console();
            if (terminal==null) throw new Exception("No se pudo instanciar la consola.");
            pin_s = new String (terminal.readPassword("Introduzca la contraseña: "));
        } catch(Exception e) {
            e.printStackTrace();
        }
        this.passwordProtection = new KeyStore.PasswordProtection(pin_s.toCharArray());
    }
    
    public PasswordProtection getPasswordProtection() {
        return this.passwordProtection;
    }	
}