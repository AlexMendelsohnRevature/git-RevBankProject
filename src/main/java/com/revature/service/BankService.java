package com.revature.service;

import com.revature.entity.BankAccount;
import com.revature.entity.User;
import com.revature.exception.CloseAccountException;
import com.revature.exception.CreateAccountException;
import com.revature.repository.BankDao;

public class BankService {

    private BankDao bankDao;

    public BankService(BankDao bankDao)
    {
        this.bankDao = bankDao;
    }


    public BankAccount openAccount(User user, double startingDeposit)
    {
        return bankDao.createBankAccount(new BankAccount(user.getUsername(), user.getPassword(), startingDeposit));
    }

    public void closeAccount(BankAccount bankAccountInfo)
    {
        bankDao.deleteBankAccount(bankAccountInfo);
    }

    public BankAccount updateBalance(BankAccount bankAccountInfo)
    {
        System.out.println("Your updated account balance is now:" + bankAccountInfo.getBalance());
        return bankDao.updateBankAccount(bankAccountInfo);
    }

    public BankAccount getAccount()
    {
        return bankDao.getBankAccount();
    }
}
