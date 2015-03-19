/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flex.aponwao.gui.exceptions;

/**
 *
 * @author flopez
 */
public class FolderIsEmpyException extends Exception {
    public FolderIsEmpyException(String msj) {
        super(msj);
    }
    
    public FolderIsEmpyException(Throwable e) {
        super(e);
    }
    
    public FolderIsEmpyException(String msj, Throwable e) {
        super(msj, e);
    }
}
