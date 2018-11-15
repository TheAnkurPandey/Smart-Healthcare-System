import java.sql.*;
import java.io.*;
import java.util.Date;
import java.util.Scanner;


public class Patient extends Users{
    private String department;
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    private int patientId;


    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://gaurav@localhost:3306/SHS?verifyServerCertificate=false&useSSL=true";

    //  Database credentials
    static final String DB_USER = "gaurav";
    static final String DB_PASS = "Root@123";
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main (String[]args) throws IOException {
     Patient p1=new Patient();
    // p1.patientRegistration();
        p1.patientLogin();
        //String ph="9873372494";
        //p1.viewProfile(ph);
    }



    public void patientRegistration() throws IOException {   //Patient registration is done here. while registering someone it checks if the entry exists in the database or not.
        //Scanner sc=new Scanner(System.in);
        System.out.println("Please Enter the following Information");
        System.out.println("Name :");
        setName(br.readLine());
        System.out.println("Date of Birth(YYYY-MM-DD) :");
        setDateOfBirth(br.readLine());
        System.out.println("Age : ");
        setAge(Integer.parseInt(br.readLine()));
        System.out.println("Gender : ");
        setGender(br.readLine());
        System.out.println("Phone Number : ");
        setPhoneNumber(Long.parseLong(br.readLine()));
        System.out.println("Email : ");
        setEmail(br.readLine());
        System.out.println("Password : ");
        setPassword(br.readLine());
        System.out.println("Address : ");
        setAddress(br.readLine());
       /* SHS.printOptionsList(" Choose the Departments  ",new String[]{"1. General Physician","2.Orthopedics","3.Gynaecology"});
        int option;
        String identity=null;
        option=Integer.parseInt(br.readLine());
        switch(option)
        {
            case 1: identity="GEN";
            break;
            case 2: identity="ORTHO";
            break;
            case 3: identity="GYNAE";
            break;


        }

*/
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "Insert into patient(name,dateofbirth,age,gender,phonenumber,email,password,address) values('" + this.getName() + "','" + this.getDateOfBirth() + "','" + this.getAge() + "','"+this.getGender()+"','"+this.getPhoneNumber()+"','"+this.getEmail()+"','"+this.getPassword()+"','"+this.getAddress()+"')";
            int i=stmt.executeUpdate(sql);
            if(i==1)
            {

                //fetching the ID for the user and printing it
                String finIdentity=null;int idNum=0;
                sql = "SELECT id FROM patient where phoneNumber='"+getPhoneNumber()+"'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                     idNum = rs.getInt("id");
                     //finIdentity = identity + idNum;
                    //setPatientId(finIdentity);

                }
                //System.out.println(finIdentity);
                   // sql = "UPDATE patient SET id= '"+finIdentity+"' WHERE SNO='"+idNum+"' ";
                    //UPDATE Users SET weight = 160, desiredWeight = 145 WHERE id = idNum;
                   // int j=stmt.executeUpdate(sql);
                    //if(j==1)
                    //{
                        System.out.println("Registration Successful");
                        System.out.println("Your ID is "+idNum);
                    }
//                    else
//                    {
//                        System.out.println("problem writing in database123!!!");
//                    }



            //}

            else {
                System.out.println("problem writing in database!!!");
            }


