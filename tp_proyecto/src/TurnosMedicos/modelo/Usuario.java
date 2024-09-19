package TurnosMedicos.modelo;

public class Usuario {
	private int id;

	private int contraseña;
	private String nombre;
	private int tipoUsuario;
	
	public Usuario(int id, int contraseña, String nombre, int tipoUsuario) {
		this.id = id;
	
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.tipoUsuario = tipoUsuario;
	}

	public Usuario( int contraseña, String nombre, int tipoUsuario) {
		
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.tipoUsuario = tipoUsuario;
	}
	
	public Usuario( int contraseña) {
		
		this.contraseña = contraseña;
	}
	
	public Usuario() {

	}

	public int getIdentificador() {
		return id;
	}

	public void setIdentificador(int id) {
		this.id = id;
	}
	

	public int getContraseña() {
		return contraseña;
	}

	public void setContraseña(int contraseña) {
		this.contraseña = contraseña;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(int tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
