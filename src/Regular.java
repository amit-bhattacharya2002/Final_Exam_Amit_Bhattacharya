import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Regular extends JFrame implements Compound_Interest_Interface{

    JFrame form = new JFrame();
    String num, name;
    double initialDeposit, amount, cinterest;
    int years;

    public Regular(String num, String name, double initialDeposit, int years) {
        this.num = num;
        this.name = name;
        this.initialDeposit = initialDeposit;
        this.years = years;
    }

    public String getNum() {
        return num;
    }

    @Override
    public String getName() {
        return name;
    }

    public double getInitialDeposit() {
        return initialDeposit;
    }

    public double getAmount() {
        return amount;
    }

    public double getCinterest() {
        return cinterest;
    }

    public int getYears() {
        return years;
    }

    public void calculateInterest(int year){
        double rate = 0.1;
        amount = initialDeposit * Math.pow(1 + (rate / 12), 12 * year);
        cinterest = amount - initialDeposit;

    }

    @Override
    public void generateTable() {


    }


}
