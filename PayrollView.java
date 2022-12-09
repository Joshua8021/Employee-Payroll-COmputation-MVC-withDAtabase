package CS002FINAL;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.net.*;
import java.awt.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PayrollView extends JFrame {
    PayrollModel m = new PayrollModel();
    JFrame payrollframe = new JFrame();
    JDialog dialog;
    JFrame loadingframe = new JFrame();
    private JTextField late;
    private JTextField lateSlip;
    private JLabel employeetypelbl ;
    private JTextField employee;
    private JTextField workdays;
    private JTextField dailyrate;
    private JTextField overtime;
    private JTextField employeeID;
    private JTextField overtimeRate;
    private JTextField holiday;
    private JTextField thome;
    private JButton calculatebtn = new JButton("Compute");
    private JDialog login;
    private JDialog payslip = new JDialog();
    private JTextField employeename;
    private JTextField tax;
    private JTextField salary;
    private JTextField philhealth;
    private JTextField deduction;
    private JTextField total;
    private JTextField sss;
    private JRadioButton add = new JRadioButton("Add Employee");
    private JRadioButton del = new JRadioButton("Del Employee");
    private JRadioButton comp = new JRadioButton("Compute Salary",true);
    private JRadioButton updt = new JRadioButton("Update daily salary");
    private JRadioButton regular = new JRadioButton("Regular",true);
    private JRadioButton contractual = new JRadioButton("Contractual");
    private JButton proceedbtn = new JButton("Proceed:");
    JButton exitbtn = new JButton("Exit");
    JButton logoutbtn = new JButton("Logout");
    private JTextField usernameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginenterbtn = new JButton("Enter");
    private JTable disp;
    Connection con=null;
    public PayrollView() {

    }
    
    Connection getCon() {
        try{
            Statement statement = con.createStatement();
            String stm= "SELECT 1";
            statement.executeQuery(stm);
        }catch(Exception e){
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(m.getUrlDB(),m.getUserDB(),m.getPassDB());
        }catch(Exception ex){
            e.printStackTrace();
        }
        }
        return con;
    }
    

    //Login dialog
    void login() {
        login = new JDialog();
        login.setUndecorated(true);
        login.getContentPane().setLayout(null);
        login.setSize(350,200);
        login.setVisible(true);
        login.setLocationRelativeTo(null);
        JLabel userloginlbl = new JLabel("User Login");
        userloginlbl.setHorizontalAlignment(SwingConstants.CENTER);
        userloginlbl.setBounds(115, 21, 90, 23);
        login.getContentPane().add(userloginlbl);

        usernameField.setBounds(125, 56, 96, 19);
        login.getContentPane().add(usernameField);
        usernameField.setColumns(10);

        JLabel usernamelbl = new JLabel("Username:");

        usernamelbl.setHorizontalAlignment(SwingConstants.CENTER);
        usernamelbl.setBounds(37, 56, 74, 19);
        login.getContentPane().add(usernamelbl);

        JLabel passwordlbl = new JLabel("Password:");

        passwordlbl.setHorizontalAlignment(SwingConstants.CENTER);
        passwordlbl.setBounds(37, 85, 74, 19);
        login.getContentPane().add(passwordlbl);

        passwordField.setBounds(125, 85, 96, 19);
        login.getContentPane().add(passwordField);

        loginenterbtn.setBounds(126, 130, 85, 21);
        login.getContentPane().add(loginenterbtn);

        exitbtn.setBounds(125, 161, 85, 21);
        login.getContentPane().add(exitbtn);

    }

    void loginclose() {
        login.dispose();
    }

    //Payroll JFrame
    void payrollwindow(){
        loading();
        JPanel contentPane = new JPanel();
        payrollframe.setTitle("Automated Payroll System");
        payrollframe.setSize(550,800);
        payrollframe.setLocationRelativeTo(null);
        payrollframe.getContentPane().setLayout(null);
        payrollframe.setResizable(false);
        payrollframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
        payrollframe.setContentPane(contentPane);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JLabel titlelbl = new JLabel("Automated Payroll System");
        titlelbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
        titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
        titlelbl.setBounds(94, 10, 353, 48);
        contentPane.add(titlelbl);

        ButtonGroup grp = new ButtonGroup();
        add.setBounds(10, 70, 110 , 20);
        grp.add(add);
        contentPane.add(add);
        del.setBounds(130, 70, 110 , 20);
        grp.add(del);
        contentPane.add(del);
        comp.setBounds(260, 70, 130 , 20);
        grp.add(comp);
        contentPane.add(comp);
        updt.setBounds(390, 70, 150 , 20);
        grp.add(updt);
        contentPane.add(updt);

        JLabel employeeIDL = new JLabel("Enter Employee ID");
        employeeIDL.setBounds(76, 100, 129, 27);
        contentPane.add(employeeIDL);

        employeeID = new JTextField();
        employeeID.setBounds(215, 100, 163, 27);
        contentPane.add(employeeID);


        JLabel employeelbl = new JLabel("Enter employee name:");
        employeelbl.setBounds(76, 135, 129, 27);
        contentPane.add(employeelbl);

        employeetypelbl = new JLabel("Type of employee:");
        employeetypelbl.setVisible(false);
        employeetypelbl.setBounds(76, 172, 129, 27);
        contentPane.add(employeetypelbl);

        JLabel daysworkedlbl = new JLabel("Regular Days worked:");
        daysworkedlbl.setBounds(76, 213, 179, 27);
        contentPane.add(daysworkedlbl);

        JLabel dailyratelbl = new JLabel("Daily rate:");
        dailyratelbl.setBounds(76, 253, 129, 27);
        contentPane.add(dailyratelbl);

        JLabel overtimelbl = new JLabel("Overtime hours:");
        overtimelbl.setBounds(76, 294, 129, 27);
        contentPane.add(overtimelbl);
        
        JLabel latelbl = new JLabel("Days late:");
        latelbl.setBounds(285, 294, 129, 27);
        contentPane.add(latelbl);

        JLabel overtimeRatelbl = new JLabel("Overtime rate:");
        overtimeRatelbl.setBounds(76, 334, 129, 27);
        contentPane.add(overtimeRatelbl);

        JLabel holidaylbl = new JLabel("Holidays Worked:");
        holidaylbl.setBounds(285, 334, 129, 27);
        contentPane.add(holidaylbl);

        calculatebtn.setBounds(204, 371, 119, 34);
        contentPane.add(calculatebtn);

        employee = new JTextField();
        employee.setEditable(false);
        employee.setBounds(215, 139, 163, 27);
        contentPane.add(employee);
        employee.setColumns(10);

        regular.setBounds(220, 178, 103, 21);
        regular.setVisible(false);
        contentPane.add(regular);

        contractual.setBounds(335, 178, 103, 21);
        contractual.setVisible(false);
        contentPane.add(contractual);

        workdays = new JTextField();
        workdays.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    
                    if(Character.isLetter(c)&&!e.isAltDown()) {
                        e.consume();
                    }
                }
            });
        workdays.setBounds(215, 217, 52, 19);
        contentPane.add(workdays);
        workdays.setColumns(10);

        dailyrate = new JTextField();
        dailyrate.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();

                    if(Character.isLetter(c)&&!e.isAltDown()) {
                        e.consume();
                    }
                }
            });
        dailyrate.setColumns(10);
        dailyrate.setEditable(false);
        dailyrate.setBounds(215, 257, 52, 19);
        contentPane.add(dailyrate);

        overtime = new JTextField();
        overtime.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();

                    if(Character.isLetter(c)&&!e.isAltDown()) {
                        e.consume();
                    }
                }
            });
        overtime.setColumns(10);
        overtime.setBounds(215, 298, 52, 19);
        contentPane.add(overtime);
        
        late = new JTextField();
        late.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();

                    if(Character.isLetter(c)&&!e.isAltDown()) {
                        e.consume();
                    }
                }
            });
        late.setColumns(10);
        late.setBounds(395, 294, 52, 19);
        contentPane.add(late);

        overtimeRate = new JTextField();
        overtimeRate.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();

                    if(Character.isLetter(c)&&!e.isAltDown()) {
                        e.consume();
                    }
                }
            });
        overtimeRate.setColumns(10);
        overtimeRate.setBounds(215, 338, 52, 19);
        contentPane.add(overtimeRate);

        holiday = new JTextField();
        holiday.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();

                    if(Character.isLetter(c)&&!e.isAltDown()) {
                        e.consume();
                    }
                }
            });
        holiday.setColumns(10);
        holiday.setBounds(394, 338, 52, 19);
        contentPane.add(holiday);

        ButtonGroup a = new ButtonGroup();
        a.add(regular);
        a.add(contractual);

        logoutbtn.setBounds(22, 408, 85, 21);
        contentPane.add(logoutbtn);

        payrollframe.setVisible(true);
        
        /** Scroll pane for database **/
        dialog.toFront();
        disp = new JTable();
        disp.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        try{
            con=getCon();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM employee" );
            ResultSet res = statement.executeQuery();
            disp.setModel(DbUtils.resultSetToTableModel(res));
        }catch(Exception e){e.printStackTrace();}
        JScrollPane pane = new JScrollPane(disp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setBounds(130, 438, 315, 180);
        contentPane.add(pane);
        dialog.dispose();
    }
    
        /**Loading Screen**/
    void loading(){
        dialog = new JDialog();
        dialog.setTitle("Fetching data from database... Please Wait..");
        dialog.setLocationRelativeTo(payrollframe);
        dialog.isAlwaysOnTop();
        dialog.setSize(350,0);
        //add label
        dialog.setVisible(true);
    }
    
    void payslip(RegularEmployee reg, ContractualEmployee con) {
        if(con==null){
            
            /** REGULAR EMPLOYEE PAYSLIP**/
            
        reg.deduction();reg.salary();reg.setLate();
        payslip.getContentPane().setLayout(null);
        payslip.setSize(400,400);
        payslip.setVisible(true);
        payslip.setLocationRelativeTo(null);
        JLabel paysliplbl = new JLabel("Payslip:");
        paysliplbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
        paysliplbl.setHorizontalAlignment(SwingConstants.CENTER);
        paysliplbl.setBounds(97, 16, 167, 34);
        payslip.getContentPane().add(paysliplbl);

        dialog.toFront();
        JLabel employeeNamelbl = new JLabel("Employee Name:");
        employeeNamelbl.setBounds(38, 54, 142, 21);
        payslip.getContentPane().add(employeeNamelbl);

        JLabel ssslbl = new JLabel("SSS Contribution:");
        ssslbl.setBounds(38, 74, 142, 21);
        payslip.getContentPane().add(ssslbl);
        sss = new JTextField();
        sss.setText(Float.toString(m.getSSS(reg.getSalary()))+" PHP");
        sss.setBorder(null);
        sss.setEditable(false);
        sss.setBounds(200 ,74 , 142, 21);
        payslip.getContentPane().add(sss);

        JLabel taxlbl = new JLabel("Tax Withheld:");
        taxlbl.setBounds(38, 94, 142, 21);
        payslip.getContentPane().add(taxlbl);
        tax = new JTextField();
        float taxes = reg.getIncomeTax();
        tax.setText(Float.toString(taxes*100)+"%");
        tax.setBorder(null);
        tax.setEditable(false);
        tax.setBounds(200 ,94 , 142, 21);
        payslip.getContentPane().add(tax);
        
        JLabel philhealthlbl = new JLabel("PhilHealth:");
        philhealthlbl.setBounds(38, 114, 142, 21);
        payslip.getContentPane().add(philhealthlbl);
        philhealth = new JTextField();
        float pHealth = m.getPhilhealth();
        philhealth.setText(Float.toString(pHealth*100)+"%");
        philhealth.setBorder(null);
        philhealth.setEditable(false);
        philhealth.setBounds(200 ,114 , 142, 21);
        payslip.getContentPane().add(philhealth);

        JLabel salarylbl = new JLabel("Salary:");
        salarylbl.setBounds(38, 134, 142, 21);
        payslip.getContentPane().add(salarylbl);
        salary = new JTextField();
        salary.setText(Float.toString(reg.getSalary()));
        salary.setBorder(null);
        salary.setEditable(false);
        salary.setBounds(200 ,134 , 142, 21);
        payslip.getContentPane().add(salary);
        
        JLabel latelbl = new JLabel("Tardiness Deduction:");
        latelbl.setBounds(38, 154, 142, 21);
        payslip.getContentPane().add(latelbl);
        lateSlip= new JTextField();
        lateSlip.setText(Float.toString(reg.late())+" PHP");
        lateSlip.setBorder(null);
        lateSlip.setEditable(false);
        lateSlip.setBounds(200 ,154 , 142, 21);
        payslip.getContentPane().add(lateSlip);
        
        
        JLabel deductionlbl = new JLabel("Total Deduction Percentage:");
        deductionlbl.setBounds(20, 224, 182, 21);
        payslip.getContentPane().add(deductionlbl);
        deduction = new JTextField();
        float deduct=reg.getDeduction();
        deduction.setText(Float.toString(deduct*100)+"%");
        deduction.setBorder(null);
        deduction.setEditable(false);
        deduction.setBounds(254 ,224 , 142, 21);
        payslip.getContentPane().add(deduction);
        
        JLabel Totalbl = new JLabel("Total Deduction in PHP:");
        Totalbl.setBounds(20, 254, 192, 21);
        payslip.getContentPane().add(Totalbl);
        total = new JTextField();
        String late = Float.toString(reg.late());
        float Tdeduct = (reg.late())+(deduct*reg.getSalary())+(m.getSSS(reg.getSalary()));
        total.setText(Float.toString(Tdeduct));
        total.setBorder(null);
        total.setEditable(false);
        total.setBounds(254 ,254 , 142, 21);
        payslip.getContentPane().add(total);
        
        JLabel Thomelbl = new JLabel("Total Takehome pay (salary PHP - deduction PHP):");
        Thomelbl.setBounds(20, 284, 290, 21);
        payslip.getContentPane().add(Thomelbl);
        thome = new JTextField();
        thome.setText(Float.toString(Float.parseFloat(salary.getText())-Float.parseFloat(total.getText())));
        thome.setBorder(null);
        thome.setEditable(false);
        thome.setBounds(320 ,284 , 142, 21);
        payslip.getContentPane().add(thome);


        proceedbtn.setBounds(141, 321, 85, 21);
        payslip.getContentPane().add(proceedbtn);

        employeename = new JTextField();
        employeename.setText(reg.getName());
        employeename.setEditable(false);
        employeename.setBounds(200, 54, 203, 21);
        payslip.getContentPane().add(employeename);
        employeename.setColumns(10);
        employeename.setBorder(null);
        payslip.toFront();
        dialog.dispose();
       }
       
        /**Contratual Employee**/
        else if(reg==null){
        con.deduction();con.salary();
        payslip.getContentPane().setLayout(null);
        payslip.setSize(400,400);
        payslip.setVisible(true);
        payslip.setLocationRelativeTo(null);
        JLabel paysliplbl = new JLabel("Payslip:");
        paysliplbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
        paysliplbl.setHorizontalAlignment(SwingConstants.CENTER);
        paysliplbl.setBounds(97, 26, 167, 34);
        payslip.getContentPane().add(paysliplbl);

        dialog.toFront();
        JLabel employeeNamelbl = new JLabel("Employee Name:");
        employeeNamelbl.setBounds(38, 74, 142, 34);
        payslip.getContentPane().add(employeeNamelbl);
        
        
        JLabel latelbl = new JLabel("Tardiness Deduction:");
        latelbl.setBounds(38, 104, 142, 21);
        payslip.getContentPane().add(latelbl);
        lateSlip = new JTextField();
        lateSlip.setText(Float.toString(con.late())+" PHP");
        lateSlip.setBorder(null);
        lateSlip.setEditable(false);
        lateSlip.setBounds(200 ,104 , 142, 21);
        payslip.getContentPane().add(lateSlip);


        JLabel salarylbl = new JLabel("Salary:");
        salarylbl.setBounds(38, 134, 142, 21);
        payslip.getContentPane().add(salarylbl);
        salary = new JTextField();
        salary.setText(Float.toString(con.getSalary()));
        salary.setBorder(null);
        salary.setEditable(false);
        salary.setBounds(200 ,134 , 142, 21);
        payslip.getContentPane().add(salary);
        
        JLabel Totalbl = new JLabel("Total Deduction in PHP:");
        Totalbl.setBounds(20, 262, 192, 21);
        payslip.getContentPane().add(Totalbl);
        total = new JTextField();
        total.setText(Float.toString((con.late())));
        total.setBorder(null);
        total.setEditable(false);
        total.setBounds(254 ,262 , 142, 21);
        payslip.getContentPane().add(total);
        
        JLabel Thomelbl = new JLabel("Total Takehome pay (salary PHP - deduction PHP):");
        Thomelbl.setBounds(20, 292, 290, 21);
        payslip.getContentPane().add(Thomelbl);
        thome = new JTextField();
        thome.setText(Float.toString(con.getSalary()-con.getDeduction()));
        thome.setBorder(null);
        thome.setEditable(false);
        thome.setBounds(320 ,292 , 142, 21);
        payslip.getContentPane().add(thome);


        proceedbtn.setBounds(141, 321, 85, 21);
        payslip.getContentPane().add(proceedbtn);

        employeename = new JTextField();
        employeename.setText(con.getName());
        employeename.setEditable(false);
        employeename.setBounds(200, 81, 203, 21);
        payslip.getContentPane().add(employeename);
        employeename.setColumns(10);
        employeename.setBorder(null);
        payslip.toFront();
        dialog.dispose();
        }
       
    }
    
    void refreshTable(){
        try{
            con=getCon();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM employee" );
            ResultSet res = statement.executeQuery();

            disp.setModel(DbUtils.resultSetToTableModel(res));
            ((DefaultTableModel)disp.getModel()).fireTableDataChanged();
            statement.close();
        }catch(Exception e){e.printStackTrace();}
        dialog.dispose();
    }


        /** Input Getters**/
    String getEmployeeIDVis(){
        return employeeID.getText();
    }

    String getHolidays(){
        return holiday.getText();
    }

    String getOvertimeRate(){
        return overtimeRate.getText();
    }

    String getEmployeeVis(){
        return employee.getText();
    }

    String getOvertimeHour(){
        return overtime.getText();
    }

    String getEmployeeDaysVis(){
        return workdays.getText();
    }
    
    Float getLate(){
        Float x= Float.parseFloat(late.getText());
        return x;
    }
    
    String getDailyRateVis(){
        return dailyrate.getText();
    }
        /**Set the Input Fields on or off**/
    void setEmployeeIDVis(boolean b){
        employeeID.setText("");
        employeeID.setEditable(b);
    }

    void setEmployeeVis(boolean b){
        employee.setText("");
        employee.setEditable(b);
    }
    
    void setOvertimeRate(boolean b){
        overtimeRate.setText("");
        overtimeRate.setEditable(b);
    }
    
    void setHoliday(boolean b){
        holiday.setText("");
        holiday.setEditable(b);
    }
    
    void setLate(boolean b){
        late.setText("");
        late.setEditable(b);
    }
    
    void setTypeOnOff(boolean b){
        regular.setSelected(true);
        regular.setVisible(b);
        contractual.setVisible(b);
        employeetypelbl.setVisible(b);
    }

    void setWorkdaysVis(boolean b){
        workdays.setText("");
        workdays.setEditable(b);
    }

    void setDailyRateVis(boolean b){
        dailyrate.setText("");
        dailyrate.setEditable(b);
    }

    void setOvertimeVis(boolean b){
        overtime.setText("");
        overtime.setEditable(b);
    }
    
    void clrFields(){
     employee.setText("");
      workdays.setText("");
     dailyrate.setText("");
     overtime.setText("");
     employeeID.setText("");
     overtimeRate.setText("");
     holiday.setText("");
     late.setText("");
    }

    public String getUsername() {
        String username = usernameField.getText();
        return username;
    }

    public String getPassword() {
        String password = passwordField.getText();
        return password;
    }

    void clearlogin () {
        usernameField.setText("");
        passwordField.setText("");
    }

    void closePayslip() {
        payslip.dispose();
        /** Clears the receipt contents**/
        payslip = new JDialog();
    }
    
    void payrollwindowclose () {
        payrollframe.dispose();
    }

    void setOvertimeEnabled() {
        overtime.setEnabled(true);
    }

    void setOvertimeFalse() {
        overtime.setEnabled(false);
        overtime.setText("");
    }

    void modCalculateBtn(String s){
        calculatebtn.setText(s);
    }

    //Action listeners for controller
    void addProcListener(ActionListener listenForprc){
        /** Add listeners for RadioButton on top **/
        add.addActionListener(listenForprc);
        del.addActionListener(listenForprc);
        comp.addActionListener(listenForprc);
        updt.addActionListener(listenForprc);
    }

    void addCalculateListener(ActionListener listenForcalculatebtn) {
        calculatebtn.addActionListener(listenForcalculatebtn);
    }

    void addRegularListener(ActionListener listenForregular) {
        regular.addActionListener(listenForregular);
    }

    void addContractualListener(ActionListener listenForcontractual) {
        contractual.addActionListener(listenForcontractual);
    }

    void addProceedListener(ActionListener listenForproceedbtn) {
        proceedbtn.addActionListener(listenForproceedbtn);
    }

    void addLoginListener(ActionListener listenForloginbtn) {
        loginenterbtn.addActionListener(listenForloginbtn);
    }

    void addLogoutListener(ActionListener listenForlogoutbtn) {
        logoutbtn.addActionListener(listenForlogoutbtn);
    }

    void ExitLogin(ActionListener listenForexitbtn) {
        exitbtn.addActionListener(listenForexitbtn);
    }

}
