import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;


public class Admin extends Users {

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
            System.out.println("Enter the name:");
            String name = reader.readLine();
            System.out.println("Enter the dateOfBirth:");
            String dateOfBirth = reader.readLine();
            LocalDate todaysDate = LocalDate.now();
            LocalDate dOfBirth = LocalDate.parse(dateOfBirth);
            Period differenceInDates = Period.between(dOfBirth,todaysDate);
            Integer age = differenceInDates.getYears();//scanner.nextInt();
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
            Boolean isValidInterval = false;
            LocalTime inOPD = LocalTime.now(),outOPD = LocalTime.now(),inLocal,outLocal;
            String inTimeOPD="",inTimeLocal="",outTimeOPD="",outTimeLocal="";
            while (!isValidInterval) {
                System.out.println("Enter the inTimeOPD:");
                 inTimeOPD = reader.readLine();
                inOPD = LocalTime.parse(inTimeOPD);
                System.out.println("Enter the outTimeOPD:");
                 outTimeOPD = reader.readLine();
                outOPD = LocalTime.parse(outTimeOPD);

                if(inOPD.isBefore(outOPD))
                    isValidInterval = true;
                else
                {
                    System.out.println("Enter correct timings!");
                }

            }
            Boolean isNonIntersecting = false;
            while(!isNonIntersecting) {
                System.out.println("Enter the inTimeLocal:");
                 inTimeLocal = reader.readLine();
                inLocal = LocalTime.parse(inTimeLocal);
                System.out.println("Enter the outTimeLocal:");
                 outTimeLocal = reader.readLine();
                outLocal = LocalTime.parse(outTimeLocal);

                if(inLocal.isBefore(outLocal)&&((inLocal.isBefore(inOPD)&&outLocal.isBefore(inOPD))||(inLocal.isAfter(outOPD)&&outLocal.isAfter(outOPD))))
                {
                    isNonIntersecting = true;
                }
                else
                {
                    System.out.println("Enter correct timings!");
                }
            }

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
            System.out.println("Enter the departmentID:");
            String departmentID = reader.readLine();
            Boolean isDesignationValid = false;
            String designation = "";
            while(!isDesignationValid) {

                System.out.println("Enter the designation:");
                 designation = reader.readLine();
                if(designation.equalsIgnoreCase("HOD"))
                {
                    Statement statement = SHS.connection.createStatement();
                    String query = "select hodid from department where id = "+departmentID+" AND hodid is null;";
                    ResultSet resultSet = statement.executeQuery(query);
                    if(resultSet.next())
                    {
                        //the department doesn't have and hod there we can assign HOD to it.
                        query = "select * from doctor order by id desc";
                        resultSet = statement.executeQuery(query);
                        int doctorID = 0;
                        if(resultSet.next())
                        {
                            doctorID = resultSet.getInt("id");
                            doctorID += 1;
                        }

                        query = "update department set hodid = "+doctorID+" where id = "+departmentID+";";
                        int rowCount = statement.executeUpdate(query);
                        if(rowCount>0)
                        {
                            System.out.println("Department with id : "+departmentID+" has been assigned an HOD" );
                        }
                        isDesignationValid = true;
                    }
                    else
                    {
                        isDesignationValid = false;
                        System.out.println("The department already have an HOD you can't add another!");
                    }

                }
                else
                    isDesignationValid = true;

                }

                Statement statement = SHS.connection.createStatement();

                     String  query = "insert into doctor values(NULL,'" + name + "','" + dateOfBirth + "','" + age + "','" + gender + "','" + phoneNumber + "','" + email + "','" + password + "','"
                                + address + "','" + schedule + "','" + inTimeOPD + "','" + outTimeOPD + "','" + specialisation + "','" + isSurgeonStatus + "','" + designation + "','"+departmentID+"','" + inTimeLocal + "','" + outTimeLocal + "');";
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
    //reassign a doctor to a patient in the appointment by deleting previous appointment and creating a new one on that same day
    public void  reassignDoctorToPatient() {//
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String appointmentID="",doctorID="",query="",departmentCode="";
            int tokenNumber = 0,departmentID=0,patientID=0;
            LocalDate dateOfAppointment = LocalDate.now();
            ResultSet resultSet;
            Boolean isAppointmentIDValid = false;
            Boolean isDoctorIDValid = false;
            Statement statement = SHS.connection.createStatement();
            while (!(isDoctorIDValid&&isAppointmentIDValid)) {


                if(!isAppointmentIDValid) {
                    System.out.println("Enter the appointment ID:");
                    appointmentID = reader.readLine();
                     query = "Select * from appointment where id = '" + appointmentID + "' AND ispatientattended = '0';";
                    resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        isAppointmentIDValid = true;
                        String date = resultSet.getString("dateofappointment");
                        dateOfAppointment = LocalDate.parse(date);
                        patientID = resultSet.getInt("patient");
                    }
                    else
                    {
                        query = "Select * from appointment where id = '" + appointmentID + "';";
                        resultSet = statement.executeQuery(query);
                        if (resultSet.next())
                        {
                            //appointment id exists but some doctor has already treated the patient.
                        }
                        else
                        {
                            System.out.println("Patient appointment is already marked done.Cannot reassign!");
                            return;
                        }
                    }
                }

                 if(!isDoctorIDValid) {
                     System.out.println("Enter the doctor ID:");
                     doctorID = reader.readLine();

                     query = "Select * from doctor  where id = '" + doctorID + "';";

                     resultSet  = statement.executeQuery(query);
                     if (resultSet.next()) {
                         isDoctorIDValid = true;
                         departmentID = resultSet.getInt("departmentID");

                         query = "select code from department where id="+departmentID+";";

                         resultSet = statement.executeQuery(query);
                         if(resultSet.next())
                             departmentCode = resultSet.getString("code");

                         query = "select tokennumber from appointment where doctor = '"+doctorID+"' AND dateofappointment = '"+dateOfAppointment+"' order by tokennumber desc;";

                         resultSet = statement.executeQuery(query);
                         if(resultSet.next()) {
                             tokenNumber = resultSet.getInt(1);
                         }

                     }
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
                String isCritical = resultSet.getString("iscritical");
                String symptomps = resultSet.getString("symptoms");
                String location =  resultSet.getString("location");

                query = "delete from appointment where id ='"+appointmentID+"';";
                statement.executeUpdate(query);

                //APPOINTMENT_ID is a place holder and will be removed by meaningfulID
                query = "insert into appointment values ('APPOINTMENT_ID',NULL,'"+patientID+"','"+doctorID+"','"+dateOfAppointment+"','"+(tokenNumber+1)+"','"+isCritical+"','"+symptomps+"','"+location+"','0','"+departmentID+"');";

                int rowCount = statement.executeUpdate(query);
                if(rowCount>0)
                {
                    int serialNumber = -1;

                    query = "select sno from appointment  where doctor = '"+doctorID+"' AND dateofappointment = '"+dateOfAppointment+"' AND ispatientattended='0'  AND patient = '"+patientID+"' AND tokennumber = "+(tokenNumber+1)+";";

                    resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        serialNumber = resultSet.getInt("sno");
                    } else {
                        // throw an exception from here
                    }
                    appointmentID = departmentCode + serialNumber;

                    query = "UPDATE appointment SET id = '"+appointmentID+"' WHERE SNO="+serialNumber+"; ";

                    int j=statement.executeUpdate(query);
                    if(j==1) {
                        System.out.println("doctor with id:"+doctorID+" has been assigned to patient with appointment id:"+appointmentID);
                    }
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
