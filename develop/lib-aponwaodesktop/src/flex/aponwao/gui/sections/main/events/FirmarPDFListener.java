/*
 * # Copyright 2008 zylk.net
 * #
 * # This file is part of Aponwao.
 * #
 * # Aponwao is free software: you can redistribute it and/or modify
 * # it under the terms of the GNU General Public License as published by
 * # the Free Software Foundation, either version 2 of the License, or
 * # (at your option) any later version.
 * #
 * # Aponwao is distributed in the hope that it will be useful,
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * # GNU General Public License for more details.
 * #
 * # You should have received a copy of the GNU General Public License
 * # along with Aponwao. If not, see <http://www.gnu.org/licenses/>. [^]
 * #
 * # See COPYRIGHT.txt for copyright notices and details.
 * #
 */

package flex.aponwao.gui.sections.main.events;


import flex.aponwao.core.exceptions.CoreException;
import flex.aponwao.core.exceptions.CorePKCS12Exception;
import flex.aponwao.core.exceptions.SignPDFException;
import flex.aponwao.core.keystore.KeyStoreBuilderFactory;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.LoggingDesktopController;
import flex.aponwao.gui.exceptions.FolderIsEmpyException;
import flex.aponwao.gui.sections.global.windows.InputDialog;
import flex.aponwao.gui.sections.main.helpers.FirmarPDFHelper;
import flex.aponwao.gui.sections.main.windows.AliasDialog;
import flex.aponwao.gui.sections.main.windows.TablePDF;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import flex.eSign.operators.exceptions.CadenaConfianzaException;
import flex.eSign.operators.exceptions.CertificadoInvalidoException;
import flex.eSign.operators.exceptions.CertificadoRevocadoException;
import flex.eSign.operators.exceptions.OCSPServerException;
import flex.eSign.operators.exceptions.PrivadaInvalidaException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import sun.security.pkcs11.wrapper.PKCS11Exception;


public class FirmarPDFListener implements SelectionListener {
	//OJO... Modificacion Felix
        private boolean duplicar = false;
        private boolean ksloaded = false;
        private String response = null;
        
        public void setKsloaded(boolean ksloaded) {
            this.ksloaded = ksloaded;
        }

        public void setResponse(String response) {
            this.response = response;
        }
        //**********************************************************************
	private static final Logger logger = Logger.getLogger(FirmarPDFListener.class.getName());
	
	private TablePDF tablePDF = null;
	
        
	public FirmarPDFListener (TablePDF t, boolean duplicar) {
		this.duplicar = duplicar;
		this.tablePDF = t;
	}
	
        @Override
	public void widgetSelected(SelectionEvent event) {
		firmarPDF();
	}

        @Override
	public void widgetDefaultSelected(SelectionEvent event) {
		widgetSelected(event);
	}
        
