package io.github.csv4pojo.model;

import io.github.csv4pojo.annotation.FieldType;

public class Account {
    @FieldType(dataType = FieldType.Type.STRING, csvColumnName = "Account Holder Name")
    private String accountHolderName;
    @FieldType(dataType = FieldType.Type.STRING, csvColumnName = "Creation Date")
    private String creationDate;
    @FieldType(dataType = FieldType.Type.STRING, csvColumnName = "Last Modified Date")
    private String lastModifiedDate;
    @FieldType(dataType = FieldType.Type.STRING, csvColumnName = "City")
    private String city;
    @FieldType(dataType = FieldType.Type.STRING, csvColumnName = "Country")
    private String country;
    @FieldType(dataType = FieldType.Type.INTEGER, csvColumnName = "Pin")
    private Integer pin;
    @FieldType(dataType = FieldType.Type.BOOLEAN, csvColumnName = "Active")
    private Boolean isActive;
    @FieldType(dataType = FieldType.Type.DOUBLE, csvColumnName = "Balance")
    private Double balance;
    @FieldType(dataType = FieldType.Type.FLOAT, csvColumnName = "Limit")
    private Float limit;
    @FieldType(dataType = FieldType.Type.CHARACTER, csvColumnName = "Gender")
    private Character gender;
    @FieldType(dataType = FieldType.Type.LONG, csvColumnName = "Transaction Count")
    private Long transactionCount;

    public Account() {
    }

    public Account(String accountHolderName, Double balance, String city, String country, String creationDate, Character gender,
                   Boolean isActive, String lastModifiedDate, Float limit, Integer pin, Long transactionCount) {
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.city = city;
        this.country = country;
        this.creationDate = creationDate;
        this.gender = gender;
        this.isActive = isActive;
        this.lastModifiedDate = lastModifiedDate;
        this.limit = limit;
        this.pin = pin;
        this.transactionCount = transactionCount;
    }

    public Account(String[] data) {
        if (null == data || data.length != 11) {
            throw new IllegalArgumentException("Data mismatch");
        }
        this.accountHolderName = data[0];
        this.creationDate = data[1];
        this.lastModifiedDate = data[2];
        this.city = data[3];
        this.country = data[4];
        this.pin = Integer.valueOf(data[5]);
        this.isActive = Boolean.valueOf(data[6]);
        this.balance = Double.valueOf(data[7]);
        this.limit = Float.valueOf(data[8]);
        this.gender = data[9].charAt(0);
        this.transactionCount = Long.valueOf(data[10]);
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Float getLimit() {
        return limit;
    }

    public void setLimit(Float limit) {
        this.limit = limit;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Long getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(Long transactionCount) {
        this.transactionCount = transactionCount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountHolderName='" + accountHolderName + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", lastModifiedDate='" + lastModifiedDate + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", pin=" + pin +
                ", isActive=" + isActive +
                ", balance=" + balance +
                ", limit=" + limit +
                ", gender=" + gender +
                ", transactionCount=" + transactionCount +
                '}';
    }
}
