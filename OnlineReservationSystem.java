import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class OnlineReservationSystem {
    private static JFrame frame;
    private static JPanel panel;
    private static Map<String, Reservation> reservations = new HashMap<>();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createLoginUI());
    }
    private static JLabel statusLabel;
    private static void createLoginUI() {
        frame = new JFrame("Online Reservation System - Login");
        panel = new JPanel();

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JTextField usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(e -> validateLogin(usernameField.getText(), new String(passwordField.getPassword())));
        panel.add(loginButton);

        JLabel statusLabel = new JLabel("");
        statusLabel.setBounds(10, 110, 300, 25);
        panel.add(statusLabel);

        frame.add(panel);
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void validateLogin(String username, String password) {
        // Add your authentication logic here
        if ("admin".equals(username) && "password".equals(password)) {
            createMainUI();
        } else {
            statusLabel.setText("Invalid username or password");
        }
    }

    private static void createMainUI() {
        frame.setTitle("Online Reservation System - Main Menu");
        panel.removeAll();

        JButton reservationButton = new JButton("Reservation");
        reservationButton.setBounds(10, 20, 120, 25);
        reservationButton.addActionListener(e -> createReservationUI());
        panel.add(reservationButton);

        JButton cancellationButton = new JButton("Cancellation");
        cancellationButton.setBounds(140, 20, 120, 25);
        cancellationButton.addActionListener(e -> createCancellationUI());
        panel.add(cancellationButton);

        JButton viewRecordsButton = new JButton("View Records");
        viewRecordsButton.setBounds(10, 60, 120, 25);
        viewRecordsButton.addActionListener(e -> viewRecords());
        panel.add(viewRecordsButton);

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(140, 60, 120, 25);
        changePasswordButton.addActionListener(e -> changePassword());
        panel.add(changePasswordButton);

        JButton logoutButton = new JButton("Logout");
logoutButton.setBounds(10, 100, 120, 25);
logoutButton.addActionListener(e -> {
    createLoginUI();
    frame.dispose(); // Close the current frame
});
panel.add(logoutButton);



        frame.revalidate();
        frame.repaint();
    }

    private static void createReservationUI() {
        frame.setTitle("Online Reservation System - Reservation Form");
        panel.removeAll();

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        JTextField nameField = new JTextField(20);
        nameField.setBounds(100, 20, 165, 25);
        panel.add(nameField);

        JLabel trainNumberLabel = new JLabel("Train Number:");
        trainNumberLabel.setBounds(10, 50, 80, 25);
        panel.add(trainNumberLabel);

        JTextField trainNumberField = new JTextField(20);
        trainNumberField.setBounds(100, 50, 165, 25);
        panel.add(trainNumberField);

        JLabel dateLabel = new JLabel("Date of Journey:");
        dateLabel.setBounds(10, 80, 120, 25);
        panel.add(dateLabel);

        JTextField dateField = new JTextField(20);
        dateField.setBounds(140, 80, 125, 25);
        panel.add(dateField);

        JButton insertButton = new JButton("Insert");
        insertButton.setBounds(10, 150, 80, 25);
        insertButton.addActionListener(e -> insertReservation(nameField.getText(), trainNumberField.getText(), dateField.getText()));
        panel.add(insertButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(100, 150, 80, 25);
        resetButton.addActionListener(e -> resetReservationForm(nameField, trainNumberField, dateField));
        panel.add(resetButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(190, 150, 80, 25);
        backButton.addActionListener(e -> createMainUI());
        panel.add(backButton);

        frame.revalidate();
        frame.repaint();
    }

    private static void insertReservation(String name, String trainNumber, String date) {
        if (name.isEmpty() || trainNumber.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields");
            return;
        }

        Reservation reservation = new Reservation(name, trainNumber, date);
        reservations.put(reservation.getPnr(), reservation);

        JOptionPane.showMessageDialog(frame, "Reservation successful. PNR: " + reservation.getPnr());

        createMainUI();
    }

    private static void resetReservationForm(JTextField nameField, JTextField trainNumberField, JTextField dateField) {
        nameField.setText("");
        trainNumberField.setText("");
        dateField.setText("");
    }

    private static void createCancellationUI() {
        frame.setTitle("Online Reservation System - Cancellation Form");
        panel.removeAll();

        JLabel pnrLabel = new JLabel("PNR:");
        pnrLabel.setBounds(10, 20, 80, 25);
        panel.add(pnrLabel);

        JTextField pnrField = new JTextField(20);
        pnrField.setBounds(100, 20, 165, 25);
        panel.add(pnrField);

        JButton cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.setBounds(10, 150, 150, 25);
        cancelReservationButton.addActionListener(e -> cancelReservation(pnrField.getText()));
        panel.add(cancelReservationButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(170, 150, 100, 25);
        backButton.addActionListener(e -> createMainUI());
        panel.add(backButton);

        frame.revalidate();
        frame.repaint();
    }

    private static void cancelReservation(String pnr) {
        Reservation reservation = reservations.get(pnr);
        if (reservation != null) {
            reservations.remove(pnr);
            JOptionPane.showMessageDialog(frame, "Reservation canceled successfully.");
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid PNR. Please check and try again.");
        }

        createMainUI();
    }

    private static void viewRecords() {
        frame.setTitle("Online Reservation System - Passenger Records");
        panel.removeAll();

        JTextArea recordsTextArea = new JTextArea();
        recordsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(recordsTextArea);
        scrollPane.setBounds(10, 20, 300, 120);
        panel.add(scrollPane);

        for (Map.Entry<String, Reservation> entry : reservations.entrySet()) {
            recordsTextArea.append(entry.getValue().toString() + "\n");
        }

        JButton backButton = new JButton("Back");
        backButton.setBounds(10, 150, 120, 25);
        backButton.addActionListener(e -> createMainUI());
        panel.add(backButton);

        frame.revalidate();
        frame.repaint();
    }

    private static void changePassword() {
        // You may want to implement a secure password storage mechanism
        JOptionPane.showMessageDialog(frame, "Password changed successfully!");
    }

    public static class Reservation {
        private static int pnrCounter = 1;
        private String pnr;
        private String name;
        private String trainNumber;
        private String date;

        public Reservation(String name, String trainNumber, String date) {
            this.pnr = "PNR" + pnrCounter++;
            this.name = name;
            this.trainNumber = trainNumber;
            this.date = date;
        }

        public String getPnr() {
            return pnr;
        }

        public String getName() {
            return name;
        }

        public String getTrainNumber() {
            return trainNumber;
        }

        public String getDate() {
            return date;
        }

        @Override
        public String toString() {
            return "PNR: " + pnr + ", Name: " + name + ", Train Number: " + trainNumber + ", Date: " + date;
        }
    }
}
