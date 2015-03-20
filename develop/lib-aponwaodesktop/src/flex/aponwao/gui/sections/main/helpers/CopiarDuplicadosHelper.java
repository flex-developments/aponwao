package flex.aponwao.gui.sections.main.helpers;

import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.ResourceHelper;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Vector;
import javax.swing.JOptionPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
/**
 *
 * @author flopez
 */
public class CopiarDuplicadosHelper {
    private static Shell sShell = null;
    
    public static void copiarDuplicados(Shell sShell, boolean respaldar) {
        try {
            CopiarDuplicadosHelper.sShell = sShell;
            //Sincronizar carpeta Firmados a dispositivo extraible
            File f = getDirectory(File.listRoots()[0], LanguageResource.getLanguage().getString("info.copiar.duplicados.destino"));
            String comando = ResourceHelper.getRootPath() + "bin/copiarduplicados" +
                             " -o " + PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_FINAL_FOLDER_VALUE) +
                             " -d " + f.getAbsolutePath();
            if(respaldar) comando = comando + " -b";
            
            System.gc();
            ejecutarComando(comando);
            System.gc();
            int respuesta2 = JOptionPane.showOptionDialog(
                null,
                LanguageResource.getLanguage().getString("info.copiar.duplicados.fin"),
                LanguageResource.getLanguage().getString("info.dialog.title"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                null,
                null);
        } catch (NullPointerException e) {
             
         }
    }
    
    private static File getDirectory(File startingDirectory, String mensaje) {
            DirectoryDialog fileDialog = new DirectoryDialog(sShell, SWT.OPEN);
            fileDialog.setMessage(mensaje);
            if (startingDirectory != null) {
                fileDialog.setFilterPath(startingDirectory.getPath());
            }
            String dir = fileDialog.open();
            if (dir != null) {
                dir = dir.trim();
                if (dir.length() > 0) {
                    return new File(dir);
                }
            }
            return null;
        }
        
    private static void ejecutarComando(String comando) {
        String s = null;
//        Vector arrayStringSalida = new Vector();
        int error=1;

        try {
            // Ejecutamos el comando 
            Process p = Runtime.getRuntime().exec(comando);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            //Leemos la salida del comando
            while ((s = stdInput.readLine()) != null) {
//                 arrayStringSalida.add(s);
            }

            //Leemos los errores si los hubiera
            while ((s = stdError.readLine()) != null) {
//                arrayStringSalida.add("\nERROR " + error);
                error++;
//                arrayStringSalida.add(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        return arrayStringSalida;
    }
}
