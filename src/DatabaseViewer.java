import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseViewer extends JFrame {
	
    private JTable table;
    private JComboBox<String> sortOptions;
    private JTextField searchField;
    private JButton searchButton;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    
    @SuppressWarnings("static-access")
	public DatabaseViewer(DefaultTableModel model) {
        setTitle("Database Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);

        // Create table and set the model
        this.sortOptions = new JComboBox<>();
        this.table = new JTable(model);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.searchField = new JTextField(8);
        this.searchButton = new JButton("Search");
        
        this.addButton = new JButton("Add Record");
        this.updateButton = new JButton("Update Records");
        this.deleteButton = new JButton("Delete Records");
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(this.addButton);
        buttonPanel.add(this.updateButton);
        buttonPanel.add(this.deleteButton);
        
        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel searchPanel = new JPanel();
        searchPanel.setLocation(500, 500);
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(this.searchField);
        searchPanel.add(this.searchButton);
        
        JPanel dropDownPanel = new JPanel();
        dropDownPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        dropDownPanel.add(new JLabel("Search from: "));
        dropDownPanel.add(this.sortOptions);
        this.sortOptions.addItem("Course");
        this.sortOptions.addItem("CRN");
        this.sortOptions.addItem("Subject");
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tablePanel, BorderLayout.NORTH);
        getContentPane().add(dropDownPanel, BorderLayout.SOUTH);
        getContentPane().add(searchPanel);
        getContentPane().add(buttonPanel, BorderLayout.WEST);
        
        // Load data from the database and populate the table
        loadDataFromDatabase(model);
        
     // Add ActionListener to the sortOptions JComboBox
        this.sortOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) DatabaseViewer.this.sortOptions.getSelectedItem();
                if (selectedOption != null) {
                   // add code here 
                }
            }
        });
        this.addButton.addActionListener(e -> showAddRecordDialog());
        this.updateButton.addActionListener(e -> refreshTableModel(model));
        this.deleteButton.addActionListener(e -> setupDeleteButton());
    }
    
    private void showAddRecordDialog() {
        // Create a dialog that is modal and blocks input to other windows
        JDialog dialog = new JDialog(this, "Add Record", Dialog.ModalityType.APPLICATION_MODAL);
        
        // Use a JPanel with a GridBagLayout to arrange the labels and fields
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        // For each field, create a label and a text field and add them to the panel
        JLabel nameLabel = new JLabel("CRN:");
        JTextField nameField = new JTextField(20);
        // ... add other fields
        
        c.gridx = 0; c.gridy = 0; panel.add(nameLabel, c);
        c.gridx = 1; c.gridy = 0; panel.add(nameField, c);
        // ... add other fields to the grid
        
        // Add a button to submit the form
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addRecord(nameField.getText() /*, otherFieldValues... */));
        c.gridx = 0; c.gridy = 1; c.gridwidth = 2; panel.add(addButton, c);
        
        // Add the panel to the dialog
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private boolean validateInput(String name /*, otherParameters */) {
        if (name.isEmpty() /* || other validation checks */) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Additional validation checks...
        return true;
    }
    
    private void setupDeleteButton() {
        this.deleteButton.addActionListener(e -> {
            int selectedRow = this.table.getSelectedRow();
            if (selectedRow >= 0) {
                // Assuming the first column is the ID column and it is a String
                String idString = (String) table.getModel().getValueAt(selectedRow, 4);
                try {
                    int idToDelete = Integer.parseInt(idString);
                    confirmAndDeleteRecord(idToDelete);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(this, "Invalid format for ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a record to delete.");
            }
        });
    }
    
    private void confirmAndDeleteRecord(int id) {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this record?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            deleteRecord(id);
        }
    }
    
    private void addRecord(String name /*, otherParameters */) {
        if (!validateInput(name /*, otherParameters */)) {
            return;
        }
        
        String sql = "INSERT INTO cbm2023 (crn /*, otherColumns */) VALUES (? /*, otherPlaceholders */)";
        
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, name);
            // statement.setXXX(2, otherValue); // Set other values
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Record added successfully.");
                refreshTableModel((DefaultTableModel) this.table.getModel());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteRecord(int id) {
        String sql = "DELETE FROM cbm2023 WHERE crn = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Record deleted successfully.");
                refreshTableModel((DefaultTableModel) table.getModel());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshTableModel(DefaultTableModel model) {
        // Clear the existing model
        model.setRowCount(0);
        model.setColumnCount(0);

        // Load the updated data from the database
        loadDataFromDatabase(model);
    }

    private void loadDataFromDatabase(DefaultTableModel model) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT * FROM cbm2023;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Get column names from the ResultSet metadata
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(resultSet.getMetaData().getColumnName(i));
            }

            // Populate the table with data from the database
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                model.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
