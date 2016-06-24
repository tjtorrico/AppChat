package Datos;

public class UsuarioSqlite {
	private String telefono;
	private String foto;
	
	public UsuarioSqlite() {
	}

	public UsuarioSqlite(String telefono, String foto) {
		super();
		this.telefono = telefono;
		this.foto = foto;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
}
