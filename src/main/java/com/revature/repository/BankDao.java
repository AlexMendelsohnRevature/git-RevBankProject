package com.revature.repository;

import com.revature.entity.BankAccount;

import java.util.List;

public interface BankDao {

    BankAccount createBankAccount(BankAccount newBankAccountInfo);
    List<BankAccount> getBankAccounts(int userid);
    double getBalance(int userid);
    void deleteBankAccount(int id);
    BankAccount getBankAccount(int userid);
    BankAccount getBankAccountByID(int id);
    BankAccount updateBankAccount(BankAccount bankAccountInfo, double updatedBalance);
    BankAccount getAllBankAccounts();
}
