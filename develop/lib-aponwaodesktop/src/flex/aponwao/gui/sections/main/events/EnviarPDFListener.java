package flex.aponwao.gui.sections.main.events;

import flex.aponwao.core.exceptions.CoreException;
import flex.aponwao.core.exceptions.CorePKCS12Exception;
import flex.aponwao.core.keystore.KeyStoreBuilderFactory;
import flex.aponwao.gui.application.DesktopHelper;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.LoggingDesktopController;
import flex.aponwao.gui.application.PDFInfo;
import flex.aponwao.gui.exceptions.SendPDFException;
import flex.aponwao.gui.sections.global.windows.MailDialog;
import flex.aponwao.gui.sections.main.helpers.EnviarPDFHelper;
import flex.aponwao.gui.sections.main.windows.AliasDialog;
import flex.aponwao.gui.sections.main.windows.TablePDF;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import flex.eSign.helpers.exceptions.CertificateHelperException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import sun.security.pkcs11.wrapper.PKCS11Exception;

public class EnviarPDFListener implements SelectionListener {
    private static final Logger	logger	= Logger.getLogger(EnviarPDFListener.class.getName());
    private TablePDF tablePDF = null;
    private String response = null;
    private boolean ksloaded = false;

    public void setKsloaded(boolean ksloaded) {
        this.ksloaded = ksloaded;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }

    public EnviarPDFListener (TablePDF t) {
            this.tablePDF = t;
    }
	
