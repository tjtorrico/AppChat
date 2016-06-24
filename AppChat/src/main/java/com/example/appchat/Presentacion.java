package com.example.appchat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import Datos.UsuarioSqlServer;
import Datos.UsuarioSqlite;
import Sqlite.DBAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Presentacion extends Activity {
	private DBAdapter db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_presentacion);
		db = new DBAdapter(this);
	
	}

	@Override
	protected void onStart() {
		super.onStart();
		//obtengo usuarioList de la base de datos
		db.abrir();
		final ArrayList<UsuarioSqlite> usuarioList = db.obtenerUsuario();
		db.cerrar();
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(usuarioList.size()!=0){//ya hay usuarioList registrado
					startActivity(new Intent(Presentacion.this,Contactos.class));
					finish();
				}else{//no hay usuarioList registrado
					
					//crea dialogo para el registro
					final Dialog dialogoRegistro = new Dialog(Presentacion.this);
					//dialogoRegistro.setCancelable(false);
					dialogoRegistro.setContentView(R.layout.dialogo_registro);
					dialogoRegistro.setTitle("Registro");

					final EditText etPlaca = (EditText) dialogoRegistro.findViewById(R.id.etPlaca);
					final EditText etTelefono = (EditText) dialogoRegistro.findViewById(R.id.etTelefono);
					Button bRegistrarUsuario = (Button) dialogoRegistro.findViewById(R.id.bRegistrarUsuario);
					dialogoRegistro.show();
					
					bRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if(etPlaca.getText().length() >= 6 && etTelefono.getText().length() == 8){
								//obtiene fecha del sistema
								Calendar cal = new GregorianCalendar();
								Date date = (Date) cal.getTime();
								SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
								String fecha = formato.format(date);

								UsuarioSqlServer usuarioMovil = new UsuarioSqlServer(etTelefono.getText().toString(), "conectado", "", fecha, etPlaca.getText().toString());

								//invoca webservice para el registro del usuarioList en el servidor
								HiloWebService hiloRegistrarUsuario = new HiloWebService(Presentacion.this, 1, usuarioMovil, null, null, null);
								Thread thread = new Thread(hiloRegistrarUsuario);
								thread.start();
								while(!hiloRegistrarUsuario.obtenerB());
								if(!hiloRegistrarUsuario.obtenerRespuestaWebService().equals("Error")){//si todo esta correcto
									Toast.makeText(getApplicationContext(), hiloRegistrarUsuario.obtenerRespuestaWebService(), Toast.LENGTH_SHORT).show();
									//registra los datos del usuarioList
									db.abrir();
									db.registrarUsuario(etTelefono.getText().toString(), "");
									db.cerrar();
									//invoca a webservice para cargar contactos del chat
									ActualizarContactos actualizar = new ActualizarContactos(Presentacion.this, Presentacion.this);
									Thread hilo = new Thread(actualizar);
									hilo.start();
									while(actualizar.obtenerAdapter() == null);
									
									startActivity(new Intent(Presentacion.this,Contactos.class));
									dialogoRegistro.dismiss();
									finish();
								}else
									Toast.makeText(getApplicationContext(), hiloRegistrarUsuario.obtenerRespuestaWebService(), Toast.LENGTH_LONG).show();
							}else
								Toast.makeText(Presentacion.this, "Introduzca placa y numéro telefono", Toast.LENGTH_SHORT).show();
							
						}
					});
				}
			}
		},3000);
	}	
	
	

}
