import Middleware.Logger;
import Middleware.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public class Doctor extends Users{

    private String name;
    private String dateOfBirth;
    private Integer age;
    private String gender;
    private Long phoneNumber;
    private String email;
    private String password;
    private int doctorID;
    private String[] schedule;
    private String address;
    private LocalTime inTimeOPD;
    private LocalTime outTimeOPD;
    private LocalTime inTimeLocal;
    private LocalTime outTimeLocal;
    private Boolean isSurgeon;
    private String specialisation;
    private String designation;
    private int departmentID;
    /*insert into doctor values('1','Aman','1999-03-20','23','M','9818123456','aman@gmail.com','amanKiAsha','Delhi','Monday,Tuesday','09:00','11:00','alpha','1','Senior Doctor');*/
    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
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

    public LocalTime getInTimeLocal() {
        return inTimeLocal;
    }

    public void setInTimeLocal(LocalTime inTimeLocal) {
        this.inTimeLocal = inTimeLocal;
    }

    public LocalTime getOutTimeLocal() {
        return outTimeLocal;
    }

    public void setOutTimeLocal(LocalTime outTimeLocal) {
        this.outTimeLocal = outTimeLocal;
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
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }


    enum DOCTOR_TYPE {JUNIOR_DOCTOR,SENIOR_DOCTOR,SPECIALIST,SENIOR_SPECIALIST}
    enum LOCATION {OPD,LOCAL};

    Doctor() {
    }

    public Doctor(String name, String dateOfBirth, Integer age, String gender, Long phoneNumber, String email, String password, int doctorID, String[] schedule, String address, LocalTime inTimeOPD, LocalTime outTimeOPD, Boolean isSurgeon, String specialisation,String designation,int departmentID,LocalTime inTimeLocal,LocalTime outTimeLocal) {
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
        this.designation = designation;
        this.departmentID = departmentID;
        this.inTimeLocal = inTimeLocal;
        this.outTimeLocal = outTimeLocal;
    }

    //Create a new Record for the patient
    public void createRecord() {
        try {//to do take date as input from the user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(String.format("%90s", "Enter the following details to create the record:"));
            Boolean isRecordCreationSuccessful = false;
            String dayOfVisit = LocalDate.now().toString();
            if(!Validator.isValidDate(dayOfVisit))
            {
                System.out.println("Incorrect date format");
                return;
            }
            System.out.println("Enter the day of discharge:");
            String dayOfDischarge = reader.readLine();
            if(!Validator.isValidDate(dayOfDischarge))
            {
                System.out.println("Incorrect date format");
                return;
            }
            System.out.println("Enter diseases identified(as comma separated values):");
            String diseasesIdentified = reader.readLine();//scanner.nextInt();
            System.out.println("Enter the medicines being prescribed(as comma separated values):");
            String medicinePrescribed = reader.readLine();
            System.out.println("Enter the tests that are advised(as comma separated values):");
            String testsAdviced = reader.readLine();
            System.out.println("Enter the patientID:");
            String patientID = reader.readLine();
            if(!Validator.isValidPatientId(patientID))
            {
                System.out.println("Wrong patient ID");
                return;
            }
            System.out.println("Enter the appointmentID:");
            String appointmentID = reader.readLine();
              
            Statement statement = SHS.connection.createStatement();

                String query = "Select * from patient where id ='"+patientID+"';";
                ResultSet resultSet = statement.executeQuery(query);
                if(!resultSet.next())
                {
                    System.out.println("There is no patient with patient ID:"+patientID);
                    System.out.println("Please,enter correct patient ID:");
                    patientID = reader.readLine();

                }
                //here patient is the patient ID:
                query = "Select * from appointment where patient ='"+patientID+"' AND id ='"+appointmentID+"';";
                resultSet = statement.executeQuery(query);
                if(!resultSet.next())
                {
                    System.out.println("There is no appointment for patient with patient ID:"+patientID);
                    System.out.println("Please,enter correct appointment ID:");
                    appointmentID = reader.readLine();
                     
                }


                    query = "insert into record values(NULL,'" + dayOfVisit + "','" + dayOfDischarge + "','" + diseasesIdentified + "','" + medicinePrescribed + "','" + testsAdviced + "','" + patientID + "','" + appointmentID + "');";
                    int row = statement.executeUpdate(query);
                    if(row>0)
                    isRecordCreationSuccessful = true;


            if (isRecordCreationSuccessful)
                System.out.println("Record successfully created!");


        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.log(e.getMessage());
        }catch (IOException e) {
            System.out.println("Please enter a valid input!" + e.getMessage());
            Logger.log(e.getMessage());
        }

    }


    //Shows all the history corresponding to a given patient ID i.e. ALL THE previous patient records.
    public void viewPatientHistory()
    {
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the patient ID:");
            String patientID = reader.readLine();
            if(!Validator.isValidPatientId(patientID))
            {
                System.out.println("Wrong patient ID");
                return;
            }
            Statement statement = SHS.connection.createStatement();

                String query = "Select * from record where patient = '" + patientID+ "'";
                ResultSet resultSet = statement.executeQuery(query);
                Boolean isHistoryPresent = false;
                    for (int i = 1;resultSet.next();i++) {

                        System.out.println(String.format("%30s","-"+i+"-"));

                        System.out.println(String.format("%-40s", "record ID:") + String.format("%20s", resultSet.getString(1)));
                        System.out.println(String.format("%-40s", "day of visit:") + String.format("%20s", resultSet.getDate(2)));
                        System.out.println(String.format("%-40s", "day of discharge:") + String.format("%20s", resultSet.getDate(3)));
                        System.out.println(String.format("%-40s", "diseases identified:") + String.format("%20s", resultSet.getString(4)));
                        System.out.println(String.format("%-40s", "medicine prescribed:") + String.format("%20s", resultSet.getString(5)));
                        System.out.println(String.format("%-40s", "test adviced:") + String.format("%20s", resultSet.getString(6)));
                        System.out.println(String.format("%-40s", "patient ID:") + String.format("%20s", resultSet.getString(7)));
                        System.out.println(String.format("%-40s", "appointment ID:") + String.format("%20s", resultSet.getString(8)));
                        isHistoryPresent= true;
                    }
                    if(!isHistoryPresent)
                    {
                        System.out.println("There is no history for the patient with ID:"+patientID);
                    }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception"+e.getMessage());
            Logger.log(e.getMessage());
        }catch (IOException e) {
            System.out.println("Please enter a valid input!" + e.getMessage());
            Logger.log(e.getMessage());
        }
    }

    public Boolean successfulDoctorLogin() {
        System.out.println(String.format("%90s","Doctor Login"));
        Connection connection ;
        Statement statement ;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {

            connection = SHS.connection;
            statement = connection.createStatement();
            String myQuery;
            int count = 0;
            while (count < 3) {
                System.out.println("Enter the username:");
                String username = reader.readLine();
                System.out.println("Enter the password:");
                String password = reader.readLine();
                if(!Validator.isValidPassword(password))
                {
                    System.out.println("Invalid password");
                    return false;
                }
                myQuery = "select * from doctor where id = '" + username + "' and password = '" + password + "';";

                ResultSet queryResult = statement.executeQuery(myQuery);

                if (queryResult.next()&&(username.equals(queryResult.getString("id"))&&password.equals(queryResult.getString("password")))) {
                    System.out.println("Access Granted!");
                    this.doctorID =queryResult.getInt(1);
                    this.name=queryResult.getString(2);
                    this.dateOfBirth= queryResult.getDate(3).toString();
                    this.age=queryResult.getInt(4);
                    this.gender=queryResult.getString(5);
                    this.phoneNumber=Long.parseLong(queryResult.getString(6));
                    this.email=queryResult.getString(7);
                    this.password=queryResult.getString(8);
                    this.address=queryResult.getString(9);
                    this.schedule=SHS.csvStringToStringArray(queryResult.getString(10));
                    this.inTimeOPD=queryResult.getTime(11).toLocalTime();
                    this.outTimeOPD=queryResult.getTime(12).toLocalTime();
                    this.specialisation=queryResult.getString(13);
                    this.isSurgeon=queryResult.getBoolean(14);
                    this.designation=queryResult.getString(15);
                    this.departmentID=queryResult.getInt(16);
                    this.inTimeLocal=queryResult.getTime(17).toLocalTime();
                    this.outTimeLocal=queryResult.getTime(18).toLocalTime();
                    return true;
                } else {
                    System.out.println("Wrong username or password!");
                }
                count++;
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            Logger.log(e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException! " + e.getMessage());
            Logger.log(e.getMessage());
        }

        return false;
    }


    //name ,address, phone number can be modified..tested
    public void editProfile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Boolean isNotWillingToModify = false;
            while (!isNotWillingToModify) {
                System.out.println("Following attributes can be modified :");
                System.out.println("1.Name\n2.Address\n3.Phone Number");
                System.out.println("Enter the number of attribute that you want to change");
                int choice = Integer.parseInt(reader.readLine());
                String input,query;
                Statement statement;
                switch (choice)
                {
                    case 1:
                        System.out.println("Enter new name:");
                        input = reader.readLine();
                        if(!Validator.isValidFullName(input))
                        {
                            System.out.println("Invalid name");
                            return;
                        }
                        statement = SHS.connection.createStatement();
                        query = "update doctor set name ='"+input+"' where id ='"+doctorID+"';";
                        int rowCount = statement.executeUpdate(query);
                        if(rowCount>=1)
                            System.out.println("Name successfully updated!");
                        break;
                    case 2:
                        System.out.println("Enter new address:");
                        input = reader.readLine();
                        statement = SHS.connection.createStatement();
                        query = "update doctor set address ='"+input+"' where id ='"+doctorID+"';";
                        rowCount = statement.executeUpdate(query);
                        if(rowCount>=1)
                            System.out.println("Address successfully updated!");
                        break;
                    case 3:
                        System.out.println("Enter new phone number:");
                        input = reader.readLine();
                        if(!Validator.isValidPhoneNumber(input))
                        {
                            System.out.println("Invalid phone number");
                            return;
                        }
                        statement = SHS.connection.createStatement();
                        query = "update doctor set phonenumber ='"+input+"' where id ='"+doctorID+"';";
                        rowCount = statement.executeUpdate(query);
                        if(rowCount>=1)
                            System.out.println("Phone numbers successfully updated!");
                        break;
                    default:
                        System.out.println("Please select a valid option!");
                        break;
                }
                System.out.println("Do you want to edit again?Press Yes/Y/yes try again else press NO/no/N");
                String response = reader.readLine();
                if (response.equalsIgnoreCase("No") || response.equalsIgnoreCase("N"))
                    return;
            }
        }
        catch (IOException exception)
        {
            System.out.println("IOException :" +exception.getMessage());
            Logger.log(exception.getMessage());
        }
        catch (SQLException exception)
        {
            System.out.println("SQLException :" +exception.getMessage());
            Logger.log(exception.getMessage());
        }
    }

    //treats the patient, create a new record and mark the appointment done
    public void treats()//record,store,appointed
    {
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Statement statement = SHS.connection.createStatement();
            ResultSet resultSet;
            String patientID="",appointmentID="",query="";

            LOCATION currentLocation = LOCATION.OPD;
            if((LocalTime.now().compareTo(inTimeOPD)>0||LocalTime.now().equals(inTimeOPD))&&(LocalTime.now().compareTo(outTimeOPD)<0||LocalTime.now().equals(outTimeOPD)))
            {
                currentLocation = LOCATION.OPD;
                System.out.println("Currently treating OPD patients");
            }
            else if((LocalTime.now().compareTo(inTimeLocal)>0||LocalTime.now().equals(inTimeLocal))&&(LocalTime.now().compareTo(outTimeLocal)<0||LocalTime.now().equals(outTimeLocal)))
            {
                currentLocation = LOCATION.LOCAL;
                System.out.println("Currently treating LOCAL patients");
            }
            else {
                System.out.println("Time to go home. OPD and LOCAL timings are over.");
                return;
            }


            System.out.println("Today's remaining appointments list by token number:\n");
            getListOfAssignedPatient(currentLocation);

            query = "Select count(*) from appointment where dateofappointment ='"+LocalDate.now()+"'AND doctor='"+doctorID+"' AND ispatientattended = '0' AND iscritical='T' order by tokennumber;";
            resultSet = statement.executeQuery(query);
            resultSet.next();
            int countOfCriticalPatients = resultSet.getInt(1);
            if(countOfCriticalPatients>1)
            {


                query = "Select * from appointment where dateofappointment ='"+LocalDate.now()+"'AND doctor='"+doctorID+"' AND ispatientattended = '0' AND iscritical='T' order by tokennumber;";
                resultSet = statement.executeQuery(query);

            }
            else
            {

                query = "Select * from appointment where dateofappointment ='"+LocalDate.now()+"'AND doctor='"+doctorID+"' AND ispatientattended = '0' AND iscritical='F' order by tokennumber;";
                resultSet = statement.executeQuery(query);

            }

            if(resultSet.next())
            {
                patientID = resultSet.getString("patient");
                appointmentID = resultSet.getString("id");

                System.out.println("\nTreating patient,\npatient ID:"+patientID+"\nappointment ID:"+resultSet.getString("id")+"\nis critical:"+resultSet.getString("iscritical"));
                System.out.println("\nDo you want to see this patient's history?\n");
                String response = reader.readLine();
                if (response.equalsIgnoreCase("Yes") || response.equalsIgnoreCase("Y"))
                    viewPatientHistory();

                System.out.println("\nTreating patient,\npatient ID:"+patientID+"\nappointment ID:"+resultSet.getString("id"));

                createRecord();

                //referring a patient to another doctor
                System.out.println("\nDo you want to refer this patient?");
                response = reader.readLine();
                if (response.equalsIgnoreCase("Yes") || response.equalsIgnoreCase("Y"))
                {
                    Doctor operatingDoctor;
                    DOCTOR_TYPE doctor_type = DOCTOR_TYPE.JUNIOR_DOCTOR;
                    if(designation.toLowerCase().contains("specialist"))
                    {
                        if(designation.toLowerCase().contains("senior"))
                            doctor_type = DOCTOR_TYPE.SENIOR_SPECIALIST;
                        else
                            doctor_type = DOCTOR_TYPE.SPECIALIST;
                    }
                    else if(designation.toLowerCase().contains("doctor"))
                    {
                        if(designation.toLowerCase().contains("junior"))
                            doctor_type = DOCTOR_TYPE.JUNIOR_DOCTOR;
                        else
                            doctor_type = DOCTOR_TYPE.SENIOR_DOCTOR;
                    }

                    int doctorBeingReferredTo;
                    int departmentBeingReferredTo;
                    String  doctor = "";
                    switch (doctor_type)
                    {
                        case JUNIOR_DOCTOR:
                            operatingDoctor = new JuniorResidentDoctor();
                            System.out.println("Enter the id of the doctor to which you want to refer this patient");
                            doctor =reader.readLine();
                            if(!Validator.isValidDoctorId(doctor))
                            {
                                System.out.println("Wrong doctorID");
                            }
                            doctorBeingReferredTo  = Integer.parseInt(doctor);
                            ((JuniorResidentDoctor) operatingDoctor).referPatient(doctorID,doctorBeingReferredTo,patientID);
                            break;
                        case SENIOR_DOCTOR:
                            operatingDoctor = new SeniorResidentDoctor();
                            System.out.println("Enter the id of the doctor to which you want to refer this patient");
                            doctor =reader.readLine();
                            if(!Validator.isValidDoctorId(doctor))
                            {
                                System.out.println("Wrong doctorID");
                            }
                            doctorBeingReferredTo = Integer.parseInt(doctor);
                            ((SeniorResidentDoctor) operatingDoctor).referPatient(doctorID,doctorBeingReferredTo,patientID);
                            break;
                        case SPECIALIST:
                            operatingDoctor = new Specialist();
                            System.out.println("Select an option:");
                            System.out.println("1.Intra Departmental Refer");
                            System.out.println("2.Inter Departmental Refer");
                            int choice = Integer.parseInt(reader.readLine());
                            System.out.println("Enter the id of the doctor to which you want to refer this patient");
                            doctor =reader.readLine();
                            if(!Validator.isValidDoctorId(doctor))
                            {
                                System.out.println("Wrong doctorID");
                                return;
                            }
                            doctorBeingReferredTo = Integer.parseInt(doctor);
                            if(choice==2)
                            {
                                System.out.println("Enter the department ID:");
                                String department = reader.readLine();
                                if(!Validator.isvalidDepartmentId(department))
                                {
                                    System.out.println("Wrong department ID");
                                    return;
                                }
                                departmentBeingReferredTo = Integer.parseInt(department);
                                ((Specialist) operatingDoctor).referToAnotherDepartment(departmentBeingReferredTo,doctorID,doctorBeingReferredTo,patientID);
                            }
                            else if(choice == 1)
                            {
                                ((Specialist) operatingDoctor).referPatient(doctorID,doctorBeingReferredTo,patientID);
                            }
                            else
                            {
                                System.out.println("Incorrect option was selected! Referral aborted");
                            }
                            break;
                        case SENIOR_SPECIALIST:
                            operatingDoctor = new SeniorSpecialist();
                            System.out.println("Enter the id of the doctor to which you want to refer this patient");
                            doctor =reader.readLine();
                            if(!Validator.isValidDoctorId(doctor))
                            {
                                System.out.println("Wrong doctorID");
                                return;
                            }
                            doctorBeingReferredTo = Integer.parseInt(doctor);
                            System.out.println("Enter the department ID:");
                            String department = reader.readLine();
                            if(!Validator.isvalidDepartmentId(department))
                            {
                                System.out.println("Wrong department ID");
                                return;
                            }
                            departmentBeingReferredTo = Integer.parseInt(department);
                            ((SeniorSpecialist) operatingDoctor).referToAnotherDepartment(departmentBeingReferredTo,doctorID,doctorBeingReferredTo,patientID);
                            break;
                    }

                }
                //after everything is done patient is marked done.
                statement = SHS.connection.createStatement();
                query = "update appointment set ispatientattended = '1' where patient = '"+patientID+"' AND dateofappointment ='"+LocalDate.now()+"' AND id = '"+appointmentID+"';";
                int rowCount = statement.executeUpdate(query);

            }
            else
            {
                System.out.println("There is no unattended patient left for today!");
            }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.log(e.getMessage());
        }catch (IOException e) {
            System.out.println("Please enter a valid input!" + e.getMessage());
            Logger.log(e.getMessage());
        }


    }

    //gets the list of patients assigned to the doctor
    //select only those patient whose appointment record has the doctor ID and is assigned for today and has not been attended yet
    public void getListOfAssignedPatient(LOCATION location) {

        try {
            Statement statement = SHS.connection.createStatement();
            String query = "select patient.name,appointment.id,appointment.patient,appointment.tokennumber,appointment.location,appointment.iscritical from patient inner join appointment on appointment.patient=patient.id where doctor = '"+doctorID+"' AND dateofappointment = '"+LocalDate.now()+"' AND ispatientattended='0' AND location='"+location+"' AND iscritical = 'T' order by tokennumber;";
            ResultSet resultSet = statement.executeQuery(query);
            Boolean isEntriesDisplayed = false;
            for(int i=1;resultSet.next();i++)
            {

                isEntriesDisplayed = true;
                System.out.println(String.format("%30s","-"+i+"-"));
                System.out.println(String.format("%-40s", "patient ID:") + String.format("%20s", resultSet.getString("patient")));
                System.out.println(String.format("%-40s", "patient name:") + String.format("%20s", resultSet.getString("name")));
                System.out.println(String.format("%-40s", "appointment ID:") + String.format("%20s", resultSet.getString("id")));
                System.out.println(String.format("%-40s", "token number:") + String.format("%20s", resultSet.getInt("tokennumber")));
                System.out.println(String.format("%-40s", "is critical:") + String.format("%20s", resultSet.getString("iscritical")));
            }

            query = "select patient.name,appointment.id,appointment.patient,appointment.tokennumber,appointment.location,appointment.iscritical from patient inner join appointment on appointment.patient=patient.id where doctor = '"+doctorID+"' AND dateofappointment = '"+LocalDate.now()+"' AND ispatientattended='0' AND location='"+location+"' AND iscritical = 'F' order by tokennumber;";
            resultSet = statement.executeQuery(query);
            for(int i=1;resultSet.next();i++)
            {

                isEntriesDisplayed = true;
                System.out.println(String.format("%30s","-"+i+"-"));
                System.out.println(String.format("%-40s", "patient ID:") + String.format("%20s", resultSet.getString("patient")));
                System.out.println(String.format("%-40s", "patient name:") + String.format("%20s", resultSet.getString("name")));
                System.out.println(String.format("%-40s", "appointment ID:") + String.format("%20s", resultSet.getString("id")));
                System.out.println(String.format("%-40s", "token number:") + String.format("%20s", resultSet.getInt("tokennumber")));
                System.out.println(String.format("%-40s", "is critical:") + String.format("%20s", resultSet.getString("iscritical")));
            }

            if(!isEntriesDisplayed)
            {
                System.out.println("There is no unattended patient left for today!");
            }
        }
        catch (SQLException exception)
        {
            System.out.println("SQLException :" +exception.getMessage());
            Logger.log(exception.getMessage());
        }
    }
    //sort the list of patients assigned to doctor by IDs
    public void sortListOfAssignedPatientByID() {//display patientID
        try {
            Statement statement = SHS.connection.createStatement();
            String query = "select patient.name,appointment.id,appointment.patient,appointment.tokennumber,appointment.iscritical from patient inner join appointment on appointment.patient=patient.id where doctor = '"+doctorID+"' AND dateofappointment >= '"+LocalDate.now()+"'  AND ispatientattended='0' order by appointment.patient;";

            Boolean isEntriesDisplayed = false;

            ResultSet resultSet = statement.executeQuery(query);
            for(int i=1;resultSet.next();i++)
            {
                isEntriesDisplayed = true;
                System.out.println(String.format("%30s","-"+i+"-"));
                System.out.println(String.format("%-40s", "patient ID:") + String.format("%20s", resultSet.getString("patient")));
                System.out.println(String.format("%-40s", "appointment ID:") + String.format("%20s", resultSet.getString("id")));
                System.out.println(String.format("%-40s", "patient name:") + String.format("%20s", resultSet.getString("name")));
                System.out.println(String.format("%-40s", "token number:") + String.format("%20s", resultSet.getInt("tokennumber")));
                System.out.println(String.format("%-40s", "is critical:") + String.format("%20s", resultSet.getString("iscritical")));

            }
            if(!isEntriesDisplayed)
            {
                System.out.println("There is no unattended patient left for today!");
            }

        }
        catch (SQLException exception)
        {
            System.out.println("SQLException :" +exception.getMessage());
            Logger.log(exception.getMessage());
        }

    }

    //sort the list of patients assigned to doctor by their names
    //select patient.name,appointment.id,appointment.patient,appointment.tokennumber from patient inner join appointment on appointment.patient=patient.id where doctor = '1995' AND dateofappointment >= '2018-11-11' order by patient.name;
    public void sortListOfAssignedPatientByName() {
        try {
            Statement statement = SHS.connection.createStatement();
            String query = "select patient.name,appointment.id,appointment.patient,appointment.tokennumber,appointment.iscritical from patient inner join appointment on appointment.patient=patient.id where doctor = '"+doctorID+"' AND dateofappointment >= '"+LocalDate.now()+"' AND ispatientattended='0' order by patient.name;";
            ResultSet resultSet = statement.executeQuery(query);

            Boolean isEntriesDisplayed = false;

            for(int i=1;resultSet.next();i++)
            {
                isEntriesDisplayed = true;
                System.out.println(String.format("%30s","-"+i+"-"));
                System.out.println(String.format("%-40s", "patient ID:") + String.format("%20s", resultSet.getString("patient")));
                System.out.println(String.format("%-40s", "patient name:") + String.format("%20s", resultSet.getString("name")));
                System.out.println(String.format("%-40s", "appointment ID:") + String.format("%20s", resultSet.getString("id")));
                System.out.println(String.format("%-40s", "token number:") + String.format("%20s", resultSet.getInt("tokennumber")));
                System.out.println(String.format("%-40s", "is critical:") + String.format("%20s", resultSet.getString("iscritical")));

            }
            if(!isEntriesDisplayed)
            {
                System.out.println("There is no unattended patient left for today!");
            }

        }
        catch (SQLException exception)
        {
            System.out.println("SQLException :" +exception.getMessage());
            Logger.log(exception.getMessage());
        }

    }
}

