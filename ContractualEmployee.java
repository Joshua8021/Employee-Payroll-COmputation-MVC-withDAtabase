package CS002FINAL;

import java.sql.*;
import javax.swing.*;
/**
 * Write a description of class ContractualEmployee here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ContractualEmployee extends PayrollModel implements Computation
{
    int id,days,overtimeHour,overtimeRate;
    float deduction=0,late;
    int count,salary;
    ContractualEmployee(){}
    ContractualEmployee(int id , int days, int overtimeHour, int overtimeRate,float late){
        this.id=id;
        this.days=days;
        this.overtimeHour=overtimeHour;
        this.overtimeRate=overtimeRate;
        this.late=late;
    }
    @Override
     public void deduction(){
      this.deduction = late*10;
    }
    @Override
     public void salary(){
      this.salary = (days*getDailyRate(id))+(overtimeHour*overtimeRate);
    }
    float late(){
        return late*10;
    }
    String getName(){
        String x="";
                     try{
            con = getCon();
            PreparedStatement statement = con.prepareStatement("SELECT name FROM employee WHERE ID='"+id+"'");
            ResultSet res = statement.executeQuery();
            while(res.next()){
                    x = res.getString("name");
            }
            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return x;
    }
    public float getDeduction(){
        return deduction;
    }
    public int getSalary(){
        return salary;
    }
}
