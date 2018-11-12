package com.revolut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.CustomException;

public class AccountDAO {

	public void transferMoney(double fromAccount, double toAccount, double transferedValue) throws CustomException {
		Connection connection = DBInitializer.openConnection();
		
		if(transferedValue<=0) {
			throw new CustomException("The transfered value must be greater than Zero, enter a valid value");
		}
		
		try {
			String accountValueStatement = "select value from account where id = ?";
			String updateAccountValue = "update account set value = ? where id = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(accountValueStatement);

			preparedStatement.setDouble(1, fromAccount);


			ResultSet  fromAccountValueResultSet = preparedStatement.executeQuery();
			
			double fromAccountValue=0;
			
			if(fromAccountValueResultSet.next()) {
				fromAccountValue = fromAccountValueResultSet.getDouble("Value");
			}
			else {
				throw new CustomException("");
				
			}
			if (transferedValue > fromAccountValue) {
				throw new CustomException("Error");
			}
			else {
				preparedStatement.setDouble(1, toAccount);
				
				ResultSet toAccountResultSet = preparedStatement.executeQuery();
				
				double toAccountValue = 0;
				double fromAccountNewValue = fromAccountValue - transferedValue;
				
				if(toAccountResultSet.next()) {
					toAccountValue = toAccountResultSet.getDouble("Value");
					toAccountValue += transferedValue;
					
					preparedStatement =  connection.prepareStatement(updateAccountValue);
					
					preparedStatement.setDouble(1, toAccountValue);
					preparedStatement.setDouble(2, toAccount);
					
					preparedStatement.executeUpdate();
					
					preparedStatement = connection.prepareStatement(updateAccountValue);
					
					preparedStatement.setDouble(1, fromAccountNewValue);
					preparedStatement.setDouble(2, fromAccount);
					
					preparedStatement.executeUpdate();
				}
				else {
					throw new CustomException("The account that you want to transfer to doesn't exist, please enter a valid one");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
    public double getAccountBalance(int accountId) throws CustomException {
    	Connection connection = DBInitializer.openConnection();
    	String accountValueStatement = "select value from account where id = ?";

    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(accountValueStatement);
			preparedStatement.setInt(1, accountId);
			
			ResultSet resultSet = preparedStatement.executeQuery();			
			if(resultSet.next()) {
				return resultSet.getDouble("Value");
			}
			else {
				throw new CustomException("There is no ID with this number, please enter a valid one");
			}
    	} catch (SQLException e) {
    		throw new CustomException("There is a problem within the Database connection");
		}
    }

}
