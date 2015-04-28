package flex.aponwao.core.validate;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CRL;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.x500.X500Principal;
import flex.aponwao.core.exceptions.ChainValidationException;
import flex.aponwao.core.exceptions.RevokedException;
import flex.aponwao.core.validator.AponwaoCertPathValidator;
import org.bouncycastle.ocsp.BasicOCSPResp;
import org.bouncycastle.tsp.TimeStampToken;
import sun.security.validator.Validator;

public class CertificatePathBuilder {
	
	
	private static final Logger logger = Logger.getLogger(CertificatePathBuilder.class.getName());
	

	/**
	 * Get a unique id from a certificate ( certificate.getSubjectX500Principal().getName() + certificate.getSerialNumber() )
	 * 
	 * @param cert
	 * @return the id
	 */
	public static String getUniqueID(X509Certificate cert) {
		
		return (cert.getSubjectX500Principal().getName() + cert.getSerialNumber());		
	}	
	

	/**
	 * Find the certificate issuer in the given keyStore
	 * 
	 * @param ks
	 * @param cert
	 * @return issuer, or null if don't find it or is a self signing certificate
	 */
	public static X509Certificate getIssuer(KeyStore ks, X509Certificate cert) {
		

		if (!cert.getIssuerX500Principal().getName(X500Principal.RFC2253).equals(
				cert.getSubjectX500Principal().getName(X500Principal.RFC2253))) {
		
			try {
				for (Enumeration aliases = ks.aliases(); aliases.hasMoreElements();) {
				    
				    String alias = (String)aliases.nextElement();
				    X509Certificate certTrusted = (X509Certificate)ks.getCertificate(alias);
				    try {
						cert.verify(certTrusted.getPublicKey());
						return (certTrusted);
						
					} catch (Exception e) {
						logger.log(Level.INFO, "Controled exception", e);
					}
				}
			} catch (KeyStoreException e) {
				logger.log(Level.SEVERE, "", e);
			}
		}
		
		return (null);
	} 
	
	
	/**
	 * Find signer CAs in the (given) trusted keystore to complete the certificate chain.
	 * All the chain ordered with the user's certificate first and the root certificate authority last.
	 * This function don't validate the input cert chain (originalChain).
	 * 
	 * @param originalChain an incomplete certificate chain (usually just the signer certificate).
	 * @param ks the keystore with the certificates 
	 * @return a complete certificate chain.
	 */
	public static X509Certificate[] completeChain(X509Certificate[] originalChain, KeyStore ks) {

		
		List<X509Certificate> newChainList = new ArrayList<X509Certificate>();
		
		for (int i=0; i<originalChain.length; i++) {
			
			newChainList.add(originalChain[i]);
		}

		X509Certificate lastCert = ((X509Certificate) originalChain[originalChain.length - 1]);


		while (!lastCert.getIssuerX500Principal().getName(X500Principal.RFC2253).equals(
						lastCert.getSubjectX500Principal().getName(X500Principal.RFC2253))) {

			try {
				boolean find = false;
				Enumeration<String> aliases = ks.aliases();
				
				while (aliases.hasMoreElements() && !find) {
					X509Certificate cert = (X509Certificate) ks.getCertificate(aliases.nextElement());
					
						try {
							lastCert.verify(cert.getPublicKey());
							lastCert = cert;
							newChainList.add(cert);
							find = true;
							
						} catch (SignatureException e) {
//							logger.log(Level.INFO, "controled exception", e);
						} catch (InvalidKeyException e) {
							logger.log(Level.SEVERE, "", e);
						} catch (CertificateException e) {
							logger.log(Level.SEVERE, "", e);
						} catch (NoSuchAlgorithmException e) {
							logger.log(Level.SEVERE, "", e);
						} catch (NoSuchProviderException e) {
							logger.log(Level.SEVERE, "", e);
						} 
						

				}
				
				if (!find) {
					logger.warning("Incomplete chain. Can't find all root certificates, so they will no be added to the chain.");
					return (X509Certificate[])newChainList.toArray(new X509Certificate[newChainList.size()]);
				}
				
			} catch (KeyStoreException e) {
				logger.log(Level.SEVERE, "", e);
			}
			
		}
		
		return (X509Certificate[])newChainList.toArray(new X509Certificate[newChainList.size()]);
	}

	
	
