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

package flex.aponwao.gui.sections.main.helpers;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.OcspClientBouncyCastle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPKCS7;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.TSAClient;
import com.itextpdf.text.pdf.TSAClientBouncyCastle;
import flex.aponwao.core.exceptions.CoreException;
import flex.aponwao.core.exceptions.CorePKCS12Exception;
import flex.aponwao.core.exceptions.SignPDFDuplicateException;
import flex.aponwao.core.exceptions.SignPDFException;
import flex.aponwao.core.keystore.KeyStoreBuilderFactory;
import flex.aponwao.core.keystore.KeyStoreBuilderFactory.KeyStoreTypes;
import flex.aponwao.gui.application.ExportHelper;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.PDFInfo;
import flex.aponwao.gui.exceptions.OverwritingException;
import flex.aponwao.gui.sections.main.events.ProgressWriter;
import flex.aponwao.gui.sections.preferences.helpers.ImageSignPreferences;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import flex.aponwao.gui.sections.preferences.helpers.TimeStampPreferences;
import flex.eSign.helpers.CertificateHelper;
import flex.eSign.helpers.exceptions.CertificateHelperException;
import flex.eSign.operators.VerificadorCertificadoOperator;
import flex.eSign.operators.exceptions.CadenaConfianzaException;
import flex.eSign.operators.exceptions.CertificadoInvalidoException;
import flex.eSign.operators.exceptions.CertificadoRevocadoException;
import flex.eSign.operators.exceptions.OCSPServerException;
import flex.eSign.operators.exceptions.PrivadaInvalidaException;
import flex.eSign.operators.exceptions.VerificadorCertificadoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.security.auth.login.LoginException;
import javax.swing.JOptionPane;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import sun.security.pkcs11.SunPKCS11;
import sun.security.pkcs11.wrapper.PKCS11Exception;
import sun.security.validator.KeyStores;

public class FirmarPDFHelper {
        private static final int DEFAULT_ROTACION_LETRA = 0;
        
        private static boolean sobreescribir = false;
	private static final Logger logger = Logger.getLogger(FirmarPDFHelper.class.getName());
	private static KeyStore ks = null;
	private static PasswordProtection passwordProtection = null;
        private static final List<String> temp = new ArrayList<>();
        
        // diferencial entre el sistema de medición de itext y el de esta pantalla
        private static final Float RELACION = (float) 1.375816993;
        
        //Operaciones sobre Keystore********************************************
	public static void loadKeyStore(Shell sShell) throws NoSuchAlgorithmException, KeyStoreException, PKCS11Exception, CoreException, CorePKCS12Exception {
		String certificadoType = PreferencesHelper.getPreferences().getString(PreferencesHelper.CERT_TYPE);
		
		String pkcs12Path = PreferencesHelper.getSoftwarePreferences().get(
				PreferencesHelper.getPreferences().getString(PreferencesHelper.SOFTWARE_DISPOSITIVE));

		String pkcs11Path = PreferencesHelper.getHardwarePreferences().get(
				PreferencesHelper.getPreferences().getString(PreferencesHelper.HARDWARE_DISPOSITIVE));
                
                
                
                PasswordCallbackHandlerDialog o = new PasswordCallbackHandlerDialog(sShell);
		
		if (certificadoType.equalsIgnoreCase(PreferencesHelper.CERT_HARDWARE_TYPE)) {
                    ks = KeyStoreBuilderFactory.getKeyStore("HARD", KeyStoreTypes.PKCS11, pkcs11Path, new KeyStore.CallbackHandlerProtection(o));
			
		} else if (certificadoType.equalsIgnoreCase(PreferencesHelper.CERT_SOFTWARE_TYPE)) {
                    ks = KeyStoreBuilderFactory.getKeyStore("SOFT", KeyStoreTypes.PKCS12, pkcs12Path, new KeyStore.CallbackHandlerProtection(o));
		}
		// fijo el passwordprotection para el PKCS12, para el PKCS11 no es necesario pero por coherencia lo uso tambien.
		passwordProtection = o.getPasswordProtection();
                o = null;
	}

