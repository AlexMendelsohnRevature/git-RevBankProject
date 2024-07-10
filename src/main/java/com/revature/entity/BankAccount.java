package com.revature.entity;

import java.io.Serializable;
import java.util.Objects;

public class BankAccount implements Serializable {

    private User user;

    private double balance;

    public BankAccount(){}

    public BankAccount(User user, double balance)
    {
        this.user = user;
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Double.compare(getBalance(), that.getBalance()) == 0 && Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getBalance());
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "user=" + user +
                ", balance=" + balance +
                '}';
    }
}
