package flex.aponwao.core.tsa;

/*
 * Copyright 2003 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;

/*
* Se han reescrito parte de las clases de TSA de la maquina virtual porque tienen un error en la creacion de la request para el tsa
* Al crear el mensaje, si el algoritmo de hash no tiene parámetros (SHA1 por ejemplo), no añaden al campo parametro un NULL en ANS.1
* y el servidor TSA no es capaz de procesar correctamente la peticion (ZYLK), además no todos los tsa devuelven la cabecera 
* application/timestamp-response, algunos devuelven application/timestamp-reply con lo que la validación en base la cabecera
* también se ha modificado 
*/
public class TimestampToken {

	private int version;
	private ObjectIdentifier policy;
	private BigInteger serialNumber;
	private AlgorithmId hashAlgorithm;
	private byte[] hashedMessage;
	private Date genTime;

	/**
	 * Constructs an object to store a timestamp token.
	 * 
	 * @param status
	 *            A buffer containing the ASN.1 BER encoding of the TSTInfo
	 *            element defined in RFC 3161.
	 */
	public TimestampToken(byte[] timestampTokenInfo) throws IOException {
		if (timestampTokenInfo == null) {
			throw new IOException("No timestamp token info");
		}
		parse(timestampTokenInfo);
	}

	/**
	 * Extract the date and time from the timestamp token.
	 * 
	 * @return The date and time when the timestamp was generated.
	 */
	public Date getDate() {
		return genTime;
	}
	
	public byte[] getHashedMessage() {
		return hashedMessage;
	}

	/*
	 * Parses the timestamp token info.
	 * 
	 * @param timestampTokenInfo A buffer containing an ASN.1 BER encoded
	 * TSTInfo.
	 * 
	 * @throws IOException The exception is thrown if a problem is encountered
	 * while parsing.
	 */
	private void parse(byte[] timestampTokenInfo) throws IOException {

		DerValue tstInfo = new DerValue(timestampTokenInfo);
		if (tstInfo.tag != DerValue.tag_Sequence) {
			throw new IOException("Bad encoding for timestamp token info");
		}
		// Parse version
		version = tstInfo.data.getInteger();
		
		// Parse policy
		policy = tstInfo.data.getOID();

		// Parse messageImprint
		DerValue messageImprint = tstInfo.data.getDerValue();
		hashAlgorithm = AlgorithmId.parse(messageImprint.data.getDerValue());
		hashedMessage = messageImprint.data.getOctetString();

		// Parse serialNumber
		serialNumber = tstInfo.data.getBigInteger();

		// Parse genTime
		genTime = tstInfo.data.getGeneralizedTime();

		// Parse optional elements, if present
		if (tstInfo.data.available() > 0) 
		{
			// Parse accuracy
			// Parse ordering
			// Parse nonce
			// Parse tsa
			// Parse extensions
		}
	}
}
