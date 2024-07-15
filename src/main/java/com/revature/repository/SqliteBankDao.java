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
        String sql = "insert into accounts (userid, balance, username) values (?, ?, ?)";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            long key = -1L;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, newBankAccountInfo.getUserID());
            preparedStatement.setDouble(2, newBankAccountInfo.getBalance());
            preparedStatement.setString(3, newBankAccountInfo.getUsername());
            int result = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(result == 1)
            {
                key = resultSet.getLong(1);
                newBankAccountInfo.setId((int)key);
                return newBankAccountInfo;
            }
            throw new UserSQLException("User could not be created please try again.");
        }catch (SQLException exception)
        {
            throw new UserSQLException((exception.getMessage()));
        }
    }

    @Override
    public List<BankAccount> getBankAccounts(int userid) {

        String sql = "select * from accounts where userid = ?";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BankAccount> bankAccounts = new ArrayList<>();
            while(resultSet.next())
            {
                BankAccount bankAccount = new BankAccount();
                bankAccount.setId(resultSet.getInt("id"));
                bankAccount.setUserID(resultSet.getInt("userid"));
                bankAccount.setBalance(resultSet.getDouble("balance"));
                bankAccount.setUsername(resultSet.getString("username"));
                bankAccounts.add(bankAccount);
            }
            return bankAccounts;
        }
        catch (SQLException exception)
        {
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public double getBalance(int userid) {
        String sql = "select balance from accounts where userid = ?";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();
            double balance = 0;
            if(resultSet.next())
            {
                balance =  resultSet.getDouble("balance");
            }
            return balance;
        }
        catch (SQLException exception)
        {
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public void deleteBankAccount(int id) {
        String sql = "delete from accounts where id = ?";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
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
    public BankAccount getBankAccount(int userid) {
        String sql = "select * from accounts where userid = ?";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();
            BankAccount bankAccount = new BankAccount();
            if(resultSet.next())
            {
                bankAccount.setId(resultSet.getInt("id"));
                bankAccount.setUserID(resultSet.getInt("userid"));
                bankAccount.setBalance(resultSet.getDouble("balance"));
                bankAccount.setUsername(resultSet.getString("username"));
            }
            return bankAccount;
        }
        catch (SQLException exception)
        {
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public BankAccount getBankAccountByID(int id) {
        String sql = "select * from accounts where id = ?";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            BankAccount bankAccount = new BankAccount();
            if(resultSet.next())
            {
                bankAccount.setId(resultSet.getInt("id"));
                bankAccount.setUserID(resultSet.getInt("userid"));
                bankAccount.setBalance(resultSet.getDouble("balance"));
                bankAccount.setUsername(resultSet.getString("username"));
            }
            return bankAccount;
        }
        catch (SQLException exception)
        {
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public BankAccount updateBankAccount(BankAccount bankAccountInfo, double updatedBalance) {
            String sql = "update accounts set balance = ? where id = ?";
            try(Connection connection = DatabaseConnector.createConnection())
            {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDouble(1, updatedBalance);
                preparedStatement.setInt(2, bankAccountInfo.getId());
                int rowCount = preparedStatement.executeUpdate();
                if(rowCount > 0)
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
    public BankAccount getAllBankAccounts() {
        
        String sql = "select * from accounts";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery((sql));
            BankAccount bankAccount = new BankAccount();
            while(resultSet.next())
            {
                bankAccount.setId(resultSet.getInt("id"));
                bankAccount.setUserID(resultSet.getInt("userid"));
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
