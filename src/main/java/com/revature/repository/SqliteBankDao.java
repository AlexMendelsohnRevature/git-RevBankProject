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
        String sql = "insert into account (username, balance) values (?, ?)";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newBankAccountInfo.getUsername());
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
        String sql = "delete from account where id = ?";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bankAccountInfo.getId());
            int rowCount = preparedStatement.executeUpdate();
            if(rowCount > 0)
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
            //Updates everything besides ID
            String sql = "update account set username = ?, balance = ? where id = ?";
            try(Connection connection = DatabaseConnector.createConnection())
            {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, bankAccountInfo.getUsername());
                preparedStatement.setDouble(2, bankAccountInfo.getBalance());
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
                bankAccount.setId(resultSet.getInt("id"));
                bankAccount.setUsername(resultSet.getString("username"));
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
