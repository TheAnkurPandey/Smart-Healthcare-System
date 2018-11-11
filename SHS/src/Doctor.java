import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalTime;

public class Doctor {

    private String name;
    private String dateOfBirth;
    private Integer age;
    private String gender;
    private Long phoneNumber;
    private String email;
    private String password;
    private String doctorID;
    private String[] schedule;
    private String address;
    private LocalTime inTimeOPD;
    private LocalTime outTimeOPD;
    private Boolean isSurgeon;
    private String specialisation;

    /*insert into doctor values('1','Aman','1999-03-20','23','M','9818123456','aman@gmail.com','amanKiAsha','Delhi','Monday,Tuesday','09:00','11:00','alpha','1','Senior Doctor');*/
    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String[] getSchedule() {
        return schedule;
    }

    public void setSchedule(String[] schedule) {
        this.schedule = schedule;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalTime getInTimeOPD() {
        return inTimeOPD;
    }

    public void setInTimeOPD(LocalTime inTimeOPD) {
        this.inTimeOPD = inTimeOPD;
    }

    public LocalTime getOutTimeOPD() {
        return outTimeOPD;
    }

    public void setOutTimeOPD(LocalTime outTimeOPD) {
        this.outTimeOPD = outTimeOPD;
    }

    public Boolean getSurgeon() {
        return isSurgeon;
    }

    public void setSurgeon(Boolean surgeon) {
        isSurgeon = surgeon;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }


    Doctor() {
    }

    public Doctor(String name, String dateOfBirth, Integer age, String gender, Long phoneNumber, String email, String password, String doctorID, String[] schedule, String address, LocalTime inTimeOPD, LocalTime outTimeOPD, Boolean isSurgeon, String specialisation) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.doctorID = doctorID;
        this.schedule = schedule;
        this.address = address;
        this.inTimeOPD = inTimeOPD;
        this.outTimeOPD = outTimeOPD;
        this.isSurgeon = isSurgeon;
        this.specialisation = specialisation;
    }

    //Create a new Record for the patient
    public void createRecord() {

    }


    //Shows all the history corresponding to a given patient ID
    public void viewPatienHistory(String patientID) {

    }

    public Boolean successfulDoctorLogin() {
        System.out.println(String.format("%90s","Doctor Login"));
        Connection connection ;
        Statement statement ;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartHealthcareSystem?verifyServerCertificate=false&useSSL=true", "rjtmhy", "#Rjtmhy25");
            statement = connection.createStatement();
            String myQuery;
            Boolean isloginSuccesful = false;

            while (!isloginSuccesful) {
                System.out.println("Enter the username:");
                String username = reader.readLine();
                System.out.println("Enter the password:");
                String password = reader.readLine();

                myQuery = "select * from doctor where id = '" + username + "' and password = '" + password + "';";

                ResultSet queryResult = statement.executeQuery(myQuery);

                if (queryResult.next()) {
                    System.out.println("Access Granted!");
                    return true;
                } else {
                    System.out.println("Wrong username or password!");
                }
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException! " + e.getMessage());
        }

        return false;
    }

    //sort the list of patients assigned to doctor by IDs
    public void sortListOfAssignedPatientByID() {

    }

    //sort the list of patients assigned to doctor by their names
    public void sortListOfAssignedPatientByName() {

    }
    
    public void editProfile() {

    }

    //treats the patient
    public void treats()
    {

    }

    //gets the list of patients assigned to the doctor
    public void getListOfAssignedPatient(Doctor doctor)
    {

    }
}

