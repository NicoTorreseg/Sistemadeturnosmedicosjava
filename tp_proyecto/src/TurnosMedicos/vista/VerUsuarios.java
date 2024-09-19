package TurnosMedicos.vista;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import TurnosMedicos.DAO.MedicoDAO;
import TurnosMedicos.DAO.TurnosDAO;
import TurnosMedicos.DAO.UsuarioDAO;
import TurnosMedicos.modelo.Medico;
import TurnosMedicos.modelo.Usuario;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class VerUsuarios extends JPanel {
	private JTable table;
	private DefaultTableModel defaultTableModel;
	
	public VerUsuarios(int tipoUsuario,JFrame marco) {
		setLayout(null);
		
		JLabel lblClientes = new JLabel("Usuarios");
		lblClientes.setFont(new Font("Dialog", Font.BOLD, 16));
		lblClientes.setBounds(10, 11, 109, 14);
		add(lblClientes);
		
		JButton btnCargarNuevoUsuario = new JButton("Cargar nuevo usuario");
		btnCargarNuevoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnCargarNuevoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				marco.setContentPane(new AltaUsuario(tipoUsuario,marco));
				marco.validate();
			}
		});
		btnCargarNuevoUsuario.setBounds(43, 36, 145, 38);
		add(btnCargarNuevoUsuario);
		
		JButton btnEliminar = new JButton("Eliminar seleccionado");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					UsuarioDAO userDAO = new UsuarioDAO();
					int fila = table.getSelectedRow();
					int id = ((Integer) table.getValueAt(fila, 0)).intValue();
					String nombre=table.getValueAt(fila, 2).toString();
					String tipoUsuario=table.getValueAt(fila, 3).toString();
					int tipo_usuarioint=0;
					int dni=0;
				
					
					
					int confirmacion;
					if(tipoUsuario=="Medico")
					{
						
						JOptionPane.showConfirmDialog(null, "Si el medico seleccionado posee turnos, se eliminaran", "Aviso", JOptionPane.CLOSED_OPTION);
						
						
					}
					
					confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este usuario?", "Alerta", JOptionPane.YES_NO_OPTION);
					if (confirmacion == JOptionPane.YES_OPTION) {
						userDAO.eliminarUsuario(id,tipo_usuarioint,dni);
						
						if(tipoUsuario=="Medico")
						{
							tipo_usuarioint=2;
							dni =Integer.parseInt(table.getValueAt(fila, 1).toString());
							System.out.println("el dni del med fue"+dni);
							
							
							MedicoDAO medao=new MedicoDAO();
							medao.eliminarMedicoPorDni(dni,nombre);
							
						}
						
						JOptionPane.showMessageDialog(null, "El usuario ha sido eliminado exitosamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
						
						
						
						cargarTabla(userDAO.listarUsuarios());
					}
					else if (confirmacion== JOptionPane.NO_OPTION) {
						//	confirmacion = JOptionPane.showConfirmDialog(null, "No se elimino ningun usuario", "Alerta", JOptionPane.CLOSED_OPTION);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "No hay ningun usuario seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnEliminar.setBounds(242, 36, 138, 38);
		btnEliminar.setEnabled(false);
		add(btnEliminar);
		
		JButton btnModificar = new JButton("Modificar seleccionado");
		btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
	        	try	{
	        		int fila = table.getSelectedRow();
					int id = ((Integer) table.getValueAt(fila, 0)).intValue();
					String nombre = table.getValueAt(fila, 1).toString();
					String contraseña = table.getValueAt(fila, 2).toString();
					String TipoUsuarioSelect=table.getValueAt(fila, 3).toString();;
					
					JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) ev.getSource());
					marco.setContentPane(new ModificarUsuario(id, nombre, contraseña,TipoUsuarioSelect));
					marco.validate();
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "No hay ningun usuario seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModificar.setBounds(438, 36, 152, 38);
		btnModificar.setEnabled(false);
		add(btnModificar);
		
		JButton btnMenuPrincipal = new JButton("Volver al Menú");
		btnMenuPrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				marco.setContentPane(new Menu(tipoUsuario,marco,0));
				marco.validate();
			}
		});
		btnMenuPrincipal.setBounds(509, 405, 120, 23);
		add(btnMenuPrincipal);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 83, 619, 309);
		add(scrollPane);
				
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				btnModificar.setEnabled(true);
				btnEliminar.setEnabled(true);
				int fila = table.getSelectedRow();
				if (fila == 0) {
					btnModificar.setEnabled(false);
					btnEliminar.setEnabled(false);
				}
			}
		});
		
		defaultTableModel = new DefaultTableModel(new String[] {"ID", "Contraseña", "Nombre", "Tipo de usuario"}, 0);
		
		UsuarioDAO userDAO = new UsuarioDAO();
		ArrayList<Usuario> listaUsuarios = userDAO.listarUsuarios();
		cargarTabla(listaUsuarios);
		
		table.setModel(defaultTableModel);
		scrollPane.setViewportView(table);
	}

	private void cargarTabla(ArrayList<Usuario> listaUsuarios) {
		defaultTableModel.setRowCount(0);
		for (Usuario u : listaUsuarios) {
			String TipoUsuario="";
			if (u.getTipoUsuario()==1)
			{
				TipoUsuario="Supervisor";
				
			}
			else{
				TipoUsuario="Medico";
			}
					
			
			
		//	TipoUsuarioDAO tipoDAO = new TipoUsuarioDAO();
	//		TipoUsuario cargoUsuario = new TipoUsuario();
		//	cargoUsuario = tipoDAO.obtenerNombreSegunID(idTipoUsuario);
			
			defaultTableModel.addRow(new Object[] {u.getIdentificador(), u.getContraseña(),u.getNombre(),TipoUsuario});
			
			
		}
	}
}
