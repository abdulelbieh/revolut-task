package com.revolut.api;

import static spark.Spark.get;
import static spark.Spark.put;


import com.google.gson.Gson;
import com.revolut.dao.AccountDAO;
import com.revolut.entity.dto.TransferMoneyDTO;

import exception.CustomException;

public class AccountAPI {
	AccountDAO accountDAO = new AccountDAO();
	
	public AccountAPI() {
		put("/transfer", (request, response) -> {
		    response.type("application/json");
		     TransferMoneyDTO transferMoneyDTO = new Gson().fromJson(request.body(), TransferMoneyDTO.class);
		     
		     if(transferMoneyDTO.getTransferValue()<=0) {
		    	 response.status(400);
		    	 return "The transfered value must be greater than 0";
		     }
		     
		     else if(transferMoneyDTO.getFromAccountID() <= 0 || transferMoneyDTO.getToAccountID()<=0) {
		    	 response.status(400);
		    	 return "Enter valid Values for accounts IDs";
		     }
		     
		     try {
				accountDAO.transferMoney(transferMoneyDTO.getFromAccountID(),
						transferMoneyDTO.getToAccountID(), transferMoneyDTO.getTransferValue());
				response.status(200);
				return "success";
			} catch (CustomException exception) {
				response.status(400);
				return "faliure";
			}
		});
		
		get("/accountbalance/:id", (request, response) -> {
		    response.type("application/json");
		    
		    int accountId = Integer.parseInt(request.params(":id"));
		    
		    if(accountId<1) {
		    	response.status(400);
		    	return "The ID can't be less than 1";
		    }
		    try {
		    	double accountBalance = accountDAO.getAccountBalance(accountId);
		    	response.status(200);
		    	return accountBalance;
		    }
		    catch(CustomException exception) {
		    	response.status(404);
		    	return "failure";
		    }
		});
	}
}
