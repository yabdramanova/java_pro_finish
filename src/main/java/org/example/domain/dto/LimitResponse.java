package org.example.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LimitResponse(Long userId,
                            BigDecimal dailyLimit,
                            BigDecimal currentDailyUsage,
                            BigDecimal availableLimit,
                            LocalDateTime lastOperationDate) {
}
