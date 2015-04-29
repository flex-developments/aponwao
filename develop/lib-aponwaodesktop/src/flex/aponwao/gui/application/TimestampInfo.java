package flex.aponwao.gui.application;

import java.security.cert.CertPathValidatorException;
import java.security.cert.X509Certificate;
import java.util.List;

public final class TimestampInfo {
    private int status;
    private List<X509Certificate> chain;
    private CertPathValidatorException chainValidationException;
    public static final int STATUS_CORRUPTED_IMPRINT = 0;
    public static final int STATUS_SIGNER_NOT_FOUND = 1;
    public static final int STATUS_INVALID_SIGN = 2;
    public static final int STATUS_INVALID_CHAIN = 3;
    public static final int STATUS_VALID = 4;
    public static final int STATUS_UKNOWN_ERROR = 10;
    
    public TimestampInfo () {
        this.setStatus(STATUS_UKNOWN_ERROR);
        this.setChain(null);
        this.setChainValidationException(null);
    }
    
    public TimestampInfo (int status, List<X509Certificate> chain, CertPathValidatorException chainValidationException) {
        this.setStatus(status);
        this.setChain(chain);
        this.setChainValidationException(chainValidationException);
    }
    
    public int getSimpleStatus() {
        if (this.status == STATUS_VALID) {
            return (PDFSignatureInfo.SIMPLE_STATUS_VALID);

        } else {
            return (PDFSignatureInfo.SIMPLE_STATUS_WARNING);
        }
    }

    public String getLocalizedDescription() {
        if (this.status == STATUS_VALID) {
                String m = LanguageResource.getLanguage().getString("info.date.valid") + "\n\n"
                                + LanguageResource.getLanguage().getString("info.date.timestamp.valid");
                return m;
                
        } else {
                return "TimeStamp invalido";
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
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