    @Override
    public void widgetSelected(SelectionEvent event) {
        if (!tablePDF.getSelectedPDFs().isEmpty()) {
            if(PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_CLIENT).toUpperCase().equals(PreferencesHelper.EMAIL_CLIENT_APONWAO)) {
                try {
                    //Envio los correos directamente desde java
                    enviar();
                } catch (CertificateHelperException ex) {
                    Logger.getLogger(EnviarPDFListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //Llamo al cliente de correo
                DesktopHelper.openDefaultMailClient(getAdjuntos());
            }
        
        } else {
            logger.severe(LanguageResource.getLanguage().getString("error.no_selected_file"));
            LoggingDesktopController.printError(LanguageResource.getLanguage().getString("error.no_selected_file"));
        }
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent event) {
            widgetSelected(event);
    }
    
    private List<String> getAdjuntos() {
        List<String> adjuntos = new ArrayList<String>();
        for (PDFInfo pdfParameter : tablePDF.getSelectedPDFs()) {
                adjuntos.add(pdfParameter.getOrigen());
        }
        return adjuntos;
    }
    
    private String stringAdjuntos() {
        String adjuntos = "";
        for (PDFInfo pdfParameter : tablePDF.getSelectedPDFs()) {
            if(!adjuntos.equals("")) adjuntos = adjuntos + ", ";
            File tmp = new File(pdfParameter.getOrigen());
            adjuntos = adjuntos + tmp.getName();
            tmp = null;
            System.gc();
        }
        return adjuntos;
    }
    
    public void enviar() throws CertificateHelperException {
        String alias = null;
        
        if(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.EMAIL_SIGN)) {
            if(!ksloaded) {
                try {
                    try {
                            ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(tablePDF.getShell());
                            progressMonitorDialog.run(true, true, new LoadKeyStoreProgress(tablePDF.getShell()));

                    } catch (InvocationTargetException e) {
                            if (e.getCause().getClass().getName().equals("java.security.KeyStoreException")) EnviarPDFHelper.loadKeyStore(tablePDF.getShell());
                            else if (e.getCause().getClass().getName().equals("org.eclipse.swt.SWTException")) EnviarPDFHelper.loadKeyStore(tablePDF.getShell());
                            else if (e.getCause().getClass().getName().equals("sun.security.pkcs11.wrapper.PKCS11Exception")) throw (PKCS11Exception) e.getCause();
                            else if (e.getCause().getClass().getName().equals("java.security.NoSuchAlgorithmException")) throw (NoSuchAlgorithmException) e.getCause();
                            else if (e.getCause().getClass().getName().equals("flex.aponwao.core.exceptions.CoreException")) throw (CoreException) e.getCause();
                    }
                } catch (CorePKCS12Exception e) {
                        String m = LanguageResource.getLanguage().getString("error.pkcs12_pin_incorrecto");
                        logger.log(Level.SEVERE, m, e);
                        LoggingDesktopController.printError(m);
                        return;
                } catch (PKCS11Exception e) {
                        if (e.getMessage().equals(KeyStoreBuilderFactory.CKR_PIN_INCORRECT)) {
                                String m = LanguageResource.getLanguage().getString("error.pin_incorrecto");
                                logger.log(Level.SEVERE, m, e);
                                LoggingDesktopController.printError(m);

                        } else if (e.getMessage().equals(KeyStoreBuilderFactory.CKR_PIN_LOCKED)) {
                                String m = LanguageResource.getLanguage().getString("error.tarjeta_bloqueada");
                                logger.log(Level.SEVERE, m, e);
                                LoggingDesktopController.printError(m);

                        } else { //Otros errores
                                String m = LanguageResource.getLanguage().getString("error.cargar_store");
                                logger.log(Level.SEVERE, m, e);
                                LoggingDesktopController.printError(m);
                        }
                        return;
                } catch (KeyStoreException e){	// Causas: -Si se pulsa cancelar en el password hander. (Si esta no se da en más casos habria que quitar el error.)
                        String m = LanguageResource.getLanguage().getString("error.cargar_store");
                        logger.log(Level.SEVERE, m, e);
                        LoggingDesktopController.printError(m);
                        return;
                } catch (InterruptedException e) {
                        String m = LanguageResource.getLanguage().getString("error.operacion_cancelada");
                        logger.log(Level.SEVERE, m, e);
                        LoggingDesktopController.printError(m);
                        return;
                } catch (NoSuchAlgorithmException e) {
                        String m = LanguageResource.getLanguage().getString("error.tarjeta_incorrecta");
                        logger.log(Level.SEVERE, m, e);
                        LoggingDesktopController.printError(m);
                        return;
                } catch (CoreException e) {
                        String m = LanguageResource.getLanguage().getString("error.configuracion_incorrecta");
                        logger.log(Level.SEVERE, m, e);
                        LoggingDesktopController.printError(m);
                        return;
                } catch (RuntimeException e) {
                        //Cuando se cancela el de tipo PKCS12 pasa por aqui.
                        return;
                }
            }
            
            //Si el keystore tiene más de un alias selecciono uno de ellos
            String selectedAlias = null;

            List<String> aliases = EnviarPDFHelper.getAlias();

            if (aliases.size() > 1) {
                    AliasDialog solicitarAliasDialog = new AliasDialog(tablePDF.getShell());
                    selectedAlias = solicitarAliasDialog.open(aliases);

                    if (selectedAlias == null) {
                            return;
                    } else {
                            alias = selectedAlias;
                    }
            } else {
                    alias = aliases.get(0);
            }
        }
        
        //Barra de progreso con el envío de los correos
        try {
            MailDialog ventana = new MailDialog(tablePDF.getShell());
            String[] parametros = ventana.open(stringAdjuntos());
            if(parametros!=null) {
                String separator = PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_ADDRESSES_SEPARATOR);
                if((separator.equals(""))||(separator == null)) throw new InvocationTargetException(new SendPDFException(LanguageResource.getLanguage().getString("error.email.no_address.separator")));
                
                List<String> destinatarios = new ArrayList<String>();
                List<String> copias = new ArrayList<String>();
                List<String> copiasOcultas = new ArrayList<String>();
                String[] auxiliar = null;
                
                if(!parametros[1].equals("")) {
                    auxiliar = parametros[1].split(PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_ADDRESSES_SEPARATOR));
                    destinatarios.addAll(Arrays.asList(auxiliar));
                    auxiliar = null;
                } else {
                    destinatarios = null;
                }
                
                if(!parametros[2].equals("")) {
                    auxiliar = parametros[2].split(PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_ADDRESSES_SEPARATOR));
                    copias.addAll(Arrays.asList(auxiliar));
                    auxiliar = null;
                } else {
                    copias = null;
                }
                
                if(!parametros[3].equals("")) {
                    auxiliar = parametros[3].split(PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_ADDRESSES_SEPARATOR));
                    copiasOcultas.addAll(Arrays.asList(auxiliar));
                    auxiliar = null;
                } else {
                    copiasOcultas = null;
                }

                if (parametros[0] != null) {
                    ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(tablePDF.getShell());
                    progressMonitorDialog.run(true, true, 
                                                  new EnviarPDFProgress(
                                                        alias,
                                                        parametros[0],
                                                        destinatarios,
                                                        copias,
                                                        copiasOcultas,
                                                        parametros[4],
                                                        parametros[5],
                                                        getAdjuntos(),
                                                        response
                                                  )
                                              );
                }
            }
            ventana = null;
            
        } catch (InvocationTargetException e) {
            String m = e.getCause().getLocalizedMessage();
            if (!(e.getCause() instanceof SendPDFException)) {
                m = LanguageResource.getLanguage().getString("error.email");
            }
            logger.log(Level.SEVERE, m, e);
            LoggingDesktopController.printError(m);
            return;

        } catch (InterruptedException e) {
            String m = LanguageResource.getLanguage().getString("error.operacion_cancelada");
            logger.log(Level.SEVERE, m, e);
            LoggingDesktopController.printError(m);
            return;
        }
        tablePDF.reloadTable();
    }
}