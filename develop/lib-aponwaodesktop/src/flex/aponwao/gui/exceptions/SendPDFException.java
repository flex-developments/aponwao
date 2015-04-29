/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.aponwao.gui.exceptions;

/**
 *
 * @author flopez
 */
public class SendPDFException extends Exception {
    
    public SendPDFException(String msj) {
        super(msj);
    }
    
    public SendPDFException(Throwable cause) {
        super(cause);
    }
    
    public SendPDFException(String message, Throwable cause) {
        super(message, cause);
    }
}