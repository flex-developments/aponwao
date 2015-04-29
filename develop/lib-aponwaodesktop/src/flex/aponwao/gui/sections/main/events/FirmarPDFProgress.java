package flex.aponwao.gui.sections.main.events;

import flex.aponwao.core.exceptions.SignPDFDuplicateException;
import flex.aponwao.core.exceptions.SignPDFException;
import flex.aponwao.gui.application.ExportHelper;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.PDFInfo;
import flex.aponwao.gui.exceptions.FolderIsEmpyException;
import flex.aponwao.gui.exceptions.OverwritingException;
import flex.aponwao.gui.sections.global.windows.FileDialogs;
import flex.aponwao.gui.sections.main.helpers.FirmarPDFHelper;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;


public class FirmarPDFProgress implements IRunnableWithProgress {
    //OJO... Modificacion Felix
    private String response = null;
    private boolean duplicar = false;
    int inicioCorrelativo, finCorrelativo;
    //**********************************************************************
    private static final Logger logger = Logger.getLogger(FirmarPDFProgress.class.getName());
    private String alias = null;
    private List<PDFInfo> pdfListaParaFirmar = null;

    //Lo llama el firmar comun y silvestre
    public FirmarPDFProgress(String alias, List<PDFInfo> pdfListaParaFirmar, String response) {
            this.alias = alias;
            this.pdfListaParaFirmar = pdfListaParaFirmar;
            this.response = response;
    }

    //Lo llama el firmar duplicados
    public FirmarPDFProgress(String alias, List<PDFInfo> pdfListaParaFirmar, int inicioCorrelativo, int finCorrelativo, String response) {
            this(alias, pdfListaParaFirmar, response);
            this.duplicar = true;
            this.inicioCorrelativo=inicioCorrelativo;
            this.finCorrelativo=finCorrelativo;
    }

    @Override
    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        List<PDFInfo> aux = new ArrayList<PDFInfo>();
        
        for (PDFInfo pdfParaFirmar : this.pdfListaParaFirmar) {
                aux.clear();
                
                if (pdfParaFirmar.getDir()){
                        List<File> fileList = FileDialogs.getFilesFromDir(new File (pdfParaFirmar.getOrigen().substring(0, 
                                pdfParaFirmar.getOrigen().lastIndexOf("/"))), FileDialogs.PDF_TYPE);
                        
                        String aux1 = pdfParaFirmar.getOrigen().substring(0, pdfParaFirmar.getOrigen().lastIndexOf("/"));
                        aux1 = PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_FINAL_FOLDER_VALUE) + 
                                aux1.substring(aux1.lastIndexOf("/"), aux1.length()) + "-Firmados";
                        
                        boolean success = false;
                        try {
                            success = (new File(aux1)).mkdir();
                        
                        } catch (Exception e) {
                                String m = LanguageResource.getLanguage().getString("error.folder.no.create");
                                logger.log(Level.WARNING, m, e);
                                Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
                        } 
                        
                        for (File file : fileList) {
                                PDFInfo pdfReaderSign = new PDFInfo();
                                pdfReaderSign.setOrigen(file.getAbsolutePath());
                                if (success)
                                    pdfReaderSign.setDestino(aux1);
                                aux.add(pdfReaderSign);
                        }     
                } else
                    aux.add(pdfParaFirmar);
        
