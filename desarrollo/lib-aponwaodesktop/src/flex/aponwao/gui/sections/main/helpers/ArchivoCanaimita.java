package flex.aponwao.gui.sections.main.helpers;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfPKCS7;
import com.itextpdf.text.pdf.PdfReader;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.global.windows.FileDialogs;
import flex.eSign.helpers.CertificateHelper;
import flex.helpers.CSVHelper;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
/**
 *
 * @author flopez
 */
public class ArchivoCanaimita {
    private static Shell sShell = null;
    public static String nombreArchivo = "archivo_canaimita_";
    
    public static void probarArchivoCanaimita(Shell sShell) throws IOException {
        try {
        ArchivoCanaimita.sShell = sShell;
        
        File pdfsFolder = getDirectory(File.listRoots()[0], LanguageResource.getLanguage().getString("preferences.duplicar.archivo.canaimitas.contratos"));
        File archivoCanaimitaFolder = getDirectory(File.listRoots()[0], LanguageResource.getLanguage().getString("preferences.duplicar.archivo.canaimitas.archivo"));

        File archivoCanaimita = new File(archivoCanaimitaFolder.getAbsolutePath() + File.separator + nombreArchivo);

        List<List<String>> csv = new ArrayList<>();

        List<File> listaPDFs = FileDialogs.getFilesFromDir(pdfsFolder, FileDialogs.PDF_TYPE);
        for(File current : listaPDFs) {
                List<String> linea = new ArrayList<>();

                //Calcular correlativo
                String[] aux = current.getAbsolutePath().split("-");
                linea.add(aux[aux.length-1].split(".pdf")[0]);

                //Extraer información de la firma
                PdfReader reader = new PdfReader(current.getAbsolutePath());
                AcroFields af = reader.getAcroFields();
                String signatureName = (String)af.getSignatureNames().get(0);
                PdfPKCS7 p7 = af.verifySignature(signatureName);
                
                //Extraigo CN
                linea.add(CertificateHelper.getCN(p7.getSigningCertificate()));
                
                //Extraigo fecha
                String fecha = new SimpleDateFormat("YYYY/MM/dd").format(p7.getSignDate().getTime());              
                linea.add("en fecha " + fecha);
                
                csv.add(linea);
                System.out.println(linea);
        }
        
        CSVHelper.exportCSV(new File(archivoCanaimita.getAbsolutePath()), csv);
        
        } catch (Exception e) {
            e.printStackTrace();
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
}