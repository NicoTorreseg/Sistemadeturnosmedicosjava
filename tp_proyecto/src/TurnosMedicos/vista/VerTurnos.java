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
import TurnosMedicos.modelo.Turno;
import TurnosMedicos.modelo.Usuario;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.beans.PropertyChangeEvent;

@SuppressWarnings("serial")
public class VerTurnos extends JPanel {
	private JTable table;
	private DefaultTableModel defaultTableModel;
	String Stringselected = "Todos";
//	boolean fdesdeselect=false;
	//boolean fhastaselect=false;
//	LocalDate FechaDesde;
//	LocalDate FechaHasta;
	//int i = 0;
	//int e = 0;
	public VerTurnos(int tipoUsuario, JFrame marco) {
		setLayout(null);

		JLabel lblClientes = new JLabel("Turnos");
		lblClientes.setFont(new Font("Dialog", Font.BOLD, 16));
		lblClientes.setBounds(315, 0, 109, 14);
		add(lblClientes);

		JButton btnCargarNuevoUsuario = new JButton("Cargar nuevo turno");
		btnCargarNuevoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnCargarNuevoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				marco.setContentPane(new AltaTurno(tipoUsuario, marco));
				marco.validate();
			}
		});
		btnCargarNuevoUsuario.setBounds(223, 36, 123, 38);
		add(btnCargarNuevoUsuario);

		JButton btnEliminar = new JButton("Eliminar seleccionado");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					TurnosDAO turndao = new TurnosDAO();
					int fila = table.getSelectedRow();
					int id = ((Integer) table.getValueAt(fila, 0)).intValue();
					int confirmacion;
					confirmacion = JOptionPane.showConfirmDialog(null,
							"¿Está seguro de que desea eliminar este turno?", "Alerta", JOptionPane.YES_NO_OPTION);
					if (confirmacion == JOptionPane.YES_OPTION) {
						turndao.eliminarTurno(id);
						JOptionPane.showMessageDialog(null, "El turno ha sido eliminado exitosamente.", "Aviso",
								JOptionPane.INFORMATION_MESSAGE);
						cargarTabla(turndao.ListarTurnos());
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "No hay ningun turno seleccionado.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnEliminar.setBounds(356, 36, 130, 38);
		btnEliminar.setEnabled(true);
		add(btnEliminar);

		JButton btnModificar = new JButton("Modificar seleccionado");
		btnModificar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					int fila = table.getSelectedRow();
					int id = ((Integer) table.getValueAt(fila, 0)).intValue();
					String fechaturno = table.getValueAt(fila, 1).toString();
					String horaturno = table.getValueAt(fila, 2).toString();
					String Nombrepaciente = table.getValueAt(fila, 4).toString();

					JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) ev.getSource());
					marco.setContentPane(new ModificarTurno(marco, id, fechaturno, horaturno, Nombrepaciente));
					marco.validate();
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "No hay ningun turno seleccionado.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModificar.setBounds(496, 36, 133, 38);
		btnModificar.setEnabled(true);
		add(btnModificar);

		JButton btnMenuPrincipal = new JButton("Volver al Menú");
		btnMenuPrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame marco = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
				marco.setContentPane(new Menu(tipoUsuario, marco, 0));
				marco.validate();
			}
		});
		btnMenuPrincipal.setBounds(509, 405, 120, 23);
		add(btnMenuPrincipal);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 85, 619, 309);
		add(scrollPane);

		table = new JTable();

		defaultTableModel = new DefaultTableModel(
				new String[] { "ID", "fecha_turno", "hora_turno", "Nombre_Medico", "nombre_paciente" }, 0);

		table.setModel(defaultTableModel);
		scrollPane.setViewportView(table);

		JDateChooser dateChooserF_Desde = new JDateChooser();
		dateChooserF_Desde.setDateFormatString("dd-MM-yyyy");
		Date date1=new Date();
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse("1900-12-02");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dateChooserF_Desde.setDate(date1);
JDateChooser dateChooser_F_hasta = new JDateChooser();
		
		dateChooser_F_hasta.setDateFormatString("dd-MM-yyyy");
		Date date2=new Date();
		try {
			date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2100-12-24");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		dateChooserF_Desde.addPropertyChangeListener(new PropertyChangeListener() {
			

			public void propertyChange(PropertyChangeEvent arg0) {

			//	System.out.println(dateChooserF_Desde.getDate());
			//	if (i > 0) {
				//	 FechaDesde = dateChooserF_Desde.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				//	System.out.println("fecha desde :"+FechaDesde);
				//	 fdesdeselect=true;
			//	}
			//i++;
		//		if(fdesdeselect&&fhastaselect){
				//	System.out.println("se seleccionaron 2 fechas");
					TurnosDAO turnsdao = new TurnosDAO();
					ArrayList<Turno> listaTurnos = turnsdao.ListarTurnos();
					cargarTablafechas(listaTurnos, dateChooserF_Desde.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),dateChooser_F_hasta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() );
					
			//		fdesdeselect=false;
				//	fhastaselect=false;
			//	}
			}

		});

		dateChooserF_Desde.setBounds(10, 64, 95, 20);
		add(dateChooserF_Desde);

		
		dateChooser_F_hasta.setDate(date2);
		dateChooser_F_hasta.addPropertyChangeListener(new PropertyChangeListener() {
			
			
			public void propertyChange(PropertyChangeEvent arg0) {
				//System.out.println(dateChooserF_Desde.getDate());
				
			//	if (e > 0) {
					// FechaHasta = dateChooser_F_hasta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				//	System.out.println("fecha hasta :"+FechaHasta);
			//		 fhastaselect=true;
		//		}
			//	e++;
		//		if(fdesdeselect&&fhastaselect){
		//			System.out.println("se seleccionaron 2 fechas");
					TurnosDAO turnsdao = new TurnosDAO();
					ArrayList<Turno> listaTurnos = turnsdao.ListarTurnosDeMedicoPorNombre(Stringselected);
					cargarTablafechas(listaTurnos, dateChooserF_Desde.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),dateChooser_F_hasta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() );
				//	fdesdeselect=false;
				//	fhastaselect=false;
			//	}
				
				
				
				
			}
		});
		dateChooser_F_hasta.setBounds(115, 64, 98, 20);
		add(dateChooser_F_hasta);

		JComboBox<String> comboBox_Medicos = new JComboBox<String>();
		comboBox_Medicos.addItem("Todos");
		MedicoDAO meddao = new MedicoDAO();
		ArrayList<Medico> medicos = meddao.listarMedicos();
		// llena el jlist
		for (Medico m : medicos) {
			comboBox_Medicos.addItem(m.getNombre());
		}

		if (Stringselected.equals("Todos")) {
			TurnosDAO turnsdao = new TurnosDAO();
			ArrayList<Turno> listaTurnos = turnsdao.ListarTurnos();
			cargarTablafechas(listaTurnos, dateChooserF_Desde.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),dateChooser_F_hasta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() );


		}

		comboBox_Medicos.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				System.out.println("Seleccionaste a: " + comboBox_Medicos.getSelectedItem().toString());
				Stringselected = comboBox_Medicos.getSelectedItem().toString();
				TurnosDAO turndao = new TurnosDAO();
				ArrayList<Turno> turnoss = turndao.ListarTurnosDeMedicoPorNombre(Stringselected);
				cargarTablafechas(turnoss, dateChooserF_Desde.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),dateChooser_F_hasta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() );


				if (Stringselected.equals("Todos")) {
					TurnosDAO turnsdao = new TurnosDAO();
					ArrayList<Turno> listaTurnos = turnsdao.ListarTurnos();
					cargarTablafechas(listaTurnos, dateChooserF_Desde.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),dateChooser_F_hasta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() );


				}
			}

		});

		comboBox_Medicos.setBounds(64, 24, 95, 20);
		add(comboBox_Medicos);

		if(comboBox_Medicos.getItemCount()==1){
			
			btnCargarNuevoUsuario.setEnabled(false);
		}
		
		JLabel lblFechaDesde = new JLabel("Desde");
		lblFechaDesde.setHorizontalAlignment(SwingConstants.CENTER);
		lblFechaDesde.setBounds(20, 48, 59, 14);
		add(lblFechaDesde);

		JLabel lblFechahasta = new JLabel("Hasta");
		lblFechahasta.setHorizontalAlignment(SwingConstants.CENTER);
		lblFechahasta.setBounds(138, 48, 59, 14);
		add(lblFechahasta);

		JLabel lblNewLabel = new JLabel("Medico");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setBounds(89, 11, 46, 14);
		add(lblNewLabel);
	}
	private void cargarTablafechas(ArrayList<Turno> listaTurnos,LocalDate fechadesdee,LocalDate fechahasta) {
		defaultTableModel.setRowCount(0);
		
		TurnosDAO turndao = new TurnosDAO();
		listaTurnos = turndao.ListarTurnosDeMedicoPorNombre(Stringselected);


		if (Stringselected.equals("Todos")) {
			TurnosDAO turnsdao = new TurnosDAO();
			listaTurnos = turnsdao.ListarTurnos();
			

		}
		for (Turno t : listaTurnos) {

			MedicoDAO MedDao = new MedicoDAO();
			String Nombremed = MedDao.BuscarMedPoridComboBox(t.getMedicoid());

			// TipoUsuarioDAO tipoDAO = new TipoUsuarioDAO();
			// TipoUsuario cargoUsuario = new TipoUsuario();
			// cargoUsuario = tipoDAO.obtenerNombreSegunID(idTipoUsuario);
		//	LocalDate fechadesdee=LocalDate.parse(fechadesde);
			
if((t.getFecha().isAfter(fechadesdee)||t.getFecha().isEqual(fechadesdee))&&( t.getFecha().isBefore(fechahasta)||t.getFecha().isEqual(fechahasta)))
{
	

			defaultTableModel.addRow(
					new Object[] { t.getId(), t.getFecha().toString(), t.getHora(), Nombremed, t.getNombrePaciente() });
}
		}
	}

	private void cargarTabla(ArrayList<Turno> listaTurnos) {
		defaultTableModel.setRowCount(0);
		for (Turno t : listaTurnos) {

			MedicoDAO MedDao = new MedicoDAO();
			String Nombremed = MedDao.BuscarMedPoridComboBox(t.getMedicoid());

			// TipoUsuarioDAO tipoDAO = new TipoUsuarioDAO();
			// TipoUsuario cargoUsuario = new TipoUsuario();
			// cargoUsuario = tipoDAO.obtenerNombreSegunID(idTipoUsuario);

			defaultTableModel.addRow(
					new Object[] { t.getId(), t.getFecha().toString(), t.getHora(), Nombremed, t.getNombrePaciente() });
		}
	}
}
