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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "farm_device")
@DynamicUpdate
public class FarmDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JoinColumn(name = "farm_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Farm farm;

    @JoinColumn(name = "device_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Device device;

    @Column(name = "status")
    private Double status;

    @Builder
    public FarmDevice(Farm farm, Device device) {
        this.farm = farm;
        this.device = device;
        this.status = 0.0;
    }

    public static FarmDevice toEntity(Farm farm, Device device) {
        return FarmDevice.builder()
                .device(device)
                .farm(farm)
                .build();
    }

    public void updateStatus(Double status) {
        this.status = status;
    }
}