	/**
	 * Original by itext. Modified to return the signer certificate.
	 * 
     * Verifies a timestamp against a KeyStore.
     * 
     * @param ts the timestamp
     * @param keystore the <CODE>KeyStore</CODE>
     * @param provider the provider or <CODE>null</CODE> to use the BouncyCastle provider
     * @return <CODE>the signer certificate</CODE> or null if the signer is not in the keystore and could not validate the token
     * @since	2.1.6
     */    
    public static X509Certificate verifyTimestampCertificates(TimeStampToken ts, KeyStore keystore, String provider) {
    	
//        if (provider == null)
//            provider = "BC";
        try {
            for (Enumeration aliases = keystore.aliases(); aliases.hasMoreElements();) {
                try {
                    String alias = (String)aliases.nextElement();
                    if (!keystore.isCertificateEntry(alias))
                        continue;
                    X509Certificate certStoreX509 = (X509Certificate)keystore.getCertificate(alias);
                    ts.validate(certStoreX509, provider);
                    return certStoreX509;
                }
                catch (Exception ex) {
                }
            }
        }
        catch (Exception e) {
        }
        return null;
    }
    
    
    /**
     * Original by itext. Modified to return the signer certificate.
     * 
     * Verifies an OCSP response against a KeyStore.
     * 
     * @param ocsp the OCSP response
     * @param keystore the <CODE>KeyStore</CODE>
     * @param provider the provider or <CODE>null</CODE> to use the BouncyCastle provider
     * @return <CODE>true</CODE> is a certificate was found
     * @since	2.1.6
     */    
    public static X509Certificate verifyOcspCertificates(BasicOCSPResp ocsp, KeyStore keystore, String provider) {
//        if (provider == null)
//            provider = "BC";
        try {
            for (Enumeration aliases = keystore.aliases(); aliases.hasMoreElements();) {
                try {
                    String alias = (String)aliases.nextElement();
                    if (!keystore.isCertificateEntry(alias))
                        continue;
                    X509Certificate certStoreX509 = (X509Certificate)keystore.getCertificate(alias);
                    if (ocsp.verify(certStoreX509.getPublicKey(), provider))
                        return certStoreX509;
                }
                catch (Exception ex) {
                }
            }
        }
        catch (Exception e) {
        }
        return null;
    }
	
	
	
	 /**
     * Original by itext.
     * Verifies a single certificate. Added ocsp validation.
     * 
     * @param cert the certificate to verify
     * @param crls the certificate revocation list or <CODE>null</CODE>
     * @param calendar the date or <CODE>null</CODE> for the current date
     * @return a <CODE>String</CODE> with the error description or <CODE>null</CODE>
     * if no error
	 * @throws CertificateNotYetValidException 
	 * @throws CertificateExpiredException 
	 * @throws RevokedException 
     */    
    public static void verifyCertificate(X509Certificate cert, Collection crls, Calendar calendar) throws CertificateExpiredException, CertificateNotYetValidException, RevokedException {
    	
        if (calendar == null)
            calendar = new GregorianCalendar();
        
        if (cert.hasUnsupportedCriticalExtension()) {
//            return "Has unsupported critical extension";
        	
        }
        
        cert.checkValidity(calendar.getTime());
        
        
        if (crls != null) {
        	
            for (Iterator it = crls.iterator(); it.hasNext();) {
                if (((CRL)it.next()).isRevoked(cert)) {
                	throw new RevokedException();
                }
            }
        }

    }
	
