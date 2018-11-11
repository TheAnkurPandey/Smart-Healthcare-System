import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SHS {

    static void drawLineSeparator()
    {
        for (int i = 0; i <170; i++) {
            System.out.print("\u2500");
        }
        System.out.println();
    }

    static void printOptionsList(String title,String[] listOfOptions)
    {
        SHS.drawLineSeparator();
        System.out.println(String.format("%90s",title));
        SHS.drawLineSeparator();
        if(listOfOptions!=null&&listOfOptions.length!=0)//short-circuit
        {
            for (String option:listOfOptions) {
                System.out.println(option);
            }
        }
    }

    static String[] csvStringToStringArray(String string)
    {
        String [] stringArray;
       if(string!=null)
       {
           stringArray = string.split(",");
           return  stringArray;
       }
       else
       {
           System.out.println("String is empty");
           return  new String[]{""};
       }

    }

    public static void main(String[] args)
    {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int choice = 0;
        int selectedOption = 0;
        while(choice != -1) {
            try {
                SHS.printOptionsList("Select the account type:",new String[]{"For Administrator: Press 1 ","For Patient : Press 2  ","For Doctor : Press 3","\n To exit from the software : Press -1"});
                choice = Integer.parseInt(reader.readLine());
                switch(choice)
                {
                    //Administrator Options:
                    case 1: Admin administrator = new Admin();
                            if(administrator.successfulAdminLogin()) {
                                selectedOption = 0;
                                while (selectedOption != -1) {
                                    SHS.printOptionsList("Administrator Account Options",
                                            new String[]{"1.Add Doctor", "2.Reassign doctor to patient","3.View Patient Details","4.View Doctor Details","5.Log out"});
                                    System.out.println("Enter an option number:");
                                    selectedOption = Integer.parseInt(reader.readLine());
                                    switch (selectedOption) {
                                        case 1://Add Doctor
                                            administrator.addDoctorInSHS();
                                            break;
                                        case 2://Reassign doctor to patient
                                            break;
                                        case 3://View Patient Details
                                            administrator.veiwPatientDetails();
                                            break;
                                        case 4://View Doctor Details
                                            administrator.viewDoctorDetails();
                                            break;
                                        case 5://logout
                                            selectedOption = -1;
                                            break;
                                        default:
                                            System.out.println("Enter the correct option:");
                                            break;
                                    }
                                }
                            }
                            else
                            {
                                System.out.println("Too many wrong attempts. Please try after some time.");
                            }
                        break;
                     // Patient options
                    case 2: Patient patient = new Patient();
                            patient.patientLogin();
                        break;
                     //Doctor options
                    case 3: Doctor doctor = new Doctor();
                            if(doctor.successfulDoctorLogin())
                            {
                                selectedOption = 0;
                                while (selectedOption != -1) {
                                    SHS.printOptionsList("Doctor Account Options",
                                            new String[]{"1.Create a new record", "2.Treat patient","3.View patient History","4.Get list of patients assigned",
                                                    "5.Sort list of assigned patients by ID","6.Sort list of assigned patients by Name","7.Edit profile","8.Logout"});
                                    System.out.println("Enter an option number:");
                                    selectedOption = Integer.parseInt(reader.readLine());
                                    switch (selectedOption) {
                                        case 1:// Create a new record
                                            break;
                                        case 2:// Treat patient
                                            break;
                                        case 3:// View patient History
                                            break;
                                        case 4://Get list of patients assigned
                                            break;
                                        case 5://Sort list of assigned patients by ID
                                            break;
                                        case 6://Sort list of assigned patients by Name
                                            break;
                                        case 7://Edit profile
                                            break;
                                        case 8://Logout
                                            selectedOption = -1;
                                            break;
                                        default:
                                            System.out.println("Enter the correct option:");
                                            break;
                                    }
                                }
                            }
                            else
                            {
                                System.out.println("Too many wrong attempts. Please try after some time.");
                            }
                        break;
                    case -1:
                        System.out.println("SHS application is closed!");
                        break;
                    default:
                        System.out.println("Please enter a correct choice:!");
                        break;
                }
            } catch (IOException exception) {
                System.out.println("Please enter a valid option!");
            }
        }
    }

}

