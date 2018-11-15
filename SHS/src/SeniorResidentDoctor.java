public class SeniorResidentDoctor extends Doctor implements Delegation {

    @Override
    public void referPatient(int doctorsID, int referredDoctorsID, String patientID) {
        System.out.println("SeniorResidentDoctor "+doctorsID+"patient "+patientID+" is referred to "+referredDoctorsID);
    }
}