                    stmt.close();
            conn.close();
        }



        catch(SQLIntegrityConstraintViolationException se)
        {
            System.out.println("User Already Exists with the Phone Number : "+getPhoneNumber()+". Please register with a different Phone Number !!!");
        }
        catch (SQLException se) {



            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }



    public void patientLogin() throws IOException {
        // takes the input from user and then checks if the credentials are correct or not.
        //if credentials are correct then it calls patientMenu function.
        int patientId;
        String password;
        Scanner sc=new Scanner(System.in);

        System.out.println("Please Enter the details below ");
        System.out.println("Patient_ID : ");
        patientId=Integer.parseInt(br.readLine());
        System.out.println("Password : ");
        password=br.readLine();
        Connection conn = null;
        Statement stmt = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            System.out.println(patientId);
            sql = "SELECT password FROM patient where id='"+patientId+"'";
            ResultSet rs = stmt.executeQuery(sql);
            boolean status=false;
                while (rs.next()) {
                    status=true;
                    String id = rs.getString("password");
                     System.out.println(id);
                    if (id.equals(password)) {
                        System.out.println("login successful!!!");
                        patientMenu(patientId);
                        //calling PatientMenu function

                    } else {
                        System.out.println("Wrong Password");

                    }

                }
                if(status==false)
                {
                    System.out.println("Wrong Patient_Id ");
                }


            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }




    }

      void patientMenu(int patientId) throws IOException, SQLException, ClassNotFoundException {
        boolean status=true;
        while(status) {
            if(status==false)
            {
                break;
            }
            SHS.printOptionsList("Patient Menu",
                    new String[]{"1.View Profile", "2.Edit Profile", "3.Book Appointment", "4.View Doctor Details ", "5.View History", "6.Search Doctors", "7.Log out"});
            System.out.println("Enter an option number:");
            int operation;
            operation = Integer.parseInt(br.readLine());
            switch (operation) {
                case 1: {
                    viewProfile(patientId);
                    break;
                }

                case 2: {
                    editProfile(patientId);
                    break;
                }
                case 3:
                    Appointment appoint=new Appointment();
                    appoint.createAppointment(patientId);
                    break;
                case 4:
                    viewDoctorDetails();
                    break;
                case 5:
                case 6:
                    searchDoctor();
                    break;
                case 7:{
                    status=false;
                    break;
                }
                default: {
                    System.out.println("Wrong Input");
                    break;
                }


            }

        }
      }
      void viewProfile(int patientId)
      {
          Connection conn = null;
          Statement stmt = null;
          try {
              //Register JDBC driver
              Class.forName("com.mysql.jdbc.Driver");

              // Open a connection
              System.out.println("Connecting to database...");
              conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

              //Execute a query
              System.out.println("Creating statement...");
              stmt = conn.createStatement();
              String sql;
              sql = "SELECT * FROM patient where id='"+patientId+"'";
              ResultSet rs = stmt.executeQuery(sql);

                  while (rs.next()) {
                      SHS.printOptionsList("Patient Details",
                              new String[]{""});
                      String tempo=rs.getString("id");
                      System.out.println("Patient_ID : "+tempo);
                      String temp = rs.getString("name");
                      System.out.print("Name :");
                      System.out.println(temp);
                      System.out.print("Date of Birth :");
                      temp=rs.getString("dateofbirth");
                      System.out.println(temp);
                      System.out.print("Age : ");
                      int age=rs.getInt("age");
                      System.out.println(age);
                        System.out.print("Gender : ");
                        temp=rs.getString("gender");
                      System.out.println(temp);
                      System.out.print("Phone Number : ");
                      long phNo=rs.getLong("phonenumber");
                        System.out.print("Email : ");
                        temp=rs.getString("email");
                      System.out.println(temp);
                        System.out.print("Address : ");
                        temp=rs.getString("address");
                      System.out.println(temp);



                  }





          }
          //catch
          catch (SQLException se) {
              //Handle errors for JDBC
              se.printStackTrace();
          } catch (Exception e) {
              //Handle errors for Class.forName
              e.printStackTrace();
          } finally {

              try {
                  if (stmt != null)
                      stmt.close();
              } catch (SQLException se2) {
              }
              try {
                  if (conn != null)
                      conn.close();
              } catch (SQLException se) {
                  se.printStackTrace();
              }
          }
      }

      void editProfile(int patientId) throws IOException, ClassNotFoundException, SQLException {
          Connection conn = null;
          Statement stmt = null;

              //Register JDBC driver
              Class.forName("com.mysql.jdbc.Driver");

              // Open a connection
              System.out.println("Connecting to database...");
              conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

              //Execute a query
              System.out.println("Creating statement...");
              stmt = conn.createStatement();
              String sql;
                SHS.printOptionsList("Enter your choice to Edit",new String[]{"1.Name","2.Email","3.Address"});
//              System.out.println("Enter your choice to Edit");
//            System.out.println("1)Name ");
//            System.out.println("2)Email ");
//            System.out.println("3)Address");
            int operation;
            operation=Integer.parseInt(br.readLine());
            switch(operation)
            {
                case 1:
                {
                    System.out.println("Name : ");
                    String patientName;
                    patientName=br.readLine();
                    try {
                         sql = "UPDATE patient SET name= '" + patientName + "' WHERE id='" + patientId + "' ";
                        int j=stmt.executeUpdate(sql);
                        if(j==1)
                        {
                            System.out.println("Update Successful");
                        }
                        else
                        {
                            System.out.println("Update Failed");
                        }
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                    break;
                }
                case 2:
                {
                    System.out.println("Email : ");
                    String email;
                    email=br.readLine();
                    try {
                        sql = "UPDATE patient SET email= '" +email + "' WHERE id='" + patientId + "' ";
                        int j=stmt.executeUpdate(sql);
                        if(j==1)
                        {
                            System.out.println("Update Successful");
                        }
                        else
                        {
                            System.out.println("Update Failed");
                        }
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                    break;
                }
                case 3:
                {
                    System.out.println("Address :");
                    String address;
                    address=br.readLine();
                    try {
                        sql = "UPDATE patient SET address= '" + address + "' WHERE id='" + patientId + "' ";
                        int j=stmt.executeUpdate(sql);
                        if(j==1)
                        {
                            System.out.println("Update Successful");
                        }
                        else
                        {
                            System.out.println("Update Failed");
                        }
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                    break;
                }
                default :
                    System.out.println("Wrong Input!!!");


            }



        }

    public void viewDoctorDetails() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Connection connection;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SHS?verifyServerCertificate=false&useSSL=true", "gaurav", "Root@123");
            Statement statement = connection.createStatement();
            String myQuery;

           // System.out.println("Enter the doctor ID:");
            //String doctorID = reader.readLine();
            String sql = "select * from doctor ";
            ResultSet queryResult = statement.executeQuery(sql);
            System.out.println( "Doctor_ID"+"\t"+"Name"+"\t"+"Gender"+"\t"+"DepartmentID");
            if(queryResult!=null) {
                while (queryResult.next()) {
                    System.out.print( queryResult.getInt(1)+"\t");
                    System.out.print( queryResult.getString(2)+"\t");
                    //System.out.println(String.format("%-40s","dateOfBirth:")+String.format("%20s",queryResult.getDate(3)));
                    //System.out.println(String.format("%-40s","age:")+String.format("%20s",queryResult.getInt(4)));
                    System.out.print( queryResult.getString(5)+"\t");
                    //System.out.println(String.format("%-40s","phoneNumber:")+String.format("%20s",queryResult.getString(6)));
                    //System.out.println(String.format("%-40s","email:")+String.format("%20s",queryResult.getString(7)));
                    //System.out.println(String.format("%-40s","password:")+String.format("%20s",queryResult.getString(8)));
                    //System.out.println(String.format("%-40s","address:")+String.format("%20s",queryResult.getString(9)));
                    //System.out.println(String.format("%-40s","schedule:")+String.format("%20s",queryResult.getString(10)));
                    //System.out.println(String.format("%-40s","inTimeOPD:")+String.format("%20s",queryResult.getTime(11)));
                    //System.out.println(String.format("%-40s","outTimeOPD:")+String.format("%20s",queryResult.getTime(12)));
                    //System.out.println(String.format("%-40s","specialisation of the Doctor:")+String.format("%20s",queryResult.getString(13)));
                    //System.out.println(String.format("%-40s","is Surgeon:")+String.format("%20s",queryResult.getBoolean(14)));
                    //System.out.println(String.format("%-40s","designation:")+String.format("%20s",queryResult.getString(15)));
                    System.out.print(String.format("%20s", queryResult.getInt(16)));
                }
                System.out.println();
                System.out.println("Enter the Doctor_ID to View Doctor Details");
                int doctorID=Integer.parseInt(reader.readLine());
                sql = "select * from doctor where  id = '" + doctorID + "' ";
                queryResult = statement.executeQuery(sql);

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
                } else {
                    System.out.println("Wrong DoctorID Entered !!!");
                }


            }
            else {
                System.out.println("No Doctor found!");
            }


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException! " + e.getMessage());
        }
    }
    public void searchDoctor() throws IOException {
        SHS.printOptionsList("Search Doctor : Based On",new String[]{"1.Department","2.ID","3.Name","4.Specialisation","5.Address"});
        System.out.println("Enter the option ");
        int choice=Integer.parseInt(br.readLine());
        switch(choice)
        {
            case 1:searchDoctorBasedOnDepartments();
            break;
            case 2: searchDoctorBasedOnID();
            break;
            case 3:searchDoctorBasedOnName();
            break;
            case 4:searchDoctorBasedOnSpecialization();
            break;
            case 5:searchDoctorBasedOnAddress();
            break;
        }


    }
   public void searchDoctorBasedOnDepartments() throws IOException, SQLException {
       System.out.println("Select the Department");
       SHS.printOptionsList(" Choose the Departments  ", new String[]{"1. General Physician", "2.Orthopedics", "3.Gynaecology"});
       int choice = Integer.parseInt(br.readLine());

       try {
           BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
           Connection connection;
           Class.forName("com.mysql.cj.jdbc.Driver");
           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SHS?verifyServerCertificate=false&useSSL=true", "gaurav", "Root@123");
           Statement statement = connection.createStatement();
           String sql = "select * from doctor where departmentid='" + choice + "' ";
           ResultSet queryResult = statement.executeQuery(sql);
           System.out.println("Doctor_ID" + "\t" + "Name" + "\t"+"DateOfBirth"+"\t"+"Age"+"\t"+ "Gender" + "\t" + "Phone-Number"+"\t"+"Email"+"\t"+"Address"+"\t"+"Schedule"+"\t"+"intimeopd \t" +
                   "outtimeopd\t" +"specialization\t" +"issurgeon\t" +"designation \t" +"departmentid");
           if(queryResult!=null){
               while (queryResult.next()) {
                   System.out.print(queryResult.getInt(1) + "\t");
                   System.out.print(queryResult.getString(2) + "\t");
                   System.out.print(queryResult.getString(3) + "\t");
                   System.out.print(queryResult.getString(4) + "\t");
                   System.out.print(queryResult.getString(5) + "\t");
                   System.out.print(queryResult.getString(6) + "\t");
                   System.out.print(queryResult.getString(7) + "\t");
                  // System.out.print(queryResult.getString(8) + "\t"); password
                   System.out.print(queryResult.getString(9) + "\t");
                   System.out.print(queryResult.getString(10) + "\t");
                   System.out.print(queryResult.getString(11) + "\t");
                   System.out.print(queryResult.getString(12) + "\t");
                   System.out.print(queryResult.getString(13) + "\t");
                   System.out.print(queryResult.getString(14) + "\t");
                   System.out.print(queryResult.getString(15) + "\t");
                   System.out.println(queryResult.getInt(16));
               }
               System.out.println();
           }
           else
           {
               System.out.println("Wrong Input or No doctor exists for the department");
           }


       } catch (SQLException e) {
           System.out.println("SQLException: " + e.getMessage());
       } catch (ClassNotFoundException e) {
           System.out.println("ClassNotFoundException " + e.getMessage());
       }
   }
    public void searchDoctorBasedOnID() throws IOException {
        System.out.println("Enter the Doctor ID ");
        int choice = Integer.parseInt(br.readLine());

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Connection connection;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SHS?verifyServerCertificate=false&useSSL=true", "gaurav", "Root@123");
            Statement statement = connection.createStatement();
            String sql = "select * from doctor where id='" + choice + "' ";
            ResultSet queryResult = statement.executeQuery(sql);
            System.out.println("Doctor_ID" + "\t" + "Name" + "\t"+"DateOfBirth"+"\t"+"Age"+"\t"+ "Gender" + "\t" + "Phone-Number"+"\t"+"Email"+"\t"+"Address"+"\t"+"Schedule"+"\t"+"intimeopd \t" +
                    "outtimeopd\t" +"specialization\t" +"issurgeon\t" +"designation \t" +"departmentid");
            if (queryResult != null) {
                while (queryResult.next()) {
                    System.out.print(queryResult.getInt(1) + "\t");
                    System.out.print(queryResult.getString(2) + "\t");
                    System.out.print(queryResult.getString(3) + "\t");
                    System.out.print(queryResult.getString(4) + "\t");
                    System.out.print(queryResult.getString(5) + "\t");
                    System.out.print(queryResult.getString(6) + "\t");
                    System.out.print(queryResult.getString(7) + "\t");
                    // System.out.print(queryResult.getString(8) + "\t"); password
                    System.out.print(queryResult.getString(9) + "\t");
                    System.out.print(queryResult.getString(10) + "\t");
                    System.out.print(queryResult.getString(11) + "\t");
                    System.out.print(queryResult.getString(12) + "\t");
                    System.out.print(queryResult.getString(13) + "\t");
                    System.out.print(queryResult.getString(14) + "\t");
                    System.out.print(queryResult.getString(15) + "\t");
                    System.out.println(queryResult.getInt(16));
                }
                System.out.println();
            }
            else
            {
                System.out.println("Wrong Doctor ID");
            }


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        }
    }
    public void searchDoctorBasedOnName() throws IOException {
        System.out.println("Enter the Doctor's Name ");
        String name = br.readLine();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Connection connection;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SHS?verifyServerCertificate=false&useSSL=true", "gaurav", "Root@123");
            Statement statement = connection.createStatement();
            String sql = "select * from doctor where name='" + name + "' ";
            ResultSet queryResult = statement.executeQuery(sql);
            System.out.println("Doctor_ID" + "\t" + "Name" + "\t"+"DateOfBirth"+"\t"+"Age"+"\t"+ "Gender" + "\t" + "Phone-Number"+"\t"+"Email"+"\t"+"Address"+"\t"+"Schedule"+"\t"+"intimeopd \t" +
                    "outtimeopd\t" +"specialization\t" +"issurgeon\t" +"designation \t" +"departmentid");
            if (queryResult != null) {
                while (queryResult.next()) {
                    System.out.print(queryResult.getInt(1) + "\t");
                    System.out.print(queryResult.getString(2) + "\t");
                    System.out.print(queryResult.getString(3) + "\t");
                    System.out.print(queryResult.getString(4) + "\t");
                    System.out.print(queryResult.getString(5) + "\t");
                    System.out.print(queryResult.getString(6) + "\t");
                    System.out.print(queryResult.getString(7) + "\t");
                    // System.out.print(queryResult.getString(8) + "\t"); password
                    System.out.print(queryResult.getString(9) + "\t");
                    System.out.print(queryResult.getString(10) + "\t");
                    System.out.print(queryResult.getString(11) + "\t");
                    System.out.print(queryResult.getString(12) + "\t");
                    System.out.print(queryResult.getString(13) + "\t");
                    System.out.print(queryResult.getString(14) + "\t");
                    System.out.print(queryResult.getString(15) + "\t");
                    System.out.println(queryResult.getInt(16));
                }
                System.out.println();
            }
            else
            {
                System.out.println("Wrong Doctor Name or no doctor exists with entered name");
            }


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        }
    }

    public void searchDoctorBasedOnSpecialization() throws IOException {
        System.out.println("Enter the Doctor's Specialization ");
        String specialisation = br.readLine();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Connection connection;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SHS?verifyServerCertificate=false&useSSL=true", "gaurav", "Root@123");
            Statement statement = connection.createStatement();
            String sql = "select * from doctor where specialization='" + specialisation + "' ";
            ResultSet queryResult = statement.executeQuery(sql);
            System.out.println("Doctor_ID" + "\t" + "Name" + "\t"+"DateOfBirth"+"\t"+"Age"+"\t"+ "Gender" + "\t" + "Phone-Number"+"\t"+"Email"+"\t"+"Address"+"\t"+"Schedule"+"\t"+"intimeopd \t" +
                    "outtimeopd\t" +"specialization\t" +"issurgeon\t" +"designation \t" +"departmentid");
            if (queryResult != null) {
                while (queryResult.next()) {
                    System.out.print(queryResult.getInt(1) + "\t");
                    System.out.print(queryResult.getString(2) + "\t");
                    System.out.print(queryResult.getString(3) + "\t");
                    System.out.print(queryResult.getString(4) + "\t");
                    System.out.print(queryResult.getString(5) + "\t");
                    System.out.print(queryResult.getString(6) + "\t");
                    System.out.print(queryResult.getString(7) + "\t");
                    // System.out.print(queryResult.getString(8) + "\t"); password
                    System.out.print(queryResult.getString(9) + "\t");
                    System.out.print(queryResult.getString(10) + "\t");
                    System.out.print(queryResult.getString(11) + "\t");
                    System.out.print(queryResult.getString(12) + "\t");
                    System.out.print(queryResult.getString(13) + "\t");
                    System.out.print(queryResult.getString(14) + "\t");
                    System.out.print(queryResult.getString(15) + "\t");
                    System.out.println(queryResult.getInt(16));
                }
                System.out.println();
            }
            else
            {
                System.out.println("Wrong Specialization or no doctor exists with entered specialization");
            }


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        }
    }
    public void searchDoctorBasedOnAddress() throws IOException {
        System.out.println("Enter the Doctor's Address ");
        String address = br.readLine();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Connection connection;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SHS?verifyServerCertificate=false&useSSL=true", "gaurav", "Root@123");
            Statement statement = connection.createStatement();
            String sql = "select * from doctor where address='" + address + "' ";
            ResultSet queryResult = statement.executeQuery(sql);
            System.out.println("Doctor_ID" + "\t" + "Name" + "\t"+"DateOfBirth"+"\t"+"Age"+"\t"+ "Gender" + "\t" + "Phone-Number"+"\t"+"Email"+"\t"+"Address"+"\t"+"Schedule"+"\t"+"intimeopd \t" +
                    "outtimeopd\t" +"specialization\t" +"issurgeon\t" +"designation \t" +"departmentid");
            if (queryResult != null) {
                while (queryResult.next()) {
                    System.out.print(queryResult.getInt(1) + "\t");
                    System.out.print(queryResult.getString(2) + "\t");
                    System.out.print(queryResult.getString(3) + "\t");
                    System.out.print(queryResult.getString(4) + "\t");
                    System.out.print(queryResult.getString(5) + "\t");
                    System.out.print(queryResult.getString(6) + "\t");
                    System.out.print(queryResult.getString(7) + "\t");
                    // System.out.print(queryResult.getString(8) + "\t"); password
                    System.out.print(queryResult.getString(9) + "\t");
                    System.out.print(queryResult.getString(10) + "\t");
                    System.out.print(queryResult.getString(11) + "\t");
                    System.out.print(queryResult.getString(12) + "\t");
                    System.out.print(queryResult.getString(13) + "\t");
                    System.out.print(queryResult.getString(14) + "\t");
                    System.out.print(queryResult.getString(15) + "\t");
                    System.out.println(queryResult.getInt(16));
                }
                System.out.println();
            }
            else
            {
                System.out.println("Wrong address or no doctor exists with entered address");
            }


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        }
    }


    // Methods are here take and implement one by one


//assignPatientToDoctor(patient:Patient) ::appointment
//getAllDetailsOfDoctor()::display from Doctor
//searchDoctorBasedOnDepartments()
//searchDoctorBasedOnID()
//searchDoctorBasedOnName()
//searchDoctorBasedOnSpecialisation()
//searchDoctorBasedOnAddress()
//selectDoctor()::doctors from a particular department
//seeDoctorsProfile()
//viewDoctorsSchedule()
//viewDoctorsContactDetails()
//editProfile():done
//viewHistory()::record table
//login():done
//patientRegistration():done(check for existing user or not):done
// viewProfile();:done



    }

