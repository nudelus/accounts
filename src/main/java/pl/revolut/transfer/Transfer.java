package pl.revolut.transfer;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The Transfer model object
 */
public class Transfer {

    private String sourceAccountNumber;
    private String targetAccountNumber;
    private BigDecimal amount;
    private String currency;

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return Objects.equals(sourceAccountNumber, transfer.sourceAccountNumber) &&
                Objects.equals(targetAccountNumber, transfer.targetAccountNumber) &&
                Objects.equals(amount, transfer.amount) &&
                Objects.equals(currency, transfer.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceAccountNumber, targetAccountNumber, amount, currency);
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "sourceAccountNumber='" + sourceAccountNumber + '\'' +
                ", targetAccountNumber='" + targetAccountNumber + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
