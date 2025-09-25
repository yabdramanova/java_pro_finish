package org.example.domain.dto;

import java.math.BigDecimal;

public record LimitUpdateRequest(BigDecimal newDailyLimit) {
}
