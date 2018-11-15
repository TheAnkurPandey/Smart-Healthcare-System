import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;


public class Admin {

    public Boolean successfulAdminLogin() {
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int count = 0;
            while (count < 3) {
                System.out.println("Enter the username:");
                String username = reader.readLine();
                System.out.println("Enter the password:");
                String password = reader.readLine();
                if (username.equals("oopd") && password.equals("project")) {
                    System.out.println("Login successful!");
                    return true;
                }
                else
                {
                    System.out.println("Wrong username or password. Please try again!");
                }
                count++;
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException message: "+e.getMessage());
        }
        return false;
    }

    public void addDoctorInSHS() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(String.format("%90s", "Enter the details of the doctor:"));
            Boolean isRegistrationSuccessful = false;
//            System.out.println("Enter the doctorID:");
//            String doctorID = reader.readLine();
            System.out.println("Enter the name:");
            String name = reader.readLine();
            System.out.println("Enter the dateOfBirth:");
            String dateOfBirth = reader.readLine();
            System.out.println("Enter the age:");
            Integer age = Integer.parseInt(reader.readLine());//scanner.nextInt();
            System.out.println("Enter the gender:");
            String gender = reader.readLine();
            System.out.println("Enter the phoneNumber:");
            Long phoneNumber = Long.parseLong(reader.readLine());//scanner.nextLong();
            System.out.println("Enter the email:");
            String email = reader.readLine();
            System.out.println("Enter the password:");
            String password = reader.readLine();
            System.out.println("Enter the address:");
            String address = reader.readLine();
            System.out.println("Enter the schedule:");
            String schedule = reader.readLine();
            System.out.println("Enter the inTimeOPD:");
            String inTimeOPD = reader.readLine();
            System.out.println("Enter the outTimeOPD:");
            String outTimeOPD = reader.readLine();
            System.out.println("Enter the specialisation of the Doctor:");
            String specialisation = reader.readLine();
            System.out.println("Is the doctor a Surgeon, if yes then enter Yes or Y else enter No or N ");
            String isSurgeon = reader.readLine();
            int isSurgeonStatus = 0;
            if (isSurgeon.equalsIgnoreCase("Yes") || isSurgeon.equalsIgnoreCase("Y")) {
                isSurgeonStatus = 1;
            } else if (isSurgeon.equalsIgnoreCase("No") || isSurgeon.equalsIgnoreCase("N")) {
                isSurgeonStatus = 0;
            }

            System.out.println("Enter the designation:");
            String designation = reader.readLine();
            System.out.println("Enter the departmentID:");
            String departmentID = reader.readLine();

                Statement statement = SHS.connection.createStatement();

                     String  query = "insert into doctor values(NULL,'" + name + "','" + dateOfBirth + "','" + age + "','" + gender + "','" + phoneNumber + "','" + email + "','" + password + "','"
                                + address + "','" + schedule + "','" + inTimeOPD + "','" + outTimeOPD + "','" + specialisation + "','" + isSurgeonStatus + "','" + designation + "','"+departmentID+"');";
                        statement.executeUpdate(query);

