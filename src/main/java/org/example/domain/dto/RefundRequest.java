package org.example.domain.dto;

import java.math.BigDecimal;

public record RefundRequest(Long userId,
                            BigDecimal amount) {
}
