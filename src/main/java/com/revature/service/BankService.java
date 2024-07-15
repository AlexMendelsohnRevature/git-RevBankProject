package com.revature.service;

import com.revature.entity.BankAccount;

import com.revature.repository.BankDao;

import java.util.List;

public class BankService {

    private BankDao bankDao;

    public BankService(BankDao bankDao)
    {
        this.bankDao = bankDao;
    }


    public BankAccount openAccount(int userid, double startingDeposit, String username)
    {
        System.out.println("New Checking Account created with a starting balance of: $" + startingDeposit);
        return bankDao.createBankAccount(new BankAccount(userid, startingDeposit, username));
    }

    public void closeAccount(int id)
    {
        bankDao.deleteBankAccount(id);
    }

    public BankAccount updateBalance(int id, String s, double amount)
    {

        BankAccount currentAccount = bankDao.getBankAccountByID(id);

        double balance = getBalance(currentAccount.getUserID());
        if(s.equals("w"))
        {
            if(currentAccount.getBalance() - amount > 0) {
                balance -= amount;
                BankAccount updatedAccount = bankDao.updateBankAccount(currentAccount, balance);
                System.out.println("Your updated account balance is now: $" + balance);
                return updatedAccount;
            }
            else {
                System.out.println("Cannot drop balance below $0.");
                return currentAccount;
            }
        }
        else if(s.equals("d"))
        {
            balance += amount;
            BankAccount updatedAccount = bankDao.updateBankAccount(currentAccount, balance);
            System.out.println("Your updated account balance is now: $" + balance);
            return updatedAccount;
        }
        else
        {
            return null;
        }
    }

    public double getBalance(int userid)
    {
        return bankDao.getBalance(userid);
    }

    public List<BankAccount> getAccounts(int userid)
    {
        return bankDao.getBankAccounts(userid);
    }
}
