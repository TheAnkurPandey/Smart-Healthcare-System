import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.*;

public class Department {
    private int departmentId;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    private String departmentName;
    private String departmentCode;

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://gaurav@localhost:3306/SHS?verifyServerCertificate=false&useSSL=true";

    //  Database credentials
    static final String DB_USER = "gaurav";
    static final String DB_PASS = "Root@123";

    public void addDepartment() throws IOException {
        SHS.printOptionsList("Add Department ", new String[]{"Please Enter the following details : "});
        System.out.println("Department Name");
        setDepartmentName(br.readLine());

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
           // System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "insert into department values(NULL,'" + getDepartmentName() + "','" + getDepartmentName().substring(0, 4) + "',NULL)";
            int i = stmt.executeUpdate(sql);
            if (i == 1) {
                System.out.println("Department Added Successfully...");

            } else {
                System.out.println("Database access failed!!!");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public void showDepartments () {
            SHS.printOptionsList("Departments ", new String[]{""});
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                //System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
               // System.out.println("Creating statement...");
                stmt = conn.createStatement();
                String sql = "select * from department";
                ResultSet rs = stmt.executeQuery(sql);
                if(rs!=null) {
                    System.out.println("DepartmentId" + "\t" + "DepartmentName" + "\t" + "DepartmentCode" + "\t" + "DepartmentHod");
                    while (rs.next()) {
                        System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4));

                    }
                }
                else
                {
                    System.out.println("No Department Exists!!!");
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }