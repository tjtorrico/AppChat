package Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	
	private static final String DATABASE_NAME = "usuario.db";
	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_TABLA_USUARIO = "CREATE  TABLE usuario "
			+ "(telefono TEXT PRIMARY KEY NOT NULL , "
			+ "foto TEXT NOT NULL)";
	
	private static final String CREATE_TABLA_CONTACTOS = "CREATE  TABLE contacto "
			+ "(telefono TEXT PRIMARY KEY NOT NULL , "
			+ "estado TEXT NOT NULL ,"
			+ "foto TEXT NOT NULL ,"
			+ "comentario TEXT NOT NULL ,"
			+ "nombre TEXT NOT NULL ,"
			+ "fecha TEXT NOT NULL )";
	
	private static final String CREATE_TABLA_MENSAJE = "CREATE  TABLE mensaje "
			+ "(telf_emisor TEXT NOT NULL , "
			+ "telf_receptor TEXT NOT NULL ,"
			+ "mensaje TEXT NOT NULL ,"
			+ "tipo_mensaje TEXT NOT NULL)";

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_TABLA_USUARIO);
		db.execSQL(CREATE_TABLA_CONTACTOS);
		db.execSQL(CREATE_TABLA_MENSAJE);
		
	}

//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//		Log.w(SQLiteHelper.class.getSimpleName(),
//				"Actualizando base de datos de la version " + oldVersion
//						+ " a la " + newVersion
//						+ ", que eliminara todos los datos");
//		db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIO);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLA_CLIENTE);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLA_ORDEN_COMPRA);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLA_DETALLE_ORDEN_COMPRA);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLA_PRODUCTO);
//		onCreate(db);
//
//	}
 
	// Metodo que se llama cada vez que se actualiza la BD
	// Sirve para manejar las versiones de la misma
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(DataBaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS usuario");
		database.execSQL("DROP TABLE IF EXISTS contacto");
		database.execSQL("DROP TABLE IF EXISTS mensaje");
		onCreate(database);
	}
}