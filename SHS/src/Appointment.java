import java.util.Date;
//declare enum
public class Appointment {
    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Boolean getPatientAttendedAppointment() {
        return patientAttendedAppointment;
    }

    public void setPatientAttendedAppointment(Boolean patientAttendedAppointment) {
        this.patientAttendedAppointment = patientAttendedAppointment;
    }

    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    public String[] getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String[] symptoms) {
        this.symptoms = symptoms;
    }

    private String appointmentID;
    private Boolean patientAttendedAppointment;
    private Date dateOfAppointment;
    private  String patientID;
    private String doctorID;
    private String departmentID;
    private int tokenNumber;
    //private enum{OPD,GENERAL} location;
    private boolean isCritical;
    private  String[] symptoms;
}
