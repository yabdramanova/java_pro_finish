package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.dto.LimitResponse;
import org.example.domain.dto.PaymentResponse;
import org.example.domain.entity.UserLimit;
import org.example.domain.exception.InsufficientLimitException;
import org.example.repository.LimitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimitService {

    @Value("${limit.default}")
    private String defaultDailyLimit;

    private final LimitRepository limitRepository;

    public LimitResponse getUserLimit(Long userId){
        UserLimit userLimit = getOrCreateUserLimit(userId);
        BigDecimal availableLimit = userLimit.getDailyLimit().subtract(userLimit.getDailyUsage());
        return new LimitResponse(
                userLimit.getUserId(),
                userLimit.getDailyLimit(),
                userLimit.getDailyUsage(),
                availableLimit,
                userLimit.getModifyDate()
        );

    }

    private UserLimit getOrCreateUserLimit(Long userId) {
        return limitRepository.findById(userId)
                .orElseGet(() -> createDefaultUserLimit(userId));
    }

    public PaymentResponse processPayment(Long userId, BigDecimal amount) {
        UserLimit userLimit = getOrCreateUserLimit(userId);
        BigDecimal availableLimit = userLimit.getDailyLimit().subtract(amount);
        if (availableLimit.compareTo(amount) >= 0) {
            throw new InsufficientLimitException(userLimit.getDailyLimit(), availableLimit, amount);
        }

        BigDecimal actualDailyUsage = userLimit.getDailyUsage().add(amount);
        userLimit.setDailyUsage(actualDailyUsage);
        UserLimit savedLimit = limitRepository.save(userLimit);

        log.info("Payment processed for user {}. Amount: {}, Remaining limit: {}",
                userId, amount, availableLimit);


        return new PaymentResponse(savedLimit.getUserId(),availableLimit);

    }

    public PaymentResponse refundPayment(Long userId, BigDecimal amount) {
        UserLimit userLimit = getOrCreateUserLimit(userId);
        BigDecimal actualDailyUsage = userLimit.getDailyUsage().subtract(amount);
        userLimit.setDailyUsage(actualDailyUsage);
        UserLimit savedLimit = limitRepository.save(userLimit);

        log.info("Payment refunded for user {}. Amount: {}, Updated usage: {}",
                userId, amount, savedLimit.getDailyUsage());
        BigDecimal availableLimit = userLimit.getDailyLimit().subtract(amount);
        return new PaymentResponse(savedLimit.getUserId(), availableLimit);
    }

    public LimitResponse updateDailyLimit(Long userId, BigDecimal newLimit) {
        UserLimit userLimit = getOrCreateUserLimit(userId);
        BigDecimal availableLimit = userLimit.getDailyLimit().subtract(userLimit.getDailyUsage());
        userLimit.setDailyLimit(newLimit);

        UserLimit savedLimit = limitRepository.save(userLimit);

        log.info("Daily limit updated for user {}. New limit: {}", userId, newLimit);

        return new LimitResponse(
                savedLimit.getUserId(),
                savedLimit.getDailyLimit(),
                savedLimit.getDailyUsage(),
                availableLimit,
                userLimit.getModifyDate()
        );
    }

    public void resetAllDailyLimits() {
        int updatedCount = limitRepository.resetLimitsForNewDay();

        log.info("Daily limits reset for {} users", updatedCount);
    }

    private UserLimit createDefaultUserLimit(Long userId) {
        UserLimit newLimit = new UserLimit(userId, new BigDecimal(defaultDailyLimit));
        UserLimit savedLimit = limitRepository.save(newLimit);

        log.info("Created default limit for new user {}. Limit: {}", userId, defaultDailyLimit);

        return savedLimit;
    }
}

