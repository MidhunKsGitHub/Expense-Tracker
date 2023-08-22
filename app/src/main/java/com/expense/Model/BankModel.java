package com.expense.Model;

public class BankModel {
    String id;
    String bankName;
    String userName;
    String accountNumber;
    String expiry;
    String ccv;

    public BankModel(String id,String bankName, String userName, String accountNumber, String expiry, String ccv) {
        this.id = id;
        this.bankName = bankName;
        this.userName = userName;
        this.accountNumber = accountNumber;
        this.expiry = expiry;
        this.ccv = ccv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }
}
