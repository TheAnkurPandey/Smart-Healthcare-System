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
                if (username.equalsIgnoreCase("oopd") && password.equalsIgnoreCase("project")) {
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
            System.out.println("Enter the doctorID:");
            String doctorID = reader.readLine();
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

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartHealthcareSystem?verifyServerCertificate=false&useSSL=true", "rjtmhy", "#Rjtmhy25");
                Statement statement = connection.createStatement();
                while (!isRegistrationSuccessful) {
                    String query = "Select id from doctor where id = '" + doctorID + "'";
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        System.out.println("DoctorID already exists in the table!");
                        System.out.println("Enter a new doctorID:");
                        doctorID = reader.readLine();
                    } else {
                        query = "insert into doctor values('" + doctorID + "','" + name + "','" + dateOfBirth + "','" + age + "','" + gender + "','" + phoneNumber + "','" + email + "','" + password + "','"
                                + address + "','" + schedule + "','" + inTimeOPD + "','" + outTimeOPD + "','" + specialisation + "','" + isSurgeonStatus + "','" + designation + "');";
                        statement.executeUpdate(query);
                        isRegistrationSuccessful = true;
                    }
                }

                if (isRegistrationSuccessful == true)
                    System.out.println("Doctor successfully registered!");


        }
         catch (SQLException e) {
        System.out.println(e.getMessage());
         } catch (ClassNotFoundException e) {
        System.out.println(e.getMessage());
         }catch (IOException e) {
            System.out.println("Please enter a valid input!" + e.getMessage());
        }

    }

    public void  reassignDoctorToPatient(String patientID,String doctorID) {

    }

    public void veiwPatientDetails() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Connection connection;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartHealthcareSystem?verifyServerCertificate=false&useSSL=true", "rjtmhy", "#Rjtmhy25");
            Statement statement = connection.createStatement();
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
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException! " + e.getMessage());
        }
    }

    public void viewDoctorDetails() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Connection connection;
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartHealthcareSystem?verifyServerCertificate=false&useSSL=true", "rjtmhy", "#Rjtmhy25");
            Statement statement = connection.createStatement();
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
                } else {
                    System.out.println("Doctor record not found!");
                }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException! " + e.getMessage());
        }
    }
}

