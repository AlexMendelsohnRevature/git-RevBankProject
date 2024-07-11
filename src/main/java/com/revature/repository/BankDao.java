package com.revature.repository;

import com.revature.entity.BankAccount;

import java.util.List;

public interface BankDao {

    BankAccount createBankAccount(BankAccount newBankAccountInfo);
    void deleteBankAccount(BankAccount bankAccountInfo);
    BankAccount updateBankAccount(BankAccount bankAccountInfo);
    List<BankAccount> getAllBankAccounts();
}
