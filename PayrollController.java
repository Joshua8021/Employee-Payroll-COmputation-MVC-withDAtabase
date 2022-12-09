package CS002FINAL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Write a description of class c here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PayrollController {
    private PayrollView view;
    private PayrollModel model;
    private String type="regular";
    
    PayrollController(){
        
    }
    public PayrollController(PayrollView view, PayrollModel model) {
        this.view = view;
        this.model = model;
        this.view.addProcListener(new prc());
        this.view.addCalculateListener(new CalculateListener());
        this.view.addRegularListener(new RegularListener());
        this.view.addContractualListener(new ContractualListener());
        this.view.addProceedListener(new ProceedListener());
        this.view.addLoginListener(new LoginListener());
        this.view.addLogoutListener(new LogoutListener());
        this.view.ExitLogin(new ExitLogin());
    }
   
    class prc implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton prc = (JRadioButton) e.getSource();
            String proc = prc.getText();
            if(proc.equals("Update daily salary")){
                view.modCalculateBtn("Update");
                view.setEmployeeVis(false);
                view.setDailyRateVis(true);
                view.setOvertimeVis(false);
                view.setEmployeeIDVis(true);
                view.setWorkdaysVis(false);
                view.setOvertimeRate(false);
                view.setHoliday(false);
                view.setTypeOnOff(false);
                view.setLate(false);
            }
            else if(proc.equals("Add Employee")){
                view.modCalculateBtn("Add");
                view.setEmployeeVis(true);
                view.setDailyRateVis(true);
                view.setOvertimeVis(false);
                view.setEmployeeIDVis(false);
                view.setWorkdaysVis(false);
                view.setOvertimeRate(false);
                view.setHoliday(false);
                view.setTypeOnOff(true);
                view.setLate(false);
                type="regular";
            }
            else if(proc.equals("Del Employee")){
                view.modCalculateBtn("Delete");
                view.setEmployeeVis(false);
                view.setDailyRateVis(false);
                view.setOvertimeVis(false);
                view.setEmployeeIDVis(true);
                view.setWorkdaysVis(false);
                view.setOvertimeRate(false);
                view.setHoliday(false);
                view.setTypeOnOff(false);
                view.setLate(false);
            }
            else if(proc.equals("Compute Salary")){
                view.modCalculateBtn("Compute");
                view.setEmployeeVis(false);
                view.setDailyRateVis(false);
                view.setOvertimeVis(true);
                view.setEmployeeIDVis(true);
                view.setWorkdaysVis(true);
                view.setOvertimeRate(true);
                view.setHoliday(true);
                view.setTypeOnOff(false);
                view.setLate(true);
            }
        }
    }
    
    class CalculateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           view.loading();
            try{
            JButton proc = (JButton) e.getSource();
            String btn = proc.getText();
            if(btn.equals("Add")){
                new PayrollModel(view.getEmployeeVis(),type,Integer.parseInt(view.getDailyRateVis()));
                view.refreshTable();
            }
            else if(btn.equals("Delete")){
                boolean x = model.isIDValid(Integer.parseInt(view.getEmployeeIDVis()));
                if(x){
                model.deleteEmployee(Integer.parseInt(view.getEmployeeIDVis()));
                view.refreshTable();}
            }
            else if(btn.equals("Update")){
                boolean x = model.isIDValid(Integer.parseInt(view.getEmployeeIDVis()));
                if(x){
                model.updateEmployee(Integer.parseInt(view.getEmployeeIDVis()),Integer.parseInt(view.getDailyRateVis()));
                view.refreshTable();}
            }
            else if(btn.equals("Compute")){
                boolean x = model.isIDValid(Integer.parseInt(view.getEmployeeIDVis()));
                if(x){
                if(model.getEmployeeType(Integer.parseInt(view.getEmployeeIDVis())).equals("regular")){
                    RegularEmployee reg= new RegularEmployee(Integer.parseInt(view.getEmployeeIDVis()),
                    Integer.parseInt(view.getEmployeeDaysVis()), Integer.parseInt(view.getOvertimeHour()),
                    Integer.parseInt(view.getOvertimeRate()),Integer.parseInt(view.getHolidays()),view.getLate());
                    view.payslip(reg,null);
                }
                else if(model.getEmployeeType(Integer.parseInt(view.getEmployeeIDVis())).equals("contractual")){
                    ContractualEmployee con= new ContractualEmployee(Integer.parseInt(view.getEmployeeIDVis()),
                    Integer.parseInt(view.getEmployeeDaysVis()), Integer.parseInt(view.getOvertimeHour()),
                    Integer.parseInt(view.getOvertimeRate()),view.getLate());
                    view.payslip(null,con);}
                }
            }
            view.clrFields();}
           catch(Exception ae){
               JOptionPane.showMessageDialog(null,"Invalid Input/s! ");
               view.dialog.dispose();
            }
           view.dialog.dispose();
      }
    }
    
    class RegularListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            type = "regular";
        }
    }
    
    class ContractualListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {      
            type = "contractual";
        }        
    }
    
    class ProceedListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.closePayslip();            
        }        
    }
    
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String Username = view.getUsername();
            @SuppressWarnings("deprecation")
            String Password = view.getPassword();

            if (Username.equals("admin") && Password.equals("admin123")) {
                JOptionPane.showMessageDialog(null, "Login Successful");
                view.loginclose();
                view.payrollwindow();
            }
            else {
                JOptionPane.showMessageDialog(null, "Username or Password mismatch ");
            }            
        }        
    }
    
    class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.payrollwindowclose();
            view.login();
            view.clearlogin();    
        }        
    }
    
    class ExitLogin implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }        
    }
    
}