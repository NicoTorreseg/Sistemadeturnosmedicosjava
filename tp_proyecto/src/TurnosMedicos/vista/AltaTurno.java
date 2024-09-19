package TurnosMedicos.vista;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import TurnosMedicos.DAO.MedicoDAO;
import TurnosMedicos.DAO.TurnosDAO;
import TurnosMedicos.DAO.UsuarioDAO;
import TurnosMedicos.modelo.Medico;
import TurnosMedicos.modelo.Turno;
import TurnosMedicos.modelo.Usuario;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import com.github.lgooddatepicker.components.TimePicker;

@SuppressWarnings("serial")
public class AltaTurno extends JPanel {
	private JTextField TextFieldPaciente;

	public AltaTurno(int tipoUsuario,JFrame marco) {
		setLayout(null);
		
		JLabel lblCargarUsuario = new JLabel("Cargar Turno");
		lblCargarUsuario.setFont(new Font("Dialog", Font.BOLD, 16));
		lblCargarUsuario.setBounds(10, 11, 127, 21);
		add(lblCargarUsuario);
		
		JLabel lblUsername = new JLabel("Hora");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(67, 89, 61, 23);
		add(lblUsername);
		
		JLabel lblContraseña = new JLabel("Paciente");
		lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContraseña.setBounds(29, 137, 108, 21);
		add(lblContraseña);
		
		TextFieldPaciente = new JTextField();
		TextFieldPaciente.setBounds(174, 138, 136, 22);
		add(TextFieldPaciente);
		TextFieldPaciente.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Medico");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(20, 188, 127, 21);
		add(lblNewLabel);
		JDateChooser dateChooser_Fecha = new JDateChooser();
		dateChooser_Fecha.setBounds(174, 43, 136, 23);
		add(dateChooser_Fecha);
		MedicoDAO daoMed = new MedicoDAO();
	ArrayList<Medico> Medicos = new ArrayList<Medico>();
		Medicos = daoMed.listarMedicos();
		JComboBox<String> boxMedico = new JComboBox<String>();
		
		ArrayList<String>MedsIdscombo=new ArrayList<String>();
	for (Medico t : Medicos) {
			boxMedico.addItem(t.getNombre());
			MedsIdscombo.add(Integer.toString(t.getId()));
			
		}
		boxMedico.setFont(new Font("Tahoma", Font.PLAIN, 14));
		boxMedico.setBounds(174, 188, 136, 20);
		add(boxMedico);
		
		
		TimePicker timePicker_Hora = new TimePicker();
		timePicker_Hora.setBounds(190, 92, 102, 21);
		add(timePicker_Hora);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmacion;
				
				
				LocalDate FechaTurno= dateChooser_Fecha.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String HoraTurno= timePicker_Hora.toString();
				String nombrePaciente = TextFieldPaciente.getText();
				int IndexSeleccionado= boxMedico.getSelectedIndex();
				int idMed=Integer.parseInt(MedsIdscombo.get(IndexSeleccionado));
				MedicoDAO Medao=new MedicoDAO();
			//	String mediconombre=Medao.BuscarMedPoridComboBox(idMed);
				
		        if ( nombrePaciente.isEmpty()|| HoraTurno.isEmpty()) {
			        JOptionPane.showMessageDialog(null, "Por favor rellene todos los campos.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else {
					confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro?", "Alerta", JOptionPane.YES_NO_OPTION);
					if (confirmacion == JOptionPane.YES_OPTION) {
			
						System.out.println("----altas"+idMed);
						Turno t = new Turno(FechaTurno, HoraTurno, nombrePaciente,idMed);
						TurnosDAO turnsDAO = new TurnosDAO();
						boolean valido=	turnsDAO.cargarTurno(t);
					if(valido)
					{
						 JOptionPane.showMessageDialog(null, "El Turno ha sido registrado exitosamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
					   		
					   		marco.setContentPane(new VerTurnos(tipoUsuario,marco));
					   		marco.validate();
					}
					else{
						JOptionPane.showMessageDialog(null, "El Turno ya existe", "Aviso", JOptionPane.ERROR_MESSAGE);
						marco.setContentPane(new AltaTurno(tipoUsuario, marco));
						marco.validate();
					}
			           
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
				marco.setContentPane(new AltaTurno(tipoUsuario,marco));
				marco.validate();
			}
		});
		btnCancelar.setBounds(154, 252, 89, 23);
		add(btnCancelar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				marco.setContentPane(new VerTurnos(tipoUsuario,marco));
				marco.validate();
			}
		});
		btnSalir.setBounds(261, 252, 81, 23);
		add(btnSalir);
		
		
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFecha.setBounds(48, 43, 89, 23);
		add(lblFecha);
	
		
	}
}