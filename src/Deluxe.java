public class Deluxe {
    String num, name;
    double initialDeposit;
    int years;

    public Deluxe(String num, String name, double initialDeposit, int years) {
        this.num = num;
        this.name = name;
        this.initialDeposit = initialDeposit;
        this.years = years;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public double getInitialDeposit() {
        return initialDeposit;
    }

    public int getYears() {
        return years;
    }

    public void calculateInterest(){
        double rate = 0.15;
        double amount = initialDeposit * Math.pow(1 + (rate / 12), 12 * years);
        double cinterest = amount - initialDeposit;

    }

}
