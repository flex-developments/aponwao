package flex.aponwao.core.tsa;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.logging.Logger;
import flex.aponwao.core.exceptions.CoreTSAException;

/*
 * Se han reescrito parte de las clases de TSA de la maquina virtual porque tienen un error en la creacion de la request para el tsa
 * Al crear el mensaje, si el algoritmo de hash no tiene parámetros (SHA1 por ejemplo), no añaden al campo parametro un NULL en ANS.1
 * y el servidor TSA no es capaz de procesar correctamente la peticion, además no todos los tsa devuelven la cabecera 
 * application/timestamp-response, algunos devuelven application/timestamp-reply con lo que la validación en base la cabecera
 * también se ha modificado 
 */
public class HttpTimestamper 
{

	
	private static final Logger logger = Logger.getLogger("net.facturae.core.tsa.HttpTimestamper");
	
	private static final int CONNECT_TIMEOUT = 15000; //time-out de la peticion http
	// The MIME type for a timestamp query
	private static final String TS_QUERY_MIME_TYPE = "application/timestamp-query"; //cabecera de la peticion http 

	// The MIME type for a timestamp reply
	private static final String TS_REPLY_MIME_TYPE_0 = "application/timestamp-response"; //posibles cabeceras de la respuesta http
	private static final String TS_REPLY_MIME_TYPE_1 = "application/timestamp-reply"; //posibles cabeceras de la respuesta http
	
	//Servidor TSA
	private String tsaUrl = null;

	public HttpTimestamper(String tsaUrl) 
	{
		this.tsaUrl = tsaUrl;
	}

	
	
	public TSResponse generateTimestamp(TSRequest tsQuery) throws CoreTSAException 
	{
		try 
		{
			return generateTimestamp(tsQuery.encode());
		} 
		catch (IOException e) 
		{
			throw new CoreTSAException(e);
		}
	}
	
	
	/*
	 * Creo un nuevo constructor que tiene como parámetro de entrada un byte[] de la requests en vez de la propia request, por
	 * si se quiere utilizar una request generada por una clase que no pertenece al paquete TSA
	 */
	
	public TSResponse generateTimestamp(byte[] request) throws CoreTSAException {

		
		//abro la conexion http
		HttpURLConnection connection;
		try 
		{
			connection = (HttpURLConnection) new URL(tsaUrl).openConnection();
			
		} 
		catch (MalformedURLException e) 
		{
			throw new CoreTSAException(e);
		} 
		catch (IOException e) 
		{
			throw new CoreTSAException(e);
		}
		
		
		//fijo unos cuantos parametros de la conexion
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Length", (request.length)+""); //esta cabecera no es obligatoria pero por si acaso 
		connection.setRequestProperty("Content-Type", TS_QUERY_MIME_TYPE); 
		try {
			connection.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			throw new CoreTSAException(e);
		}
		connection.setRequestProperty("Content-Transfer-Encoding", "binary"); //tipo de transferencia, binario
		
		// fijo el time-out de la petición
		connection.setConnectTimeout(CONNECT_TIMEOUT);
		try 
		{
			connection.connect();
		} 
		catch (IOException e) 
		{
			throw new CoreTSAException(e);
		}

		// Envio la solicitud por el canal http que acabamos de abrir
		DataOutputStream output = null;
		try 
		{
			try 
			{
				output = new DataOutputStream(connection.getOutputStream());
				output.write(request, 0, request.length);
				output.flush();
			} 
			catch (IOException e) 
			{
				throw new CoreTSAException(e);
			}
		} 
		finally 
		{
			if (output != null) 
			{
				try 
				{
					output.close();
				} 
				catch (IOException e) 
				{
					throw new CoreTSAException(e);
				}
			}
		}

		// Recojo la respuesta
		BufferedInputStream input = null;
		byte[] replyBuffer = null;
		try 
		{
			try 
			{
				input = new BufferedInputStream(connection.getInputStream());
				verifyMimeType(connection.getContentType());
				int contentLength = connection.getContentLength();
				if (contentLength == -1) 
				{
					contentLength = Integer.MAX_VALUE;
				}

				replyBuffer = new byte[contentLength];
				int total = 0;
				int count = 0;
				while (count != -1 && total < contentLength) 
				{
					count = input.read(replyBuffer, total, replyBuffer.length
							- total);
					total += count;
				}
			} 
			catch (IOException e) 
			{
				throw new CoreTSAException(e);
			}
			
		}
		finally 
		{
			if (input != null) 
			{
				try {
					input.close();
				} catch (IOException e) {
					throw new CoreTSAException(e);
				}
			}
		}
		try {
			return new TSResponse(replyBuffer);
		} catch (IOException e) {
			throw new CoreTSAException(e);
		}
	}

	/*
	 * Checks that the MIME content type is a timestamp reply.
	 * 
	 * @param contentType The MIME content type to be checked.
	 * 
	 * @throws IOException The exception is thrown if a mismatch occurs.
	 */
	private static void verifyMimeType(String contentType) throws IOException {
		if (!TS_REPLY_MIME_TYPE_0.equalsIgnoreCase(contentType) && !TS_REPLY_MIME_TYPE_1.equalsIgnoreCase(contentType)) 
		{
			throw new IOException("MIME Content-Type is not "+ TS_REPLY_MIME_TYPE_0 + " or "+TS_REPLY_MIME_TYPE_1);
		}
	}
}
