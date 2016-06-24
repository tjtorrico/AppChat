package WebService;

import android.util.Log;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import Datos.ContactoTelefonico;
import Datos.Mensaje;
import Datos.UsuarioSqlServer;

import com.google.gson.Gson;

public class WebService {
	private static Gson gson;
	//Namespace de el Webservice
	private static String NAMESPACE = "http://tjtorrico.net/";
	//Webservice URL 	
	private static String URL = "http://200.87.62.123:9010/servicealerta/ServiceAlerts.asmx";
	//Namespace + incluir el nombre del metodo
	private static String SOAP_ACTION = "http://tjtorrico.net/";
	//METHOD_NAME metodo en este caso el metodo entra por parametro
	private static  String METHOD_NAME;

	public static String actualizarContactos(ArrayList<ContactoTelefonico> lista, String telefono) {
		METHOD_NAME = "actualizarContactos";
		String resTxt = null;
		//creamos el objeto gson
		gson = new Gson();
		//convertimos a formatojson
		String formatoJSON = gson.toJson(lista);
		// creamos el request
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		//agregamos parametros del metodo
		request.addProperty("contactosTelefonicos", formatoJSON);
		request.addProperty("telefono", telefono);
		// creamos un sobre para el envio
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// guardamos en el sobre nuestro objeto request
		envelope.setOutputSoapObject(request);
		// creamos un objeto Http...
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			//invocamos web service
			androidHttpTransport.call(SOAP_ACTION + METHOD_NAME, envelope);
			// obtenemos la respuesta
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// asignamos la respuesta a una variable string
			resTxt = response.toString();

		} catch (Exception e) {
			//Print error
			e.printStackTrace();
			//Assign error message to resTxt
			resTxt = "Error";
		} 
		//Return resTxt to calling object
		return resTxt;
	}
	
	public static String eliminarMensaje(String _id ) {
		METHOD_NAME = "eliminarMensaje";
		String resTxt = null;
		//creamos el objeto gson
		// creamos el request
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		//agregamos parametros del metodo
		request.addProperty("_id", _id);
		// creamos un sobre para el envio
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// guardamos en el sobre nuestro objeto request
		envelope.setOutputSoapObject(request);
		// creamos un objeto Http...
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			//invocamos web service
			androidHttpTransport.call(SOAP_ACTION + METHOD_NAME, envelope);
			// obtenemos la respuesta
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// asignamos la respuesta a una variable string
			resTxt = response.toString();

		} catch (Exception e) {
			//Print error
			e.printStackTrace();
			//Assign error message to resTxt
			resTxt = "Error";
		} 
		//Return resTxt to calling object
		return resTxt;
	}
	
	public static String enviarMensaje(ArrayList<Mensaje> mensaje) {
		METHOD_NAME = "enviarMensaje";
		String resTxt = null;
		//creamos el objeto gson
		gson = new Gson();
		//convertimos a formatojson
		String formatoJSON = gson.toJson(mensaje);
		// creamos el request
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		//agregamos parametros del metodo
		request.addProperty("mensaje", formatoJSON);
		// creamos un sobre para el envio
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// guardamos en el sobre nuestro objeto request
		envelope.setOutputSoapObject(request);
		// creamos un objeto Http...
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			//invocamos web service
			androidHttpTransport.call(SOAP_ACTION + METHOD_NAME, envelope);
			// obtenemos la respuesta
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// asignamos la respuesta a una variable string
			resTxt = response.toString();

		} catch (Exception e) {
			//Print error
			e.printStackTrace();
			//Assign error message to resTxt
			resTxt = "Error";
		} 
		//Return resTxt to calling object
		return resTxt;
	}
	
	public static String recibirMensaje(String telefono) {
		METHOD_NAME = "recibirMensaje";
		String resTxt = null;
		// creamos el request
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		//agregamos parametros del metodo
		request.addProperty("telefono", telefono);
		// creamos un sobre para el envio
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// guardamos en el sobre nuestro objeto request
		envelope.setOutputSoapObject(request);
		// creamos un objeto Http...
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			//invocamos web service
			androidHttpTransport.call(SOAP_ACTION + METHOD_NAME, envelope);
			// obtenemos la respuesta
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// asignamos la respuesta a una variable string
			resTxt = response.toString();

		} catch (Exception e) {
			//Print error
			e.printStackTrace();
			//Assign error message to resTxt
			resTxt = "Error";
		} 
		//Return resTxt to calling object
		return resTxt;
	}
	
	public static String registrarUsuario(UsuarioSqlServer usuarioMovil) {
		METHOD_NAME = "registrarUsuario";
		String resTxt = null;
		//creamos el objeto gson
		gson = new Gson();
		//convertimos a formatojson
		String formatoJSON = gson.toJson(usuarioMovil);
		// creamos el request
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		//agregamos parametros del metodo
		request.addProperty("datosUsuario", formatoJSON);
		// creamos un sobre para el envio
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        // guardamos en el sobre nuestro objeto request
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		try {
			// creamos un objeto Http...
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 2000);
			//invocamos web service
			androidHttpTransport.call(SOAP_ACTION + METHOD_NAME, envelope);
			// obtenemos la respuesta
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// asignamos la respuesta a una variable string
			resTxt = response.toString();

		} catch (Exception e) {
			//Print error
			e.printStackTrace();
			//Assign error message to resTxt
			resTxt = "Error";
		} 
		//Return resTxt to calling object
		return resTxt;
	}
}
