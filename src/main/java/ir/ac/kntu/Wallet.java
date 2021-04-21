package ir.ac.kntu;

public class Wallet {

    private double balance;

    public Wallet() {
    }

    public Wallet(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double add) {
        balance += add;
    }
}
