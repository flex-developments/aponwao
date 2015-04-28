package flex.aponwao.core.keystore;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.ProviderException;
import java.security.Security;
import javax.crypto.BadPaddingException;
import flex.aponwao.core.exceptions.CoreException;
import flex.aponwao.core.exceptions.CorePKCS12Exception;
import sun.security.pkcs11.SunPKCS11;
import sun.security.pkcs11.wrapper.PKCS11Exception;

public class KeyStoreBuilderFactory 
{
	public static enum KeyStoreTypes { PKCS11, PKCS12 };
	
	/* Codigos de estado del wraper al dispositivos pkcs11 */
	
	// errores relacionados con el PIN
	public final static String CKR_PIN_INCORRECT = "CKR_PIN_INCORRECT";
	public final static String CKR_PIN_INVALID = "CKR_PIN_INVALID";
	public final static String CKR_PIN_LEN_RANGE = "CKR_PIN_LEN_RANGE";
	public final static String CKR_PIN_EXPIRED = "CKR_PIN_EXPIRED";
	public final static String CKR_PIN_LOCKED = "CKR_PIN_LOCKED";
	
	//error general se produce cuando la tarjeta esta mal insertada o no esta insertada
	public final static String CKR_GENERAL_ERROR = "CKR_GENERAL_ERROR"; 
	
	//error sobre el puerto, implica que el lector esta mal configurado o que el demonio opensc esta inaccesible
	public final static String CKR_DEVICE_ERROR = "CKR_DEVICE_ERROR";
	
	public static KeyStore getKeyStore(String name, KeyStoreTypes type, String file, KeyStore.CallbackHandlerProtection callback ) throws PKCS11Exception, NoSuchAlgorithmException, KeyStoreException, CoreException, CorePKCS12Exception
	{
		return getKeyStore(name,type, file, "0", callback );
	}
	
	
	public static KeyStore getKeyStore(String name,KeyStoreTypes type, String file, String slot, KeyStore.CallbackHandlerProtection callback ) throws PKCS11Exception, NoSuchAlgorithmException, KeyStoreException, CoreException, CorePKCS12Exception 
	{
		KeyStore.Builder builder = null;
		KeyStore ks = null;
		switch( type ) 
		{
			case PKCS11:
				try
				{
					//Si existe el prvider con el nombre que utilizo lo quito
					String pkcs11config = "name = "+name+"\nlibrary ="+file+"\nshowInfo=false\nslotListIndex="+slot;
					InputStream confStream = new ByteArrayInputStream(pkcs11config.getBytes());
					
					
					Provider sunpkcs11 = new SunPKCS11(confStream);
					//si no se ha añadidi antes lo añado
					
					if(Security.getProvider(sunpkcs11.getName()) != null ) //la otra alternativa sería implementar un patron-singleton
					{
						Security.removeProvider(sunpkcs11.getName());
					}
					Security.addProvider(sunpkcs11);
					try
					{
						builder = KeyStore.Builder.newInstance("PKCS11", sunpkcs11 , callback);
					}
					catch (RuntimeException e)
					{
						throw new CoreException(e);
					}
					try 
					{
						ks = builder.getKeyStore();
					}
					catch (KeyStoreException e) 
					{
						Security.removeProvider(sunpkcs11.getName()); //si se produce un error elimino el provider del scope
						// Capturo la excepcion y busco el origen, para identificar si hay problemas con el dispositivo, con el pin de la tarjeta etc...
						throwNestedException(e);
					}
					return ks;
				}
				// capturo todas las excepciones de runtime a la hora de crear el keystore porque si el lector esta mal configurado o el demonio opensc esta parado
				// o la tarjeta está mal insertada la excepcion que se genera es de runtime y enmscara un de tipo PKCS11
				catch (RuntimeException e)
				{
					throwNestedException(e);
				}
			case PKCS12:
				
				try {
                                    builder = KeyStore.Builder.newInstance("PKCS12", null, new File(file), callback);
                                        
				} catch (RuntimeException e) {
					throw new CoreException(e);
				}
				
				try {
                                    ks = builder.getKeyStore();
                                        
				} catch (KeyStoreException e) {
                                    throwNestedException(e);
				}
                                
				if(ks == null) throw new CoreException(new Exception("")); 
				
				return ks;
			default:
				return null;
		}
	}
	
	private static void throwNestedException(Throwable e) throws PKCS11Exception, NoSuchAlgorithmException, KeyStoreException, CoreException, CorePKCS12Exception 
	{
		Throwable origin = e;
		Throwable o = e;
		
		while(o.getCause() != null)
		{
			o = o.getCause();
		}
            //una vez que he llegado a la excepción origen, la lanzo para no perder su información en la traza
            switch (o.getClass().getName()) {
                case "sun.security.pkcs11.wrapper.PKCS11Exception":
                    throw (PKCS11Exception)o;
                case "java.security.NoSuchAlgorithmException":
                    throw (NoSuchAlgorithmException)o;
                case "java.security.ProviderException":
                    throw new CoreException((ProviderException)o);
                case "java.io.IOException":
                    throw new CoreException((IOException)o);
                case "javax.crypto.BadPaddingException":
                    throw new CorePKCS12Exception((BadPaddingException)o);
            }
		
            //si no es de tipo PKCS11 o NoSuchAlgorithmException devuelvo la de entrada
            switch (origin.getClass().getName()) {
                case "java.security.KeyStoreException":
                    throw (KeyStoreException) origin;
                case "java.lang.RuntimeException":
                    throw (RuntimeException) origin;
                default:
                    throw (RuntimeException) origin;
            }
		
	}
}