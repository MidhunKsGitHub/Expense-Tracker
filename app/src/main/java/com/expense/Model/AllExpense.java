package com.expense.Model;

public class AllExpense {
String ampunt;
String expenseType;
String category;
String date;
String id;
String desc;

    public AllExpense(String ampunt, String expenseType, String category, String date, String desc,String id) {
        this.ampunt = ampunt;
        this.expenseType = expenseType;
        this.category = category;
        this.date = date;
        this.id = id;
        this.desc=desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setId(String id) {
        this.id = id;
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
