package org.example.domain.dto;

import java.math.BigDecimal;

public record PaymentResponse(Long userId, BigDecimal availableLimit) {
}
