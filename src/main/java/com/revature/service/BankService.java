package com.revature.service;

import com.revature.entity.BankAccount;
import com.revature.exception.CreateAccountException;
import com.revature.repository.BankDao;

public class BankService {

    private BankDao bankDao;

    public BankService(BankDao bankDao)
    {
        this.bankDao = bankDao;
    }

    public BankAccount openAccount(BankAccount bankAccountInfo)
    {
        for(BankAccount account : bankDao.getAllBankAccounts())
        {
            boolean hasAccount = account.getUser().hasAccount();
            if(!hasAccount)
            {
                account.getUser().setAccount(true);
                System.out.println("Account created successfully.");
                return bankAccountInfo;
            }
        }
        throw new CreateAccountException("Account could not be created.");
    }

    public BankAccount closeAccount(BankAccount bankAccountInfo)
    {
        return null;
    }

    public BankAccount withdrawFromAccount(BankAccount bankAccountInfo)
    {
        return null;
    }

    public BankAccount depositIntoAccount(BankAccount bankAccountInfo)
    {
        return null;
    }
}
