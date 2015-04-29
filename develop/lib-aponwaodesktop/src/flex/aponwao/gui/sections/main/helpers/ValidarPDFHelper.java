package flex.aponwao.gui.sections.main.helpers;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfPKCS7;
import com.itextpdf.text.pdf.PdfReader;
import flex.aponwao.core.validate.CertificatePathBuilder;
import flex.aponwao.gui.application.PDFInfo;
import flex.aponwao.gui.application.PDFSignatureInfo;
import flex.aponwao.gui.application.TimestampInfo;
import flex.aponwao.gui.exceptions.DocumentValidationException;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import flex.eSign.helpers.CertificateHelper;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.tsp.TimeStampToken;

public class ValidarPDFHelper {

	private static final Logger logger = Logger.getLogger(ValidarPDFHelper.class.getName());
        //OJO... Modificacion felix
        private static ArrayList<String> listaSignatarios = new ArrayList<String>();
        private static ArrayList<String> listaFechasFirmas = new ArrayList<String>();

        public static ArrayList<String> getSignatarios() {
            return listaSignatarios;
        }

        public static ArrayList<String> getFechasFirmas() {
            return listaFechasFirmas;
        }
        //**********************************************************************
	
	public static List<PDFSignatureInfo> validatePDF (PDFInfo pdfParameter) throws DocumentValidationException {
            List<PDFSignatureInfo> firmasEncontradas = new ArrayList<PDFSignatureInfo>();
            KeyStore kall = PdfPKCS7.loadCacertsKeyStore();
            PdfReader reader = null;
            try {
                reader = new PdfReader(pdfParameter.getOrigen());
                
            } catch (IOException e) {
                    throw new DocumentValidationException(e);
            }
            
            AcroFields af = reader.getAcroFields();
            ArrayList names = af.getSignatureNames();
            listaSignatarios.clear();
            listaFechasFirmas.clear();
            
            for (int k = 0; k < names.size(); k++) {
                String name = (String)names.get(k);
                PDFSignatureInfo pdfSignature = new PDFSignatureInfo();
                pdfSignature.setName(name);
                PdfPKCS7 p7 = af.verifySignature(pdfSignature.getName());
                Calendar date = p7.getSignDate();
                pdfSignature.setDate(date.getTime());
                
                //OJO... Modificacion Felix ------------------------------------
                listaSignatarios.add(CertificateHelper.getCN(p7.getSigningCertificate()));
                listaFechasFirmas.add(new java.sql.Timestamp(pdfSignature.getDate().getTime()).toString());
                try {
                    //Si no se verifica la firma
                    if (!p7.verify())  pdfSignature.setStatusFirma(PDFSignatureInfo.STATUS_CORRUPTED);
                    //Si la firma no cubre todo el documento
                    if (!af.signatureCoversWholeDocument(name))  pdfSignature.setStatusFirma(PDFSignatureInfo.STATUS_INVALID_SIGN);
                } catch (SignatureException e) {
                    pdfSignature.setStatusFirma(PDFSignatureInfo.STATUS_UKNOWN_ERROR);
                }
                
                //**************************************************************
                
                Object fails[] = PdfPKCS7.verifyCertificates(p7.getCertificates(), kall, null, p7.getSignDate());
                
                if (fails == null)
                    System.out.println("Certificates verified against the KeyStore");
                else
                    System.out.println("Certificate failed: " + fails[1]);

                // TIMESTAMP VERIFICATION
                pdfSignature.setTimestampInfo(validateTimestamp(p7));
                TimestampInfo tsInfo = pdfSignature.getTimestampInfo();

                // if timestamp is correct set it
                if (tsInfo != null && tsInfo.getStatus() == TimestampInfo.STATUS_VALID) {
                        Calendar timestampDate = p7.getTimeStampDate();
                        pdfSignature.setDate(timestampDate.getTime());
                }
                Certificate pkc[] = p7.getSignCertificateChain();
                
                // BUILD AND VALIDATE THE CHAIN
                List<CertStore> certStoreList = new ArrayList<CertStore>();
                certStoreList.add(CertificatePathBuilder.convert2CertStore(pkc));
                certStoreList.add(CertificatePathBuilder.convert2CertStore(PreferencesHelper.getCacheKeystorePreferences()));
                
                CertPath certPath = null;
                try {
                        certPath = CertificatePathBuilder.buildCertPath((X509Certificate)pkc[0], PreferencesHelper.getTrustedKeystorePreferences(), certStoreList);
                        CertificatePathBuilder.printCertPath(certPath);
                        
                        CertificatePathBuilder.verifyCertificateChainRealTime(certPath, PreferencesHelper.getTrustedKeystorePreferences(), date);
                        pdfSignature.setStatusFirma(PDFSignatureInfo.STATUS_VALID);
                        
                } catch (CertPathBuilderException e) {
                        logger.log(Level.SEVERE, "Signature chain generation failed", e);
                        
                } catch (CertPathValidatorException e) {
                        logger.log(Level.SEVERE, "Signature chain validation failed", e);
                        CertPath certPathError = e.getCertPath();
                        List<X509Certificate> listError = (List<X509Certificate>) certPathError.getCertificates();

                        int i = 0;
                        for (X509Certificate certError : listError) {
                                i++;
                        }
                        pdfSignature.setStatusFirma(PDFSignatureInfo.STATUS_INVALID_CHAIN);
                        pdfSignature.setChainValidationException(e);
                        
                } finally {
                        List<X509Certificate> path = new ArrayList<X509Certificate>();
                        for (Certificate c : pkc) {
                                path.add((X509Certificate)c);
                        }
                        pdfSignature.setChain(path);
                }
                
                firmasEncontradas.add(pdfSignature);
            } //Fin del for
                
            pdfParameter.setSignatures(firmasEncontradas);
            return firmasEncontradas;
	}
	
	
	
