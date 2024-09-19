package TurnosMedicos.modelo;

public class Medico {
	int id;
	String nombre;
	int dnimedico;
	String ContraseñaAnt;
	public String getContraseñaAnt() {
		return ContraseñaAnt;
	}
	public void setContraseñaAnt(String contraseñaAnt) {
		ContraseñaAnt = contraseñaAnt;
	}
	public Medico(String nomMed, int dni_contraseña) {
		// TODO Auto-generated constructor stub
		this.nombre=nomMed;
		this.dnimedico=dni_contraseña;
	}
	public Medico(int dni) {
		// TODO Auto-generated constructor stub
		this.dnimedico=dni;
	}
	public Medico(int id2, String nombre2, int dni) {
		// TODO Auto-generated constructor stub
		this.id=id2;
		this.nombre=nombre2;
		this.dnimedico=dni;
		
	}
	public Medico() {
		// TODO Auto-generated constructor stub
	}
	public Medico(int id2, String nombre2, int contraseña, String contraseñaAntigua) {
		// TODO Auto-generated constructor stub
		this.nombre=nombre2;
		this.dnimedico=contraseña;
		this.ContraseñaAnt=contraseñaAntigua;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getDnimedico() {
		return dnimedico;
	}
	public void setDnimedico(int dnimedico) {
		this.dnimedico = dnimedico;
	}
	

}
