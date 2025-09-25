package org.example.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.LimitService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LimitResetScheduler {


    private final LimitService limitService;

    // Каждый день в 00:00
    @Scheduled(cron = "${scheduler.cron}")
    public void resetDailyLimits() {
        try {
            log.info("Starting daily limit reset...");
            limitService.resetAllDailyLimits();
            log.info("Daily limit reset completed successfully");
        } catch (Exception e) {
            log.error("Error during daily limit reset", e);
        }
    }
}
