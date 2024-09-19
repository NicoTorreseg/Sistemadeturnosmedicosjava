package TurnosMedicos.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import TurnosMedicos.modelo.Turno;
import TurnosMedicos.modelo.Usuario;

public class TurnosDAO {

	public ArrayList<Turno> ListarTurnos() {
		ArrayList<Turno> ListaTurnos = new ArrayList<Turno>();
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "SELECT * FROM turnos";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int id = rs.getInt(1);
				LocalDate fecha = LocalDate.parse(rs.getString(2));
				String hora = rs.getTime(3).toString();
				hora=hora.substring(0, hora.length()-3);//sacarle los segundos
				System.out.println("turnos dao---la hora fue de"+hora);
				int Medicoid = rs.getInt(4);
				String NombrePaciente=rs.getString(5);
				Turno turn = new Turno(id, fecha, hora, Medicoid,NombrePaciente);
				ListaTurnos.add(turn);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ListaTurnos;
	}
	public boolean TurnoRepetido(Turno turn) {
		boolean respuesta = false;
		ArrayList<Turno> turnos = ListarTurnos();
		//campos del turno ingresado
		String Fechaacomparar=turn.getFecha().toString();
		String HoraaComparar=turn.getHora();
		String medicoidacomparar=Integer.toString(turn.getMedicoid());
		String acomparar=Fechaacomparar+HoraaComparar+medicoidacomparar;
		System.out.println("---VALIDAR TURNO-----");
		System.out.println("---Turno ingresado="+Fechaacomparar+HoraaComparar+medicoidacomparar);
		for (Turno t : turnos) {
			String fecha=t.getFecha().toString();
			String hora=t.getHora();
			String medicoid=Integer.toString(t.getMedicoid());
			String delabd=fecha+hora+medicoid;
			System.out.println("--- turno en la bd= "+fecha+hora+medicoid);
			
			if (acomparar.equals(delabd)) 
			{
				respuesta = true;
			}
		}
		return respuesta;
		//false si no hubo ningun error
		//true si hubo algun error
	}

	public boolean cargarTurno(Turno t) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		if(TurnoRepetido(t))
		{
			System.out.println("Usuariodao-Cargarusuario---El turo ya fue asignado");
	return false;
		}
		else{
			

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "INSERT INTO turnos (fecha_turno,hora_turno,medico_id,nombre_paciente) VALUES (?, ?, ?, ?)";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
					
			pStmt.setDate(1,java.sql.Date.valueOf(t.getFecha()));
			pStmt.setString(2,t.getHora());
			pStmt.setInt(3, t.getMedicoid());
			pStmt.setString(4, t.getNombrePaciente());

			pStmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		}
		return true;
	}
	
	public boolean validarUsuario(Usuario user) {
		boolean respuesta = false;
	//	ArrayList<Usuario> listaUsuarios = listarUsuarios();
	//	for (Usuario us : listaUsuarios) {
	//		int contraseña = us.getContraseña();
		//	if (user.getContraseña()==(contraseña)) {
				respuesta = true;
	//		}
	//	}
		return respuesta;
	}

	public ArrayList<Usuario> buscarUsuariosPorNombre(String busqueda) {
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "SELECT * FROM usuarios WHERE nombre LIKE ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, "%" + busqueda + "%");
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				int contraseña = rs.getInt(2);
				String nombre = rs.getString(3);
				int idTipoUsuario = rs.getInt(4);
				Usuario usuario = new Usuario(id,contraseña, nombre, idTipoUsuario);
				listaUsuarios.add(usuario);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaUsuarios;
	}

	public String obtenerNombreUsuario(String username) {
		String nombre = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "SELECT nombre FROM usuarios WHERE username = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, username);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				nombre = rs.getString(1);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nombre;
	}

	public int obtenerTipoDeUsuario(int i) {
		int tipo = 0;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "SELECT tipo_usuario FROM usuarios WHERE contraseña_DNI = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, i);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				tipo = rs.getInt(1);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tipo;
	}
	
	public void eliminarTurno(int id) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "DELETE FROM turnos WHERE id_turno = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			pStmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean actualizarTurno(Turno u) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		if(TurnoRepetido(u))
		{
			System.out.println("Usuariodao-Cargarusuario---El turno ya fue asignado");
	return false;
		}
		else{
			
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "UPDATE turnos SET fecha_turno = ?, hora_turno = ?, medico_id = ?, nombre_paciente = ? WHERE id_turno = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
		
			pStmt.setDate(1,java.sql.Date.valueOf(u.getFecha()));	
			pStmt.setString(2, u.getHora());
			pStmt.setInt(3, u.getMedicoid());
			pStmt.setString(4, u.getNombrePaciente());
			pStmt.setInt(5, u.getId());
			pStmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}}
		
		return true;
	}

	public ArrayList<Turno> ListarTurnosDeMedico(int idmed) {
		ArrayList<Turno> ListaTurnos = new ArrayList<Turno>();
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "SELECT * FROM turnos WHERE medico_id = ? ";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, idmed);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				LocalDate fecha = LocalDate.parse(rs.getString(2));
				String hora = rs.getTime(3).toString();
				hora=hora.substring(0, hora.length()-3);//sacarle los segundos
				System.out.println("turnos dao---la hora fue de"+hora);
				int Medicoid = rs.getInt(4);
				String NombrePaciente=rs.getString(5);
				Turno turn = new Turno(id, fecha, hora, Medicoid,NombrePaciente);
				ListaTurnos.add(turn);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ListaTurnos;
	}

	public void eliminarTurnosPorMedid(int id) {
		// TODO Auto-generated method stub
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "DELETE FROM turnos WHERE medico_id = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			pStmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	public ArrayList<Turno> ListarTurnosDeMedicoPorNombre(String stringselected) {
		ArrayList<Turno> ListaTurnos = new ArrayList<Turno>();
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "SELECT * FROM turnos INNER JOIN medicos ON turnos.medico_id = medicos.id_medico WHERE medicos.nombre_medico = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, stringselected);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				LocalDate fecha = LocalDate.parse(rs.getString(2));
				String hora = rs.getTime(3).toString();
				hora=hora.substring(0, hora.length()-3);//sacarle los segundos
				System.out.println("turnos dao---la hora fue de"+hora);
				int Medicoid = rs.getInt(4);
				String NombrePaciente=rs.getString(5);
				Turno turn = new Turno(id, fecha, hora, Medicoid,NombrePaciente);
				ListaTurnos.add(turn);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ListaTurnos;
	}
	public void eliminarTurnosPorNombremed(String Nombremed) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "DELETE turnos FROM turnos INNER JOIN medicos ON turnos.medico_id = medicos.id_medico WHERE medicos.nombre_medico = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, Nombremed);
			pStmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		// TODO Auto-generated method stub
		
	}
	
}
