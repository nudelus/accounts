package pl.revolut.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Transaction model object
 */
public class Transaction {

    private String transactionId;
    private String accountNumber;
    private LocalDateTime transactionDate;
    private BigDecimal amountInAccountCurrency;
    private BigDecimal amountInOriginalCurrency;
    private TransactionType transactionType;
    private String partnerAccountNumber;
    private String currency;
    private BigDecimal exchangeRate;

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

    public BigDecimal getAmountInAccountCurrency() {
        return amountInAccountCurrency;
    }

    public void setAmountInAccountCurrency(BigDecimal amountInAccountCurrency) {
        this.amountInAccountCurrency = amountInAccountCurrency;
    }

    public BigDecimal getAmountInOriginalCurrency() {
        return amountInOriginalCurrency;
    }

    public void setAmountInOriginalCurrency(BigDecimal amountInOriginalCurrency) {
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

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId) &&
                Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(amountInAccountCurrency, that.amountInAccountCurrency) &&
                Objects.equals(amountInOriginalCurrency, that.amountInOriginalCurrency) &&
                transactionType == that.transactionType &&
                Objects.equals(partnerAccountNumber, that.partnerAccountNumber) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(exchangeRate, that.exchangeRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, accountNumber, transactionDate, amountInAccountCurrency, amountInOriginalCurrency, transactionType, partnerAccountNumber, currency, exchangeRate);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", transactionDate=" + transactionDate +
                ", amountInAccountCurrency=" + amountInAccountCurrency +
                ", amountInOriginalCurrency=" + amountInOriginalCurrency +
                ", transactionType=" + transactionType +
                ", partnerAccountNumber='" + partnerAccountNumber + '\'' +
                ", currency='" + currency + '\'' +
                ", exchangeRate=" + exchangeRate +
                '}';
    }
}
