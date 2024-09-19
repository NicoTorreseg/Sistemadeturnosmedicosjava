package TurnosMedicos.vista;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.privatejgoodies.common.base.SystemUtils;

import TurnosMedicos.DAO.UsuarioDAO;

import TurnosMedicos.modelo.Usuario;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class AltaUsuario extends JPanel {
	private JTextField txtNombre_usuario;
	private JTextField txtContrase�a;

	public AltaUsuario(int tipoUsuario,JFrame marco) {
		setLayout(null);
		
		JLabel lblCargarUsuario = new JLabel("Cargar Usuario");
		lblCargarUsuario.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCargarUsuario.setBounds(10, 11, 127, 21);
		add(lblCargarUsuario);
		
		JLabel lblUsername = new JLabel("Nombre");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(26, 94, 89, 14);
		add(lblUsername);
		
		txtNombre_usuario = new JTextField();
		txtNombre_usuario.setBounds(144, 93, 198, 20);
		add(txtNombre_usuario);
		txtNombre_usuario.setColumns(10);
		
		JLabel lblContrase�a = new JLabel("Contrase\u00F1a(DNI):");
		lblContrase�a.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContrase�a.setBounds(26, 132, 108, 21);
		add(lblContrase�a);
		
		txtContrase�a = new JTextField();
		txtContrase�a.setBounds(144, 134, 198, 20);
		add(txtContrase�a);
		txtContrase�a.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Cargo:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 170, 127, 21);
		add(lblNewLabel);
		
	//	TipoUsuarioDAO daoTipo = new TipoUsuarioDAO();
//		ArrayList<TipoUsuario> tipos = new ArrayList<TipoUsuario>();
	//	tipos = daoTipo.listarLosTipos();
		JComboBox<String> boxTipoUsuario = new JComboBox<String>();
//		for (TipoUsuario t : tipos) {
			boxTipoUsuario.addItem("Supervisor");
			boxTipoUsuario.addItem("Medico");
	//	}
		boxTipoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		boxTipoUsuario.setBounds(144, 170, 198, 20);
		add(boxTipoUsuario);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmacion;
				
				
				String contrase�a = txtContrase�a.getText();
				String nombre = txtNombre_usuario.getText();
				int tipoSeleccionado = boxTipoUsuario.getSelectedIndex()+1;
				
				
				
				
						
				
			     if ( contrase�a.isEmpty()|| nombre.isEmpty()) {
				        JOptionPane.showMessageDialog(null, "Por favor rellene todos los campos.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
					else {
						
					
				
				try{
					Integer.parseInt(txtContrase�a.getText());
				
				
		   
					
					
					
					confirmacion = JOptionPane.showConfirmDialog(null, "�Est� seguro?", "Alerta", JOptionPane.YES_NO_OPTION);
					
					int dniF=Integer.parseInt(txtContrase�a.getText());
					if(contrase�a.length()==8&&dniF>0)
					{
						System.out.println("la contrase�a ingresada tiene 8 dig");
					
					if (confirmacion == JOptionPane.YES_OPTION) {
						
					//	try{
							
						
						int contrase�anumero=Integer.parseInt(txtContrase�a.getText());
						
						
						System.out.println("----altas"+tipoSeleccionado);
						Usuario user = new Usuario(contrase�anumero, nombre, tipoSeleccionado);
						UsuarioDAO userDAO = new UsuarioDAO();
						boolean cartel=userDAO.cargarUsuario(user);
						if(cartel)
						{
			            JOptionPane.showMessageDialog(null, "El usuario ha sido registrado exitosamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
			            marco.setContentPane(new VerUsuarios(tipoUsuario,marco));
				   		marco.validate();
						}
						else{
							 JOptionPane.showMessageDialog(null, "El usuario ingresado ya existe. \nPor favor ingrese los datos nuevamente ", "Aviso", JOptionPane.ERROR_MESSAGE);
							marco.setContentPane(new AltaUsuario(tipoUsuario,marco));
					   		marco.validate();
						}
					//	}
					//	catch(Exception a){
						//	JOptionPane.showMessageDialog(null, "Ingrese numeros en el dni down de mierda ", "Aviso", JOptionPane.ERROR_MESSAGE);
					//		marco.setContentPane(new AltaUsuario(tipoUsuario,marco));
					  // 		marco.validate();
					//	}
				   		
			       	}
					
					}
					else{
						JOptionPane.showMessageDialog(null, "Dni ingresado invalido", "Aviso", JOptionPane.ERROR_MESSAGE);
						marco.setContentPane(new AltaUsuario(tipoUsuario,marco));
				   		marco.validate();
						
					}
				
				}catch(Exception k){
					
					JOptionPane.showMessageDialog(null, "Ingrese un dni valido ", "Aviso", JOptionPane.ERROR_MESSAGE);
						marco.setContentPane(new AltaUsuario(tipoUsuario,marco));
					  		marco.validate();
					
				}
					}
					
			}
				
			
		});
		btnGuardar.setBounds(48, 252, 89, 23);
		add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				marco.setContentPane(new AltaUsuario(tipoUsuario,marco));
				marco.validate();
			}
		});
		btnCancelar.setBounds(154, 252, 89, 23);
		add(btnCancelar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				marco.setContentPane(new VerUsuarios(tipoUsuario,marco));
				marco.validate();
			}
		});
		btnSalir.setBounds(261, 252, 81, 23);
		add(btnSalir);
		
	}
}