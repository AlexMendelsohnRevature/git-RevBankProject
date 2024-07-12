package com.revature.repository;

import com.revature.entity.User;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDao implements UserDao {


    @Override
    public User createUser(User newUserCredentials) {

        String sql = "insert into user (username, password, loggedIn) values (?, ?, ?)";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            long key = -1L;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newUserCredentials.getUsername());
            preparedStatement.setString(2, newUserCredentials.getPassword());
            preparedStatement.setBoolean(3, newUserCredentials.isLoggedIn());
            int result = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(result == 1)
            {
                key = resultSet.getLong(1);
                newUserCredentials.setId((int)key);
                return newUserCredentials;
            }
            throw new UserSQLException("User could not be created please try again.");
        }catch (SQLException exception)
        {
            throw new UserSQLException((exception.getMessage()));
        }
    }

    @Override
    public User getUser(int id) {

        String sql = "select username, password, loggedIn from user where id = ?";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = new User();
            if(resultSet.next())
            {
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setLoggedIn(resultSet.getBoolean("loggedIn"));
                user.setId(id);
            }
            return user;
        }
        catch (SQLException exception)
        {
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public User updateUser(User userCredentials) {
        String sql = "update user set username = ?, password = ?, loggedIn = ? where id = ?";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userCredentials.getUsername());
            preparedStatement.setString(2, userCredentials.getPassword());
            preparedStatement.setBoolean(3, userCredentials.isLoggedIn());
            preparedStatement.setInt(4, userCredentials.getId());
            int rowCount = preparedStatement.executeUpdate();
            if(rowCount > 0)
            {
                return userCredentials;
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
    public User getUserID(String username, String password) {
        String sql = "select id from user where username = ? and password = ?";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = new User();
            if(resultSet.next())
            {
                user.setId(resultSet.getInt("id"));
            }
            return user;
        }
        catch (SQLException exception)
        {
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {

        String sql = "select * from user";
        try(Connection connection = DatabaseConnector.createConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery((sql));
            List<User> users = new ArrayList<>();
            while(resultSet.next())
            {
                User userRecord = new User();
                userRecord.setUsername(resultSet.getString("username"));
                userRecord.setPassword(resultSet.getString("password"));
                users.add(userRecord);
            }
            return users;
        }
        catch (SQLException exception)
        {
            throw new UserSQLException(exception.getMessage());
        }
    }
}
