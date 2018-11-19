import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//import java.util.Date;
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

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
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
    private int doctorID;
    private String departmentID;
    private int tokenNumber;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String location;
    private boolean isCritical;
    private  String[] symptoms;


    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://gaurav@localhost:3306/SHS?verifyServerCertificate=false&useSSL=true";

    //  Database credentials
    static final String DB_USER = "gaurav";
    static final String DB_PASS = "Root@123";
    Statement stmt=null;
    Connection conn=null;

    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    void createAppointment(int patientId) throws IOException, SQLException, ClassNotFoundException {

      //  SHS.drawLineSeparator();
        SHS.printOptionsList("Book Appointment: Enter the following Details ",new String[]{""});
        Department dept=new Department();
        dept.showDepartments();
        System.out.println("Enter DepartmentID :");
        int choice=Integer.parseInt(br.readLine());
        String identity=null;
            int departmentInt=choice;
        try{
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();
            String sql="select code from department where id ='"+choice+"'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.isBeforeFirst() ) {
                while (rs.next()) {
                    identity = rs.getString("code");

                }
            }
            else
            {
                System.out.println("No Department Exists with DepartmentID "+choice);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        System.out.println("Date of Appointment yyyy-mm-dd :");
        String temp=br.readLine();
        Date dateOfAppointment1=Date.valueOf(temp);
        setDateOfAppointment(dateOfAppointment1);
        this.patientID=patientID;
        System.out.println("Enter your choice"+"\n"+"For OPD : Press 1 "+"\n"+"For LOCAL : Press 2");
        choice=Integer.parseInt(br.readLine());
        if(choice==1)
        {
            setLocation("OPD");

        }
        else if(choice==2)
        {
            //location="LOCAL";
            setLocation("LOCAL");
        }
        else
        {
            System.out.println("Wrong input");
        }
        System.out.println("Enter the Symptoms (comma seperated) :");
        temp=br.readLine();
        String temp1=temp;
        setSymptoms(temp.split(","));
        System.out.println("Patient critical? "+"\n"+"For Yes : Press 1"+"\n"+"For No : Press 2");
        choice=Integer.parseInt(br.readLine());
        String critical=null;
        if(choice==1)
        {isCritical=true;
            critical="T";}
        else if(choice==2)
        {isCritical=false;
            critical="F";}
        else
        {System.out.println("Wrong input....");}

        System.out.println("Do you want to select the doctor 1.Yes 2.No");
        choice=Integer.parseInt(br.readLine());

        Connection conn = null;
        Statement stmt = null;
        if(choice==1)
        {
            //code for selecting doctor manually

            try {
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                System.out.println("Creating statement...");
                stmt = conn.createStatement();
                String sql;
                sql="SELECT * from doctor WHERE departmentid ='"+departmentInt+"'";
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println("Doctor_ID"+"\t"+"Name"+"\t"+"Gender"+"\t"+"Schedule      "+"\t"+"Specialization"+"\t"+"Designation"+"\t"+"Department_ID");
                int docId=0;
                while (rs.next()) {
                    docId=rs.getInt("id");
                    String docName=rs.getString("name");
                    String gender=rs.getString("gender");
                    String docSchedule=rs.getString("schedule");
                    String docSpecial=rs.getString("specialization");
                    String docDesignation=rs.getString("designation");
                    int docDepartid=rs.getInt("departmentid");
                    System.out.println(docId+"\t"+docName+"\t"+gender+"\t"+docSchedule+"\t"+docSpecial+"\t"+docDesignation+"\t"+docDepartid);


                }
                System.out.println("Enter the Doctor id ");
                int finalDocId=Integer.parseInt(br.readLine());

                sql="Insert into appointment(patient,doctor,dateofappointment, iscritical,symptoms,location,department,ispatientattended) values('"+patientId+"','"+ finalDocId+"','"+this.dateOfAppointment+"','"+critical+"','"+temp1+"','"+this.location+"','"+departmentInt+"','0')";
                String sql1="select LAST_INSERT_ID() from appointment;";
                String finIdentity=null; int idNum=-1;
                int i=stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
                if(i==1) {




                    rs = stmt.getGeneratedKeys();

                    if (rs.next()) {
                        idNum = rs.getInt(1);
                    } else {

                        // throw an exception from here
                    }





                    System.out.println("success");

                    finIdentity = identity + idNum;
                    setAppointmentID(finIdentity);




                    sql = "UPDATE appointment SET id= '"+finIdentity+"' WHERE SNO='"+idNum+"' ";
                    //UPDATE Users SET weight = 160, desiredWeight = 145 WHERE id = idNum;
                    int j=stmt.executeUpdate(sql);
                    if(j==1)
                    {
                        System.out.println("Appointment Booked Successfully");
                        System.out.println("Your Appointment_ID is "+finIdentity);
                    }
                    else
                    {
                        System.out.println("problem writing in database!!!");
                    }


                }
                else {
                    System.out.println("failure");

                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            }

      }
        else if(choice==2)
        {
            //intelligent algo to assign doctor

            try {

            //intelligent algo to assign doctor
            String dayOfWeek = new SimpleDateFormat("EE").format(dateOfAppointment);
            //System.out.println("dayOfWeek: " + dayOfWeek);

            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            //System.out.println("departmentID: " + departmentID);
            String sql = "SELECT * FROM doctor WHERE departmentid = '" + departmentID + "' ";
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Integer> listOfDoctorsOnThatDay = new ArrayList<>();
            ArrayList<Long> totalTokenCountOfDoctorOnThatDay = new ArrayList<>();

            while (rs.next()) {
                if (Arrays.asList(rs.getString("schedule").split(",")).contains(dayOfWeek)) {
                    listOfDoctorsOnThatDay.add(rs.getInt("id"));
                    //System.out.println("doctor id: " + rs.getInt("id"));
                    LocalTime inTime = rs.getTime("intimeopd").toLocalTime();
                    LocalTime outTime = rs.getTime("outtimeopd").toLocalTime();
                    long totalTokenCount = Duration.between(inTime, outTime).toMinutes() / 10L;
                    //System.out.println("totalTokenCount: " + totalTokenCount);
                    totalTokenCountOfDoctorOnThatDay.add(totalTokenCount);

                }
            }

            ArrayList<Integer> listOfAvailableDoctorOnThatDay = new ArrayList<>();
            int newToken;
            for (int i = 0; i < listOfDoctorsOnThatDay.size(); i++) {
                sql = "SELECT COUNT(*) AS total FROM appointment WHERE doctor = '" + listOfDoctorsOnThatDay.get(i) + "' ";
                rs = stmt.executeQuery(sql);
                rs.next();
                if (rs.getInt("total") < totalTokenCountOfDoctorOnThatDay.get(i)) {
                    listOfAvailableDoctorOnThatDay.add(listOfDoctorsOnThatDay.get(i));
                    newToken=rs.getInt("total")+1;

                    //System.out.println("Added to list of available doctor: " + listOfDoctorsOnThatDay.get(i));
                }

            }

            ArrayList<String> specialisationOfAvailableDoctorOnThatDay = new ArrayList<>();
            for (int i = 0; i < listOfAvailableDoctorOnThatDay.size(); i++) {
                sql = "SELECT specialization FROM doctor WHERE id = '" + listOfAvailableDoctorOnThatDay.get(i) + "' ";
                rs = stmt.executeQuery(sql);
                rs.next();
                specialisationOfAvailableDoctorOnThatDay.add(rs.getString("specialization").toLowerCase());
                //System.out.println("specialization: " + rs.getString("specialization"));
            }

            //System.out.println("specialisationOfAvailableDoctorOnThatDay: " + specialisationOfAvailableDoctorOnThatDay.toString());



            sql = "SELECT * FROM knowledgebase";
            rs = stmt.executeQuery(sql);
            HashMap<String, Integer> matches = new HashMap<>();
            ArrayList<String> specialisationList = new ArrayList<>();

            Arrays.sort(symptoms);
            while(rs.next()) {
                String specialisation = rs.getString("specialization");
                //System.out.println("specialization2: " + specialisation);
                if (!specialisationOfAvailableDoctorOnThatDay.contains(specialisation.toLowerCase()))
                    continue;
                specialisationList.add(specialisation);
                //System.out.println("Added to the specialisationList");
                String specialisationSymptomsList[];
                Arrays.sort(specialisationSymptomsList = rs.getString("symptomsList").split(",") );
                //System.out.println(specialisationSymptomsList.toString());
                matches.put(specialisation, getMatchingSymptomsCount(symptoms, specialisationSymptomsList));

            }
            int indexOfMax = -1;
            int maxValue = 0;
            for(int i = 0; i < specialisationList.size(); i++) {
                if(matches.get(specialisationList.get(i)) > maxValue) {
                    maxValue = matches.get(specialisationList.get(i));
                    indexOfMax = i;
                }
            }

            if (maxValue == 0) {
                System.out.println("Go to general physician");
            }
            else {
                System.out.println("doctor id:" + listOfAvailableDoctorOnThatDay.get(
                        specialisationOfAvailableDoctorOnThatDay.indexOf( specialisationList.get(indexOfMax) )
                ));
            }

        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        }
        else
        {
            System.out.println("Wrong input...");
        }


    }


    private int getMatchingSymptomsCount(String a[], String b[]) {
        int matchesFound = 0;

        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < b.length; j++)
                if (a[i].equalsIgnoreCase(b[j]))
                    matchesFound ++;

        return matchesFound;
    }
}