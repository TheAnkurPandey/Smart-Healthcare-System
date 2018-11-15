import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class JuniorResidentDoctor extends Doctor implements Delegation {
    @Override
    public void referPatient(int doctorsID, int referredDoctorsID, String patientID) {
        try {
            Statement statement = SHS.connection.createStatement();
            int departmentID = 0,tokennumber=0;
            String query = "Select departmentID from doctor where id ='"+doctorsID+"';";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next())
                departmentID = resultSet.getInt("departmentID");

            //It is important that designation is always one of these in the table:
            query = "Select * from doctor where id = '"+referredDoctorsID+"' AND (designation = 'SeniorDoctor' " +
                    "OR designation = 'Specialist' OR designation = 'SeniorSpecialist' ) AND departmentID = '"+departmentID+"';";
            resultSet = statement.executeQuery(query);
            //the referred doctor exists
            if(resultSet.next())
            {

                 query = "select tokennumber from appointment where doctor = '"+referredDoctorsID+"' AND dateofappointment = '"+LocalDate.now()+"' order by tokennumber desc;";
                 resultSet = statement.executeQuery(query);
                 if(resultSet.next()) {
                      tokennumber = resultSet.getInt(1);
                 }
                 else {
                     //If referredDoctor has no appointment on that day!
                    tokennumber = 0;
                 }
                     query = "select * from appointment  where doctor = '"+doctorsID+"' AND dateofappointment = '"+LocalDate.now()+"' AND ispatientattended='0'  AND patient = '"+patientID+"';";
                     resultSet = statement.executeQuery(query);
                     resultSet.next();
                     byte isPatientAttended = 0;
                     query = "insert into appointment values (NULL,'"+resultSet.getString(2)+"','"+referredDoctorsID+"','"
                             +resultSet.getDate(4)+"','"+(tokennumber+1)+"','"+resultSet.getString(6)+"','"
                             +resultSet.getString(7)+"','"+ resultSet.getString(8)+"','"+isPatientAttended+"','"
                             +resultSet.getInt(10)+"');";
                    int rowCount = statement.executeUpdate(query);
                    if(rowCount>0)
                        System.out.println("Referral Completed successfully");
                System.out.println("patient with id:" + patientID + " is successfully referred to doctor with id:" + referredDoctorsID);
            }
            else
            {
                System.out.println("The doctor id of the doctor being referred to is invalid!");
                return;
            }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage()+e.getSQLState());
        }
    }
}
