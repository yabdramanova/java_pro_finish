package org.example.domain.dto;

import java.math.BigDecimal;

public record PaymentRequest(Long userId,
                             BigDecimal amount) {
}
