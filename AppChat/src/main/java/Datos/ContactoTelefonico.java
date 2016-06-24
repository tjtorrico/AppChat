package Datos;

public class ContactoTelefonico {
	private String nombre;
	private String telefono;
	
	public ContactoTelefonico(){
		
	}

	public ContactoTelefonico(String nombre, String telefono) {
		super();
		this.nombre = nombre;
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}
