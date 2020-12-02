package payrollsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

class Employee {
     
    // employee attributes
    private String empName;
    private double hourlyPayRate, withHoldingTaxRate, stateTaxRate;
    private int noOfHoursPerWeek;
    
    private static double grossPay;
    
    // constructor 
    public Employee(String name, int noOfHrs, double hourlyPay, double withHoldingTax, double stateTax ){
        this.empName = name;
        this.noOfHoursPerWeek = noOfHrs;
        this.hourlyPayRate = hourlyPay;
        this.withHoldingTaxRate = withHoldingTax;
        this.stateTaxRate = stateTax;
        Employee.grossPay = noOfHrs * hourlyPay;
    }


    public String getEmpName() {
        return empName;
    }

    public int getNoOfHoursPerWeek() {
        return noOfHoursPerWeek;
    }
     
    public double getHourlyPayRate() {
        return hourlyPayRate;
    }

    public double getWithHoldingTax() {
        return withHoldingTaxRate;
    }

    public double getStateTax() {
        return stateTaxRate;
    }

    static class SalaryCalculations{
        
        public static double calcGrossPay(int noOfHoursPerWeek, double hourlyPayRate){
            return noOfHoursPerWeek * hourlyPayRate;
        }

        public static double calcWithHoldingTax(double grossPay, double withholdingTaxRate ){
            return grossPay * withholdingTaxRate;
        }

        public static double calcStateTax(double grossPay, double stateTaxRate){
            return grossPay * stateTaxRate;
        }

        public static double calcTotalDeductions(double withholdingTax, double stateTax){
            return withholdingTax + stateTax;
        }

        public static double calcNetPay(double grossPay, double totalDeductions){
            return grossPay - totalDeductions;
        }
    } // end of SalaryCalculations class
    
} // end of Employee class

//Runner
public class PayrollSystem {
    
    public static void main(String[] args) {
 
        System.out.println("========================================"
                       + "\n======       PAYROLL SYSTEM       ======" 
                       + "\n========================================");
        
        String name = null;
        int hoursWorked = 0;
        double hourlyRate = 0.0, withholdingTaxRate = 0.0, stateTaxRate = 0.0;
        
        // read inputs
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            
            System.out.print("Employee's Full Name: ");
            name = br.readLine();
            
            System.out.print("Number of Hours worked in a week: ");
            hoursWorked = Integer.parseInt(br.readLine());
            
            System.out.print("Hourly Pay Rate: ");
            hourlyRate = Double.parseDouble(br.readLine());
            
            System.out.print("Withholding Tax Rate (%): ");
            withholdingTaxRate = Double.parseDouble(br.readLine());
            
            System.out.print("State Tax Withholding Rate (%): ");
            stateTaxRate = Double.parseDouble(br.readLine());
            
        } catch (IOException | NumberFormatException ex) { 
            Logger.getLogger(PayrollSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        // create instance of class Employee
        Employee emp = new Employee(name, hoursWorked, hourlyRate, withholdingTaxRate/100, stateTaxRate/100);
        
        
        double grossPay = Employee.SalaryCalculations.calcGrossPay(emp.getNoOfHoursPerWeek(), emp.getHourlyPayRate());
        double withHoldingTax = Employee.SalaryCalculations.calcWithHoldingTax(grossPay, emp.getWithHoldingTax());
        double stateTax = Employee.SalaryCalculations.calcStateTax(grossPay, emp.getStateTax());
        double totalDeduction = Employee.SalaryCalculations.calcTotalDeductions(withHoldingTax, stateTax);
        double netPay = Employee.SalaryCalculations.calcNetPay(grossPay, totalDeduction);
        
        // for formatting
        Locale dollars = new Locale("en", "US");
        NumberFormat nf = NumberFormat.getCurrencyInstance(dollars);
        nf.setGroupingUsed(true);
        
        String information = "Employee's Full Name: " + emp.getEmpName() + "\nNumbers of Hours worked in a week: " + 
                    emp.getNoOfHoursPerWeek() + "\nHourly Rate: " + nf.format(emp.getHourlyPayRate()) + "\nGross Pay: " + 
                    nf.format(grossPay) + "\nDeductions: \nFederal Tax withholding Rate ("
                    + (int)withholdingTaxRate + "%): " + nf.format(withHoldingTax) + "\nState Tax withholding Rate (" + (int)stateTaxRate +
                    "%): " + nf.format(stateTax) + "\nTotal Deduction: " + nf.format(totalDeduction) + "\nNet Pay: " + nf.format(netPay);
        
        // Print output in message dialog box
        JOptionPane.showMessageDialog(null, information, "Payroll Statement", JOptionPane.INFORMATION_MESSAGE);
        
        }
    }  

