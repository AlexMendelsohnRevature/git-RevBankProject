package com.revature.repository;

import com.revature.entity.BankAccount;

import java.util.List;

public interface BankDao {

    BankAccount createBankAccount(BankAccount newBankAccountInfo);
    List<BankAccount> getAllBankAccounts();
}
