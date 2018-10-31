import java.sql.*;
import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class Patient extends Users{
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    private int patientId;


    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://gaurav@localhost:3306/SHS";

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
                System.out.println("Registration Successful");
                //fetching the ID for the user and printing it
                sql = "SELECT password FROM Patient where phoneNumber='"+getPhoneNumber()+"'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int id = rs.getInt("id");
                    setPatientId(id);
                    System.out.println("Your Patient_ID is "+id+" ");

                }
            }

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

      void patientMenu(int patientId)
      {
          Scanner sc=new Scanner(System.in);
          int operation;
          System.out.println("<------Patient Menu------>");
          System.out.println("Please Enter your choice : ");
          System.out.println("1)View Profile ");
          System.out.println("2)Edit Profile ");
          operation=sc.nextInt();
          switch(operation)
          {
              case 1:
              {
                viewProfile(patientId);
              }
              case 2:
              {


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
                      System.out.println("<------Patient Details are as follows------>");
                      int tempo=rs.getInt("id");
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


          // Methods are here take and implement one by one


//assignPatientToDoctor(patient:Patient)
//getAllDetailsOfDoctor()
//searchDoctorBasedOnDepartments()
//searchDoctorBasedOnID()
//searchDoctorBasedOnName()
//searchDoctorBasedOnSpecialisation()
//searchDoctorBasedOnAddress()
//selectDoctor()
//seeDoctorsProfile()
//viewDoctorsSchedule()
//viewDoctorsContactDetails()
//editProfile()
//viewHistory()
//login():done
//patientRegistration():done(check for existing user or not)
// viewProfile();:done


    }

