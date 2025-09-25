package org.example.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "users_limits")
@Getter
@Setter
@NoArgsConstructor
public class UserLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "daily_limit")
    private BigDecimal dailyLimit;

    @Column(name = "daily_usage")
    private BigDecimal dailyUsage;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    public UserLimit(Long userId, BigDecimal dailyLimit) {
        this.userId = userId;
        this.dailyLimit = dailyLimit;
        this.dailyUsage = BigDecimal.ZERO;
    }
}
