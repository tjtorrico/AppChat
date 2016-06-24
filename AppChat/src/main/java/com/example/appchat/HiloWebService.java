package com.example.appchat;

import java.util.ArrayList;

import android.content.Context;

import Sqlite.DBAdapter;
import WebService.WebService;
import Datos.ContactoTelefonico;
import Datos.Mensaje;
import Datos.UsuarioSqlServer;
import Datos.UsuarioSqlite;

public class HiloWebService implements Runnable{
	private int tipoWebService;
	private String respuestaWebService;
	private UsuarioSqlServer usuario;
	private ArrayList<ContactoTelefonico> contactosTelefonicos;
	private ArrayList<Mensaje> mensaje;
	private DBAdapter dbMovil;
	private Context context;
	private String _id;
	private boolean b;
	
	
	public HiloWebService(Context context, int tipoWebService, UsuarioSqlServer usuario, ArrayList<ContactoTelefonico> contactosTelefonicos, ArrayList<Mensaje> mensaje, String _id) {
		super();
		this.tipoWebService = tipoWebService;
		this.usuario = usuario;
		this.contactosTelefonicos = contactosTelefonicos;
		this.b = false;
		this.context = context;
		this.dbMovil = new DBAdapter(this.context);
		this.mensaje = mensaje;
		this._id = _id;
	}
	

	@Override
	public void run() {
		switch(tipoWebService){
		case 1:
			respuestaWebService = WebService.registrarUsuario(usuario);
			b = true;
			break;
		case 2:
			dbMovil.abrir();
			ArrayList<UsuarioSqlite> usuario = dbMovil.obtenerUsuario();
			dbMovil.cerrar();
			String telefono = usuario.get(0).getTelefono();
			respuestaWebService = WebService.actualizarContactos(contactosTelefonicos, telefono);
			b = true;
			break;
		case 3:
			respuestaWebService = WebService.enviarMensaje(mensaje);
			break;
		case 4:
			WebService.eliminarMensaje(_id);
			break;	
		}
		
		
		
	}
	
	public boolean obtenerB(){
		return b;
	}
	
	public String obtenerRespuestaWebService(){
		return respuestaWebService;
	}

}
