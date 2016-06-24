package com.example.appchat;

import java.util.ArrayList;

import Datos.UsuarioSqlServer;
import Datos.UsuarioSqlite;
import Sqlite.DBAdapter;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Perfil extends Activity {
	private Base_64 base;
	private Button bCambiarTelefono;
	private Button bCambiarComentario;
	private DBAdapter db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);
		
		
		UsuarioSqlServer usuario = Comunicador.getUsuario();
		
		ImageView ivFotoPerfil = (ImageView) findViewById(R.id.ivFotoPerfil);
		base = new Base_64();
		Bitmap bitmap = base.decodeImage(usuario.getFoto());
		ivFotoPerfil.setImageBitmap(bitmap);
		
		TextView tvNombrePerfil = (TextView) findViewById(R.id.tvNombrePerfil);
		tvNombrePerfil.setText(usuario.getNombre().trim());
		
		TextView tvTelefonoPerfil = (TextView) findViewById(R.id.tvTelefonoPerfil);
		tvTelefonoPerfil.setText(usuario.getTelefono().trim());
		
		TextView tvComentarioPerfil = (TextView) findViewById(R.id.tvComentarioPerfil);
		tvComentarioPerfil.setText(usuario.getComentario().trim());
		
		bCambiarTelefono = (Button) findViewById(R.id.bCambiarTelefono);
		
		bCambiarComentario = (Button) findViewById(R.id.bCabiarComentario);
		
		db = new DBAdapter(this);
		db.abrir();
		ArrayList<UsuarioSqlite> listaUaurio = db.obtenerUsuario();
		db.cerrar();
		
		if(!listaUaurio.get(0).getTelefono().trim().equals(usuario.getTelefono().trim())){
			bCambiarTelefono.setVisibility(View.GONE);
			bCambiarComentario.setVisibility(View.GONE);
		}
		
	}


}
