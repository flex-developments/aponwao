package flex.aponwao.gui.sections.main.events;

import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.exceptions.SendPDFException;
import flex.aponwao.gui.sections.main.helpers.EnviarPDFHelper;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;


public class EnviarPDFProgress implements IRunnableWithProgress {
	private static final Logger logger = Logger.getLogger(EnviarPDFProgress.class.getName());
	private List<String> adjuntos = null;
        private String response = null;
        private String contraseña = null;
        private List<String> destinatarios = null;
        private List<String> copias = null;
        private List<String> copiasOculta = null;
        private String asunto = null;
        private String mensaje = null;
        private String alias = null;
        
	public EnviarPDFProgress(String alias, String contraseña, List<String> destinatarios, List<String> copias, List<String> copiasOcultas, String asunto, String mensaje, List<String> adjuntos, String response) {
            this.alias = alias;
            this.contraseña = contraseña;
            this.destinatarios = destinatarios;
            this.copias = copias;
            this.copiasOculta = copiasOcultas;
            this.asunto = asunto;
            this.mensaje = mensaje;
            this.adjuntos = adjuntos;
            this.response = response;
	}
        
        @Override
	public void run(IProgressMonitor monitor) throws InterruptedException, InvocationTargetException {
            if (!monitor.isCanceled()) {
                String m2 = LanguageResource.getLanguage().getString("info.email.sending");
                monitor.beginTask(m2 , IProgressMonitor.UNKNOWN);

                try {
                    EnviarPDFHelper.sendMail(alias, contraseña, destinatarios, copias, copiasOculta, asunto, mensaje, adjuntos);

                    if(response!=null) {
                        //Escribo el resultado en un archivo.
                        FileWriter fichero = null;
                        PrintWriter pw = null;
                        try {
                            fichero = new FileWriter(response);
                            pw = new PrintWriter(fichero);
                            String m3 = LanguageResource.getLanguage().getString("info.email.sent");
                            pw.println(m3);

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
                    String m = LanguageResource.getLanguage().getString("info.email.sent");
                    Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.INFO, m));

                } catch (MessagingException e) {
                    throw new InvocationTargetException(e);

                } catch (SendPDFException e) {
                    throw new InvocationTargetException(e, e.getLocalizedMessage());
                }

            } else {
                throw new InterruptedException(LanguageResource.getLanguage().getString("error.operacion_cancelada"));
            }
            monitor.done();
	}
}