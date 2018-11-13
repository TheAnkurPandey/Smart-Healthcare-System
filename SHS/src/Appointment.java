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
    void createAppointment(String patientId) throws IOException, SQLException, ClassNotFoundException {








        SHS.drawLineSeparator();
        SHS.printOptionsList("Book Appointment: Enter the following Details ",new String[]{"Departments ","1. General Physician","2.Orthopedics","3.Gynaecology"});
        System.out.println("Please choose the department and enter your choice");
        String identity;int departmentInt=0;
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
         dateOfAppointment=Date.valueOf(temp);
         this.patientID=patientID;
        System.out.println("Enter your choice"+"\n"+"OPD 1 "+"\n"+"LOCAL 2");
        choice=Integer.parseInt(br.readLine());
        if(choice==1)
        {
            location="OPD";
        }
        else if(choice==2)
        {
            location="LOCAL";
        }
        else
        {
            System.out.println("Wrong input");
        }
        System.out.println("Enter the symptoms comma seperated");
        temp=br.readLine();
        String temp1=temp;
        symptoms=temp.split(",");
        System.out.println("Patient critical? "+"\n"+"1. yes 2.no");
        choice=Integer.parseInt(br.readLine());
        if(choice==1)
        {isCritical=true;}
        else if(choice==2)
        {isCritical=false;}
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
                while (rs.next()) {
                    int docId=rs.getInt("id");
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

                sql="insert into appointment";

            }
            catch (Exception e)
            {

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
