import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class BloodDonationApp extends JFrame {
    JLabel titleLabel, nameLabel, ageLabel, bloodGroupLabel, contactLabel, emailLabel;
    JTextField nameField, ageField, contactField, emailField;
    JComboBox<String> bloodGroupChoice;
    JButton addButton, recordButton, searchButton, viewAllButton, donationHistoryButton;

    public BloodDonationApp() {
    
        setTitle("Blood Donation System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.setBackground(Color.WHITE);

        titleLabel = new JLabel("Blood Donation System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));  // Increased font size
        titleLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);  // Add padding
        mainPanel.add(titleLabel, gbc);

        nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Set larger font
        ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        bloodGroupLabel = new JLabel("Blood Group:");
        bloodGroupLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        contactLabel = new JLabel("Contact:");
        contactLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 18));  // Set font size for text fields
        ageField = new JTextField(5);
        ageField.setFont(new Font("Arial", Font.PLAIN, 18));
        contactField = new JTextField(15);
        contactField.setFont(new Font("Arial", Font.PLAIN, 18));
        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 18));

        bloodGroupChoice = new JComboBox<>(new String[]{"A+", "B+", "O+", "AB+", "A-", "B-", "O-", "AB-"});
        bloodGroupChoice.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size for combo box

        addButton = new JButton("Add Donor");
        recordButton = new JButton("Record Donation");
        searchButton = new JButton("Search Donor");
        viewAllButton = new JButton("View All Donors");
        donationHistoryButton = new JButton("Donation History");

        addButton.setFont(new Font("Arial", Font.PLAIN, 18));
        recordButton.setFont(new Font("Arial", Font.PLAIN, 18));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 18));
        viewAllButton.setFont(new Font("Arial", Font.PLAIN, 18));
        donationHistoryButton.setFont(new Font("Arial", Font.PLAIN, 18));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(ageLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(bloodGroupLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(bloodGroupChoice, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(contactLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(contactField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        mainPanel.add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        mainPanel.add(recordButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        mainPanel.add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        mainPanel.add(viewAllButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        mainPanel.add(donationHistoryButton, gbc);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDonor();
            }
        });

        recordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                recordDonation();
            }
        });

        // Event listener for searching donor
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchDonor();
            }
        });

        // Event listener for viewing all donors
        viewAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAllDonors();
            }
        });

        // Event listener for viewing donation history
        donationHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewDonationHistory();
            }
        });

        // Set the main panel as the content pane
        setContentPane(mainPanel);
        setVisible(true);
    }

    // Add donor details to the database
    private void addDonor() {
        String name = nameField.getText();
        String ageText = ageField.getText();
        String bloodGroup = (String) bloodGroupChoice.getSelectedItem();
        String contact = contactField.getText();
        String email = emailField.getText();

        // Validate input fields
        if (validateDonorDetails(name, ageText, contact, email)) {
            int age = Integer.parseInt(ageText);

            try {
                Connection con = DatabaseConnection.getConnection();
                String query = "INSERT INTO Donor(name, age, bloodGroup, contact, email) VALUES(?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setInt(2, age);
                stmt.setString(3, bloodGroup);
                stmt.setString(4, contact);
                stmt.setString(5, email);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Donor Added Successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    // Validate donor details before adding
    private boolean validateDonorDetails(String name, String ageText, String contact, String email) {
        if (name.isEmpty() || ageText.isEmpty() || contact.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return false;
        }

        try {
            Integer.parseInt(ageText); // Check if age is a valid number
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a valid number.");
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return false;
        }

        return true;
    }

    // Record a donation
    private void recordDonation() {
        try {
            int donorId = Integer.parseInt(JOptionPane.showInputDialog("Enter Donor ID"));
            String donationDate = JOptionPane.showInputDialog("Enter Donation Date (YYYY-MM-DD)");

            Connection con = DatabaseConnection.getConnection();
            String query = "INSERT INTO DonationRecord(id, donationDate) VALUES(?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, donorId);
            stmt.setString(2, donationDate);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Donation Recorded Successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // Search donor by ID or Name
    private void searchDonor() {
        String searchQuery = JOptionPane.showInputDialog("Enter Donor ID or Name:");
        try {
            Connection con = DatabaseConnection.getConnection();
            String query = "SELECT * FROM Donor WHERE id = ? OR name = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, searchQuery);
            stmt.setString(2, searchQuery);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String result = "ID: " + rs.getInt("id") + "\n"
                        + "Name: " + rs.getString("name") + "\n"
                        + "Age: " + rs.getInt("age") + "\n"
                        + "Blood Group: " + rs.getString("bloodGroup") + "\n"
                        + "Contact: " + rs.getString("contact") + "\n"
                        + "Email: " + rs.getString("email");
                JOptionPane.showMessageDialog(this, result);
            } else {
                JOptionPane.showMessageDialog(this, "Donor not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // View all donors
    private void viewAllDonors() {
        try {
            Connection con = DatabaseConnection.getConnection();
            String query = "SELECT * FROM Donor";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id")).append("\n")
                        .append("Name: ").append(rs.getString("name")).append("\n")
                        .append("Age: ").append(rs.getInt("age")).append("\n")
                        .append("Blood Group: ").append(rs.getString("bloodGroup")).append("\n")
                        .append("Contact: ").append(rs.getString("contact")).append("\n")
                        .append("Email: ").append(rs.getString("email")).append("\n\n");
            }

            if (result.length() > 0) {
                JOptionPane.showMessageDialog(this, result.toString());
            } else {
                JOptionPane.showMessageDialog(this, "No donors found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // View donation history
    private void viewDonationHistory() {
        try {
            int donorId = Integer.parseInt(JOptionPane.showInputDialog("Enter Donor ID"));

            Connection con = DatabaseConnection.getConnection();
            String query = "SELECT * FROM DonationRecord WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, donorId);
            ResultSet rs = stmt.executeQuery();

            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                result.append("Donation ID: ").append(rs.getInt("id")).append("\n")
                        .append("Date: ").append(rs.getString("donationDate")).append("\n\n");
            }

            if (result.length() > 0) {
                JOptionPane.showMessageDialog(this, result.toString());
            } else {
                JOptionPane.showMessageDialog(this, "No donation history found for this donor.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new BloodDonationApp();
    }
}