        public void firmarPDF() {
            if (!tablePDF.getSelectedPDFs().isEmpty()) {
                if(!ksloaded) {
                    try {
                            FirmarPDFHelper.loadKeyStore(tablePDF.getShell());
                            
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
                String alias = null;
                String selectedAlias = null;

                List<String> aliases = FirmarPDFHelper.getAlias();

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
                
                // barra con el proceso de firma de los PDF
                try {
                    //Verificamos el certificado antes de firmar
                    try {
                        FirmarPDFHelper.verificarCertificado(alias);
                    } catch (SignPDFException ex) {
                            logger.log(Level.SEVERE, "en el proceso de firma.", ex);

                            if (ex.getCause() instanceof CertificadoInvalidoException) {
                                // si la causa es una exception de certificado invalido
                                String m = MessageFormat.format(
                                        LanguageResource.getLanguage().getString("error.certificate.invalid"),
                                        ((CertificadoInvalidoException)ex.getCause()).getMetodoVerificacion()
                                );
                                logger.log(Level.SEVERE, m, ex);
                                LoggingDesktopController.printError(m);

                            } else if (ex.getCause() instanceof PrivadaInvalidaException) {
                                //si la causa es una exception de privada invalida            
                                String m = MessageFormat.format(
                                        LanguageResource.getLanguage().getString("error.private.key.invalid"),
                                        ((PrivadaInvalidaException)ex.getCause()).getMetodoVerificacion()
                                );
                                logger.log(Level.SEVERE, m, ex);
                                LoggingDesktopController.printError(m);

                            } else if (ex.getCause() instanceof CadenaConfianzaException) {
                                // si la causa es una exception de certificado revocado
                                String m = LanguageResource.getLanguage().getString("error.certificate.path");
                                logger.log(Level.SEVERE, m, ex);
                                LoggingDesktopController.printError(m);

                            } else if (ex.getCause() instanceof CertificadoRevocadoException) {
                                // si la causa es una exception de certificado revocado
                                String m = MessageFormat.format(
                                        LanguageResource.getLanguage().getString("error.certificate.revoked"),
                                        ((CertificadoRevocadoException)ex.getCause()).getMetodoVerificacion()
                                );
                                logger.log(Level.SEVERE, m, ex);
                                LoggingDesktopController.printError(m);

                            } else if (ex.getCause() instanceof OCSPServerException) {
                                // si la causa es una exception de certificado revocado
                                String m = LanguageResource.getLanguage().getString("error.certificate.ocsp_error");
                                logger.log(Level.SEVERE, m, ex);
                                LoggingDesktopController.printError(m);
                            }
                            throw new InvocationTargetException(ex.getCause());
                    }
                
                    //OJO... Modificacion Felix y Yessica
                    ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(tablePDF.getShell());
                    if(!duplicar) {
                        //Ejecución de Firma
                        progressMonitorDialog.run(true, true, new FirmarPDFProgress(alias, tablePDF.getSelectedPDFs(), response));
                        
                    } else {
                        //Capturar correlativos
                        InputDialog inputDialog = new InputDialog(tablePDF.getShell());
                        int inicioCorrelativo = 0, finCorrelativo = 0;
                        
                        String auxiliar = null;
                        auxiliar = inputDialog.open(LanguageResource.getLanguage().getString("input.dialog.duplicar.inicio.correlativo"));
                        if (auxiliar == null || auxiliar.equals("")) auxiliar = "0";
                        inicioCorrelativo = Integer.parseInt(auxiliar);
                        
                        
                        inputDialog = null; auxiliar = null;
                        inputDialog = new InputDialog(tablePDF.getShell());
                        auxiliar = inputDialog.open(LanguageResource.getLanguage().getString("input.dialog.duplicar.fin.correlativo"));
                        if (auxiliar == null || auxiliar.equals("")) auxiliar = "0";
                        finCorrelativo = Integer.parseInt(auxiliar);
                        //Validación de correlativos
                        if((inicioCorrelativo == 0 || finCorrelativo == 0) || (finCorrelativo < inicioCorrelativo)){
                            String m = LanguageResource.getLanguage().getString("error.dialog.duplicar");
                            logger.log(Level.SEVERE, m);
                            Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
                            
                        } else {
                            //Ejecución de Firma
                            progressMonitorDialog.run(true, true, new FirmarPDFProgress(alias, tablePDF.getSelectedPDFs(), inicioCorrelativo, finCorrelativo, response));
                            //Pregunto si quiere copiar los duplicados
                            if (PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APLICATION_COPIAR_DUPLICADOS)) {
                                //Preguntar si desea copiar los archivos
                                String m = LanguageResource.getLanguage().getString("info.copiar.duplicados");
                                int copiar = JOptionPane.showOptionDialog(
                                    null,
                                    m,
                                    LanguageResource.getLanguage().getString("info.dialog.title"),
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    null,
                                    null);
                                if(copiar == JOptionPane.YES_OPTION) {
                                    //Preguntar si desea mover los archivos a respaldos
                                    m = LanguageResource.getLanguage().getString("info.copiar.duplicados.respaldar");
                                    int respaldar = JOptionPane.showOptionDialog(
                                        null,
                                        m,
                                        LanguageResource.getLanguage().getString("info.dialog.title"),
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        null,
                                        null);
                                    if(respaldar == JOptionPane.YES_OPTION) flex.aponwao.gui.sections.main.helpers.CopiarDuplicadosHelper.copiarDuplicados(tablePDF.getShell(), true);
                                    else flex.aponwao.gui.sections.main.helpers.CopiarDuplicadosHelper.copiarDuplicados(tablePDF.getShell(), false);
                                }
                            }
                        }
                    }
                    //******************************************
                } catch (InvocationTargetException e) {

                        logger.log(Level.SEVERE, "en el proceso de firma.", e);
                        if (e.getCause() instanceof FolderIsEmpyException) {
                            logger.log(Level.SEVERE, e.getCause().getLocalizedMessage(), e.getCause());
                            LoggingDesktopController.printError(e.getCause().getLocalizedMessage());
                        }

                        // Si la causa es una exception de conexion en la tsa
    //					catch (CoreTSAException e) {				
    //					}

                } catch (InterruptedException e) {

                        logger.log(Level.SEVERE, "en el proceso de firma.", e);
                        String m = LanguageResource.getLanguage().getString("error.operacion_cancelada");
                        LoggingDesktopController.printError(m);
                        logger.severe(m);					
                }

                // cierro la session con la tarjeta para que pida el pin otra vez después de una firma masiva o una firma simple
                if(PreferencesHelper.getPreferences().getString(PreferencesHelper.CERT_TYPE).equals(PreferencesHelper.CERT_HARDWARE_TYPE)) FirmarPDFHelper.logout();

                tablePDF.reloadTable();
			
            } else {
                    logger.severe(LanguageResource.getLanguage().getString("error.no_selected_file"));
                    LoggingDesktopController.printError(LanguageResource.getLanguage().getString("error.no_selected_file"));
            }
        }
}