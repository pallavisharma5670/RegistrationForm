import java.lang.*;
import javax.swing.*;
import javax.swing.JDialog;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class StudentRegistration extends JDialog{
    private JTextField tfFName;
    private JTextField tfMName;
    private JTextField tfEmail;
    private JTextField tfPassword;
    private JTextField tfBranch;
    private JTextField tfYear;
    private JTextField tfAddress;
    private JTextField tfMobile;
    private JTextField tfName;
    private JTextField tfSID;
    private JButton submitButton;
    private JButton cancelButton;
    private JPanel registerPanel;

    public StudentRegistration(JFrame parent){
        super(parent);
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        submitButton.addActionListener(e -> registerUser());
        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }

    private void registerUser() {
        String name = tfName.getText();
        String id = tfSID.getText();
        String fName = tfFName.getText();
        String mName = tfMName.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(tfPassword);
        String branch = tfBranch.getText();
        String year = tfYear.getText();
        String address = tfAddress.getText();
        String phone = tfMobile.getText();

        if (name.isEmpty() || id.isEmpty() || fName.isEmpty() || mName.isEmpty() || email.isEmpty() || phone.isEmpty() || branch.isEmpty() || year.isEmpty() || address.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please enter",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        student = addStudentUserToDatabase(name,id,fName,mName,email,password,branch,year,address,phone);
        if (student!= null){
            dispose();
        }else{
            JOptionPane.showMessageDialog(this,
                    "Failed to register ",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
    public Student student;
    private Student addStudentUserToDatabase(String name, String id, String fName,String mName,String email, String password,String branch, String year, String address, String phone){
        Student student = null;
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try{
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO students (name,id,fName,mName,email,password,branch,year,address,phone)" ;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,id);
            preparedStatement.setString(3,fName);
            preparedStatement.setString(4,mName);
            preparedStatement.setString(5,email);
            preparedStatement.setString(6,password);
            preparedStatement.setString(7,branch);
            preparedStatement.setString(8,year);
            preparedStatement.setString(9,address);
            preparedStatement.setString(10,phone);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows>0){
                student = new Student();
                student.name = name;
                student.id = id;
                student.fName = fName;
                student.mName = mName;
                student.email = email;
                student.password = password;
                student.branch = branch;
                student.year = year;
                student.address = address;
                student.phone = phone;

            }
            stmt.close();
            conn.close();

        }catch (Exception e ){
            e.printStackTrace();
        }

        return student;
    }

    public static void main(String[] args){
        StudentRegistration myForm = new StudentRegistration(null);
        Student student = myForm.student;
        if (student!=null){
            System.out.println("Successfully Registration" + student.name);
        }else{
            System.out.println("Registration cancelled");
        }
    }


}
