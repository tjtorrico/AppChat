package com.example.appchat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Datos.Mensaje;
import Datos.UsuarioSqlite;
import Sqlite.DBAdapter;
import WebService.WebService;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@SuppressLint("ResourceAsColor")
public class MensajesNuevos extends AsyncTask<Void, String, Void> implements OnClickListener{
	private DBAdapter db;
	private Gson gson;
	private TextView tvAutodestructible;
	private Base_64 base;
	private Context context;
	
	public MensajesNuevos(Context context){
		this.context = context;
		db = new DBAdapter(context);
		gson = new Gson();
		base = new Base_64();
	}
	
	
	@Override
	protected Void doInBackground(Void... params) {
		String respuesta = "";
		db.abrir();
		ArrayList<UsuarioSqlite> usuario = db.obtenerUsuario();
		db.cerrar();
		
		while(true){
			respuesta = WebService.recibirMensaje(usuario.get(0).getTelefono());
			if(!respuesta.equals("no hay mensajes nuevos") && !respuesta.equals("Error")){
				
			     Type tipoObjeto = new TypeToken<ArrayList <Mensaje>>(){}.getType();
			     ArrayList<Mensaje> listaMensajes= gson.fromJson(respuesta, tipoObjeto);
				 
			     for (int i = 0; i < listaMensajes.size(); i++) {
			    	 
			    	 publishProgress(listaMensajes.get(i).getTelfEmisor(), listaMensajes.get(i).getTelfReceptor(), Md5.decrypt(listaMensajes.get(i).getMensaje()), listaMensajes.get(i).getTipoMensaje(), listaMensajes.get(i).get_id()+"");
			    	 if(listaMensajes.get(i).getTipoMensaje().trim().equals("autodestructible"))
			    		 publishProgress("autodestructible", listaMensajes.get(i).get_id()+"".trim());
			     }
			     
			}
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		}
		
	}



	@Override
	protected void onProgressUpdate(final String... values) {
		super.onProgressUpdate(values);
		if(values.length==5 && !values[3].trim().equals("normal1")){
			//tono recepcion de mensaje
			Audio audio = new Audio();
			audio.reproduce(context, R.raw.msn);
		
			
			if(values[3].trim().equals("normal")){
				if(Chat.contactoSeleccionado!=null){
					if(Chat.contactoSeleccionado.get(0).getTelefono().trim().equals(values[0].trim())){
						TextView tv = new TextView(Chat.contextChat);
						tv.setTextColor(Color.WHITE);
						tv.setText(Chat.contactoSeleccionado.get(0).getNombre()+": "+values[2]);
			
						Chat.tableroMensajes.addView(tv);
					}
				}
				db.abrir();
				db.registrarMensaje(values[0].trim(), values[1].trim(), values[2].trim(), values[3].trim());
				db.cerrar();
			}
			
			if(values[3].trim().equals("autodestructible")){
				if(Chat.contactoSeleccionado!=null){
					if(Chat.contactoSeleccionado.get(0).getTelefono().trim().equals(values[0].trim())){
						tvAutodestructible = new TextView(Chat.contextChat);
						tvAutodestructible.setTextColor(Color.WHITE);
						tvAutodestructible.setText(Chat.contactoSeleccionado.get(0).getNombre()+": "+values[2]);
			
						Chat.tableroMensajes.addView(tvAutodestructible);
							
					}	
				}
			}
		
		if(values[3].trim().equals("imagen")){
			
			TextView tv = new TextView(Chat.contextChat);
			tv.setTextColor(Color.WHITE);
			tv.setText(Chat.contactoSeleccionado.get(0).getNombre()+": ");
			//se crea un layout
			LinearLayout ll = new LinearLayout(Chat.contextChat);
    		ll.setLayoutParams(new LayoutParams(150, 150));
    		ll.setOrientation(LinearLayout.VERTICAL);
    		//se crea un imageview para poner la foto sleccionada de la galeria
    		ImageView iv = new ImageView(Chat.contextChat);
    		iv.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 100));
    		Bitmap bitmap = base.decodeImage(values[2]);
    		iv.setImageBitmap(bitmap);
    		//se crea un boton
    		Button b = new Button(Chat.contextChat);
    		b.setWidth(android.view.ViewGroup.LayoutParams.MATCH_PARENT);
    		b.setHeight(50);
    		b.setTextSize(12);
    		b.setText("Ver");
    		b.setTextColor(Color.WHITE);
    		b.setBackgroundColor(android.R.color.transparent);
    		db.abrir();
    		int pos = db.contarMensajes()+1;
    		b.setId(pos);
    		b.setTag("imagen");
    		db.cerrar();
    		b.setOnClickListener(this);
    		//se agrega el imageview y el boton al layout
    		ll.addView(iv);
    		ll.addView(b);
    		//se agrega el layout ll al layou principal
    		Chat.tableroMensajes.addView(tv);
    		Chat.tableroMensajes.addView(ll);
    		
    		//guarda el mensaje
    		db.abrir();
			db.registrarMensaje(values[0].trim(), values[1].trim(), values[2].trim(), values[3].trim());
			db.cerrar();
		}
		
