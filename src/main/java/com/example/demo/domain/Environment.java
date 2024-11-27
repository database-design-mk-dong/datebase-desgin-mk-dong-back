package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "environment")
@DynamicUpdate
public class Environment {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JoinColumn(name = "farm_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Farm farm;

    @Column(name = "n")
    private Double n;

    @Column(name = "k")
    private Double k;

    @Column(name = "p")
    private Double p;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "ph")
    private Double ph;

    @Column(name = "rainfall")
    private Double rainfall;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;



}
