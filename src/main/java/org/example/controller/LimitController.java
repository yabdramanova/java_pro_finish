package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.dto.*;
import org.example.service.LimitService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/limits")
@RequiredArgsConstructor
public class LimitController {

    private final LimitService limitService;

    @GetMapping("/{userId}")
    public LimitResponse getUserLimit(@PathVariable(name = "userId") Long userId) {
        return limitService.getUserLimit(userId);
    }

    @PostMapping("/payment")
    public PaymentResponse processPayment(@RequestBody PaymentRequest request) {
        return limitService.processPayment(request.userId(), request.amount());

    }

    @PostMapping("/refund")
    public PaymentResponse refundPayment(@RequestBody RefundRequest request) {
        return limitService.refundPayment(request.userId(), request.amount());
    }

    @PutMapping("/{userId}/limit")
    public LimitResponse updateDailyLimit(
            @PathVariable(name = "userId") Long userId,
            @RequestBody LimitUpdateRequest request) {

        return limitService.updateDailyLimit(userId, request.newDailyLimit());
    }

    @PostMapping("/reset")
    public void manualReset() {
        limitService.resetAllDailyLimits();
    }
}
