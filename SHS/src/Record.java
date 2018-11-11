import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Record {
    private String patientID;
    private int recordID;
    private int appointmentID;
    private Time timeOfVisit;
    private Date dayOfVisit;
    private String departmentID;
    private Time timeOfDischarge;
    private Date dayOfDischarge;
    private String[] doctorsID;
    private String[] diseaseIdentified;
    private String[] medicinesPrescribed;
    private String[] testsAdvised;

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Time getTimeOfVisit() {
        return timeOfVisit;
    }

    public void setTimeOfVisit(Time timeOfVisit) {
        this.timeOfVisit = timeOfVisit;
    }

    public Date getDayOfVisit() {
        return dayOfVisit;
    }

    public void setDayOfVisit(Date dayOfVisit) {
        this.dayOfVisit = dayOfVisit;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public Time getTimeOfDischarge() {
        return timeOfDischarge;
    }

    public void setTimeOfDischarge(Time timeOfDischarge) {
        this.timeOfDischarge = timeOfDischarge;
    }

    public Date getDayOfDischarge() {
        return dayOfDischarge;
    }

    public void setDayOfDischarge(Date dayOfDischarge) {
        this.dayOfDischarge = dayOfDischarge;
    }

    public String[] getDoctorsID() {
        return doctorsID;
    }

    public void setDoctorsID(String[] doctorsID) {
        this.doctorsID = doctorsID;
    }

    public String[] getDiseaseIdentified() {
        return diseaseIdentified;
    }

    public void setDiseaseIdentified(String[] diseaseIdentified) {
        this.diseaseIdentified = diseaseIdentified;
    }

    public String[] getMedicinesPrescribed() {
        return medicinesPrescribed;
    }

    public void setMedicinesPrescribed(String[] medicinesPrescribed) {
        this.medicinesPrescribed = medicinesPrescribed;
    }

    public String[] getTestsAdvised() {
        return testsAdvised;
    }

    public void setTestsAdvised(String[] testsAdvised) {
        this.testsAdvised = testsAdvised;
    }


    //prints the record of the patient with patientID
    void getRecord(String patientID)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartHealthcareSystem?verifyServerCertificate=false&useSSL=true","rjtmhy","#Rjtmhy25");
            Statement statement = connection.createStatement();
            String myQuery;

                myQuery = "select * from record where id = '" + patientID+ "';";

                ResultSet queryResult = statement.executeQuery(myQuery);

                if (queryResult.next()) {
                    System.out.println("Record:");
                    recordID = queryResult.getInt("id");
                    dayOfVisit = queryResult.getDate("dayofvisit");
                    dayOfDischarge = queryResult.getDate("dayofdischarge");
                    diseaseIdentified = SHS.csvStringToStringArray(queryResult.getString("diseaseidentified"));
                    medicinesPrescribed = SHS.csvStringToStringArray(queryResult.getString("medicineprescribed"));
                    testsAdvised = SHS.csvStringToStringArray(queryResult.getString("testadviced"));
                    this.patientID = queryResult.getString("patient");
                    appointmentID = queryResult.getInt("appointment");
                } else {
                    System.out.println("Record not found!");
                }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}

