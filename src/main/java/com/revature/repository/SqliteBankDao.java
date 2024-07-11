package com.revature.repository;

import com.revature.entity.BankAccount;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteBankDao implements BankDao {
    @Override
    public BankAccount createBankAccount(BankAccount newBankAccountInfo) {
        String sql = "insert into user values (?, ?)";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newBankAccountInfo.getUser().getUsername());
            preparedStatement.setDouble(2, newBankAccountInfo.getBalance());
            int result = preparedStatement.executeUpdate();
            if(result == 1)
            {
                return newBankAccountInfo;
            }
            throw new UserSQLException("User could not be created please try again.");
        }catch (SQLException exception)
        {
            throw new UserSQLException((exception.getMessage()));
        }
    }

    @Override
    public void deleteBankAccount(BankAccount bankAccountInfo) {
        String sql = "delete from account where username = ?";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bankAccountInfo.getUser().getUsername());
            int resultSet = preparedStatement.executeUpdate();
            if(resultSet > 0)
            {
                System.out.println("Account deleted successfully!");
            }
            else
            {
                System.out.println("Account not found.");
            }
            throw new UserSQLException("Account could not be deleted please try again.");
        }catch (SQLException exception)
        {
            throw new UserSQLException((exception.getMessage()));
        }
    }

    @Override
    public BankAccount updateBankAccount(BankAccount bankAccountInfo) {
            String sql = "update account where balance = ?";
            try(Connection connection = DatabaseConnector.createConnection())
            {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDouble(1, bankAccountInfo.getBalance());
                int resultSet = preparedStatement.executeUpdate();
                if(resultSet == 1)
                {
                    return bankAccountInfo;
                }
                else
                {
                    System.out.println("Account not found.");
                }
                throw new UserSQLException("Account could not be updated please try again.");
            }catch (SQLException exception)
            {
                throw new UserSQLException((exception.getMessage()));
            }
        }


    @Override
    public List<BankAccount> getAllBankAccounts() {
        
        String sql = "select * from account";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery((sql));
            List<BankAccount> accounts = new ArrayList<>();
            while(resultSet.next())
            {
                BankAccount bankRecord = new BankAccount();
                bankRecord.getUser().setUsername(resultSet.getString("username"));
                bankRecord.getUser().setPassword(resultSet.getString("password"));
                bankRecord.setBalance(resultSet.getDouble("balance"));
                accounts.add(bankRecord);
            }
            return accounts;
        }
        catch (SQLException exception)
        {
            throw new UserSQLException(exception.getMessage());
        }
    }
}
