package TurnosMedicos.vista;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import TurnosMedicos.DAO.MedicoDAO;
import TurnosMedicos.DAO.UsuarioDAO;
import TurnosMedicos.modelo.Medico;
import TurnosMedicos.modelo.Usuario;

@SuppressWarnings("serial")
public class ModificarUsuario extends JPanel {
	private JTextField txtNombre;
	private JTextField txtContrase�a;
	int tipoUsuario=1;
	int tipoSeleccionado;
	public ModificarUsuario( int id,String contrase�aAntigua  , String nombre,String tipoSelect) {
		setLayout(null);
			
		JLabel lblModificarUsuario = new JLabel("Modificar Usuario");
		lblModificarUsuario.setFont(new Font("Dialog", Font.BOLD, 16));
		lblModificarUsuario.setBounds(10, 11, 150, 21);
		add(lblModificarUsuario);
			
		JLabel lblUsername = new JLabel(" Nombre:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(56, 94, 89, 14);
		add(lblUsername);
		
		txtNombre = new JTextField(nombre);
		txtNombre.setBounds(155, 93, 198, 20);
		add(txtNombre);
		txtNombre.setColumns(10);
	
		JLabel lblContrase�a = new JLabel("Contrase\u00F1a(Dni)");
		lblContrase�a.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContrase�a.setBounds(34, 132, 111, 21);
		add(lblContrase�a);
		
		txtContrase�a = new JTextField(contrase�aAntigua);
		txtContrase�a.setBounds(155, 134, 198, 20);
		add(txtContrase�a);
		txtContrase�a.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmacion;
				String nombre = txtNombre.getText();
				
				
				
		        if (nombre.isEmpty()) {
		        	
		        	
			        JOptionPane.showMessageDialog(null, "Por favor rellene todos los campos.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else {
					try{
						Integer.parseInt(txtContrase�a.getText());
					
						
					
					confirmacion = JOptionPane.showConfirmDialog(null, "�Est� seguro?", "Alerta", JOptionPane.YES_NO_OPTION);
					int contrase�a = Integer.parseInt(txtContrase�a.getText());
					
					if(txtContrase�a.getText().length()==8&&contrase�a>0)
					{
						System.out.println("la contrase�a ingresada tiene 8 dig");
					if (confirmacion == JOptionPane.YES_OPTION) {
						
						int tip=0;
						if(tipoSelect=="Supervisor"){
							tip=1;
						}
						if(tipoSelect=="Medico")
						{
							tip=2;
						}
						Usuario user = new Usuario(id, contrase�a, nombre,tip );
						UsuarioDAO userDAO = new UsuarioDAO();
						boolean cartel=userDAO.actualizarUsuario(user);
						if(cartel)
						{
							
						
			            JOptionPane.showMessageDialog(null, "Los datos del usuario han sido modificado exitosamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
			            if(tipoSelect=="Medico"){
				   			
				   			MedicoDAO medao=new MedicoDAO();
				   			Medico mednuevo=new Medico(id,nombre,contrase�a,contrase�aAntigua);
				   			medao.actualizarMedico(mednuevo);
				   		}
				   		JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				   		marco.setContentPane(new VerUsuarios(1,marco));
				   		marco.validate();
				   		
				   		
						}
						else{
							JOptionPane.showMessageDialog(null, "El usuario ingresado ya existe. \nPor favor ingrese los datos nuevamente ", "Aviso", JOptionPane.ERROR_MESSAGE);
							JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
							
							
							marco.setContentPane(new ModificarUsuario(id,contrase�aAntigua,nombre,tipoSelect));
					   		marco.validate();
						}
					
			       	}
					}else{
						JOptionPane.showMessageDialog(null, "Por favor un dni valido", "Aviso", JOptionPane.ERROR_MESSAGE);
					}
						
					}catch(Exception x){
			       		JOptionPane.showMessageDialog(null, "Por favor ingrese los datos correctamente", "Aviso", JOptionPane.ERROR_MESSAGE);
			       	}
					
					
				}
			}
		});
		btnGuardar.setBounds(56, 252, 89, 23);
		add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				
				marco.setContentPane(new ModificarUsuario( id,  contrase�aAntigua,nombre,tipoSelect));
				marco.validate();
			}
		});
		btnCancelar.setBounds(170, 252, 89, 23);
		add(btnCancelar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				marco.setContentPane(new VerUsuarios(1,marco));
				marco.validate();
			}
		});
		btnSalir.setBounds(269, 252, 81, 23);
		add(btnSalir);
		
	}
}