                    System.out.println("Doctor successfully registered!");


        }
         catch (SQLException e) {
        System.out.println(e.getMessage());
         }catch (IOException e) {
            System.out.println("Please enter a valid input!" + e.getMessage());
        }

    }

    //appointment insertion syntax :  insert into appointment values ('11','rajat','1995','2018-10-02','1212','Yes','Fever,Body ache,Restlessness','Delhi','0','1');
    //reassign a doctor to a patient in the appointment
    public void  reassignDoctorToPatient() {//ms..tested//As per gaurav I have to ask only with appointmentid
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String appointmentID="",doctorID="",query="";
            ResultSet resultSet;
            Boolean isAppointmentIDValid = false;
            Boolean isDoctorIDValid = false;
            Statement statement = SHS.connection.createStatement();
            while (!(isDoctorIDValid&&isAppointmentIDValid)) {//infinite


                if(!isAppointmentIDValid) {
                    System.out.println("Enter the appointment ID:");
                    appointmentID = reader.readLine();
                     query = "Select * from appointment where id = '" + appointmentID + "';";
                    resultSet = statement.executeQuery(query);
                    if (resultSet.next())
                        isAppointmentIDValid = true;
                }

                 if(!isDoctorIDValid) {
                     System.out.println("Enter the doctor ID:");
                     doctorID = reader.readLine();
                     query = "Select * from doctor  where id = '" + doctorID + "';";
                     resultSet  = statement.executeQuery(query);
                     if (resultSet.next())
                         isDoctorIDValid = true;
                 }

                 if(!isDoctorIDValid)
                     System.out.println("incorrect doctorID");
                 if(!isAppointmentIDValid)
                    System.out.println("incorrect appointmentID");

                 if(!isDoctorIDValid||!isAppointmentIDValid) {
                     System.out.println("Do you want to try again. Press Yes/Y/yes try again else press NO/no/N");
                     String response = reader.readLine();
                     if (response.equalsIgnoreCase("No") || response.equalsIgnoreCase("N"))
                         return;
                 }
            }

            query = "Select * from appointment where id = '"+appointmentID+"';";
            resultSet = statement.executeQuery(query);
            if(resultSet.next())
            {

                query = "update appointment set doctor = '"+doctorID+"' where id = '"+appointmentID+"';";
                int rowCount =  statement.executeUpdate(query);

                if(rowCount>=1)
                {
                    System.out.println("doctor with id:"+doctorID+" has been assigned to patient with appointment id:"+appointmentID);
                }
            }
            else
            {
                System.out.println("There is no appointment for this patient!");
            }
        }
        catch (SQLException e) {
        System.out.println("SQLException: " + e.getMessage());
    }  catch (IOException e) {
        System.out.println("IOException! " + e.getMessage());
    }

    }

    public void veiwPatientDetails() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Statement statement = SHS.connection.createStatement();
            String myQuery;

            System.out.println("Enter the patient ID:");
            String doctorID = reader.readLine();
            myQuery = "select * from patient where id = '" + doctorID + "';";
            ResultSet queryResult = statement.executeQuery(myQuery);

            if (queryResult.next()) {
                System.out.println(String.format("%-40s","patientID:")+String.format("%20s",queryResult.getString(1)));
                System.out.println(String.format("%-40s","name:")+String.format("%20s",queryResult.getString(2)));
                System.out.println(String.format("%-40s","dateOfBirth:")+String.format("%20s",queryResult.getDate(3)));
                System.out.println(String.format("%-40s","age:")+String.format("%20s",queryResult.getInt(4)));
                System.out.println(String.format("%-40s","gender:")+String.format("%20s",queryResult.getString(5)));
                System.out.println(String.format("%-40s","phoneNumber:")+String.format("%20s",queryResult.getString(6)));
                System.out.println(String.format("%-40s","email:")+String.format("%20s",queryResult.getString(7)));
                System.out.println(String.format("%-40s","password:")+String.format("%20s",queryResult.getString(8)));
                System.out.println(String.format("%-40s","address:")+String.format("%20s",queryResult.getString(9)));

            } else {
                System.out.println("Patient record not found!");
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }catch (IOException e) {
            System.out.println("IOException! " + e.getMessage());
        }
    }

    public void viewDoctorDetails() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
              Statement statement = SHS.connection.createStatement();
            String myQuery;

                System.out.println("Enter the doctor ID:");
                String doctorID = reader.readLine();
                myQuery = "select * from doctor where id = '" + doctorID + "';";
                ResultSet queryResult = statement.executeQuery(myQuery);

                if (queryResult.next()) {
                    System.out.println(String.format("%-40s","doctorID:")+String.format("%20s",queryResult.getInt(1)));
                    System.out.println(String.format("%-40s","name:")+String.format("%20s",queryResult.getString(2)));
                    System.out.println(String.format("%-40s","dateOfBirth:")+String.format("%20s",queryResult.getDate(3)));
                    System.out.println(String.format("%-40s","age:")+String.format("%20s",queryResult.getInt(4)));
                    System.out.println(String.format("%-40s","gender:")+String.format("%20s",queryResult.getString(5)));
                    System.out.println(String.format("%-40s","phoneNumber:")+String.format("%20s",queryResult.getString(6)));
                    System.out.println(String.format("%-40s","email:")+String.format("%20s",queryResult.getString(7)));
                    System.out.println(String.format("%-40s","password:")+String.format("%20s",queryResult.getString(8)));
                    System.out.println(String.format("%-40s","address:")+String.format("%20s",queryResult.getString(9)));
                    System.out.println(String.format("%-40s","schedule:")+String.format("%20s",queryResult.getString(10)));
                    System.out.println(String.format("%-40s","inTimeOPD:")+String.format("%20s",queryResult.getTime(11)));
                    System.out.println(String.format("%-40s","outTimeOPD:")+String.format("%20s",queryResult.getTime(12)));
                    System.out.println(String.format("%-40s","specialisation of the Doctor:")+String.format("%20s",queryResult.getString(13)));
                    System.out.println(String.format("%-40s","is Surgeon:")+String.format("%20s",queryResult.getBoolean(14)));
                    System.out.println(String.format("%-40s","designation:")+String.format("%20s",queryResult.getString(15)));
                    System.out.println(String.format("%-40s","department ID:")+String.format("%20s",queryResult.getInt(16)));

                } else {
                    System.out.println("Doctor record not found!");
                }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }  catch (IOException e) {
            System.out.println("IOException! " + e.getMessage());
        }
    }
}
