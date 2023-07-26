package com.login.project.auth.jwt.entity;

import com.login.project.auth.login.entity.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @Column(name = "uid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uid;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_uid")
    private Account account;

    @Column(nullable = false, name = "refresh_token")
    private String refreshToken;

    @Column(nullable = false, name = "expire_at")
    private LocalDateTime expireAt;

    @Column(name = "create_at")
    private LocalDateTime createAt;
}
