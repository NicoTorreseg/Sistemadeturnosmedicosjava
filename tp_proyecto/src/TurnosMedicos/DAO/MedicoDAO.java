package TurnosMedicos.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TurnosMedicos.modelo.Medico;
import TurnosMedicos.modelo.Usuario;

public class MedicoDAO {

	public ArrayList<Medico> listarMedicos() {
		ArrayList<Medico> listaMedicos = new ArrayList<Medico>();
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "SELECT * FROM Medicos";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int id = rs.getInt(1);
				
				String nombre = rs.getString(2);
				int Dni = rs.getInt(3);
				Medico Med = new Medico(id,nombre, Dni);
				listaMedicos.add(Med);
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
		return listaMedicos;
	}
	
	public void cargarMedico(Medico med) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "INSERT INTO medicos (nombre_medico , dni_medico) VALUES ( ?, ?)";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
					
			pStmt.setString(1, med.getNombre());
			pStmt.setInt(2, med.getDnimedico());
			

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
	
	public boolean validarUsuario(Usuario user) {
		boolean respuesta = false;
	///	ArrayList<Usuario> listaUsuarios = listarUsuarios();
//		for (Usuario us : listaUsuarios) {
	//		int contraseña = us.getContraseña();
	//		if (user.getContraseña()==(contraseña)) {
				respuesta = true;
		//	}
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
	
	public void eliminarMedicoPorDni(int dni, String Nombremed) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			TurnosDAO turndao=new TurnosDAO();
			turndao.eliminarTurnosPorNombremed(Nombremed);
			
			
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "DELETE FROM Medicos WHERE dni_medico = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, dni);
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

	public void actualizarUsuario(Usuario u) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "UPDATE usuarios SET contraseña = ?, nombre = ?, id_tipo = ? WHERE id_usuario = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
		
			pStmt.setInt(1, u.getContraseña());
			pStmt.setString(2, u.getNombre());
			pStmt.setInt(3, u.getTipoUsuario());
			pStmt.setInt(4, u.getIdentificador());
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

	public String BuscarMedPoridComboBox(int id) {
		// TODO Auto-generated method stub
		String nombremed="";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "SELECT nombre_medico FROM Medicos WHERE id_medico = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				nombremed = rs.getString(1);
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
		return nombremed;
		
	}
	
	public Medico BuscarMedPordni(int Dni) {
		// TODO Auto-generated method stub
	
		Medico med=new Medico();
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "SELECT id_medico, nombre_medico FROM medicos WHERE dni_medico = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, Dni);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				int idmed = rs.getInt(1);
				String nombremed = rs.getString(2);
				
				
				Medico med1=new Medico(idmed,nombremed,Dni);
				med=med1;
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
		return med;
		
	}

	public void actualizarMedico(Medico mednuevo) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		MedicoDAO medao=new MedicoDAO();
		System.out.println(mednuevo.getContraseñaAnt());
	Medico ant=	medao.BuscarMedPordni(Integer.parseInt(mednuevo.getContraseñaAnt()));

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			
			
			String sql = "UPDATE Medicos SET nombre_medico = ?, dni_medico = ? WHERE id_medico = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
		
		
			pStmt.setString(1, mednuevo.getNombre());
			pStmt.setInt(2, mednuevo.getDnimedico());
			pStmt.setInt(3, ant.getId());
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
	
	
}
