package com.revature.entity;

import java.io.Serializable;
import java.util.Objects;

public class BankAccount implements Serializable {

    private int id;
    private int userid;
    private double balance;
    private String username;

    public BankAccount(){}

    public BankAccount(int userid, double balance, String username)
    {
        this.userid = userid;
        this.balance = balance;
        this.username = username;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userid;
    }

    public void setUserID(int userid) {
        this.userid = userid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return getId() == that.getId() && userid == that.userid && Double.compare(getBalance(), that.getBalance()) == 0 && Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), userid, getBalance(), getUsername());
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", userid=" + userid +
                ", balance=" + balance +
                ", username='" + username + '\'' +
                '}';
    }
}
