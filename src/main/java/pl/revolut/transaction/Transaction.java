package pl.revolut.transaction;

import java.time.LocalDateTime;

public class Transaction {

    private String transactionId;
    private String accountNumber;
    private LocalDateTime transactionDate;
    private Double amountInAccountCurrency;
    private Double amountInOriginalCurrency;
    private TransactionType transactionType;
    private String partnerAccountNumber;
    private String currency;
    private Double exchangeRate;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getAmountInAccountCurrency() {
        return amountInAccountCurrency;
    }

    public void setAmountInAccountCurrency(Double amountInAccountCurrency) {
        this.amountInAccountCurrency = amountInAccountCurrency;
    }

    public Double getAmountInOriginalCurrency() {
        return amountInOriginalCurrency;
    }

    public void setAmountInOriginalCurrency(Double amountInOriginalCurrency) {
        this.amountInOriginalCurrency = amountInOriginalCurrency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getPartnerAccountNumber() {
        return partnerAccountNumber;
    }

    public void setPartnerAccountNumber(String partnerAccountNumber) {
        this.partnerAccountNumber = partnerAccountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
