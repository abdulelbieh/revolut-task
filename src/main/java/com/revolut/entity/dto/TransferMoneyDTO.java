package com.revolut.entity.dto;

public class TransferMoneyDTO {
	private double transferValue;
	private int fromAccountID;
	private int toAccountID;

	public double getTransferValue() {
		return transferValue;
	}
	public void setTransferValue(double transferValue) {
		this.transferValue = transferValue;
	}
	public int getFromAccountID() {
		return fromAccountID;
	}
	public void setFromAccountID(int fromAccountID) {
		this.fromAccountID = fromAccountID;
	}
	public int getToAccountID() {
		return toAccountID;
	}
	public void setToAccountID(int toAccountID) {
		this.toAccountID = toAccountID;
	}
}
