package flex.aponwao.gui.application;

import flex.eSign.helpers.CertificateHelper;
import java.security.cert.CertPathValidatorException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class PDFSignatureInfo {
        public static final int STATUS_CORRUPTED = 0;
	public static final int STATUS_SIGNER_NOT_FOUND = 1;
	public static final int STATUS_INVALID_SIGN = 2;
	public static final int STATUS_INVALID_CHAIN = 3;
	public static final int STATUS_VALID = 4;
	public static final int STATUS_UKNOWN_ERROR = 10;
	public static final int SIMPLE_STATUS_ERROR = 0;
	public static final int SIMPLE_STATUS_WARNING = 1;
	public static final int SIMPLE_STATUS_VALID = 2;
        //OJO... Modificacion Felix
	public static final int SIMPLE_STATUS_INVALID = 3;
        //**********************************************************************
        
	private String name;
	private int statusFirma = STATUS_UKNOWN_ERROR;
	private List<X509Certificate> chain;
	private CertPathValidatorException chainValidationException;
	private Date date;
	private TimestampInfo timestampInfo;

	public PDFSignatureInfo() {
		this.setName(null);
		this.setChain(null);
		this.setChainValidationException(null);
		this.setDate(null);
		this.setTimestampInfo(null);
	}

	public PDFSignatureInfo(String name, int status, List<X509Certificate> chain, CertPathValidatorException chainValidationException,
			Date date, TimestampInfo timestampInfo) {

		this.setName(name);
		this.setStatusFirma(status);
		this.setChain(chain);
		this.setChainValidationException(chainValidationException);
		this.setDate(date);
		this.setTimestampInfo(timestampInfo);
	}

	public int getSignerSimpleStatus() {
		if (this.statusFirma == STATUS_VALID) {
			return (SIMPLE_STATUS_VALID);
                //OJO... Modificacion Felix
                } else if (this.statusFirma == STATUS_INVALID_SIGN) {
			return (SIMPLE_STATUS_INVALID);
                
		} else if (this.statusFirma == PDFSignatureInfo.STATUS_CORRUPTED || this.statusFirma == PDFSignatureInfo.STATUS_INVALID_CHAIN) {
			//return (SIMPLE_STATUS_ERROR);
                        return (SIMPLE_STATUS_INVALID);
                //**************************************************************        
		} else {
			return (SIMPLE_STATUS_WARNING);
		}
	}

	public int getDateSimpleStatus() {

		if (this.timestampInfo != null) {

			return this.timestampInfo.getSimpleStatus();

		} else {

			return SIMPLE_STATUS_WARNING;
		}
	}

	public String getSignerLocalizedDescription() {
	
		
		String m = "";
		
		if (this.statusFirma == STATUS_VALID) {
			
			m = LanguageResource.getLanguage().getString("info.signature.valid");
			
		} else if (this.statusFirma == STATUS_INVALID_CHAIN) {

			// revocado
			if (this.chainValidationException.getMessage().contains("Certificate has been revoked") || 
					(this.chainValidationException.getCause() != null && 
							this.chainValidationException.getCause().getClass().getSimpleName().toString().equals("CertificateRevokedException")))  {
				
				SimpleDateFormat dateFormat = LanguageResource.getFullFormater();
				m = LanguageResource.getLanguage().getString("error.signature.invalid")
						+ "\n\n"
						+ MessageFormat.format(LanguageResource.getLanguage().getString("error.chain.revoked"), CertificateHelper
								.getCN(this.chain.get(this.chainValidationException.getIndex())), dateFormat.format(this.date) );
				
			// hay que confiar en la ocsp
			} else if (this.chainValidationException.getMessage().contains("Responder's certificate not valid for signing OCSP responses") ) { 
				
				m = LanguageResource.getLanguage().getString("warning.signature.valid")
						+ "\n\n"
						+ MessageFormat.format(LanguageResource.getLanguage().getString("warning.chain.ocsp.no_trusted"),
								CertificateHelper.getCN(this.chain.get(this.chainValidationException.getIndex())));

			// revocacion sin terminar (falta de conexion, ...)
			// con el mensaje "Must specify the location of an OCSP Responder" se cubren los fallos de la crl, ya que generalmente sino tiene nodo ocsp tiene crl.
			// sumo tambien el de fallo de conexion ocsp
			} else if (this.chainValidationException.getMessage().contains("Must specify the location of an OCSP Responder") ||
					(this.chainValidationException.getCause() != null && 
							this.chainValidationException.getCause().getClass().getName().toString().equals("java.net.UnknownHostException"))) {
				
				m = LanguageResource.getLanguage().getString("warning.signature.valid")
				+ "\n\n"
				+ MessageFormat.format(LanguageResource.getLanguage().getString("warning.chain.revocation.uknown"),
						CertificateHelper.getCN(this.chain.get(this.chainValidationException.getIndex())));
				
			// mensaje generico de invalid chain
			} else { 
				
				m = MessageFormat.format(LanguageResource.getLanguage().getString("error.chain.invalid"), 
						CertificateHelper.getCN(this.chain.get(this.chainValidationException.getIndex())), this.chainValidationException.getMessage());
			}
				
		} else {
			
			m = LanguageResource.getLanguage().getString("error.signature.invalid");
		}	
		
		return m;
	}

	public String getDateLocalizedDescription() {

		if (this.timestampInfo != null) {

			return this.timestampInfo.getLocalizedDescription();

		} else {
			
			String m = LanguageResource.getLanguage().getString("info.date.valid") + "\n\n"
					+ LanguageResource.getLanguage().getString("warning.date.local");
			return m;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setStatusFirma(int status) {
            if(this.statusFirma == STATUS_UKNOWN_ERROR )
                this.statusFirma = status;	
	}

	public int getStatusFirma() {
		return statusFirma;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setTimestampInfo(TimestampInfo timestampInfo) {
		this.timestampInfo = timestampInfo;
	}

	public TimestampInfo getTimestampInfo() {
		return timestampInfo;
	}

	public void setChain(List<X509Certificate> chain) {
		this.chain = chain;
	}

	public List<X509Certificate> getChain() {
		return chain;
	}

	public void setChainValidationException(CertPathValidatorException chainValidationException) {
		this.chainValidationException = chainValidationException;
	}

	public Exception getChainValidationException() {
		return chainValidationException;
	}

}