import Middleware.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class SeniorResidentDoctor extends Doctor implements Delegation {

    public void referPatient(int doctorsID, int referredDoctorsID, String patientID) {
        try {
            Statement statement = SHS.connection.createStatement();
            int departmentID = 0,tokennumber=0;
            String query = "Select departmentID from doctor where id ='"+doctorsID+"';";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next())
                departmentID = resultSet.getInt("departmentID");

            //It is important that designation is always one of these in the table:
            query = "Select * from doctor where id = '"+referredDoctorsID+"' AND (" +
                    "designation = 'Specialist' OR designation = 'SeniorSpecialist' ) AND departmentID = '"+departmentID+"';";
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

                String isCritical = resultSet.getString("iscritical");
                String symptomps = resultSet.getString("symptoms");
                String location =  resultSet.getString("location");
                int department_ID = resultSet.getInt("department");

                //insert into appointment values ('ORTHO1234',NULL,'1','122','2018-11-19',1,'Yes','Fever,Body ache,Restlessness','OPD','0','1');
                //APPOINTMENT_ID is a place holder and will be removed by meaningfulID
                query = "insert into appointment values ('APPOINTMENT_ID',NULL,'"+patientID+"','"+referredDoctorsID+"','"+LocalDate.now()+"','"+(tokennumber+1)+"','"+isCritical+"','"+symptomps+"','"+location+"','0','"+department_ID+"');";
                int rowCount = statement.executeUpdate(query);
                if(rowCount>0)
                {
                    query = "select code from department where id ="+department_ID+";";
                    resultSet = statement.executeQuery(query);
                    resultSet.next();
                    String departmentCode = resultSet.getString("code");
                    String appointmentID = "";
                    int serialNumber = -1;
                    query = "select sno from appointment  where doctor = '"+referredDoctorsID+"' AND dateofappointment = '"+LocalDate.now()+"' AND ispatientattended='0'  AND patient = '"+patientID+"' AND tokennumber = "+(tokennumber+1)+";";
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
                        System.out.println("Referral Completed successfully");
                        System.out.println("patient with id:" + patientID + " is successfully referred to doctor with id:" + referredDoctorsID);
                    }
                }
            }
            else
            {
                System.out.println("The doctor id of the doctor being referred to is invalid!");
                return;
            }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage()+e.getSQLState());
            Logger.log(e.getMessage());
        }
    }


}

