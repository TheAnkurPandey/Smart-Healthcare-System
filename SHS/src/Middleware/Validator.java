package Middleware;

import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isValidAlphaNumericText(String str) {
        return Pattern.matches("^[a-zA-Z0-9 ]*$", str);
    }

    public static boolean isValidSingleDigitInt(String str) {
        return Pattern.matches("^[0-9]$", str);
    }

    public static boolean isItInteger(String str) {
        return Pattern.matches("^[0-9]+$", str);
    }

    public static boolean isValidUserName(String str) {
        return Pattern.matches("^[a-zA-Z.'\\-]+$", str);
    }

    //first last
    public static boolean isValidFullName(String str) {
        return Pattern.matches("^[a-zA-Z ]+$", str);
    }

    public static boolean isItContainingSpecialCharacters(String str) {
        return Pattern.matches("[^a-zA-Z0-9\\s]*", str) && !str.equals("");
    }

    public static boolean isItStartingWithAlphabet(String str) {
        return Pattern.matches("^[a-zA-Z][a-zA-Z0-9.,$;[^a-zA-Z0-9\\s]]+", str);
    }

    public static boolean validateEmail(String email) {
        boolean isValid = true;
        if (!Pattern.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$", email))
            isValid = false;

        return isValid;
    }

    public static boolean isValidPassword(String str) {
        boolean isValid = true;
        if (!Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~!@#$%^&*])\\S{6,30}$", str))
            isValid = false;

        return isValid;
    }

    public static boolean isContainingValidIntInRange(String input, String a, String b) {
        boolean isValid = true;
        if (a.length() != 1 || b.length() != 1 || !isValidSingleDigitInt(a) || !isValidSingleDigitInt(b))
            return false;
        String regex = "^[" + a + "-" + b + "]$";
        if (!Pattern.matches(regex, input))
            isValid = false;

        return isValid;
    }

    public static boolean isValidDate(String input) {
        return Pattern.matches("^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", input);
    }

    public static boolean isValidTime(String input) {
        return Pattern.matches("^(\\d{1,2}):(\\d{2})$", input);
    }

    public static boolean isValidAge(String input) {
        if (!isItInteger(input))
            return false;
        if (input.length() > 3)
            return false;
        if (Integer.parseInt(input) > 100)
            return false;
        return true;
    }

    public static boolean isValidGender(String str) {
        return Pattern.matches("^m|male|f|female$", str.toLowerCase());
    }

    public static boolean isValidPhoneNumber(String str) {
        if (!isItInteger(str))
            return false;
        if (str.length() < 3 || str.length() > 10)
            return false;
        return true;
    }

    public static boolean isValidSchedule(String str) {
        String days[] = str.split(",");
        if (days.length > 7)
            return false;

        String validDays[] = new String[]{"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
        Set<String> set = new HashSet<String>(Arrays.asList(validDays));

        for (String day : days) {
            if (!set.contains(day.toLowerCase()))
                return false;
        }

        return true;
    }

    public static boolean isValidSpecialisation(String specialization) {
        String validSpecialization[] = new String[]{"cardiology", "dermatology", "ent", "eyes", "general"};
        Set<String> set = new HashSet<String>(Arrays.asList(validSpecialization));

        if (!set.contains(specialization.toLowerCase()))
            return false;

        return true;
    }

    public static boolean isValidDesignation(String designation) {
        String validDesignation[] = new String[]{"juniordoctor", "seniordoctor", "specialist", "seniorspecialist","hod"};
        Set<String> set = new HashSet<String>(Arrays.asList(validDesignation));

        if (!set.contains(designation.toLowerCase()))
            return false;

        return true;
    }

    public static boolean isvalidDepartmentId(String deptID) {
        if (!isItInteger(deptID))
            return false;

        Connection connection;
        Statement statement;
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartHealthcareSystem?verifyServerCertificate=false&useSSL=true", "rjtmhy", "#Rjtmhy25");
            statement = connection.createStatement();
            String sql = "SELECT id FROM department WHERE id = '" + deptID + "';";
            rs = statement.executeQuery(sql);
            Set<Integer> set = new HashSet<>();

            while (rs.next()) {
                set.add(rs.getInt("id"));
            }

            if (set.contains(Integer.parseInt(deptID)))
                return true;
            else
                return false;
        }
        catch (SQLException exception)
        {
            System.out.println("SQLException"+exception.getMessage());
        }
        catch (ClassNotFoundException exception)
        {
            System.out.println("ClassNotFoundException"+exception.getMessage());
        }
        return false;
    }

    public static boolean isValidAppointmentId(String appointmentID) {


        Connection connection;
        Statement statement;
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartHealthcareSystem?verifyServerCertificate=false&useSSL=true", "rjtmhy", "#Rjtmhy25");
            statement = connection.createStatement();
            String sql = "SELECT id FROM appointment WHERE id = '" + appointmentID + "';";
            rs = statement.executeQuery(sql);
            Set<Integer> set = new HashSet<>();

            while (rs.next()) {
                set.add(rs.getInt("id"));
            }

            if (set.contains(appointmentID))
                return true;
            else
                return false;
        }
        catch (SQLException exception)
        {
            System.out.println("SQLException"+exception.getMessage());
        }
        catch (ClassNotFoundException exception)
        {
            System.out.println("ClassNotFoundException"+exception.getMessage());
        }
        return false;
    }

    public static boolean isValidDoctorId(String doctorID) {
        if (!isItInteger(doctorID))
            return false;

        Connection connection;
        Statement statement;
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartHealthcareSystem?verifyServerCertificate=false&useSSL=true", "rjtmhy", "#Rjtmhy25");
            statement = connection.createStatement();
            String sql = "SELECT id FROM doctor WHERE id = '" + doctorID + "';";
            rs = statement.executeQuery(sql);
            Set<Integer> set = new HashSet<>();

            while (rs.next()) {
                set.add(rs.getInt("id"));
            }

            if (set.contains(Integer.parseInt(doctorID)))
                return true;
            else
                return false;
        }
        catch (SQLException exception)
        {
            System.out.println("SQLException"+exception.getMessage());
        }
        catch (ClassNotFoundException exception)
        {
            System.out.println("ClassNotFoundException"+exception.getMessage());
        }
        return false;
    }

    public static boolean isValidPatientId(String patientID) {
        if (!isItInteger(patientID))
            return false;

        Connection connection;
        Statement statement;
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartHealthcareSystem?verifyServerCertificate=false&useSSL=true", "rjtmhy", "#Rjtmhy25");
            statement = connection.createStatement();
            String sql = "SELECT id FROM patient WHERE id = '" + patientID + "';";
            rs = statement.executeQuery(sql);
            Set<Integer> set = new HashSet<>();

            while (rs.next()) {
                set.add(rs.getInt("id"));
            }

            if (set.contains(Integer.parseInt(patientID)))
                return true;
            else
                return false;
        }
        catch (SQLException exception)
        {
            System.out.println("SQLException"+exception.getMessage());
        }
        catch (ClassNotFoundException exception)
        {
            System.out.println("ClassNotFoundException"+exception.getMessage());
        }
        return false;
    }
}