	public static TimestampInfo validateTimestamp(PdfPKCS7 pk) {
		
		TimestampInfo timestampInfo = null;
		
		TimeStampToken token = pk.getTimeStampToken();
		
		if (token != null) {
		
			timestampInfo = new TimestampInfo();
			
			// VERIFY IMPRINT
			try {
				if (!pk.verifyTimestampImprint()) {
					
//					logger.info("timestamp imprint invalid");
					timestampInfo.setStatus(TimestampInfo.STATUS_CORRUPTED_IMPRINT);
					return timestampInfo;
				}
			} catch (NoSuchAlgorithmException e) {
				
				logger.log(Level.SEVERE, "Error verifiging the timestamp imprint", e);
				timestampInfo.setStatus(TimestampInfo.STATUS_UKNOWN_ERROR);
				return timestampInfo;
			}
			
			
			// generate a Keystore with the timestamp certificates
			Collection<X509Certificate> timestampCertsList = null;
			CertStore certStoreTimestamp = null;
			try {
				certStoreTimestamp = token.getCertificatesAndCRLs("Collection", null);
				timestampCertsList = (Collection<X509Certificate>) certStoreTimestamp.getCertificates(new X509CertSelector());
				
			} catch (NoSuchAlgorithmException e) {
				logger.log(Level.SEVERE, "", e);
				timestampInfo.setStatus(TimestampInfo.STATUS_UKNOWN_ERROR);
				return timestampInfo;
			} catch (NoSuchProviderException e) {
				logger.log(Level.SEVERE, "", e);
				timestampInfo.setStatus(TimestampInfo.STATUS_UKNOWN_ERROR);
				return timestampInfo;
			} catch (CMSException e) {
				logger.log(Level.SEVERE, "", e);
				timestampInfo.setStatus(TimestampInfo.STATUS_UKNOWN_ERROR);
				return timestampInfo;
			} catch (CertStoreException e) {
				logger.log(Level.SEVERE, "", e);
				timestampInfo.setStatus(TimestampInfo.STATUS_UKNOWN_ERROR);
				return timestampInfo;
			}
			
			
			KeyStore ksTimestamp = null;
			try {
				ksTimestamp = KeyStore.getInstance(KeyStore.getDefaultType());
				ksTimestamp.load(null, null);
				
			} catch (KeyStoreException e) {
				logger.log(Level.SEVERE, "", e);
				timestampInfo.setStatus(TimestampInfo.STATUS_UKNOWN_ERROR);
				return timestampInfo;
			} catch (NoSuchAlgorithmException e) {
				logger.log(Level.SEVERE, "", e);
				timestampInfo.setStatus(TimestampInfo.STATUS_UKNOWN_ERROR);
				return timestampInfo;
			} catch (CertificateException e) {
				logger.log(Level.SEVERE, "", e);
				timestampInfo.setStatus(TimestampInfo.STATUS_UKNOWN_ERROR);
				return timestampInfo;
			} catch (IOException e) {
				logger.log(Level.SEVERE, "", e);
				timestampInfo.setStatus(TimestampInfo.STATUS_UKNOWN_ERROR);
				return timestampInfo;
			}
			
			for (X509Certificate certificate : timestampCertsList) {
				
				try {
					ksTimestamp.setCertificateEntry(CertificatePathBuilder.getUniqueID(certificate), certificate);
//					logger.info("timestamp CA certificates: " + certificate.getSubjectDN().getName());
					
				} catch (KeyStoreException e) {
					
					logger.log(Level.SEVERE, "Error adding a certificate in the keystore" , e);
				}
			}
			

			// VERIFY THE SIGNER
			X509Certificate signer = CertificatePathBuilder.verifyTimestampCertificates(token, ksTimestamp, null);
			
			if (signer == null) {
				signer = CertificatePathBuilder.verifyTimestampCertificates(token, PreferencesHelper.getCacheKeystorePreferences(), null);
				if (signer == null) {
					timestampInfo.setStatus(TimestampInfo.STATUS_SIGNER_NOT_FOUND);
					return timestampInfo;
				}
			}


			// BUILD AND VALIDATE THE CHAIN
			List<CertStore> certStoreList = new ArrayList<CertStore>();

			certStoreList.add(certStoreTimestamp);
			certStoreList.add(CertificatePathBuilder.convert2CertStore(PreferencesHelper.getCacheKeystorePreferences()));
			
			CertPath certPath = null;
			
			try {
				certPath = CertificatePathBuilder.buildCertPath(signer, PreferencesHelper.getTrustedKeystorePreferences(), certStoreList);

				CertificatePathBuilder.printCertPath(certPath);
				
				CertificatePathBuilder.verifyCertificateChainRealTime(certPath, PreferencesHelper.getTrustedKeystorePreferences(),
						pk.getTimeStampDate());
				
//				logger.info("Timestamp chain verified");
				
			} catch (CertPathBuilderException e) {
					
				logger.log(Level.SEVERE, "Time stamp chain generation failed", e);
				
				timestampInfo.setStatus(TimestampInfo.STATUS_UKNOWN_ERROR);
				return timestampInfo;
					
			} catch (CertPathValidatorException e) {
				
				// sumarle el trusted

				logger.log(Level.SEVERE, "Time stamp chain validation failed", e);


				CertPath certPathError = e.getCertPath();
				List<X509Certificate> listError = (List<X509Certificate>) certPathError.getCertificates();

				int i = 0;
				for (X509Certificate certError : listError) {
//					logger.info("Error chain " + i + " : " + certError.getSubjectDN());
					i++;
				}
				
//				logger.info("Error en el cert index: " + e.getIndex());
				
				timestampInfo.setChainValidationException(e);
				timestampInfo.setStatus(TimestampInfo.STATUS_INVALID_CHAIN);				
				return timestampInfo;

			} finally {
				
				// COMPLETE DE CHAIN TODO poner esto mejor
				X509Certificate[] chainTimestamp = new X509Certificate[1];
				chainTimestamp [0] = signer;
				chainTimestamp = (X509Certificate[]) CertificatePathBuilder.completeChain(chainTimestamp, ksTimestamp);
				chainTimestamp = (X509Certificate[]) CertificatePathBuilder.completeChain(chainTimestamp, PreferencesHelper.getCacheKeystorePreferences());
				
				timestampInfo.setChain( (List<X509Certificate>)new ArrayList<X509Certificate>(Arrays.asList(chainTimestamp)) );
				
			}
			
			timestampInfo.setStatus(TimestampInfo.STATUS_VALID);
			
			return timestampInfo;			

		}
		
		return timestampInfo;
	}
        
        //Modificacion... Yessica
        //Verificar si el PDF esta firmado
        public static boolean isSigned (String path){
                PdfReader reader = null;
                try {
                    reader = new PdfReader(path);
                } catch (IOException ex) {
                    Logger.getLogger(ValidarPDFHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
                AcroFields af = reader.getAcroFields();
                ArrayList names = af.getSignatureNames(); 
                
                if (names.size() >0) return true;
                else return false;
        }
        //////////////////////////////////////
}