package com.example.appchat;

import Datos.UsuarioSqlServer;
import Datos.UsuarioSqlite;

public class Comunicador {
	private static UsuarioSqlServer usuario;
	
	public Comunicador(){
	}

	public static UsuarioSqlServer getUsuario() {
		return usuario;
	}

	public static void setUsuario(UsuarioSqlServer u) {
		usuario = u;
	}
	
}
