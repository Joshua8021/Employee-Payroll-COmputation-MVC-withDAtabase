package CS002FINAL;

import java.sql.*;
import javax.swing.*;
/**
 * Write a description of class RegularEmployee here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RegularEmployee extends PayrollModel implements Computation
{
    int id,days,overtimeHour,overtimeRate,holiday;
    float late;
    int salary;
    float deduction=0;
    RegularEmployee(){}
    RegularEmployee(int id,int days, int overtimeHour,int overtimeRate, int holiday, float late){
        this.id=id;
        this.days=days;
        this.overtimeHour=overtimeHour;
        this.overtimeRate=overtimeRate;
        this.holiday=holiday;
        this.late=late;
    }
    @Override
    public void deduction(){
        this.deduction = (getIncomeTax())+(getPhilhealth());
    }
    @Override
    public void salary(){
        this.salary = (days*getDailyRate(id))+(overtimeHour*overtimeRate+(holiday*(getDailyRate(id)*2)));
    }
    void setLate(){
        this.late = late*10;
    }
    float late(){
        return late;
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
    
    float getIncomeTax(){
        float x = 0;
        x = getTax(getDailyRate(id)*days);
        return x;
    }
    public float getDeduction(){
        return deduction;
    }
    public int getSalary(){
        return salary;
    }
}