	/**
     * Verifies a certificate chain against a KeyStore. If the function finish the chain is valid.
     *  
     * @param the certificate chain
     * @param kstrusted the <CODE>KeyStore</CODE>
     * @param crls the certificate revocation list or <CODE>null</CODE>
     * @param calendar the date or <CODE>null</CODE> for the current date
     * 
	 * @throws ChainValidationException 
	 *  
     */    
    public static void verifyCertificateChain(Certificate chain[], KeyStore kstrusted, Collection crls, Calendar calendar) throws ChainValidationException {
    	
    	
    	// genero un ks con todos los cert de la chain
    	KeyStore ksTemp = null;
		try {
			ksTemp = KeyStore.getInstance(KeyStore.getDefaultType());
			ksTemp.load(null, null);
			
		} catch (KeyStoreException e) {
			logger.log(Level.SEVERE, "", e);
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "", e);
		} catch (CertificateException e) {
			logger.log(Level.SEVERE, "", e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}
		
		for (X509Certificate certificate : (X509Certificate[])chain) {
			
			try {
				ksTemp.setCertificateEntry(CertificatePathBuilder.getUniqueID(certificate), certificate);
				
			} catch (KeyStoreException e) {		
				logger.log(Level.SEVERE, "Error adding a certificate in the keystore" , e);
			}
		}
    	
    	// validacion JAVA adicional de la chain (este validador creo que comprueba el keyusage)
		Validator validator = Validator.getInstance(Validator.TYPE_PKIX, Validator.VAR_GENERIC, ksTemp);
		
		try {
			// validate one or more chains using the validator
			validator.validate((X509Certificate[])chain);
			logger.info("Chain validated, PKIX validation");
			
		} catch (CertificateException e) {
			// throws CertificateException if failed
			throw new ChainValidationException ("Error in chain, PKIX validation", e);
		}
    	
		
		
    	
    	
        if (calendar == null)
            calendar = new GregorianCalendar();
        
        for (int k = 0; k < chain.length; ++k) {
        	
            X509Certificate certChain = (X509Certificate)chain[k];
            
            try {
            	verifyCertificate(certChain, crls, calendar);
            	
	        } catch (CertificateExpiredException e) {
	        	throw new ChainValidationException ("Expired", e, certChain);
	        	
			} catch (CertificateNotYetValidException e) {
				throw new ChainValidationException ("NotYetValid", e, certChain);
				
			} catch (RevokedException e) {
				throw new ChainValidationException ("Revoked", e, certChain);
			}
            
            try {
                for (Enumeration aliases = kstrusted.aliases(); aliases.hasMoreElements();) {
                    try {
                        String alias = (String)aliases.nextElement();
                        if (!kstrusted.isCertificateEntry(alias))
                            continue;
                        X509Certificate certStoreX509 = (X509Certificate)kstrusted.getCertificate(alias);
                        
                        try {
                        	verifyCertificate(certStoreX509, crls, calendar);
                        } catch (CertificateExpiredException e) {
                        	logger.log(Level.INFO, "Controled exception", e);
						} catch (CertificateNotYetValidException e) {
							logger.log(Level.INFO, "Controled exception", e);
						} catch (RevokedException e) {
							logger.log(Level.INFO, "Controled exception", e);
						}
                            
                        try {
                            certChain.verify(certStoreX509.getPublicKey());
                            // OK
                            return;
                        }
                        catch (Exception e) {
                            continue;
                        }
                    }
                    catch (Exception ex) {
                    }
                }
            }
            catch (Exception e) {
            }
            int j;
            for (j = 0; j < chain.length; ++j) {
                if (j == k)
                    continue;
                X509Certificate certNext = (X509Certificate)chain[j];
                try {
                    certChain.verify(certNext.getPublicKey());
                    break;
                }
                catch (Exception e) {
                }
            }
            if (j == chain.length) {
            	throw new ChainValidationException ("Cannot be verified against the KeyStore or the certificate chain", certChain);
            }
        }
        
        throw new ChainValidationException ("Invalid state. Possible circular certificate chain");
        
    }
	
	
	
	
    /**
     * Verifies a certificate chain against a KeyStore. If the function finish the chain is valid.
     *  
     * @param chain the certificate chain
     * @param ksTrust the <CODE>KeyStore</CODE>
     * @param crls the certificate revocation list or <CODE>null</CODE>
     * @param calendar the date or <CODE>null</CODE> for the current date
     * 
	 * @throws ChainValidationException 
	 *  
     */    
    public static void verifyCertificateChainRealTime(CertPath certPath, KeyStore ksTrust, Calendar calendar) throws CertPathValidatorException {
    	
    	
    	// pasar los cert del certPath a certificados creados con el provider de sun, sino da error
    	CertificateFactory certFact = null;
		try {
			certFact = CertificateFactory.getInstance("X.509");
			
		} catch (CertificateException e) {
			logger.log(Level.SEVERE, "", e);
		}
    	
    	List<Certificate> certList = (List<Certificate>) certPath.getCertificates();
    	
    	List<Certificate> newList = new ArrayList<Certificate>();
    	 
    	for (Certificate cert : certList) {
			
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(cert.getEncoded());
				Certificate certificate = certFact.generateCertificate(bais);
				newList.add(certificate);
				
			} catch (CertificateEncodingException e) {
				logger.log(Level.SEVERE, "", e);

			} catch (CertificateException e) {
				logger.log(Level.SEVERE, "", e);
			}
		}
    	
