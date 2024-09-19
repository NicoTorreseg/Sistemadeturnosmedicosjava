package TurnosMedicos.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import TurnosMedicos.modelo.Medico;
import TurnosMedicos.modelo.Usuario;

public class UsuarioDAO {

	public ArrayList<Usuario> listarUsuarios() {
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "SELECT * FROM usuarios";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int id = rs.getInt(1);
				int contraseña = rs.getInt(2);
				String nombre = rs.getString(3);
				int idTipoUsuario = rs.getInt(4);
				Usuario user = new Usuario(id, contraseña, nombre, idTipoUsuario);
				listaUsuarios.add(user);
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
	
	public boolean ContraseñaNorepetida(Usuario user) {
		boolean respuesta = false;
		ArrayList<Usuario> listaUsuarios = listarUsuarios();
		for (Usuario us : listaUsuarios) {
			int contraseña = us.getContraseña();
			if (user.getContraseña()==(contraseña)) {
				respuesta = true;
				System.out.println("ValidarContraseñaRepetida---La contraseña ya existe");
			}
		}
		return respuesta;
	}
	
	public boolean cargarUsuario(Usuario u) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		boolean x=true;
		

		if(ContraseñaNorepetida(u))
		{
			System.out.println("Usuariodao-Cargarusuario---La contraseña ya existe");
			x=false;
		}
		else{
			
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "INSERT INTO usuarios (contraseña_DNI, nombre_usuario, tipo_usuario) VALUES (?, ?, ?)";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
					
			pStmt.setInt(1, u.getContraseña());
			pStmt.setString(2, u.getNombre());
			pStmt.setInt(3, u.getTipoUsuario());

			pStmt.executeUpdate();
			
			if(u.getTipoUsuario()==2)
			{
				String nomMed=u.getNombre();
				int Dni_contraseña=u.getContraseña();
				Medico med=new Medico(nomMed,Dni_contraseña);
					
				MedicoDAO medDAO=new MedicoDAO();
				medDAO.cargarMedico(med);
					
						
				
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
		
		}
		return x;
	}
	
	public boolean validarUsuario(Usuario user) {
		boolean respuesta = false;
		ArrayList<Usuario> listaUsuarios = listarUsuarios();
		for (Usuario us : listaUsuarios) {
			int contraseña = us.getContraseña();
			if (user.getContraseña()==(contraseña)) {
				respuesta = true;
			}
		}
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
	
	public void eliminarUsuario(int id,int tipoUsuario,int dni) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
			java.sql.PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			pStmt.executeUpdate();
			
			if(tipoUsuario==2){
				MedicoDAO meddao=new MedicoDAO();
				
				meddao.eliminarMedicoPorDni(dni,"");
				
				
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
	}

	public boolean actualizarUsuario(Usuario u) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tp_proyecto_hospital";
		Connection conn = null;
	
		if(ContraseñaNorepetida(u))
		{
			System.out.println("Usuariodao-Cargarusuario---La contraseña ya existe");
			return false;
		}
		else{
			
			
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "root", "");
			String sql = "UPDATE usuarios SET contraseña_DNI = ?, nombre_usuario = ?, tipo_usuario = ? WHERE id_usuario = ?";
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
	
		return true;
	}
	
}
