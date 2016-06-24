package Sqlite;

import java.util.ArrayList;

import Datos.Mensaje;
import Datos.UsuarioSqlServer;
import Datos.UsuarioSqlite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	 
	public static final String TABLA_USUARIO = "usuario";
	public static final String COLUMNA_USUARIO_TELEFONO= "telefono";
	public static final String COLUMNA_USUARIO_FOTO = "foto";
	
	public static final String TABLA_CONTACTO = "contacto";
	public static final String COLUMNA_CONTACTO_TELEFONO= "telefono";
	public static final String COLUMNA_CONTACTO_ESTADO = "estado";
	public static final String COLUMNA_CONTACTO_FOTO = "foto";
	public static final String COLUMNA_CONTACTO_COMENTARIO = "comentario";
	public static final String COLUMNA_CONTACTO_NOMBRE = "nombre";
	public static final String COLUMNA_CONTACTO_FECHA = "fecha";
	
	public static final String TABLA_MENSAJE = "mensaje";
	public static final String COLUMNA_MENSAJE_TELF_EMISOR = "telf_emisor";
	public static final String COLUMNA_MENSAJE_TELF_RECEPTOR = "telf_receptor";
	public static final String COLUMNA_MENSAJE_MENSAJE = "mensaje";
	public static final String COLUMNA_MENSAJE_TIPO_MENSAJE = "tipo_mensaje";

	public static final String[] COLUMNAS_USUARIO = new String[] {"telefono","foto"};
	public static final String[] COLUMNAS_CONTACTO = new String[] {"telefono", "estado", "foto", "comentario", "nombre","fecha"};
	public static final String[] COLUMNAS_MENSAJE = new String[] {"telf_emisor","telf_receptor","mensaje","tipo_mensaje"};
	
	private Context context;
	private SQLiteDatabase database;
	private DataBaseHelper dbHelper;
 
	public DBAdapter(Context context) {
		this.context = context;
	}
 
	public DBAdapter abrir() throws SQLException {
		dbHelper = new DataBaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
 
	public void cerrar() {
		dbHelper.close();
	}
 
	/**
	 * Crea una nuevo ususario, si esto va bien retorna la
	 * rowId de la tarea, de lo contrario retorna -1
	 */
	
	public void deleteContactos() {
		database.execSQL("DELETE FROM  contacto");
	}
	//////////////////////actualizar/////////////////////////////
	public int actualizarUsuario(String telefono, String foto) {
		ContentValues updateValues = contentValuesUsuario(telefono, foto);
		
		return database.update(TABLA_USUARIO, updateValues, "rowid"+ "=" + "1", null);
	}
	//////////////////////////////Obtener//////////////////////////////////////
	public ArrayList<UsuarioSqlite> obtenerUsuario() {
		
	ArrayList<UsuarioSqlite> listaUsuario = new ArrayList<UsuarioSqlite>();
	Cursor result = database.query(TABLA_USUARIO,COLUMNAS_USUARIO, null, null, null, null, null,null);
		if (result.moveToFirst()){
			do {
				listaUsuario.add(new UsuarioSqlite(result.getString(result.getColumnIndex("telefono")),
									 	     result.getString(result.getColumnIndex("foto"))));
			} while(result.moveToNext());
		}
	return listaUsuario;
	}
	
	public ArrayList<UsuarioSqlServer> obtenerContactos() {
		
	ArrayList<UsuarioSqlServer> listaContactos = new ArrayList<UsuarioSqlServer>();
	Cursor result = database.query(TABLA_CONTACTO,COLUMNAS_CONTACTO, null, null, null, null, null,null);
		if (result.moveToFirst()){
			do {
				listaContactos.add(new UsuarioSqlServer(result.getString(result.getColumnIndex("telefono")),
														result.getString(result.getColumnIndex("estado")),
														result.getString(result.getColumnIndex("foto")),
														result.getString(result.getColumnIndex("comentario")),
														result.getString(result.getColumnIndex("nombre")),
														result.getString(result.getColumnIndex("fecha"))));
			} while(result.moveToNext());
		}
	return listaContactos;
	}
	
	public ArrayList<UsuarioSqlServer> obtenerContacto(String id) {
		
		ArrayList<UsuarioSqlServer> listaContactos = new ArrayList<UsuarioSqlServer>();
		Cursor result = database.query(TABLA_CONTACTO,COLUMNAS_CONTACTO, "rowid = "+id, null, null, null, null,null);
			if (result.moveToFirst()){
				do {
					listaContactos.add(new UsuarioSqlServer(result.getString(result.getColumnIndex("telefono")),
															result.getString(result.getColumnIndex("estado")),
															result.getString(result.getColumnIndex("foto")),
															result.getString(result.getColumnIndex("comentario")),
															result.getString(result.getColumnIndex("nombre")),
															result.getString(result.getColumnIndex("fecha"))));
				} while(result.moveToNext());
			}
		return listaContactos;
	}
	
	public ArrayList<Mensaje> obtenerMensajes() {
		
		ArrayList<Mensaje> listaMensajes = new ArrayList<Mensaje>();
		Cursor result = database.query(TABLA_MENSAJE,COLUMNAS_MENSAJE, null, null, null, null, null,null);
			if (result.moveToFirst()){
				do {
					listaMensajes.add(new Mensaje(result.getString(result.getColumnIndex("telf_emisor")),
															result.getString(result.getColumnIndex("telf_receptor")),
															result.getString(result.getColumnIndex("mensaje")),
															result.getString(result.getColumnIndex("tipo_mensaje"))));
				} while(result.moveToNext());
			}
		return listaMensajes;
	}
	
	public ArrayList<Mensaje> obtenerMensajesId(String telefono) {
		
		ArrayList<Mensaje> listaMensajes = new ArrayList<Mensaje>();
		Cursor result = database.query(TABLA_MENSAJE,COLUMNAS_MENSAJE, "telf_emisor='"+telefono+"' or telf_receptor='"+telefono+"'", null, null, null, null,null);
			if (result.moveToFirst()){
				do {
					listaMensajes.add(new Mensaje(result.getString(result.getColumnIndex("telf_emisor")),
															result.getString(result.getColumnIndex("telf_receptor")),
															result.getString(result.getColumnIndex("mensaje")),
															result.getString(result.getColumnIndex("tipo_mensaje"))));
				} while(result.moveToNext());
			}
		return listaMensajes;
	}
	
	public int contarMensajes() {
		
		ArrayList<Mensaje> listaMensajes = new ArrayList<Mensaje>();
		Cursor result = database.query(TABLA_MENSAJE,COLUMNAS_MENSAJE, null, null, null, null, null,null);
			if (result.moveToFirst()){
				do {
					listaMensajes.add(new Mensaje(result.getString(result.getColumnIndex("telf_emisor")),
															result.getString(result.getColumnIndex("telf_receptor")),
															result.getString(result.getColumnIndex("mensaje")),
															result.getString(result.getColumnIndex("tipo_mensaje"))));
				} while(result.moveToNext());
			}
		return listaMensajes.size();
	}
	////////////////////////////////////////Registrar////////////////////////////////////////////////////////
	public long registrarUsuario(String telefono, String foto) {
		ContentValues initialValues = contentValuesUsuario(telefono, foto);
 
		return database.insert(TABLA_USUARIO, null, initialValues);
	}
	
	private ContentValues contentValuesUsuario(String telefono, String foto) {
		
		ContentValues values = new ContentValues();
		
		values.put(COLUMNA_USUARIO_TELEFONO,telefono);
		values.put(COLUMNA_USUARIO_FOTO, foto);
		return values;
		
	}
	
	public long registrarContacto(String telefono, String estado, String foto, String comentario, String nombre, String fecha) {
		ContentValues initialValues = contentValuesContacto(telefono, estado, foto, comentario, nombre,fecha);
 
		return database.insert(TABLA_CONTACTO, null, initialValues);
	}
	
	private ContentValues contentValuesContacto(String telefono, String estado, String foto, String comentario, String nombre, String fecha) {
		
		ContentValues values = new ContentValues();
		
		values.put(COLUMNA_CONTACTO_TELEFONO,telefono);
		values.put(COLUMNA_CONTACTO_ESTADO, estado);
		values.put(COLUMNA_CONTACTO_FOTO, foto);
		values.put(COLUMNA_CONTACTO_COMENTARIO, comentario);
		values.put(COLUMNA_CONTACTO_NOMBRE, nombre);
		values.put(COLUMNA_CONTACTO_FECHA, fecha);
	
		return values;
		
	}
	
	public long registrarMensaje(String telf_emisor, String telf_receptor, String mensaje, String tipo_mensaje) {
		ContentValues initialValues = contentValuesMensaje(telf_emisor, telf_receptor, mensaje, tipo_mensaje);
 
		return database.insert(TABLA_MENSAJE, null, initialValues);
	}
	
	private ContentValues contentValuesMensaje(String telf_emisor, String telf_receptor, String mensaje, String tipo_mensaje) {
		
		ContentValues values = new ContentValues();
		
		values.put(COLUMNA_MENSAJE_TELF_EMISOR, telf_emisor);
		values.put(COLUMNA_MENSAJE_TELF_RECEPTOR, telf_receptor);
		values.put(COLUMNA_MENSAJE_MENSAJE, mensaje);
		values.put(COLUMNA_MENSAJE_TIPO_MENSAJE, tipo_mensaje);
	
		return values;
		
	}
}