package com.example.appchat;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.w3c.dom.ls.LSInput;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Adaptadores.AdapterContactos;
import Datos.ContactoTelefonico;
import Datos.UsuarioSqlServer;
import Sqlite.DBAdapter;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

public class ActualizarContactos implements Runnable{
	private Context context;
	private Activity activity;
	private  AdapterContactos adapter;
	private ArrayList<UsuarioSqlServer> listaContactosActualizados;
	private DBAdapter db;
	
	public ActualizarContactos(Context context, Activity activity){
		this.context = context;
		this.activity = activity;
		db = new DBAdapter(context);
	}
	
	@Override
	public void run() {
		
		actualizarContactos();
	}
	
	public void actualizarContactos(){
		ArrayList<ContactoTelefonico> listaContactosTelefonicos = recuperarContactosTelefonicos();
		//
//		ContactoTelefonico contacto = new ContactoTelefonico("luis", "78188766");
//		ContactoTelefonico contacto1 = new ContactoTelefonico("Breidy", "78523234");
//		ArrayList<ContactoTelefonico> listaContactosTelefonicos = new ArrayList<ContactoTelefonico>();
//		listaContactosTelefonicos.add(contacto);
//		listaContactosTelefonicos.add(contacto1);
		//
		HiloWebService runnable = new HiloWebService(context,2, null, listaContactosTelefonicos, null, null);
		Thread hilo = new Thread(runnable);
		hilo.start();
		while(!runnable.obtenerB());
		if(!runnable.obtenerRespuestaWebService().equals("Error")){
			Gson gson = new Gson();
        	Type tipoObjeto = new TypeToken<ArrayList <UsuarioSqlServer>>(){}.getType();
        	listaContactosActualizados = gson.fromJson(runnable.obtenerRespuestaWebService(), tipoObjeto);
	
        	adapter = new AdapterContactos(activity, listaContactosActualizados);
    	
        	db.abrir();
        	db.deleteContactos();
        	
        	for (int i = 0; i < listaContactosActualizados.size(); i++) {
        		db.registrarContacto(listaContactosActualizados.get(i).getTelefono().trim(), listaContactosActualizados.get(i).getEstado().trim(), listaContactosActualizados.get(i).getFoto().trim(), listaContactosActualizados.get(i).getComentario().trim(), listaContactosActualizados.get(i).getNombre().trim(), listaContactosActualizados.get(i).getFecha().trim());
        	}
        	db.cerrar();
		}
	}
	
	public ArrayList<ContactoTelefonico> recuperarContactosTelefonicos(){
		//Creamos un objeto de tipo Uri con el content provider que vamos a utilizar en este caso para obtener informacion a cerca de los contactos
        Uri uriContactos = ContactsContract.Contacts.CONTENT_URI;//Para obtener el nombre del contacto
        Uri uriTelefonos = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;//Para obtener el telefono del contacto
 
        //Array especificando las columnas que queremos obtener de Contactos (Uno para cada uri definida)
        String[] idNombre = new String[] {
                Contacts._ID,//Id contacto
                Contacts.DISPLAY_NAME//Nombre del contacto
        };
 
        String[] numeroTelefono = new String[]{
                CommonDataKinds.Phone.NUMBER//Tel�fono del contacto
        };
 
        //Consulta para este registro
        //managedQuery es un m�todo de la clase Activity
        Cursor cursorIdNombre = activity.managedQuery( uriContactos,
                idNombre, //Nombre de la columna que va a devolver, en este caso hemos creado anteriormente el array de nombre de columnas.
                null, // Clausula where
                null, // Argumentos de seleccion
                null); // Orden del conenido.
 
        Cursor cursorNumeroTelefono = activity.managedQuery(uriTelefonos,
                    numeroTelefono,
                    null,
                    null,
                    null
                );
        ArrayList<ContactoTelefonico> listaContactos = new ArrayList<ContactoTelefonico>();
      //Si el cursor no contiene no contiene nigun registro, no entra en el if
        if ( cursorIdNombre.getCount() != 0)
        {
            //Colocamos a ambos cursores en el primer registro
            cursorIdNombre.moveToFirst();
            cursorNumeroTelefono.moveToFirst();
         
            //Vamos a obtener el numero de la columna con nombre DISPLAY_NAME(contiene el nombre de los contactos) para despu�s poder acceder a ella
            int numeroColumnaNombre = cursorIdNombre.getColumnIndex(Contacts.DISPLAY_NAME);
            //Ahora obtenemos el primer registro de la columna que ser� el nombre del primer contacto de nuestra lista de contactos
            String nombreContacto = cursorIdNombre.getString(numeroColumnaNombre);
         
            //Hacemos lo mismo con el segundo cursor
            int numeroColumnaTelefono = cursorNumeroTelefono.getColumnIndex(Phone.NUMBER);
            String numeroContacto = cursorNumeroTelefono.getString(numeroColumnaTelefono);
            
            ContactoTelefonico contacto1 = new ContactoTelefonico();
            contacto1.setNombre(nombreContacto);
            contacto1.setTelefono(numeroContacto);
            listaContactos.add(contacto1);
         
            //Ahora recorremos el cursor
            
            while(cursorNumeroTelefono.moveToNext() && cursorNumeroTelefono!=null )
            {
       
            	cursorIdNombre.moveToNext();
            	
                nombreContacto = cursorIdNombre.getString(numeroColumnaNombre);
         
                numeroContacto = cursorNumeroTelefono.getString(numeroColumnaTelefono);
                
                ContactoTelefonico contacto2 = new ContactoTelefonico();
                contacto2.setNombre(nombreContacto);
                contacto2.setTelefono(numeroContacto);
                listaContactos.add(contacto2);
            }

        }
        
        return listaContactos;
        
	}

	public AdapterContactos obtenerAdapter() {
		return adapter;
	}
	
	public ArrayList<UsuarioSqlServer> obtenerListaContactos(){
		return listaContactosActualizados;
	}

}