    	CertPath certPathSun = null;
		try {
			certPathSun = certFact.generateCertPath(newList);
			
		} catch (CertificateException e) {
			logger.log(Level.SEVERE, "", e);
		}
    	
		// TODO si no registrarlo
		Provider provider = Security.getProvider("SUN");
    	
		
		
		// Create the parameters for the validator
		PKIXParameters params = null;
		try {
			params = new PKIXParameters(ksTrust);

		} catch (KeyStoreException e) {
			logger.log(Level.SEVERE, "", e);
		} catch (InvalidAlgorithmParameterException e) {
			logger.log(Level.SEVERE, "", e);
		}


		params.setRevocationEnabled(true);
		
		
		
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		System.out.println("Class loader system: " + loader.toString());
		
		System.out.println("Class loader sun: start");
		if (params.getClass().getClassLoader() != null)
			System.out.println("Class loader sun: " + params.getClass().getClassLoader().toString());
		System.out.println("Class loader sun: end");
		
		
		
		
		
		logger.info("chain validation date: " + calendar.getTime().toGMTString());
		params.setDate(calendar.getTime());
		
//		critical policy qualifiers present in certificate
		params.setPolicyQualifiersRejected(false);
		

		// Create the validator and validate the path
		AponwaoCertPathValidator certPathValidator = null;
		try {
			certPathValidator = AponwaoCertPathValidator.getInstance("PKIX", provider);

		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "", e);
		}

		
		
		try {
			logger.info("Starting path validation...");
			
			CertPathValidatorResult result = certPathValidator.validate(certPathSun, params);

			// Get the CA used to validate this path
			PKIXCertPathValidatorResult pkixResult = (PKIXCertPathValidatorResult) result;


		} catch (InvalidAlgorithmParameterException e) {
			
			logger.log(Level.SEVERE, "", e);
		}
    	
    }
	
	
	
	
	
	
    public static CertPath convert2CertPath(Certificate[] certs) {
    	
    	
        try {
            CertificateFactory certFact = CertificateFactory.getInstance("X.509");
            CertPath path = certFact.generateCertPath(Arrays.asList(certs));
            
            return path;
            
        } catch (CertificateEncodingException e) {
        	logger.log(Level.SEVERE, "", e);
        } catch (CertificateException e) {
        	logger.log(Level.SEVERE, "", e);
        }
        
        return null;
    }
	
    public static CertPath convert2CertPath(List certs) {
    	
        try {
            CertificateFactory certFact = CertificateFactory.getInstance("X.509");
            CertPath path = certFact.generateCertPath(certs);
            
            return path;
            
        } catch (CertificateEncodingException e) {
        	logger.log(Level.SEVERE, "", e);
        } catch (CertificateException e) {
        	logger.log(Level.SEVERE, "", e);
        }
        
        return null;
    }
    
    
    public static CertStore convert2CertStore(KeyStore ks) {

    	List<Certificate> mylist = new ArrayList<Certificate>();
    	
		try {
			Enumeration<String> aliases = ks.aliases();
			
	    	while ( aliases.hasMoreElements() ) {
				
	    		String alias = aliases.nextElement();
	    		Certificate cert = ks.getCertificate(alias);
	    		mylist.add(cert);
			}
		} catch (KeyStoreException e) {
			logger.log(Level.SEVERE, "", e);
		}
    	
    	CertStoreParameters cparam = new CollectionCertStoreParameters(mylist);
    	
	    CertStore cs = null;
		try {
			cs = CertStore.getInstance("Collection", cparam);
			
		} catch (InvalidAlgorithmParameterException e) {
			logger.log(Level.SEVERE, "", e);
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "", e);
		}
		    
	    return cs;
	}
    
    
    public static CertStore convert2CertStore(Certificate[] array) {

    	
    	List<Certificate> mylist = new ArrayList<Certificate>();
    	
    	for (Certificate cert : array) {
			
	    	mylist.add(cert);
		}
		
    	
    	CertStoreParameters cparam = new CollectionCertStoreParameters(mylist);
    	
	    CertStore cs = null;
		try {
			cs = CertStore.getInstance("Collection", cparam);
			
		} catch (InvalidAlgorithmParameterException e) {
			logger.log(Level.SEVERE, "", e);
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.SEVERE, "", e);
		}
		    
	    return cs;
	}
	
	
	
    /**
     * Devuelve un CertPath vacion en caso de que se confie directamente en el firmante
     * 
     * @param cert
     * @param ksTrust
     * @param certStoreList
     * @return
     * @throws CertPathBuilderException
     */
    public static CertPath buildCertPath(X509Certificate cert, KeyStore ksTrust, List<CertStore> certStoreList) throws CertPathBuilderException {
		
    	
    	try {
    	
    		CertificateFactory certFact = CertificateFactory.getInstance("X.509");
    		
	    	List<X509Certificate> certPathList = new ArrayList<X509Certificate>();
	    	
	    	X509Certificate currentCert = cert;

	    	while (currentCert != null) {
	    	
	    		// 	comprobar si el firmante esta en el trusted
				
				Enumeration<String> en = ksTrust.aliases();
			
				while (en.hasMoreElements()) {
					
					X509Certificate ca = (X509Certificate)ksTrust.getCertificate(en.nextElement());
					
					if ( currentCert.getSubjectX500Principal().getName().equals(ca.getSubjectX500Principal().getName()) ) {
						
						// devuelvo un cert path vacio ya que directamente confia en el firmante
						CertPath certPath = certFact.generateCertPath(certPathList);
						return certPath;
					}
						
					if (currentCert.getIssuerX500Principal().getName().equals(ca.getSubjectX500Principal().getName())) {

						certPathList.add(currentCert);
						CertPath certPath = certFact.generateCertPath(certPathList);
						return certPath;
					}
				}
				
				certPathList.add(currentCert);
				
				X509CertSelector s = new X509CertSelector();
				s.setSubject(((X509Certificate)currentCert).getIssuerX500Principal());
					
					
				// recorro los certStore
				Iterator<CertStore> it = certStoreList.iterator();
				
				boolean find = false;
					
				while (it.hasNext() && !find ) {
						
					CertStore certStore = it.next();
						
					Set<X509Certificate> set = (Set<X509Certificate>)certStore.getCertificates(s);
					
					if (set.size() > 0) { // encontrado el CA
						
						X509Certificate nextCert = (X509Certificate)set.iterator().next();
						
						// Si no es el root CA
						if (!nextCert.getSubjectX500Principal().getName().equals(nextCert.getIssuerX500Principal().getName())) {
							
							currentCert = nextCert;							
							find = true;
							
						} else {
							currentCert = null;
						}
					}
					
				}
				
				if (!find) { // si no lo ha encontrado 
					
					currentCert = null;
				}
				
			}
			
	    	throw new CertPathBuilderException();
			
		} catch (KeyStoreException e) {
			logger.log(Level.SEVERE, "", e);
		} catch (CertStoreException e) {
			logger.log(Level.SEVERE, "", e);
		} catch (CertificateException e) {
			logger.log(Level.SEVERE, "", e);
		}

	    
		return null;

    }
    
    
    
    public static CertPath buildCertPathSun(X509Certificate cert, KeyStore ksTrust, List<CertStore> certStoreList) throws CertPathBuilderException {
		

			X509CertSelector s = new X509CertSelector();
			
			s.setSerialNumber(((X509Certificate)cert).getSerialNumber());
			try {
				s.setSubject(((X509Certificate)cert).getSubjectX500Principal().getEncoded());
				System.out.println("Builder: " + cert.getSubjectX500Principal().getName());
			} catch (IOException e) {
				logger.log(Level.SEVERE, "", e);
			}
			

			PKIXBuilderParameters parameters = null;
			try {
//	    		parameters = new PKIXBuilderParameters( Collections.singleton(new TrustAnchor((X509Certificate)cert, null)), s);
				parameters = new PKIXBuilderParameters(ksTrust, s);
				
			} catch (KeyStoreException e) {
				logger.log(Level.SEVERE, "", e);
			} catch (InvalidAlgorithmParameterException e) {
				logger.log(Level.SEVERE, "", e);
			}
			
			parameters.setRevocationEnabled(false);

			
			for (CertStore certStore : certStoreList) {
				
				parameters.addCertStore(certStore);
			}
			
			CertPathBuilder certPathBuilder = null;
			try {
				certPathBuilder = CertPathBuilder.getInstance("PKIX");
			} catch (NoSuchAlgorithmException e) {
				logger.log(Level.SEVERE, "", e);
			}
			
			PKIXCertPathBuilderResult r = null;
			try {
				r = (PKIXCertPathBuilderResult)certPathBuilder.build(parameters);
			} catch (InvalidAlgorithmParameterException e) {
				logger.log(Level.SEVERE, "", e);
			}
			
			
			CertPath cp = r.getCertPath();
			
			System.out.println("size: " + cp.getCertificates().size());
			
			for (Certificate c : cp.getCertificates()) {
				
				System.out.println("chain: " + ((X509Certificate)c).getSubjectDN());
				
			}
			
			
			TrustAnchor anchor = r.getTrustAnchor();
			
			System.out.println("trust: " + anchor.getTrustedCert().getSubjectDN());
			
			
			return cp;

        }


	public static void printCertPath(CertPath certPath) {
		
		
		List<X509Certificate> list = (List<X509Certificate>)certPath.getCertificates();
		
		System.out.println("---print cert path---");
		for (X509Certificate certificate : list) {
			
			System.out.println("certificate.getSubjectDN(): " + certificate.getSubjectDN());
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	


	
	
//	public static void ocspValidation(boolean enable, String ocspUrl)
//	{
//		if(enable)
//		{
//			Security.setProperty("ocsp.enable", "true");
//			Security.setProperty("ocsp.responderURL", ocspUrl);
//		}
//		else
//			Security.setProperty("ocsp.enable", "false");
//	}
	
	
	
//	public static CertPathValidatorResult validateCert(KeyStore ks, String certalias2validate, Set<TrustAnchor> trustCertSet)
//	{
//		// obtenemos los certificados de la ruta de certificacion del alias certalias2validate
//	    Certificate[] certArray = null;
//	    X509CertImpl cert = null;
//	    String issuerDN = null;
////	    System.out.println("certalias2validate->"+certalias2validate);
//	    
//		try {
//			certArray = ks.getCertificateChain(certalias2validate);
//		} catch (KeyStoreException e) {
//			logger.log(Level.SEVERE,"KeyStoreException",e);
//		}
//	    // convertimos a una lista los certificados de la ruta de certificacion
//		List<Certificate> certList = Arrays.asList(certArray);
//		
//		X509CertImpl issuer = null;
//		for (Certificate certificate : certList) 
//		{	
//			issuer = (X509CertImpl) certificate;
//		}
//		
//	    
//	    //creamos el objeto certPath a validar
//	    
//	    CertificateFactory cf;
//	    CertPath cp = null;
//		try {
//			cf = CertificateFactory.getInstance("X.509");
//			cp = cf.generateCertPath(certList);
//		} catch (CertificateException e) {
//			logger.log(Level.SEVERE,"CertificateException",e);
//		}
//	    
//		PKIXParameters params = null;
//		
//		try {
//			params = new PKIXParameters(trustCertSet);
//			//no compruebo el estado de revocacion
//			params.setRevocationEnabled(false);
//			
//		} catch (InvalidAlgorithmParameterException e) {
//			logger.log(Level.SEVERE,"InvalidAlgorithmParameterException",e);
//		}
//		CertPathValidator certPathValidator = null;
//		try {
//			certPathValidator = CertPathValidator.getInstance("PKIX");
//		} catch (NoSuchAlgorithmException e) {
//			logger.log(Level.SEVERE,"NoSuchAlgorithmException",e);
//		}
//		
//		CertPathValidatorResult res = null;
//		try
//		{
//			res = certPathValidator.validate(cp, params);
//		}
//		catch(CertPathValidatorException e)
//		{
//			logger.log(Level.SEVERE,"CertPathValidatorException",e);
//		} catch (InvalidAlgorithmParameterException e) {
//			logger.log(Level.SEVERE,"InvalidAlgorithmParameterException",e);
//		}
//		return res;
//	}
}
