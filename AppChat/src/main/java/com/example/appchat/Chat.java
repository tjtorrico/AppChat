package com.example.appchat;

import java.util.ArrayList;
import Datos.Mensaje;
import Datos.UsuarioSqlServer;
import Datos.UsuarioSqlite;
import Sqlite.DBAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View.OnClickListener;



public class Chat extends Activity implements OnClickListener {
	private DBAdapter db;
	private Base_64 base;
	public static LinearLayout tableroMensajes;
	private Button enviarMensaje;
	private Button enviarImagen;
	public static ArrayList<UsuarioSqlServer> contactoSeleccionado;
	private EditText etmensaje;
	private static final int GALERIA = 1;
	private static final int GALERIA_ESTEGANOGRAFIA = 2;
	private Uri miUriImagen;
	private ImageView ivFoto;
	public static Context contextChat;
	private Dialog dialogoEsteganografia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		contextChat = this;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onStart() {
		super.onStart();
		
		Bundle bundle = getIntent().getExtras();
		db = new DBAdapter(this);
		base = new Base_64();
		//obtengo contacto seleccionado
		db.abrir();
	    contactoSeleccionado = db.obtenerContacto(bundle.getInt("fila")+"");
		db.cerrar();
		
		ivFoto = (ImageView) findViewById(R.id.ivFotoEncabezado);
		Bitmap bm = base.decodeImage(contactoSeleccionado.get(0).getFoto());
		ivFoto.setImageBitmap(bm);
		ivFoto.setOnClickListener(this);
		
		TextView tvNombre = (TextView) findViewById(R.id.tvNombreEncabezado);
		tvNombre.setText(contactoSeleccionado.get(0).getNombre());
		
		TextView tvEstado = (TextView) findViewById(R.id.tvEstadoEncabezado);
		tvEstado.setText(contactoSeleccionado.get(0).getEstado());
		
		tableroMensajes = (LinearLayout) findViewById(R.id.tMensajes);
		
		enviarMensaje = (Button) findViewById(R.id.bEnviar);
		enviarMensaje.setOnClickListener(this);
		
		enviarImagen = (Button) findViewById(R.id.bImagen);
		enviarImagen.setOnClickListener(this);
		
		etmensaje = (EditText) findViewById(R.id.etMensajePie);
		
		
		//mostrar historial
		tableroMensajes.removeAllViews();
		db.abrir();
		ArrayList<Mensaje> listaMensajes = db.obtenerMensajesId(contactoSeleccionado.get(0).getTelefono().trim());
		db.cerrar();
		
		for (int i = 0; i < listaMensajes.size(); i++) {
			TextView tv = new TextView(this);
			if(contactoSeleccionado.get(0).getTelefono().trim().equals(listaMensajes.get(i).getTelfEmisor())){
				
				if(listaMensajes.get(i).getTipoMensaje().trim().equals("normal")){
					tv.setTextColor(Color.WHITE);
					tv.setText(contactoSeleccionado.get(0).getNombre()+": "+listaMensajes.get(i).getMensaje());
					tableroMensajes.addView(tv);
				}else{
					if(listaMensajes.get(i).getTipoMensaje().trim().equals("imagen")){
						tv.setTextColor(Color.WHITE);
						tv.setText(contactoSeleccionado.get(0).getNombre()+": ");
						//se crea un layout
						LinearLayout ll = new LinearLayout(this);
			    		ll.setLayoutParams(new LayoutParams(150, 150));
			    		ll.setOrientation(LinearLayout.VERTICAL);
			    		//se crea un imageview para poner la foto sleccionada de la galeria
			    		ImageView iv = new ImageView(this);
			    		iv.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 100));
			    		Bitmap bitmap = base.decodeImage(listaMensajes.get(i).getMensaje());
			    		iv.setImageBitmap(bitmap);
			    		//se crea un boton
			    		Button b = new Button(this);
			    		b.setWidth(android.view.ViewGroup.LayoutParams.MATCH_PARENT);
			    		b.setHeight(50);
			    		b.setTextSize(12);
			    		b.setId(i);
			    		b.setTextColor(Color.WHITE);
			    		b.setBackgroundColor(android.R.color.transparent);
			    		b.setTag("imagen");
			    		b.setText("Ver");
			    		b.setOnClickListener(this);
			    		//se agrega el imageview y el boton al layout
			    		ll.addView(iv);
			    		ll.addView(b);
			    		//se agrega el layout ll al layou principal
			    		tableroMensajes.addView(tv);
			    		tableroMensajes.addView(ll);
					}else{
						if(listaMensajes.get(i).getTipoMensaje().trim().equals("esteganografia")){
							tv.setTextColor(Color.WHITE);
							tv.setText(contactoSeleccionado.get(0).getNombre()+": ");
							//se crea un layout
							LinearLayout ll = new LinearLayout(this);
				    		ll.setLayoutParams(new LayoutParams(150, 150));
				    		ll.setOrientation(LinearLayout.VERTICAL);
				    		//se crea un imageview para poner la foto sleccionada de la galeria
				    		ImageView iv = new ImageView(this);
				    		iv.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 100));
				    		Bitmap bitmap = base.decodeImage(listaMensajes.get(i).getMensaje());
				    		iv.setImageBitmap(bitmap);
				    		//se crea un boton
				    		Button b = new Button(this);
				    		b.setWidth(android.view.ViewGroup.LayoutParams.MATCH_PARENT);
				    		b.setHeight(50);
				    		b.setTextSize(12);
				    		b.setId(i);
				    		b.setTextColor(Color.WHITE);
				    		b.setBackgroundColor(android.R.color.transparent);
				    		b.setTag("esteganografia");
				    		b.setText("Ver");
				    		b.setOnClickListener(this);
				    		//se agrega el imageview y el boton al layout
				    		ll.addView(iv);
				    		ll.addView(b);
				    		//se agrega el layout ll al layou principal
				    		tableroMensajes.addView(tv);
				    		tableroMensajes.addView(ll);
						}
					}
				}
			}else{
				if(listaMensajes.get(i).getTipoMensaje().trim().equals("normal")){
					tv.setTextColor(Color.WHITE);
					tv.setText("yo: "+listaMensajes.get(i).getMensaje());
					tableroMensajes.addView(tv);
				}else{
					if(listaMensajes.get(i).getTipoMensaje().trim().equals("imagen")){
						tv.setTextColor(Color.WHITE);
						tv.setText("yo: ");
						//se crea un layout
						LinearLayout ll = new LinearLayout(this);
			    		ll.setLayoutParams(new LayoutParams(150, 150));
			    		ll.setOrientation(LinearLayout.VERTICAL);
			    		//se crea un imageview para poner la foto sleccionada de la galeria
			    		ImageView iv = new ImageView(this);
			    		iv.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 100));
			    		Bitmap bitmap = base.decodeImage(listaMensajes.get(i).getMensaje());
			    		iv.setImageBitmap(bitmap);
			    		//se crea un boton
			    		Button b = new Button(this);
			    		b.setWidth(android.view.ViewGroup.LayoutParams.MATCH_PARENT);
			    		b.setHeight(50);
			    		b.setTextSize(12);
			    		b.setId(i);
			    		b.setTextColor(Color.WHITE);
			    		b.setBackgroundColor(android.R.color.transparent);
			    		b.setTag("imagen");
			    		b.setText("Ver");
			    		b.setOnClickListener(this);
			    		//se agrega el imageview y el boton al layout
			    		ll.addView(iv);
			    		ll.addView(b);
			    		//se agrega el layout ll al layou principal
			    		tableroMensajes.addView(tv);
			    		tableroMensajes.addView(ll);
					}else{
						if(listaMensajes.get(i).getTipoMensaje().trim().equals("esteganografia")){
							tv.setTextColor(Color.WHITE);
							tv.setText("yo: ");
							//se crea un layout
							LinearLayout ll = new LinearLayout(this);
				    		ll.setLayoutParams(new LayoutParams(150, 150));
				    		ll.setOrientation(LinearLayout.VERTICAL);
				    		//se crea un imageview para poner la foto sleccionada de la galeria
				    		ImageView iv = new ImageView(this);
				    		iv.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 100));
				    		Bitmap bitmap = base.decodeImage(listaMensajes.get(i).getMensaje());
				    		iv.setImageBitmap(bitmap);
				    		//se crea un boton
				    		Button b = new Button(this);
				    		b.setWidth(android.view.ViewGroup.LayoutParams.MATCH_PARENT);
				    		b.setHeight(50);
				    		b.setTextSize(12);
				    		b.setId(i);
				    		b.setTextColor(Color.WHITE);
				    		b.setBackgroundColor(android.R.color.transparent);
				    		b.setTag("esteganografia");
				    		b.setText("Ver");
				    		b.setOnClickListener(this);
				    		//se agrega el imageview y el boton al layout
				    		ll.addView(iv);
				    		ll.addView(b);
				    		//se agrega el layout ll al layou principal
				    		tableroMensajes.addView(tv);
				    		tableroMensajes.addView(ll);
						}
					}
				}
			}
		}
		///
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.bEnviar:
				if(etmensaje.getText().length()!=0){
					if(Configuracion.Autodestructible==null){
						Mensaje mensaje = new Mensaje();
						//obtiene usuario
						db.abrir();
						ArrayList<UsuarioSqlite> usuario = db.obtenerUsuario();
						db.cerrar();
						//setea todos los atributos de mensaje
						mensaje.setTelfEmisor(usuario.get(0).getTelefono());
						mensaje.setTelfReceptor(contactoSeleccionado.get(0).getTelefono());
						mensaje.setMensaje(Md5.encrypt(etmensaje.getText()+""));
						mensaje.setTipoMensaje("normal");
						
						ArrayList<Mensaje> listaMensaje = new ArrayList<Mensaje>();
						//agrega el objeto mensaje a la lista
						listaMensaje.add(mensaje);
						//agrega mensaje escrito al layout
						TextView tv = new TextView(this);
						tv.setTextColor(Color.WHITE);
						tv.setText("yo: "+etmensaje.getText()+"");
						tableroMensajes.addView(tv);
						
						//guardamos el mensaje
						db.abrir();
						db.registrarMensaje(mensaje.getTelfEmisor().trim(), mensaje.getTelfReceptor().trim(), etmensaje.getText()+"", mensaje.getTipoMensaje());
						db.cerrar();
						
						etmensaje.setText("");
						//guarda el mensaje en el servidor
						HiloWebService hiloWebServiceEnviarMensaje = new HiloWebService(this, 3, null, null, listaMensaje, null);
						Thread hilo = new Thread(hiloWebServiceEnviarMensaje);
						hilo.start();
					}else{
						Mensaje mensaje = new Mensaje();
						//obtiene usuario
						db.abrir();
						ArrayList<UsuarioSqlite> usuario = db.obtenerUsuario();
						db.cerrar();
						//setea todos los atributos de mensaje
						mensaje.setTelfEmisor(usuario.get(0).getTelefono());
						mensaje.setTelfReceptor(contactoSeleccionado.get(0).getTelefono());
						mensaje.setMensaje(Md5.encrypt(etmensaje.getText()+""));
						mensaje.setTipoMensaje("autodestructible");
						
						ArrayList<Mensaje> listaMensaje = new ArrayList<Mensaje>();
						//agrega el objeto mensaje a la lista
						listaMensaje.add(mensaje);
						//guarda el mensaje en el servidor
						HiloWebService hiloWebServiceEnviarMensaje = new HiloWebService(this, 3, null, null, listaMensaje, null);
						Thread hilo = new Thread(hiloWebServiceEnviarMensaje);
						hilo.start();
					}
				}
			break;
			
			case R.id.bImagen:
				if(Configuracion.Esteganografia==null){
	                Intent intent = new Intent();
	                intent.setType("image/*");
	                intent.setAction(Intent.ACTION_GET_CONTENT);
	                startActivityForResult(intent,GALERIA);
				}else{
					Intent intent = new Intent();
	                intent.setType("image/*");
	                intent.setAction(Intent.ACTION_GET_CONTENT);
	                startActivityForResult(intent,GALERIA_ESTEGANOGRAFIA);
				}
			break;
			
			case R.id.ivFotoEncabezado:
				
				Comunicador.setUsuario(contactoSeleccionado.get(0));
				startActivity(new Intent(Chat.this, Perfil.class));
			break;

		}
		
		if(v.getId()!=R.id.bEnviar && v.getId()!=R.id.bImagen && v.getId()!=R.id.ivFotoEncabezado){
			if(v.getTag().equals("imagen")){
				Dialog dialogo = new Dialog(this);
				dialogo.setTitle("Imagen");
				ImageView iv = new ImageView(this);
				iv.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
				
				db.abrir();
				ArrayList<Mensaje> mensajes = db.obtenerMensajes();
				db.cerrar();
				
				String imagen = mensajes.get(v.getId()).getMensaje();
				Bitmap bm = base.decodeImage(imagen);
				
				iv.setImageBitmap(bm);
				
				dialogo.setContentView(iv);
				dialogo.show();
			}
			
			if(v.getTag().equals("esteganografia")){
				Dialog dialogo = new Dialog(Chat.contextChat);
				dialogo.setTitle("Texto Oculto");
				EditText etTexto = new EditText(this);
				
				db.abrir();
				ArrayList<Mensaje> mensajes = db.obtenerMensajes();
				db.cerrar();
				
				String mensaje = mensajes.get(v.getId()+1).getMensaje();
				etTexto.setText(mensaje);
				
				dialogo.setContentView(etTexto);
				dialogo.show();
			}
			
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if(resultCode!=RESULT_OK)return;
	    	switch (requestCode) {
		    	case GALERIA:
		    		miUriImagen = data.getData();
		    		//se crea un layout 
		    		LinearLayout ll = new LinearLayout(this);
		    		ll.setLayoutParams(new LayoutParams(200, 200));
		    		ll.setOrientation(LinearLayout.VERTICAL);
		    		//se crea un imageview para poner la foto sleccionada de la galeria
		    		ImageView iv = new ImageView(this);
		    		iv.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 150));
		    		iv.setImageURI(miUriImagen);
		    		//se crea un boton
		    		Button b = new Button(this);
		    		b.setWidth(android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		    		b.setHeight(50);
		    		b.setTextSize(12);
		    		b.setText("Ver");
		    		//se agrega el imageview y el boton al layout
		    		ll.addView(iv);
		    		ll.addView(b);
		    		//se agrega el layout ll al layou principal
		    		tableroMensajes.addView(ll);
		    		//convertimos la imagen a bitmap
		    		BitmapDrawable bmdG = (BitmapDrawable) iv.getDrawable();
		    		Bitmap bmG = bmdG.getBitmap();
		    		//convertimos a base64
		    		String imagen = base.encodeImage(bmG);
		    		//se crea objeto mensaje
		    		Mensaje mensaje = new Mensaje();
					//obtiene usuario
					db.abrir();
					ArrayList<UsuarioSqlite> usuario = db.obtenerUsuario();
					db.cerrar();
					//setea todos los atributos de mensaje
					mensaje.setTelfEmisor(usuario.get(0).getTelefono());
					mensaje.setTelfReceptor(contactoSeleccionado.get(0).getTelefono());
					mensaje.setMensaje(Md5.encrypt(imagen));
					mensaje.setTipoMensaje("imagen");
					
					ArrayList<Mensaje> listaMensaje = new ArrayList<Mensaje>();
					//agrega el objeto mensaje a la lista
					listaMensaje.add(mensaje);
					//guardamos el mensaje
					db.abrir();
					db.registrarMensaje(mensaje.getTelfEmisor().trim(), mensaje.getTelfReceptor().trim(), imagen, mensaje.getTipoMensaje());
					db.cerrar();
					//guarda el mensaje en el servidor
					HiloWebService hiloWebServiceEnviarMensaje = new HiloWebService(this, 3, null, null, listaMensaje, null);
					Thread hilo = new Thread(hiloWebServiceEnviarMensaje);
					hilo.start();
		    		
		    	break;
		    	
		    	case GALERIA_ESTEGANOGRAFIA:
		    		miUriImagen = data.getData();
//		    		se crea un layout 
		    		final LinearLayout llEsteganografia = new LinearLayout(this);
		    		llEsteganografia.setLayoutParams(new LayoutParams(200, 200));
		    		llEsteganografia.setOrientation(LinearLayout.VERTICAL);
		    		//se crea un imageview para poner la foto sleccionada de la galeria
		    		ImageView ivEsteganografia = new ImageView(this);
		    		ivEsteganografia.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 150));
		    		ivEsteganografia.setImageURI(miUriImagen);
		    		//se crea un boton
		    		Button bEsteganografia = new Button(this);
		    		bEsteganografia.setWidth(android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		    		bEsteganografia.setHeight(50);
		    		bEsteganografia.setTextSize(12);
		    		bEsteganografia.setText("Ver");
		    		//se agrega el imageview y el boton al layout
		    		llEsteganografia.addView(ivEsteganografia);
		    		llEsteganografia.addView(bEsteganografia);
		    		//convertimos la imagen a bitmap
		    		BitmapDrawable bmd = (BitmapDrawable) ivEsteganografia.getDrawable();
		    		Bitmap bm = bmd.getBitmap();
		    		//convertimos a base64
		    		String imagen1 = base.encodeImage(bm);
		    		//se crea objeto mensaje
		    		Mensaje mensaje1 = new Mensaje();
					//obtiene usuario
					db.abrir();
					ArrayList<UsuarioSqlite> usuario1 = db.obtenerUsuario();
					db.cerrar();
					//setea todos los atributos de mensaje
					mensaje1.setTelfEmisor(usuario1.get(0).getTelefono());
					mensaje1.setTelfReceptor(contactoSeleccionado.get(0).getTelefono());
					mensaje1.setMensaje(Md5.encrypt(imagen1));
					mensaje1.setTipoMensaje("esteganografia");
					
					final ArrayList<Mensaje> listaMensaje1 = new ArrayList<Mensaje>();
					//agrega el objeto mensaje a la lista
					listaMensaje1.add(mensaje1);
					//guardamos el mensaje
					db.abrir();
					db.registrarMensaje(mensaje1.getTelfEmisor().trim(), mensaje1.getTelfReceptor().trim(), imagen1, mensaje1.getTipoMensaje());
					db.cerrar();
					//creo un dialogo para introducir el texto para la esteganografia
					dialogoEsteganografia = new Dialog(this);
					dialogoEsteganografia.setTitle("Introducir texto");
					dialogoEsteganografia.setCancelable(false);
					final EditText etTexto = new EditText(this);
					
					Button bEnviar = new Button(this);
					bEnviar.setText("Enviar");
					bEnviar.setBackgroundColor(Color.TRANSPARENT);
					bEnviar.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							
							
							Mensaje mensaje = new Mensaje();
							//obtiene usuario
							db.abrir();
							ArrayList<UsuarioSqlite> usuario = db.obtenerUsuario();
							db.cerrar();
							//setea todos los atributos de mensaje
							mensaje.setTelfEmisor(usuario.get(0).getTelefono());
							mensaje.setTelfReceptor(contactoSeleccionado.get(0).getTelefono());
							mensaje.setMensaje(Md5.encrypt(etTexto.getText()+""));
							mensaje.setTipoMensaje("normal1");
							
							dialogoEsteganografia.dismiss();
							
							ArrayList<Mensaje> listaMensaje = new ArrayList<Mensaje>();
							//agrega el objeto mensaje a la lista
							listaMensaje.add(mensaje);
							//guarda el mensaje en el servidor
							HiloWebService hiloWebServiceEnviarMensaje1 = new HiloWebService(Chat.this, 3, null, null, listaMensaje1, null);
							Thread hilo1 = new Thread(hiloWebServiceEnviarMensaje1);
							hilo1.start();
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//guarda el mensaje en el servidor
							HiloWebService hiloWebServiceEnviarMensaje2 = new HiloWebService(Chat.this, 3, null, null, listaMensaje, null);
							Thread hilo2 = new Thread(hiloWebServiceEnviarMensaje2);
							hilo2.start();
							//db.abrir();
							db.abrir();
							db.registrarMensaje(mensaje.getTelfEmisor().trim(), mensaje.getTelfReceptor().trim(), etTexto.getText()+"", mensaje.getTipoMensaje());
							db.cerrar();
						}
					});
					
					LinearLayout ll2 = new LinearLayout(this);
					ll2.setOrientation(1);
					
					ll2.addView(etTexto);
					ll2.addView(bEnviar);
					
					dialogoEsteganografia.setContentView(ll2);
					dialogoEsteganografia.show();

		    	break;
	    	}
	}
	
}
