package org.example.domain.exception;

import java.math.BigDecimal;

public class InsufficientLimitException extends RuntimeException{
    private BigDecimal dailyLimit;
    private BigDecimal availableLimit;
    private BigDecimal amount;
    public InsufficientLimitException(BigDecimal dailyLimit, BigDecimal availableLimit, BigDecimal amount) {
        this.dailyLimit = dailyLimit;
        this.availableLimit = availableLimit;
        this.amount = amount;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public BigDecimal getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(BigDecimal availableLimit) {
        this.availableLimit = availableLimit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
