package project_1;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JTextField textField;
    private JTextField keyField;
    private JTextArea outputArea;

    public GUI() {
        setTitle("K-Rail Fence Cipher Encryption/Decryption");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JLabel textLabel = new JLabel("Enter Text:");
        textField = new JTextField();
        JLabel keyLabel = new JLabel("Enter Key:");
        keyField = new JTextField();
        JButton encryptButton = new JButton("Encrypt");
        JButton decryptButton = new JButton("Decrypt");

        inputPanel.add(textLabel);
        inputPanel.add(textField);
        inputPanel.add(keyLabel);
        inputPanel.add(keyField);
        inputPanel.add(encryptButton);
        inputPanel.add(decryptButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText().replaceAll("\\s", "");
                int key = Integer.parseInt(keyField.getText());
                String encryptedText = project_1_computer_security.encryption(text, key);
                char[][] cipher = project_1_computer_security.printSegment(text, key);

                // Display the encrypted text and the array in the output area
                StringBuilder outputText = new StringBuilder();
                outputText.append("Encrypted Text:\n").append(encryptedText).append("\n");
                outputText.append("Encrypted Text Model:\n");
                for (char[] row : cipher) {
                    for (char c : row) {
                        outputText.append(c).append("   ");
                    }
                    outputText.append("\n");
                }
                outputArea.setText(outputText.toString());
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText().replaceAll("\\s", "");
                int key = Integer.parseInt(keyField.getText());
                String decryptedText = project_1_computer_security.decryption(text, key);
                outputArea.setText("Decrypted Text:\n" + decryptedText);
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    
    public static void showWelcomeMessage() {
        JOptionPane.showMessageDialog(null,
                "Welcome to k-Rail Fence Cipher Encryption/Decryption Tool!",
                "Welcome",
                JOptionPane.INFORMATION_MESSAGE);

        int choice = JOptionPane.showConfirmDialog(null,
                "Would you like to start the encryption/decryption tool?",
                "Start",
                JOptionPane.OK_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new GUI();
                }
            });
        } else {
            JOptionPane.showMessageDialog(null,
                    "Thank you for using Affine Cipher Encryption/Decryption Tool!",
                    "Goodbye",
                    JOptionPane.INFORMATION_MESSAGE);
        }
}
    
    
    public static void main(String[] args) {
    	showWelcomeMessage();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
