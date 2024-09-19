package TurnosMedicos.modelo;

public class Usuario {
	private int id;

	private int contrase�a;
	private String nombre;
	private int tipoUsuario;
	
	public Usuario(int id, int contrase�a, String nombre, int tipoUsuario) {
		this.id = id;
	
		this.contrase�a = contrase�a;
		this.nombre = nombre;
		this.tipoUsuario = tipoUsuario;
	}

	public Usuario( int contrase�a, String nombre, int tipoUsuario) {
		
		this.contrase�a = contrase�a;
		this.nombre = nombre;
		this.tipoUsuario = tipoUsuario;
	}
	
	public Usuario( int contrase�a) {
		
		this.contrase�a = contrase�a;
	}
	
	public Usuario() {

	}

	public int getIdentificador() {
		return id;
	}

	public void setIdentificador(int id) {
		this.id = id;
	}
	

	public int getContrase�a() {
		return contrase�a;
	}

	public void setContrase�a(int contrase�a) {
		this.contrase�a = contrase�a;
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
