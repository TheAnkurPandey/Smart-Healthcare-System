import java.sql.Time;
import java.util.Date;

public class Record {
    private String patientID;
    private String appointmentID;
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

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
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


}