	public static void logout() {
		// Una vez que se ha firmado ... hago un logout de la session del provider
		try {
			((SunPKCS11) ks.getProvider()).logout();
			
                } catch (ClassCastException e) {
			// TODO: si el provider es de tipo PKCS12 no se puede hacer el cast ni el logout pero no es necesario
			logger.log(Level.INFO, "Controled exception", e);
		} catch (LoginException e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

	public static List<String> getAlias() {
		Enumeration<String> aliases = null;
		List<String> list = new ArrayList<>();
		try {
			aliases = ks.aliases();
			while (aliases.hasMoreElements()) {
				String string = (String) aliases.nextElement();
				list.add(string);
			}
		} catch (KeyStoreException e) {
			logger.log(Level.SEVERE, "Problemas leyendo los alias de la tarjeta", e);
		}

		return (list);
	}
        
        public static void setKs(KeyStore ks) {
            FirmarPDFHelper.ks = ks;
        }

        public static KeyStore getKs() {
            return ks;
        }
        
        //Operaciones sobre el certificado**************************************
        public static void verificarCertificado(String alias) throws SignPDFException {
            try {
                X509Certificate certFirmante = (X509Certificate) ks.getCertificate(alias);
                VerificadorCertificadoOperator.verificarFechas(certFirmante, null);
                
                if(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.VALIDATE_OCSP_ENABLE)){
                    //Armo lista de certificados a verificar
                    List<X509Certificate> certificados = new ArrayList<>();
                    certificados.add(certFirmante);

                    //Obtengo URL OCSP
                    String ocspURL = CertificateHelper.getURLOCSP(certFirmante).toString();

                    //Armo lista de autoridades
                    List<X509Certificate> certACs = new ArrayList<>();
                    Set<X509Certificate> set = KeyStores.getTrustedCerts(PreferencesHelper.getCacheKeystorePreferences());
                    for (X509Certificate cert : set) certACs.add(cert);
                    set = null;
                    System.gc();
                    try {
                        VerificadorCertificadoOperator.verificarCadenaYOCSP(certificados, certACs, ocspURL);
                    } catch (OCSPServerException ex) {
                        if(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.VALIDATE_OCSP_VERIFY_ON_ERROR)) throw ex;
                    }
                }
                    
            } catch (CertificadoInvalidoException | PrivadaInvalidaException | CadenaConfianzaException | CertificadoRevocadoException | OCSPServerException | VerificadorCertificadoException ex) {
                throw new SignPDFException("", ex);
                
            } catch (KeyStoreException | CertificateHelperException ex) {
                logger.log(Level.SEVERE, "" , ex);
                throw new SignPDFException("", ex);
            }
        }
        
