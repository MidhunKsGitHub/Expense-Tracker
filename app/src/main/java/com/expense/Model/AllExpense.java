package com.expense.Model;

public class AllExpense {
String ampunt;
String expenseType;


    public AllExpense(String ampunt, String expenseType) {
        this.ampunt = ampunt;
        this.expenseType = expenseType;
    }

    public String getAmpunt() {
        return ampunt;
    }

    public void setAmpunt(String ampunt) {
        this.ampunt = ampunt;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }
}