		if(values[3].trim().equals("esteganografia")){
			
			TextView tv = new TextView(Chat.contextChat);
			tv.setTextColor(Color.WHITE);
			tv.setText(Chat.contactoSeleccionado.get(0).getNombre()+": ");
			//se crea un layout
			LinearLayout ll = new LinearLayout(Chat.contextChat);
    		ll.setLayoutParams(new LayoutParams(150, 150));
    		ll.setOrientation(LinearLayout.VERTICAL);
    		//se crea un imageview para poner la foto sleccionada de la galeria
    		ImageView iv = new ImageView(Chat.contextChat);
    		iv.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 100));
    		Bitmap bitmap = base.decodeImage(values[2]);
    		iv.setImageBitmap(bitmap);
    		//se crea un boton
    		Button b = new Button(Chat.contextChat);
    		b.setWidth(android.view.ViewGroup.LayoutParams.MATCH_PARENT);
    		b.setHeight(50);
    		b.setTextSize(12);
    		b.setTextColor(Color.WHITE);
    		b.setBackgroundColor(android.R.color.transparent);
    		b.setText("Ver");
    		db.abrir();
    		int pos = db.contarMensajes()+1;
    		b.setId(pos);
    		b.setTag("esteganografia");
    		db.cerrar();
    		b.setOnClickListener(this);
    		iv.setOnClickListener(this);
    		iv.setTag("esteganografia");
    		iv.setId(pos);
    		//se agrega el imageview y el boton al layout
    		ll.addView(iv);
    		
    		//se agrega el layout ll al layou principal
    		Chat.tableroMensajes.addView(tv);
    		Chat.tableroMensajes.addView(ll);
    		
    		//guarda el mensaje
    		db.abrir();
			db.registrarMensaje(values[0].trim(), values[1].trim(), values[2].trim(), values[3].trim());
			db.cerrar();
		}
	}
		
		if(values[0].trim().equals("autodestructible")){

			Toast.makeText(Chat.contextChat.getApplicationContext(), "Este Mensaje se Autodestruira en 5 Segundos", Toast.LENGTH_SHORT).show();
			
			BorrarMensaje borrarMensaje = new BorrarMensaje(Chat.contextChat);
			borrarMensaje.execute(tvAutodestructible);
			
			HiloWebService hiloWebService = new HiloWebService(Chat.contextChat, 4, null, null, null, values[0]);
			Thread hilo = new Thread(hiloWebService);
			hilo.start();	
			
		}
		
		if(values.length==5 && values[3].trim().equals("normal1" )){
			//guarda el mensaje
    		db.abrir();
			db.registrarMensaje(values[0].trim(), values[1].trim(), values[2].trim(), values[3].trim());
			db.cerrar();
		}

	}


	@Override
	public void onClick(View v) {
		if(v.getTag().equals("imagen")){
			Dialog dialogo = new Dialog(Chat.contextChat);
			dialogo.setTitle("Imagen");
			ImageView iv = new ImageView(Chat.contextChat);
			iv.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
			
			db.abrir();
			ArrayList<Mensaje> mensajes = db.obtenerMensajes();
			db.cerrar();
			
	
			String imagen = mensajes.get(v.getId()-1).getMensaje();
			Bitmap bm = base.decodeImage(imagen);
			
			iv.setImageBitmap(bm);
			
			dialogo.setContentView(iv);
			dialogo.show();
		}
		
		if(v.getTag().equals("esteganografia")){
			Dialog dialogo = new Dialog(Chat.contextChat);
			dialogo.setTitle("Texto Oculto");
			EditText etTexto = new EditText(context);
			
			db.abrir();
			ArrayList<Mensaje> mensajes = db.obtenerMensajes();
			db.cerrar();
			
			String mensaje = mensajes.get(v.getId()).getMensaje();
			etTexto.setText(mensaje);
			
			dialogo.setContentView(etTexto);
			dialogo.show();
		}
		
	}
	
}
