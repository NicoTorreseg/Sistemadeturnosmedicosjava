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

import com.toedter.calendar.JDateChooser;

import TurnosMedicos.DAO.MedicoDAO;
import TurnosMedicos.DAO.TurnosDAO;
import TurnosMedicos.DAO.UsuarioDAO;
import TurnosMedicos.modelo.Medico;
import TurnosMedicos.modelo.Turno;
import TurnosMedicos.modelo.Usuario;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

@SuppressWarnings("serial")
public class VerTurnosDeUnMedico extends JPanel {
	private JTable table;
	private DefaultTableModel defaultTableModel;
	
	public VerTurnosDeUnMedico(int tipoUsuario,JFrame marco,Medico med) {
		setLayout(null);
		
		JLabel lblClientes = new JLabel("Turnos");
		lblClientes.setFont(new Font("Dialog", Font.BOLD, 16));
		lblClientes.setBounds(277, 11, 109, 14);
		add(lblClientes);
		
		JButton btnMenuPrincipal = new JButton("Volver al Menú");
		btnMenuPrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				marco.setContentPane(new Menu(tipoUsuario,marco,med.getDnimedico()));
				marco.validate();
			}
		});
		btnMenuPrincipal.setBounds(509, 405, 120, 23);
		add(btnMenuPrincipal);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 83, 619, 309);
		add(scrollPane);
				
		table = new JTable();
		
			
		
		
		defaultTableModel = new DefaultTableModel(new String[] {"ID", "fecha_turno", "hora_turno", "Nombre_Medico","nombre_paciente"}, 0);
		
		
		
		table.setModel(defaultTableModel);
		scrollPane.setViewportView(table);
		
		JDateChooser dateChooserF_Desde = new JDateChooser();
		
		JDateChooser dateChooserF_Hasta = new JDateChooser();
		dateChooserF_Hasta.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
				TurnosDAO turnsdao = new TurnosDAO();
				ArrayList<Turno> listaTurnos = turnsdao.ListarTurnosDeMedico(med.getId());
				cargarTabla(listaTurnos, dateChooserF_Desde.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),dateChooserF_Hasta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() );
				
				
			}
		});
		dateChooserF_Hasta.setBounds(320, 52, 95, 20);
		add(dateChooserF_Hasta);
		
		dateChooserF_Desde.setDateFormatString("dd-MM-yyyy");
		Date date1=new Date();
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse("1900-12-02");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dateChooserF_Desde.setDate(date1);

		
		dateChooserF_Hasta.setDateFormatString("dd-MM-yyyy");
		Date date2=new Date();
		try {
			date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2100-12-24");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dateChooserF_Hasta.setDate(date2);
		dateChooserF_Desde.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				
				
				TurnosDAO turnsdao = new TurnosDAO();
				ArrayList<Turno> listaTurnos = turnsdao.ListarTurnosDeMedico(med.getId());
				cargarTabla(listaTurnos, dateChooserF_Desde.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),dateChooserF_Hasta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() );
				
				
				
				
				
				
			}
		});
		dateChooserF_Desde.setBounds(210, 52, 95, 20);
		add(dateChooserF_Desde);
		
		
		
		JLabel lblDesde = new JLabel("Desde");
		lblDesde.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesde.setBounds(233, 36, 46, 14);
		add(lblDesde);
		
		JLabel lblHasta = new JLabel("Hasta");
		lblHasta.setHorizontalAlignment(SwingConstants.CENTER);
		lblHasta.setBounds(344, 36, 46, 14);
		add(lblHasta);
		
		
		TurnosDAO turnsdao = new TurnosDAO();
		ArrayList<Turno> listaTurnos = turnsdao.ListarTurnosDeMedico(med.getId());
		cargarTabla(listaTurnos,dateChooserF_Desde.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), dateChooserF_Hasta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() );
		
		
	}
	
	

	private void cargarTabla(ArrayList<Turno> listaTurnos,LocalDate fechadesdee,LocalDate fechahasta) {
		defaultTableModel.setRowCount(0);
		for (Turno t : listaTurnos) {
		
					
			MedicoDAO MedDao=new MedicoDAO();
			String Nombremed=MedDao.BuscarMedPoridComboBox(t.getMedicoid());
			
		//	TipoUsuarioDAO tipoDAO = new TipoUsuarioDAO();
	//		TipoUsuario cargoUsuario = new TipoUsuario();
		//	cargoUsuario = tipoDAO.obtenerNombreSegunID(idTipoUsuario);
			if((t.getFecha().isAfter(fechadesdee)||t.getFecha().isEqual(fechadesdee))&&( t.getFecha().isBefore(fechahasta)||t.getFecha().isEqual(fechahasta)))
			{
				

						defaultTableModel.addRow(
								new Object[] { t.getId(), t.getFecha().toString(), t.getHora(), Nombremed, t.getNombrePaciente() });
			}}
	}
}
