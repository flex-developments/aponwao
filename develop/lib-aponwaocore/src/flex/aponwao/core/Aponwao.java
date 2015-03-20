package flex.aponwao.core;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.OcspClientBouncyCastle;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.util.Properties;
import javax.security.auth.callback.CallbackHandler;
import sun.security.pkcs11.SunPKCS11;
import java.io.File;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.HashMap;
import flex.aponwao.core.keystore.KeyStoreBuilderFactory;
import flex.aponwao.core.keystore.KeyStoreBuilderFactory.KeyStoreTypes;
import flex.aponwao.core.password.PasswordExtractor;
import flex.aponwao.core.exceptions.SignPDFException;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Aponwao extends Thread {

	// PREFERENCES KEYS
	
	// Proxy
	public static final String PROXY_HOST = "proxy.http.host";
	public static final String PROXY_PORT = "proxy.http.port";
	public static final String PROXY_USER = "proxy.http.user";
	public static final String PROXY_PASS = "proxy.http.pass";
	public static final String PROXY_NON_PROXY = "proxy.http.non_proxy";
	public static final String PROXY_ENABLE = "proxy.http.enable";

	// Certifications
	public static final String CERT_TYPE = "preferencias.radioCertType.active";
	public static final String CERT_SOFTWARE_TYPE = "0";
	public static final String CERT_HARDWARE_TYPE = "1";
	public static final String HARDWARE_DISPOSITIVE = "hardware.dispositive";
	public static final String SOFTWARE_DISPOSITIVE = "software.dispositive";

	public static final String KS_CACHE = "ks.cache";
	public static final String KS_CACHE_PASSWORD = "ks.cache.pass";
	public static final String CERTIFICATE_ALIAS = "certificate.alias";

	// Appearance
	public static final String APPEARANCE_VISIBLE = "appearance.visible";
	public static final String APPEARANCE_PAGE = "appearance.page";
	public static final String APPEARANCE_REASON = "appearance.reason";
	public static final String APPEARANCE_LOCATION = "appearance.location";
	public static final String APPEARANCE_STAMP_ENABLE = "appearance.stamp.enable";
	public static final String APPEARANCE_STAMP_PATH = "appearance.stamp.path";
	public static final String APPEARANCE_STAMP_WIDTH = "appearance.stamp.width";
	public static final String APPEARANCE_STAMP_HEIGHT = "appearance.stamp.height";
	public static final String APPEARANCE_STAMP_X = "appearance.stamp.x";
	public static final String APPEARANCE_STAMP_Y = "appearance.stamp.y";

	// Validate
      //OJO... Modificacion Felix
      //public static final String VALIDATE_OUT_PATH = "validate.out.path";
      //****************************************************
	public static final String VALIDATE_TS_ENABLE = "validate.ts.enable";
	public static final String VALIDATE_TS_TSA = "validate.ts.tsa";
	public static final String VALIDATE_OCSP_ENABLE = "validate.ocsp.enable";
	public static final String VALIDATE_CERTIFIED = "validate.certified";

	// Password callbackhander
	public static final String PASSWORD_CALLBACK_HANDLER = "callback.handler";

      //OJO... Modificacion Felix
      private static Properties p = null;
      private static String inputPath = null;
      private static String outputPath = null;
      private static String preferencesPath = null;

      private static Boolean checkOutPath = false;
      private static Boolean duplicate = false;
      private static String ext = null;
      private static int sC= 1;
      private static int eC = 1;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
            Security.addProvider(new BouncyCastleProvider());
            // analisis de parametros.
            for (int i = 0; i < args.length; i++) {

                    if (args[i].equals("-input")) {
                            inputPath = args[i + 1];
                    }
                    if (args[i].equals("-output")) {
                            outputPath = args[i + 1];
                    }
                    if (args[i].equals("-preferences")) {

                            preferencesPath = args[i + 1];
                            p = new Properties();
                            p.load(new FileInputStream(preferencesPath));
                    }

              //OJO... Modificacion Felix
              if (args[i].equals("-checkOutPath")) {
                            checkOutPath = Boolean.parseBoolean(args[i + 1]);
                    }
              if (args[i].equals("-duplicate")) {
                            duplicate = Boolean.parseBoolean(args[i + 1]);
                    }
              if (args[i].equals("-sC")) {
                            sC = Integer.parseInt(args[i + 1]);
                    }
              if (args[i].equals("-eC")) {
                            eC = Integer.parseInt(args[i + 1]);
                    }
              if (args[i].equals("-ext")) {
                            ext = args[i + 1];
                    }
            }
            
            String outputPathInit = outputPath;
            FileOutputStream fos = null;
            //OJO... Modificacion Felix
            eC++;
            for(int C=sC; C<eC; C++) {
                System.out.println("Procesando " + C);
                if(duplicate) {
                    outputPath = outputPathInit + "-"+ C + "-Firmado" + ext;
                }
                if (checkOutPath) {
                    File fichero = new File(outputPath);
                    if (fichero.exists()) {
                        throw new SignPDFException("El archivo " + outputPath + " ya existe", new IOException());
                    } else {
                        fos = new FileOutputStream(outputPath);
                    }
                } else {
                    fos = new FileOutputStream(outputPath);
                }
                //**********************************************
                Boolean proxyEnable = Boolean.parseBoolean(p.getProperty(PROXY_ENABLE));
                String proxyHost = p.getProperty(PROXY_HOST);
                String proxyPort = p.getProperty(PROXY_PORT);
                String proxyUser = p.getProperty(PROXY_USER);
                String proxyPass = p.getProperty(PROXY_PASS);
                String proxyNonProxy = p.getProperty(PROXY_NON_PROXY);
                
                String certType = p.getProperty(CERT_TYPE);
                String hardwarePath = p.getProperty(HARDWARE_DISPOSITIVE);
                String softwarePath = p.getProperty(SOFTWARE_DISPOSITIVE);
                
                String ksCache = p.getProperty(KS_CACHE);
                String ksCachePass = p.getProperty(KS_CACHE_PASSWORD);
                String alias = p.getProperty(CERTIFICATE_ALIAS);
                
                Boolean appearanceVisible = Boolean.parseBoolean(p.getProperty(APPEARANCE_VISIBLE));
                //OJO... Modificacion Felix
                //Falta evaluar los posibles valores que tenga page
                int page = Integer.parseInt(p.getProperty(APPEARANCE_PAGE));
                
                //**********************************************
                String appearanceReason = p.getProperty(APPEARANCE_REASON);
                String appearanceLocation = p.getProperty(APPEARANCE_LOCATION);
                Boolean appearanceStampEnable = Boolean.parseBoolean(p.getProperty(APPEARANCE_STAMP_ENABLE));
                String appearanceStampPath = p.getProperty(APPEARANCE_STAMP_PATH);
                int appearanceStampWidth = Integer.parseInt(p.getProperty(APPEARANCE_STAMP_WIDTH));
                int appearanceStampHeight = Integer.parseInt(p.getProperty(APPEARANCE_STAMP_HEIGHT));
                int appearanceStampX = Integer.parseInt(p.getProperty(APPEARANCE_STAMP_X));
                int appearanceStampY = Integer.parseInt(p.getProperty(APPEARANCE_STAMP_Y));
                
                Boolean validateTsEnable = Boolean.parseBoolean(p.getProperty(VALIDATE_TS_ENABLE));
                String validateTsURL = p.getProperty(VALIDATE_TS_TSA);
                String tsa_user  = null;
                String tsa_password  = null;
                Boolean validateOcspEnable = Boolean.parseBoolean(p.getProperty(VALIDATE_OCSP_ENABLE));
                String validateCertified = p.getProperty(VALIDATE_CERTIFIED);
                String passwordCallbackHandler = null;
                //if(passGUI){
                    //passwordCallbackHandler = pass.getPassword();
                //} else {
                    passwordCallbackHandler = p.getProperty(PASSWORD_CALLBACK_HANDLER);
                //}
                
                //Proxy
                if (proxyEnable) {

                      System.getProperties().put("http.proxySet", "true");
                      System.getProperties().put("http.proxyHost", proxyHost);
                      System.getProperties().put("http.proxyPort", proxyPort);

                      if (proxyUser != null && !proxyUser.equals("")) {

                            System.getProperties().put("http.proxyUser", proxyUser);
                            if (proxyPass != null) {
                                  System.getProperties().put("http.proxyPassword", proxyPass);
                            }
                      }

                      if (proxyNonProxy != null && !proxyNonProxy.equals("")) {
                            System.getProperties().put("http.nonProxyHosts", proxyNonProxy);
                      }

                } else {

                      System.getProperties().put("http.proxySet", "null");
                      System.getProperties().put("http.proxyHost", "null");
                      System.getProperties().put("http.proxyPort", "null");
                      System.getProperties().put("http.proxyUser", "null");
                      System.getProperties().put("http.proxyPassword", "null");
                }
                
                // FIRMA
                // loadKeyStore
                KeyStore ks = null;
                Object o = Class.forName(passwordCallbackHandler).getConstructor(new Class[] { String.class } ).newInstance(new Object[] {preferencesPath});

                if (certType.equalsIgnoreCase(CERT_HARDWARE_TYPE)) {

                      ks = KeyStoreBuilderFactory.getKeyStore("HARD", KeyStoreTypes.PKCS11, hardwarePath, new KeyStore.CallbackHandlerProtection((CallbackHandler)o));

                } else if (certType.equalsIgnoreCase(CERT_SOFTWARE_TYPE)) {

                      ks = KeyStoreBuilderFactory.getKeyStore("SOFT", KeyStoreTypes.PKCS12, softwarePath, new KeyStore.CallbackHandlerProtection((CallbackHandler)o));

                }
                // fijo el passwordprotection para el PKCS12, para el PKCS11 no es necesario pero por coherencia lo uso tambien.
                PasswordProtection passwordProtection = ((PasswordExtractor)o).getPasswordProtection();
                PrivateKey privateKey = null;
                Certificate[] certs = null;
                if (!ks.aliases().hasMoreElements()) {
                    throw new KeyStoreException();
                } else {
                    privateKey = (PrivateKey) ks.getKey(alias, passwordProtection.getPassword());
                    certs = ks.getCertificateChain(alias);
                }
                
                // PREPARAMOS TODAS LAS PROPIEDADES DE LA FIRMA
                PdfReader reader = new PdfReader(inputPath);
                PdfStamper stp = PdfStamper.createSignature(reader, fos, '\0');
                PdfSignatureAppearance sap = stp.getSignatureAppearance();
                sap.setReason(appearanceReason);
                sap.setLocation(appearanceLocation);
//                sap.setCertificationLevel(validateCertified); Comparar y mandar el valor correcto
                if(appearanceVisible) {
                    Image sello = null;
                    if (appearanceStampEnable) {
                      try {
                            sello = Image.getInstance(appearanceStampPath);

                      } catch (BadElementException e) {
                          //OJO... FALTA CAPTURAR ESTA EXCEPCION
                          e.printStackTrace();
                      }
                    }
                    sap.setImage(sello);
                    
                    int startX = appearanceStampX;
                    int startY = appearanceStampY;
                    int width = appearanceStampWidth;
                    int height = appearanceStampHeight;
                    
                    Rectangle rec = reader.getPageSize(1);
                    int x1 = startX;
                    int y1 = new Float((rec.getHeight() - height) - startY).intValue();
                    int x2 = new Float(x1 + width).intValue();
                    int y2 = new Float(y1 + height).intValue();
                    sap.setVisibleSignature(new Rectangle(x1, y1, x2, y2), page, null);
                }
                if(!validateTsEnable) {
                    sap.setCrypto(privateKey, certs, null, PdfSignatureAppearance.WINCER_SIGNED);
                    stp.close();
                    
                } else {
                    sap.setCrypto(null, certs, null, PdfSignatureAppearance.SELF_SIGNED);
                    
                    TSAClient tsc = new TSAClientBouncyCastle(validateTsURL, tsa_user, tsa_password);
                    
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
                    
                    PdfPKCS7 sgn = new PdfPKCS7(privateKey, certs, null, "SHA1", null, false);
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
                    if(validateOcspEnable){
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
                        throw new Exception("Not enough space");

                    byte[] paddedSig = new byte[contentEstimated];
                    System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);

                    PdfDictionary dic2 = new PdfDictionary();
                    dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
                    sap.close(dic2);
                }
                System.gc();
                
                // Una vez que se ha firmado ... hago un logout de la session del provider
                if (certType.equalsIgnoreCase(CERT_HARDWARE_TYPE)) {
                    try {
                          ((SunPKCS11) ks.getProvider()).logout();

                    } catch (ClassCastException e) {
                        //OJO... FALTA CAPTURAR ESTA EXCEPCION
                        e.printStackTrace();
                    }
                }
                System.out.println(C + " Listo!\n");
            }
	}
}