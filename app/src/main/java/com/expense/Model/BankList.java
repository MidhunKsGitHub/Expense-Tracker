package com.expense.Model;

import com.google.gson.annotations.SerializedName;

public class BankList {

String name;

    public BankList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}