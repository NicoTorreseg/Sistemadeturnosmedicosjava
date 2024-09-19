package TurnosMedicos.modelo;

import java.time.LocalDate;

public class Turno {

	
	private int id;
	private LocalDate fecha;
	private int medicoid;
	private String hora;
	private String nombrePaciente;

	public Turno(int id, LocalDate fecha, String hora, int medicoid, String nombrePaciente) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.fecha=fecha;
		this.hora=hora;
		this.medicoid=medicoid;
		this.nombrePaciente=nombrePaciente;
	}

	

	public Turno(LocalDate fechaTurno, String horaTurno, String nombrePaciente2, int Medid) {
		// TODO Auto-generated constructor stub
		this.fecha=fechaTurno;
		this.hora=horaTurno;
		this.nombrePaciente=nombrePaciente2;
		this.medicoid=Medid;
	}

	public Turno(int id2, LocalDate fechaTurno, String horaTurno, String nombrePaciente2, int idMed) {
		// TODO Auto-generated constructor stub
		
		this.id=id2;
		this.fecha=fechaTurno;
		this.hora=horaTurno;
		this.nombrePaciente=nombrePaciente2;
		this.medicoid=idMed;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getNombrePaciente() {
		return nombrePaciente;
	}

	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getMedicoid() {
		return medicoid;
	}

	public void setMedicoid(int medicoid) {
		this.medicoid = medicoid;
	}

}
