import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import TurnosMedicos.vista.InicioSesion;

public class App {
	public static void main(String[] args) {
		JFrame marco = new JFrame();
		marco.setTitle("Tp Proyecto informatico");
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco.setBounds(100, 100, 400, 300);
		marco.setVisible(true);
		marco.setContentPane(new InicioSesion(marco));
		JDesktopPane desktopPane = new JDesktopPane();
		JInternalFrame internalFrame = new JInternalFrame();        
		JPanel mainPanel = new JPanel();
		mainPanel.add(desktopPane);
		mainPanel.add(internalFrame);
		marco.add(mainPanel);
		marco.validate();
	}
}