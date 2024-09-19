package TurnosMedicos.vista;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.xml.soap.Text;

import TurnosMedicos.DAO.UsuarioDAO;
import TurnosMedicos.modelo.Usuario;

import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class InicioSesion extends JPanel {
	private JTextField textpassContraseña;

	public InicioSesion(JFrame marco) {
		setLayout(null);

		JLabel lblSistemaClinica = new JLabel("Sistema informatico \u00A9OSDE ");
		lblSistemaClinica.setFont(new Font("Dialog", Font.BOLD, 17));
		lblSistemaClinica.setBounds(10, 11, 234, 14);
		add(lblSistemaClinica);
		
		JLabel lblInicio = new JLabel("Por favor ingrese con su cuenta");
		lblInicio.setHorizontalAlignment(SwingConstants.CENTER);
		lblInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInicio.setBounds(56, 78, 223, 22);
		add(lblInicio);
		
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContraseña.setBounds(56, 129, 99, 22);
		add(lblContraseña);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean noesNumero;
				int contraseña;
			System.out.println("errror");
			try{
				contraseña = Integer.parseInt(textpassContraseña.getText());
			noesNumero=true;
			}
			catch(Exception a){
				JOptionPane.showMessageDialog(null, "Inicio de sesión incorrecto, por favor intentelo nuevamente.\nSi olvidó su contraseña pida su recuperación a un administrador.", "ERROR", JOptionPane.WARNING_MESSAGE);
				noesNumero=false;
				contraseña=0;
			}
			if(noesNumero&&textpassContraseña.getText().length()==8)
			{
				
			
				Usuario usuarioConContraseña = new Usuario(contraseña);//lepone la contraseña ingresada
				System.out.println("errror2");
				
				UsuarioDAO userDAO = new UsuarioDAO();
				boolean validacion = userDAO.validarUsuario(usuarioConContraseña);
				
				if (validacion == true) {
					System.out.println("errror3");
					int tipoUsuario = userDAO.obtenerTipoDeUsuario(contraseña);
					
					System.out.println("tipo de usuario "+tipoUsuario);
		            JOptionPane.showMessageDialog(null, "Inicio de sesión correcto.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
					JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
					
					marco.setContentPane(new Menu(tipoUsuario,marco,contraseña));
					marco.validate();
				} else {
		            JOptionPane.showMessageDialog(null, "Inicio de sesión incorrecto, por favor intentelo nuevamente.\n El DNI ingresado no existe, Pida ayuda a un administrador.", "ERROR", JOptionPane.WARNING_MESSAGE);
				}
			}
			else{
				  JOptionPane.showMessageDialog(null, "Inicio de sesión incorrecto, por favor ingrese un DNI valido", "ERROR", JOptionPane.WARNING_MESSAGE);
			}
			
			
			}
		});
		btnIngresar.setBounds(91, 189, 162, 23);
		add(btnIngresar);
		
		textpassContraseña = new JTextField();
		textpassContraseña.setBounds(176, 129, 120, 23);
		add(textpassContraseña);
		textpassContraseña.setColumns(10);
	}
}