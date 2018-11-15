import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.SimpleDateFormat;

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

    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    void createAppointment(int patientId) throws IOException, SQLException, ClassNotFoundException {








        SHS.drawLineSeparator();
        SHS.printOptionsList("Book Appointment: Enter the following Details ",new String[]{"Departments ","1. General Physician","2.Orthopedics","3.Gynaecology"});
        System.out.println("Please choose the department and enter your choice");
        String identity=null;int departmentInt=0;
        int choice=Integer.parseInt(br.readLine());
        switch(choice)
        {


                case 1: identity="GEN";
                        departmentInt=1;
                    break;
                case 2: identity="ORTHO";
                        departmentInt=2;
                    break;
                case 3: identity="GYNAE";
                        departmentInt=3;
                    break;



            //assign departmentId accordingly
        }
        System.out.println("Date of Appointment yyyy-mm-dd");
        String temp=br.readLine();
         Date dateOfAppointment1=Date.valueOf(temp);
         setDateOfAppointment(dateOfAppointment1);
         this.patientID=patientID;
        System.out.println("Enter your choice"+"\n"+"OPD 1 "+"\n"+"LOCAL 2");
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
        System.out.println("Enter the symptoms comma seperated");
        temp=br.readLine();
        String temp1=temp;
        setSymptoms(temp.split(","));
        System.out.println("Patient critical? "+"\n"+"1. yes 2.no");
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
        }
        else
        {
            System.out.println("Wrong input...");
        }











    }
}
