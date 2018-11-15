public class Specialist extends Doctor implements Delegation {
    @Override
    public void referPatient(int doctorsID, int referredDoctorsID, String patientID) {
        System.out.println("Specialist "+doctorsID+"patient "+patientID+" is referred to "+referredDoctorsID);
    }

    public void referToAnotherDepartment(int referredDepartmentID,int referredDoctorsID,String patientID)
    {

    }
}
