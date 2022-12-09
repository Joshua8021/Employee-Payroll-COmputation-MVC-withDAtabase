package CS002FINAL;

import java.sql.*;
import javax.swing.*;
//model
public class PayrollModel{
    private String fullname;
    private double workdays;
    private double overtimehours;
    private double dailyrate;
    private double regularsalary;
    private double contractualsalary;
    private double basicpay;
    private double overtimepay;
    private String userDB = "yourUser";
    private String passDB = "yourUserPassw";
    private String urlDB = "jdbc:mysql://yourDBurl";
    Connection con = null;
    PayrollModel(){
        
    }
    
    Connection getCon() {
        //Keep DB connection alive Fix.
        try{
            Statement statement = con.createStatement();
            String stm= "SELECT 1";
            statement.executeQuery(stm);
        }catch(Exception e){
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(urlDB,userDB,passDB);
        }catch(Exception ex){
            e.printStackTrace();
        }
        }
        return con;
    }
    PayrollModel(String name , String type, int dailyRate){
          if(type.equals("regular")||type.equals("contractual")){
        try{
            con = getCon();
            Statement statement = con.createStatement();
            String stm = "INSERT INTO employee (name, type, dailyRate) VALUES('"+name+"','"+type+"','"+dailyRate+"')";
            statement.executeUpdate(stm);
            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{JOptionPane.showMessageDialog(null,"Employee added to database!");
            
        }
      }
      else{JOptionPane.showMessageDialog(null,"Please select Employee Type!");}
    }
    float getPhilhealth(){
        float x=0;
                     try{
            con = getCon();
            PreparedStatement statement = con.prepareStatement("SELECT contribution FROM philhealth");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                    x = res.getFloat("contribution");
            }
            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return x;
    }
    boolean isIDValid(int ID){
        int x=0;
                     try{
            con = getCon();
            PreparedStatement statement = con.prepareStatement("SELECT ID FROM employee WHERE ID='"+ID+"'");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                    x = res.getInt("ID");
            }
            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(x!=ID){
            JOptionPane.showMessageDialog(null," ID not found in database!");
            return false;
        }
        else{
            return true;
        }
    }
    float getTax(double totalSalary){
        float x=0;
                     try{
            con = getCon();
            PreparedStatement statement = con.prepareStatement("SELECT salary_range,salary_deduct FROM tax");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                if(totalSalary<=res.getDouble("salary_range")){
                    x=res.getFloat("salary_deduct");
                    break;
                }
            }
            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return x;
    }
    void deleteEmployee(int delID){
        try{
            con = getCon();
            Statement statement = con.createStatement();
            String stm = "DELETE FROM employee WHERE ID ="+delID;
            statement.executeUpdate(stm);
            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{JOptionPane.showMessageDialog(null,"Employee Removed!");
            
        }
    }
    
    void updateEmployee(int ID,int newRate){
        try{
            con = getCon();
            Statement statement = con.createStatement();
            String stm = "UPDATE employee SET dailyRate="+newRate+" WHERE ID="+ID;
            statement.executeUpdate(stm);
            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{JOptionPane.showMessageDialog(null,"Employee rate updated!");
           
        }
    }
    int getDailyRate(int id){
        int x=0;
                     try{
            con = getCon();
            PreparedStatement statement = con.prepareStatement("SELECT dailyRate FROM employee WHERE ID='"+id+"'");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                    x = res.getInt("dailyRate");
            }
            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return x;
    }
    String getEmployeeType(int ID){
        String x = "";
                     try{
            con = getCon();
            PreparedStatement statement = con.prepareStatement("SELECT type FROM employee WHERE ID='"+ID+"'");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                x=res.getString("type");
            }
            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return x;
    }
    
    public float getSSS(int totalSalary){
        float x=0;
                     try{
            con = getCon();
            PreparedStatement statement = con.prepareStatement("SELECT salary_range,msc,employee_contribution FROM sss");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                if(totalSalary<=res.getDouble("salary_range")){
                    x=(res.getFloat("msc")*res.getFloat("employee_contribution"));
                    break;
                }
            }
            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return x;
    }
    
    public String getUserDB(){
        return userDB;
    }
    public String getPassDB(){
        return passDB;
    }
    public String getUrlDB(){
        return urlDB;
    }
}
