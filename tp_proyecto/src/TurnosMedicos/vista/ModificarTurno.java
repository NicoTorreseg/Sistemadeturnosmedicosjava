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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import com.github.lgooddatepicker.components.TimePicker;

@SuppressWarnings("serial")
public class ModificarTurno extends JPanel {
	private JTextField TextFieldPaciente;

	public ModificarTurno(JFrame marco,int id,String fechaturno,String horaturno,String Nombrepaciente) {
		setLayout(null);
		
		JLabel lblCargarUsuario = new JLabel("Modificar Turno");
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
		
		TextFieldPaciente = new JTextField(Nombrepaciente);
		TextFieldPaciente.setBounds(174, 138, 136, 22);
		add(TextFieldPaciente);
		TextFieldPaciente.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Medico");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(20, 188, 127, 21);
		add(lblNewLabel);
		JDateChooser dateChooser_Fecha = new JDateChooser();
		dateChooser_Fecha.setDateFormatString("dd-MM-yyyy");
		Date date1=new Date();
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaturno);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dateChooser_Fecha.setDate(date1);
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
		timePicker_Hora.getComponentTimeTextField().setText(horaturno);
		
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
						Turno t = new Turno(id,FechaTurno, HoraTurno, nombrePaciente,idMed);
						TurnosDAO turnsDAO = new TurnosDAO();
						boolean valido=	turnsDAO.actualizarTurno(t);
					if(valido)
					{
						 JOptionPane.showMessageDialog(null, "El Turno se ha sido modificado exitosamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
						 int tipoUsuario=1;
					   		marco.setContentPane(new VerTurnos(tipoUsuario,marco));
					   		marco.validate();
					}
					else{
						JOptionPane.showMessageDialog(null, "El Turno ya existe", "Aviso", JOptionPane.ERROR_MESSAGE);
					
						marco.setContentPane(new ModificarTurno( marco,id,fechaturno,horaturno,Nombrepaciente));
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
				
				marco.setContentPane(new ModificarTurno(marco,id,fechaturno,horaturno,Nombrepaciente));
				marco.validate();
			}
		});
		btnCancelar.setBounds(154, 252, 89, 23);
		add(btnCancelar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				int tipoUsuario=1;
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