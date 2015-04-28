package flex.aponwao.gui.sections.main.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.security.auth.login.LoginException;
import flex.aponwao.core.exceptions.CoreException;
import flex.aponwao.core.exceptions.CorePKCS12Exception;
import flex.aponwao.core.keystore.KeyStoreBuilderFactory;
import flex.aponwao.core.keystore.KeyStoreBuilderFactory.KeyStoreTypes;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.exceptions.SendPDFException;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.smime.SMIMECapabilitiesAttribute;
import org.bouncycastle.asn1.smime.SMIMECapability;
import org.bouncycastle.asn1.smime.SMIMECapabilityVector;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.mail.smime.SMIMESignedGenerator;
import org.bouncycastle.util.Store;
import org.eclipse.swt.widgets.Shell;
import sun.security.pkcs11.SunPKCS11;
import sun.security.pkcs11.wrapper.PKCS11Exception;

public class EnviarPDFHelper {
    private static final Logger logger = Logger.getLogger(EnviarPDFHelper.class.getName());
    private static KeyStore ks = null;
    private static PasswordProtection passwordProtection = null;
    
    public static void setKs(KeyStore ks) {
        EnviarPDFHelper.ks = ks;
    }

    public static KeyStore getKs() {
        return ks;
    }

    public static void loadKeyStore(Shell sShell) throws NoSuchAlgorithmException, KeyStoreException, PKCS11Exception, CoreException, CorePKCS12Exception {

            String certificadoType = PreferencesHelper.getPreferences().getString(PreferencesHelper.CERT_TYPE);

            String pkcs12Path = PreferencesHelper.getSoftwarePreferences().get(
                            PreferencesHelper.getPreferences().getString(PreferencesHelper.SOFTWARE_DISPOSITIVE));

            String pkcs11Path = PreferencesHelper.getHardwarePreferences().get(
                            PreferencesHelper.getPreferences().getString(PreferencesHelper.HARDWARE_DISPOSITIVE));

            PasswordCallbackHandlerDialog o = new PasswordCallbackHandlerDialog(sShell);

            // Compruebo las preferencias y cargo el certificado del dispositivo hardware, o del el file-system

            if (certificadoType.equalsIgnoreCase(PreferencesHelper.CERT_HARDWARE_TYPE)) {

                ks = KeyStoreBuilderFactory.getKeyStore("HARD", KeyStoreTypes.PKCS11, pkcs11Path, new KeyStore.CallbackHandlerProtection(o));

            } else if (certificadoType.equalsIgnoreCase(PreferencesHelper.CERT_SOFTWARE_TYPE)) {

                ks = KeyStoreBuilderFactory.getKeyStore("SOFT", KeyStoreTypes.PKCS12, pkcs12Path, new KeyStore.CallbackHandlerProtection(o));
            }
            // fijo el passwordprotection para el PKCS12, para el PKCS11 no es necesario pero por coherencia lo uso tambien.
            passwordProtection = o.getPasswordProtection();
    }

    public static void logout() {

            // Una vez que se ha firmado ... hago un logout de la session del provider
            try {
                    ((SunPKCS11) ks.getProvider()).logout();

            } catch (ClassCastException e) {
                    // TODO: si el provider es de tipo PKCS12 no se puede hacer el cast ni el logout pero no es necesario
//			logger.log(Level.INFO, "Controled exception", e);
            } catch (LoginException e) {
                    logger.log(Level.SEVERE, "", e);
            }
    }

