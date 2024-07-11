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
        String sql = "insert into account values (?, ?, ?)";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newBankAccountInfo.getUsername());
            preparedStatement.setString(2, newBankAccountInfo.getPassword());
            preparedStatement.setDouble(3, newBankAccountInfo.getBalance());
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
            preparedStatement.setString(1, bankAccountInfo.getUsername());
            int resultSet = preparedStatement.executeUpdate();
            if(resultSet > 0)
            {
                System.out.println("Account deleted successfully!");
            }
            else
            {
                System.out.println("Account not found.");
            }
        }catch (SQLException exception)
        {
            throw new UserSQLException((exception.getMessage()));
        }
    }

    @Override
    public BankAccount updateBankAccount(BankAccount bankAccountInfo) {
            String sql = "update account set username = ?, password = ?, balance = ?";
            try(Connection connection = DatabaseConnector.createConnection())
            {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, bankAccountInfo.getUsername());
                preparedStatement.setString(2, bankAccountInfo.getPassword());
                preparedStatement.setDouble(3, bankAccountInfo.getBalance());
                int resultSet = preparedStatement.executeUpdate();
                if(resultSet > 0)
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
    public BankAccount getBankAccount() {
        
        String sql = "select * from account";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery((sql));
            BankAccount bankAccount = new BankAccount();
            while(resultSet.next())
            {
                bankAccount.setUsername(resultSet.getString("username"));
                bankAccount.setPassword(resultSet.getString("password"));
                bankAccount.setBalance(resultSet.getDouble("balance"));

            }
            return bankAccount;
        }
        catch (SQLException exception)
        {
            throw new UserSQLException(exception.getMessage());
        }
    }
}
