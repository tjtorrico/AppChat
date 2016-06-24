package Datos;

public class Mensaje {
	private int _id;
	
	private String telfEmisor;
	private String telfReceptor;
	private String mensaje;
	private String tipoMensaje;
	private String estado;
	
	public Mensaje(int _id, String telfEmisor, String telfReceptor,
			String mensaje, String tipoMensaje) {
		super();
		this._id = _id;
		this.telfEmisor = telfEmisor;
		this.telfReceptor = telfReceptor;
		this.mensaje = mensaje;
		this.tipoMensaje = tipoMensaje;
	}

	public Mensaje(String telfEmisor, String telfReceptor, String mensaje,
			String tipoMensaje) {
		super();
		this.telfEmisor = telfEmisor;
		this.telfReceptor = telfReceptor;
		this.mensaje = mensaje;
		this.tipoMensaje = tipoMensaje;
	}
	
	public Mensaje(String telfEmisor, String telfReceptor,
			String mensaje, String tipoMensaje, String estado) {
		super();
		this._id = _id;
		this.telfEmisor = telfEmisor;
		this.telfReceptor = telfReceptor;
		this.mensaje = mensaje;
		this.tipoMensaje = tipoMensaje;
		this.estado = estado;
	}

	public Mensaje(){
		
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getTelfEmisor() {
		return telfEmisor;
	}

	public void setTelfEmisor(String telfEmisor) {
		this.telfEmisor = telfEmisor;
	}

	public String getTelfReceptor() {
		return telfReceptor;
	}

	public void setTelfReceptor(String telfReceptor) {
		this.telfReceptor = telfReceptor;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
