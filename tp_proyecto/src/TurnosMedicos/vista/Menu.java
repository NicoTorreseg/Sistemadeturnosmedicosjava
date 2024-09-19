package TurnosMedicos.vista;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import TurnosMedicos.DAO.MedicoDAO;
import TurnosMedicos.modelo.Medico;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class Menu extends JPanel {

	public Menu(int tipoUsuario,JFrame marco,int contraseña) {
		setLayout(null);
		marco.setBounds(100, 100, 650, 476);
		
String TipoUsuariostring="";
if (tipoUsuario==1)
{
	TipoUsuariostring="SUPERVISOR";
	
}
else{
	TipoUsuariostring="MEDICO";
	
	
	//contraseña=dni
	
}
		//1=SUPERVISOR
		//2=MEDICO
	
		
		
		JLabel lblSistemaMusimundo = new JLabel(TipoUsuariostring );
		lblSistemaMusimundo.setFont(new Font("Dialog", Font.BOLD, 17));
		lblSistemaMusimundo.setBounds(10, 11, 204, 14);
		add(lblSistemaMusimundo);
		
		JButton btnABMUsuario = new JButton("ABM Usuario");
		btnABMUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			marco.setContentPane(new VerUsuarios(tipoUsuario,marco));
			marco.validate();
				
				
				
				
			}
		});
		btnABMUsuario.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnABMUsuario.setBounds(212, 55, 204, 64);
		add(btnABMUsuario);
		
		JButton btnConsultarTurnos = new JButton("Turnos");
		btnConsultarTurnos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(tipoUsuario==1)
				{
					marco.setContentPane(new VerTurnos(tipoUsuario,marco));
					marco.validate();
				}
				if(tipoUsuario==2)
				{
					Medico med1=new Medico();
					MedicoDAO meddao=new MedicoDAO();
					med1=meddao.BuscarMedPordni(contraseña);
					
					marco.setContentPane(new VerTurnosDeUnMedico(tipoUsuario,marco,med1));
					marco.validate();
				}
				
				
			}
		});
		btnConsultarTurnos.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnConsultarTurnos.setBounds(210, 149, 206, 64);
		add(btnConsultarTurnos);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesión");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmacion;
				confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que quiere cerrar sesión?", "Alerta", JOptionPane.YES_NO_OPTION);
				if (confirmacion == JOptionPane.YES_OPTION) {
					marco.setBounds(100, 100, 400, 300);
					marco.setContentPane(new InicioSesion(marco));
					marco.validate();
	        	}
			}
		});
		btnCerrarSesion.setFont(new Font("Dialog", Font.PLAIN, 14));
		btnCerrarSesion.setBounds(212, 240, 204, 64);
		add(btnCerrarSesion);
		
		//RESTRICCIONES DE USUARIO
		
		if(TipoUsuariostring.equals("MEDICO"))
		{
			btnABMUsuario.setEnabled(false);//HACER Gris el boton
			btnABMUsuario.setVisible(false);//OCULTAR EL BOTON
		}
		
	}
}