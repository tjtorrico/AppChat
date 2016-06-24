package com.example.appchat;


import java.util.ArrayList;

import Adaptadores.AdapterContactos;
import Datos.UsuarioSqlServer;
import Sqlite.DBAdapter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Contactos extends Activity implements OnItemClickListener{
	private DBAdapter db;
	private static final int MENU_OPC1 = 1;
	private static final int MENU_OPC2 = 2;
	private ListView lvContactos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactos);
		
		db = new DBAdapter(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		db.abrir();
		ArrayList<UsuarioSqlServer> listaContactos = db.obtenerContactos();
		db.cerrar();
		
		AdapterContactos adaptadorContactos = new AdapterContactos(this, listaContactos);
		
		lvContactos = (ListView) findViewById(R.id.lvContactos);
		lvContactos.setOnItemClickListener(this);
		lvContactos.setAdapter(adaptadorContactos);
		
		//hilo q recibe los mensajes
		MensajesNuevos mensajesNuevos = new MensajesNuevos(this);
		mensajesNuevos.execute();
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch(arg0.getId()){
			case R.id.lvContactos:
				Intent intent = new Intent(Contactos.this, Chat.class);
				intent.putExtra("fila", arg2+1);
				startActivity(intent);
				
			break;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_OPC1, Menu.NONE, "Configuracion").setIcon(android.R.drawable.alert_dark_frame);
		menu.add(Menu.NONE, MENU_OPC2, Menu.NONE, "Actualizar Contactos").setIcon(android.R.drawable.alert_dark_frame);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()){
			case MENU_OPC1:
				startActivity(new Intent(Contactos.this, Configuracion.class));
			break;
			
			case MENU_OPC2:
				ActualizarContactos actualizar = new ActualizarContactos(Contactos.this,Contactos.this);
				Thread hilo = new Thread(actualizar);
				hilo.start();
				while(actualizar.obtenerAdapter()==null);
				
				db.abrir();
				ArrayList<UsuarioSqlServer> listaContactos = db.obtenerContactos();
				db.cerrar();
				
				AdapterContactos adaptadorContactos = new AdapterContactos(this, listaContactos);
				lvContactos.setAdapter(adaptadorContactos);
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	public static boolean getMusic(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("musica", true);
	}

}
