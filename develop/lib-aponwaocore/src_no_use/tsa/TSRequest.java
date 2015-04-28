package flex.aponwao.core.tsa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.X509Extension;
import sun.security.util.DerValue;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;

/*
* Se han reescrito parte de las clases de TSA de la maquina virtual porque tienen un error en la creacion de la request para el tsa
* Al crear el mensaje, si el algoritmo de hash no tiene parámetros (SHA1 por ejemplo), no añaden al campo parametro un NULL en ANS.1
* y el servidor TSA no es capaz de procesar correctamente la peticion, además no todos los tsa devuelven la cabecera 
* application/timestamp-response, algunos devuelven application/timestamp-reply con lo que la validación en base la cabecera
* también se ha modificado 
*/
public class TSRequest {

	private static final ObjectIdentifier SHA1_OID;
	private static final ObjectIdentifier MD5_OID;
	static {
		ObjectIdentifier sha1 = null;
		ObjectIdentifier md5 = null;
		try {
			sha1 = new ObjectIdentifier("1.3.14.3.2.26");
			md5 = new ObjectIdentifier("1.2.840.113549.2.5");
		} catch (IOException ioe) {
			// should not happen
		}
		SHA1_OID = sha1;
		MD5_OID = md5;
	}

	private int version = 1;
	private ObjectIdentifier hashAlgorithmId = null;
	private byte[] hashValue;
	private String policyId = null;
	private BigInteger nonce = null;
	private boolean returnCertificate = false;
	private X509Extension[] extensions = null;

	/**
	 * Constructs a timestamp request for the supplied hash value..
	 * 
	 * @param hashValue
	 *            The hash value. This is the data to be timestamped.
	 * @param hashAlgorithm
	 *            The name of the hash algorithm.
	 */
	public TSRequest(byte[] hashValue, String hashAlgorithm) {

		// Check the common hash algorithms
		if ("MD5".equalsIgnoreCase(hashAlgorithm)) {
			hashAlgorithmId = MD5_OID;
			// Check that the hash value matches the hash algorithm
			assert hashValue.length == 16;

		} else if ("SHA-1".equalsIgnoreCase(hashAlgorithm)
				|| "SHA".equalsIgnoreCase(hashAlgorithm)
				|| "SHA1".equalsIgnoreCase(hashAlgorithm)) {
			hashAlgorithmId = SHA1_OID;
			// Check that the hash value matches the hash algorithm
			assert hashValue.length == 20;
		}
		// Clone the hash value
		this.hashValue = new byte[hashValue.length];
		//this.hashValue = hashValue;
		System.arraycopy(hashValue, 0, this.hashValue, 0, hashValue.length);
	}

	/**
	 * Sets the Time-Stamp Protocol version.
	 * 
	 * @param version
	 *            The TSP version.
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * Sets an object identifier for the Time-Stamp Protocol policy.
	 * 
	 * @param version
	 *            The policy object identifier.
	 */
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	/**
	 * Sets a nonce. A nonce is a single-use random number.
	 * 
	 * @param nonce
	 *            The nonce value.
	 */
	public void setNonce(BigInteger nonce) {
		this.nonce = nonce;
	}

	/**
	 * Request that the TSA include its signing certificate in the response.
	 * 
	 * @param returnCertificate
	 *            True if the TSA should return its signing certificate. By
	 *            default it is not returned.
	 */
	public void requestCertificate(boolean returnCertificate) {
		this.returnCertificate = returnCertificate;
	}

	/**
	 * Sets the Time-Stamp Protocol extensions.
	 * 
	 * @param extensions
	 *            The protocol extensions.
	 */
	public void setExtensions(X509Extension[] extensions) {
		this.extensions = extensions;
	}

	public byte[] encode() throws IOException 
	{		
		//creo el cuerpo del mensaje request
		DerOutputStream request = new DerOutputStream();
		// le añado la version
		request.putInteger(version);

		// creo el segundo elemento del mensaje, el hash del mensaje para el timestamp y el identificador del algoritmo de hash 
		// encode messageImprint
		DerOutputStream messageImprint = new DerOutputStream();
		DerOutputStream hashAlgorithm = new DerOutputStream();
		hashAlgorithm.putOID(hashAlgorithmId);
		
		/*
		 * Esta linea es necesaria y en el codigo original de la clase no está, si no está resulta que el servidor TSA no entiende la petición y
		 * devuelve un error. 
		 * Esta line es la razon por la que se ha reescrito el código. 
		 */
		
		hashAlgorithm.putNull();
		
		messageImprint.write(DerValue.tag_Sequence, hashAlgorithm);
		messageImprint.putOctetString(hashValue);
		
		//escribo el hash y el id del hash como una sequence en el request
		request.write(DerValue.tag_Sequence, messageImprint);

		// encode optional elements

		if (policyId != null) 
		{
			request.putOID(new ObjectIdentifier(policyId));
		}
		if (nonce != null) {
			//request.putInteger();
			request.putInteger(nonce);
		}
		if (returnCertificate) {
			request.putBoolean(true);
		}

		//cierro el mensaje como un secuencia
		DerOutputStream out = new DerOutputStream();
		out.write(DerValue.tag_Sequence, request);
		return out.toByteArray();
	}
}