        //Validar sobreescritura************************************************
        private static boolean validarSobreescrituraDuplicado() throws OverwritingException {
            String m = LanguageResource.getLanguage().getString("overwriting.duplicados");
            if(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APLICATION_VALIDATE_OVERWRITING)) {
                int respuesta = JOptionPane.showOptionDialog(
                                    null,
                                    m,
                                    LanguageResource.getLanguage().getString("overwriting.title"),
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    null,
                                    null);
                if(respuesta == JOptionPane.YES_OPTION) sobreescribir = true;
                else throw new OverwritingException();
            } else {
                sobreescribir = true;
            }
            return true;
        }
        
        private static boolean validarSobreescritura(String archivoDestino) throws OverwritingException {
            File archivo = new File(archivoDestino);
            if (archivo.exists()) {
                String m = MessageFormat.format(LanguageResource.getLanguage().getString("overwriting.question"),
                        archivoDestino.substring(archivoDestino.lastIndexOf("/")+1, archivoDestino.length()));
                if(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APLICATION_VALIDATE_OVERWRITING)) {
                    int respuesta = JOptionPane.showOptionDialog(
                                        null,
                                        m,
                                        LanguageResource.getLanguage().getString("overwriting.title"),
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        null,
                                        null);
                    if(respuesta == JOptionPane.YES_OPTION) sobreescribir = true;
                    else throw new OverwritingException();
                } else {
                    sobreescribir = true;
                }
                archivo = null;
                System.gc();
                return true;
            } else {
                archivo = null;
                System.gc();
                return true;
            }
        }
        
        //Core para modificacion del documento********************************************
        private static void agregarTexto(
            PdfStamper stamper, 
            String texto, 
            int pagina, 
            int posX, 
            int posY, 
            int rotacion, 
            int tamanoLetra,
            boolean negrita,
            boolean cursiva
        ) throws DocumentException, IOException {
            PdfContentByte cb = stamper.getOverContent(pagina);
            cb.beginText();
            BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.setFontAndSize(bf, tamanoLetra);
            if(negrita) {
                cb.setCharacterSpacing(1);
                cb.setLineWidth(new Float("0.5"));
                cb.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
            }
            if (cursiva) cb.setTextMatrix(1, 0, 25, 1, 0, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, texto, posX, posY, rotacion);
            cb.endText();
        }
        
        private static void agregarImagen(PdfStamper stamper, Image staticImage, int pagina, float width, float height, int posX, int posY) throws DocumentException  {
            PdfContentByte cb = stamper.getOverContent(pagina);
            cb.addImage(staticImage, width, 0, 0, height, posX, posY);
        }        
        
        //Información agregable*************************************************
        private static String addFirmaEstatica(String rutaOrigen, String alias) {
            String rutaDestino = agregarTemp(PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_STAMP_STATIC_DIRTEMP) + System.getProperty("file.separator") + "temp1");

            try {
                PdfReader reader = new PdfReader(rutaOrigen);
                FileOutputStream pdfFOS = new FileOutputStream(rutaDestino);
                PdfStamper stamper = new PdfStamper(reader, pdfFOS);
                
                //Leer coordenadas -----------------------------------------
                ImageSignPreferences imageStatic = PreferencesHelper.getImagePreferences().get(
                                PreferencesHelper.getPreferences().getString(PreferencesHelper.IMAGE_DISPOSITIVE));
                int pagina = Integer.parseInt(imageStatic.getPage());
                if (pagina > reader.getNumberOfPages() || pagina<0) pagina = reader.getNumberOfPages();

                Float xAux = Integer.parseInt(imageStatic.getPosX())*RELACION;
                Float yAux = Integer.parseInt(imageStatic.getPosY())*RELACION;
                Float widthAux = Integer.parseInt(imageStatic.getWidth())*RELACION;
                Float heightAux = Integer.parseInt(imageStatic.getHeight())*RELACION;

                int width = widthAux.intValue();
                int height = heightAux.intValue();
                int posX = xAux.intValue();
                int startY = yAux.intValue();

                Rectangle rec = reader.getPageSize(1);
                int posY = new Float((rec.getHeight() - height) - startY).intValue();

                //Agregar imagen -------------------------------------------
                if (Boolean.parseBoolean(imageStatic.getImageVisible())){
                    Image staticImage = null;
                    try {
                        staticImage = Image.getInstance(imageStatic.getPath());

                    } catch (IOException e) {
                        String m = LanguageResource.getLanguage().getString("error.imagen_no_encontrada");
                        logger.log(Level.SEVERE, m, e);
                        Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
                    }

                    if(staticImage!=null) {
                        if(pagina>0) {
                            agregarImagen(stamper, staticImage, pagina, width, height, posX, posY);

                        } else {
                            int total = reader.getNumberOfPages();
                            for(int i=1; i <= total; i++) {
                                agregarImagen(stamper, staticImage, i, width, height, posX, posY);
                            }
                        }
                    } else {
                        rutaDestino = null;
                    }
                }

                //Agregar informacion de la firma --------------------------
                posY = posY + height;
                String cn = CertificateHelper.getCN((X509Certificate) ks.getCertificateChain(alias)[0]);

                java.util.Date utilDate = new java.util.Date(); //fecha actual
                long lnMilisegundos = utilDate.getTime();
                java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(lnMilisegundos);
                
                boolean negrita = false;
                boolean cursiva = false;
                if(pagina>0) {
                    agregarTexto(stamper, "Firmado electrónicamente por", pagina, posX, posY-20, DEFAULT_ROTACION_LETRA, PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_FONT_SIZE), negrita, cursiva);
                    agregarTexto(stamper, cn, pagina, posX, posY-30, DEFAULT_ROTACION_LETRA, PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_FONT_SIZE), negrita, cursiva);
                    agregarTexto(stamper, "en fecha "+sqlTimestamp, pagina, posX, posY-40, DEFAULT_ROTACION_LETRA, PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_FONT_SIZE), negrita, cursiva);

                } else {
                    int total = reader.getNumberOfPages();
                    for(int i=1; i <= total; i++) {
                        agregarTexto(stamper, "Firmado electrónicamente por", i, posX, posY-20, DEFAULT_ROTACION_LETRA, PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_FONT_SIZE), negrita, cursiva);
                        agregarTexto(stamper, cn, i, posX, posY-30, DEFAULT_ROTACION_LETRA, PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_FONT_SIZE), negrita, cursiva);
                        agregarTexto(stamper, "en fecha "+sqlTimestamp, i, posX, posY-40, DEFAULT_ROTACION_LETRA, PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_FONT_SIZE), negrita, cursiva);
                    }
                }

                reader.close();
                stamper.close();

            } catch (IOException | DocumentException | NumberFormatException | KeyStoreException e) {
                logger.log(Level.SEVERE, "", e);
                Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, LanguageResource.getLanguage().getString("error.sign")));
                rutaDestino = null;
            }

            return rutaDestino;
        }
        
        private static String addCorrelativo(String rutaOrigen, int correlativo, int pagina) {
            String rutaDestino = agregarTemp(PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_STAMP_STATIC_DIRTEMP) + System.getProperty("file.separator") + "temp2");

            try {
                PdfReader reader = new PdfReader(rutaOrigen);
                FileOutputStream pdfFOS = new FileOutputStream(rutaDestino);
                PdfStamper stamper = new PdfStamper(reader, pdfFOS);
                
                //Determinar pagina
                if (pagina > reader.getNumberOfPages()) pagina = reader.getNumberOfPages();
                
                //Leer coordenadas
                Rectangle rec = reader.getPageSize(pagina);
                int posX = PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_CORRELATIVO_POS_X);
                //int posY = new Float(rec.getHeight() - 50).intValue();
                int posY = PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_CORRELATIVO_POS_Y);
                
                //Agregar correlativo ------------------------------------------
                boolean negrita = PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APPEARANCE_CORRELATIVO_FONT_BOLD);
                boolean cursiva = PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APPEARANCE_CORRELATIVO_FONT_ITALICS);
                
                if(pagina>0) {
                    agregarTexto(stamper, String.valueOf(correlativo), pagina, posX, posY, DEFAULT_ROTACION_LETRA, PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_CORRELATIVO_FONT_SIZE), negrita, cursiva);

                } else {
                    int total = reader.getNumberOfPages();
                    for(pagina=1; pagina <= total; pagina++)
                        agregarTexto(stamper, String.valueOf(correlativo), pagina, posX, posY, DEFAULT_ROTACION_LETRA, PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_CORRELATIVO_FONT_SIZE), negrita, cursiva);
                }
                
                reader.close();
                stamper.close();

            } catch (IOException | DocumentException e) {
                logger.log(Level.SEVERE, "", e);
            }

            return rutaDestino;
        }
        
        private static String addSerialEnBarras(String rutaEntrada, String codigo, int pagina) {
            String rutaSalida = agregarTemp(PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_STAMP_STATIC_DIRTEMP) + System.getProperty("file.separator") + "temp3");

            try {
                PdfReader reader = new PdfReader(rutaEntrada);
                FileOutputStream pdfFOS = new FileOutputStream(rutaSalida);
                PdfStamper stamper = new PdfStamper(reader, pdfFOS);
                
                //Abrir contenido para crear imagen dimanica
                PdfContentByte cb = stamper.getOverContent(1);
                
                //Crear codificador para crear imagen dinamica
                Barcode128 code128 = new Barcode128();
                code128.setGenerateChecksum(true);
                code128.setCode(codigo);
                
                //Crear imagen
                Image staticImage = code128.createImageWithBarcode(cb, null, null);

                //Leer coordenadas
                int width = PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_CODBARRA_WIDTH);
                int height = PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_CODBARRA_HEIGHT);
                int posX = PreferencesHelper.getPreferences().getInt(PreferencesHelper. APPEARANCE_CODBARRA_X);
                int startY = PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_CODBARRA_Y);

                Rectangle rec = reader.getPageSize(1);
                int posY = new Float((rec.getHeight() - height) - startY).intValue();
                
                //Agregar imagen -----------------------------------------------
                agregarImagen(stamper, staticImage, pagina, width, height, posX, posY);
                agregarTexto(stamper, codigo, pagina, 410, 475, DEFAULT_ROTACION_LETRA, PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_FONT_SIZE), false, false);

                reader.close();
                stamper.close();

            } catch (IOException | DocumentException e) {
                logger.log(Level.SEVERE, "", e);
            }

            return rutaSalida;
        }
        
        private static String addColetillaFactura(String rutaOrigen) {
            String rutaDestino = agregarTemp(PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_STAMP_STATIC_DIRTEMP) + System.getProperty("file.separator") + "temp4");

            try {
                PdfReader reader = new PdfReader(rutaOrigen);
                FileOutputStream pdfFOS = new FileOutputStream(rutaDestino);
                PdfStamper stamper = new PdfStamper(reader, pdfFOS);
                
                //Leer coordenadas----------------------------------------------
                ImageSignPreferences imageStatic = PreferencesHelper.getImagePreferences().get(
                                PreferencesHelper.getPreferences().getString(PreferencesHelper.IMAGE_DISPOSITIVE));
                int pagina = Integer.parseInt(imageStatic.getPage());
                if (pagina > reader.getNumberOfPages() || pagina<0) pagina = reader.getNumberOfPages();
                
                //Armo coletilla------------------------------------------------
                List<String> coletilla = new ArrayList<>();
                try {
                    File tempp = new File(PreferencesHelper.APPEARANCE_COLETILLA_FACTURA_SUFIX_FILE);
                    BufferedReader entrada = new BufferedReader( new FileReader( tempp ) );
                    if(tempp.exists()) {
                        do {
                            String linea = entrada.readLine();
                            if(linea==null) break;
                            else coletilla.add(linea);
                        } while(true);
                    }
                } catch (IOException e) {
                    throw new IOException(LanguageResource.getLanguage().getString("error.sign.coletilla.factura"), e);
                }
                
                //Agregar coletilla --------------------------------------------
                //                Float xAux = new Float(Integer.parseInt(imageStatic.getPosX())*RELACION);
//                Float yAux = new Float(Integer.parseInt(imageStatic.getPosY())*RELACION);
//                Float widthAux = new Float(Integer.parseInt(imageStatic.getWidth())*RELACION);
//                Float heightAux = new Float(Integer.parseInt(imageStatic.getHeight())*RELACION);
//                
//                int width = widthAux.intValue();
//                int height = heightAux.intValue();
//                int posX = xAux.intValue();
//                int startY = yAux.intValue();
//                
//                Rectangle rec = reader.getPageSize(1);
//                int posY = new Float((rec.getHeight() - height) - startY).intValue() - 3;
                //----------------------------------------------------------
                int tamanoLetra = 9;
                int posX = PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_COLETILLA_FACTURA_X);
                int posY = (coletilla.size() * (tamanoLetra + 1) ) + 20;
                
                if(pagina>0) {
                    int proximaLinea = 0;
                    for (String linea : coletilla) {
                        agregarTexto(stamper, linea, pagina, posX, posY-proximaLinea, DEFAULT_ROTACION_LETRA, tamanoLetra, false, false);
                        proximaLinea = proximaLinea + tamanoLetra + 1;
                    }

                } else {
                    int total = reader.getNumberOfPages();
                    for(pagina=1; pagina <= total; pagina++) {
                        int proximaLinea = 0;
                        for (String linea : coletilla) {
                            agregarTexto(stamper, linea, pagina, posX, posY-proximaLinea, DEFAULT_ROTACION_LETRA, tamanoLetra, false, false);
                            proximaLinea = proximaLinea + tamanoLetra + 1;
                        }
                    }
                }
                
                reader.close();
                stamper.close();

            } catch (IOException | DocumentException | NumberFormatException e) {
                logger.log(Level.SEVERE, "", e);
                Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, LanguageResource.getLanguage().getString("error.sign")));
                rutaDestino = null;
            }
            
            return rutaDestino;
        }        
        
        //Operaciones de Firma *************************************************
        public static boolean firmar(String alias, PDFInfo pdfParameter) throws OverwritingException, SignPDFException, IOException {
            sobreescribir = false;
            String rutaOrigen = pdfParameter.getOrigen();
            String carpetaRespaldo = getCarpetaResplado();
            String rutaDestino = getRutaDestino(pdfParameter);

            if (validarSobreescritura(rutaDestino)){
                //Agregar imagen estatica
                ImageSignPreferences imageStatic = PreferencesHelper.getImagePreferences().get(
				PreferencesHelper.getPreferences().getString(PreferencesHelper.IMAGE_DISPOSITIVE));
                if (!imageStatic.getType().equalsIgnoreCase("noimage") && imageStatic.getType().equalsIgnoreCase("simple"))
                    rutaOrigen = addFirmaEstatica(rutaOrigen, alias);
                
                //Agregar coletilla de factura
                if(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APPEARANCE_COLETILLA_FACTURA))
                    rutaOrigen = addColetillaFactura(rutaOrigen);
                
                //Firmar
                finalizarFirma(rutaOrigen, alias, rutaDestino);
                
                //Respaldar archivo origen
                respaldar(pdfParameter.getOrigen(), carpetaRespaldo);
                
                //Limpiar temporales generados
                limpiarTemporales();
                
                //Fin
                return true;
            } else return false;
	}
        
	public static boolean firmarDuplicados(String alias, PDFInfo pdfParameter, int inicioCorrelativo, int finCorrelativo) throws OverwritingException, SignPDFDuplicateException, SignPDFException, IOException {
            sobreescribir = false;
            String carpetaRespaldo = getCarpetaResplado();
            String rutaDestinoDuplicados = getRutaDestinoDuplicados(pdfParameter);
            List<String> rutasDestino = new ArrayList<>();
            List<String> seriales = new ArrayList<>();

            //Leer seriales
            if (PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APPEARANCE_CODBARRA_ENABLE)){
                try {
                    File archivo = null;
                    FileReader fr = null;
                    BufferedReader br = null;
                    archivo = new File (PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_CODBARRA_PATH));
                    if (archivo.exists()){
                        fr = new FileReader (archivo);
                        br = new BufferedReader(fr);
                        String linea;
                        int count = 0;
                        while((linea=br.readLine())!=null && count < finCorrelativo) {
                            seriales.add(linea);
                            rutasDestino.add(rutaDestinoDuplicados + "-" + linea + ".pdf");
                            count++;
                        }
                        br.close();
                        fr.close();

                    } else {
                        String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.codbarra.no_exist"),
                                    PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_CODBARRA_PATH).substring(
                                    PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_CODBARRA_PATH).lastIndexOf("/")+1,
                                    PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_CODBARRA_PATH).length()));
                        logger.log(Level.SEVERE, m);
                        Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
                        throw new FileNotFoundException();
                    }
                } catch (FileNotFoundException e) {
                    //Cuando no encuentra los archivos de seriales
                    String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.codbarra.no_exist"),
                                                    PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_CODBARRA_PATH));
                    logger.log(Level.SEVERE, m, e);
                    Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
                    throw new SignPDFDuplicateException("", e);

                } catch(IOException e) {
                    //Cuando hay un error durante la lectura del archivo de seriales
                    String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.codbarra.no_read"),
                                                    PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_CODBARRA_PATH));
                    logger.log(Level.SEVERE, m, e);
                    Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
                    throw new SignPDFDuplicateException("", e);
                }

            } else {
                for(int correlativo=inicioCorrelativo; correlativo<=finCorrelativo; correlativo++) rutasDestino.add(rutaDestinoDuplicados + "-" + correlativo + ".pdf");
            }

            if (validarSobreescrituraDuplicado()) {
                int cont = 0;
                
                FileWriter writer = null;
                //Validar generacion de CSV
                if(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.DUPLICADO_CSV_ENABLE))
                    writer = new FileWriter(rutaDestinoDuplicados + "-Lista Firmados-" + new Date() + ".csv");
                for(int correlativo=inicioCorrelativo; correlativo<=finCorrelativo; correlativo++) {
                    System.out.println(correlativo);
                    String rutaOrigen = pdfParameter.getOrigen();
                    //Agregar Imagen Estatica
                    ImageSignPreferences imageStatic = PreferencesHelper.getImagePreferences().get(
                            PreferencesHelper.getPreferences().getString(PreferencesHelper.IMAGE_DISPOSITIVE));
                    if (!imageStatic.getType().equalsIgnoreCase("noimage") && imageStatic.getType().equalsIgnoreCase("simple"))
                        rutaOrigen = addFirmaEstatica(rutaOrigen, alias);

                    //Agregar Correlativo
                    if (PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APPEARANCE_CORRELATIVO_ENABLE))
                        rutaOrigen = addCorrelativo(rutaOrigen, correlativo, PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_CORRELATIVO_PAGE));

                    //Agregar Codigo de Barras
                    if (PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.APPEARANCE_CODBARRA_ENABLE))
                        rutaOrigen = addSerialEnBarras(rutaOrigen, seriales.get(cont), PreferencesHelper.getPreferences().getInt(PreferencesHelper.APPEARANCE_CODBARRA_PAGE));

                    String sigDate = finalizarFirma(rutaOrigen, alias, rutasDestino.get(cont));
                    
                    //Escribir linea en el csv
                    if (writer!=null) {
                        try {
                            writer.append(
                                correlativo + ";" +
                                CertificateHelper.getCN((X509Certificate) ks.getCertificateChain(alias)[0]) + ";" +
                                "en fecha " + sigDate
                            );
                        } catch (KeyStoreException ex) {
                            throw new IOException(ex);
                        }
                    }
                    cont++;
                }

                if (writer!=null) writer.close();
                
                respaldar(pdfParameter.getOrigen(), carpetaRespaldo);
                limpiarTemporales();
                seriales.clear(); seriales = null;
                rutasDestino.clear(); rutasDestino = null;
                System.gc();
                return true;

            } else {
                seriales.clear(); seriales = null;
                rutasDestino.clear(); rutasDestino = null;
                System.gc();
                return false;
            }
	}
        
        public static String finalizarFirma(String rutaOrigen, String alias, String rutaDestinoDef) throws SignPDFException, IOException {
            String result = null;
            try {
                // TODO si tiene password??? -> preguntar pass
                System.out.println("ruta destino: "+rutaDestinoDef);
                String rutaDestino = rutaDestinoDef;
                if(sobreescribir) rutaDestino = rutaDestino + ".temp";
                
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(rutaDestino, true);
                    
                } catch (FileNotFoundException e) {
                    String m = LanguageResource.getLanguage().getString("error.file_destino_no_encontrado");
                    logger.log(Level.SEVERE, m , e);
                    Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
                }
                
                Certificate[] certs = ks.getCertificateChain(alias);
                PrivateKey privateKey = (PrivateKey) ks.getKey(alias, passwordProtection.getPassword());
                    
                // PREPARAMOS TODAS LAS PROPIEDADES DE LA FIRMA		
                PdfReader reader = new PdfReader(rutaOrigen);
                PdfStamper stp = PdfStamper.createSignature(reader, fos, '\0', null, true);
                PdfSignatureAppearance sap = stp.getSignatureAppearance();

                ///OJO... Modificacion yessica
                ImageSignPreferences imageStatic = PreferencesHelper.getImagePreferences().get(
				PreferencesHelper.getPreferences().getString(PreferencesHelper.IMAGE_DISPOSITIVE));

                String reason = imageStatic.getReason();
                String location = imageStatic.getLocate();
                sap.setReason(reason);
                sap.setLocation(location);
//                signaturePreferences.setCertified(PreferencesHelper.getPreferences().getInt(PreferencesHelper.VALIDATE_CERTIFIED));   

                if (!imageStatic.getType().equalsIgnoreCase("noimage") && imageStatic.getType().equalsIgnoreCase("adobe")) {

                    if (Boolean.parseBoolean(imageStatic.getImageVisible())){
                        Image sello = null;
                        try {
                            sello = Image.getInstance(imageStatic.getPath());

                        } catch (IOException e) {
                            String m = LanguageResource.getLanguage().getString("error.imagen_no_encontrada");
                            logger.log(Level.SEVERE, m, e);
                            Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
                        }
                        sap.setImage(sello);
                    }
                    
                    Float xAux = Integer.parseInt(imageStatic.getPosX())*RELACION;
                    Float yAux = Integer.parseInt(imageStatic.getPosY())*RELACION;
                    Float widthAux = Integer.parseInt(imageStatic.getWidth())*RELACION;
                    Float heightAux = Integer.parseInt(imageStatic.getHeight())*RELACION;

                    int width = widthAux.intValue();
                    int height = heightAux.intValue();
                    int startX = xAux.intValue();
                    int startY = yAux.intValue();

                    Rectangle rec = reader.getPageSize(1);
                    int x1 = startX;
                    int y1 = new Float((rec.getHeight() - height) - startY).intValue();
                    int x2 = new Float(x1 + width).intValue();
                    int y2 = new Float(y1 + height).intValue();

                    int page = Integer.parseInt(imageStatic.getPage());
                    //Si es mayor a la cantidad de paginas la pongo en la ultima
                    if (page > reader.getNumberOfPages() || page<0) page = reader.getNumberOfPages();

                    sap.setVisibleSignature(new Rectangle(x1, y1, x2, y2), page, null);
                }
                
                if(!PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.VALIDATE_TS_ENABLE)) {
                    sap.setCrypto(privateKey, certs, null, PdfSignatureAppearance.WINCER_SIGNED);
                    stp.close();

                } else {
                    sap.setCrypto(null, certs, null, PdfSignatureAppearance.SELF_SIGNED);
                    TimeStampPreferences tss = PreferencesHelper.getTimestampPreferencesNew().get(PreferencesHelper.getPreferences().getString(PreferencesHelper.VALIDATE_TS_TSA));
                    TSAClient tsc = new TSAClientBouncyCastle(tss.getUrl(), tss.getUser(), tss.getPassword());

                    PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, new PdfName("adbe.pkcs7.detached"));
                    dic.setReason(sap.getReason());
                    dic.setLocation(sap.getLocation());
                    dic.setContact(sap.getContact());
                    dic.setDate(new PdfDate(sap.getSignDate()));
                    sap.setCryptoDictionary(dic);

                    int contentEstimated = 15000;
                    HashMap exc = new HashMap();
                    exc.put(PdfName.CONTENTS, new Integer(contentEstimated * 2 + 2));
                    sap.preClose(exc);

                    PdfPKCS7 sgn = new PdfPKCS7(privateKey, certs, null, PreferencesHelper.getPreferences().getString(PreferencesHelper.SIGN_HASH).toUpperCase(), null, false);

                    InputStream data = sap.getRangeStream();
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA1");

                    byte buf[] = new byte[8192];
                    int n;
                    while ((n = data.read(buf)) > 0) {
                        messageDigest.update(buf, 0, n);
                    }
                    byte hash[] = messageDigest.digest();

                    Calendar cal = Calendar.getInstance();
                    byte[] ocsp = null;
                    if(PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.VALIDATE_OCSP_ENABLE)){
                        if (certs.length >= 2) {
                            String url = PdfPKCS7.getOCSPURL((X509Certificate)certs[0]);
                            if (url != null && url.length() > 0)
                                ocsp = new OcspClientBouncyCastle((X509Certificate)certs[0], (X509Certificate)certs[1], url).getEncoded();
                        }
                    }

                    byte sh[] = sgn.getAuthenticatedAttributeBytes(hash, cal, ocsp);
                    sgn.update(sh, 0, sh.length);

                    byte[] encodedSig = sgn.getEncodedPKCS7(hash, cal, tsc, ocsp);

                    if (contentEstimated + 2 < encodedSig.length)
                        throw new SignPDFException("No hay espacio suficiente", new IOException());

                    byte[] paddedSig = new byte[contentEstimated];
                    System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);

                    PdfDictionary dic2 = new PdfDictionary();
                    dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
                    sap.close(dic2);
                }
                
                result = sap.getSignDate().toString();
                
                if(sobreescribir) {
                    //ExportHelper.deleteFile(rutaDestinoDef);
                    ExportHelper.copyFile(rutaDestino, rutaDestinoDef);
                    ExportHelper.deleteFile(rutaDestino);
                }
                
            } catch (DocumentException e) {
                logger.log(Level.SEVERE, "", e);
                throw new SignPDFException("", new IOException());
                
            } catch (NoSuchAlgorithmException e) {
                    String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.sign.hash"), PreferencesHelper.getPreferences().getString(PreferencesHelper.SIGN_HASH).toUpperCase());
                    logger.log(Level.SEVERE, m , e);
                    Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
                    throw new SignPDFException(m, e);
                    
            } catch (IOException | SignPDFException e) {
                throw e;
                
            } catch (KeyStoreException | UnrecoverableKeyException | NumberFormatException | InvalidKeyException | NoSuchProviderException | CertificateParsingException | SignatureException e) {
                logger.log(Level.SEVERE, "" , e);
                throw new SignPDFException("", e);
            }
            System.gc();
            return result;
        }
        
        //Operaciones post-Firma ***********************************************
        public static void respaldar(String origen, String backup) {
             if (PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.OUTPUT_DIR_BACKUP_ENABLE)) {
                File a = new File(origen);
                String destino = backup + File.separatorChar + a.getName();
                a = null;
                if(!origen.equals(destino)) {
                    ExportHelper.copyFile(origen, destino);
                    ExportHelper.deleteFile(origen);
                }
                System.gc();
            }
        }
        
        public static void limpiarTemporales() {
            for(String ruta : temp) ExportHelper.deleteFile(ruta);
        }
        
        //Otras operaciones ****************************************************
        private static String agregarTemp(String ruta) {
            if(!temp.contains(ruta)) temp.add(ruta);
            return ruta;
        }
        
        private static String getRutaDestino(PDFInfo pdfParameter) {
            File archivo = new File (pdfParameter.getOrigen());
            String rutaDestino = null;
            
            if(pdfParameter.getDestino()!= null && !pdfParameter.getOrigen().equals(pdfParameter.getDestino()))
                    rutaDestino = pdfParameter.getDestino() + File.separatorChar + ExportHelper.getOutputName(archivo.getName());
            else rutaDestino = ExportHelper.getOutputDir(archivo) + File.separatorChar + ExportHelper.getOutputName(archivo.getName());
                    
            archivo = null;
            System.gc();
            pdfParameter.setDestino(rutaDestino);
            return rutaDestino;
        }
        
        private static String getRutaDestinoDuplicados(PDFInfo pdfParameter) {
            if (PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_SOURCE).equals(PreferencesHelper.APLICATION_PATH_SOURCE_FOLDER)) {
                File archivo = new File (pdfParameter.getOrigen());
                String rutaDestino = ExportHelper.getOutputDir(archivo) + File.separatorChar + archivo.getName().substring(0, archivo.getName().length()-4);
                archivo = null;
                System.gc();
                pdfParameter.setDestino(rutaDestino);
                return rutaDestino;
            } else {
                File archivo = new File (pdfParameter.getDestino());
                String rutaDestino = archivo.getName().substring(0, archivo.getName().length()-4);
                pdfParameter.setDestino(rutaDestino);
                return rutaDestino;
            }
        }
        
        private static String getCarpetaResplado() {
            String dirBackUp = null;
            if (PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.OUTPUT_DIR_BACKUP_ENABLE)) {
                File dirFile = new File(PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_BACKUP_FOLDER_VALUE));
                if (dirFile.exists()) dirBackUp = PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_BACKUP_FOLDER_VALUE);
                else {
                    dirBackUp = null;
                    String m = LanguageResource.getLanguage().getString("error.backup.no_exist");
                    logger.log(Level.SEVERE, m);
                    Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.INFO, m));
                }
                dirFile = null;
                System.gc();
            }
            return dirBackUp;
        }
}