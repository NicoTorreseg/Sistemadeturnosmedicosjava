package TurnosMedicos.modelo;

public class Medico {
	int id;
	String nombre;
	int dnimedico;
	String Contrase�aAnt;
	public String getContrase�aAnt() {
		return Contrase�aAnt;
	}
	public void setContrase�aAnt(String contrase�aAnt) {
		Contrase�aAnt = contrase�aAnt;
	}
	public Medico(String nomMed, int dni_contrase�a) {
		// TODO Auto-generated constructor stub
		this.nombre=nomMed;
		this.dnimedico=dni_contrase�a;
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
	public Medico(int id2, String nombre2, int contrase�a, String contrase�aAntigua) {
		// TODO Auto-generated constructor stub
		this.nombre=nombre2;
		this.dnimedico=contrase�a;
		this.Contrase�aAnt=contrase�aAntigua;
		
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
