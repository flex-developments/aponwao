package flex.aponwao.gui.sections.main.events;

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

import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.PDFInfo;
import flex.aponwao.gui.application.PDFSignatureInfo;
import flex.aponwao.gui.exceptions.DocumentValidationException;
import flex.aponwao.gui.sections.main.helpers.ValidarPDFHelper;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;


public class ValidarPDFProgress implements IRunnableWithProgress {
	
	private static Logger logger = Logger.getLogger(FirmarPDFProgress.class.getName());

	private List<PDFInfo> pdfParameterList = null;
        
        //OJO... Modificacion Felix
        private String response = null;
        
	public ValidarPDFProgress(List<PDFInfo> pdfParameter, String response) {
		this.pdfParameterList = pdfParameter;
                this.response = response;
	}
        //**********************************************************************

	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		
		for (PDFInfo pdfParameter : this.pdfParameterList) {
                        File temp = new File(pdfParameter.getOrigen());
			if (!monitor.isCanceled()) {
				String m2 = MessageFormat.format(LanguageResource.getLanguage().getString("info.document.validating"), temp.getName());
				monitor.beginTask(m2 , IProgressMonitor.UNKNOWN);
                                
				try  {
                                    List<String> mensaje = new ArrayList<String>();
                                    List<PDFSignatureInfo> pdfSignaturesListAux = new ArrayList<PDFSignatureInfo>();
				    pdfSignaturesListAux = ValidarPDFHelper.validatePDF(pdfParameter);
                                        
                                        if(ValidarPDFHelper.getSignatarios().size() > 0) {
                                            //Agrego en [0]
                                            mensaje.add(MessageFormat.format(LanguageResource.getLanguage().getString("info.document.validated"), temp.getName()));
                                            //Agrego en [1]
                                            mensaje.add("\n" + LanguageResource.getLanguage().getString("info.signature.in.document") + "\n");
                                            //Agrego desde [2] hasta [mensaje.size()]
                                            for (int i = 0; i < ValidarPDFHelper.getSignatarios().size(); i++) {
                                               String m= ValidarPDFHelper.getSignatarios().get(i);

                                                /////////////OJO... Modificacion Yessica
                                                if (pdfSignaturesListAux.size() > 0){
                                                    if (pdfSignaturesListAux.get(i).getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_VALID) {
                                                        m = m + " " + LanguageResource.getLanguage().getString("info.certificate.dependable");
                                                    } else if (pdfSignaturesListAux.get(i).getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_ERROR) {
                                                        m = m + " " + LanguageResource.getLanguage().getString("info.certificate.unknown");
                                                    } else if (pdfSignaturesListAux.get(i).getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_INVALID) {
                                                        m = m + " " + LanguageResource.getLanguage().getString("info.signature.invalid");
                                                    } else {
                                                        m = m + " " + LanguageResource.getLanguage().getString("info.certificate.warning");
                                                    }
                                                }
                                                /////////////////////////////////////////
                                                m = m + "\n     " + ValidarPDFHelper.getFechasFirmas().get(i);
                                                if (i<(ValidarPDFHelper.getSignatarios().size()-1)) m = m + "\n";
                                                mensaje.add(m);
                                            }
                                            
                                            //Imprimo resultado en la ventana de salida
                                            String mostrar = "";
                                            for(int x=0; x<mensaje.size(); x++) {
                                                mostrar = mostrar + mensaje.get(x);
                                            }
                                            
                                            if(pdfSignaturesListAux.get(0).getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_VALID) {
                                                Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.INFO, mostrar));
                                            } else if(pdfSignaturesListAux.get(0).getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_INVALID) {
                                                Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, mostrar));
                                            } else if(pdfSignaturesListAux.get(0).getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_WARNING) {
                                                Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.WARNING, mostrar));
                                            }
                                            
                                        } else {
                                            //Imprimo resultado en la ventana de salida
                                            mensaje.add(""); mensaje.add(""); //Relleno [0] y [1] con vacio para que la siguiente linea aparezca en el archivo de respuesta
                                            mensaje.add(MessageFormat.format(LanguageResource.getLanguage().getString("info.document.sin.firmar"), temp.getName()));
                                            String mostrar = "";
                                            for(int x=0; x<mensaje.size(); x++) {
                                                mostrar = mostrar + mensaje.get(x);
                                            }
                                            Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.INFO, mostrar));
                                        }
                                        
                                        //Escribo el resultado en un archivo de ser necesario.
                                        //0-Sin Firma, 1-Firma Valida (Certificado confiable), 2-Firma Invalida, 3-Firma Valida (Certificado desconocido), 4-Error durante la comprobacion del cert
                                        if(response!=null) {
                                            FileWriter fichero = null;
                                            PrintWriter pw = null;
                                            try {
                                                fichero = new FileWriter(response);
                                                pw = new PrintWriter(fichero);
                                                String mostrar = "";
                                                if (pdfSignaturesListAux.size() > 0){
                                                    if (pdfSignaturesListAux.get(0).getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_VALID) {
                                                        mostrar = mostrar + "1\n";
                                                        
                                                    } else if (pdfSignaturesListAux.get(0).getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_INVALID) {
                                                        mostrar = mostrar + "2\n";
                                                        
                                                    } else if (pdfSignaturesListAux.get(0).getSignerSimpleStatus() == PDFSignatureInfo.SIMPLE_STATUS_ERROR) {
                                                        mostrar = mostrar + "3\n";
                                                        
                                                    } else {
                                                        mostrar = mostrar + "4\n";
                                                        
                                                    }
                                                } else mostrar = mostrar + "0\n";
                                                
                                                //Arranco el for en 2 porque las primeras 2 lineas no hacen falta para el archivo de respuesta.
                                                for(int x=2; x<mensaje.size(); x++) {
                                                    mostrar = mostrar + mensaje.get(x);
                                                }
                                                pw.println(mostrar);

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
                                        
				} catch (DocumentValidationException e) {
					
					String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.certificate.validation.unexpected"),
							temp.getName(), e.getCause().toString());
					logger.log(Level.SEVERE, m, e);
					Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
				}
				
			} else {
				
				throw new InterruptedException("The long running operation was cancelled");
			}
			temp = null;
		}
		
		monitor.done();
	}
	
}