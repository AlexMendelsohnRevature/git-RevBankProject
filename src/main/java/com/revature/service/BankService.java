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
        for(BankAccount account : bankDao.getAllBankAccounts())
        {
            boolean hasAccount = account.getUser().hasAccount();
            if(!hasAccount)
            {
                account.getUser().setAccount(true);
                System.out.println("Account created successfully.");
                return bankDao.createBankAccount(new BankAccount(user, startingDeposit));
            }
        }
        throw new CreateAccountException("Account could not be created.");
    }

    public void closeAccount(BankAccount bankAccountInfo)
    {
        for(BankAccount account : bankDao.getAllBankAccounts())
        {
            boolean hasAccount = account.getUser().hasAccount();
            if(hasAccount)
            {
                account.getUser().setAccount(false);
                System.out.println("Account closed successfully.");
                bankDao.deleteBankAccount(bankAccountInfo);
            }
        }
        throw new CloseAccountException("Account could not be closed.");
    }

    public BankAccount updateBalance(BankAccount bankAccountInfo)
    {
        System.out.println("Your updated account balance is now:" + bankAccountInfo.getBalance());
        return bankDao.updateBankAccount(bankAccountInfo);
    }
}
