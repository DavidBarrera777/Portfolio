import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = new DefaultTableModel();
            DatabaseViewer viewer = new DatabaseViewer(model);
            viewer.setVisible(true);
        });

	}
	
	

}
