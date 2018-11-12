package com.revolut.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.revolut.dao.AccountDAO;
import com.revolut.dao.DBInitializer;

import exception.CustomException;

public class DAOLayerTest {
	private AccountDAO accountDAO = new AccountDAO();
	@Test
	public void testConnection() {
		DBInitializer.initializeDB();
	}
	
	@Test
	public void testMoneyTransfer() {
		try {
			accountDAO.transferMoney(1, 2, 500);
		} catch (CustomException e) {
			assert(false);
		}
		
		try {
			accountDAO.transferMoney(1, 2, -10);
			assert(false);
		}
		catch(CustomException e) {
			assert(true);
		}
	}
	
	@Test
	public void testGetBalanceMethod() {
		try {
			accountDAO.getAccountBalance(5);
			assert(false);
		} catch (CustomException e) {
			assert(true);
		}

		try {
			accountDAO.getAccountBalance(1);
			assert(true);
		} catch (CustomException e) {
			assert(false);
		}
	}
}
