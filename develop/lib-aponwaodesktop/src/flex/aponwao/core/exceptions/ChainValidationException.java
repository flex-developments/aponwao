package flex.aponwao.core.exceptions;

import java.security.cert.X509Certificate;

/**
 * @author alfredo
 *
 */ 
public final class ChainValidationException extends Exception {
	// Mantengo esta excepcion porque la otra no da revoked con las comprobaciones ocsp. Aunque creo que se podria utilizar la otra 
	// y encapsular la excepcion de revoked.

	private X509Certificate certificate;
	
	
	public ChainValidationException(String message) {
		
		super(message);
	}
	
	public ChainValidationException(String message, X509Certificate certificate) {
		
		super(message);
		this.setCertificate(certificate);	
	}

	public ChainValidationException(String message, Throwable e) {
		
		super(message, e);
	
	}
	
	public ChainValidationException(String message, Throwable e, X509Certificate certificate) {
		
		super(message, e);
		this.setCertificate(certificate);
	}

	
	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}

	public X509Certificate getCertificate() {
		return certificate;
	}

}