    public static List<String> getAlias() {
            Enumeration<String> aliases = null;
            List<String> list = new ArrayList<String>();
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
        
    public static boolean sendMail(String alias, String contraseña, List<String> destinatarios, List<String> copias, List<String> copiasOcultas, String asunto, String mensaje, List<String> archivoAdjunto) throws SendPDFException, MessagingException {
        String add=null;
        try {
            //Se evaluan los destinatarios
            if((destinatarios==null)&&(copias==null)&&(copiasOcultas==null)) throw new AddressException();
            //Levantar información del archivo de configuracion
            Properties props = new Properties();
            //Esto desde un archivo de configuracion
            props.setProperty("mail.smtp.host", PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_HOST));
            props.setProperty("mail.smtp.starttls.enable", PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_STARTTLS_ENABLE));
            props.setProperty("mail.smtp.port", PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_PORT));
            props.setProperty("mail.smtp.user", PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_USER));
            props.setProperty("mail.smtp.auth", PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_AUTH));
            //Validamos datos del properties
            if(PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_HOST).equals("")) throw new SendPDFException(LanguageResource.getLanguage().getString("error.email.no_smtp.host.defined"));
            if(PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_STARTTLS_ENABLE).equals("")) throw new SendPDFException(LanguageResource.getLanguage().getString("error.email.no_ttls.defined"));
            if(PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_PORT).equals("")) throw new SendPDFException(LanguageResource.getLanguage().getString("error.email.no_smtp.port.defined"));
            if(PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_USER).equals("")) throw new SendPDFException(LanguageResource.getLanguage().getString("error.email.no_smtp.user.defined"));
            if(PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_AUTH).equals("")) throw new SendPDFException(LanguageResource.getLanguage().getString("error.email.no_smtp.auth.defined"));
            
            //Si asunto esta en NULL se envía el asunto en blanco
            if(asunto==null) asunto = "";
            //Si todo el contenido es null se envía el mensaje en blanco,
            if((mensaje==null)&&(archivoAdjunto.isEmpty())) mensaje = "";
            
            //Agregar firma
            try {
                File temp = new File(PreferencesHelper.EMAIL_USER_SUFIX_FILE);
                BufferedReader entrada = new BufferedReader( new FileReader( temp ) );
                if(temp.exists()) {
                    do {
                        String linea = entrada.readLine();
                        if(linea==null) break;
                        else mensaje = mensaje + "\n" + linea;
                    } while(true);
                }
            } catch (IOException e) {
                throw new SendPDFException(LanguageResource.getLanguage().getString("error.email.suffix"), e);
            }
            
            //Construye el cuerpo
            MimeMultipart multiParte = new MimeMultipart();
            
            //Add Mensaje
            if (mensaje!=null) {
                MimeBodyPart texto = new MimeBodyPart();
                texto.setText(mensaje);
                multiParte.addBodyPart(texto);
            }
            
            //Add Adjuntos
            if (archivoAdjunto!=null) {
                for(int x=0; x<archivoAdjunto.size(); x++) {
                        add = archivoAdjunto.get(x);
                        MimeBodyPart adjunto = new MimeBodyPart();
                        adjunto.setDataHandler(new DataHandler(new FileDataSource(archivoAdjunto.get(x))));
                        File archivo = new File(archivoAdjunto.get(x));
                        adjunto.setFileName(archivo.getName());
                        archivo = null;
                        System.gc();
                        multiParte.addBodyPart(adjunto);
                }
            }
            
            MimeMultipart contenidoDefitivo = null;
            //Se firma electronicamente el correo
            if (alias!=null) {
                try {
                    SMIMESignedGenerator gen = new SMIMESignedGenerator();
                    List certList = new ArrayList();
                    X509Certificate signCert = (X509Certificate) ks.getCertificate(alias);
                    certList.add(signCert);
                    Store certs = new JcaCertStore(certList);
                    PrivateKey privada = (PrivateKey) ks.getKey(alias, passwordProtection.getPassword());
                    
                    ASN1EncodableVector         signedAttrs = new ASN1EncodableVector();
                    SMIMECapabilityVector       caps = new SMIMECapabilityVector();
                    caps.addCapability(SMIMECapability.dES_EDE3_CBC);
                    caps.addCapability(SMIMECapability.rC2_CBC, 128);
                    caps.addCapability(SMIMECapability.dES_CBC);
                    signedAttrs.add(new SMIMECapabilitiesAttribute(caps));
                    
                    String hash = null;
                    String hashconf = PreferencesHelper.getPreferences().getString(PreferencesHelper.SIGN_HASH).toUpperCase();
                    if(hashconf.equals("SHA1")) hash = SMIMESignedGenerator.DIGEST_SHA1;
                    else if(hashconf.equals("SHA256")) hash = SMIMESignedGenerator.DIGEST_SHA256;
                    else if(hashconf.equals("SHA384")) hash = SMIMESignedGenerator.DIGEST_SHA384;
                    else if(hashconf.equals("SHA512")) hash = SMIMESignedGenerator.DIGEST_SHA512;
                    hashconf=null; System.gc();
                    if(hash==null) throw new NoSuchAlgorithmException();
                    gen.addSigner(privada, signCert, hash, new AttributeTable(signedAttrs), null);
                    gen.addCertificates(certs);
                    //Se genera la firma
                    MimeBodyPart m = new MimeBodyPart();
                    m.setContent(multiParte);
                    contenidoDefitivo = gen.generate(m);
                                        
                } catch (NoSuchAlgorithmException e) {
                    String msj = MessageFormat.format(LanguageResource.getLanguage().getString("error.sign.hash"), PreferencesHelper.getPreferences().getString(PreferencesHelper.SIGN_HASH).toUpperCase());
                    throw new SendPDFException(msj, e);

                } catch (Exception e) {
                    throw new MessagingException(e.getLocalizedMessage(), e);
                    
                }
            } else {
                contenidoDefitivo = multiParte;
            }

            
            //Abre sesión y construye el paquete
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);

            //Empaqueta el mail
            message.setFrom(new InternetAddress(PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_USER)));
            if(destinatarios != null)
                for(String destinatario : destinatarios)
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            
            if(copias != null)
                for(String copia : copias)
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(copia));
            
            if(copiasOcultas != null)
                for(String copiaOculta : copiasOcultas)
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(copiaOculta));
            
            message.setSubject(asunto);
            message.setContent(contenidoDefitivo);
            message.saveChanges();
                    
            //Envia el mail
            Transport t = session.getTransport("smtp");
            t.connect(PreferencesHelper.getPreferences().getString(PreferencesHelper.EMAIL_SMTP_USER), contraseña);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            return true;
            
        } catch (MessagingException e) {
             if(e.getCause() instanceof IOException) {
                 //Si no encuentra algun archivo que va a adjuntar
                 String msj = MessageFormat.format(LanguageResource.getLanguage().getString("error.email.attachment"), add);
                 throw new SendPDFException(msj, e);
                 
             } else if(e instanceof AddressException) {
                 //Si no se especifica un destinatario
                 String msj = LanguageResource.getLanguage().getString("error.email.no_address");
                 throw new SendPDFException(msj, e);
                 
             } else if(e instanceof AuthenticationFailedException) {
                 //Error de autenticación contra el SMTP
                 String msj = LanguageResource.getLanguage().getString("error.email.authentication");
                 throw new SendPDFException(msj, e);
                 
             } else {
                 logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
                 throw e;
             }
        }
    }
}