                //Validar entrada de datos de base de datos o de directorio
                if (PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_SOURCE).equals(PreferencesHelper.APLICATION_PATH_SOURCE_FOLDER)) {

                    for (PDFInfo pdfParaFirmar1 : aux)
                        procesar(monitor, pdfParaFirmar1);

                } else {
                    for (PDFInfo pdfParaFirmar2 : aux) {

                        List<PDFInfo> auxiliar = listarDirectorio(pdfParaFirmar2.getOrigen(), pdfParaFirmar2.getDestino());
                        if(!auxiliar.isEmpty()) {
                            for (PDFInfo pdf : auxiliar)
                                procesar(monitor, pdf);

                        } else {
                            String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.folder.empy"), pdfParaFirmar.getOrigen());
                            throw new InvocationTargetException(new FolderIsEmpyException(m));
                        }
                    }
                }
                
                pdfParaFirmar.setSigned(true);
        }
        monitor.done();
    }
    
    private List<PDFInfo> listarDirectorio(String dirOrigen, String dirDestino) {
        File destino = new File(dirDestino);
        destino.mkdirs();
        
        List<File> fileList = new ArrayList<File>();
        if (dirOrigen != null) {
                File dirFile = new File(dirOrigen);
                fileList = FileDialogs.getFilesFromDir(dirFile, FileDialogs.PDF_TYPE);
        }
        
        List<PDFInfo> result = new ArrayList<PDFInfo>();
        for(File ruta: fileList) {
            PDFInfo temp = new PDFInfo();
            temp.setOrigen(ruta.getAbsolutePath());
            temp.setDestino(dirDestino + "/" + ruta.getName());
            result.add(temp);
        }
        return result;

    }
    
    private void procesar(IProgressMonitor monitor, PDFInfo pdfParaFirmar) throws InterruptedException, InvocationTargetException {
        File temp = new File(pdfParaFirmar.getOrigen());
        if (!monitor.isCanceled()) {
                String m2 = MessageFormat.format(LanguageResource.getLanguage().getString("info.document.signing"), temp.getName());
                monitor.beginTask(m2 , IProgressMonitor.UNKNOWN);

                try {
                    boolean firmado = false;
                        try {
                            if(!duplicar) firmado = FirmarPDFHelper.firmar(alias, pdfParaFirmar);
                            else firmado = FirmarPDFHelper.firmarDuplicados(alias, pdfParaFirmar, inicioCorrelativo, finCorrelativo);
                            
                            if (firmado){
                                String m = MessageFormat.format(LanguageResource.getLanguage().getString("info.document.signed"), temp.getName());
                                Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.INFO, m));
                                
                                // actualizo la entrada de la tabla
//                              String fileDestino = ExportHelper.getOutputDir(temp) + File.separatorChar + ExportHelper.getOutputName(temp.getName());
                                String fileDestino = pdfParaFirmar.getDestino();
                                // pongo las opciones del pdf a firmado, para que se muestren en la tabla.
                                pdfParaFirmar.setSigned(true);
                                pdfParaFirmar.setOrigen(fileDestino);
                                pdfParaFirmar.setSignatures(null);
                            }
                            
                            if(response!=null) {
                                //Escribo el resultado en un archivo.
                                FileWriter fichero = null;
                                PrintWriter pw = null;
                                try {
                                    fichero = new FileWriter(response);
                                    pw = new PrintWriter(fichero);
                                    pw.println(ExportHelper.getOutputDir(temp) + File.separatorChar + ExportHelper.getOutputName(temp.getName()));

                                } catch (IOException e) {
                                    String m = LanguageResource.getLanguage().getString("error.archivo.respuesta");
                                    logger.log(Level.WARNING, m, e);
                                    Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));

                                } finally {
                                    try {
                                        if (null != fichero) fichero.close();
                                    } catch (Exception e2) {
                                        String m = LanguageResource.getLanguage().getString("error.runtime");
                                        logger.log(Level.WARNING, m, e2);
                                        Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
                                    }
                                }
                            }

                        } catch (IOException e) {
                            String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.certificate.sign.unexpected"),
                                                temp.getName(), e.getCause().toString());
                            logger.log(Level.SEVERE, m, e);
                        }

                } catch (SignPDFException ex) {
                    String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.certificate.sign.unexpected"),
                                            temp.getName(), ex.getCause().toString());
                    logger.log(Level.SEVERE, m, ex);
                    Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
                    
                } catch (SignPDFDuplicateException e) { // el bucle continua

                    String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.certificate.sign.duplicate.unexpected"),
                                            temp.getName(), e.getCause().toString());

                    logger.log(Level.SEVERE, m, e);
                    Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));

                } catch (OverwritingException e) { // el bucle continua

                    String fileDestino = ExportHelper.getOutputName(temp.getName());

                    String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.overwrite"),
                                    temp.getName(), fileDestino);

                    logger.log(Level.SEVERE, m, e);
                    Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));

                }

        } else {
                throw new InterruptedException("The long running operation was cancelled");
        }
        temp = null;
    }
}