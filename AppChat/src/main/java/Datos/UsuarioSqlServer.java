package Datos;

public class UsuarioSqlServer {
	private String telefono;
	private String estado;
	private String foto;
	private String fecha;
	
	private String nombre;
	private String comentario;
	
	public UsuarioSqlServer(String telefono, String estado, String foto, String fecha) {
		super();
		this.telefono = telefono;
		this.estado = estado;
		this.foto = foto;
		this.fecha = fecha;
	}
	
	public UsuarioSqlServer(String telefono, String estado, String foto, String fecha, String nombre) {
		super();
		this.telefono = telefono;
		this.estado = estado;
		this.foto = foto;
		this.fecha = fecha;
		this.nombre = nombre;
	}
	
	public UsuarioSqlServer(String telefono, String estado, String foto,
			 String comentario, String nombre, String fecha) {
		super();
		this.telefono = telefono;
		this.estado = estado;
		this.foto = foto;
		this.fecha = fecha;
		this.nombre = nombre;
		this.comentario = comentario;
	}

	public UsuarioSqlServer() {
		super();
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
}
